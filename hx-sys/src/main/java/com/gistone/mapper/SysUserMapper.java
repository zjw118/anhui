package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.SysRole;
import com.gistone.entity.SysUser;
import com.gistone.util.PageBean;


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
   SysRole getRoleOfUser(Integer userId);
   // 	分页查询-获取总数量
   int getPoSum(PageBean pageBean);
   //	分页查询-获取分页数据
   List<Object> selectPoList(PageBean pageBean);

}
