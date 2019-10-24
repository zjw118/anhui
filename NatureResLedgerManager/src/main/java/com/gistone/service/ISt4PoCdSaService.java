package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.St4PoCdSa;
import com.gistone.util.Result;
import com.gistone.util.ResultCp;

import java.util.List;

/**
 * <p>
 * 核查点分配记录表 服务类
 * </p>
 *
 * @author zjw
 * @since 2019-08-15
 */
public interface ISt4PoCdSaService extends IService<St4PoCdSa> {
    /**
     * 下发任务
     * @param uids
     * @param taskId
     * @return
     */
    ResultCp givePoint(List<Integer> uids,Integer taskId);

    /**
     * 任务绑定台账
     * @param ledgerIdList
     * @param taskId
     * @return
     */
    ResultCp taskLedger(List<Integer> ledgerIdList,Integer taskId);




}
