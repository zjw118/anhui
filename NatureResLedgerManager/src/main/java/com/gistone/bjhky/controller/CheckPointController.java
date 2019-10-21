package com.gistone.bjhky.controller;


import com.auth0.jwt.JWT;
import com.gistone.bjhky.annotation.PassToken;
import com.gistone.bjhky.entity.*;
import com.gistone.bjhky.pkname.Swagger;
import com.gistone.bjhky.service.*;
import com.gistone.bjhky.swagger.SharePoint;
import com.gistone.bjhky.util.ObjectUtils;
import com.gistone.bjhky.util.Result;
import com.gistone.bjhky.util.ResultMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 问题点表 前端控制器
 * </p>
 *
 * @author LiuXiong
 * @since 2019-08-13
 */
@Valid
@RestController
@RequestMapping("/api/checkPoint")
@Api(value = "API-UserManage", tags = "问题点位管理接口", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class CheckPointController {

    @Autowired
    private ISt4ScsCdService iSt4ScsCdService;
    @Autowired
    private ISt4ScsClService iSt4ScsClService;
    @Autowired
    private ISt4ScsChService iSt4ScsChService;

    @Autowired
    private ISt4PoCdSaService checkUserRelavantService;

    @Autowired
    private ISt4PoSaSgService st4PoSaSgService;
    @Autowired
    private ISt4ScsCrService st4ScsCrService;
    /**
     * 一张图展示，问题点按保护地查询
     * @param
     * @return
     */

    @ApiOperation(value = "一张图展示，问题点按保护地查询", notes = "此接口返回问题点数据数据", response = Result.class)
    @PostMapping("/listCheckPointToView")
    public Result listCheckPointToView(@RequestBody @ApiParam(name = "保护地id", value = "json格式", required = true) Swagger<St4ScsCd> data) {
        St4ScsCd param = data.getData();
        return iSt4ScsCdService.listCheckPointToView(param);
    }



    /**
     * 问题点列表
     * @param
     * @return
     */
    @PassToken
    @ApiOperation(value = "问题点列表", notes = "问题点列表", response = Result.class)
    @PostMapping("/listCheckPoint")
    public Result listCheckPoint(@RequestBody @ApiParam(name = "", value = "json格式", required = true) Swagger<St4ScsCd> data, HttpServletRequest request) {
        St4ScsCd param = data.getData();
        if(!ObjectUtils.isNotNullAndEmpty(param.getPageNumber())||!ObjectUtils.isNotNullAndEmpty(param.getPageSize())){
            return Result.build(1001,"pageSize,pageNumber"+ ResultMsg.MSG_1001);
        }
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId = JWT.decode(token).getAudience().get(0);
        St4SysSa seUser = new St4SysSa();
        seUser.setSa001(Integer.valueOf(userId));
        return iSt4ScsCdService.listCheckPoint(param,seUser);
    }

    /**
     * 问题点修改接口
     * @param
     * @return
     */
    @PassToken
    @ApiOperation(value = "问题点修改接口", notes = "问题点修改接口", response = Result.class)
    @PostMapping("/updateCheckPoint")
    public Result updateCheckPoint(@RequestBody @ApiParam(name = "", value = "json格式", required = true) Swagger<St4ScsCd> data) {
        St4ScsCd param = data.getData();
        if(!ObjectUtils.isNotNullAndEmpty(param.getCd001())){
            return Result.build(1001,ResultMsg.MSG_1001);
        }
        if(iSt4ScsCdService.updateById(param)){
            return  Result.build(1000,"修改"+ResultMsg.MSG_1000);
        }
        return  Result.build(1005,ResultMsg.MSG_1005);
    }

    /**
     * 问题点删除接口
     * @param
     * @return
     */
    @PassToken
    @ApiOperation(value = "问题点删除接口", notes = "问题点删除接口", response = Result.class)
    @PostMapping("/deleteCheckPoint")
    public Result deleteCheckPoint(@RequestBody @ApiParam(name = "", value = "json格式", required = true) Swagger<St4ScsCd> data) {
        St4ScsCd param = data.getData();
        if(!ObjectUtils.isNotNullAndEmpty(param.getCd001())){
            return Result.build(1001,ResultMsg.MSG_1001);
        }
        param.setCd009(0);
        if(iSt4ScsCdService.updateById(param)){
            return  Result.build(1000,"删除"+ResultMsg.MSG_1000);
        }
        return  Result.build(1005,ResultMsg.MSG_1005);
    }

    /**
     * 问题点添加接口
     * @param
     * @return
     */
    @PassToken
    @ApiOperation(value = "问题点添加接口", notes = "问题点添加接口", response = Result.class)
    @PostMapping("/insertCheckPoint")
    public Result insertCheckPoint( @RequestBody @ApiParam(name = "", value = "json格式", required = true) @Valid Swagger<St4ScsCd> data
            , HttpServletRequest request) {

        St4ScsCd param = data.getData();
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId = JWT.decode(token).getAudience().get(0);
        param.setCd010(Integer.valueOf(userId));

        return  iSt4ScsCdService.insertCheckPoint(param);
    }
    /**
     * 问题点详情接口
     * @param
     * @return
     */
    @PassToken
    @ApiOperation(value = "问题点详情接口", notes = "问题点详情接口", response = Result.class)
    @PostMapping("/getCheckPointById")
    public Result getCheckPointById( @RequestBody @ApiParam(name = "", value = "json格式", required = true)  Swagger<St4ScsCd> data
            ) {

        St4ScsCd param = data.getData();
        if(!ObjectUtils.isNotNullAndEmpty(param.getCd001())){
            return Result.build(1001,"问题点cd001"+ResultMsg.MSG_1001);
        }
        param = iSt4ScsCdService.getById(param.getCd001());
        Result res = new Result();
        res.setStatus(1000);
        res.setMsg("加载"+ResultMsg.MSG_1000);
        Integer taskId = param.getCl001();
        St4ScsCl cl = iSt4ScsClService.getById(taskId);
        if(ObjectUtils.isNotNullAndEmpty(cl.getCl002())){
            param.setTaskName(cl.getCl002());
        }

        try{
            res.setData(BeanUtils.describe(param));
        }catch (Exception e){
            e.printStackTrace();
            return Result.build(1003,ResultMsg.MSG_1003);
        }

        return  res;
    }

    /**
     * 下发问题点
     * @param spSwagger
     * @return
     */
    @PassToken
    @ApiOperation(value="下发问题点",notes = "下发问题点",response = St4PoCdSa.class)
    @RequestMapping(value="/sharePoint",method = RequestMethod.POST)
    public Result sharePoint(@ApiParam(name="下发问题点", value="json格式", required=true)@RequestBody Swagger<SharePoint> spSwagger) {
        SharePoint sp = spSwagger.getData();
        List<Integer> pointList = sp.getPointIdList();
        List<Integer> uids = sp.getUidList();
        if(pointList==null||pointList.size()<1){
            return Result.build(1001,"问题点不能为空");
        }
        if(uids==null||uids.size()<1){
            return Result.build(1001,"下发人员不能为空");
        }

        return checkUserRelavantService.givePoint(uids,pointList);

    }
  /*  *//**
     * 活动设施类型展示下拉框
     * @param spSwagger
     * @return
     *//*
    @PassToken
    @ApiOperation(value="活动设施类型展示下拉框",notes = "活动设施类型展示下拉框",response = St4ScsCr.class)
    @RequestMapping(value="/showActivity",method = RequestMethod.POST)
    public Result showActivity(@ApiParam(name="下发问题点", value="json格式", required=true)@RequestBody Swagger<T> spSwagger) {
        Result result = new Result();

        result.setStatus(1000);
        result.setMsg("加载成功");
        result.setData(st4ScsCrService.list());

        return result;
    }
*/





}

