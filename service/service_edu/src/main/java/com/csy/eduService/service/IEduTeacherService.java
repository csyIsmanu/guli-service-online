package com.csy.eduService.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csy.eduService.entity.EduCourse;
import com.csy.eduService.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.csy.eduService.entity.vo.TeacherQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author csy
 * @since 2020-09-10
 */
public interface IEduTeacherService extends IService<EduTeacher> {
    List<EduTeacher> listVo(QueryWrapper<EduTeacher> wrapperTeacher);
    //分页查询讲师
    Map<String,Object> getTeacherFrontList(Page<EduTeacher> pageTheacher);
}
