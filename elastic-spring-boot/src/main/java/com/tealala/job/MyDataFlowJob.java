package com.tealala.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/5/7
 */
public class MyDataFlowJob implements DataflowJob {
    @Override
    public List fetchData(ShardingContext shardingContext) {
        System.out.println("===========DataflowJob==============");
        System.out.println(String
            .format("------【获取数据】Thread ID: %s, %s,任务总片数: %s, " + "当前分片项: %s.当前参数: %s," + "当前任务名称: %s.当前任务参数 %s",
                Thread.currentThread().getId(), new SimpleDateFormat("HH:mm:ss").format(new Date()),
                shardingContext.getShardingTotalCount(), shardingContext.getShardingItem(),
                shardingContext.getShardingParameter(), shardingContext.getJobName(),
                shardingContext.getJobParameter()));
        // 假装从文件或者数据库中获取到了数据
        Random random = new Random();
        if (random.nextInt() % 3 != 0) {
            return null;
        }
        return Arrays.asList("qingshan", "jack", "seven");
    }

    @Override
    public void processData(ShardingContext shardingContext, List list) {
        System.out.println(String
            .format("------【处理数据】Thread ID: %s, %s,任务总片数: %s, " + "当前分片项: %s.当前参数: %s," + "当前任务名称: %s.当前任务参数 %s",
                Thread.currentThread().getId(), new SimpleDateFormat("HH:mm:ss").format(new Date()),
                shardingContext.getShardingTotalCount(), shardingContext.getShardingItem(),
                shardingContext.getShardingParameter(), shardingContext.getJobName(),
                shardingContext.getJobParameter()));
        // 处理完数据要移除掉，不然就会一直跑
        list.forEach(x -> System.out.println("开始处理数据：" + x));
    }
}
