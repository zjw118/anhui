package com.gistone.mapper;

import com.gistone.entity.St4ScsCbd;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 移动端提交检测表 Mapper 接口
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-12-04
 */
public interface ISt4ScsCbdMapper extends BaseMapper<St4ScsCbd> {

    int updateByType();

    int updateByType2();
}
