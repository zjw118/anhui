package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.LoginLog;
import com.gistone.util.PageBean;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xjc
 * @since 2019-04-03
 */
public interface LoginLogMapper extends BaseMapper<LoginLog> {
    // 	分页查询-获取总条量
    int getPoSum(PageBean pageBean);
    //	分页查询-获取数据
    List<Object> selectPoList(PageBean pageBean);



}
