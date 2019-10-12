package com.gistone.bjhky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.bjhky.entity.St4ScsCaa;
import com.gistone.bjhky.util.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiuXiong
 * @since 2019-08-27
 */
public interface ISt4ScsCaaService extends IService<St4ScsCaa> {

    Result getNewVersion(St4ScsCaa data);
}
