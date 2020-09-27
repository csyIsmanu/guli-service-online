package com.csy.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    //    上传视频到阿里云
    String uploadVideoAly(MultipartFile file);
    //    根据视频id集合删除多个阿里云视频
    void removeMoreAlyVideo(List videoIdList);
}
