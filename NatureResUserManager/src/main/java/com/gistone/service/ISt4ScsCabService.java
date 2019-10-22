package com.gistone.service;

import com.gistone.entity.St4ScsCab;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.util.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjw
 * @since 2019-09-02
 */
public interface ISt4ScsCabService extends IService<St4ScsCab> {

    Result listForApp(St4ScsCab data);

    Result getByIdForApp(St4ScsCab data);
}
