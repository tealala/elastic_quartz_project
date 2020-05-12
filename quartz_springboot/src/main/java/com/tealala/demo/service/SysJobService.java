package com.tealala.demo.service;

import com.tealala.demo.entity.SysJob;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/4/27
 */
public interface SysJobService{

    void insertJob(@Param("sysJob") SysJob sysJob);

    List<SysJob> findAll();

    List<SysJob> querySysJobList(SysJob sysJob);
}
