package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.VO.SurveyTableInfoVO;
import com.gistone.entity.DicIndexItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;



/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xjc
 * @since 2019-03-01
 */
public interface DicIndexItemMapper extends BaseMapper<DicIndexItem> {

	List<Map<String, Object>> getAllList(DicIndexItem dit);

	List<Map<String, Object>> selectSonTableList(@Param("map") Map<String, Object> resultMap);

	int selectSonTableListCount(@Param("map") Map<String, Object> resultMap);

	int saveSonTable(@Param("map") Map<String, Object> resultMap);

	List<Map<String, Object>> selectFiledList(@Param("map") Map<String, Object> resultMap);

	int selectFiledCount(@Param("map") Map<String, Object> resultMap);

	int saveFiled(@Param("map") Map<String, Object> resultMap);

	String getPkId(@Param("map") Map<String, Object> resultMap);

	int deleteFiled(@Param("pkId") String pkId);

	List<Map<String, Object>> selectSonTableInfo(@Param("pkId") String pkId);

	int deleteSonTable(@Param("pkId") String pkId);

	int deleteFiledType(@Param("pkId") String pkId);

	String getTablePkId(@Param("map") Map<String, Object> resultMap);

	int updateFiledById(@Param("map") Map<String, Object> resultMap);

	int deleteFiledTypeById(@Param("pkId") String pkId);

	List<Map<String,Object>> selectSurveyTableList(@Param("redlineName") String redlineName,@Param("tableName") String tableName,@Param("startNum") Integer startNum,@Param("pageSize") Integer pageSize);

	int selectTotal(@Param("redlineName") String redlineName, @Param("tableName") String tableName );

	Map<String,Object> selectTableInfo(@Param("id") Integer id);

	String selectName(@Param("id") Integer id);

	List<SurveyTableInfoVO> selectFileds(@Param("id") Integer id);

	int deleteFileds(@Param("id") Integer id);

    int deleteFiledValues(@Param("id") Integer id);

    int updateSort(Map<String,Object> map);

    int selectId();



}
