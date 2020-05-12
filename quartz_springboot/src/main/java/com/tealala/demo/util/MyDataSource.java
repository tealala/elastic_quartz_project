package com.tealala.demo.util;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/4/27
 */
@Component
public class MyDataSource {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    private DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        return dataSource;
    }
}
