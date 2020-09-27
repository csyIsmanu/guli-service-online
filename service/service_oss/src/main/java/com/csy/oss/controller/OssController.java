package com.csy.oss.controller;

import com.csy.oss.service.OssService;
import com.csy.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileOss")
//@CrossOrigin
@Api(description="图像Oss管理")
public class OssController {

    @Autowired
    private OssService ossService;

    @ApiOperation(value = "上传头像文件到阿里云oss")
    @PostMapping
    public Result uploadOssFile(@ApiParam(name = "file", value = "图片" ,required = true) MultipartFile file){
        String url = ossService.uploadFileAvatar(file);
        return Result.ok().data("url",url);
    }
}
