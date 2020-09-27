package com.csy.order.client;

import com.csy.vo.UcenterMemberVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    @ApiOperation(value = "用户id获取用户信息")
    @GetMapping("/educenter/member/getUserInfo/{uid}")
    public UcenterMemberVo getUserInfo(@PathVariable("uid") String uid);
}
