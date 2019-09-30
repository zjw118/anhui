package com.gistone.service;

import com.gistone.VO.ResultVO;
import com.gistone.entity.SysMobileUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.SysRole;


import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjc
 * @since 2019-03-05
 */
public interface ISysMobileUserService extends IService<SysMobileUser> {
    List<SysMobileUser> selectMobileUserList(Integer page,Integer limit,String realName,String phoneNumber,String code,String department);
    ResultVO deleteUser(Integer userId);

    SysMobileUser selectMobileUserById(Integer id);

    SysMobileUser selectMobileUserByPhoneNumber(String phoneNumber);

    boolean editUser(SysMobileUser user);

    int selectTotal(Integer page, Integer limit, String realName, String phoneNumber, String code, String department);

    void updateUser(SysMobileUser user);

    Set<SysRole> getRoles(Integer userId);

    Integer getLevelByCode(String code);

}
