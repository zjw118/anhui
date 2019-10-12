package com.gistone.bjhky.mapper;

import com.gistone.bjhky.entity.St4PoSaCl;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 巡护任务表 Mapper 接口
 * </p>
 *
 * @author zjw
 * @since 2019-08-15
 */
public interface St4PoSaClMapper extends BaseMapper<St4PoSaCl> {
    int insertList (List<St4PoSaCl> uidList);


}
