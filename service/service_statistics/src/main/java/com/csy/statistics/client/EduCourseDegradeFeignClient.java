package com.csy.statistics.client;

import com.csy.utils.Result;
import org.springframework.stereotype.Component;

@Component
public class EduCourseDegradeFeignClient implements EduCourseClient{

    @Override
    public Result countCourse(String day) {
        return Result.error().message("查询失败");
    }
}
