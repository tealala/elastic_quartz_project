package simple;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.apache.commons.dbcp.BasicDataSource;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/5/7
 */
public class MyLocalSimpleJobTest {

    public static void main(String[] args){
        //zk注册
        CoordinatorRegistryCenter center =
            new ZookeeperRegistryCenter(new ZookeeperConfiguration("172.22.22.29:2181", "my-local-simple-com.tealala.config.job"));

        //定义作业核心配置
        JobCoreConfiguration simpleCoreConfig = JobCoreConfiguration.newBuilder("demoSimpleJob", "0/15 * * * * ?", 4)
            .shardingItemParameters("0=SHARDING_ONE, 1=SHARDING_TWO, 2=SHARDING_THREE, 3=SHARDING_FOUR").failover(true)
            .build();

        //定义type类型配置：simple/dataflaw/script
        SimpleJobConfiguration simpleJobConfiguration =
            new SimpleJobConfiguration(simpleCoreConfig, MyLocalSimpleJob.class.getCanonicalName());

        //定义Lite作业根配置
        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfiguration).build();

        //初始化数据源
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.186.1:3306/local_quartz");
        dataSource.setUsername("root");
        dataSource.setPassword("root123");

        //定义日志数据库事件溯源配置
        JobEventConfiguration jobEventConfiguration = new JobEventRdbConfiguration(dataSource);

        //启动作业
        center.init();
        new JobScheduler(center, simpleJobRootConfig, jobEventConfiguration).init();

    }
}
