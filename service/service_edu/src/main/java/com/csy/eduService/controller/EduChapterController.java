package com.csy.eduService.controller;


import com.csy.eduService.entity.EduChapter;
import com.csy.eduService.entity.chapter.ChapterVo;
import com.csy.eduService.service.IEduChapterService;
import com.csy.eduService.service.IEduCourseService;
import com.csy.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController
@Api(description="课程章节管理")
@RequestMapping("/eduService/chapter")
//@CrossOrigin //在使用gateWay处理跨域问题后，便可以取消这个跨域注解，不然会造成两次跨域效果，
public class EduChapterController {

    @Autowired
    private IEduChapterService chapterService;


//    课程大纲列表，根据课程id查询
    @ApiOperation(value = "嵌套章节数据列表")
    @GetMapping("getChapterVideo/{courseId}")
    public Result getChapterVideo(@ApiParam(name = "courseId",value = "课程id",required = true)
                                      @PathVariable String courseId){
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return Result.ok().data("allChapterVideo",list);
    }
//    添加章节
    @ApiOperation(value = "添加章节")
    @PostMapping("addChapter")
    public Result addChapter(@ApiParam(name = "chapter",value = "章节对象",required = true)
                             @RequestBody EduChapter chapter){
        chapterService.save(chapter);
        return Result.ok();
    }

    //    根据id查询章节
    @ApiOperation(value = "查询章节")
    @GetMapping("getChapterInfo/{chapterId}")
    public Result getChapterInfo(@ApiParam(name = "chapterId",value = "章节id",required = true)
                             @PathVariable String chapterId){
        EduChapter byId = chapterService.getById(chapterId);
        return Result.ok().data("chapter",byId);
    }

    //    修改章节
    @ApiOperation(value = "修改章节")
    @PostMapping("updateChapter")
    public Result updateChapter(@ApiParam(name = "chapter",value = "章节对象",required = true)
                             @RequestBody EduChapter chapter){
        chapterService.updateById(chapter);
        return Result.ok();
    }
//    删除章节
    @ApiOperation(value = "删除章节")
    @DeleteMapping("deleteChapter/{chapterId}")
    public Result deleteChapter(@ApiParam(name = "chapterId",value = "章节id",required = true)
                                    @PathVariable String chapterId){
        boolean flag = chapterService.deleteChapter(chapterId);
        return flag?Result.ok():Result.error();
    }
}

