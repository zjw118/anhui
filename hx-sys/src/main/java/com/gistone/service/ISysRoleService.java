package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.SysRole;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xxh
 * @since 2019-01-22
 */
public interface ISysRoleService extends IService<SysRole> {

    List<SysRole> findRoleListByUserId(Integer userId);

    SysRole selectByName(String name);

    Map<String,Object> getRoleList(String name, Integer pageNum, Integer pageSize);

    void deleteRole(SysRole sysRole);

    void updateRoles(SysRole sysRole);

    void saveRoleResource(Integer roleId, List<Integer> respurces);

    void saveRole(SysRole sysRole);


}