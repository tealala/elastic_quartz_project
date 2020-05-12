package com.tealala.demo.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/5/9
 */
public class JobTask2 implements BasicJob {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("=============jobTask_2=============");
        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
        String name = jobKey.getName();
        String group = jobKey.getGroup();
        String str = String
            .format("current job name【%s】 ;group:【%s】 ;thread id 【%s】", name, group, Thread.currentThread().getId());
        System.out.println(str);
    }
}
