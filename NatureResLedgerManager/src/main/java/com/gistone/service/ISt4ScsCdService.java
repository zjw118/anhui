package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.VO.ResultVO;
import com.gistone.entity.*;
import com.gistone.util.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

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
    Result sysPointDataLd (Integer roleId,Integer uid);



    Map list2(Integer id);
    Map<String, Object> list(Integer pageNum, Integer pageSize, Integer id);
    void delete(List<Integer> id);
    ResultVO insert(List<Map<String, Object>> data,Integer imageId,Integer createBy) throws Exception;
    void edit(St4ScsCd entity);


    Map<String,Object> exporExcel(Integer id, HttpServletResponse response);

    ResultVO upload(HttpServletRequest request, Image image);

    /**
     *根据任务id查询出所有的问题斑块
     * @param data
     * @return
     */
    ResultVO getProblemPlaque(St4ScsCd data);

    /**
     * 得到已经分配到的问题斑块的人和点
     * @param uid
     * @return
     */
    ResultVO getPersonAndPoint(Integer uid);

    /**
     * 撤销下发的问题斑块
     * @param uid
     * @param points
     * @return
     */
    ResultVO deletePersonAndPoint(Integer uid,List<Integer> points);



    /**
     * 根据台账id查询到具体的斑块信息
     * @param rlGroupID
     * @return
     */
    ResultVO getPointFromStage(Integer rlGroupID);

    /**
     * 在核查统计分析处展示
     * @param cl
     * @return
     */
    ResultVO listStaticPoint(St4ScsCl cl);

    /**
     *人类活动巡查结果质量评估得传递所属监管台账的id
     * @param rl
     * @return
     */
    ResultVO pointQuality(RlhdGroup rl);


    /**
     * (安徽用)人类活动巡查结果质量评估明细
     * @return
     */
    ResultVO  pointQualityDetail(RlhdGroup rl);
}
