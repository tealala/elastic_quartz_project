package com.tealala.config;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.api.strategy.impl.AverageAllocationJobShardingStrategy;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.tealala.job.MyDataFlowJob;
import com.tealala.job.MySimpleJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/5/7
 */

@Configuration
public class ElasticJobConfig {

    @Autowired
    private ZookeeperRegistryCenter registryCenter;

    @Autowired
    private JobProperties jobProperties;

    @Autowired
    private DataSource myDatasource;

    @Bean(initMethod = "init", name = "simpleJobScheduler")
    public JobScheduler simpleJobScheduler() throws IllegalAccessException, InstantiationException {
        JobEventConfiguration jobEventConfiguration = new JobEventRdbConfiguration(myDatasource);
        BaseJobParameters simpleJob = jobProperties.getSimple();
        JobCoreConfiguration coreConfiguration = JobCoreConfiguration
            .newBuilder(simpleJob.getJobParameter(), simpleJob.getCron(), simpleJob.getShardingTotalCount())
            .shardingItemParameters(simpleJob.getShardingItemParameters()).failover(true).build();

        SimpleJobConfiguration simpleJobConfiguration =
            new SimpleJobConfiguration(coreConfiguration, MySimpleJob.class.getCanonicalName());

        //基于平均分配算法的分配策略
        String strategyClass = AverageAllocationJobShardingStrategy.class.getCanonicalName();

        LiteJobConfiguration jobConfiguration = LiteJobConfiguration.newBuilder(simpleJobConfiguration)
            .jobShardingStrategyClass(strategyClass).build();
        JobScheduler jobScheduler =
            new SpringJobScheduler(MySimpleJob.class.newInstance(), registryCenter, jobConfiguration,
                jobEventConfiguration);
        return jobScheduler;
    }

//    @Bean(initMethod = "init", name = "dataFlawJobScheduler")
    public JobScheduler dataFlawJobScheduler() throws IllegalAccessException, InstantiationException {
        JobEventConfiguration jobEventConfiguration = new JobEventRdbConfiguration(myDatasource);

        BaseJobParameters dataflow = jobProperties.getDataflow();

        JobCoreConfiguration coreConfiguration = JobCoreConfiguration
            .newBuilder(dataflow.getJobParameter(), dataflow.getCron(), dataflow.getShardingTotalCount())
            .shardingItemParameters(dataflow.getShardingItemParameters()).failover(true).build();

        DataflowJobConfiguration dataflowJobConfiguration =
            new DataflowJobConfiguration(coreConfiguration, MyDataFlowJob.class.getCanonicalName(), false);

        //基于平均分配算法的分配策略
        String strategyClass = AverageAllocationJobShardingStrategy.class.getCanonicalName();

        LiteJobConfiguration jobConfiguration = LiteJobConfiguration.newBuilder(dataflowJobConfiguration)
            .jobShardingStrategyClass(strategyClass).build();
        JobScheduler jobScheduler =
            new SpringJobScheduler(MyDataFlowJob.class.newInstance(), registryCenter, jobConfiguration,
                jobEventConfiguration);
        return jobScheduler;
    }

}
