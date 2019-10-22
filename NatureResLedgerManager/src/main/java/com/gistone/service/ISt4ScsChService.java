package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.St4ScsCh;
import com.gistone.util.Result;


/**
 * <p>
 * 巡查人员实时位置表 服务类
 * </p>
 *
 * @author zjw
 * @since 2019-08-22
 */
public interface ISt4ScsChService extends IService<St4ScsCh> {
    /**
     * 得到离线在线和正在巡护中的人数
     * @return
     */
    Result getPersonNumber();

}
