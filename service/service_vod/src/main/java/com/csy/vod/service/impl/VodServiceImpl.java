package com.csy.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.csy.serviceBase.ExceptionHandler.GuliException;
import com.csy.utils.ExceptionUtil;
import com.csy.utils.Result;
import com.csy.vod.service.VodService;
import com.csy.vod.utils.ConstantVodUtils;
import com.csy.vod.utils.InitVodClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadVideoAly(MultipartFile file) {
        try {
            //        fileName上传文件的原始名称
            String fileName = file.getOriginalFilename();
            //        title上传之后的名称
            String title = fileName.substring(0,fileName.lastIndexOf("."));

//        inputStream上传文件的输入流
            InputStream inputStream = file.getInputStream();

            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.KEY_ID, ConstantVodUtils.KEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl upload = new UploadVideoImpl();
            UploadStreamResponse response = upload.uploadStream(request);
            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else {
                videoId = response.getVideoId();
            }
            return videoId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //    根据视频id集合删除多个阿里云视频
    @Override
    public void removeMoreAlyVideo(List videoIdList) {
        try {
//            初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.KEY_ID, ConstantVodUtils.KEY_SECRET);
//                创建删除视频request对像
            DeleteVideoRequest request = new DeleteVideoRequest();
            String ids = StringUtils.join(videoIdList.toArray(), ",");
            request.setVideoIds(ids);
            client.getAcsResponse(request);

        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }
}
