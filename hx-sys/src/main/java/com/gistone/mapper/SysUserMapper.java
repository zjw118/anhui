package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.SysRole;
import com.gistone.entity.SysUser;


import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xxh
 * @since 2019-01-22
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
   List<SysRole> getRoleOfUser(Integer userId);



}
