package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.EXCEL.LmPointVO;
import com.gistone.entity.LmPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjc
 * @since 2019-02-28
 */
public interface ILmPointService extends IService<LmPoint> {

	Map<String, Object> selectLmPointList(Map<String, Object> resultMap);

	Map<String, Object> insertLmPointInfor(LmPoint lp);

	Map<String, Object> updateLmPointInfor(LmPoint lp);

	boolean deleteLmPointInfor(String lpId);

	String exportLmPointInfor(Map<String, Object> requestData, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> getLmPointBysrld(Map<String, Object> resultMap);

	List<LmPoint> selectPointList(String code,String param,Integer redlineId,Integer pageNum,Integer pageSize);

	int selectTotal(String code,String param,Integer redlineId);

	Integer getLevelByCode(String code);

	List<LmPointVO> selectPointListForAll(String codes, String param, Integer redlineId);

	Map<String,Object> getPointList(String pointNum,Integer pageNum,Integer pageSize);
}
