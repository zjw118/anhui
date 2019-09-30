package com.gistone.service;

import com.gistone.entity.SysRole;
import com.gistone.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xxh
 * @since 2019-01-22
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

    List<SysRole> selectRoleByUserId(Integer userId);

}
