package com.tealala.demo.config;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/4/27
 */
@Configuration
public class SchedulerConfig implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private MyJobFactory jobFactory;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JobDetail myJobDetail;

    @Autowired
    private Trigger myTrigger;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("任务已经启动了：" + contextRefreshedEvent.getSource());
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        //获取配置属性
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        //在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();

        //创建SchedulerFactoryBean
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
//        factory.setQuartzProperties(propertiesFactoryBean.getObject());
        factory.setDataSource(dataSource);
        factory.setJobFactory(jobFactory);
        //当Spring关闭的时候，会等待启动的job结束之后，spring 才能完全的shutdown
        factory.setWaitForJobsToCompleteOnShutdown(true);
        factory.setOverwriteExistingJobs(false);
        factory.setStartupDelay(1);
        return factory;
    }

    @Bean
    public Scheduler scheduler() throws IOException {
//        schedulerFactoryBean().
        return schedulerFactoryBean().getScheduler();
    }

    @Bean
    public QuartzInitializerListener executorListener() {
        return new QuartzInitializerListener();
    }
}
