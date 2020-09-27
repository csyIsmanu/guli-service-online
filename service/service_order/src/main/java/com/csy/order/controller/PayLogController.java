package com.csy.order.controller;


import com.csy.order.service.IPayLogService;
import com.csy.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author csy
 * @since 2020-09-24
 */
@RestController
@RequestMapping("/order/payLog")
//@CrossOrigin
public class PayLogController {

    @Autowired
    private IPayLogService payLogService;

    //生成微信支付二维码接口
    //参数是订单号
    @GetMapping("createNative/{orderNo}")
    public Result createNative(@PathVariable String orderNo){
        //返回信息，包含二维码地址，还有其他需要的信息
        Map map = payLogService.createNative(orderNo);
        return Result.ok().data(map);
    }

    @ApiOperation("查询订单的支付状态")
    @GetMapping("queryPayStatus/{orderNo}")
    public Result queryPayStatus(@PathVariable String orderNo){
        Map<String,String> map =  payLogService.queryPayStatus(orderNo);

        if(CollectionUtils.isEmpty(map)){
            return Result.error().message("支付出错了");
        }

        //如果返回的map不为空，通过map获取订单内容
        if("SUCCESS".equals(map.get("trade_state"))){
            //添加记录到支付表，更新订单表订单状态
            payLogService.updateOrderStatus(map);
            return Result.ok().message("支付成功");
        }
        return Result.ok().code(25000).message("支付中");
    }
}

