package com.tealala.config;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/4/30
 */

@Configuration
public class ElasticRegCenterConfig {

    @Autowired
    private JobProperties jobProperties;

    @Bean(initMethod = "init",name = "registryCenter")
    public ZookeeperRegistryCenter registryCenter() {
        ZookeeperRegistryCenter center = new ZookeeperRegistryCenter(
            new ZookeeperConfiguration(jobProperties.getServerList(), jobProperties.getNamespace()));
        return center;
    }

}
