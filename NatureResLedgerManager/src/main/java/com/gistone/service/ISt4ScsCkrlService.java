package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.St4ScsCkrl;
import com.gistone.util.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjw
 * @since 2019-11-07
 */
public interface ISt4ScsCkrlService extends IService<St4ScsCkrl> {
    Result listHumanStage(St4ScsCkrl ckrl);

}
