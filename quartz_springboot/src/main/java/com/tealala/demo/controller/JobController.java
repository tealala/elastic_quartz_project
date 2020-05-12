package com.tealala.demo.controller;

import com.tealala.demo.entity.SysJob;
import com.tealala.demo.service.SysJobService;
import com.tealala.demo.util.JobUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/4/27
 */

@RestController
@RequestMapping(value = "/com.tealala.config.job", produces = "application/json;charset=UTF-8")
public class JobController {

    @Autowired
    private JobUtil jobUtil;

    @Autowired
    private SysJobService sysJobService;

    @RequestMapping("/add")
    public String addJob(@RequestBody SysJob sysJob) throws Exception {
        sysJobService.insertJob(sysJob);
        jobUtil.addJob(sysJob);
        return "success";
    }

    /**
     * 暂停某个job
     *
     * @param sysJob
     * @return
     * @throws Exception
     */
    @RequestMapping("/pause")
    public String shutDown(@RequestBody SysJob sysJob) throws Exception {
        jobUtil.pauseJob(sysJob.getJobName(), sysJob.getJobGroup());
        return "success";
    }

    /**
     * 暂停所有job
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/pauseall")
    public String shutDownAll() throws Exception {
        List<SysJob> result = sysJobService.findAll();
        jobUtil.pauseAllJob(result);
        return "success";
    }

    /**
     * 恢复某个job
     *
     * @param sysJob
     * @return
     * @throws Exception
     */
    @RequestMapping("/resume")
    public String resumeJob(@RequestBody SysJob sysJob) throws Exception {
        jobUtil.resumeJob(sysJob.getJobName(), sysJob.getJobGroup());
        return "success";
    }

    /**
     * 恢复某个job
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/resumeall")
    public String resumeJobAll() throws Exception {
        List<SysJob> result = sysJobService.findAll();
        jobUtil.resumeJob(result);
        return "success";
    }

}
