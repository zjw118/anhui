package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.VO.ResultVO;
import com.gistone.entity.St4ScsCd;
import com.gistone.entity.St4ScsCk;
import com.gistone.entity.St4SysSa;
import com.gistone.entity.St4SysSd;
import com.gistone.util.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xxh
 * @since 2019-08-14
 */
public interface ISt4ScsCkService extends IService<St4ScsCk> {


    Result saveLedger(St4ScsCk checkLedger, HttpServletRequest request
            , St4SysSa seUser);



    Result updateLedger(St4ScsCk checkLedger, HttpServletRequest request, St4SysSa seUser);
    Result deleteLedger(Integer cld);


    ResultVO listLedger(St4ScsCk checkLedger, St4SysSa seUser);
    Result listHkyLedger(St4ScsCk checkLedger,St4SysSa seUser);

    Result listLedgerSpace(Integer roleId, St4ScsCk checkLedger);

    ResultVO getDetail(St4ScsCk ck);


    Result importExcelCommon(Map<String, MultipartFile> map, St4SysSa seUser, Integer taskId, List<Integer> uidList);

    /**
     * @explain : 根据巡航点唯一标识获取台账信息
     * @author xxh
     * @date 2019/8/16
     * @param cc002
     * @return St4ScsCk
     */
    St4ScsCk getSt4ScsCkByCc002(String cc002);

    /**
     * 同步问题点数据带有台账
     * @param uid
     * @return
     */
    Result sysPointData (Integer uid);

    /**
     * 一张图展示，查询头部保护地、问题点、巡护次数、巡护里程数量
     * @param
     * @return
     */
    Result selectReserveNumTiew(St4SysSd data);




    /**
     * 统计当日核查问题点数量；
     * @return cd
     */
    Result pointStatisticsToday(St4ScsCd cd);

    /**
     * 统计已整改、未整改、整改中、无需整改的问题点数量
     * @return
     */
    Result pointStatisticsRepairCon();


    /**
     * 统计新增的问题点数量
     * @return
     */
    Result pointStatisRecentAdd();

    /**
     * 台账申诉审核(ck067必传,审核:未审核0 1是已审核 2是退回)
     * @param
     * @return
     */
    ResultVO pointStageExamine(St4ScsCk ck);

    /**
     * 提交台账（绿盾）
     * @param
     * @return
     */
    ResultVO insertLedgerLd(St4ScsCd cd, String userId);

    /**
     * 导出excel
     * @param ck
     */
    void doExcel(St4ScsCk ck,HttpServletResponse response);

    /**
     *统计核查审核的质量评估
     * @return
     */
    ResultVO examineQuality();

}
