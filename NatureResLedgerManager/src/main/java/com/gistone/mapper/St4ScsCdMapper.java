package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.*;
import com.gistone.entity.excel.ReportVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 问题点表 Mapper 接口
 * </p>
 *
 * @author zhaojingwei
 * @since 2019-08-13
 */
public interface St4ScsCdMapper extends BaseMapper<St4ScsCd> {

    void insertList(List<St4ScsCd> list);

    int insertMap(Map map);

    List<St4ScsCd> batchSelectByCode(List<String> list);

    List<St4ScsCd> listCheckPointToView(St4ScsCd data);

    List<St4ScsCd>  listCheckPoint (St4ScsCd data);

    List<St4ScsCd> sysPointAndLedgerDataLd(@Param("list") List<Integer> cd001);

    List<St4ScsCd> getDataByCd004(St4ScsCd data);

    List<St4ScsCd> getSpotByTaskId(Integer tid);

    List<St4ScsCd>  getPointBySa001(Integer uid);

    List<St4ScsCd> getStaticPoint(St4ScsCd data);

    List<Map> select(@Param("image_id")Integer image_id);
    List<St4ScsCd> selectAll(@Param("image_id")Integer image_id);



    /**
     * 根据任务id查询出所有的问题斑块
     * @param data
     * @return
     */
    List<St4ScsCd> getProblemPlaque(St4ScsCd data);

    /**
     * 得到指定任务下已经下发的点
     * @param data
     * @return
     */
    List<St4ScsCd> getSharedPoint(Integer data);

    /**
     *
     * @param uid
     * @return
     */
    List<St4SysSa> getPersonAndPoint(Integer uid);

    /**
     * 核查结果统计分析斑块地图展示接口
     * @param cl
     * @return
     */
    List<St4ScsCd> listStaticPoint(St4ScsCl cl);

    /**
     * 统计当前任务下原来来的活动设施类型及类型数
     * @param rl
     * @return
     */
    List<Map> pointQualityOrgin(RlhdGroup rl);

    /**
     * 统计当前任务下现在的活动设施类型及类型数
     * @param rl
     * @return
     */
    List<Map> pointQualityNow(RlhdGroup rl);

    List<Map> pointQualityOrginExport(RlhdGroup rl);

    List<Map> pointQualityNowExport(RlhdGroup rl);

    List<ReportVo> redLineReportExport(SysCompany com);
}
