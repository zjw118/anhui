package com.gistone.controller;

import com.gistone.VO.ResultVO;
import com.gistone.annotation.PassToken;
import com.gistone.entity.St4ScsCc;
import com.gistone.entity.St4ScsCd;
import com.gistone.entity.St4ScsCk;
import com.gistone.pkname.Swagger;
import com.gistone.service.ISt4ScsCkService;
import com.gistone.service.ISt4ScsClService;
import com.gistone.service.StatisService;
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

	/**
	 * 分页
	 * @param requestData
	 * @param request
	 * @param response
	 * @return
	 */
	/*@ApiOperation(value = "巡查-分页", notes = "条件参数(人员外键)", response = Result.class)
	@PostMapping("/listPatrolBySA001")
	public Result listPatrolBySA001(@RequestBody Swagger<St4ScsCy> requestData, HttpServletRequest request, HttpServletResponse response){
		try {
			if(null==requestData.getData().getSa001())
				return Result.build(1001, "sa001参数不能为空");
			if(null==requestData.getData().getPageNumber())
				return	Result.build(1001, "PageNumber当前页异常");
			if(null==requestData.getData().getPageSize())
				return	Result.build(1001,"pageSize显示条数异常");
			return statisService.listPatrolBySA001(requestData.getData());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.build(1006, "查询异常");
		}
	}
*/


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
	@ApiOperation(value = "(安徽用)台账记录统计接口年份查询传递参数名称taskYear(年份)返回的参数是 sd001(行政区)  ck010(活动设施名称) ck012(活动设施现状) cn010(整改进展)", notes = "台账记录统计接口", response = Result.class)
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

