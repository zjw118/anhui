package com.gistone.bjhky.mapper;

import com.gistone.bjhky.entity.St4SysSa;
import com.gistone.bjhky.entity.St4SysSj;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.bjhky.util.Result;

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
