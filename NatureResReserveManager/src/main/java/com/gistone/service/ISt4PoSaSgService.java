package com.gistone.service;

import com.gistone.entity.St4PoSaSg;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.util.Result;

import java.util.List;

/**
 * <p>
 * 巡查人员授权保护地数据关联表 服务类
 * </p>
 *
 * @author zjw
 * @since 2019-08-30
 */
public interface ISt4PoSaSgService extends IService<St4PoSaSg> {
    /**
     * 授权保护地边界
     * @param uids
     * @param reserveList
     * @return
     */
    Result giveReserve(Integer uids, List<Integer> reserveList);
}
