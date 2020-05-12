package simple;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.api.strategy.impl.AverageAllocationJobShardingStrategy;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import org.apache.commons.dbcp.BasicDataSource;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/4/28
 */
public class SimpleJobTest {

    public static void main(String[] args) {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/local_quartz");
        dataSource.setUsername("root");
        dataSource.setPassword("root123");
        JobEventConfiguration jobEventConfig = new JobEventRdbConfiguration(dataSource);


        //ZK 注册
        CoordinatorRegistryCenter center = new ZookeeperRegistryCenter(
            new ZookeeperConfiguration("172.22.22.29:2181", "elastic-com.tealala.config.job-simplejob-single-test"));
        center.init();

        //定义作业核心配置  JobCoreConfiguration，JobTypeConfiguration和LiteJobConfiguration
        JobCoreConfiguration coreConfiguration = JobCoreConfiguration.newBuilder("mySimpleJob", "0/2 * * * * ?", 4)
            .shardingItemParameters("0=SHARDING_ONE, 1=SHARDING_TWO, 2=SHARDING_THREE, 3=SHARDING_FOUR").failover(true)
            .build();
        //定义simple 类型配置
        SimpleJobConfiguration simpleJobConfiguration =
            new SimpleJobConfiguration(coreConfiguration, MySimpleJob.class.getCanonicalName());

        //作业分片策略
        //基于平均分配算法的分配策略
        String strategyClass = AverageAllocationJobShardingStrategy.class.getCanonicalName();

        //定义Lite作业根配置
        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfiguration).build();

        //构建job
        new JobScheduler(center, simpleJobRootConfig,jobEventConfig).init();

    }
}
