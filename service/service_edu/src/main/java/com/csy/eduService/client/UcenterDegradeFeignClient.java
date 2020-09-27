package com.csy.eduService.client;

import com.csy.serviceBase.ExceptionHandler.GuliException;
import com.csy.utils.Result;
import com.csy.vo.UcenterMemberVo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UcenterDegradeFeignClient implements UcenterClient{

    @Override
    public UcenterMemberVo getUserInfo(String uid) {
        throw new GuliException(20001,"查询用户信息出错");
    }
}
