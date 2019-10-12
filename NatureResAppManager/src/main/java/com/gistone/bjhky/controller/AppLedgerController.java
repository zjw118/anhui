package com.gistone.bjhky.controller;

import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.bjhky.SawggerModel.SysData;
import com.gistone.bjhky.annotation.PassToken;
import com.gistone.bjhky.entity.*;
import com.gistone.bjhky.mapper.St4PoCdSaMapper;
import com.gistone.bjhky.mapper.St4PoSaCzMapper;
import com.gistone.bjhky.mapper.St4ScsCkMapper;
import com.gistone.bjhky.mapper.St4SysSaMapper;
import com.gistone.bjhky.pkname.Swagger;
import com.gistone.bjhky.service.*;
import com.gistone.bjhky.util.ObjectUtils;
import com.gistone.bjhky.util.Result;
import com.gistone.bjhky.util.ResultMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author LiuXiong
 * @since 2019-08-06
 * app接口台账相关
 */
@RestController
@RequestMapping("/api/app/ledger")
@Api(value="app台账接口",tags = "app台账接口的的根目录")
public class AppLedgerController {

    @Autowired
    private ISt4SysSaService userService;

    @Autowired
    private ISt4PoCdSaService checkUserRelavantService;

    @Autowired
    private ISt4PoSaClService st4PoSaClService;

    @Autowired
    private ISt4ScsCkService st4ScsCkService;

    @Autowired
    private ISt4ScsCiService warnService;

    @Autowired
    private ISt4ScsCdService st4ScsCdService;

    @Autowired
    private ISt4ScsCnService st4ScsCnService;

    @Autowired
    private ISt4ScsCeService st4ScsCeService;

    @Autowired
    private St4PoSaCzMapper st4PoSaCzMapper;

    @Autowired
    private St4PoCdSaMapper st4PoCdSaMapper;

    @Autowired
    private St4ScsCkMapper st4ScsCkMapper;

