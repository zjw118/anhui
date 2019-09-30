package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.SysRole;
import com.gistone.entity.SysUser;

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
public interface ISysUserService extends IService<SysUser> {
    List<SysRole> selectRoleOfUser(Integer userId);

    Map<String,Object> getList(String name,Integer pageNum,Integer pageSize);


}
