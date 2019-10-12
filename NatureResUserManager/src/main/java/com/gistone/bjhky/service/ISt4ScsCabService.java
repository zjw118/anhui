package com.gistone.bjhky.service;

import com.gistone.bjhky.entity.St4ScsCab;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.bjhky.util.Result;

import java.util.List;

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
