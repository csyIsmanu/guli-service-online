package com.csy.eduService.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csy.eduService.entity.EduSubject;
import com.csy.eduService.entity.excel.SubjectData;
import com.csy.eduService.entity.subject.OneSubject;
import com.csy.eduService.entity.subject.TwoSubject;
import com.csy.eduService.listener.SubjectExcelListener;
import com.csy.eduService.mapper.EduSubjectMapper;
import com.csy.eduService.service.IEduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author csy
 * @since 2020-09-17
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements IEduSubjectService {
    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,IEduSubjectService subjectService) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    课程分类列表(树形)
    @Override
    public List<OneSubject> getAllOneAndTwoSubject() {
        //查询所有一级分类
        QueryWrapper<EduSubject> oneSubject = new QueryWrapper<>();
        oneSubject.eq("parent_id","0");
        List<EduSubject> oneSubjects = baseMapper.selectList(oneSubject);
        //查询所有二级分类
        QueryWrapper<EduSubject> twoSubject = new QueryWrapper<>();
        twoSubject.ne("parent_id","0");
        List<EduSubject> twoSubjects = baseMapper.selectList(twoSubject);

        List<OneSubject> finalSubjectList = new ArrayList<>();
        for (EduSubject subject : oneSubjects) {
            OneSubject oneSubject1 = new OneSubject();
//            oneSubject1.setId(subject.getId());
//            oneSubject1.setTitle(subject.getTitle());//因为如果又多个值就会很不方便，所以可以用BeanUtils工具栏
            BeanUtils.copyProperties(subject,oneSubject1);
            List<TwoSubject> twoSubjects1 = new ArrayList<>();
            for (EduSubject subject1 : twoSubjects) {
                if(subject1.getParentId().equals(subject.getId())){
                    TwoSubject twoSubject1 = new TwoSubject();
//                    twoSubject1.setId(subject1.getId());
//                    twoSubject1.setTitle(subject1.getTitle());
                    BeanUtils.copyProperties(subject1,twoSubject1);
                    twoSubjects1.add(twoSubject1);
                }
            }
            oneSubject1.setChildren(twoSubjects1);
            finalSubjectList.add(oneSubject1);
        }


        return finalSubjectList;
    }
}
