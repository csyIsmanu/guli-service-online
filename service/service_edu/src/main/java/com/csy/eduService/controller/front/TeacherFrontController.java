package com.csy.eduService.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csy.eduService.entity.EduCourse;
import com.csy.eduService.entity.EduTeacher;
import com.csy.eduService.service.IEduCourseService;
import com.csy.eduService.service.IEduTeacherService;
import com.csy.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduService/teacherfront")
//@CrossOrigin
@Api(description="前台讲师展示")
public class TeacherFrontController {
    @Autowired
    private IEduTeacherService teacherService;
    @Autowired
    private IEduCourseService courseService;
    //分页查询讲师
    @ApiOperation(value = "分页讲师列表")
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public Result getTeacherFrontList(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
                                      @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Integer limit){
        Page<EduTeacher> pageTheacher = new Page<>(page, limit);
        Map<String,Object> map = teacherService.getTeacherFrontList(pageTheacher);
        //返回分页所有数据
        return Result.ok().data(map);
    }
    //讲师详情信息
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public Result getTeacherFrontInfo(@PathVariable String teacherId){
        EduTeacher teacher = teacherService.getById(teacherId);

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> list = courseService.list(wrapper);
        return Result.ok().data("teacher",teacher).data("courseList",list);
    }
}
