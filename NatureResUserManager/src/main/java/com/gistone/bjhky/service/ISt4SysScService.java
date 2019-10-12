package com.gistone.bjhky.service;

import com.gistone.bjhky.entity.St4SysSc;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.bjhky.util.Result;

/**
 * <p>
 * 模块权限表 服务类
 * </p>
 *
 * @author zjw
 * @since 2019-08-28
 */
public interface ISt4SysScService extends IService<St4SysSc> {
    /**
     * 模块权限列表
     * @param sc
     * @return
     */
    Result listModule(St4SysSc sc);
    /**
     * 模块权限列表(当前用户所有的权限)
     * @param sa001
     * @return
     */
    Result listModuleBySa001(Integer sa001);
}
