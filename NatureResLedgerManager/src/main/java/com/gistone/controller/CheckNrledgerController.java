package com.gistone.controller;


import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gistone.VO.ResultVO;
import com.gistone.annotation.PassToken;
import com.gistone.entity.*;
import com.gistone.pkname.Swagger;
import com.gistone.service.*;
import com.gistone.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhaojingwei
 * @since 2019-07-26
 */
@Api(value="台账接口",tags = "台账相关的接口")
@RestController
@RequestMapping("/api/ledger")
public class CheckNrledgerController {


    @Autowired
    private ISt4ScsCkService icheckLedgerService;

    @Autowired
    private ISt4ScsCkrlService iSt4ScsCkrlService;
    @Autowired
    private ISt4ScsCiService st4ScsCiService;
    @Autowired
    private ISt4ScsClService st4ScsClService;

    @Autowired
    private ISt4ScsCcService iSt4ScsCcService;
    @Autowired
    private ISt4ScsChService iSt4ScsChService;


    @Autowired
    private ISt4PoCdSaService checkUserRelavantService;

    @Autowired
    private ISt4PoSaClService st4PoSaClService;
    @Autowired
    private ISt4ScsCiService warnService;
    @Autowired
    private  ISt4ScsCyService st4ScsCyService;
    @Autowired
    private ISt4SysSgService st4SysSgService;

    @Autowired
    private ISt4ScsCcService st4ScsCcService;

    @Autowired
    private ISt4ScsCfService st4ScsCfService;
    @Autowired
    private ISt4ScsCadService st4ScsCadService;



    /**
     * 台账表的插入2产品化
     * @param checkLedger
     * @param request
     * @return
     */
    @ApiOperation(value="台账字段动态配置接口",notes = "台账插入2",response = St4ScsCk.class)
    @RequestMapping(value="/doSelfStage",method = RequestMethod.POST)
    public Result doSelfStage(@RequestBody @ApiParam(name="台账信息", value="json格式", required=true)
                                          Swagger<St4ScsCk> checkLedger,
                              HttpServletRequest request) {
        return null;

    }




    /**
     * 台账删除2
     * @param request
     * @return
     */
    /*@ApiOperation(value="台账删除2",notes = "台账删除2",response = St4ScsCk.class)
    @RequestMapping(value="/deleteStage2",method = RequestMethod.POST)
    public Result deleteLedger2(@RequestBody @ApiParam(name="台账删除2", value="json格式", required=true) CheckLedger checkLedger,
                                HttpServletRequest request,
                                HttpServletResponse response) {
       *//* if(checkLedger.getClId()<1){
            return  Result.build(1001,"主键clId"+ ResultMsg.MSG_1001);
        }
        return  icheckLedgerService.deleteLedger(checkLedger.getClId());*//*
    }
*/
    @ApiOperation(value="(安徽用)人类活动台账管理列表",notes = "人类活动台账管理列表人类活动台账管理列表",response = St4ScsCkrl.class)
    @RequestMapping(value="/listHumanStage",method = RequestMethod.POST)
    public Result listHkyStage(@Validated@RequestBody @ApiParam(name="人类活动台账管理列表pageSize和pageNumber必传其余的查询项是非必传", value="json格式", required=true)
                                     Swagger<St4ScsCkrl> swagger,BindingResult blindResult,
                             HttpServletRequest request) {
        if(blindResult.getErrorCount()>0){
            List<FieldError> errorFields = blindResult.getFieldErrors();
            for (FieldError fieldError:errorFields) {
                return Result.build(1001,fieldError.getDefaultMessage());
            }
        }
        St4ScsCkrl checkLedger = swagger.getData();


        /*if(!ObjectUtils.isNotNullAndEmpty(pageNumber)||!ObjectUtils.isNotNullAndEmpty(pageSize)){
            return  Result.build(1001,"pageNumber，pageSize"+ ResultMsg.MSG_1001);
        }*/
        return iSt4ScsCkrlService.listHumanStage(checkLedger);

    }

