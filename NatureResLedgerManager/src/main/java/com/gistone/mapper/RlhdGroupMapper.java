package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.RlhdGroup;

import java.util.List;

/**
 * <p>
 * 人类活动台账信息表 Mapper 接口
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-10-25
 */
public interface RlhdGroupMapper extends BaseMapper<RlhdGroup> {
    RlhdGroup getPointFromStage(Integer rl);

     List<RlhdGroup> getLedgerUnbinded();
}
