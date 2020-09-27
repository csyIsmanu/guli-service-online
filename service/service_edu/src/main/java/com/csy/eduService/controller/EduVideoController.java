package com.csy.eduService.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.csy.eduService.client.VodClient;
import com.csy.eduService.entity.EduChapter;
import com.csy.eduService.entity.EduVideo;
import com.csy.eduService.service.IEduVideoService;
import com.csy.serviceBase.ExceptionHandler.GuliException;
import com.csy.utils.Result;
import com.sun.xml.bind.v2.model.core.ID;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author csy
 * @since 2020-09-17
 */
@RestController
@Api(description="课时视频管理")
@RequestMapping("/eduService/video")
//@CrossOrigin
public class EduVideoController {
    @Autowired
    private IEduVideoService videoService;

//    注入VodClient调用服务方法
    @Autowired
    private VodClient vodClient;

//    添加小节
    @ApiOperation(value = "添加小节")
    @PostMapping("addVideo")
    public Result addVideo(@ApiParam(name = "video",value = "课程小节对象",required = true)
                             @RequestBody EduVideo video){
        videoService.save(video);
        return Result.ok();
    }

//    删除小节
    @ApiOperation(value = "删除课程小节")
    @DeleteMapping("deleteVideo/{videoId}")
    public Result deleteVideo(@ApiParam(name = "videoId",value = "小节id",required = true)
                                @PathVariable String videoId){
        //        先根据小节id找到视频id再删除视频
        EduVideo video = videoService.getById(videoId);
        String id = video.getVideoSourceId();
        if(!StringUtils.isEmpty(id)){
            Result result = vodClient.removeAlyVideo(id);
            if(result.getCode()==20001){
                throw new GuliException(20001,"删除视频失败，熔断器....");
            }
        }
//        使用springCloud调用删除视频的方法
        boolean flag = videoService.removeById(videoId);
        return flag?Result.ok():Result.error();
    }
    //根据id查询小节
    @ApiOperation(value = "查询课时")
    @GetMapping("getVideoInfo/{videoId}")
    public Result getVideoInfo(@ApiParam(name = "videoId",value = "小节id",required = true)
                                 @PathVariable String videoId){
        EduVideo byId = videoService.getById(videoId);
        return Result.ok().data("video",byId);
    }
//    修改小节
    @ApiOperation(value = "修改小节")
    @PostMapping("updateVideo")
    public Result updateVideo(@ApiParam(name = "video",value = "小节对象",required = true)
                                @RequestBody EduVideo video){
        videoService.updateById(video);
        return Result.ok();
    }
}

