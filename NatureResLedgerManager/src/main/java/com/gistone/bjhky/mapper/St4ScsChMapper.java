package com.gistone.bjhky.mapper;

import com.gistone.bjhky.entity.St4ScsCh;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 巡查人员实时位置表 Mapper 接口
 * </p>
 *
 * @author zjw
 * @since 2019-08-22
 */
public interface St4ScsChMapper extends BaseMapper<St4ScsCh> {
        List<Map<String,String>> getPersonNumber();
}
