package com.csy.order.service;

import com.csy.order.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author csy
 * @since 2020-09-24
 */
public interface IPayLogService extends IService<PayLog> {
    //生成微信支付二维码接口
    Map createNative(String orderNo);

    Map<String,String> queryPayStatus(String orderNo);

    void updateOrderStatus(Map<String, String> map);
}
