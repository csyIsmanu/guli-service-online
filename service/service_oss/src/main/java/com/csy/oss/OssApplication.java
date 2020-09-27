package com.csy.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//exclude = DataSourceAutoConfiguration.class配置当启动类时不去自动配置数据源，就是不用去访问数据库，这样可以解决Failed to configure a DataSource: 'url' attribute is not specified and no e...错误
@ComponentScan(basePackages = "com.csy")
@EnableDiscoveryClient
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class,args);
    }
}
