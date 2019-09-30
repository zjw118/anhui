package com.gistone.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.SysRole;
import com.gistone.entity.SysUserRole;
import com.gistone.mapper.SysUserRoleMapper;
import com.gistone.service.ISysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xxh
 * @since 2019-01-22
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Override
    public List<SysRole> selectRoleByUserId(Integer userId) {
        List<SysRole> sysRoleList = sysUserRoleMapper.getRoleByUserId(userId);
        return sysRoleList;
    }
}
