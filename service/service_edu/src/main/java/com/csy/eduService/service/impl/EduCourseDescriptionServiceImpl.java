package com.csy.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csy.eduService.entity.EduChapter;
import com.csy.eduService.entity.EduCourseDescription;
import com.csy.eduService.mapper.EduCourseDescriptionMapper;
import com.csy.eduService.service.IEduCourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author csy
 * @since 2020-09-17
 */
@Service
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements IEduCourseDescriptionService {
}
