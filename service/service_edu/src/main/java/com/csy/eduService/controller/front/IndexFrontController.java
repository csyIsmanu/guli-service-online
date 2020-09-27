package com.csy.eduService.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.csy.eduService.entity.EduCourse;
import com.csy.eduService.entity.EduTeacher;
import com.csy.eduService.service.IEduCourseService;
import com.csy.eduService.service.IEduTeacherService;
import com.csy.utils.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Api(description="前台首页展示")
@RestController
@RequestMapping("/eduService/indexfront")
//@CrossOrigin
public class IndexFrontController {

    @Autowired
    private IEduCourseService courseService;
    @Autowired
    private IEduTeacherService teacherService;
        //查询前8条热门课程，查询前4条名师
    @GetMapping("index")
    public Result index() {

        //查询前8条热门课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> eduList = courseService.listVo(wrapper);
        //查询前4条名师
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> teacherList = teacherService.listVo(wrapperTeacher);
        return Result.ok().data("eduList",eduList).data("teacherList",teacherList);
    }
}
