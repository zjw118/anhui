package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.DataRenew;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;
import java.util.Map;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xjc
 * @since 2019-04-19
 */
public interface DataRenewMapper extends BaseMapper<DataRenew> {

	List<Map<String, Object>> selectRenewVer(@Param("version") String version);

	String selectMaxId();

}
