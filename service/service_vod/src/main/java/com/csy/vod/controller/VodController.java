package com.csy.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.csy.serviceBase.ExceptionHandler.GuliException;
import com.csy.utils.Result;
import com.csy.vod.service.VodService;
import com.csy.vod.utils.ConstantVodUtils;
import com.csy.vod.utils.InitVodClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
//@CrossOrigin
@Api(description="课程阿里云视频管理")
public class VodController {

    @Autowired
    private VodService vodService;


    //    上传视频到阿里云
    @ApiOperation(value = "上传视频到阿里云")
    @PostMapping("uploadAliyunVideo")
    public Result uploadAliyunVideo(@ApiParam(name = "file", value = "视频对象", required = true) MultipartFile file) {
        //返回上传视频的id值
        String videoId = vodService.uploadVideoAly(file);
        return Result.ok().data("videoId", videoId);
    }

    //    根据视频id删除阿里云视频
    @ApiOperation(value = "删除阿里云视频")
    @DeleteMapping("removeAlyVideo/{id}")
    public Result removeAlyVideo(@ApiParam(name = "id", value = "视频id", required = true) @PathVariable String id) {
        try {
//            初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.KEY_ID, ConstantVodUtils.KEY_SECRET);
//                创建删除视频request对像
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            client.getAcsResponse(request);
            return Result.ok();

        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }

    //    根据视频id集合删除多个阿里云视频
    @ApiOperation(value = "删除多个阿里云视频")
    @DeleteMapping("removeAlyVideos")
    public Result removeAlyVideos(
            @ApiParam(name = "videoIdList", value = "视频id集合", required = true)
            @RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeMoreAlyVideo(videoIdList);
        return Result.ok();
    }
    //根据视频id获取凭证
    @GetMapping("getPlayAuth/{id}")
    public Result getPlayAuth(@PathVariable String id){
        try{
            //创建初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.KEY_ID, ConstantVodUtils.KEY_SECRET);
            //创建获取凭证的response和request
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request设置视频id
            request.setVideoId(id);
            //调用方法得到凭证
            response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return Result.ok().data("playAuth",playAuth);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"获取凭证失败");
        }
    }
}
