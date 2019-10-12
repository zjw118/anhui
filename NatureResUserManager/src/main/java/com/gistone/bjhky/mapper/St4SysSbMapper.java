package com.gistone.bjhky.mapper;

import com.gistone.bjhky.entity.St4SysSb;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjw
 * @since 2019-08-28
 */
public interface St4SysSbMapper extends BaseMapper<St4SysSb> {
    St4SysSb getRoleDetail(St4SysSb sb);
}
