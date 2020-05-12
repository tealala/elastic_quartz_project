package com.tealala.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/4/27
 */
@EnableScheduling
@MapperScan(basePackages = "com.tealala.demo.mapper")
@SpringBootApplication
public class QuartzApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuartzApplication.class, args);
    }
}
