package com.csy.eduService.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csy.eduService.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.csy.eduService.entity.frontvo.CourseFrontVo;
import com.csy.eduService.entity.frontvo.CourseWebVo;
import com.csy.eduService.entity.vo.CourseInfoVo;
import com.csy.eduService.entity.vo.CoursePublishVo;
import com.csy.eduService.entity.vo.CourseQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author csy
 * @since 2020-09-17
 */
public interface IEduCourseService extends IService<EduCourse> {
    /**
     * 保存课程和课程详情信息
     * @param courseInfoVo
     * @return 新生成的课程id
     */
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    //    根据课程id查询课程基本信息
    CourseInfoVo getCourseInfo(String courseId);

    //    修改课程基本信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    //根据课程id查询课程确认信息
    CoursePublishVo getCoursePublishVoById(String id);

    //分页多条件查询课程
    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);

    //  删除课程
    void removeCourse(String id);

    List<EduCourse> listVo(QueryWrapper<EduCourse> wrapper);
//    课程条件带分页列表
    Map<String,Object> getCourseList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo);

    //根据课程id查询课程信息，要用到左外连接
    CourseWebVo getBaseCourseInfo(String courseId);

    Integer countCourseDay(String day);
}
