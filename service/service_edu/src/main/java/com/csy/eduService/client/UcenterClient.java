package com.csy.eduService.client;

import com.csy.utils.Result;
import com.csy.vo.UcenterMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-ucenter",fallback = UcenterDegradeFeignClient.class)
public interface UcenterClient {
    //    定义调用方法的路径
    @GetMapping("/educenter/member/getUserInfo/{uid}")//路径一定要是完全路径
    public UcenterMemberVo getUserInfo(@PathVariable("uid") String uid);//@PathVariable一定要指定参数名称，不然会出现问题

}
