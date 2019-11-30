package com.gistone.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.VO.ResultVO;
import com.gistone.annotation.PassToken;
import com.gistone.entity.*;
import com.gistone.pkname.Swagger;
import com.gistone.service.*;
import com.gistone.swagger.StaticSwagger;
import com.gistone.util.Result;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Api(value = "(安徽用)统计分析模块",tags = "统计分析模块")
@RestController
@RequestMapping("/api/statis")
public class StatisController {
	@Autowired
	private StatisService statisService;
	@Autowired
	private ISt4ScsCkService st4ScsCkservice;
	@Autowired
	private ISt4ScsCdService st4ScsCdservice;
	@Autowired
	private ISt4ScsCbdService iSt4ScsCbdService;
	@Autowired
	private IDirTempService iDirTempService;

	@ApiOperation(value = "(安徽用)临时文件夹存储", notes = "", response = Result.class)
	@PostMapping("/insertDirTemp")
	public ResultVO insertDirTemp(@RequestBody Swagger<DirTemp> requestData, HttpServletRequest request, HttpServletResponse response){
		DirTemp dt = requestData.getData();
		if(iDirTempService.save(dt)){
			return ResultVOUtil.success();
		}
		return ResultVOUtil.success();

	}


	@ApiOperation(value = "(安徽用)统计核查审核的质量评估导出", notes = "", response = Result.class)
	@PostMapping("/examineQualityExport")
	public ResultVO examineQualityExport(@RequestBody Swagger<St4ScsCl> requestData, HttpServletRequest request, HttpServletResponse response){
		St4ScsCl cl = requestData.getData();
		return statisService.examineQualityExport(cl,response);
	}

	@ApiOperation(value = "(安徽用)统计核查审核的质量评估", notes = "", response = Result.class)
	@PostMapping("/examineQuality")
	public ResultVO examineQuality(@RequestBody Swagger<RlhdGroup> requestData, HttpServletRequest request, HttpServletResponse response){
		return st4ScsCkservice.examineQuality();
	}
	@ApiOperation(value = "(安徽用)人类活动巡查结果质量评估导出", notes = "", response = Result.class)
	@PostMapping("/pointQualityExport")
	public ResultVO pointQualityExport(@RequestBody Swagger<RlhdGroup> requestData, HttpServletResponse response){
		RlhdGroup cl = requestData.getData();
		return statisService.pointQualityExport(cl,response);
	}

	@ApiOperation(value = "(安徽用)人类活动巡查结果质量评估得传递所属监管台账的id", notes = "", response = Result.class)
	@PostMapping("/pointQuality")
	public ResultVO pointQuality(@RequestBody Swagger<RlhdGroup> requestData, HttpServletRequest request, HttpServletResponse response){
		RlhdGroup rg = requestData.getData();
		return st4ScsCdservice.pointQuality(rg);
	}

	@ApiOperation(value = "(安徽用)核查统计分析处展示问题斑块任务名称cl002任务年份:cl010,行政区划:adminRegionId", notes = "", response = Result.class)
	@PostMapping("/staticPoint")
	public ResultVO staticPoint(@RequestBody Swagger<St4ScsCl> requestData, HttpServletRequest request, HttpServletResponse response){
		return st4ScsCdservice.listStaticPoint(requestData.getData());
	}
	@ApiOperation(value = "(安徽用)首页进来的邮件提示列表展示前五条数据", notes = "", response = Result.class)
	@PostMapping("/listCheckMsg")
	public ResultVO listCheckMsg(@RequestBody Swagger<St4ScsCl> requestData, HttpServletRequest request, HttpServletResponse response){
		St4ScsCl cl = requestData.getData();
		QueryWrapper<St4ScsCbd> cbdQueryWrapper = new QueryWrapper<>();
		cbdQueryWrapper.orderByDesc("CBD003");
		cbdQueryWrapper.last("limit 5");

		return ResultVOUtil.success(ResultVOUtil.success(iSt4ScsCbdService.list(cbdQueryWrapper)));
	}
	/**
	 * 分页
	 * @param requestData
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "(安徽用)航迹数据统计接口只能传递uname其中groupByName默认传递sf,点击单位的时候传递sa015,sysCompany对象的comFPkid默认传递1)", notes = "巡查-统计列表", response = Result.class)
	@PostMapping("/listPatrol")
	public ResultVO listPatrol(@RequestBody Swagger<St4ScsCy> requestData, HttpServletRequest request, HttpServletResponse response){
		try {
			return statisService.listPatrol(requestData.getData());
		} catch (Exception e) {
			e.printStackTrace();
			return ResultVOUtil.error("1222","处理结果失败");
		}
	}
	@ApiOperation(value = "(安徽用)航迹数据按人员统计导出接口)", notes = "巡查-统计列表", response = Result.class)
	@PostMapping("/exportRecordStatic")
	public ResultVO exportRecordStatic(@RequestBody Swagger<St4ScsCy> requestData,  HttpServletResponse response){
		try {
			return statisService.exportRecordStatic(requestData.getData());
		} catch (Exception e) {
			e.printStackTrace();
			return ResultVOUtil.error("1222","处理结果失败");
		}
	}



	//-------------------------------航点数据统计-------------------------------




	/**
	 * 统计列表
	 * @param requestData
	 * @param request
	 * @param response
	 * @return
	 */
	/*@ApiOperation(value = "航点数据统计 monitorNum(巡护航点数)checkNum(核查航点数)lawNum(执法航点数)surveyNum(调查航点数)", notes = "航点数据统计", response = Result.class)
	@PostMapping("/listWaypoint")
	public Result listWaypoint(@RequestBody Swagger<St4ScsCc> requestData, HttpServletRequest request, HttpServletResponse response){
		try {
			return statisService.listWaypoint(requestData.getData());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.build(1006, "查询异常");
		}
	}
*/


