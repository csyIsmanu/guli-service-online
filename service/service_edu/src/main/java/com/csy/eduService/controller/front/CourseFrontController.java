package com.csy.eduService.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csy.eduService.client.OrderClient;
import com.csy.eduService.entity.EduCourse;
import com.csy.eduService.entity.EduTeacher;
import com.csy.eduService.entity.chapter.ChapterVo;
import com.csy.eduService.entity.frontvo.CourseFrontVo;
import com.csy.eduService.entity.frontvo.CourseWebVo;
import com.csy.eduService.service.IEduChapterService;
import com.csy.eduService.service.IEduCourseService;
import com.csy.eduService.service.IEduTeacherService;
import com.csy.utils.JwtUtils;
import com.csy.utils.Result;
import com.csy.vo.CourseCommonVO;
import com.csy.vo.UcenterMemberVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduService/coursefront")
//@CrossOrigin
@Api(description = "前台课程展示")
public class CourseFrontController {

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private IEduCourseService courseService;
    @Autowired
    private IEduChapterService chapterService;

    @ApiOperation(value = "课程条件带分页列表")
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public Result getFrontCourseList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseFrontVo", value = "查询对象", required = false)
            @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageParam = new Page<EduCourse>(page, limit);
        Map<String, Object> map = courseService.getCourseList(pageParam, courseFrontVo);
        return Result.ok().data(map);
    }

//    查询课程详情的方法
    @GetMapping("getFrontCourseInfo/{courseId}")
    public Result getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){
        //根据课程id查询课程信息，要用到左外连接
        CourseWebVo courseInfo = courseService.getBaseCourseInfo(courseId);
        //根据课程id查询章节小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        //根据课程id和用户id查询改课程是否已经支付过了
        boolean buyCourse = orderClient.isBuyCourse(courseId, JwtUtils.getMemberIdByJwtToken(request));

        return Result.ok().data("courseInfo",courseInfo).data("chapterVideoList",chapterVideoList).data("isBuy",buyCourse);
    }
//    根据课程id查询课程信息
    @ApiOperation(value = "课程id查询课程信息")
    @GetMapping("getCourseInfo/{courseId}")
    public CourseCommonVO getCourseInfo(@PathVariable String courseId) {
        CourseWebVo courseInfo = courseService.getBaseCourseInfo(courseId);
        CourseCommonVO courseCommonVO = new CourseCommonVO();
        BeanUtils.copyProperties(courseInfo,courseCommonVO);
        return courseCommonVO;
    }

}
