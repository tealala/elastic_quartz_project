package com.tealala.demo.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.stereotype.Component;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/5/9
 */
@Component
public class MyJobListener implements JobListener {
    @Override
    public String getName() {
        return "my_job_listener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {

    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {

    }

    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {

    }
}
