package com.gistone.bjhky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gistone.bjhky.entity.St4ScsCc;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 航点基础表 Mapper 接口
 * </p>
 *
 * @author xxh
 * @since 2019-08-14
 */
public interface St4ScsCcMapper extends BaseMapper<St4ScsCc> {
    /**
     * 航点管理列表
     * @param cc
     * @return
     */
    List<St4ScsCc> getSailList(St4ScsCc cc);

    /**
     * 通过航点ID拿到在巡护里面的详情
     * @param cc
     * @return
     */
    List<St4ScsCc> getSailPointRecordDetail(St4ScsCc cc);

    /**
     * 通过航点ID拿到在台账里面的详情
     * @param cc
     * @return
     */
    List<St4ScsCc> getSailPointLedgerDetail(St4ScsCc cc);
    /**
     * 统计查询
     * @param St4ScsCc
     * @return
     */
    List<St4ScsCc> selectSt4ScsCc(St4ScsCc St4ScsCc);
    /**
     * 分页查询-获取总数量
     * NatureResDecodeManager
     * @param St4ScsCc
     * @return
     */
    int getPoSum(St4ScsCc St4ScsCc);
    /**
     * 分页查询-获取分页数据
     * NatureResDecodeManager
     * @param St4ScsCc
     * @return
     */
    List<Object> selectPoList(St4ScsCc St4ScsCc);
    /**
     * 查询物种分布
     * NatureResDecodeManager
     * @param
     * @return
     */
    List<St4ScsCc> listSpeciesToMap(St4ScsCc data);

    /**
     * 通过保护区ID拿到航点数据
     * @param reserveId
     * @return
     */
    List<St4ScsCc> getSailPointDataByReserveId (Integer reserveId);

    List<Map> statisDw(St4ScsCc data);

    List<Map> statisZw(St4ScsCc data);

   IPage<St4ScsCc> getSailPointData(Page page, @Param("cc") St4ScsCc cc);
}
