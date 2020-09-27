package com.csy.eduService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csy.eduService.entity.EduCourse;
import com.csy.eduService.entity.EduTeacher;
import com.csy.eduService.entity.vo.CourseInfoVo;
import com.csy.eduService.entity.vo.CoursePublishVo;
import com.csy.eduService.entity.vo.CourseQuery;
import com.csy.eduService.entity.vo.TeacherQuery;
import com.csy.eduService.service.IEduCourseService;
import com.csy.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author csy
 * @since 2020-09-17
 */
@Api(description = "课程管理")
@RestController
@RequestMapping("/eduService/course")
//@CrossOrigin
public class EduCourseController {
    @Autowired
    private IEduCourseService courseService;

    //    课程列表 Todo
    @ApiOperation(value = "所有课程列表")
    @GetMapping("getCourseList")
    public Result getCourseList() {
        return Result.ok().data("list", courseService.list(null));
    }

    @ApiOperation(value = "分页课程列表")
    @PostMapping("pageQuery/{page}/{limit}")
    public Result pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody(required = false) CourseQuery courseQuery) {
        Page<EduCourse> pageParam = new Page<>(page, limit);
        courseService.pageQuery(pageParam, courseQuery);
        List<EduCourse> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return Result.ok().data("total", total).data("rows", records);
    }


    @ApiOperation(value = "新增课程")
    @PostMapping("addCourseInfo")
    public Result addCourseInfo(@ApiParam(name = "CourseInfoForm", value = "课程基本信息", required = true) @RequestBody CourseInfoVo courseInfoVo) {
        String id = courseService.saveCourseInfo(courseInfoVo);
        return Result.ok().data("courseId", id);
    }

    //    根据课程id查询课程基本信息
    @ApiOperation(value = "查询课程基本信息")
    @GetMapping("getCourseInfo/{courseId}")
    public Result getCourseInfo(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return Result.ok().data("courseInfoVo", courseInfoVo);
    }

    //    修改课程基本信息
    @ApiOperation(value = "修改课程基本信息")
    @PostMapping("updateCourseInfo")
    public Result updateCourseInfo(
            @ApiParam(name = "courseInfoVo", value = "课程Info对象", required = true)
            @RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return Result.ok();
    }
    //根据课程id查询课程确认信息

    @ApiOperation(value = "根据ID获取课程发布信息")
    @GetMapping("getPublishCourseInfo/{id}")
    public Result getCoursePublishVoById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {
        CoursePublishVo courseInfoForm = courseService.getCoursePublishVoById(id);
        return Result.ok().data("publishCourse", courseInfoForm);
    }

    //    课程最终发布
    @ApiOperation(value = "发布课程")
    @PostMapping("publishCourse/{id}")
    public Result publishCourse(
            @ApiParam(name = "id", value = "课程id", required = true)
            @PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");//设置课程发布状态
        courseService.updateById(eduCourse);
        return Result.ok();
    }

    //  删除课程
    @ApiOperation(value = "删除课程")
    @DeleteMapping("deleteCourse/{id}")
    public Result deleteCourse(
            @ApiParam(name = "id", value = "课程id", required = true)
            @PathVariable String id) {
        courseService.removeCourse(id);
        return Result.ok();
    }

    @ApiOperation(value = "查询每日新增课程数")
    @GetMapping("countCourse/{day}")
    public Result countCourse(@PathVariable String day) {
        Integer count = courseService.countCourseDay(day);
        return Result.ok().data("countCourse",count);
    }
}

