package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.St4SysSa;
import com.gistone.util.Result;
import com.gistone.util.ResultCp;

import java.util.List;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author xxh
 * @since 2019-08-12
 */
public interface ISt4SysSaService extends IService<St4SysSa> {

    /**
     * @explain : 登陆接口
     * @author xxh
     * @date 2019/7/16
     * @param username 用户名
     * @param password 密码
     * @return com.gistone.util.Ret
     */
    Result searchSysUserByLogin(String username, String password);

    Result listPhoneUserToView(St4SysSa data);

    ResultCp updateAppUser(St4SysSa data) throws Exception;
    Result add(St4SysSa user, St4SysSa seUser, List<Integer> roleList, List<Integer> unitList);

    /**
     * 用户列表无分页
     * @param sa
     * @return
     */
    Result list(St4SysSa sa,St4SysSa seUser);

    /**
     * 用户列表有分页
     * @param sa
     * @return
     */
    Result listUser(St4SysSa sa,St4SysSa seUser);

    /**
     * 用户详情接口
     * @param sa
     * @return
     */
    Result getUserDetail(St4SysSa sa);

    /**
     * 修改用户
     * @param user
     * @param seUser
     * @param roleList
     * @return
     */
    Result updateUser(St4SysSa user, St4SysSa seUser, List<Integer> roleList, List<Integer> unitList);

    /**
     * 删除用户
     * @param sa
     * @return
     */
     Result deleteUser(St4SysSa sa,St4SysSa seUser);



}
