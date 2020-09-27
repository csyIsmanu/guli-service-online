package com.csy.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.csy.msmservice.service.MsmService;
import com.csy.serviceBase.ExceptionHandler.GuliException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService{
//    发送短信的方法
    @Override
    public boolean send(Map<String, Object> param, String phone) {
        if(StringUtils.isEmpty(phone)){
            return false;
        }
        DefaultProfile profile =
        DefaultProfile.getProfile("default", "LTAI4G1y8HNgY1g5mmkg3UHi", "BGlANc9HO4DHFlSgY9Ztqka7NqQmwa");
        IAcsClient client = new DefaultAcsClient(profile);
//        设置固定参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

//        设置发送相关参数
        request.putQueryParameter("PhoneNumbers", phone);//手机号
        request.putQueryParameter("SignName", "我的在线教育平台");//阿里云申请签名名称
        request.putQueryParameter("TemplateCode", "SMS_203185779");//阿里云申请的模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));//JSONObject.toJSONString()可以将map集合变成json数据
//        最终发送
        try{
            CommonResponse commonResponse = client.getCommonResponse(request);
            boolean success = commonResponse.getHttpResponse().isSuccess();
            return success;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
