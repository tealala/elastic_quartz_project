package simple;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/5/7
 */
public class MyLocalSimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        String str = String
            .format("分片项 ShardingItem: %s | 运行时间: %s | 线程ID: %s | 分片参数: %s ", shardingContext.getShardingItem(),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:SS").format(new Date()), Thread.currentThread().getId(),
                shardingContext.getShardingParameter());
        switch (shardingContext.getShardingItem()) {
            case 0:
                System.out.println(str);
                break;
            case 1:
                System.out.println(str);
                break;
            case 2:
                System.out.println(str);
                break;
            case 3:
                System.out.println(str);
                break;

        };
    }
}
