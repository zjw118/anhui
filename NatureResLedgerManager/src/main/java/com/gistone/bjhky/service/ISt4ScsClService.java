package com.gistone.bjhky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.bjhky.entity.St4ScsCl;
import com.gistone.bjhky.entity.St4SysSa;
import com.gistone.bjhky.util.Result;

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
    Result listTask(St4ScsCl data, St4SysSa seUser);
}
