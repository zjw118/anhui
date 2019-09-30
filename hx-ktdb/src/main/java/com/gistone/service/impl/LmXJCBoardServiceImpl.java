package com.gistone.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.LmXJCBoard;
import com.gistone.mapper.LmXJCBoardMapper;
import com.gistone.service.ILmXJCBoardService;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjc
 * @since 2019-02-28
 */
@Service
public class LmXJCBoardServiceImpl extends ServiceImpl<LmXJCBoardMapper, LmXJCBoard> implements ILmXJCBoardService {

	/*@Autowired
	private LmXJCBoardMapper lmBoardMapper;
	
	@Autowired
	private LmFilesMapper lmFilesMapper;
	
	@Autowired
	private ConfigUtils configUtils;
	
	//标识标牌列表查询
	@Override
	public Map<String, Object> seleteLmBoardList(Map<String, Object> resultMap) {
		
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
		List<Map<String,Object>> list = lmBoardMapper.seleteLmBoardList(resultMap);
		
		//加序号
		for (int i = 1; i <= list.size(); i++) {
			list.get(i-1).put("RN", i+number);
		}
		for (Map<String,Object> map : list) {
			if(map.get("lf_path")!=null && !"".equals(map.get("lf_path").toString())){
				
				String str = "";
				String path = map.get("lf_path").toString();
				String s [] = path.split(",");
				for(int i=0;i<s.length;i++) {
					str += s[i].substring(s[i].indexOf("attached")-1)+",";
				}
				map.put("lf_path", str.substring(0,str.length()-1));
			}
		}
				
		//查询数据总条数
		int total = lmBoardMapper.selectLmBoardListCount(resultMap);
		
		Map<String, Object> ret = new HashMap<>(); 
		
		ret.put("rows", list);
		ret.put("total", total);
		ret.put("page", page);
		
		return ret;
	}

	//保存标识标牌
	@Override
	public Map<String, Object> save(LmXJCBoard lm, String picturePath) {
		
		Map<String, Object> map = new HashMap<>();
		String code = null;
		String msg = null;
		
		List<Map<String, Object>> listMap = lmBoardMapper.seleteLmBoardInfo(lm.getLbCode(), lm.getLbSrldId());
		if(listMap != null && listMap.size() > 0) {
			code = "9999";
			msg = "标识标牌编号重复，添加失败！！";
		}else {
		
			int t = lmBoardMapper.saveLmBoard(lm);
			if(t > 0) {
				String pkid = lmBoardMapper.getPkid(lm);
				if(picturePath != null && !"".equals(picturePath)) {
					
					List<LmFiles> list = new ArrayList<LmFiles>();
					String[] path = picturePath.split(",");
					for(int i=0;i<path.length;i++) {
						
						LmFiles lf = new LmFiles();
						lf.setLfBoardId(Integer.parseInt(pkid));
						lf.setLfPath(path[i]);
						
						list.add(lf);
					}
					
					int i = lmFilesMapper.insertBatch(list);
					if(i > 0) {
						code = "0000";
						msg = "添加成功！！";
					}else {
						code = "9999";
						msg = "添加失败！！";
					}
				}else {
					code = "0000";
					msg = "添加成功！！";
				}
			}else {
				code = "9999";
				msg = "添加失败！！";
			}
		}
			
		map.put("code", code);
		map.put("msg", msg);
		
		return map;
	}
	
	//上传图片
	@Override
	public String upload(MultipartFile files) {
		
		String path = configUtils.getPICTURE_PATH()+"signage/";
		String filePath = PictureUtils.getPicturePath(path, files);
		
		return filePath;
	}

	//修改
	@Override
	public Map<String, Object> update(LmXJCBoard lm, String picturePath) {
		
		Map<String, Object> map = new HashMap<>();
		String code = null;
		String msg = null;
		
		List<Map<String, Object>> listMap = lmBoardMapper.seleteLmBoardInfo(lm.getLbCode(), lm.getLbSrldId());
		if(listMap != null && listMap.size() > 0) {
			code = "9999";
			msg = "标识标牌编号重复，添加失败！！";
		}else {
			
			int i = lmBoardMapper.updateLmBoard(lm);
			if(i > 0) {
				
				if(picturePath != null && !"".equals(picturePath)) {
					
					lmFilesMapper.deleteBatch(String.valueOf(lm.getLbId()));
					List<LmFiles> list = new ArrayList<LmFiles>();
					String[] path = picturePath.split(",");
					for(int j=0;j<path.length;j++) {
						
						LmFiles lf = new LmFiles();
						lf.setLfBoardId(lm.getLbId());
						lf.setLfPath(path[j]);
						
						list.add(lf);
					}
					
					int t = lmFilesMapper.insertBatch(list);
					if(t > 0) {
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
	public boolean delete(String lbId) {
		
		boolean b = false;
		
		int t = lmBoardMapper.deleteLmBoard(lbId);
		if(t > 0) {
			b = true;
		}else {
			b = false;
		}
		
		return b;
	}

	//导出
	@Override
	public String exportLmBoardInfor(Map<String, Object> resultMap, HttpServletRequest request,
			HttpServletResponse response) {
		
		resultMap.put("limit", 0);
		String filePath = null;
		//分页查询
		List<Map<String,Object>> list = lmBoardMapper.seleteLmBoardList(resultMap);
		//加序号
		for (int i = 1; i <= list.size(); i++) {
			list.get(i-1).put("RN", i);
		}
		String name ="标识标牌数据";
		String path = configUtils.getExcel_PATH()+"board";
		List<String> order = new ArrayList<>(); //列名
		List<String> orderName = new ArrayList<>(); //列名
		order.add(0,"RN");
		order.add(1,"srld_name");
		order.add(2,"lb_name");
		order.add(3,"lb_code");
		order.add(4,"lb_x");
		order.add(5,"lb_y");
		order.add(6,"lb_lon");
		order.add(7,"lb_lat");
		order.add(8,"lb_zgbm");
		order.add(9,"lb_jdbm");
		order.add(10,"lb_phone");
		order.add(11,"lb_setTime");
		orderName.add(0, "序号");
		orderName.add(1,"红线台账名称");
		orderName.add(2,"标牌名称");
		orderName.add(3,"编号");
		orderName.add(4,"平面坐标x");
		orderName.add(5,"平面坐标y");
		orderName.add(6,"经度");
		orderName.add(7,"纬度");
		orderName.add(8,"主管部门");
		orderName.add(9,"监督部门");
		orderName.add(10,"联系电话");
		orderName.add(11,"标识牌树立时间");
		
		try {
			filePath = ExcelUtils.exportExcelsToStream(list, name, order, orderName, request, response, path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return filePath;
	}*/

}
