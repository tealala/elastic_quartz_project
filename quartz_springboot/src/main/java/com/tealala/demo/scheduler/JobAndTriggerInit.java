package com.tealala.demo.scheduler;

import com.tealala.demo.entity.SysJob;
import com.tealala.demo.job.BasicJob;
import com.tealala.demo.job.JobTask1;
import com.tealala.demo.job.JobTask2;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/4/27
 */

@Configuration
public class JobAndTriggerInit {

    @Bean("myJobDetail")
    public JobDetail jobDetailInit() {
        return JobBuilder.newJob(JobTask1.class).withIdentity("jobtask_1", "group1").build();
    }

    @Bean("myTrigger")
    public Trigger triggerInit() {
        return TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").forJob(jobDetailInit())
            .withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatForever()).build();
    }

    public static JobDetail jobDetail(SysJob sysJob)
        throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class clazz = Class.forName(sysJob.getJobClassPath());
        BasicJob basicJob = (BasicJob)clazz.newInstance();
        JobDetail jobDetail =
            JobBuilder.newJob(basicJob.getClass()).withIdentity(sysJob.getJobName(), sysJob.getJobGroup())
                .withDescription(sysJob.getJobDescribe()).build();
        return jobDetail;
    }

    public static Trigger trigger(SysJob sysJob) {
        Date date = new Date(new Date().getTime() + 2000L);
        return TriggerBuilder.newTrigger().startAt(date).withIdentity(sysJob.getJobName(), sysJob.getJobGroup())
            .withSchedule(
                CronScheduleBuilder.cronSchedule(sysJob.getJobCron()).withMisfireHandlingInstructionDoNothing())
            .build();
    }

}
