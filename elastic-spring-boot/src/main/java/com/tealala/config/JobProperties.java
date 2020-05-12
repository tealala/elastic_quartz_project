package com.tealala.config;

import lombok.Data;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/5/7
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "tealala.job")
public class JobProperties {

    @Value("${regCenter.serverList}")
    private String serverList;

    @Value("${regCenter.namespace}")
    private String namespace;

    private BaseJobParameters simple;

    private BaseJobParameters script;

    private BaseJobParameters dataflow;


    @Bean(name = "myDatasource")
    public DataSource dataSource(@Value("${spring.datasource.driver-class-name}")String driverclassName,@Value
        ("${spring.datasource.url}") String url,@Value("${spring.datasource.username}")String username,@Value
        ("${spring.datasource.password}")String password){
//        DataSource dataSource = DataSourceBuilder.create().driverClassName(driverclassName).url(url).username(username).password(password)
//            .build();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/local_quartz");
        dataSource.setUsername("root");
        dataSource.setPassword("root123");
        return dataSource;
    }

}
