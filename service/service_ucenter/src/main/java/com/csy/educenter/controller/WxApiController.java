package com.csy.educenter.controller;

import com.csy.educenter.entity.UcenterMember;
import com.csy.educenter.service.IUcenterMemberService;
import com.csy.educenter.utils.ConstantWxUtils;
import com.csy.educenter.utils.HttpClientUtils;
import com.csy.serviceBase.ExceptionHandler.GuliException;
import com.csy.utils.JwtUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;

@Controller //只是请求地址，不需要返回数据
@RequestMapping("/api/ucenter/wx")
//@CrossOrigin
public class WxApiController {
    @Autowired
    private IUcenterMemberService memberService;

    //1、生成微信扫描二维码
    @GetMapping("login")
    public String getWxCode() {
        //固定地址后面拼接参数 %s是占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //对redirect_url进行URLEncoder编码
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "csy");

        //重定向到请求微信地址
        return "redirect:"+url;
    }
    //获取扫描人的信息，添加数据
    @GetMapping("callback")
    public String callback(String code,String state){
        try{
            //1.获取code值，临时票据，类似于验证码
            //2.拿着code请求，微信固定的地址，得到两个值 access_token 和openId
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            //拼接三个参数 ：id  秘钥 和 code值
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code
            );
            //请求这个拼接好的地址，得到返回两个值 access_token 和 openId
            //使用httpclient发送请求，得到返回结果
            String info = HttpClientUtils.get(accessTokenUrl);

            //从accessTokenInfo 字符串出来两个值 accessToken 和 openId
            //把accessTokenInfo 字符串转换成map集合，根据map里面可以 获取对应的值

            //使用json转换工具
            Gson gson = new Gson();
            HashMap accessTokenMap = gson.fromJson(info, HashMap.class);
            //昵称
            String access_token = (String) accessTokenMap.get("access_token");
            //头像
            String openid = (String) accessTokenMap.get("openid");


            //查询数据库当前用用户是否曾经使用过微信登录,根据openid查询
            UcenterMember member=memberService.getOpenIdMember(openid);
            if(member==null){//表示表里没有，可以加

                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String format = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                );
                String s = HttpClientUtils.get(format);
                HashMap hashMap = gson.fromJson(s, HashMap.class);
                String nickname = (String) hashMap.get("nickname");
                String headimgurl = (String) hashMap.get("headimgurl");

                //向数据库中插入一条记录
                member = new UcenterMember();
                member.setNickname(nickname);
                member.setOpenid(openid);
                member.setAvatar(headimgurl);
                memberService.save(member);
            }
            member.setLoginDay(new Date());
            memberService.updateById(member);
            //使用jwt根据member对象生成token字符串
            String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());

            //最后: 返回首页面，通过路径传递token字符串

            return "redirect:http://localhost:3000?token="+token;

        }catch (Exception e){
            throw new GuliException(20001,"微信登录失败");
        }
    }
}
