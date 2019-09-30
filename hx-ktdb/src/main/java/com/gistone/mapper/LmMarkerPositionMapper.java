package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.LmMarkerPosition;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xjc
 * @since 2019-02-28
 */
public interface LmMarkerPositionMapper extends BaseMapper<LmMarkerPosition> {

	int insertLmMarkerPositionInfor(List<Map<String, Object>> lmp);

	void deleteLmMarkerPositionInfor(@Param("lmlpId") String lmlpId);

}
