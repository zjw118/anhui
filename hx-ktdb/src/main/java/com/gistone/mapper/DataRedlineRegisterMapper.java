package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.VO.DataRedlineRegisterVO;
import com.gistone.entity.DataRedlineRegister;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;
import java.util.Map;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xjc
 * @since 2019-02-28
 */
public interface DataRedlineRegisterMapper extends BaseMapper<DataRedlineRegister> {

	List<Map<String, Object>> listAll(DataRedlineRegister date);

	List<Map<String, Object>> getRedLindList(@Param("id") String id);

	String selectRedlineNumber(@Param("redlineId") Integer redlineId);

	void updateTarget(@Param("id") Integer id,@Param("target") String target);

	void updateBy(DataRedlineRegister data);
	List<DataRedlineRegisterVO> getList();

}