    @Autowired
    private St4SysSaMapper st4SysSaMapper;
    @Autowired
    private ISt4ScsCadService st4ScsCadService;
    /**
     * app提交上来的的预警信息
     * @param data
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value="app提交上来的的预警信息",notes = "app提交上来的的预警信息",response = St4ScsCi.class)
    @RequestMapping(value = "/appWarn",method = RequestMethod.POST)
    public Result appWarn(@ApiParam(name="app提交上来的的预警信息", value="json格式", required=true)@RequestBody Swagger<St4ScsCi> data,
                              HttpServletRequest request, HttpServletResponse response) {

        St4ScsCi ci = data.getData();
        if(ci.getCi002()==null){
            return Result.build(1006,"预警类型"+ResultMsg.MSG_1001);
        }
        if(ci.getCi003()==null){
            return Result.build(1006,"预警名称"+ ResultMsg.MSG_1001);
        }
        if(warnService.save(ci)){
            return Result.build(1000,"预警"+ResultMsg.MSG_1000);
        }
        return Result.build(1006,"预警"+ResultMsg.MSG_1006);
    }

    /**
     * 同步问题点数据
     * @param data
     * @param request
     * @param response
     * @return
     */
    @PassToken
    @ApiOperation(value="同步问题点数据",notes = "同步问题点数据",response = St4SysSa.class)
    @RequestMapping(value = "/sysPointData",method = RequestMethod.POST)
    public Result sysPointData(@ApiParam(name="同步问题点数据", value="json格式", required=true)@RequestBody Swagger<St4SysSa> data,
                               HttpServletRequest request, HttpServletResponse response) {
        //TODO 传递过来巡护人员的id
        //从redis或者session里面取出来roleId
        St4SysSa param = data.getData();
        if(param ==null || param.getSa001() == null){
            return Result.build(1006,"用户id"+ResultMsg.MSG_1001);
        }

        Integer uid = param.getSa001();
        Integer roleId = 1;
       return st4ScsCkService.sysPointData(roleId,uid);
//        return Result.build(1005, ResultMsg.MSG_1005);
    }
    /**
     * 同步问题点数据(绿盾巡查暂时使用)
     * @param data
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value="绿盾同步问题点数据 st4PoCdSaList里面 sa001:巡护人ID cdsa001：点位台账状态0待确认1已核查 sa019:巡查员姓名 ",notes = "绿盾同步问题点数据",response = St4SysSa.class)
    @RequestMapping(value = "/sysPointDataLd",method = RequestMethod.POST)
    public Result sysPointDataLd(@ApiParam(name="绿盾同步问题点数据", value="json格式", required=true)@RequestBody Swagger<St4SysSa> data,
                               HttpServletRequest request, HttpServletResponse response) {
        //TODO 传递过来巡护人员的id
        //从redis或者session里面取出来roleId
        St4SysSa param = data.getData();
        if(param ==null || param.getSa001() == null){
            return Result.build(1006,"用户id"+ResultMsg.MSG_1001);
        }

        Integer uid = param.getSa001();
        Integer roleId = null;
       return st4ScsCdService.sysPointDataLd(roleId,uid);
    }
    /**
     * （绿盾使用）台账表提交接口
     * @param
     * @return
     */
    @ApiOperation(value = "（绿盾使用）台账表提交接口", notes = "（绿盾使用）台账表提交接口", response = St4ScsCd.class)
    @PostMapping("/insertLedgerLd")
    public Result insertLedgerLd( @RequestBody @ApiParam(name = "（绿盾使用）台账表提交接口", value = "json格式", required = true)  Swagger<St4ScsCd>
                                              data,HttpServletRequest request
    ) {

        St4ScsCd param = data.getData();
        //问题点类型
        if(!ObjectUtils.isNotNullAndEmpty(param.getCd007())){
            return Result.build(1001,"问题点类型cd007"+ResultMsg.MSG_1001);
        }
        //问题点编号
        if(!ObjectUtils.isNotNullAndEmpty(param.getCd004())){
            return Result.build(1001,"问题点编号cd004"+ResultMsg.MSG_1001);
        }
        if(!ObjectUtils.isNotNullAndEmpty(param.getCd002())){
            return Result.build(1001,"经度cd002"+ResultMsg.MSG_1001);
        }
        if(!ObjectUtils.isNotNullAndEmpty(param.getCd003())){
            return Result.build(1001,"纬度cd003"+ResultMsg.MSG_1001);
        }
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId = JWT.decode(token).getAudience().get(0);
        Result res = st4ScsCkService.insertLedgerLd(param,userId);

        return res;

    }
    /**
     * 同步任务批次数据
     * @param data
     * @param request
     * @param response
     * @return
     */
    @PassToken
    @ApiOperation(value="同步任务批次数据",notes = "同步问题点数据",response = St4ScsCl.class)
    @RequestMapping(value = "/sysTaskData",method = RequestMethod.POST)
    public Result sysTaskData(@ApiParam(name="同步问题点数据", value="json格式", required=true)@RequestBody Swagger<SysData> data,
                               HttpServletRequest request, HttpServletResponse response) {
        //TODO 传递过来巡护人员的id

        SysData cr = data.getData();
        Integer uid = cr.getUid();

        return st4PoSaClService.sysTaskData(uid);
        //return Result.build(1005,ResultMsg.MSG_1005);
    }
    @PassToken
    @ApiOperation(value="消息列表(app肯定用，后台管理应该也用得到，所以写在这里isApp参数比传递app访问传递布尔值true，pc端访问传递false)",notes = "消息列表(app肯定用，后台管理应该也用得到，所以写在这里)",response = St4ScsCad.class)
    @RequestMapping(value="/appMessageList",method = RequestMethod.POST)
    public Result appMessageList(@RequestBody  @ApiParam(name="消息列表", value="json格式", required=true)
                                         Swagger<St4ScsCad> messageSwagger) {
        St4ScsCad cad = messageSwagger.getData();
        if(cad.getIsApp()==null){
            return Result.build(1001,"isApp"+ResultMsg.MSG_1001);
        }
        if(cad.getIsApp()==1){
            if(cad.getCad007()==null){
                return Result.build(1001,"推送人的ID"+ResultMsg.MSG_1001);
            }
            if(!ObjectUtils.isNotNullAndEmpty(cad.getPageNumber())||!ObjectUtils.isNotNullAndEmpty(cad.getPageSize())){
                return Result.build(1001,"pageNumber和pageSize"+ResultMsg.MSG_1001);
            }
        }else if(cad.getIsApp()==0){
            if(!ObjectUtils.isNotNullAndEmpty(cad.getPageNumber())||!ObjectUtils.isNotNullAndEmpty(cad.getPageSize())){
                return Result.build(1001,"pageNumber和pageSize"+ResultMsg.MSG_1001);
            }
        }else {
            return Result.build(1005,ResultMsg.MSG_1005);
        }

        return  st4ScsCadService.getMessageData(cad);
    }
    @PassToken
    @ApiOperation(value="设置消息已读",notes = "设置消息已读",response = St4ScsCad.class)
    @RequestMapping(value="/setReadMsg",method = RequestMethod.POST)
    public Result setReadMsg(@RequestBody  @ApiParam(name="设置消息已读", value="json格式", required=true)
                                         Swagger<St4ScsCad> messageSwagger) {
        St4ScsCad cad = messageSwagger.getData();
        if(cad.getCad001()==null){
            return Result.build(1001,"消息主键cad001"+ResultMsg.MSG_1001);
        }
        cad.setCad004(1);
        if(st4ScsCadService.updateById(cad)){
            return Result.build(1000,"设置"+ResultMsg.MSG_1000);
        }else {
            return Result.build(1003,ResultMsg.MSG_1003);
        }


    }
    @PassToken
    @ApiOperation(value="获取未读消息数目",notes = "获取未读消息数目",response = St4ScsCad.class)
    @RequestMapping(value="/getUnReadNum",method = RequestMethod.POST)
    public Result getUnReadNum(@RequestBody  @ApiParam(name="获取未读消息数目", value="json格式", required=true)
                                       Swagger<St4ScsCad> messageSwagger) {
        St4ScsCad cad = messageSwagger.getData();
        if(cad.getCad007()==null){
            return Result.build(1001,"推送人的ID"+ResultMsg.MSG_1001);
        }
        QueryWrapper<St4ScsCad> wrapper = new QueryWrapper<>();
        wrapper.eq("CAD004",0);
        wrapper.eq("CAD008",1);
        wrapper.eq("CAD007",cad.getCad007());
        Result result = new Result();
        result.setStatus(1000);
        result.setMsg("加载成功");
        result.setData(st4ScsCadService.list(wrapper).size());
        return result;

    }
}
