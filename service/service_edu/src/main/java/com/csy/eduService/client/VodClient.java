package com.csy.eduService.client;

import com.csy.utils.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component//将改类交给spring管理
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)//加入nocas注册的生产者服务  fallback：表示熔断错误后执行该类方法，
public interface VodClient {
    //    定义调用方法的路径
//    根据视频id远程调用方法删除阿里云视频
    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")//路径一定要是完全路径
    public Result removeAlyVideo(@PathVariable("id") String id);//@PathVariable一定要指定参数名称，不然会出现问题

    //    定义调用方法的路径
    //    根据视频id集合删除多个阿里云视频
    @DeleteMapping("/eduvod/video/removeAlyVideos")//路径一定要是完全路径
    public Result removeAlyVideos(@RequestParam("videoIdList") List<String> videoIdList);//@PathVariable一定要指定参数名称，不然会出现问题

}
