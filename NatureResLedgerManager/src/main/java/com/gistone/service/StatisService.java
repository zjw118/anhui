package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.VO.ResultVO;
import com.gistone.entity.St4ScsCc;
import com.gistone.entity.St4ScsCd;
import com.gistone.entity.St4ScsCk;
import com.gistone.entity.St4ScsCy;
import com.gistone.util.Result;

/**
 *
 * 统计
 * @author wzy
 * @since 2019-08-14
 */
public interface StatisService extends IService<St4ScsCy> {


    /**
     * 航点-分页
     * @param St4ScsCc
     * @return
     * @throws Exception
     */
    //Result listWaypointFY(St4ScsCc St4ScsCc)throws Exception;

    /**
     * 航点-统计列表
     * @param St4ScsCc
     * @return
     * @throws Exception
     */
   // Result listWaypoint(St4ScsCc St4ScsCc)throws Exception;

    /**
     * 巡查-查询分页
     * @param st4ScsCy
     * @return
     */
    //Result listPatrol(St4ScsCy st4ScsCy)throws Exception;
    /**
     * 巡查-人员外键条件分页
     * @param st4ScsCy
     * @return
     */
    //Result listPatrolBySA001(St4ScsCy st4ScsCy)throws Exception;

    /**
     * 台账记录-条件列表
     * @param St4ScsCk
     * @return
     * @throws Exception
     */
    ResultVO listLedger(St4ScsCk St4ScsCk)throws Exception;

   /* *//**
     * 台账-分页
     * @param St4ScsCk
     * @return
     * @throws Exception
     *//*
    Result listLedgerFY(St4ScsCk St4ScsCk)throws Exception;*/
    /**
     * 台账-条件列表
     * @param St4ScsCd
     * @return
     * @throws Exception
     */
    Result listLedgerzg(St4ScsCd St4ScsCd)throws Exception;

    /**
     * 动物统计分析
     * @param data
     * @return
     */
    Result statisDw(St4ScsCc data);

    /**
     * 植物统计分析
     * @param data
     * @return
     */
    Result statisZw(St4ScsCc data);
}
