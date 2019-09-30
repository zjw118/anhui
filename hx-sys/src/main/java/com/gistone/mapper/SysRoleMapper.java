package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.SysResources;
import com.gistone.entity.SysRole;
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
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> getRoleListByUserId(@Param("userId") Integer userId);

    List<SysRole> selectRoleList(@Param("startNum") Integer startNum,@Param("pageSize") Integer pagesize,@Param("name") String name);

     Set<SysResources> getResourceByRoleId(@Param("roleId") Integer roleId);

     Integer selectRoleListCount(@Param("name") String name);
}
