package com.csy.eduService.controller;


import com.csy.eduService.entity.EduSubject;
import com.csy.eduService.entity.subject.OneSubject;
import com.csy.eduService.service.IEduSubjectService;
import com.csy.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author csy
 * @since 2020-09-17
 */
@RestController
@RequestMapping("/eduService/subject")
//@CrossOrigin
@Api(description ="课程分类管理")
public class EduSubjectController {
    @Autowired
    private IEduSubjectService subjectService;

    //添加课程分类
    @ApiOperation(value = "Excel批量导入")
    @PostMapping("addSubject")
    public Result addSubject(MultipartFile file){
        //1 获取上传的excel文件 MultipartFile
        subjectService.saveSubject(file,subjectService);
        return Result.ok();
    }

//    课程分类列表(树形)
    @GetMapping("getAllSubject")
    public Result getAllSubject(){
        List<OneSubject> list = subjectService.getAllOneAndTwoSubject();
        return Result.ok().data("list",list);
    }
}

