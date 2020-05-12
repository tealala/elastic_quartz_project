package listener;

import com.sun.org.apache.regexp.internal.RE;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/4/27
 */
public class MyTriggerListener implements TriggerListener {

    private String name;

    public MyTriggerListener(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * trigger 被触发 ，Job上的execute() 方法将要被执行时
     *
     * @param trigger
     * @param jobExecutionContext
     */
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext jobExecutionContext) {
        System.out.println("triggerFired method:" + trigger.getKey().getName() + " ");
    }

    /**
     * trigger 触发后，Job将要 执行时由Scheduler 调用这个方法
     * 返回true时，这个任务不会被触发
     *
     * @param trigger
     * @param jobExecutionContext
     * @return
     */
    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
        System.out.println("vetoJobExecution method:" + trigger.getKey().getName() + " ");
        return false;
    }

    /**
     * 该trigger错过了触发的时间
     *
     * @param trigger
     */
    @Override
    public void triggerMisfired(Trigger trigger) {
        System.out.println("triggerMisfired method:" + trigger.getKey().getName() + " ");
    }

    /**
     * trigger执行完毕
     *
     * @param trigger
     * @param jobExecutionContext
     * @param completedExecutionInstruction
     */
    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext,
        Trigger.CompletedExecutionInstruction completedExecutionInstruction) {
        System.out.println("triggerComplete method:" + trigger.getKey().getName() + " ");
    }
}
