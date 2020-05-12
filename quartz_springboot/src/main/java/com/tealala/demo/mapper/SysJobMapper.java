package com.tealala.demo.mapper;

import com.tealala.demo.entity.SysJob;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/4/27
 */

public interface SysJobMapper extends Mapper<SysJob> {

    void insertJob(@Param("sysJob") SysJob sysJob);

    List<SysJob> findAll();

    List<SysJob> querySysJobList(@Param("sysJob") SysJob sysJob);
}