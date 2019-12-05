package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.VO.ResultVO;
import com.gistone.entity.*;
import com.gistone.swagger.StaticSwagger;
import com.gistone.util.Result;

import javax.servlet.http.HttpServletResponse;

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
     * 航迹数据统计接口
     * @param st4ScsCy
     * @return
     */
    ResultVO listPatrol(St4ScsCy st4ScsCy);

    /**
     * 按人员导出航迹数据接口
     * @param cy
     * @return
     */
    ResultVO exportRecordStatic(St4ScsCy cy);
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

    ResultVO pointStatistics(StaticSwagger ss);

    /**
     * cl
     * 导出质量控制侧重审核
     * @return
     */
    ResultVO examineQualityExport(St4ScsCl cl, HttpServletResponse response);

    /**
     * 核查质量评估侧重核查情况
     * @param cl
     * @param response
     * @return
     */
    ResultVO pointQualityExport(RlhdGroup cl, HttpServletResponse response);

    /**
     * 生成生态保护红线核查监管报告
     * @param sc
     * @param response
     * @return
     */
    ResultVO redLineReportExport(SysCompany sc, HttpServletResponse response);

    /**
     * 生态保护红线核查监管报告列表
     * @param sc
     * @param response
     * @return
     */
    ResultVO redLineReport(SysCompany sc, HttpServletResponse response);


}
