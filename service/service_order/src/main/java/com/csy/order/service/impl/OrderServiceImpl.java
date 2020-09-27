package com.csy.order.service.impl;

import com.csy.order.client.EduClient;
import com.csy.order.client.UcenterClient;
import com.csy.order.entity.Order;
import com.csy.order.mapper.OrderMapper;
import com.csy.order.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csy.order.utils.OrderNoUtil;
import com.csy.vo.CourseCommonVO;
import com.csy.vo.UcenterMemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author csy
 * @since 2020-09-24
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;
    //1、生成订单的方法
    @Override
    public String createOrder(String courseId, String memberId) {
        //通过用户id远程调用获取用户信息
        CourseCommonVO courseInfo = eduClient.getCourseInfo(courseId);
        //通过课程id远程调用获取课程信息
        UcenterMemberVo userInfo = ucenterClient.getUserInfo(memberId);

        //创建order对象，向order对象里面设置需要的数据
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId);//课程id
        order.setCourseTitle(courseInfo.getTitle());//课程名称
        order.setCourseCover(courseInfo.getCover());//课程封面
        order.setTeacherName(courseInfo.getTeacherName());//讲师名称
        order.setTotalFee(courseInfo.getPrice());//课程价格
        order.setMemberId(memberId);//会员id
        order.setMobile(userInfo.getMobile());//会员手机号
        order.setNickname(userInfo.getNickname());//会员昵称
        order.setStatus(0);//订单状态（0：未支付 1：已支付
        order.setPayType(1);//支付类型（1：微信 2：支付宝）
        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
