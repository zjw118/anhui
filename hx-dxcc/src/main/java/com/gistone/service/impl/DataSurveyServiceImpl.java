package com.gistone.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.DataSurvey;
import com.gistone.entity.DataSurveyFiles;
import com.gistone.entity.DataSurveyJson;
import com.gistone.mapper.DataSurveyFilesMapper;
import com.gistone.mapper.DataSurveyJsonMapper;
import com.gistone.mapper.DataSurveyMapper;
import com.gistone.service.IDataSurveyService;
import com.gistone.util.ConfigUtils;
import com.gistone.util.DateUtils;
import com.gistone.util.PictureUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjc
 * @since 2019-03-01
 */
@Service
public class DataSurveyServiceImpl extends ServiceImpl<DataSurveyMapper, DataSurvey> implements IDataSurveyService {

	@Autowired
	private DataSurveyMapper dataSurveyMapper;
	
	@Autowired
	private ConfigUtils configUtils;
	
	@Autowired
	private DataSurveyJsonMapper dataSurveyJsonMapper;
	
	@Autowired
	private DataSurveyFilesMapper dataSurveyFilesMapper;
	
	//查询该用户所拥有红线块
	@Override
	public List<Map<String, Object>> getRedLindList(String id) {
		
		List<Map<String,Object>> map = dataSurveyMapper.getRedLindList(id);
 		
		return map;
	}

	//保存调查点信息
	@Override
	public boolean save(DataSurvey dataSurvey, String detailsArr) {
		
		boolean b = false;
		
		int flag = dataSurveyMapper.insert(dataSurvey);
		if(flag > 0) {
			
			String pkid = dataSurveyMapper.getPkid(dataSurvey);
			
			if(StringUtils.isNotEmpty(detailsArr)) {
				
				List<DataSurveyJson> list = new ArrayList<DataSurveyJson>();
				JSONArray array = JSONArray.parseArray(detailsArr);
				for(int i=0;i<array.size();i++) {
					
					DataSurveyJson dsj = new DataSurveyJson();
					String indexItemId = JSONObject.parseObject(JSONObject.toJSONString(array.get(i))).getString("indexItemId");
					String indexItemData = JSONObject.parseObject(JSONObject.toJSONString(array.get(i))).getString("indexItemData");
					
					dsj.setFkSurveyId(Integer.parseInt(pkid));
					dsj.setIndexItemId(Integer.parseInt(indexItemId));
					dsj.setSurveyJsondata(indexItemData);
					
					list.add(dsj);
				}
				
				int t = dataSurveyJsonMapper.insertBatch(list);
				if(t > 0) {
					b = true;
				}else {
					b = false;
				}
			}else {
				b = true; 
			}
			
		}else {
			b = false;
		}
		
		return b;
	}

	//图片上传
	@Override
	public Map<String, Object> upload(MultipartFile[] files) {
		
		Map<String, Object> map = new HashMap<>();
		
		String path = configUtils.getPICTURE_PATH()+"dataSurvey/";
		String pattern = "yyyyMMddHHmmss";
		String code = "";
		String msg = "";
		String data = DateUtils.format(new Date(),pattern);
		List<DataSurveyFiles> list = new ArrayList<DataSurveyFiles>();
		for(int i=0;i<files.length;i++) {
			
			MultipartFile file = files[i];
			
			DataSurveyFiles dataSurveyFiles = new DataSurveyFiles();
			
			String mapData = PictureUtils.getPicturePath(path, file);
			
			dataSurveyFiles.setIdentification(data);
			dataSurveyFiles.setPath(mapData);
//			dataSurveyFiles.setIndexItemId(Integer.parseInt(mapData.get("indexItemId").toString()));
			
			list.add(dataSurveyFiles);
		}
		
		int t = dataSurveyFilesMapper.insertBatch(list);
		if(t > 0) {
			code = "0000";
			msg = "上传成功！！";
		}else {
			code = "9999";
			msg = "上传失败！！";
			data = null;
		}
		
		map.put("data", data);
		map.put("code", code);
		map.put("msg", msg);
		
		return map;
	}



}