    /**
     * 台账列表
     * @param swagger
     * @param request
     * @return
     */
    @ApiOperation(value="(安徽用)台账列表(目前模糊查询只查询台账的name字段即data里面在st4ScsCo对象里传递name值)",notes = "台账列表",response = St4ScsCk.class)
    @RequestMapping(value="/listStage",method = RequestMethod.POST)
    public ResultVO ledgerList(@RequestBody @ApiParam(name="台账列表pageSize和pageNumber必传其余的查询项是非必传", value="json格式", required=true)
                                      Swagger<St4ScsCk> swagger,
                               HttpServletRequest request) {
        St4ScsCk checkLedger = swagger.getData();
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId ="1";// JWT.decode(token).getAudience().get(0);
        St4SysSa seUser = new St4SysSa();
        seUser.setSa001(Integer.valueOf(userId));
        Object pageNumber=checkLedger.getPageNumber();
        Object pageSize=checkLedger.getPageSize();
        if(!ObjectUtils.isNotNullAndEmpty(pageNumber)||!ObjectUtils.isNotNullAndEmpty(pageSize)){
            return ResultVOUtil.error(ResultEnum.HANDLEFAIL.getCode(), "pageNumber，pageSize不能为空");
        }
        return icheckLedgerService.listLedger(checkLedger,seUser);
    }
    /**
     * 台账空间分布列表
     * @param checkLedger
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value="台账空间分布列表",notes = "台账空间分布列表",response = St4ScsCk.class)
    @RequestMapping(value="/listStageSpace",method = RequestMethod.POST)
    public Result ledgerListSpace(@RequestBody @ApiParam(name="台账空间分布列表不必传pageNumber及pageSize后台也不返回page属性的值", value="json格式",
            required=true) St4ScsCk checkLedger,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        //TODO
        Integer roleId =1;
        return icheckLedgerService.listLedgerSpace(roleId,checkLedger);
    }




    /**
     * 台账详情
     * @param
     * @param
     * @param
     * @return
     */
    @ApiOperation(value="台账详情(ck088字段为1的代表是最新的台账，ck088为0代表是原始台账，原始台账一定有，最新台账不一定有，可拿这个值测试'LAL-009')",notes = "台账详情",response = St4ScsCk.class)
    @RequestMapping(value="/getStageDetail",method = RequestMethod.POST)
    public Result getStageDetail( @RequestBody @ApiParam(name="台账详情", value="json格式", required=true) Swagger<St4ScsCk>  checkLedger
                             ) {
        St4ScsCk ck = checkLedger.getData();
        if(ck.getCd004()==null){

            return  Result.build(1001,"问题点编号cd004"+ ResultMsg.MSG_1001);
        }

        return icheckLedgerService.getDetail(ck);
    }


    /**
     * 导入台账Excel
     * @param
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value="导入台账Excel",notes = "导入台账Excel",response = St4ScsCk.class)
    @RequestMapping(value="/importExcel",method = RequestMethod.POST)
    public Result importExcel(HttpServletRequest request,HttpServletResponse response) {
        Result result= new Result();
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        try{
            String userId =JWT.decode(token).getAudience().get(0);
            St4SysSa seUser = new St4SysSa();
            seUser.setSa001(Integer.valueOf(userId));
            if(ObjectUtils.isNotNullAndEmpty(request.getParameter("taskId"))){
                Integer taskId = Integer.valueOf(request.getParameter("taskId"));
                MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
                Map<String, MultipartFile> items = req.getFileMap();
                List<Integer> uidList = new ArrayList<>();
                return icheckLedgerService.importExcelCommon(items,seUser,taskId,uidList);
            }
        }catch (Exception e){
            result.setStatus(1000);
            result.setMsg("无token，请重新登录");
            e.printStackTrace();
        }

        return result;
    }



    @ApiOperation(value="分配批次任务",notes = "分配批次任务",response = St4ScsCl.class)
    @RequestMapping(value="/shareTask",method = RequestMethod.POST)
    public Result shareTask(@ApiParam(name="分配批次任务", value="json格式", required=true)@RequestBody Swagger<Task> task,
                            HttpServletRequest request, HttpServletResponse response) {
        //  Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        //ShareSwagger.PkData pkdata = shareSwagger.getData();
        //List<Map<Integer, List<String>>> list=pkdata.list;
        Task shareTask = task.getData();
        List<Integer> uidList = shareTask.getUidList();
        List<Integer> taskList = shareTask.getTaskList();
        return st4PoSaClService.insertList(uidList,taskList);
    }
    /**
     * 展示用户将要选择的字段名
     * @param
     * @param request
     * @param response
     * @return
     */
    /*@ApiOperation(value="展示用户将要选择的字段名",notes = "展示用户将要选择的字段名",response = CheckLedgerColumn.class)
    @RequestMapping(value="/showLedgerColumn",method = RequestMethod.POST)
    public Result showLedgerColumn(@RequestBody @ApiParam(name="展示用户将要选择的字段名", value="json格式", required=true)
                                           CheckLedgerColumn checkLedgerColumn,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {

        Result result = new Result();
        result.setStatus(1000);
        result.setMsg("加载成功");
        List<CheckLedgerColumn> list = checkLedgerColumnService.list();
        result.setRows(list);

        return result ;
    }*/


