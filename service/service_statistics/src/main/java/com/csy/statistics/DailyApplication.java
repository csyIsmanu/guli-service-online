package com.csy.statistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"com.csy"})
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.csy.statistics.mapper")
@EnableScheduling//开启定时任务
public class DailyApplication {
    public static void main(String[] args) {
        SpringApplication.run(DailyApplication.class,args);
    }
}
