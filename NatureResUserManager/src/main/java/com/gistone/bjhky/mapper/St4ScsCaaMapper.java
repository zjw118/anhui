package com.gistone.bjhky.mapper;

import com.gistone.bjhky.entity.St4ScsCaa;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LiuXiong
 * @since 2019-08-27
 */
public interface St4ScsCaaMapper extends BaseMapper<St4ScsCaa> {

    St4ScsCaa getNewVersion(St4ScsCaa data);
}
