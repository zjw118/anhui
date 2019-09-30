package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.LmMarker;

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
public interface LmMarkerMapper extends BaseMapper<LmMarker> {

	List<Map<String, Object>> seleteLmMarkerList(Map<String, Object> resultMap);

	int selectLmMarkerCount(Map<String, Object> resultMap);

	List<Map<String, Object>> selectLmMarkerInfor(LmMarker lm);

	int insertLmMarkerInfor(LmMarker lm);

	int deleteByLmId(@Param("lmId") String lmId);

	int updateLmMarkerInfor(LmMarker lm);

}
