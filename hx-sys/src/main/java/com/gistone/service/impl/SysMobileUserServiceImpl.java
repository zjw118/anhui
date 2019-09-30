package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.VO.ResultVO;
import com.gistone.entity.SysMobileUser;
import com.gistone.entity.SysRole;
import com.gistone.entity.SysUser;
import com.gistone.entity.SysUserRole;
import com.gistone.mapper.SysMobileUserMapper;
import com.gistone.mapper.SysUserRoleMapper;
import com.gistone.service.ISysMobileUserService;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 服务实现类-
 * </p>
 *
 * @author xjc
 * @since 2019-03-05
 */
@Service
@Transactional
public class SysMobileUserServiceImpl extends ServiceImpl<SysMobileUserMapper, SysMobileUser> implements ISysMobileUserService {
    @Autowired
    private SysMobileUserMapper sysMobileUserMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;


    @Override
    public List<SysMobileUser> selectMobileUserList(Integer pageNum, Integer pageSize, String realName, String phoneNumber, String code, String departmnet) {
        //准备参数
        Map<String, Object> params = new HashMap();
        params.put("page", (pageNum - 1)*pageSize);
        params.put("limit", pageSize);
        params.put("realName", realName);
        params.put("phoneNumber", phoneNumber);
        params.put("code", code);
        params.put("department", departmnet);
        List<SysMobileUser> sysMobileUserList = sysMobileUserMapper.findMobileUserList(params);
        return sysMobileUserList;
    }

    @Override
    public ResultVO deleteUser(Integer userId) {
        //根据用户userId查询用户
        SysUser sysUser = sysMobileUserMapper.getSysUser(userId);
        if (sysUser == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"用户不存在");
        }
        sysUser.setEnable(0);
        int result = sysMobileUserMapper.updateUser(sysUser.getId(), sysUser.getEnable());
        if (result > 0) {
            return ResultVOUtil.success();
        }
        return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "删除失败");
    }

    @Override
    public SysMobileUser selectMobileUserById(Integer id) {
        SysMobileUser sysMobileUser = sysMobileUserMapper.getSysMobileUserById(id);
        return sysMobileUser;
    }

    @Override
    public SysMobileUser selectMobileUserByPhoneNumber(String phoneNumber) {
        SysMobileUser sysMobileUser = sysMobileUserMapper.getSysMobileUserByPhoneNumber(phoneNumber);
        return sysMobileUser;
    }

    @Override
    public boolean editUser(SysMobileUser user) {

        return false;
    }

    @Override
    public int selectTotal(Integer pageNum,Integer pageSize,String realName, String phoneNumber, String code, String department) {

        int total = sysMobileUserMapper.getTotal((pageNum - 1)*pageSize,pageSize,realName,phoneNumber,code,department);
        return total;
    }

    @Override
    public void updateUser(SysMobileUser user) {
        sysMobileUserMapper.updateById(user);
        //删除所有用户角色列表，重新插入
        sysUserRoleMapper.delete(new QueryWrapper<SysUserRole>().eq("user_id",user.getUserId()));
//        sysUserRoleMapper.saveUserRole(user.getUserId(),user.getRoleIds());
    }

    @Override
    public Set<SysRole> getRoles(Integer userId) {
        Set<SysRole> sysRoles = sysMobileUserMapper.selectRolesById(userId);
        return sysRoles;
    }

    @Override
    public Integer getLevelByCode(String code) {
        Integer leve = sysMobileUserMapper.getLevel(code);
        return leve;
    }
}
