package com.csy.eduService.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csy.eduService.entity.EduSubject;
import com.csy.eduService.entity.excel.SubjectData;
import com.csy.eduService.service.IEduSubjectService;
import com.csy.serviceBase.ExceptionHandler.GuliException;

//不SubjectExcelListener能交给spring容器管理，也不能注入需要手动注入
public class SubjectExcelListener extends AnalysisEventListener<SubjectData>{
    //手动注入
    public IEduSubjectService subjectService;

    public SubjectExcelListener() {}
    public SubjectExcelListener(IEduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //读取excel内容，一行一行进行读取
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData==null){
            throw new GuliException(20001,"文件数据为空");
        }
        //一行一行进行读取，每次读取两个值，第一个值为一级分类，第二个值为二级分类
        EduSubject eduSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if(eduSubject==null){
//            没有一级分类，添加
            eduSubject = new EduSubject();
            eduSubject.setParentId("0");
            eduSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(eduSubject);
        }
        String pid = eduSubject.getId();
        EduSubject eduSubject1 = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(),pid);
        if(eduSubject1==null){
//            没有二级分类，添加
            eduSubject1 = new EduSubject();
            eduSubject1.setParentId(pid);
            eduSubject1.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(eduSubject1);
        }
    }
    //判断二级分类是否重复
    private EduSubject existTwoSubject(IEduSubjectService subjectService,String name,String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject eduSubject = subjectService.getOne(wrapper);
        return eduSubject;
    }
        //判断一级分类是否重复
    private EduSubject existOneSubject(IEduSubjectService subjectService, String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject eduSubject = subjectService.getOne(wrapper);
        return eduSubject;
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
