package com.tealala.config.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/4/26
 */
public class HelloJob implements Job {
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Date time = new Date();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
        String message = jobExecutionContext.getJobDetail().getDescription();
        System.out.println("任务job1，执行时间：" + date + "; 执行的信息为：" + message);
    }
}
