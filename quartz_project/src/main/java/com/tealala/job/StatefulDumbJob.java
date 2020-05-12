package com.tealala.config.job;

import org.quartz.*;

import java.util.Date;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/4/27
 */

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class StatefulDumbJob implements Job {

    public static final String NUM_EXECUTIONS = "NumExecutions";

    public static final String EXECUTION_DELAY = "ExecutionDelay";

    public StatefulDumbJob() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {

        System.out.println(context.getJobDetail().getKey() + ": executing.[" + new Date() + "]");

        JobDataMap map = context.getJobDetail().getJobDataMap();

        int executeCount = 0;
        if (map.containsKey(NUM_EXECUTIONS)) {
            executeCount = map.getInt(NUM_EXECUTIONS);
        }

        map.put(NUM_EXECUTIONS, executeCount);

        long delay = 50001;
        if (map.containsKey(EXECUTION_DELAY)) {
            delay = map.getLong(EXECUTION_DELAY);
        }

        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(context.getJobDetail().getKey() + " complete (" + executeCount + ")");

    }
}
