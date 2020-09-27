package com.csy.eduService.service;

import com.csy.eduService.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.csy.eduService.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author csy
 * @since 2020-09-17
 */
public interface IEduChapterService extends IService<EduChapter> {

    //    课程大纲列表，根据课程id查询
    List<ChapterVo> getChapterVideoByCourseId(String courseId);
    //    删除章节
    boolean deleteChapter(String chapterId);

    //        删除章节
    void removeChapterByCourseId(String id);
}
