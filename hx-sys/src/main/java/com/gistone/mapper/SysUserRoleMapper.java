package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.SysRole;
import com.gistone.entity.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xxh
 * @since 2019-01-22
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    List<SysRole> getRoleByUserId(@Param("userId") Integer userId);
    void saveUserRole(@Param("userId") Integer userId, @Param("roleIds") Set<Integer> roleIds);

}
