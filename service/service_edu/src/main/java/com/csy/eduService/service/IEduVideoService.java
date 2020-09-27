package com.csy.eduService.service;

import com.csy.eduService.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author csy
 * @since 2020-09-17
 */
public interface IEduVideoService extends IService<EduVideo> {

    //        删除小节
    void removeVideoByCourseId(String id);
}
