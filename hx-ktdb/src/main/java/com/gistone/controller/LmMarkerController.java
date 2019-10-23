package com.gistone.controller;


import com.gistone.entity.LmMarker;
import com.gistone.service.ILmMarkerService;
import com.gistone.util.DateUtils;
import com.gistone.util.RegUtil;
import com.gistone.util.Result;
import com.gistone.util.ResultEnum;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器 界碑管理
 * </p>
 *
 * @author xjc
 * @since 2019-02-28
 */
@RestController
@RequestMapping("/api/ktdb/lmMarker")
public class LmMarkerController {

	@Autowired
	private ILmMarkerService iLmMarkerService;
	
	@Autowired
	private StringRedisTemplate redisTemplate; 
	
	/**
	 * 获取界碑列表
	 * @param requestData
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "getLmMarkerList", method = RequestMethod.POST)
	public Object getLmMarkerList(@RequestBody Map<String, Object> requestData,HttpServletRequest request,HttpServletResponse response) {
		
		//请求参数格式校验
		Object dataParam = requestData.get("data");
		if (dataParam == null) {
			return Result.build(1333, "请求数据data不能为空！");
		}
		
		Map<String, Object> data = (Map<String, Object>) requestData.get("data");
		Map<String, Object> resultMap = new HashMap<>();
		//获取前端传递的参数
		//采集时间起
		if (RegUtil.CheckParameter(data.get("startTime"), null, null, false)) {
			resultMap.put("startTime",data.get("startTime"));
		}
		//采集时间止
		if (RegUtil.CheckParameter(data.get("endTime"), null, null, false)) {
			resultMap.put("endTime",data.get("endTime"));
		}
		//界桩刻号
		if (RegUtil.CheckParameter(data.get("lmJzkh"), null, null, false)) {
			resultMap.put("lmJzkh",data.get("lmJzkh"));
		}
		//所在地
		if (RegUtil.CheckParameter(data.get("lmSzd"), null, null, false)) {
			resultMap.put("lmSzd",data.get("lmSzd"));
		}
		//每页条数
		if (RegUtil.CheckParameter(data.get("pageSize"), null, null, false)) {
			resultMap.put("pageSize",Integer.parseInt(data.get("pageSize").toString()));
		}
		//开始索引
		if (RegUtil.CheckParameter(data.get("pageNumber"), null, null, false)) {
			resultMap.put("pageNumber",Integer.parseInt(data.get("pageNumber").toString()));
		}
		
		Map<String,Object> map = iLmMarkerService.getLmMarkerList(resultMap);
		
		return Result.ok(map);
	}
	
	/**
	 * 图片上传
	 * @param files
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public Object upload(@RequestParam(value="files") MultipartFile files,HttpServletRequest request, HttpServletResponse response) {
		
		String path = iLmMarkerService.upload(files);
		return Result.ok(path);
	}
	
	/**
	 * 保存界碑信息
	 * @param requestData
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Object save(@RequestBody Map<String, Object> requestData,HttpServletRequest request,HttpServletResponse response) {
		
		//请求参数格式校验
		Object dataParam = requestData.get("data");
		if (dataParam == null) {
			return Result.build(1333, "请求数据data不能为空！");
		}
		//根据token得到用户信息
		String user = redisTemplate.opsForValue().get(requestData.get("accessToken"));
		
		JSONObject jsStr = JSONObject.fromObject(user); 
		LmMarker lm = new LmMarker();
		
		Map<String, Object> data = (Map<String, Object>) requestData.get("data");
		//红线台账id
		if (RegUtil.CheckParameter(data.get("lmSrldId"), null, null, false)) {
			lm.setLmSrldId(Integer.parseInt(data.get("lmSrldId").toString()));
		}
		//拐点id
		if (RegUtil.CheckParameter(data.get("lmLpId"), null, null, false)) {
			lm.setLmLpId(Integer.parseInt(data.get("lmLpId").toString()));
		}
		//界碑刻号
		if (RegUtil.CheckParameter(data.get("lmJzkh"), null, null, false)) {
			lm.setLmJzkh(data.get("lmJzkh").toString());
		}
		//所在地
		if (RegUtil.CheckParameter(data.get("lmSzd"), null, null, false)) {
			lm.setLmSzd(data.get("lmSzd").toString());
		}
		//是否附标识牌
		if (RegUtil.CheckParameter(data.get("lmSffbsp"), null, null, false)) {
			lm.setLmSffbsp(data.get("lmSffbsp").toString());
		}
		//界桩位置略图
		if(RegUtil.CheckParameter(data.get("lmJzwzlt"), null, null, false)) {
			lm.setLmJzwzlt(data.get("lmJzwzlt").toString());
		}
		//界桩现场照片
		if(RegUtil.CheckParameter(data.get("lmJzxczp"), null, null, false)) {
			lm.setLmJzxczp(data.get("lmJzxczp").toString());
		}
		//备注
		if (RegUtil.CheckParameter(data.get("lmBeizhu"), null, null, false)) {
			lm.setLmBeizhu(data.get("lmBeizhu").toString());
		}
		//技术负责人
		if (RegUtil.CheckParameter(data.get("lmJsfzr"), null, null, false)) {
			lm.setLmJsfzr(data.get("lmJsfzr").toString());
		}
		//设立时间
		if (RegUtil.CheckParameter(data.get("lmSettime"), null, null, false)) {
			lm.setLmSettime(DateUtils.StrtoDateYMD(data.get("lmSettime").toString()));
		}
		
		Map<String,Object> map = (Map<String, Object>) JSONObject.toBean(jsStr); 
		//新增时间
		lm.setLmAddTime(new Date());
		//新增用户id
		lm.setLmAddUid(Integer.parseInt(map.get("id").toString()));
		lm.setLmIdDel("0");
		
		//界碑位置描述表 
		List<Map<String,Object>> lmp = null;
		if (RegUtil.CheckParameter(data.get("lmMarkerPosition"), null, null, false)) {
			lmp = (List<Map<String,Object>>) data.get("lmMarkerPosition");
		}
		
		Map<String,Object> resultMap = iLmMarkerService.save(lm, lmp);
		
		return Result.build(0000, resultMap.get("msg").toString());
	}
	
	/**
	 * 修改界碑信息
	 * @param requestData
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public Object update(@RequestBody Map<String, Object> requestData,HttpServletRequest request,HttpServletResponse response) {
		
		//请求参数格式校验
		Object dataParam = requestData.get("data");
		if (dataParam == null) {
			return Result.build(1333, "请求数据data不能为空！");
		}
		//根据token得到用户信息
		String user = redisTemplate.opsForValue().get(requestData.get("accessToken"));
		
		JSONObject jsStr = JSONObject.fromObject(user); 
		LmMarker lm = new LmMarker();
		
		Map<String, Object> data = (Map<String, Object>) requestData.get("data");
		//主键id
		if(RegUtil.CheckParameter(data.get("lmId"), null, null, false)) {
			lm.setLmId(Integer.parseInt(data.get("lmId").toString()));
		}
		//红线台账id
		if (RegUtil.CheckParameter(data.get("lmSrldId"), null, null, false)) {
			lm.setLmSrldId(Integer.parseInt(data.get("lmSrldId").toString()));
		}
		//拐点id
		if (RegUtil.CheckParameter(data.get("lmLpId"), null, null, false)) {
			lm.setLmLpId(Integer.parseInt(data.get("lmLpId").toString()));
		}
		//界碑刻号
		if (RegUtil.CheckParameter(data.get("lmJzkh"), null, null, false)) {
			lm.setLmJzkh(data.get("lmJzkh").toString());
		}
		//所在地
		if (RegUtil.CheckParameter(data.get("lmSzd"), null, null, false)) {
			lm.setLmSzd(data.get("lmSzd").toString());
		}
		//是否附标识牌
		if (RegUtil.CheckParameter(data.get("lmSffbsp"), null, null, false)) {
			lm.setLmSffbsp(data.get("lmSffbsp").toString());
		}
		//界桩位置略图
		if(RegUtil.CheckParameter(data.get("lmJzwzlt"), null, null, false)) {
			lm.setLmJzwzlt(data.get("lmJzwzlt").toString());
		}
		//界桩现场照片
		if(RegUtil.CheckParameter(data.get("lmJzxczp"), null, null, false)) {
			lm.setLmJzxczp(data.get("lmJzxczp").toString());
		}
		//备注
		if (RegUtil.CheckParameter(data.get("lmBeizhu"), null, null, false)) {
			lm.setLmBeizhu(data.get("lmBeizhu").toString());
		}
		//技术负责人
		if (RegUtil.CheckParameter(data.get("lmJsfzr"), null, null, false)) {
			lm.setLmJsfzr(data.get("lmJsfzr").toString());
		}
		//设立时间
		if (RegUtil.CheckParameter(data.get("lmSettime"), null, null, false)) {
			lm.setLmSettime(DateUtils.StrtoDateYMD(data.get("lmSettime").toString()));
		}
		
		Map<String,Object> map = (Map<String, Object>) JSONObject.toBean(jsStr); 
		//新增时间
		lm.setLmUpdTime(new Date());
		//新增用户id
		lm.setLmUpdUid(Integer.parseInt(map.get("id").toString()));
		
		//界碑位置描述表 
		List<Map<String,Object>> lmp = null;
		if (RegUtil.CheckParameter(data.get("lmMarkerPosition"), null, null, false)) {
			lmp = (List<Map<String,Object>>) data.get("lmMarkerPosition");
		}
		
		Map<String,Object> resultMap = iLmMarkerService.update(lm, lmp);
		
		return Result.build(0000, resultMap.get("msg").toString());
	}
	
	/**
	 * 删除界碑
	 * @param requestData
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public Object delete(@RequestBody Map<String, Object> requestData,HttpServletRequest request,HttpServletResponse response) {
		
		//请求参数格式校验
		Object dataParam = requestData.get("data");
		if (dataParam == null) {
			return Result.build(1333, "请求数据data不能为空！");
		}
		
		Map<String, Object> data = (Map<String, Object>) requestData.get("data");
		//主键id
		String lmId = null;
		if(RegUtil.CheckParameter(data.get("lmId"), null, null, false)) {
			lmId = data.get("lmId").toString();
		}
		
		boolean b = iLmMarkerService.deleteByLmId(lmId);
		if(b) {
			return Result.build(0000, "删除成功！！");
		}else {
			return Result.build(9999, "删除失败！！");
		}
	}
	
	/**
	 * 拐点数据导出
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "exportLmMarkerInfor", method = RequestMethod.POST)
	public Object exportLmMarkerInfor(@RequestBody Map<String, Object> requestData, HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		//请求参数格式校验
		Object dataParam = requestData.get("data");
		if (dataParam == null) {
			return Result.build(1333, "请求数据data不能为空！");
		}
		Map<String, Object> data = (Map<String, Object>) requestData.get("data");
		Map<String, Object> resultMap = new HashMap<>();
		//采集时间起
		if (RegUtil.CheckParameter(data.get("startTime"), null, null, false)) {
			resultMap.put("startTime",data.get("startTime"));
		}
		//采集时间止
		if (RegUtil.CheckParameter(data.get("endTime"), null, null, false)) {
			resultMap.put("endTime",data.get("endTime"));
		}
		//界桩刻号
		if (RegUtil.CheckParameter(data.get("lmJzkh"), null, null, false)) {
			resultMap.put("lmJzkh",data.get("lmJzkh"));
		}
		//所在地
		if (RegUtil.CheckParameter(data.get("lmSzd"), null, null, false)) {
			resultMap.put("lmSzd",data.get("lmSzd"));
		}
		
		String path = iLmMarkerService.exportLmMarkerInfor(resultMap, request, response);
		
		JSONObject dataObj = new JSONObject();
		dataObj.put("filePath", path);
		
		return Result.ok(dataObj);
	}
	
}

