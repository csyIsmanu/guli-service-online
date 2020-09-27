package com.csy.statistics.service;

import com.csy.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author csy
 * @since 2020-09-25
 */
public interface IDailyService extends IService<Daily> {

    void registerCount(String day);

    Map<String,Object> getChartData(String type, String begin, String end);
}
