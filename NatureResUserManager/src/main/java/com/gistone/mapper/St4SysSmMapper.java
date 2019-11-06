package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.St4SysSm;

import java.util.List;

/**
 * <p>
 * 保护地类型表 Mapper 接口
 * </p>
 *
 * @author zjw
 * @since 2019-11-04
 */
public interface St4SysSmMapper extends BaseMapper<St4SysSm> {
    List<St4SysSm> listTreeSt4SysSg(St4SysSm data);
}
