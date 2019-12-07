package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gistone.entity.RlhdGroup;
import org.apache.ibatis.annotations.Param;

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


    IPage<RlhdGroup> detailWithStage(IPage<RlhdGroup> list,@Param("id") Integer id,@Param("number")String number,@Param("name")String name);
}
