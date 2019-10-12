package com.gistone.bjhky.mapper;

import com.gistone.bjhky.entity.St4ScsCac;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 环科院专用标识字段更新时间表 Mapper 接口
 * </p>
 *
 * @author zhaojingwei
 * @since 2019-09-26
 */
public interface St4ScsCacMapper extends BaseMapper<St4ScsCac> {
    int updateByCk001(St4ScsCac cac);

}
