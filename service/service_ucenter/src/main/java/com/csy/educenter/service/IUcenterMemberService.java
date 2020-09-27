package com.csy.educenter.service;

import com.csy.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.csy.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author csy
 * @since 2020-09-22
 */
public interface IUcenterMemberService extends IService<UcenterMember> {
//登录
    String login(UcenterMember loginVo);

    void register(RegisterVo registerVo);

    UcenterMember getOpenIdMember(String openid);

    Integer countRegisterDay(String day);

    Integer countLoginDay(String day);
}
