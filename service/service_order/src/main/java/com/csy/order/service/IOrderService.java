package com.csy.order.service;

import com.csy.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author csy
 * @since 2020-09-24
 */
public interface IOrderService extends IService<Order> {
    //1、生成订单的方法
    String createOrder(String courseId, String memberIdByJwtToken);
}
