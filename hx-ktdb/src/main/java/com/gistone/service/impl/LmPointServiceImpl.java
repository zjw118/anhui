package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.EXCEL.LmPointVO;
import com.gistone.entity.LmPoint;
import com.gistone.mapper.LmPointMapper;
import com.gistone.service.ILmPointService;
import com.gistone.util.ConfigUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjc
 * @since 2019-02-28
 */
@Service
public class LmPointServiceImpl extends ServiceImpl<LmPointMapper, LmPoint> implements ILmPointService {

	@Autowired
	private LmPointMapper lmPointMapper;
	
	@Autowired
	private ConfigUtils configUtils;
	
	@Override
	public Map<String, Object> selectLmPointList(Map<String, Object> resultMap) {
		
		//分页查询条件
		int number = 0;
		int size = 0;
		int page = 0;
		if(resultMap.get("pageSize")!=null && !"".equals(resultMap.get("pageSize").toString())) {
			size = Integer.parseInt(resultMap.get("pageSize").toString());//每页条数
			number = Integer.parseInt(resultMap.get("pageNumber").toString());//开始索引
			page = (number / size) + 1;//当前页码
			
			number = (number-1)*size;
			
			resultMap.put("number", number);
			resultMap.put("size", size);
			resultMap.put("limit", 1);
		}
		
		//分页查询
		List<Map<String,Object>> list = lmPointMapper.selectLmPointList(resultMap);
		
		//加序号
		for (int i = 1; i <= list.size(); i++) {
			list.get(i-1).put("RN", i+number);
		}
		
		//查询数据总条数
		int total = lmPointMapper.selectLmPointListCount(resultMap);
		
		Map<String, Object> ret = new HashMap<>(); 
		
		ret.put("rows", list);
		ret.put("total", total);
		ret.put("page", page);
		
		return ret;
	}

	//保存拐点信息
	@Override
	public Map<String, Object> insertLmPointInfor(LmPoint lp) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		String code = null;
		String msg = null;
		
		//验证拐点是否重复
		List<Map<String, Object>> list = lmPointMapper.selectLmPointInfor(lp.getLpCode(), String.valueOf(lp.getLpSrldId()));
		if(list.get(0) !=null && list.size() > 0) {
			code = "9999";
			msg = "拐点编号重复，添加失败！!";
		}else {
		
			int flag = lmPointMapper.insertLmPointInfor(lp);
			if(flag > 0) {
				code = "0000";
				msg = "添加成功！!";
			}else {
				code = "9999";
				msg = "添加失败！!";
			}
		}
		
		map.put("code", code);
		map.put("msg", msg);
		
		return map;
	}

	//修改拐点信息
	@Override
	public Map<String, Object> updateLmPointInfor(LmPoint lp) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		String code = null;
		String msg = null;
		
		//验证拐点是否重复
		List<Map<String, Object>> list = lmPointMapper.selectLmPointInfor(lp.getLpCode(), String.valueOf(lp.getLpSrldId()));
		if(list.get(0) !=null && list.size() > 0) {
			code = "9999";
			msg = "拐点编号重复，修改失败！!";
		}else {
			
			int flag = lmPointMapper.updateLmPointInfor(lp);
			if(flag > 0) {
				code = "0000";
				msg = "修改成功！!";
			}else {
				code = "0000";
				msg = "修改失败！!";
			}
		}
		
		map.put("code", code);
		map.put("msg", msg);
		
		return map;
	}

	//删除拐点信息
	@Override
	public boolean deleteLmPointInfor(String lpId) {
		
		boolean b = false;
		
		int flag = lmPointMapper.deleteLmPointInfor(lpId);
		if(flag > 0) {
			b = true;
		}else {
			b = false;
		}
		
		return b;
	}

	//导出拐点数据
	@Override
	public String exportLmPointInfor(Map<String, Object> resultMap, HttpServletRequest request,
			HttpServletResponse response) {
		
		/*resultMap.put("limit", 0);
		String filePath = null;
		//分页查询
		List<Map<String,Object>> list = lmPointMapper.selectLmPointList(resultMap);
		//加序号
		for (int i = 1; i <= list.size(); i++) {
			list.get(i-1).put("RN", i);
		}
		String name ="拐点数据";
		String path = configUtils.getExcel_PATH()+"point";
		List<String> order = new ArrayList<>(); //列名
		List<String> orderName = new ArrayList<>(); //列名
		order.add("RN");
		order.add("lp_code");
		order.add("lp_lon");
		order.add("lp_lat");
		order.add("lp_x");
		order.add("lp_y");
		order.add("srld_name");
		order.add("lp_getTime");

		orderName.add("序号");
		orderName.add("编号");
		orderName.add("经度");
		orderName.add("纬度");
		orderName.add("平面坐标x");
		orderName.add("平面坐标y");
		orderName.add("红线台账名称");
		orderName.add("采集时间");

		try {
			filePath = ExcelUtils.exportExcelsToStream(list, name, order, orderName, request, response, path);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return filePath;*/
		return  null;
	}

	//根据红线id查询拐点数据
	@Override
	public Map<String, Object> getLmPointBysrld(Map<String, Object> resultMap) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Map<String, Object>> list = lmPointMapper.selectAllpointBysrld(resultMap);
		
		map.put("rows", list);
		
		return map;
	}

	@Override
	public List<LmPoint> selectPointList(String code,String param,Integer redlineId,Integer pageNum,Integer pageSize) {

		List<LmPoint> pointList =  lmPointMapper.getPointList(code,param,redlineId,(pageNum-1)*pageSize,pageSize);

		return pointList;
	}

	@Override
	public int selectTotal(String code,String param,Integer redlineId) {
		int total = lmPointMapper.getTotal(code,param,redlineId);
		return total;
	}

	@Override
	public Integer getLevelByCode(String code) {
		Integer level = lmPointMapper.selectLevelByCode(code);
		return level;
	}

	@Override
	public List<LmPointVO> selectPointListForAll(String codes, String param, Integer redlineId) {
		return lmPointMapper.selectPointListForAll(codes,param,redlineId);
	}

	@Override
	public Map<String, Object> getPointList(String pointNum, Integer pageNum, Integer pageSize) {
		Map<String,Object> result = new HashMap<>();
		QueryWrapper<LmPoint> wrapper = new QueryWrapper<>();
		if(StringUtils.isNotBlank(pointNum)){
			wrapper.likeRight("lp_code",pointNum);
		}

		IPage<LmPoint> IPage = lmPointMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
		result.put("rows", IPage.getRecords());
		result.put("total", IPage.getTotal());
		return result;
	}


}
