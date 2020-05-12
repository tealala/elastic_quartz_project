package com.tealala.demo.service.impl;

import com.tealala.demo.entity.SysJob;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.tealala.demo.mapper.SysJobMapper;
import com.tealala.demo.service.SysJobService;

import java.util.List;
import java.util.Map;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/4/27
 */
@Service
public class SysJobServiceImpl implements SysJobService {

    @Resource
    private SysJobMapper sysJobMapper;

    @Override
    public void insertJob(SysJob sysJob) {
        sysJobMapper.insertJob(sysJob);
    }

    @Override
    public List<SysJob> findAll() {
        return sysJobMapper.findAll();
    }

    @Override
    public List<SysJob> querySysJobList(SysJob sysJob) {
        return sysJobMapper.querySysJobList(sysJob);
    }
}
