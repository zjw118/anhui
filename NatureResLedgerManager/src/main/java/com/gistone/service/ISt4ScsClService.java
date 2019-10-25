package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.St4ScsCl;
import com.gistone.entity.St4SysSa;
import com.gistone.entity.SysUser;
import com.gistone.util.Result;
import com.gistone.util.ResultCp;

/**
 * <p>
 * 任务批次表 服务类
 * </p>
 *
 * @author LiuXiong
 * @since 2019-08-14
 */
public interface ISt4ScsClService extends IService<St4ScsCl> {

    Result listForView(St4ScsCl data);
    ResultCp listTask(St4ScsCl data, SysUser seUser);

    /**
     * 插入任务
     * @param data
     * @param seUser
     * @return
     */
    ResultCp insertTask(St4ScsCl data, SysUser seUser);
    /**
     * 修改任务
     * @param data
     * @param seUser
     * @return
     */
    ResultCp updateTask(St4ScsCl data, SysUser seUser);
    /**
     * 删除任务
     * @param data
     * @param seUser
     * @return
     */
    ResultCp deleteTask(St4ScsCl data, SysUser seUser);

    /**
     * 任务详情
     * @param data
     * @return
     */
    ResultCp getTaskDetail(St4ScsCl data);
}
