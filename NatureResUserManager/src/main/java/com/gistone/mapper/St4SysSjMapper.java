package com.gistone.mapper;

import com.gistone.entity.St4SysSj;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjw
 * @since 2019-08-28
 */
public interface St4SysSjMapper extends BaseMapper<St4SysSj> {
    St4SysSj getUnitDetail(St4SysSj sj);

    List<Map<Object,Object>> getUnitResData(St4SysSj sj);
}
