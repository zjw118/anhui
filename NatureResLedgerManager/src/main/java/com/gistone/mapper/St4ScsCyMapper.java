package com.gistone.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gistone.entity.St4ScsCy;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 巡护记录表 Mapper 接口
 * </p>
 *
 * @author zhaojingwei
 * @since 2019-08-17
 */
@Mapper
public interface St4ScsCyMapper extends BaseMapper<St4ScsCy> {
    //考勤统计
    List<St4ScsCy> selectPoList(St4ScsCy st4ScsCy);

    /**
     * 修改后的考勤一个斑块在不同的任务下可以下发给不同的人
     * @param st4ScsCy
     * @return
     */
    List<St4ScsCy> selectPoListNew(St4ScsCy st4ScsCy);

    //分页查询-获取总数量
    int getPoSum2(St4ScsCy st4ScsCy);
    //分页查询-获取分页数据
    List<Object> selectPoList3(St4ScsCy st4ScsCy);

    /**
     * 航迹详情
     * @param cy
     * @return
     */
    List<St4ScsCy> getSailRecordDetail(St4ScsCy cy);
    /**
     * 通过保护区ID拿到航点数据
     * @param reserveId
     * @return
     */
    List<St4ScsCy> getSailRouteDataByReserveId (Integer reserveId);

    /**
     * 航迹查询列表
     * @param cy
     * @return
     */
    List<St4ScsCy> listSailRecord(@Param("cy") St4ScsCy cy);

    /**
     * 轨迹分布
     * @return
     */
    List<St4ScsCy> trackDistribution(@Param("taskName") String taskName,@Param("ledgerId") Integer ledgerId);
}
