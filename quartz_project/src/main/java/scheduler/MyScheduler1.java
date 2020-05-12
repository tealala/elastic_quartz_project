package scheduler;

import com.tealala.config.job.HelloJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/4/26
 */
public class MyScheduler1 {
    public static void main(String[] args) throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
            .withDescription("test myscheduler description")
            .withIdentity("job1", "group1").build();

        Date runTime = new Date();

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startAt(runTime).build();

        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();

        scheduler.shutdown(true);

    }
}
