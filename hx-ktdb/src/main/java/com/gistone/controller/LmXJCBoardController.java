package com.gistone.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器 标识标牌管理
 * </p>
 *
 * @author xjc
 * @since 2019-02-28
 */
@RestController
@RequestMapping("/api/ktdb/lmBoard")
public class LmXJCBoardController {
/*
	@Autowired
	private ILmXJCBoardService iLmBoardService;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "getLmBoardList", method = RequestMethod.POST)
	public Object getLmBoardList(@RequestBody Map<String, Object> requestData,HttpServletRequest request,HttpServletResponse response) {
	
		//请求参数格式校验
		Object dataParam = requestData.get("data");
		if (dataParam == null) {
			return Result.build(ResultEnum.PARAMETEREMPTY, "请求数据data不能为空！");
		}
		
		Map<String, Object> data = (Map<String, Object>) requestData.get("data");
		Map<String, Object> resultMap = new HashMap<>();
		//获取前端传递的参数
		//红线台账名称
		if (RegUtil.CheckParameter(data.get("srldName"), null, null, false)) {
			resultMap.put("srldName",data.get("srldName"));
		}
		//红线台账编码
		if (RegUtil.CheckParameter(data.get("srldcode"), null, null, false)) {
			resultMap.put("srldcode",data.get("srldcode"));
		}
		//标牌名称
		if (RegUtil.CheckParameter(data.get("lbName"), null, null, false)) {
			resultMap.put("lbName",data.get("lbName").toString());
		}
		//每页条数
		if (RegUtil.CheckParameter(data.get("pageSize"), null, null, false)) {
			resultMap.put("pageSize",Integer.parseInt(data.get("pageSize").toString()));
		}
		//开始索引
		if (RegUtil.CheckParameter(data.get("pageNumber"), null, null, false)) {
			resultMap.put("pageNumber",Integer.parseInt(data.get("pageNumber").toString()));
		}
		
		Map<String,Object> map = iLmBoardService.seleteLmBoardList(resultMap);
		
		return Result.success(map);
	}
	
	*//**
	 * 图片上传
	 * @param files
	 * @param request
	 * @param response
	 * @return
	 *//*
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public Object upload(@RequestParam(value="file",required=false) MultipartFile files,HttpServletRequest request, HttpServletResponse response) {
		
		String path = iLmBoardService.upload(files);
		
		return Result.success(path);
	}
	
	*//**
	 * 保存标识标牌
	 * @param requestData
	 * @param request
	 * @param response
	 * @return
	 *//*
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Object save(@RequestBody Map<String, Object> requestData,HttpServletRequest request,HttpServletResponse response) {
		
		//请求参数格式校验
		Object dataParam = requestData.get("data");
		if (dataParam == null) {
			return Result.build(ResultEnum.PARAMETEREMPTY, "请求数据data不能为空！");
		}
		
		//根据token得到用户信息
		String user = redisTemplate.opsForValue().get(requestData.get("accessToken"));
				
		JSONObject jsStr = JSONObject.fromObject(user); 
		LmXJCBoard lm = new LmXJCBoard();
		String picturePath = "";
		Map<String, Object> data = (Map<String, Object>) requestData.get("data");
		//红线id
		if(RegUtil.CheckParameter(data.get("lbSrldId"), null, null, false)) {
			lm.setLbSrldId(Integer.parseInt(data.get("lbSrldId").toString()));
		}
		//标牌名称
		if(RegUtil.CheckParameter(data.get("lbName"), null, null, false)) {
			lm.setLbName(data.get("lbName").toString());
		}
		//编号
		if(RegUtil.CheckParameter(data.get("lbCode"), null, null, false)) {
			lm.setLbCode(data.get("lbCode").toString());
		}
		//平面坐标x
		if(RegUtil.CheckParameter(data.get("lbX"), null, null, false)) {
			//return Result.build(ResultEnum.PARAMETEREMPTY, "平面坐标x不能为空");
			lm.setLbX(Double.parseDouble(data.get("lbX").toString()));
		}
		//平面坐标y
		if(RegUtil.CheckParameter(data.get("lbY"), null, null, false)) {
			//return Result.build(ResultEnum.PARAMETEREMPTY, "平面坐标y不能为空");
			lm.setLbY(Double.parseDouble(data.get("lbY").toString()));
		}
		//经度
		if(RegUtil.CheckParameter(data.get("lbLon"), null, null, false)) {
			//return Result.build(ResultEnum.PARAMETEREMPTY, "经度不能为空");
			lm.setLbLon(Double.parseDouble(data.get("lbLon").toString()));
		}
		//纬度
		if(RegUtil.CheckParameter(data.get("lbLat"), null, null, false)) {
			//return Result.build(ResultEnum.PARAMETEREMPTY, "纬度不能为空");
			lm.setLbLat(Double.parseDouble(data.get("lbLat").toString()));
		}
		//主管部门
		if(RegUtil.CheckParameter(data.get("lbZgbm"), null, null, false)) {
			lm.setLbZgbm(data.get("lbZgbm").toString());
		}
		//监督部门
		if(RegUtil.CheckParameter(data.get("lbJdbm"), null, null, false)) {
			lm.setLbJdbm(data.get("lbJdbm").toString());
		}
		//联系电话
		if(RegUtil.CheckParameter(data.get("lbPhone"), null, null, false)) {
			lm.setLbPhone(data.get("lbPhone").toString());
		}
		//标识牌树立时间
		if(RegUtil.CheckParameter(data.get("lbSettime"), null, null, false)) {
			lm.setLbSettime(DateUtils.StrtoDateYMD(data.get("lbSettime").toString()));
		}
		//图片路径
		if(RegUtil.CheckParameter(data.get("picturePath"), null, null, false)) {
			picturePath = data.get("picturePath").toString();
		}else {
			picturePath = null;
		}
		Map<String,Object> map = (Map<String, Object>) JSONObject.toBean(jsStr); 
		//新增用户id
		lm.setLbAddUid(Integer.parseInt(map.get("id").toString()));
		//新增时间
		lm.setLbAddTime(new Date());
		
		Map<String, Object> resultMap = iLmBoardService.save(lm, picturePath);
		
		return Result.build(resultMap.get("code").toString(), resultMap.get("msg").toString());
	}
	
	*//**
	 * 修改标识标牌
	 * @param requestData
	 * @param request
	 * @param response
	 * @return
	 *//*
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public Object update(@RequestBody Map<String, Object> requestData,HttpServletRequest request,HttpServletResponse response) {
		
		//请求参数格式校验
		Object dataParam = requestData.get("data");
		if (dataParam == null) {
			return Result.build(ResultEnum.PARAMETEREMPTY, "请求数据data不能为空！");
		}
		
		//根据token得到用户信息
		*//*String user = redisTemplate.opsForValue().get(requestData.get("accessToken"));
				
		JSONObject jsStr = JSONObject.fromObject(user); *//*
		LmXJCBoard lm = new LmXJCBoard();
		String picturePath = "";
		Map<String, Object> data = (Map<String, Object>) requestData.get("data");
		//主键id
		if(RegUtil.CheckParameter(data.get("lbId"), null, null, false)) {
			lm.setLbId(Integer.parseInt(data.get("lbId").toString()));
		}
		//红线id
		if(RegUtil.CheckParameter(data.get("lbSrldId"), null, null, false)) {
			lm.setLbSrldId(Integer.parseInt(data.get("lbSrldId").toString()));
		}
		//标牌名称
		if(RegUtil.CheckParameter(data.get("lbName"), null, null, false)) {
			lm.setLbName(data.get("lbName").toString());
		}
		//编号
		if(RegUtil.CheckParameter(data.get("lbCode"), null, null, false)) {
			lm.setLbCode(data.get("lbCode").toString());
		}
		//平面坐标x
		if(RegUtil.CheckParameter(data.get("lbX"), null, null, false)) {
			//return Result.build(ResultEnum.PARAMETEREMPTY, "平面坐标x不能为空");
			lm.setLbX(Double.parseDouble(data.get("lbX").toString()));
		}
		//平面坐标y
		if(RegUtil.CheckParameter(data.get("lbY"), null, null, false)) {
			//return Result.build(ResultEnum.PARAMETEREMPTY, "平面坐标y不能为空");
			lm.setLbY(Double.parseDouble(data.get("lbY").toString()));
		}
		//经度
		if(RegUtil.CheckParameter(data.get("lbLon"), null, null, false)) {
			//return Result.build(ResultEnum.PARAMETEREMPTY, "经度不能为空");
			lm.setLbLon(Double.parseDouble(data.get("lbLon").toString()));
		}
		//纬度
		if(RegUtil.CheckParameter(data.get("lbLat"), null, null, false)) {
			//return Result.build(ResultEnum.PARAMETEREMPTY, "纬度不能为空");
			lm.setLbLat(Double.parseDouble(data.get("lbLat").toString()));
		}
		//主管部门
		if(RegUtil.CheckParameter(data.get("lbZgbm"), null, null, false)) {
			lm.setLbZgbm(data.get("lbZgbm").toString());
		}
		//监督部门
		if(RegUtil.CheckParameter(data.get("lbJdbm"), null, null, false)) {
			lm.setLbJdbm(data.get("lbJdbm").toString());
		}
		//联系电话
		if(RegUtil.CheckParameter(data.get("lbPhone"), null, null, false)) {
			lm.setLbPhone(data.get("lbPhone").toString());
		}
		//标识牌树立时间
		if(RegUtil.CheckParameter(data.get("lbSettime"), null, null, false)) {
			lm.setLbSettime(DateUtils.StrtoDateYMD(data.get("lbSettime").toString()));
		}
		//图片路径
		if(RegUtil.CheckParameter(data.get("picturePath"), null, null, false)) {
			picturePath = data.get("picturePath").toString();
		}else {
			picturePath = null;
		}
		//Map<String,Object> map = (Map<String, Object>) JSONObject.toBean(jsStr); 
		//新增用户id
		//lm.setLbUpdUid(Integer.parseInt(map.get("id").toString()));
		lm.setLbUpdUid(8);
		//新增时间
		lm.setLbUpdTime(new Date());
		
		Map<String, Object> resultMap = iLmBoardService.update(lm, picturePath);
		
		return Result.build(resultMap.get("code").toString(), resultMap.get("msg").toString());
	}
	
	*//**
	 * 删除标识标牌
	 * @param requestData
	 * @param request
	 * @param response
	 * @return
	 *//*
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public Object delete(@RequestBody Map<String, Object> requestData,HttpServletRequest request,HttpServletResponse response) {
		
		//请求参数格式校验
		Object dataParam = requestData.get("data");
		if (dataParam == null) {
			return Result.build(ResultEnum.PARAMETEREMPTY, "请求数据data不能为空！");
		}
		
		Map<String, Object> data = (Map<String, Object>) requestData.get("data");
		//主键id
		String lbId = null;
		if(RegUtil.CheckParameter(data.get("lbId"), null, null, false)) {
			lbId = data.get("lbId").toString();
		}
		
		boolean flag = iLmBoardService.delete(lbId);
		if(flag) {
			return Result.build(ResultEnum.SUCCESS, "删除成功！！");
		}else {
			return Result.build(ResultEnum.ERROR, "删除失败！！");
		}
	}
	
	*//**
	 * 标识标牌导出
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *//*
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "exportLmBoardInfor", method = RequestMethod.POST)
	public Object exportLmBoardInfor(@RequestBody Map<String, Object> requestData, HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		//请求参数格式校验
		Object dataParam = requestData.get("data");
		if (dataParam == null) {
			return Result.build(ResultEnum.PARAMETEREMPTY, "请求数据data不能为空！");
		}
		Map<String, Object> data = (Map<String, Object>) requestData.get("data");
		Map<String, Object> resultMap = new HashMap<>();
		//红线台账名称
		if (RegUtil.CheckParameter(data.get("srldName"), null, null, false)) {
			resultMap.put("srldName",data.get("srldName"));
		}
		//红线台账编码
		if (RegUtil.CheckParameter(data.get("srldcode"), null, null, false)) {
			resultMap.put("srldcode",data.get("srldcode"));
		}
		//标牌名称
		if (RegUtil.CheckParameter(data.get("lbName"), null, null, false)) {
			resultMap.put("lbName",data.get("lbName").toString());
		}
		
		String path = iLmBoardService.exportLmBoardInfor(resultMap, request, response);
		
		JSONObject dataObj = new JSONObject();
		dataObj.put("filePath", path);
		
		return Result.success(dataObj);
	}*/
	
}

