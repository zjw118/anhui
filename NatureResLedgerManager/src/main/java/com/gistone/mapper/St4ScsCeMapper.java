package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.St4ScsCe;

import java.util.List;

/**
 * <p>
 * 航点附件表 Mapper 接口
 * </p>
 *
 * @author xxh
 * @since 2019-08-14
 */
public interface St4ScsCeMapper extends BaseMapper<St4ScsCe> {
    int batchDelete (List<String> list);
}
