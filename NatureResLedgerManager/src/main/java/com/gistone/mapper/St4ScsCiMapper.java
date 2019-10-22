package com.gistone.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gistone.entity.St4ScsCi;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * app预警表 Mapper 接口
 * </p>
 *
 * @author zjw
 * @since 2019-08-17
 */
public interface St4ScsCiMapper extends BaseMapper<St4ScsCi> {
    List<St4ScsCi> getWarnDetail (St4ScsCi ci);

    IPage<St4ScsCi> listAppWarn(Page<St4ScsCi> page,@Param("ci") St4ScsCi ci);
}
