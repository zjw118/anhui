package com.gistone.mapper;

import com.gistone.entity.Iterpretation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 人类活动解译信息表 Mapper 接口
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-10-18
 */
public interface IterpretationMapper extends BaseMapper<Iterpretation> {
    List<Iterpretation> getSpotByTaskId(Integer tid);

    List<Iterpretation> sysSpotData(Integer tid);
}
