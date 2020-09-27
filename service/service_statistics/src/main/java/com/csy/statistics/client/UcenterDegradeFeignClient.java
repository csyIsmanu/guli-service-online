package com.csy.statistics.client;

import com.csy.utils.Result;
import org.springframework.stereotype.Component;

@Component
public class UcenterDegradeFeignClient implements UcenterClient{
    @Override
    public Result countRegister(String day) {
        return Result.error().message("查询失败");
    }

    @Override
    public Result countLogin(String day) {
        return Result.error().message("查询失败");
    }
}
