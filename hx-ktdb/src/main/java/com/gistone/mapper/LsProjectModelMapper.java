package com.gistone.mapper;

import com.gistone.entity.LsProjectModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-11-26
 */
public interface LsProjectModelMapper extends BaseMapper<LsProjectModel> {

    LsProjectModel getLsProjectModelByType(@Param(value="type")Integer type);
    int updateFlag1(LsProjectModel lsProjectModel);
    int updateFlag2(LsProjectModel lsProjectModel);
    int updateFlag(Integer type);

}
