package com.csy.eduCms.controller;


import com.csy.eduCms.entity.CrmBanner;
import com.csy.eduCms.service.ICrmBannerService;
import com.csy.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器 用户查看
 * </p>
 *
 * @author csy
 * @since 2020-09-21
 */
@RestController
@RequestMapping("/educms/bannerFront")
//@CrossOrigin
public class CrmBannerFrontController {
    @Autowired
    private ICrmBannerService bannerService;

//    查询所有banner
    @Cacheable(key = "'seletIndexList'",value = "banner")
    @GetMapping("getAllBanner")
    public Result getAllBanner(){
        List<CrmBanner> list = bannerService.selectAllBanner();
        return Result.ok().data("list",list);
    }
}

