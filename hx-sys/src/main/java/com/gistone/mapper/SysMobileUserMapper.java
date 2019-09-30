package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.SysMobileUser;
import com.gistone.entity.SysRole;
import com.gistone.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xjc
 * @since 2019-03-05
 */
public interface SysMobileUserMapper extends BaseMapper<SysMobileUser> {

    List<SysMobileUser> findMobileUserList(Map<String,Object> params);

    SysUser getSysUser(Integer userId);

    int updateUser(@Param("id") Integer id,@Param("enable") Integer enable);

    SysMobileUser getSysMobileUserById(Integer id);

    SysMobileUser getSysMobileUserByPhoneNumber(String phoneNumber);

    int getTotal (@Param("startNum") Integer startNum,@Param("pageSize") Integer pageSize,@Param("realName") String realName,@Param("phoneNumber") String phoneNumber,@Param("code") String code,@Param("department") String department);

    Set<SysRole> selectRolesById(@Param("userId") Integer userId);

    int getLevel(String code);
}
