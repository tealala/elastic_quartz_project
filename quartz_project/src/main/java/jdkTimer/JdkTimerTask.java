package jdkTimer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/4/26
 */
public class JdkTimerTask extends TimerTask {
    @Override
    public void run() {
        Date time = new Date(this.scheduledExecutionTime());
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS").format(time);
        System.out.println("使用jdktimerTask执行任务调度:"+date);
    }
}
