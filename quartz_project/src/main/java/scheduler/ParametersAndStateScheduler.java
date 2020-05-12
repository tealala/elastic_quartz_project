package scheduler;

import com.tealala.config.job.ColorJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/4/26
 */
public class ParametersAndStateScheduler {

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();

        JobDetail jobDetail1 = JobBuilder.newJob(ColorJob.class)
            .withIdentity("job1","group1")
            .build();

        SimpleTrigger trigger1 = TriggerBuilder.newTrigger()
            .withIdentity("trigger1","group1")
            .startAt(new Date())
            .withSchedule(
                SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(10)
                    .withRepeatCount(4))
            .build();

        jobDetail1.getJobDataMap().put(ColorJob.FAVORITE_COLOR,"green");
        jobDetail1.getJobDataMap().put(ColorJob.EXECUTION_COUNT,1);

        JobDetail jobDetail2 = JobBuilder.newJob(ColorJob.class)
            .withIdentity("job2","group1")
            .build();

        SimpleTrigger trigger2 = TriggerBuilder.newTrigger()
            .withIdentity("trigger2","group1")
            .startAt(new Date())
            .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(10)
                .withRepeatCount(4))
            .build();

        jobDetail2.getJobDataMap().put(ColorJob.FAVORITE_COLOR,"red");
        jobDetail2.getJobDataMap().put(ColorJob.EXECUTION_COUNT, 1);

        scheduler.scheduleJob(jobDetail1,trigger1);
        scheduler.scheduleJob(jobDetail2,trigger2);
        scheduler.start();

        Thread.sleep(60L*1000L);

        scheduler.shutdown();

    }
}
