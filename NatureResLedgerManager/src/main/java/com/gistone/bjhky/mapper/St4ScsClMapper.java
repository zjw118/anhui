package com.gistone.bjhky.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gistone.bjhky.entity.St4ScsCi;
import com.gistone.bjhky.entity.St4ScsCl;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 任务批次表 Mapper 接口
 * </p>
 *
 * @author LiuXiong
 * @since 2019-08-14
 */
public interface St4ScsClMapper extends BaseMapper<St4ScsCl> {

    IPage<St4ScsCl> listTask(IPage page,@Param("cl") St4ScsCl cl);

}
