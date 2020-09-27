package com.csy.msmservice.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.csy.msmservice.service.MsmService;
import com.csy.msmservice.utils.RandomUtil;
import com.csy.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
//@CrossOrigin
public class MsmController {
    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    //    发送短信的方法
    @GetMapping("send/{phone}")
    public Result sendMsm(@PathVariable String phone) {
//        先从redis获取验证码，如果获取直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)) {
            return Result.ok();
        }
        code = RandomUtil.getFourBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
//        调用service中发送短信的方法
        boolean isSend = msmService.send(param,phone);
        if (isSend) {
//            发送成功则将发送成功后的验证码传入redis中
//            设置有效时间
            redisTemplate.opsForValue().set(phone,code,5,TimeUnit.MINUTES);
            return Result.ok();
        } else {
            return Result.error().message("发送短信失败");
        }
    }
}

