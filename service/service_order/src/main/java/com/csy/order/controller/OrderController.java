package com.csy.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csy.order.entity.Order;
import com.csy.order.service.IOrderService;
import com.csy.utils.JwtUtils;
import com.csy.utils.Result;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author csy
 * @since 2020-09-24
 */
@RestController
@RequestMapping("/order/orderweb")
//@CrossOrigin
public class OrderController {
    @Autowired
    private IOrderService orderService;
    //1、生成订单的方法
    @PostMapping("createOrder/{courseId}")
    public Result createOrder(@PathVariable String courseId, HttpServletRequest request){
        String token = JwtUtils.getMemberIdByJwtToken(request);
        if(token.isEmpty()||token.equals("")){
            return Result.error().message("您还未登录，不能购买课程");
        }
        //创建订单，返回订单号
        String orderNo = orderService.createOrder(courseId, token);
        return Result.ok().data("orderId",orderNo);
    }

    //1、根据订单id查询订单信息
    @GetMapping("getOrderInfo/{oid}")
    public Result getOrderInfo(@PathVariable String oid){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",oid);
        Order one = orderService.getOne(wrapper);
        return Result.ok().data("item",one);
    }
    //根据课程id和用户id查询订单表中订单状态
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,@PathVariable String memberId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);
        int count = orderService.count(wrapper);
        return count>0?true:false;
    }
}

