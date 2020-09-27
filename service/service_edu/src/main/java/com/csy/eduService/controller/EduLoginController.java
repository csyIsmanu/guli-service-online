package com.csy.eduService.controller;

import com.csy.utils.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
@Api(description = "后台登录")
@RestController
@RequestMapping("/eduService/user")
//@CrossOrigin //解决端口跨域问题
public class EduLoginController {

    @PostMapping("login")
    public Result login() {
        return Result.ok().data("token", "admin");
    }

    @GetMapping("info")
    public Result info() {
        return Result.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://guli-avatar-file.oss-cn-beijing.aliyuncs.com/2020/09/16/f2d39ab3ea8d4a73921e950cd3a6047bfile.png");
    }
}
