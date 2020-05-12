package com.tealala.demo.listener;

import org.quartz.*;
import org.springframework.stereotype.Component;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/5/9
 */
@Component
public class MySchedulerListener implements SchedulerListener {
    @Override
    public void jobScheduled(Trigger trigger) {
        System.out.println("this is my job scheduler");
    }

    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {
        System.out.println("job is unscheduled");
    }

    @Override
    public void triggerFinalized(Trigger trigger) {
        System.out.println("The bind trigger is finalized");
    }

    @Override
    public void triggerPaused(TriggerKey triggerKey) {
        System.out.println("my trigger is paused  【"+triggerKey.getName()+"】【"+triggerKey.getGroup()+"】");
    }

    @Override
    public void triggersPaused(String s) {
        System.out.println("my trigger is paused "+s);
    }

    @Override
    public void triggerResumed(TriggerKey triggerKey) {
        System.out.println("my trigger is resumed 【"+triggerKey.getName()+"】【"+triggerKey.getGroup()+"】");
    }

    @Override
    public void triggersResumed(String s) {
        System.out.println("my trigger is resumed "+s);
    }

    @Override
    public void jobAdded(JobDetail jobDetail) {
        System.out.println("add job 【"+jobDetail.getKey().getName()+"】【" + jobDetail.getKey().getGroup() +"】");
    }

    @Override
    public void jobDeleted(JobKey jobKey) {
        System.out.println("delete job 【" +jobKey.getName()+"】【" + jobKey.getGroup() +"】");
    }

    @Override
    public void jobPaused(JobKey jobKey) {
        System.out.println("my scheduler is started");
    }

    @Override
    public void jobsPaused(String s) {
        System.out.println("my scheduler is started");
    }

    @Override
    public void jobResumed(JobKey jobKey) {
        System.out.println("my scheduler is started");
    }

    @Override
    public void jobsResumed(String s) {
        System.out.println("my scheduler is started");
    }

    @Override
    public void schedulerError(String s, SchedulerException e) {
        System.out.println("my scheduler is started");
    }

    @Override
    public void schedulerInStandbyMode() {
        System.out.println("my scheduler is started");
    }

    @Override
    public void schedulerStarted() {
        System.out.println("my scheduler is started");
    }

    @Override
    public void schedulerStarting() {
        System.out.println("my scheduler is started");
    }

    @Override
    public void schedulerShutdown() {
        System.out.println("my scheduler is started");
    }

    @Override
    public void schedulerShuttingdown() {
        System.out.println("my scheduler is started");
    }

    @Override
    public void schedulingDataCleared() {
        System.out.println("my scheduler is started");
    }
}
