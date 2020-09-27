package com.csy.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csy.educenter.entity.UcenterMember;
import com.csy.educenter.entity.vo.RegisterVo;
import com.csy.educenter.mapper.UcenterMemberMapper;
import com.csy.educenter.service.IUcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csy.serviceBase.ExceptionHandler.GuliException;
import com.csy.utils.JwtUtils;
import com.csy.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author csy
 * @since 2020-09-22
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements IUcenterMemberService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //登录
    @Override
    public String login(UcenterMember loginVo) {
//        获取登录手机号和密码
        String password = loginVo.getPassword();
        String phone = loginVo.getMobile();
//        手机号和密码非空判断
        if(StringUtils.isEmpty(password)||StringUtils.isEmpty(phone)){
            throw new GuliException(20001,"登录失败");
        }
//        判断手机号
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",phone);
        UcenterMember one = baseMapper.selectOne(wrapper);
        if(one==null){
            throw new GuliException(20001,"手机号不存在，登录失败");
        }

        //   判断密码 因为密码存入数据库不可能是明文，所以需要将password转成密文再比较
//        使用MD5加密 ：特点 只能加密不能解密
        if(!MD5.encrypt(password).equals(one.getPassword())){
            throw new GuliException(20001,"密码错误，登录失败");
        }
//        判断用户是否禁用
        if(one.getIsDisabled()){
            throw new GuliException(20001,"用户已被禁用，登录失败");
        }
//        登录成功后生成token字符串 使用jwt工具类实现
        String jwtToken = JwtUtils.getJwtToken(one.getId(), one.getNickname());
        //登录成功后更新loginDay
        one.setLoginDay(new Date());
        baseMapper.updateById(one);
        return jwtToken;
    }

    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        if(StringUtils.isEmpty(password)||StringUtils.isEmpty(mobile)
                ||StringUtils.isEmpty(code)||StringUtils.isEmpty(nickname)){
            throw new GuliException(20001,"注册失败");
        }
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)){
            throw new GuliException(20001,"注册失败");
        }
//        判断手机号是否重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        int count = baseMapper.selectCount(wrapper);
        if(count>0){
            throw new GuliException(20001,"注册失败");
        }
        UcenterMember member = new UcenterMember();
        member.setNickname(nickname);
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("https://guli-avatar-file.oss-cn-beijing.aliyuncs.com/2020/09/16/f2d39ab3ea8d4a73921e950cd3a6047bfile.png");
        baseMapper.insert(member);
    }

    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }

    @Override
    public Integer countLoginDay(String day) {
        return baseMapper.countLoginDay(day);
    }
}
