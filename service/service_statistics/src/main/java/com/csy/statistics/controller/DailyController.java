package com.csy.statistics.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.csy.statistics.service.IDailyService;
import com.csy.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author csy
 * @since 2020-09-25
 */
@RestController
@RequestMapping("/statistics/daily")
//@CrossOrigin
public class DailyController {
    @Autowired
    private IDailyService dailyService;

    //统计某一天注册人数
    @PostMapping("registerCount/{day}")
    public Result registerCount(@PathVariable String day) {
        dailyService.registerCount(day);
        return Result.ok();
    }

    //图表显示，返回两部分数据，日期json数组，数量json数组
    @GetMapping("showData/{type}/{begin}/{end}")
    public Result showData(@PathVariable String type, @PathVariable String begin, @PathVariable String end) {
        Map<String, Object> map = dailyService.getChartData( type,begin, end);
        return Result.ok().data(map);
    }
}

