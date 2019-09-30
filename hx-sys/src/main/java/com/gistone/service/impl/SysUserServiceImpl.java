package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.SysRole;
import com.gistone.entity.SysUser;
import com.gistone.mapper.SysUserMapper;
import com.gistone.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xxh
 * @since 2019-01-22
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysRole> selectRoleOfUser(Integer userId) {
        sysUserMapper.getRoleOfUser(userId);
        return null;
    }

    @Override
    public Map<String, Object> getList(String name, Integer pageNum, Integer pageSize) {
        QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper<>();
        sysUserQueryWrapper.eq("type", 0);
        sysUserQueryWrapper.eq("enable", 1);
        IPage<SysUser> sysUser = sysUserMapper.selectPage(new Page<>(pageNum, pageSize), sysUserQueryWrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", sysUser.getRecords());
        result.put("total", sysUser.getTotal());

        return result;
    }
}
