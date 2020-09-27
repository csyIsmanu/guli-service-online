package com.csy.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csy.order.entity.Order;
import com.csy.order.entity.PayLog;
import com.csy.order.mapper.PayLogMapper;
import com.csy.order.service.IOrderService;
import com.csy.order.service.IPayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csy.order.utils.HttpClient;
import com.csy.serviceBase.ExceptionHandler.GuliException;
import com.csy.utils.Result;
import com.csy.utils.ResultCode;
import com.github.wxpay.sdk.WXPayUtil;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.management.Query;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author csy
 * @since 2020-09-24
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements IPayLogService {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private  IPayLogService payLogService;
    //生成微信支付二维码接口
    @Override
    public Map createNative(String orderNo) {
        try {
            //1、根据订单号查询订单信息
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no", orderNo);
            Order order = orderService.getOne(wrapper);

            //2、使用map设置生成二维码需要参数
            Map map = new HashMap();
            map.put("appid", "wx74862e0dfcf69954");
            map.put("mch_id", "1558950191");
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            //课程标题
            map.put("body", order.getCourseTitle());
            //订单号
            map.put("out_trade_no", orderNo);
            map.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");
            map.put("spbill_create_ip", "127.0.0.1");//如果有项目，有域名，则这里填域名
            map.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            map.put("trade_type", "NATIVE");

            //3、发送httpClient请求，传递参数xml格式
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //设置xml格式的参数
            client.setXmlParam(WXPayUtil.generateSignedXml(map, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);//设置运行https访问
            //执行发送请求
            client.post();
            //4、得到发送请求返回结果
            //返回的内容是xml格式
            String content = client.getContent();
            //将xml格式转换map集合，把map集合返回
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);
            //resultMap返回的值有二维码的地址但是确实其他值故而需要再封装
            //最终返回数据 的封装
            Map dataMap = Maps.newHashMap();
            dataMap.put("out_trade_no", orderNo);
            dataMap.put("course_id", order.getCourseId());
            dataMap.put("total_fee", order.getTotalFee());
            //返回二维码操作状态码
            dataMap.put("result_code", resultMap.get("result_code"));
            //二维码地址
            dataMap.put("code_url", resultMap.get("code_url"));
            return dataMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "生成二维码失败");
        }
    }

//    查询订单的支付状态
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        //1、封装参数
        Map m = new HashMap<>(16);
        m.put("appid", "wx74862e0dfcf69954");
        m.put("mch_id", "1558950191");
        m.put("out_trade_no", orderNo);
        m.put("nonce_str", WXPayUtil.generateNonceStr());


        //2.发送httpClient
        HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
        try {
            client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();

            //3.得到请求返回的内容
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            //4.转成map返回
            return resultMap;
        } catch (Exception e) {
            return null;
        }
    }

//    添加支付记录和更新订单状态
    @Override
    public void updateOrderStatus(Map<String, String> map) {
        //从map中获取订单号
        String orderNo  = map.get("out_trade_no");
        //根据订单号查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);
        if(order.getStatus().intValue() == 1){
            return ;
        }

        //1代表已经支付
        order.setStatus(1);
        orderService.updateById(order);

        //向支付表添加支付记录
        PayLog payLog=new PayLog();
        payLog.setOrderNo(order.getOrderNo());//支付订单号
        payLog.setPayTime(new Date());//订单完成时间
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(map));
        baseMapper.insert(payLog);//插入到支付日志表
    }

}
