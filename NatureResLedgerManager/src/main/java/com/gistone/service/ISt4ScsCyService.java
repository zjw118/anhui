package com.gistone.service;

import com.gistone.VO.ResultVO;
import com.gistone.entity.St4ScsCy;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.St4SysSa;
import com.gistone.swagger.TrackDistribution;
import com.gistone.util.Result;

/**
 * <p>
 * 巡护记录表 服务类
 * </p>
 *
 * @author zhaojingwei
 * @since 2019-08-17
 */
public interface ISt4ScsCyService extends IService<St4ScsCy> {
    /**
     * 巡护记录列表
     * @param sy
     * @return
     */

    Result listRecord(St4ScsCy sy, St4SysSa seUser);

    /**
     * 航迹详情
     * @param sy
     * @return
     */
    Result getSailRecordDetail(St4ScsCy sy);


    /**
     * 人员外键-条件分页查询
     * @param st4ScsCy
     * @return
     */
    Result listPatrolBySA001(St4ScsCy st4ScsCy)throws Exception;


    /**
     * 根据保护区id查询到航迹
     * @param rid
     * @return
     */
    Result getSailRouteByReserveId(Integer rid);

    /**
     *轨迹分布地图接口
     * @return
     */
    ResultVO trackDistribution(TrackDistribution td);

}
