package com.gistone.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gistone.entity.St4ScsCbb;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhaojingwei
 * @since 2019-11-16
 */
public interface St4ScsCbbMapper extends BaseMapper<St4ScsCbb> {
    IPage<St4ScsCbb> listReserveData(Page<St4ScsCbb> page, @Param("cbb") St4ScsCbb cba);
}
