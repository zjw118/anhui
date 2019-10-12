package com.gistone.bjhky.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gistone.bjhky.entity.St4ScsCy;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.bjhky.entity.St4SysSa;
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
public interface St4ScsCyMapper extends BaseMapper<St4ScsCy> {
    //查询列表
    List<Object> selectPoList(St4ScsCy st4ScsCy);

    //分页查询-获取总数量
    int getPoSum2(St4ScsCy st4ScsCy);
    //分页查询-获取分页数据
    List<Object> selectPoList2(St4ScsCy st4ScsCy);

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
    IPage<St4ScsCy> listSailRecord(Page<St4ScsCy>page, @Param("cy") St4ScsCy cy);
}