    /**
     * 存储用户选择的字段名
     * @param
     * @param request
     * @param response
     * @return
     */
    /*@ApiOperation(value="存储用户选择的字段名",notes = "存储用户选择的字段名",response = CheckLedgerSelect.class)
    @RequestMapping(value="/saveLedgerColumn",method = RequestMethod.POST)
    public Result acceptLedgerColumn(@RequestBody @ApiParam(name="存储用户选择的字段名", value="json格式", required=true)
                                             HttpServletRequest request,
                                     HttpServletResponse response) {
        //TODO 角色ID之后从session里面去获取
        Integer roleId = 1;
 *//*       CheckLedgerSelectSwagger.PkData pkData = checkLedgerSelectSwagger.getData();
        List<Integer> list = pkData.list;
        Integer clcId=0;
        String clcName="";
        String clsRemark=pkData.remark;
        List<CheckLedgerSelect> clseList = new ArrayList<>();
        CheckLedgerSelect cls = null;
        for (Integer cid : list) {
            cls=new CheckLedgerSelect();
            cls.setClsColumnId(cid);
            cls.setClsRoleId(roleId);
            cls.setClsRemark(clsRemark);
            clseList.add(cls);
        }
        return checkLedgerSelectService.insertList(clseList);*//*
        return null;
    }*/

    /**
     * 任务批次添加接口
     * @param checkTaskSwagger
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value="任务批次添加接口",notes = "任务批次添加接口",response = St4ScsCl.class)
    @RequestMapping(value="/insertTask",method = RequestMethod.POST)
    public Result insertTask(@RequestBody @ApiParam(name="任务批次添加接口", value="json格式", required=true)
                                     Swagger<St4ScsCl> checkTaskSwagger,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        St4SysSa seUser = new St4SysSa();
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId = JWT.decode(token).getAudience().get(0);
        seUser.setSa001(Integer.valueOf(userId));

        St4ScsCl ct = checkTaskSwagger.getData();
        ct.setCl014(LocalDateTime.now());
        ct.setCl013(seUser.getSa001());
        Result result = new Result();
        if(st4ScsClService.save(ct)){
            return Result.build(1000,"添加"+ResultMsg.MSG_1000);
        }
        return Result.build(1005,ResultMsg.MSG_1005);
    }

    /**
     * 任务批次修改接口
     * @param checkTaskSwagger
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value="任务批次修改接口",notes = "任务批次修改接口",response = St4ScsCl.class)
    @RequestMapping(value="/updateTask",method = RequestMethod.POST)
    public Result updateTask(@RequestBody @ApiParam(name="任务批次修改接口", value="json格式", required=true)
                                     Swagger<St4ScsCl> checkTaskSwagger,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        St4SysSa seUser = new St4SysSa();
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId = JWT.decode(token).getAudience().get(0);
        seUser.setSa001(Integer.valueOf(userId));
        St4ScsCl ct = checkTaskSwagger.getData();
        ct.setCl014(LocalDateTime.now());
        ct.setCl013(seUser.getSa001());
        Result result = new Result();
        if(st4ScsClService.updateById(ct)){
            return Result.build(1000,"修改"+ResultMsg.MSG_1000);
        }
        return Result.build(1005,ResultMsg.MSG_1005);
    }

    @ApiOperation(value="任务批次删除接口",notes = "任务批次删除接口",response = St4ScsCl.class)
    @RequestMapping(value="/deleteTask",method = RequestMethod.POST)
    public Result deleteTask(@RequestBody @ApiParam(name="任务批次删除接口", value="json格式", required=true)
                                     Swagger<St4ScsCl> checkTaskSwagger,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        St4SysSa seUser = new St4SysSa();
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId = JWT.decode(token).getAudience().get(0);
        seUser.setSa001(Integer.valueOf(userId));
        St4ScsCl ct = checkTaskSwagger.getData();
        ct.setCl012(0);
        Result result = new Result();
        if(st4ScsClService.updateById(ct)){
            return Result.build(1000,"删除"+ResultMsg.MSG_1000);
        }
        return Result.build(1005,ResultMsg.MSG_1005);
    }


    /**
     * 一张图展示，查询头部保护地、问题点、巡护次数、巡护里程数量
     * @param
     * @return
     */
    @ApiOperation(value="一张图展示，查询头部保护地、问题点、巡护次数、巡护里程数量",notes = "一张图展示，查询头部保护地、问题点、巡护次数、巡护里程数量",response = St4ScsCl.class)
    @RequestMapping(value="/selectReserveNumTiew",method = RequestMethod.POST)
    public Result selectReserveNumTiew( @RequestBody @Validated @ApiParam(name="保护地id", value="json格式", required=true)
                                    Swagger<St4SysSd> data, HttpServletRequest request, HttpServletResponse response) {
        St4SysSd st4SysSd = data.getData();
        return icheckLedgerService.selectReserveNumTiew(st4SysSd);
    }
    /**
     * 统计分析，物种分布统计
     * @param
     * @return
     */
    @ApiOperation(value="统计分析，物种分布",notes = "统计分析，物种分布,根据行政区划与保护地查询",response = St4ScsCl.class)
    @RequestMapping(value="/listSpeciesToMap",method = RequestMethod.POST)
    public Result listSpeciesToMap( @RequestBody @Validated @ApiParam(name="保护地id", value="json格式", required=true)
                                    Swagger<St4ScsCc> data, HttpServletRequest request, HttpServletResponse response) {
        St4ScsCc st4ScsCc = data.getData();
        return iSt4ScsCcService.listSpeciesToMap(st4ScsCc);
    }


