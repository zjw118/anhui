package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.ShpBatch;

import java.util.List;

/**
 * <p>
 * 预设数据文件批次表 Mapper 接口
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-10-29
 */
public interface ShpBatchMapper extends BaseMapper<ShpBatch> {
    List<ShpBatch> getNewList();
    ShpBatch getNewShpBatch();



}
