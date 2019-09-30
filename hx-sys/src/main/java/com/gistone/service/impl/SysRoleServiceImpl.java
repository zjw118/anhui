package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.SysRole;
import com.gistone.entity.SysRoleResources;
import com.gistone.entity.SysUser;
import com.gistone.mapper.SysRoleMapper;
import com.gistone.mapper.SysRoleResourcesMapper;
import com.gistone.mapper.SysUserMapper;
import com.gistone.service.ISysRoleService;
import org.apache.commons.lang3.StringUtils;
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
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleResourcesMapper sysRoleResourcesMapper;

    @Override
    public List<SysRole> findRoleListByUserId(Integer userId) {
        List<SysRole> sysRoleList = sysRoleMapper.getRoleListByUserId(userId);

        return sysRoleList;
    }

    @Override
    public SysRole selectByName(String name) {
        SysRole sysRole = sysRoleMapper.selectOne(new QueryWrapper<SysRole>().eq("name", name).eq("del_flag", 1));
        return sysRole;
    }

    @Override
    public Map<String, Object> getRoleList(String name, Integer pageNum, Integer pageSize) {

        QueryWrapper<SysRole> sysRoleQueryWrapper = new QueryWrapper<>();
        sysRoleQueryWrapper.eq("del_flag", 1);
        if (StringUtils.isNotBlank(name)) {
            sysRoleQueryWrapper.like("name", name);
        }
        /*IPage<SysRole> roleIPage = sysRoleMapper.selectPage(new Page<>(pageNum, pageSize), sysRoleQueryWrapper);
        List<SysRole> roleList = setUserNameToRole(roleIPage.getRecords());*/

        List<SysRole> roleList1 = sysRoleMapper.selectRoleList((pageNum-1)*pageSize,pageSize,name);
        Integer total = sysRoleMapper.selectRoleListCount(name);
        if (roleList1 != null && roleList1.size() > 0) {
            for (SysRole sysRole : roleList1) {
                Integer id = sysRole.getId();
                //通过角色id获取所拥有的权限
                sysRole.setResourcesSet(sysRoleMapper.getResourceByRoleId(id));
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("rows", roleList1);
        result.put("total", total);

        return result;
    }

    @Override
    public void deleteRole(SysRole sysRole) {
        sysRole.setDelFlag(0);
        sysRoleMapper.updateById(sysRole);
    }

    @Override
    public void updateRoles(SysRole sysRole) {
        //更新role
        sysRoleMapper.updateById(sysRole);
        //更新关联表
        if( sysRole.getResourceIds()!=null&&sysRole.getResourceIds().size()>0){
            saveRoleResource(sysRole.getId(), sysRole.getResourceIds());
        }


    }

    @Override
    public void saveRoleResource(Integer roleId, List<Integer> resources) {
        //删除角色资源所有关系
        sysRoleResourcesMapper.delete(new QueryWrapper<SysRoleResources>().eq("role_id", roleId));
        //建立角色资源所有的关系
        sysRoleResourcesMapper.saveRoleResources(roleId, resources);
    }

    @Override
    public void saveRole(SysRole sysRole) {
        sysRoleMapper.insert(sysRole);
        if(sysRole.getResourceIds()!=null&&sysRole.getResourceIds().size()>0){
            saveRoleResource(sysRole.getId(), sysRole.getResourceIds());
        }

    }

    private List<SysRole> setUserNameToRole(List<SysRole> roles) {
        for (SysRole r : roles) {
            if (r.getCreateBy() != null && r.getCreateBy() != 0) {
                SysUser u = sysUserMapper.selectById(r.getCreateBy());

                r.setCreateUser(u.getUsername());
            }
            if (r.getUpdateBy() != null && r.getUpdateBy() != 0) {
                SysUser u = sysUserMapper.selectById(r.getUpdateBy());

                r.setUpdateUser(u.getUsername());
            }
        }
        return roles;
    }
}