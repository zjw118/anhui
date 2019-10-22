package com.gistone.mapper;

import com.gistone.entity.St4ScsCn;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xxh
 * @since 2019-08-14
 */
public interface St4ScsCnMapper extends BaseMapper<St4ScsCn> {
    int insertList(List<St4ScsCn> list);
    int batchUpdateByCid(List<String> list);
    St4ScsCn selectCn010ByCk001(Integer ck001);
}
