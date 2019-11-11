package com.gistone.mapper;

import com.gistone.entity.St4ScsCd;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    List<St4ScsCd> batchSelectByCode(List<String> list);

    List<St4ScsCd> listCheckPointToView(St4ScsCd data);

    List<St4ScsCd>  listCheckPoint (St4ScsCd data);

    List<St4ScsCd> sysPointAndLedgerDataLd(@Param("list") List<Integer> cd001);

    List<St4ScsCd> getDataByCd004(St4ScsCd data);


    List<St4ScsCd> getSpotByTaskId(Integer tid);
    List<St4ScsCd> sysSpotData(Integer tid);
    List<St4ScsCd>  getPointBySa001(Integer uid);
    List<St4ScsCd> getStaticPoint(St4ScsCd data);

}
