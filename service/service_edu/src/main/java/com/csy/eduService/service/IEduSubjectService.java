package com.csy.eduService.service;

import com.csy.eduService.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.csy.eduService.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author csy
 * @since 2020-09-17
 */
public interface IEduSubjectService extends IService<EduSubject> {

    //添加课程分类
    void saveSubject(MultipartFile file,IEduSubjectService subjectService);

//    课程分类列表(树形)
    List<OneSubject> getAllOneAndTwoSubject();
}
