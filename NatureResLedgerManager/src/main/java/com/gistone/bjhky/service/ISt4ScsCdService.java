package com.gistone.bjhky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.bjhky.entity.St4ScsCd;
import com.gistone.bjhky.entity.St4SysSa;
import com.gistone.bjhky.util.Result;

/**
 * <p>
 * 问题点表 服务类
 * </p>
 *
 * @author zhaojingwei
 * @since 2019-08-13
 */
public interface ISt4ScsCdService extends IService<St4ScsCd> {

    Result listCheckPointToView(St4ScsCd data);

    Result listCheckPoint(St4ScsCd data, St4SysSa seUser);

    Result insertCheckPoint(St4ScsCd cd);
    /**
     * 同步问题点数据带有台账(绿盾)
     * @param roleId
     * @param uid
     * @return
     */
    Result sysPointDataLd (Integer roleId,Integer uid);

}
