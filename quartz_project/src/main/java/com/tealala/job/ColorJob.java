package com.tealala.config.job;

import org.quartz.*;

import java.util.Date;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/4/26
 *
 * @DisallowConcurrentExecution 表示下一个任务必须在上一个任务执行完之后才能执行
 *
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ColorJob implements Job {
    public static final String FAVORITE_COLOR = "FAVORITE_COLOR";
    public static final String EXECUTION_COUNT = "EXECUTION_COUNT";

    private int _counter = 1;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap data = context.getJobDetail().getJobDataMap();
        String favoriteColor = data.getString(FAVORITE_COLOR);
        int count = data.getInt(EXECUTION_COUNT);

        String message = "ColorJob: " + context.getJobDetail().getKey() + "\n"
            + " executing at " + new Date() + "\n"
            + "  favorite color is " + favoriteColor + "\n" + "  execution count (from com.tealala.job map) is " + count + "\n"
            + "  execution count (from com.tealala.job member variable) is " + _counter;
        System.out.println(message);
        ;
    }
}
