package com.gistone.controller;


import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.annotation.PassToken;
import com.gistone.entity.St4PoSaCz;
import com.gistone.entity.St4ScsCl;
import com.gistone.entity.St4ScsCz;
import com.gistone.entity.St4SysSa;
import com.gistone.pkname.Swagger;
import com.gistone.service.ISt4PoSaCzService;
import com.gistone.service.ISt4ScsClService;
import com.gistone.service.ISt4ScsCzService;
import com.gistone.service.ISt4SysSaService;
import com.gistone.swagger.St4ScsCzSwagger;
import com.gistone.util.ObjectUtils;
import com.gistone.util.Result;
import com.gistone.util.ResultCp;
import com.gistone.util.ResultMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * <p>
 * 任务批次表 前端控制器
 * </p>
 *
 * @author LiuXiong
 * @since 2019-08-14
 */
@RestController
@RequestMapping("/api/task")
@Api(value = "API-UserManage", tags = "任务批次表接口", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {
    @Autowired
    private ISt4SysSaService st4SysSaService;
    @Autowired
    private ISt4ScsClService iSt4ScsClService;

    @Autowired
    private ISt4PoSaCzService iSt4PoSaCzService;
    @Autowired
    private ISt4ScsCzService iSt4ScsCzService;

    @ApiOperation(value = "问题点批次", notes = "此接口返回问题点批次数据", response = Result.class)
    @PostMapping("/listCheckPointToView")
    public Result listCheckPointToView(@RequestBody @ApiParam(name = "问题点批次", value = "json格式", required = true) Swagger<St4ScsCl> data) {
        St4ScsCl param = data.getData();
        return iSt4ScsClService.listForView(param);
    }

    /**
     * 任务批次添加接口
     * @param data
     * @return
     */
    @ApiOperation(value = "任务批次添加接口", notes = "此接口返回问题点批次数据", response = Result.class)
    @PostMapping("/insertTask")
    public ResultCp insertTask(@RequestBody @ApiParam(name = "任务批次添加接口", value = "json格式", required = true) Swagger<St4ScsCl> data, HttpServletRequest request) {
        St4ScsCl param = data.getData();
        LocalDateTime date = LocalDateTime.now();
        //安徽项目没有巡护所以去掉验证
        /*if(param.getCl004()==null){
            return  Result.build(1001,"任务类型cl002"+ResultMsg.MSG_1001);
        }*/
        St4SysSa seUser = new St4SysSa();
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId ="1";// JWT.decode(token).getAudience().get(0);
        seUser.setSa001(Integer.valueOf(userId));
        param.setCl013(seUser.getSa001());
        param.setCl014(date);

        if(iSt4ScsClService.save(param)){
            return  ResultCp.build(1000,"添加"+ ResultMsg.MSG_1000);
        }
        return ResultCp.build(1005,ResultMsg.MSG_1005);
    }

    /**
     * 任务批次修改接口
     * @param data
     * @return
     */
    @PassToken
    @ApiOperation(value = "任务批次修改接口", notes = "此接口返回问题点批次数据", response = St4ScsCl.class)
    @PostMapping("/updateTask")
    public ResultCp updateTask(@RequestBody @ApiParam(name = "任务批次修改接口", value = "json格式", required = true) Swagger<St4ScsCl> data) {
        St4ScsCl param = data.getData();
        if(param.getCl001()==null){
            return  ResultCp.build(1001,"批次任务ciId"+ResultMsg.MSG_1001);
        }
        if(iSt4ScsClService.updateById(param)){
            return  ResultCp.build(1000,"修改"+ ResultMsg.MSG_1000);
        }
        return ResultCp.build(1005,ResultMsg.MSG_1005);
    }

    /**
     * 任务批次删除接口
     * @param data
     * @return
     */
      
    @ApiOperation(value = "任务批次删除接口", notes = "此接口返回问题点批次数据", response = Result.class)
    @PostMapping("/deleteTask")
    public ResultCp deleteTask(@RequestBody @ApiParam(name = "任务批次修改接口", value = "json格式", required = true) Swagger<St4ScsCl> data) {
        St4ScsCl param = data.getData();
        if(param.getCl001()==null){
            return  ResultCp.build(1001,"批次任务ciId"+ResultMsg.MSG_1001);
        }
        param.setCl012(0);
        if(iSt4ScsClService.updateById(param)){
            return  ResultCp.build(1000,"删除"+ ResultMsg.MSG_1000);
        }
        return ResultCp.build(1005,ResultMsg.MSG_1005);
    }

    /**
     * 任务批次列表接口
     * @param data
     * @return
     */
    @ApiOperation(value = "任务批次列表接口", notes = "此接口返回问题点批次数据", response = Result.class)
    @PostMapping("/listTask")
    public ResultCp listTask(@RequestBody @ApiParam(name = "任务批次列表接口", value = "json格式", required = true)
                                         Swagger<St4ScsCl> data,HttpServletRequest request) {
        St4ScsCl param = data.getData();

        if(!ObjectUtils.isNotNullAndEmpty(param.getPageNumber())&&!ObjectUtils.isNotNullAndEmpty(param.getPageSize())){
            return  ResultCp.build(1001,"pageSize和pageNumber"+ResultMsg.MSG_1001);
        }
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId ="1" ;//JWT.decode(token).getAudience().get(0);
        St4SysSa serUser = new St4SysSa();
        serUser.setSa001(Integer.valueOf(userId));
        return  iSt4ScsClService.listTask(param,serUser);
    }
    /**
     * 任务批次下拉框列表接口
     * @param data
     * @return
     */
    @PassToken
    @ApiOperation(value = "任务批次下拉框列表接口", notes = "此接口返回问题点批次数据", response = Result.class)
    @PostMapping("/listTaskSelect")
    public ResultCp listTaskSelect(@RequestBody @ApiParam(name = "任务批次下拉框列表接口", value = "json格式", required = true) Swagger<St4ScsCl> data) {
        St4ScsCl param = data.getData();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("CL012",1);
        ResultCp res = new ResultCp();
        res.setStatus(1000);
        res.setMsg("加载"+ResultMsg.MSG_1000);
        res.setData(iSt4ScsClService.list(wrapper));
        return  res;
    }

    /**
     * 任务批次单个详情接口
     * @param data
     * @return
     */
    @PassToken
    @ApiOperation(value = "任务批次单个详情接口", notes = "此接口返回问题点批次数据", response = Result.class)
    @PostMapping("/getTaskById")
    public Result getTaskById(@RequestBody @ApiParam(name = "任务批次单个详情接口", value = "json格式", required = true) Swagger<St4ScsCl> data) {
        St4ScsCl param = data.getData();
        if(param.getCl001()==null){
            return  Result.build(1001,"任务批次CL001"+ResultMsg.MSG_1001);
        }
        param = iSt4ScsClService.getById(param.getCl001());
        Result res = new Result();
        res.setStatus(1000);
        res.setMsg("加载"+ResultMsg.MSG_1000);
        try{
            res.setData(param);
        }catch (Exception e){
            e.printStackTrace();
            return Result.build(1003,ResultMsg.MSG_1003);
        }

        return  res;
    }

    /**
     * 新建巡护小组
     * @param data
     * @return
     */
    @PassToken
    @ApiOperation(value = "新建巡护小组*****", notes = "新建巡护小组", response = St4ScsCz.class)
    @PostMapping("/insertGroup")
    public Result insertGroup(@RequestBody @ApiParam(name = "新建巡护小组", value = "json格式", required = true)
                                          Swagger<St4ScsCzSwagger> data,HttpServletRequest request) {
        St4ScsCzSwagger param = data.getData();
        if(param.groupName==null){
            return  Result.build(1001,"巡护人员分组名称groupName"+ResultMsg.MSG_1001);
        }
        QueryWrapper<St4ScsCz> wrapper = new QueryWrapper<>();
        wrapper.eq("CZ002",param.groupName.trim());
        wrapper.eq("CZ003",1);
        if(iSt4ScsCzService.list(wrapper).size()>0){
            return  Result.build(1006,"巡护小组名称不能重复");
        }
        if(param.captainId==null){
            return  Result.build(1001,"巡护人员组长ID"+ResultMsg.MSG_1001);
        }
        if(param.uidList.size()<1){
            return  Result.build(1001,"巡护人员集合uidList"+ResultMsg.MSG_1001);
        }
        return  iSt4ScsCzService.insertGroupData(param,request);
    }
    @PassToken
    @ApiOperation(value = "展示巡护小组应该显示的人(无需传参)", notes = "展示巡护小组应该显示的人", response = St4ScsCz.class)
    @PostMapping("/showUser")
    public Result showUserInCheck(@RequestBody @ApiParam(name = "展示巡护小组应该显示的人", value = "json格式", required = true) Swagger<T> data) {

        return  iSt4ScsCzService.showUserInCheck();
    }
    /**
     * 修改巡护人员小组信息
     * @param data
     * @return
     */
    @PassToken
    @ApiOperation(value = "修改巡护小组信息*****", notes = "修改巡护人员小组信息", response = St4ScsCz.class)
    @PostMapping("/updateGroup")
    public Result updateGroup(@RequestBody @ApiParam(name = "修改巡护人员小组信息", value = "json格式", required = true) Swagger<St4ScsCzSwagger> data) {
        St4ScsCzSwagger param = data.getData();

        if(param.groupId==null){
            return  Result.build(1001,"巡护人员分组Id"+ResultMsg.MSG_1001);
        }
        return  iSt4ScsCzService.updateGroupData(param);
    }

    /**
     * 删除巡护人员分组
     * @param data
     * @return
     */
    @PassToken
    @ApiOperation(value = "删除巡护人员分组******", notes = "删除巡护人员分组", response = St4ScsCz.class)
    @PostMapping("/deleteGroup")
    public Result deleteGroup(@RequestBody @ApiParam(name = "删除巡护人员分组", value = "json格式", required = true) Swagger<St4ScsCz> data) {
        St4ScsCz param = data.getData();

        if(iSt4ScsCzService.getById(param.getCz001())==null){
            return  Result.build(1001,"巡护人员分组cz001"+ResultMsg.MSG_1001);
        }
        param.setCz003(0);
        if(iSt4ScsCzService.updateById(param)){
            QueryWrapper<St4PoSaCz> wrapper = new QueryWrapper<>();
            wrapper.eq("CZ001",param.getCz001());
            if(iSt4PoSaCzService.remove(wrapper)){
                return Result.build(1000,"删除"+ResultMsg.MSG_1000);
            }else{
                return Result.build(1003,"删除失败，"+ResultMsg.MSG_1003);
            }

        }
        return  Result.build(1005,ResultMsg.MSG_1005);
    }

    /**
     * 人员分组详情接口
     * @param data
     * @return
     */
    @PassToken
    @ApiOperation(value = "人员分组详情接口******", notes = "人员分组详情接口", response = St4ScsCz.class)
    @PostMapping("/getGroupUserDetail")
    public Result getGroupUserDetail(@RequestBody @ApiParam(name = "人员分组详情接口", value = "json格式", required = true) Swagger<St4ScsCz> data) {
        St4ScsCz param = data.getData();

        if(iSt4ScsCzService.getById(param.getCz001())==null){
            return  Result.build(1001,"巡护人员分组cz001"+ResultMsg.MSG_1001);
        }
        return  iSt4ScsCzService.getGroupUserDetail(param);
    }
    /**
     * 巡护人员分组列表
     * @param data
     * @return
     */
    @PassToken
    @ApiOperation(value = "巡护人员分组列表******", notes = "巡护人员分组列表", response = St4ScsCz.class)
    @PostMapping("/listGroup")
    public Result listGroup(@RequestBody @ApiParam(name = "巡护人员分组列表", value = "json格式", required = true)
                                        Swagger<St4ScsCz> data,HttpServletRequest request) {
        St4ScsCz param = data.getData();

        if(!ObjectUtils.isNotNullAndEmpty(param.getPageNumber())&&!ObjectUtils.isNotNullAndEmpty(param.getPageSize())){
            return  Result.build(1001,"pageSize和pageNumber"+ResultMsg.MSG_1001);
        }
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId = JWT.decode(token).getAudience().get(0);
        St4SysSa serUser = new St4SysSa();
        serUser.setSa001(Integer.valueOf(userId));
        return  iSt4ScsCzService.listGroup(param,serUser);
    }
    /**
     * 测试mybatisplus多表关联查询接口
     * @param data
     * @return
     *//*
    @PassToken
    @ApiOperation(value = "任务批次单个详情接口", notes = "此接口返回问题点批次数据", response = Result.class)
    @PostMapping("/plusList")
    public Result plusList(@RequestBody @ApiParam(name = "任务批次单个详情接口", value = "json格式", required = true) Swagger<St4ScsCl> data) {
        St4ScsCl param = data.getData();
        if(param.getCl001()==null){
            return  Result.build(1001,"任务批次CL001"+ResultMsg.MSG_1001);
        }
        param = iSt4ScsClService.getById(param.getCl001());
        Result res = new Result();
        res.setStatus(1000);
        res.setMsg("加载"+ResultMsg.MSG_1000);
        try{
            res.setData(BeanUtils.describe(param));
        }catch (Exception e){
            e.printStackTrace();
            return Result.build(1003,ResultMsg.MSG_1003);
        }

        return  res;
    }*/

}
