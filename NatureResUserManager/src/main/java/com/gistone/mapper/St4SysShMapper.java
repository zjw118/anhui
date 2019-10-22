package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gistone.entity.St4SysSh;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 系统日志表 Mapper 接口
 * </p>
 *
 * @author xxh
 * @since 2019-08-12
 */
public interface St4SysShMapper extends BaseMapper<St4SysSh> {

   IPage<St4SysSh> listLog (Page<St4SysSh> page, @Param("sh") St4SysSh sh);

}
