package com.csy.educenter.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.csy.educenter.entity.UcenterMember;
import com.csy.educenter.entity.vo.RegisterVo;
import com.csy.educenter.service.IUcenterMemberService;
import com.csy.serviceBase.ExceptionHandler.GuliException;
import com.csy.utils.JwtUtils;
import com.csy.utils.Result;
import com.csy.vo.UcenterMemberVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author csy
 * @since 2020-09-22
 */
@RestController
@RequestMapping("/educenter/member")
//@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private IUcenterMemberService memberService;

    @ApiOperation(value = "会员登录")
    @PostMapping("login")
    public Result login(@RequestBody UcenterMember loginVo) {
        String token = memberService.login(loginVo);
        return Result.ok().data("token", token);
    }

    @ApiOperation(value = "会员注册")
    @PostMapping("register")
    public Result register(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return Result.ok();
    }

    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("getMemberInfo")
    public Result getLoginInfo(HttpServletRequest request) {

        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember byId = memberService.getById(memberId);
        return Result.ok().data("userInfo", byId);

    }

    @ApiOperation(value = "用户id获取用户信息")
    @GetMapping("getUserInfo/{uid}")
    public UcenterMemberVo getUserInfo(@PathVariable String uid) {
        UcenterMember byId = memberService.getById(uid);
        UcenterMemberVo ucenterMemberVo = new UcenterMemberVo();
        BeanUtils.copyProperties(byId,ucenterMemberVo);
        return ucenterMemberVo;

    }

    @ApiOperation(value = "查询某一天注册人数")
    @GetMapping("countRegister/{day}")
    public Result countRegister(@PathVariable String day) {
        Integer count = memberService.countRegisterDay(day);
        return Result.ok().data("countRegister",count);
    }
    @ApiOperation(value = "查询某一天登录人数")
    @GetMapping("countLogin/{day}")
    public Result countLogin(@PathVariable String day) {
        Integer count = memberService.countLoginDay(day);
        return Result.ok().data("countLogin",count);
    }
}

