package com.csy.statistics.client;

import com.csy.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-edu",fallback = EduCourseDegradeFeignClient.class)
public interface EduCourseClient {
    @GetMapping("/eduService/course/countCourse/{day}")
    public Result countCourse(@PathVariable("day") String day);
}