    /**
     * 预警列表
     * @param checkTaskSwagger
     * @param request
     * @return
     */
    @ApiOperation(value="预警列表",notes = "预警列表",response = St4ScsCi.class)
    @RequestMapping(value="/listAppWarn",method = RequestMethod.POST)
    public Result listAppWarn( @RequestBody @Validated @ApiParam(name="任务批次列表接口", value="json格式", required=true)
                                    Swagger<St4ScsCi> checkTaskSwagger,
                            HttpServletRequest request) {

        St4ScsCi ci = checkTaskSwagger.getData();
        if(!ObjectUtils.isNotNullAndEmpty(ci.getPageNumber())||!ObjectUtils.isNotNullAndEmpty(ci.getPageNumber())){
            return Result.build(1001,"pageSize,pageNumber"+ResultMsg.MSG_1001);
        }
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId = JWT.decode(token).getAudience().get(0);
        St4SysSa seUser = new St4SysSa();
        seUser.setSa001(Integer.valueOf(userId));
        return warnService.listAppWarn(ci,seUser);
    }
    @ApiOperation(value="预警详情",notes = "预警详情",response = St4ScsCi.class)
    @RequestMapping(value="/getWarnDetail",method = RequestMethod.POST)
    public Result listAppWarn( @RequestBody  @ApiParam(name="任务批次列表接口", value="json格式", required=true)
                                       Swagger<St4ScsCi> data) {
        St4ScsCi ci = data.getData();
        if(ci.getCi001()==null){
            return  Result.build(1001,"预警主键"+ResultMsg.MSG_1001);
        }
        return st4ScsCiService.getWarnDetail(ci);

    }
    /**
     * 航迹列表
     * @param checkTaskSwagger
     * @param request
     * @return
     */
    @PassToken
    @ApiOperation(value="航迹列表",notes = "航迹列表",response = St4ScsCy.class)
    @RequestMapping(value="/listRecord",method = RequestMethod.POST)
    public Result listRecord( @RequestBody @Validated @ApiParam(name="航迹列表", value="json格式", required=true)
                                       Swagger<St4ScsCy> checkTaskSwagger,
                               HttpServletRequest request) {

        St4ScsCy ci = checkTaskSwagger.getData();
        if(!ObjectUtils.isNotNullAndEmpty(ci.getPageNumber())||!ObjectUtils.isNotNullAndEmpty(ci.getPageNumber())){
            return Result.build(1001,"pageSize,pageNumber"+ResultMsg.MSG_1001);
        }
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId = JWT.decode(token).getAudience().get(0);
        St4SysSa seUser = new St4SysSa();
        seUser.setSa001(Integer.valueOf(userId));
        return st4ScsCyService.listRecord(ci,seUser);
    }

