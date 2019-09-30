package com.gistone.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.LmMarker;
import com.gistone.mapper.LmMarkerMapper;
import com.gistone.mapper.LmMarkerPositionMapper;
import com.gistone.service.ILmMarkerService;
import com.gistone.util.ConfigUtils;
import com.gistone.util.PictureUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
public class LmMarkerServiceImpl extends ServiceImpl<LmMarkerMapper, LmMarker> implements ILmMarkerService {

	@Autowired
	private LmMarkerMapper lmMarkerMapper;
	
	@Autowired
	private ConfigUtils configUtils;
	
	@Autowired
	private LmMarkerPositionMapper lmMarkerPositionMapper;
	
	@Override
	public Map<String, Object> getLmMarkerList(Map<String, Object> resultMap) {
		
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
		List<Map<String,Object>> list = lmMarkerMapper.seleteLmMarkerList(resultMap);
		//加序号
		for (int i = 1; i <= list.size(); i++) {
			list.get(i-1).put("RN", i+number);
		}
		
		int total = lmMarkerMapper.selectLmMarkerCount(resultMap);
		
		Map<String, Object> ret = new HashMap<>(); 
		
		ret.put("rows", list);
		ret.put("total", total);
		ret.put("page", page);
		
		return ret;
	}

	//图片上传
	@Override
	public String upload(MultipartFile files) {
		
		String path = configUtils.getPICTURE_PATH()+"marker/";
		String filePath = PictureUtils.getPicturePath(path, files);
		
		return filePath;
	}

	//保存界碑数据
	@Override
	public Map<String, Object> save(LmMarker lm, List<Map<String, Object>> lmp) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		String code = "";
		String msg = "";
		
		//根据刻号查询是否重复
		List<Map<String, Object>> list = lmMarkerMapper.selectLmMarkerInfor(lm);
		if(list != null && list.size() > 0) {
			code = "9999";
			msg = "界碑刻号重复，保存失败！！";
		}else {
			
			int t = lmMarkerMapper.insertLmMarkerInfor(lm);
			if(t > 0) {
				
				int lmId = lm.getLmId();
				if(lmp !=null && lmp.size()>0 && lmp.get(0)!=null) {
					for (Map<String, Object> map1 : lmp) {
						map1.put("lmpLbId", lmId);
					}
					
					int i = lmMarkerPositionMapper.insertLmMarkerPositionInfor(lmp);
					if(i > 0) {
						code = "0000";
						msg = "保存成功！！";
					}else {
						code = "9999";
						msg = "保存失败！！";
					}
				}else {
					code = "0000";
					msg = "保存成功！！";
				}
				
			}else {
				code = "9999";
				msg = "保存失败！！";
			}
			
		}
		
		map.put("code", code);
		map.put("msg", msg);
		
		return map;
	}

	//修改
	@Override
	public Map<String, Object> update(LmMarker lm, List<Map<String, Object>> lmp) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		String code = "";
		String msg = "";
		
		//根据刻号查询是否重复
		List<Map<String, Object>> list = lmMarkerMapper.selectLmMarkerInfor(lm);
		if(list != null && list.size() > 0) {
			code = "9999";
			msg = "界碑刻号重复，修改失败！！";
		}else {
			
			int t = lmMarkerMapper.updateLmMarkerInfor(lm);
			if(t > 0) {
				
				lmMarkerPositionMapper.deleteLmMarkerPositionInfor(String.valueOf(lm.getLmId()));
				if(lmp !=null && lmp.size()>0 && lmp.get(0)!=null) {
					for (Map<String, Object> map1 : lmp) {
						map1.put("lmpLbId", lm.getLmId());
					}
					
					int i = lmMarkerPositionMapper.insertLmMarkerPositionInfor(lmp);
					if(i > 0) {
						code = "0000";
						msg = "修改成功！！";
					}else {
						code = "9999";
						msg = "修改失败！！";
					}
				}else {
					code = "0000";
					msg = "修改成功！！";
				}
			}else {
				code = "9999";
				msg = "修改失败！！";
			}
			
		}
		
		map.put("code", code);
		map.put("msg", msg);
		
		return map;
	}
	
	//删除
	@Override
	public boolean deleteByLmId(String lmId) {
		
		boolean b = false;
		
		int t = lmMarkerMapper.deleteByLmId(lmId);
		if(t > 0) {
			b = true;
		}else {
			b = false;
		}
		
		return b;
	}

	//导出
	@Override
	public String exportLmMarkerInfor(Map<String, Object> resultMap, HttpServletRequest request,
			HttpServletResponse response) {
		
		/*resultMap.put("limit", 0);
		String filePath = null;
		//分页查询
		List<Map<String,Object>> list = lmMarkerMapper.seleteLmMarkerList(resultMap);
		//加序号
		for (int i = 1; i <= list.size(); i++) {
			list.get(i-1).put("RN", i);
		}

		String name ="界碑数据";
		String path = configUtils.getExcel_PATH()+"marker";
		List<String> order = new ArrayList<>(); //列名
		List<String> orderName = new ArrayList<>(); //列名
		order.add("RN");
		order.add("lm_lp_id");
		order.add("lm_jzkh");
		order.add("lm_szd");
		order.add("lm_sffbsp");
		order.add("lm_beizhu");
		order.add("lm_jsfzr");
		order.add("lm_setTime");

		orderName.add("序号");
		orderName.add("拐点id");
		orderName.add("界桩刻号");
		orderName.add("所在地");
		orderName.add("是否附标识牌");
		orderName.add("备注");
		orderName.add("技术负责人");
		orderName.add("设立时间");

		try {
			filePath = ExcelUtils.exportExcelsToStream(list, name, order, orderName, request, response, path);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return filePath;*/

		return  null;
	}

}
