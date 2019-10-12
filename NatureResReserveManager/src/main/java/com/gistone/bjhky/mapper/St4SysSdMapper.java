package com.gistone.bjhky.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.bjhky.entity.St4SysSd;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xxh
 * @since 2019-08-14
 */
public interface St4SysSdMapper extends BaseMapper<St4SysSd> {

    List<St4SysSd> listTreeToView();
}
