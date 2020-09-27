package com.csy.order.client;

import com.csy.vo.CourseCommonVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-edu")
public interface EduClient {

    //    根据课程id查询课程信息
    @ApiOperation(value = "课程id查询课程信息")
    @GetMapping("/eduService/coursefront/getCourseInfo/{courseId}")
    public CourseCommonVO getCourseInfo(@PathVariable("courseId") String courseId);
}
