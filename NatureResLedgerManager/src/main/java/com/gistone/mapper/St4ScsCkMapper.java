package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gistone.entity.St4ScsCd;
import com.gistone.entity.St4ScsCk;
import com.gistone.entity.St4SysSd;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xxh
 * @since 2019-08-14
 */
public interface St4ScsCkMapper extends BaseMapper<St4ScsCk> {
    Integer insertAsHashMap(Map<String,Object> map);

    IPage<St4ScsCk> listLedger(IPage<St4ScsCk> page, @Param("ck") St4ScsCk checkLedger);

    IPage<St4ScsCk> listHkyLedger(IPage<St4ScsCk> page, @Param("ck") St4ScsCk checkLedger);
//    List<St4ScsCk> getDetailLedger(St4ScsCk checkLedger);

    List<St4ScsCk> getStageDetail(St4ScsCk checkLedger);

    void insertList(List<St4ScsCk> list);
    /**
     * 核查结果统计分析
     * NatureResDecodeManager
     * @param st4ScsCk
     * @return
     */
    List<St4ScsCk> selectSt4ScsCk(St4ScsCk st4ScsCk);



    /**
     * APP端同步问题点台账接口
     * @param uid
     * @return
     */
    List<St4ScsCk> sysPointAndLedgerData(@Param("sa001") Integer uid,@Param("list") List<String> cl003list);


    /**
     * APP端同步问题点的原始台账接口
     * @param uid
     * @return
     */
    List<St4ScsCk> sysPointAndLedgerDataOrign(@Param("sa001") Integer uid,@Param("list")  List<String>   cl003list);

    /**
     * 台账整改统计
     * NatureResDecodeManager
     * @param st4ScsCd
     * @return
     */
    List<Map> selectSt4ScsCkzg(St4ScsCd st4ScsCd);
    /**
     * 分页查询-获取总数量
     * NatureResDecodeManager
     * @param St4ScsCk
     * @return
     */
    int getPoSum(St4ScsCk St4ScsCk);
    /**
     * 分页查询-获取分页数据
     * NatureResDecodeManager
     * @param St4ScsCk
     * @return
     */
    List<Object> selectPoList(St4ScsCk St4ScsCk);

    /**
     * 一张图展示，查询头部保护地、问题点、巡护次数、巡护里程数量
     * @param
     * @return
     */
    List<Map> selectReserveNumTiew(St4SysSd data);

    /**
     * 统计出已核查和未核查的点位数量
     * @return
     */
    List<Map> pointStatistics();


    /**
     * 统计已整改、未整改、整改中、无需整改的问题点数量
     * @return
     */
    List<Map> pointStatisticsRepairCon();

    /**
     * 申诉审核不通过的时候给对应的核查小组的人员即时推送消息
     * @param ck
     * @return
     */
    List<Map> getTaskMemberUnpass(St4ScsCk ck );

    /**
     * 获取极光推送的信息
     * @return
     */
    St4ScsCk getSendMsgByCk001(St4ScsCk ck);


    List<St4ScsCk> getExportData(St4ScsCk ck);

    /**
     * 环科院的导入用来查询最新的一条台账记录，用于接下来的比对插入更新操作
     * @param ck
     * @return
     */
    St4ScsCk getLastLedgerHky(St4ScsCk ck);

    /**
     * 统计核查审核的质量评估
     * @return
     */
    List<St4ScsCk> examineQuality();

    /**
     * 导出execl的采用easypoi
     * @return
     */
    List<Map> examineQualityEasyPoi();
    List<Map> selectSt4ScsCkMap(St4ScsCk ck);
}
