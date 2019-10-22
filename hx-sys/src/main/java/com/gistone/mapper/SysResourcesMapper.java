package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.SysResources;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xxh
 * @since 2019-01-22
 */
public interface SysResourcesMapper extends BaseMapper<SysResources> {
    Integer getMaxSort();
    Integer selectMaxSort(@Param("id") Integer id);

}
