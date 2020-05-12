package com.tealala.demo.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/4/27
 */
@Data
@Table(name = "sys_job")
public class SysJob implements Serializable {
    /**
     * ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 任务名称
     */
    @Column(name = "job_name")
    private String jobName;

    /**
     * 任务组名
     */
    @Column(name = "job_group")
    private String jobGroup;

    /**
     * 时间表达式
     */
    @Column(name = "job_cron")
    private String jobCron;

    /**
     * 类路径,全类型
     */
    @Column(name = "job_class_path")
    private String jobClassPath;

    /**
     * 传递map参数
     */
    @Column(name = "job_data_map")
    private String jobDataMap;

    /**
     * 状态:1启用 0停用
     */
    @Column(name = "job_status")
    private Integer jobStatus;

    /**
     * 任务功能描述
     */
    @Column(name = "job_describe")
    private String jobDescribe;

    private static final long serialVersionUID = 1L;
}