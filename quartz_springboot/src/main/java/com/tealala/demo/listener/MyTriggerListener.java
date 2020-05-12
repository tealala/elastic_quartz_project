package com.tealala.demo.listener;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.springframework.stereotype.Component;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/5/9
 */

@Component
public class MyTriggerListener implements TriggerListener {
    private String name;

    public MyTriggerListener() {
    }

    public MyTriggerListener(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        if(this.name ==null){
            this.name = "my_trigger_listener";
        }
        return this.name;
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext jobExecutionContext) {
        System.out.println("my trigger is fired");
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        System.out.println("my trigger is misfired");
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext,
        Trigger.CompletedExecutionInstruction completedExecutionInstruction) {
        System.out.println("my trigger is completed");
    }
}
