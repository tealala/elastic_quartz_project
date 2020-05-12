package jdkTimer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author tealala@digidite.com
 * @date 2020/4/26
 */
public class JdkTimerTest {
    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask timerTask = new JdkTimerTask();
        timer.schedule(timerTask,5000L, 1000L);
    }
}
