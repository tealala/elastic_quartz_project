package listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/4/27
 */
public class MyJobListener implements JobListener {
    @Override
    public String getName() {
        System.out.println("获取监听器名称：" + getClass().getSimpleName());
        return getClass().getSimpleName();
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
        String jobName = jobExecutionContext.getJobDetail().getKey().getName();
        System.out.println("执行的job名称为：" + jobName + "--------任务即将执行");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
        String jobName = jobExecutionContext.getJobDetail().getKey().getName();
        System.out.println("执行的job名称为：" + jobName + "--------任务被否决");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        String jobName = jobExecutionContext.getJobDetail().getKey().getName();
        System.out.println("执行的job名称为：" + jobName + "--------任务执行完毕");
    }
}
