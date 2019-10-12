package com.gistone.bjhky.service;

import com.gistone.bjhky.entity.St4ScsCi;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.bjhky.entity.St4SysSa;
import com.gistone.bjhky.util.Result;

import java.util.List;

/**
 * <p>
 * app预警表 服务类
 * </p>
 *
 * @author zjw
 * @since 2019-08-17
 */
public interface ISt4ScsCiService extends IService<St4ScsCi> {
    /**
     * 预警列表
     * @param ci
     * @return
     */
    Result listAppWarn (St4ScsCi ci, St4SysSa seUser);

    /**
     * 预警详情
     * @param ci
     * @return
     */
    Result getWarnDetail (St4ScsCi ci);

}
