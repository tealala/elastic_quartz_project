package scheduler;

import com.tealala.config.job.HelloJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/4/26
 */
public class CronBasedTriggersScheduler {
    public static void main(String[] args) throws SchedulerException, InterruptedException {
        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
            .withDescription("cron based trigger com.tealala.job scheduler")
            .withIdentity("cronJob","group1")
            .build();

        CronTrigger trigger = TriggerBuilder.newTrigger()
            .withIdentity("cron trigger","group1")
            .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?"))
            .build();

        scheduler.scheduleJob(jobDetail,trigger);
        scheduler.start();

        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
