package com.csy.eduCms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csy.eduCms.entity.CrmBanner;
import com.csy.eduCms.mapper.CrmBannerMapper;
import com.csy.eduCms.service.ICrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author csy
 * @since 2020-09-21
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements ICrmBannerService {
//    查询所有banner
    @Override
    public List<CrmBanner> selectAllBanner() {
//        根据sort进行降序查询，显示排列后前五个记录
        QueryWrapper<CrmBanner> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("sort");
//        last拼接sql语句
        queryWrapper.last("limit 5");
        return baseMapper.selectList(queryWrapper);
    }
}
