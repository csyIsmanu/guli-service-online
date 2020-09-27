package com.csy.eduService.client;

import com.csy.utils.Result;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class VodFileDegradeFeignClient implements VodClient{
//    熔断方法，如果远程调用没错则不会调用此类方法，出错则调用
    @Override
    public Result removeAlyVideo(String id) {
        return Result.error().message("删除视频出错了");
    }

    @Override
    public Result removeAlyVideos(List<String> videoIdList) {
        return Result.error().message("删除多个视频出错了");
    }
}
