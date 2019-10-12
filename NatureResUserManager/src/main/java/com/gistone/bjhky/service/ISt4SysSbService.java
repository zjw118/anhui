package com.gistone.bjhky.service;

import com.gistone.bjhky.entity.St4SysSa;
import com.gistone.bjhky.entity.St4SysSb;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.bjhky.util.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjw
 * @since 2019-08-28
 */
public interface ISt4SysSbService extends IService<St4SysSb> {
    /**
     * 角色列表分页查询
     * @param sb
     * @return
     */
    Result listRole(St4SysSb sb, HttpServletRequest request);

    /**
     * 角色列表下拉框
     * @param sb
     * @return
     */
    Result listRoleNoLimit(St4SysSb sb, HttpServletRequest request);
    /**
     * 添加角色及权限信息
     */
    Result insertRole(St4SysSb sb,St4SysSa seUser);

    /**
     * 修改角色及权限信息
     * @param sb
     * @return
     */
    Result updateRole(St4SysSb sb, St4SysSa seUser);

    /**
     * 角色详情
     * @param sb
     * @return
     */
    Result getRoleDetail(St4SysSb sb);
}