	/**
	 * 分页
	 * @param requestData
	 * @param request
	 * @param response
	 * @return
	 */
	/*@ApiOperation(value = "航点-分页", notes = "参数(人员外键、保护地外键、行政区外键)", response = Result.class)
	@PostMapping("/listWaypointFY")
	public Result listWaypointFY(@RequestBody Swagger<St4ScsCc> requestData, HttpServletRequest request, HttpServletResponse response){
		try {
			if(null==requestData.getData().getPageNumber())
				return	Result.build(1001, "PageNumber当前页异常");
			if(null==requestData.getData().getPageSize())
				return	Result.build(1001,"pageSize显示条数异常");

			return statisService.listWaypointFY(requestData.getData());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.build(1006, "查询异常");
		}
	}*/










	//-------------------------------台账记录统计-------------------------------

	/**
	 * 台账记录-统计列表
	 * @param requestData
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "(安徽用)问题斑块记录统计接口年份查询传递参数名称taskYear(年份)返回的参数是 sd001(行政区)  ck010(活动设施名称) ck067(审核状态0未审核1已审核2审核不通过)", notes = "台账记录统计接口", response = Result.class)
	@PostMapping("/listLedger")
	@PassToken
	public ResultVO listLedger(@RequestBody Swagger<St4ScsCk> requestData, HttpServletRequest request, HttpServletResponse response){
		try {
			return statisService.listLedger(requestData.getData());
		} catch (Exception e) {
			e.printStackTrace();
			return ResultVOUtil.error(ResultEnum.HANDLEFAIL.getCode(), "服务器处理失败！");
		}
	}
	/**
	 *
	 * @param
	 * @param
	 * @return
	 */
	@ApiOperation(value="问题点位统计（问题点总数、已核查数、未核查数、当日核查数、新增问题点数量；默认按行政区分组groupByName和之前的巡查数据分析统计传递的规则一样）",notes = "",response = St4ScsCd.class)
	@RequestMapping(value="/pointStatistics",method = RequestMethod.POST)
	public ResultVO pointStatistics(@RequestBody  @ApiParam(name="航点记录列表管理列表查看详情", value="json格式", required=true)
										  Swagger<StaticSwagger> ssSwagger, HttpServletRequest request, HttpServletResponse response) {
		StaticSwagger ss = ssSwagger.getData();
		return statisService.pointStatistics(ss);
	}
	//-------------------------------台账整改统计-------------------------------

	/**
	 * 台账整改-统计列表
	 * @param requestData
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "台账整改-统计列表", notes = "保护区主键sg004,行政区主键ck002，必须传其中之一", response = Result.class)
	@PostMapping("/listLedgerzg")
	public Result listLedgerzg(@RequestBody Swagger<St4ScsCd> requestData, HttpServletRequest request, HttpServletResponse response){
		try {

			return statisService.listLedgerzg(requestData.getData());
		} catch (Exception e){
			e.printStackTrace();
			return Result.build(1006, "查询异常");
		}
	}



	/**
	 * 台账记录、整改-通用分页
	 * @param requestData
	 * @param request
	 * @param response
	 * @return
	 */
	/*@ApiOperation(value = "台账记录、台账整改-通用分页", notes = "查询参数(保护区外键、行政区外键)", response = Result.class)
	@PostMapping("/listLedgerFY")
	public Result listLedgerFY(@RequestBody Swagger<St4ScsCk> requestData, HttpServletRequest request, HttpServletResponse response){
		try {
			if(null==requestData.getData().getPageNumber())
				return	Result.build(1001, "PageNumber当前页异常");
			if(null==requestData.getData().getPageSize())
				return	Result.build(1001,"pageSize显示条数异常");

			return statisService.listLedgerFY(requestData.getData());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.build(1006, "查询异常");
		}
	}*/
	/**
	 * 动物数据统计
	 * @param requestData
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "动物数据统计 植物名称animalName 植物出现次数animalNum", notes = "动物数据统计", response = Result.class)
	@PostMapping("/statisDw")
	public Result statisDw(@RequestBody Swagger<St4ScsCc> requestData, HttpServletRequest request, HttpServletResponse response){
		try {
			return statisService.statisDw(requestData.getData());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.build(1006, "查询异常");
		}
	}
	/**
	 * 植物数据统计
	 * @param requestData
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "植物数据统计 植物名称plantsName 植物出现次数plantsNum", notes = "植物数据统计", response = Result.class)
	@PostMapping("/statisZw")
	public Result statisZw(@RequestBody Swagger<St4ScsCc> requestData, HttpServletRequest request, HttpServletResponse response){
		try {
			return statisService.statisZw(requestData.getData());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.build(1006, "查询异常");
		}
	}





}

