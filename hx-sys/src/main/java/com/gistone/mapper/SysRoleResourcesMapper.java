package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.SysResources;
import com.gistone.entity.SysRoleResources;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xxh
 * @since 2019-01-22
 */
public interface SysRoleResourcesMapper extends BaseMapper<SysRoleResources> {
    List<SysResources> getResourcesByRoleId(@Param("roleIds") List<Integer> roleIds);

    void saveRoleResources(@Param("roleId") Integer roleId, @Param("resourcesSet") List<Integer> resourcesSet);


}
