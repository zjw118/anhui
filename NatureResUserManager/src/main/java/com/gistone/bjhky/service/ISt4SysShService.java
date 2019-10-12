package com.gistone.bjhky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.bjhky.entity.St4SysSh;
import com.gistone.bjhky.util.Result;

/**
 * <p>
 * 系统日志表 服务类
 * </p>
 *
 * @author xxh
 * @since 2019-08-12
 */
public interface ISt4SysShService extends IService<St4SysSh> {
    /**
     * 日志列表
     * @param sh
     * @return
     */
    Result listLog(St4SysSh  sh);

}
