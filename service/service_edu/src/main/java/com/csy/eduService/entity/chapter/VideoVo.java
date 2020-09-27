package com.csy.eduService.entity.chapter;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "课时信息")
@Data
public class VideoVo {
    private String id;

    private String title;

    private String videoSourceId;//视频id
}
