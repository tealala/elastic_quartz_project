package scheduler;


import com.tealala.config.job.StatefulDumbJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/4/27
 */
public class MisfireScheduler {

    public static void main(String[] args) throws SchedulerException {

        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();

        JobDetail jobDetail1 = JobBuilder.newJob(StatefulDumbJob.class)
            .withIdentity("statefulJob1","group1")
            .usingJobData(StatefulDumbJob.EXECUTION_DELAY,10000L)
            .build();

        SimpleTrigger trigger1 = TriggerBuilder.newTrigger()
            .withIdentity("trigger1","group1")
            .startAt(new Date())
            .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(3)
                .repeatForever())
            .build();


        JobDetail jobDetail2 = JobBuilder.newJob(StatefulDumbJob.class)
            .withIdentity("statefulJob2","group2")
            .usingJobData(StatefulDumbJob.EXECUTION_DELAY,10000L)
            .build();

        SimpleTrigger trigger2 = TriggerBuilder.newTrigger()
            .withIdentity("trigger2","group2")
            .startAt(new Date())
            .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(3)
                .repeatForever()
                .withMisfireHandlingInstructionNextWithExistingCount())
            .build();
        scheduler.scheduleJob(jobDetail1,trigger1);
        scheduler.scheduleJob(jobDetail2,trigger2);

        scheduler.start();

        try {
            Thread.sleep(600L* 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        scheduler.shutdown(true);
    }

}
