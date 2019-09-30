package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.VO.SurveyTableInfoVO;
import com.gistone.entity.DicIndexItem;

import java.util.List;
import java.util.Map;



/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjc
 * @since 2019-03-01
 */
public interface IDicIndexItemService extends IService<DicIndexItem> {

	List<Map<String, Object>> getAllList(DicIndexItem dit);

	Map<String, Object> getSonTableList(Map<String, Object> resultMap);

	boolean saveSonTable(Map<String, Object> resultMap);

	Map<String, Object> getFiledList(Map<String, Object> resultMap);

	Map<String, Object> saveFiled(Map<String, Object> resultMap);

	boolean deleteFiled(String pkId);

	Map<String,Object> deleteSonTable(String pkId);

	boolean updateSonTable(Map<String, Object> resultMap);

	boolean updateFiled(Map<String, Object> resultMap);

	List<Map<String,Object>> getSurveyTableList(String redlineName,String tableName,Integer pageNum,Integer pageSize);

	int getTotal(String redlineName,String tableName);

	Map<String,Object> getTableInfo(Integer id);

	List<SurveyTableInfoVO> getFileds(Integer id);

	boolean deleteFileds(Integer id);

	boolean editSort(List<Map<String,Object>> list);

}
