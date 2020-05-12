package com.tealala.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/4/30
 */

@Component
public class MySimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
//        System.out.println("===========simpleJob==============");
        String str = String
            .format("分片项 ShardingItem: %s | 运行时间: %s | 线程ID: %s | 分片参数: %s ", shardingContext.getShardingItem(),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:SS").format(new Date()), Thread.currentThread().getId(),
                shardingContext.getShardingParameter());
        System.out.println(str);
        int mod =  Math.floorMod(shardingContext.getShardingItem(),2);
//        switch (mod) {
//            case 0:
//                System.out.println(String.format("++++++++++++  mod:%s",mod));
//                System.out.println(str);
//                break;
//            case 1:
//                System.out.println(String.format("++++++++++++  mod:%s",mod));
//                System.out.println(str);
//                break;
//            case 2:
//                System.out.println(String.format("++++++++++++  mod:%s",mod));
//                System.out.println(str);
//                break;
//            case 3:
//                System.out.println(String.format("++++++++++++  mod:%s",mod));
//                System.out.println(str);
//                break;
//
//        };
    }
}
