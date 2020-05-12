package com.tealala.demo.util;

import com.tealala.demo.entity.SysJob;
import com.tealala.demo.scheduler.JobAndTriggerInit;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/4/27
 */
@Service
public class JobUtil {

    @Autowired
    @Qualifier("scheduler")
    private Scheduler scheduler;

    @Autowired
    @Qualifier("myJobDetail")
    private JobDetail jobDetail;

    @Autowired
    @Qualifier("myTrigger")
    private Trigger trigger;

    /**
     * 添加job
     *
     * @return
     * @throws Exception
     */
    public String addJob(SysJob sysJob) throws Exception {
        JobKey jobKey = new JobKey(sysJob.getJobName(), sysJob.getJobGroup());
        if (!scheduler.isShutdown()) {
            JobDetail detail = scheduler.getJobDetail(jobKey);
            if (detail != null) {
                scheduler.deleteJob(jobKey);
            }
        }
        scheduler.scheduleJob(JobAndTriggerInit.jobDetail(sysJob), JobAndTriggerInit.trigger(sysJob));
        scheduler.start();
        //        Thread.sleep(10);
        //        scheduler.shutdown(true);
        return "success";
    }

    public String addJobLocal() throws SchedulerException, InterruptedException {
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
        Thread.sleep(10);
        scheduler.shutdown(true);
        return "success";
    }

    /**
     * 获取job状态
     *
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public String getJobState(String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(jobName, jobGroup);
        return scheduler.getTriggerState(triggerKey).name();
    }

    /**
     * 暂停所有任务
     *
     * @throws SchedulerException
     */
    public String pauseAllJob(List<SysJob> list) throws SchedulerException {
        for (SysJob sysJob : list) {
            TriggerKey triggerKey = new TriggerKey(sysJob.getJobName(), sysJob.getJobGroup());
            Trigger trigger = scheduler.getTrigger(triggerKey);
            JobKey jobKey = new JobKey(sysJob.getJobName(), sysJob.getJobGroup());
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (trigger != null) {
                scheduler.unscheduleJob(triggerKey);
                scheduler.pauseJob(jobKey);
            }
        }
        return "don't have any running com.tealala.config.job";

    }

    /**
     * 暂停某个任务
     *
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public String pauseJob(String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(jobName, jobGroup);
        JobKey jobKey = new JobKey(jobName, jobGroup);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return "fail";
        } else {
            scheduler.unscheduleJob(triggerKey);
            scheduler.pauseJob(jobKey);
            return "success";
        }
    }

    /**
     * 恢复某个job
     *
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public String resumeJob(String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(jobName, jobGroup);
        Trigger trigger = scheduler.getTrigger(triggerKey);
        if (trigger == null) {
            return "fail";
        } else {
            scheduler.rescheduleJob(triggerKey, trigger);
            return "success";
        }
    }

    /**
     * 恢复所有的job
     *
     * @return
     * @throws SchedulerException
     */
    public String resumeJob(List<SysJob> list) throws SchedulerException {
        for (SysJob sysJob : list) {
            TriggerKey triggerKey = new TriggerKey(sysJob.getJobName(), sysJob.getJobGroup());
            Trigger trigger = scheduler.getTrigger(triggerKey);
            JobKey jobKey = new JobKey(sysJob.getJobName(), sysJob.getJobGroup());
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail != null) {
                scheduler.resumeJob(jobKey);
                scheduler.rescheduleJob(triggerKey, trigger);
            }
        }
        return "success";

    }

    /**
     * 删除 某个job
     *
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public String deleteJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return "jobDetail is null";
        } else if (!scheduler.checkExists(jobKey)) {
            return "jobKey is not exist";
        } else {
            scheduler.deleteJob(jobKey);
            return "success";
        }
    }

    public String modifyJob(String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        JobKey jobKey = new JobKey(jobName, jobGroup);
        if (scheduler.checkExists(jobKey) && scheduler.checkExists(triggerKey)) {
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup).build();
            scheduler.rescheduleJob(triggerKey, trigger);
            return "success";
        } else {
            return "com.tealala.config.job or trigger not exist";
        }
    }
}
