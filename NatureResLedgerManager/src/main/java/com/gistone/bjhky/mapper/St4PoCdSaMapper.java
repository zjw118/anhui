package com.gistone.bjhky.mapper;

import com.gistone.bjhky.entity.St4PoCdSa;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 核查点分配记录表 Mapper 接口
 * </p>
 *
 * @author zjw
 * @since 2019-08-15
 */
public interface St4PoCdSaMapper extends BaseMapper<St4PoCdSa> {
    int insertList(List<St4PoCdSa> list);
    int submitPoint(St4PoCdSa cdsa);

}
