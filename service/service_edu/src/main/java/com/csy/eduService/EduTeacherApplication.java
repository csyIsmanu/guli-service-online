package com.csy.eduService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
//SpringBoot在写启动类的时候如果不使用@ComponentScan指明对象扫描范围，
// 默认指扫描当前启动类所在的包里的对象，如果当前启动类没有包，
// 则在启动时会报错：Your ApplicationContext is unlikely to start due to a @ComponentScan of the default package错误。
// 因为启动类不能直接放在main/java文件夹下，必须要建一个包把它放进去或者使用@ComponentScan指明要扫描的包

@SpringBootApplication
@ComponentScan(basePackages = {"com.csy"})
@MapperScan("com.csy.eduService.mapper")
@EnableDiscoveryClient //nocas注册启动注解
@EnableFeignClients //服务的调用，代码要写在调用方（消费者）
public class EduTeacherApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduTeacherApplication.class, args);
    }
}
