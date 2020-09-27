package com.csy.eduCms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csy.eduCms.entity.CrmBanner;
import com.csy.eduCms.service.ICrmBannerService;
import com.csy.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器 管理员增删改
 * </p>
 *
 * @author csy
 * @since 2020-09-21
 */
@Api(description = "轮播图管理")
@RestController
@RequestMapping("/educms/bannerAdmin")
//@CrossOrigin
public class CrmBannerAdminController {
    @Autowired
    private ICrmBannerService bannerService;

    //    分页查询banner
    @ApiOperation(value = "分页轮播图列表")
    @GetMapping("pageBanner/{page}/{limit}")
    public Result pageBanner(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
                              @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Integer limit) {
        Page<CrmBanner> pageBanner = new Page<>(page, limit);
        bannerService.page(pageBanner, null);
        long total = pageBanner.getTotal();
        List<CrmBanner> records = pageBanner.getRecords();
        return Result.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("getBanner/{id}")
    public Result get(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return Result.ok().data("item", banner);
    }

    @ApiOperation(value = "新增Banner")
    @PostMapping("saveBanner")
    public Result save(@RequestBody CrmBanner banner) {
        bannerService.save(banner);
        return Result.ok();
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("updateBanner")
    public Result updateById(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return Result.ok();
    }
    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id) {
        bannerService.removeById(id);
        return Result.ok();
    }

}

