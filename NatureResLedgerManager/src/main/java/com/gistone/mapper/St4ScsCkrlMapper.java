package com.gistone.mapper;

import com.gistone.entity.St4ScsCkrl;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.excel.St4ScsCkrlVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjw
 * @since 2019-11-07
 */
public interface St4ScsCkrlMapper extends BaseMapper<St4ScsCkrl> {

    List<St4ScsCkrlVO> exportHumanStage (St4ScsCkrl rl);

}
