package com.gistone.bjhky.mapper;

import com.gistone.bjhky.entity.St4ScsCab;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjw
 * @since 2019-09-02
 */
public interface St4ScsCabMapper extends BaseMapper<St4ScsCab> {

    List<St4ScsCab> listForApp(St4ScsCab data);

    St4ScsCab getByIdForApp(St4ScsCab data);
}
