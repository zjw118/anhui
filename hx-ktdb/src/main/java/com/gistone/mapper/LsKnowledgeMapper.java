package com.gistone.mapper;

import com.gistone.entity.LsKnowledge;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-11-26
 */
public interface LsKnowledgeMapper extends BaseMapper<LsKnowledge> {
        int updateFlag();
}
