package com.csy.eduCms.service;

import com.csy.eduCms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author csy
 * @since 2020-09-21
 */
public interface ICrmBannerService extends IService<CrmBanner> {
//    查询所有banner
    List<CrmBanner> selectAllBanner();
}
