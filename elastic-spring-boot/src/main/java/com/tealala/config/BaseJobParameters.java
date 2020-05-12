package com.tealala.config;

import lombok.Data;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/5/7
 */

@Data
public class BaseJobParameters implements Cloneable{
    private String cron;
    private int shardingTotalCount;
    private String shardingItemParameters;
    private String jobParameter;
}
