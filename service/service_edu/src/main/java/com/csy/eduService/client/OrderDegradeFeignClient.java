package com.csy.eduService.client;

import com.csy.serviceBase.ExceptionHandler.GuliException;
import com.csy.vo.UcenterMemberVo;
import org.springframework.stereotype.Component;

@Component
public class OrderDegradeFeignClient implements OrderClient{

    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
//        throw new GuliException(20001,"查询失败");
        return false;
    }
}
