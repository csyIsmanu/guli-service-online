package com.csy.statistics.schedule;

import com.csy.statistics.service.IDailyService;
import com.csy.statistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {
    @Autowired
    private IDailyService dailyService;
    /**
     * 每天凌晨1点执行定时，将前一天的数据进行数据查询添加
     */
    @Scheduled(cron = "0 0 1 * * ?")//springboot默认支持六位
    public void task2() {
        //获取上一天的日期
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        dailyService.registerCount(day);
    }
}
