package dataflaw;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.api.strategy.impl.AverageAllocationJobShardingStrategy;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/4/30
 */
public class MyDataflowJobTest {

    public static void main(String[] args) {
        // core type lite
        //ZK 注册
        CoordinatorRegistryCenter center = new ZookeeperRegistryCenter(
            new ZookeeperConfiguration("172.22.22.29:2181", "mydataflowjob-namespace-test"));
        center.init();

        //定义作业核心配置
        JobCoreConfiguration coreConfiguration = JobCoreConfiguration.newBuilder("myDataflowJob", "1/5 * * * * ?", 4)
            .shardingItemParameters("0=dataflow_1, 1=dataflow_2, 2=dataflow_3, 3=dataflow_4").build();

        //定义simple 类型配置
        DataflowJobConfiguration jobConfiguration =
            new DataflowJobConfiguration(coreConfiguration, MyDataflowJob.class.getCanonicalName(), true);

        //作业分片策略
        //基于平均分配算法 的分配策略
        String jobShardingStrategyClass = AverageAllocationJobShardingStrategy.class.getCanonicalName();

        //定义Lite作业根配置
        LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(jobConfiguration)
            .jobShardingStrategyClass(jobShardingStrategyClass).build();

        //构建job
        new JobScheduler(center, liteJobConfiguration).init();

    }
}
