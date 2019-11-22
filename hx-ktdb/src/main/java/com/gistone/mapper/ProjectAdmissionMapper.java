package com.gistone.mapper;

import com.gistone.entity.ProjectAdmission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-05-05
 */
public interface ProjectAdmissionMapper extends BaseMapper<ProjectAdmission> {

    ProjectAdmission get(@Param(value="id")Integer id);



}
