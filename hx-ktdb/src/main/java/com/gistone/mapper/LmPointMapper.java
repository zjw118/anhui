package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.EXCEL.LmPointVO;
import com.gistone.entity.LmBoard;
import com.gistone.entity.LmPoint;

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
public interface LmPointMapper extends BaseMapper<LmPoint> {

	List<Map<String, Object>> selectLmPointList(@Param("map") Map<String, Object> resultMap);

	int selectLmPointListCount(@Param("map") Map<String, Object> resultMap);

	int deleteLmPointInfor(@Param("lpId") String lpId);

	int insertLmPointInfor(LmPoint lp);

	int updateLmPointInfor(LmPoint lp);

	List<Map<String, Object>> selectLmPointInfor(@Param("lpCode") String lpCode, @Param("lpSrldId") String lpSrldId);

	List<Map<String, Object>> selectAllpointBysrld(Map<String, Object> resultMap);

	List<LmPoint> getPointList(@Param("code") String code,@Param("param") String param,@Param("redlineId") Integer redlineId,@Param("startNum") Integer startNum,@Param("pageSize") Integer pageSize);

	int getTotal(@Param("code") String code,@Param("param") String param,@Param("redlineId") Integer redlineId);

	Integer selectLevelByCode(String code);

	List<LmPointVO> selectPointListForAll(@Param("code")String codes, @Param("param")String param,@Param("redlineId") Integer redlineId);



	int add4(LmPoint lmPoint);
	int delete4(@Param("id")Integer id);

}
