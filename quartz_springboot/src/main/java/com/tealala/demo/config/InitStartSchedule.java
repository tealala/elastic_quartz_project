package com.tealala.demo.config;

import com.alibaba.fastjson.JSONObject;
import com.tealala.demo.entity.SysJob;
import com.tealala.demo.job.BasicJob;
import com.tealala.demo.listener.MyJobListener;
import com.tealala.demo.listener.MySchedulerListener;
import com.tealala.demo.listener.MyTriggerListener;
import com.tealala.demo.service.SysJobService;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/4/28
 */
@Component
public class InitStartSchedule implements CommandLineRunner {

    @Autowired
    private SysJobService sysJobService;

    @Autowired
    private MyJobFactory jobFactory;

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private MyJobListener myJobListener;

    @Autowired
    private MyTriggerListener myTriggerListener;

    @Autowired
    private MySchedulerListener mySchedulerListener;

    @Override
    public void run(String... args) throws Exception {
        SysJob inner = new SysJob();
        inner.setJobStatus(1);
        List<SysJob> sysJobs = sysJobService.querySysJobList(inner);
        if (sysJobs == null) {
            System.out.println("don't have any task need to carry on!");
        }

        //        SchedulerFactory factory = new StdSchedulerFactory();
        //        Scheduler scheduler = factory.getScheduler();
        //        scheduler.setJobFactory(jobFactory);
        scheduler.start();
        scheduler.getListenerManager().addJobListener(myJobListener);
        scheduler.getListenerManager().addSchedulerListener(mySchedulerListener);
        scheduler.getListenerManager().addTriggerListener(myTriggerListener);

        for (SysJob sysJob : sysJobs) {
            //            scheduler.pauseTrigger(TriggerKey.triggerKey(sysJob.getJobName(), sysJob.getJobGroup()));
            //            scheduler.unscheduleJob(TriggerKey.triggerKey(sysJob.getJobName(), sysJob.getJobGroup()));
            //            scheduler.deleteJob(JobKey.jobKey(sysJob.getJobName(), sysJob.getJobGroup()));

            JobDetail jobDetail = JobBuilder.newJob(initClass(sysJob.getJobClassPath()).getClass())
                .withIdentity(sysJob.getJobName(), sysJob.getJobGroup()).build();
            if (StringUtils.isNotEmpty(sysJob.getJobDataMap())) {
                JSONObject jsonObject = JSONObject.parseObject(sysJob.getJobDataMap());
                Map<String, Object> data = (Map<String, Object>)jsonObject.get("data");
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    jobDetail.getJobDataMap().put(entry.getKey(), entry.getValue());
                }
            }

            CronTrigger cronTrigger =
                TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(sysJob.getJobCron()))
                    .withIdentity(sysJob.getJobName(), sysJob.getJobGroup()).startNow().build();

            if (!scheduler.checkExists(jobDetail.getKey())) {
                scheduler.scheduleJob(jobDetail, cronTrigger);
            }
        }

    }

    public BasicJob initClass(String className) throws Exception {
        Class clazz = Class.forName(className);
        return (BasicJob)clazz.newInstance();
    }
}