    /**
     * 航迹详情
     * @param checkTaskSwagger
     * @param request
     * @param response
     * @return
     */
    @PassToken
    @ApiOperation(value="航迹详情",notes = "航迹详情",response = St4ScsCy.class)
    @RequestMapping(value="/getSailRecordDetail",method = RequestMethod.POST)
    public Result getSailRecordDetail( @RequestBody @Validated @ApiParam(name="航迹详情", value="json格式", required=true)
                                      Swagger<St4ScsCy> checkTaskSwagger,BindingResult blindResult,
                              HttpServletRequest request,
                              HttpServletResponse response) {

        St4ScsCy ci = checkTaskSwagger.getData();

        return st4ScsCyService.getSailRecordDetail(ci);
    }



    /**
     * 航点记录列表（一张图用）
     * @param checkTaskSwagger
     * @param request
     * @param response
     * @return
     */
    @PassToken
    @ApiOperation(value="航点记录列表（一张图用）",notes = "航点记录列表（一张图用）",response = St4ScsCc.class)
    @RequestMapping(value="/listSailRecord",method = RequestMethod.POST)
    public Result listSailRecord( @RequestBody @Validated @ApiParam(name="航点记录列表（一张图用）", value="json格式", required=true)
                                          Swagger<St4SysSg> checkTaskSwagger,BindingResult blindResult,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {

        St4SysSg ci = checkTaskSwagger.getData();

        if(!ObjectUtils.isNotNullAndEmpty(ci.getSg001())){
            return Result.build(1001,"sg001"+ResultMsg.MSG_1001);
        }
        return st4ScsCcService.getSailPointByReserveId(ci.getSg001());
    }
    /**
     * 航点记录列表管理列表
     * @param checkTaskSwagger
     * @param request
     * @return
     */
    @PassToken
    @ApiOperation(value="航点记录列表管理列表",notes = "航点记录列表管理列表",response = St4ScsCf.class)
    @RequestMapping(value="/listSailRecordForManager",method = RequestMethod.POST)
    public Result listSailRecordForManager( @RequestBody @Validated @ApiParam(name="航点记录列表管理列表", value="json格式", required=true)
                                          Swagger<St4ScsCc> checkTaskSwagger,HttpServletRequest request) {

        St4ScsCc cc = checkTaskSwagger.getData();

        if(!ObjectUtils.isNotNullAndEmpty(cc.getPageNumber())||!ObjectUtils.isNotNullAndEmpty(cc.getPageNumber())){
            return Result.build(1001,"pageSize,pageNumber"+ResultMsg.MSG_1001);
        }
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId = JWT.decode(token).getAudience().get(0);
        St4SysSa seUser = new St4SysSa();
        seUser.setSa001(Integer.valueOf(userId));

        return st4ScsCcService.listSt4ScsCcByPage(cc,seUser);
    }

    /**
     * 航点记录列表管理列表查看详情
     * @param checkTaskSwagger
     * @param request
     * @param response
     * @return
     */
    @PassToken
    @ApiOperation(value="航点记录列表管理列表查看详情(返回值cc003的值是0则展示st4Scscf包裹的内容，cc003的值是1则展示st4ScsCk包裹的内容)",notes = "航点记录列表管理列表查看详情",response = St4ScsCc.class)
    @RequestMapping(value="/getSailDetail",method = RequestMethod.POST)
    public Result getSailDetail( @RequestBody @Validated @ApiParam(name="航点记录列表管理列表查看详情", value="json格式", required=true)
                                                    Swagger<St4ScsCc> checkTaskSwagger,BindingResult blindResult,
                                            HttpServletRequest request,
                                            HttpServletResponse response) {

        St4ScsCc cc = checkTaskSwagger.getData();

        if(!ObjectUtils.isNotNullAndEmpty(cc.getCc001())){
            return Result.build(1001,"航点主键cc001"+ResultMsg.MSG_1001);
        }

        return st4ScsCcService.getDetailSailPoint(cc);
    }
    /**
     *设置重点台账
     * @param
     * @param
     * @return
     */
    @PassToken
    @ApiOperation(value="设置重点台账",notes = "设置重点台账",response = St4ScsCk.class)
    @RequestMapping(value="/setImportantStage",method = RequestMethod.POST)
    public Result setImportantStage(@RequestBody  @ApiParam(name="设置重点台账）", value="json格式", required=true)
                                           Swagger<St4ScsCk> ckSwagger,HttpServletRequest request) {
        /*St4ScsCk ck = ckSwagger.getData();
        if(ck.getCk001()==null){
            return Result.build(1001,"主键ck001"+ResultMsg.MSG_1001);
        }
        ck.setCk084(1);
        if(icheckLedgerService.updateById(ck)){
            return Result.build(1000,"设置"+ResultMsg.MSG_1000);
        }*/
        return Result.build(1003,ResultMsg.MSG_1003);


    }
    /**
     *设置销号台账
     * @param
     * @param
     * @return
     */
    @PassToken
    @ApiOperation(value="设置销号台账",notes = "设置销号台账",response = St4ScsCk.class)
    @RequestMapping(value="/setRemoveStage",method = RequestMethod.POST)
    public Result setRemoveStage(@RequestBody  @ApiParam(name="设置销号台账）", value="json格式", required=true)
                                            Swagger<St4ScsCk> ckSwagger,HttpServletRequest request) {
        St4ScsCk ck = ckSwagger.getData();
        if(ck.getCk001()==null){
            return Result.build(1001,"主键ck001"+ResultMsg.MSG_1001);
        }
        ck.setCk046("是");
        if(icheckLedgerService.updateById(ck)){
            return Result.build(1000,"设置"+ResultMsg.MSG_1000);
        }
        return Result.build(1003,ResultMsg.MSG_1003);


    }
    /**
     *
     * @param
     * @param
     * @return
     */
    @PassToken
    @ApiOperation(value="台账申诉",notes = "台账申诉",response = St4ScsCk.class)
    @RequestMapping(value="/pointStageAppeal",method = RequestMethod.POST)
    public Result pointStageAppeal(@RequestBody  @ApiParam(name="台账申诉）", value="json格式", required=true)
                                           Swagger<St4ScsCk> ckSwagger,HttpServletRequest request ) {
        St4ScsCk ck = ckSwagger.getData();
        if(ck.getCk001()==null){
            return Result.build(1001,"台账主键ck001不能为空");
        }
        ck.setCk089(1);
        if(icheckLedgerService.updateById(ck)){
            return Result.build(1000,"申诉成功");
        }
        return  Result.build(1005,ResultMsg.MSG_1005);
    }
    /**
     *
     * @param
     * @param
     * @return
     */
    @PassToken
    @ApiOperation(value="(安徽用)台账审核(ck067必传,审核:未审核0 1是已审核 2是退回)",notes = "台账审核",response = St4ScsCk.class)
    @RequestMapping(value="/pointStageExamine",method = RequestMethod.POST)
    public ResultVO pointStageExamine(@RequestBody  @ApiParam(name="台账审核）", value="json格式", required=true)
                                           Swagger<St4ScsCk> ckSwagger,HttpServletRequest request,HttpServletResponse response) {
        St4ScsCk ck = ckSwagger.getData();
        if(ck.getCk001()==null){
            return ResultVOUtil.error(ResultEnum.HANDLEFAIL.getCode(), "台账主键ck001不能为空！");
        }
        if(ck.getCk067()==null){
            return ResultVOUtil.error(ResultEnum.HANDLEFAIL.getCode(), "审核状态不能为空！");
        }


        return  icheckLedgerService.pointStageExamine(ck);
    }
    /**
     *
     * @param
     * @param
     * @return
     */
    @PassToken
    @ApiOperation(value="问题点位统计（已核查和未核查,不必传参）",notes = "问题点位统计（已核查和未核查）",response = St4ScsCd.class)
    @RequestMapping(value="/pointStatistics",method = RequestMethod.POST)
    public Result pointStatistics(@RequestBody  @ApiParam(name="航点记录列表管理列表查看详情", value="json格式", required=true)
                                              Swagger<St4ScsCd> checkTaskSwagger,HttpServletRequest request,HttpServletResponse response) {
        return icheckLedgerService.pointStatistics();
    }
    /**
     *
     * @param
     * @param
     * @return
     */
    @PassToken
    @ApiOperation(value="统计当日核查问题点数量",notes = "统计当日核查问题点数量",response = St4ScsCd.class)
    @RequestMapping(value="/pointStatisticsToday",method = RequestMethod.POST)
    public Result pointStatisticsToday(@RequestBody  @ApiParam(name="统计当日核查问题点数量", value="json格式", required=true)
                                          Swagger<St4ScsCd> st4ScsCdSwagger,HttpServletRequest request,HttpServletResponse response) {
        St4ScsCd cd = st4ScsCdSwagger.getData();
        return icheckLedgerService.pointStatisticsToday(cd);
    }
    /**
     *
     * @param
     * @param
     * @return
     */
    @PassToken
    @ApiOperation(value="统计已整改、未整改、整改中、无需整改的问题点数量（已核查和未核查,不必传参）",notes = "统计已整改、未整改、整改中、无需整改的问题点数量",response = St4ScsCd.class)
    @RequestMapping(value="/pointStatisticsRepairCon",method = RequestMethod.POST)
    public Result pointStatisticsRepairCon(@RequestBody  @ApiParam(name="统计已整改、未整改、整改中、无需整改的问题点数量（已核查和未核查,不必传参）", value="json格式", required=true)
                                          Swagger<St4ScsCd> checkTaskSwagger,HttpServletRequest request,HttpServletResponse response) {
        return icheckLedgerService.pointStatisticsRepairCon();
    }

    /**
     *
     * @param
     * @param
     * @return
     */
    @PassToken
    @ApiOperation(value="统计新增问题点数量（已核查和未核查,不必传参）",notes = "统计已整改、未整改、整改中、无需整改的问题点数量",response = St4ScsCd.class)
    @RequestMapping(value="/pointStatisRecentAdd",method = RequestMethod.POST)
    public Result pointStatisRecentAdd(@RequestBody  @ApiParam(name="统计新增问题点数量（已核查和未核查,不必传参）", value="json格式", required=true)
                                                   Swagger<St4ScsCd> checkTaskSwagger,HttpServletRequest request,HttpServletResponse response) {
        return icheckLedgerService.pointStatisRecentAdd();
    }

    /**
     * 返回离线和在线和巡护中的人数(一张图用)
     * @param checkTaskSwagger
     * @param request
     * @param response
     * @return
     */
    @PassToken
    @ApiOperation(value="返回离线和在线和巡护中的人数(一张图用)",notes = "返回离线和在线和巡护中的人数(一张图用)",response = St4ScsCh.class)
    @RequestMapping(value="/getPersonNumber",method = RequestMethod.POST)
    public Result getPersonNumber(@RequestBody  @ApiParam(name="返回离线和在线和巡护中的人数(一张图用)", value="json格式", required=true)
                                               Swagger<St4ScsCh> checkTaskSwagger,HttpServletRequest request,HttpServletResponse response) {

        return iSt4ScsChService.getPersonNumber();
    }


    @PassToken
    @ApiOperation(value="导出EXCEL(此接口只传ck001即可)",notes = "导出EXCEL(此接口只传ck001即可)",response = St4ScsCk.class)
    @RequestMapping(value="/exportExcel",method = RequestMethod.GET)
    public void exportExcel(HttpServletRequest request,HttpServletResponse response) {
        String ck001 =request.getParameter("ck001");

        St4ScsCk ck = icheckLedgerService.getById(ck001);
        icheckLedgerService.doExcel(ck,response);

    }
    @PassToken
    @ApiOperation(value="环科院导出EXCEL(此接口只传ck001即可)",notes = "导出EXCEL(此接口只传ck001即可)",response = St4ScsCk.class)
    @RequestMapping(value="/exportHkyExcel",method = RequestMethod.GET)
    public void exportHkyExcel(HttpServletRequest request,HttpServletResponse response) {

    }



}

