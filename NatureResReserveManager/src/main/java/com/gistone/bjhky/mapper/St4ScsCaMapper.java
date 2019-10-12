package com.gistone.bjhky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.bjhky.entity.St4ScsCa;

import java.util.List;

/**
 * <p>
 * 保护地类型表 Mapper 接口
 * </p>
 *
 * @author LiuXiong
 * @since 2019-08-15
 */
public interface St4ScsCaMapper extends BaseMapper<St4ScsCa> {

    List<St4ScsCa> listTreeSt4SysSg(St4ScsCa data);
}
