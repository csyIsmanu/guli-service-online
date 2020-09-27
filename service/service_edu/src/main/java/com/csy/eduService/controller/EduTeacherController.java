package com.csy.eduService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csy.eduService.entity.EduTeacher;
import com.csy.eduService.entity.vo.TeacherQuery;
import com.csy.eduService.service.IEduTeacherService;
import com.csy.serviceBase.ExceptionHandler.GuliException;
import com.csy.utils.Result;
import com.csy.utils.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 * rest风格：查询用@GetMapping，添加用@PostMapping,修改用@PutMapping,删除用
 *
 * @author csy
 * @since 2020-09-10
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduService/teacher")
//@CrossOrigin
public class EduTeacherController {
    @Autowired
    private IEduTeacherService teacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public Result findAll() {
        return Result.ok().data("items", teacherService.list(null));
    }

    @ApiOperation(value = "根据id删除讲师")
    @DeleteMapping("deleteById/{id}")
    public Result removeTeacher(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable String id) {
        return teacherService.removeById(id) ? Result.ok() : Result.error();
    }

    @ApiOperation(value = "分页讲师列表")
    @GetMapping("pageTeacher/{page}/{limit}")
    public Result pageTeacher(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
                              @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Integer limit) {
        Page<EduTeacher> pageTheacher = new Page<>(page, limit);
        teacherService.page(pageTheacher, null);
        long total = pageTheacher.getTotal();
        List<EduTeacher> records = pageTheacher.getRecords();
        return Result.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "讲师多条件查询加分页列表")
    @PostMapping("pageTeacherQuery/{page}/{limit}")
    public Result pageTeacherQuery(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
                                   @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Integer limit,
                                   @ApiParam(name = "teacherQuery", value = "查询对象", required = false) @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<EduTeacher> teacherPage = new Page<>(page, limit);
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            queryWrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create", end);
        }
        queryWrapper.orderByDesc("gmt_create");
        teacherService.page(teacherPage, queryWrapper);
        long total = teacherPage.getTotal();
        List<EduTeacher> records = teacherPage.getRecords();
        return Result.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "新增讲师")
    @PostMapping("addTeacher")
    public Result saveTeacher(@RequestBody EduTeacher teacher) {
        boolean save = teacherService.save(teacher);
        return save ? Result.ok() : Result.error();
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("findTeacher/{id}")
    public Result findTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        EduTeacher teacher = teacherService.getById(id);
        return teacher==null?Result.error():Result.ok().data("item", teacher);
    }

    @ApiOperation(value = "修改讲师")
    @PostMapping("updateTeacher")
    public Result updateTeacher(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher teacher) {
        boolean b = teacherService.updateById(teacher);
        return b?Result.ok():Result.error();
    }
}

