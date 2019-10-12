package com.gistone.bjhky.mapper;

import com.gistone.bjhky.entity.St4ScsCd;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
}
