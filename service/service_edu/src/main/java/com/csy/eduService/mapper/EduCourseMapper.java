package com.csy.eduService.mapper;

import com.csy.eduService.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csy.eduService.entity.frontvo.CourseWebVo;
import com.csy.eduService.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author csy
 * @since 2020-09-17
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    public CoursePublishVo getPublishCourseInfo(String courseId);

    //根据课程id查询课程信息，要用到左外连接
    CourseWebVo getBaseCourseInfo(String courseId);

    Integer countCourseDay(String day);
}
