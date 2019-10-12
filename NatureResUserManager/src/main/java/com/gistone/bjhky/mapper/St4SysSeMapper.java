package com.gistone.bjhky.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gistone.bjhky.entity.St4SysSe;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 数据备份表 Mapper 接口
 * </p>
 *
 * @author zhaojingwei
 * @since 2019-09-04
 */
public interface St4SysSeMapper extends BaseMapper<St4SysSe> {
    //根据条件，查询列表，判断null
    List<St4SysSe> listByName(St4SysSe data);


    IPage<St4SysSe> listDataBackUp(Page<St4SysSe> page, @Param("se") St4SysSe se);
}
