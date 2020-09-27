package com.csy.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csy.eduService.client.VodClient;
import com.csy.eduService.entity.EduVideo;
import com.csy.eduService.mapper.EduVideoMapper;
import com.csy.eduService.service.IEduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author csy
 * @since 2020-09-17
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements IEduVideoService {
//        删除小节

//    注入vodClient
    @Autowired
    private VodClient vodClient;
    @Override
    public void removeVideoByCourseId(String id) {
//        1、根据课程id查询ke所有视频id

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
//        查询指定的列值
        wrapper.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(wrapper);
        List<String> list = new ArrayList<>();
        for (EduVideo video : eduVideos) {
            String s = video.getVideoSourceId();
            if(!StringUtils.isEmpty(s)){
                list.add(s);

            }
        }
        if(list.size()>0){
            vodClient.removeAlyVideos(list);
        }
        //删除小节
        QueryWrapper<EduVideo> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("course_id",id);
        baseMapper.delete(wrapper1);
    }
}
