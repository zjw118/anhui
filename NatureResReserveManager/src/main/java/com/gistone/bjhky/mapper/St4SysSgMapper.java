package com.gistone.bjhky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gistone.bjhky.entity.St4SysSg;
import com.gistone.bjhky.entity.St4SysSj;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xxh
 * @since 2019-08-14
 */
public interface St4SysSgMapper extends BaseMapper<St4SysSg> {

    IPage<St4SysSg> lists(Page<St4SysSg> page, @Param("sg") St4SysSg sg);

    int total (St4SysSg data);

    int update(St4SysSg data);

    int insert(St4SysSg data);

    int delete(St4SysSg data);

    List<St4SysSg>  getIdAndValue();

    List<St4SysSg> listByParam(St4SysSg data);

    List<St4SysSg> appList(St4SysSg data);

    List<St4SysSg> appListToPoint(St4SysSg data);

    List<St4SysSg> getDataById(St4SysSg data);

    List<St4SysSg> getReserveDataBySaid(@Param("sa001") Integer said);

}
