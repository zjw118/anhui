package com.gistone.controller;

import com.auth0.jwt.JWT;
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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName LoginController
 * @Description 登录接口
 * @Author xxh
 * @Date 2019/7/18 10:04
 * @Version 1.0
 **/
@RestController
@Api(value = "API-UserManage", tags = "用户相关接口", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private ISt4SysSaService userService;

    @Autowired
    private ISt4SysSbService roleService;


    @Autowired
    private ISt4SysSjService unitService;

    @Autowired
    private ISt4SysScService moduleService;

    @Autowired
    private ISt4PoSaSbService sysUserRoleService;

    @Autowired
    private ISt4PoSaSjService sysUserUnitRelevantService;

    @Autowired
    private  ISt4SysShService st4SysShService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 用户详情
     * @param data
     * @return
     */
    @PassToken
    @ApiOperation(value = "用户详情(包含用户所属的角色和角色)", notes = "用户详情", response = St4SysSa.class)
    @PostMapping("/getUserDetail")
    public Result getUserDetail(@RequestBody @ApiParam(name = "用户详情", value = "json格式", required = true) Swagger<St4SysSa> data){

//        St4SysSa sa = data.getData();
//
//        if(sa.getSa001()==null){
//            return Result.build(1001,"用户名主键sa001"+ ResultMsg.MSG_1001);
//        }
//        return  userService.getUserDetail(sa);
        return null;
    }
    /**
     * 人员分组列表（无分页）
     * @param
     * @param
     * @return
     * @throws Exception
     */
    @PassToken
    @ApiOperation(value = "人员分组列表（无分页）******", notes = "人员分组列表（无分页）", response = St4SysSa.class)
    @PostMapping("/list")
    public Result list(@RequestBody @ApiParam(name = "人员分组列表（无分页）", value = "json格式", required = true) Swagger<St4SysSa> data,
                       HttpServletRequest request){
//            St4SysSa seUser = new St4SysSa();
////            String token = request.getHeader("token");// 从 http 请求头中取出 token
////            //String UserId = JWT.decode(token).getAudience().get(0);
////            seUser.setSa001(Integer.parseInt("1"));
////            St4SysSa sa = data.getData();
////
////            Result result = userService.list(sa,seUser);
////            return result;
        return null;
    }

    /**
     * 人员分组列表（有分页）
     * @param
     * @param
     * @return
     * @throws Exception
     */
    @PassToken
    @ApiOperation(value = "(安徽用)人员分组列表（有分页）真实姓名sa019手机号sa012", notes = "人员分组列表（有分页）", response = St4SysSa.class)
    @PostMapping("/listUser")
    public ResultVO listUser(@RequestBody @ApiParam(name = "人员分组列表（有分页）", value = "json格式", required = true) Swagger<St4SysSa> data,
                           HttpServletRequest request){
        St4SysSa sa = data.getData();
        if(sa.getPageNumber()== null){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "pageNumber！");
        }
        if(sa.getPageSize()== null){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "pageSize不能为空！");
        }
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        //String UserId = JWT.decode(token).getAudience().get(0);
        St4SysSa seUser = new St4SysSa();

        seUser.setSa001(Integer.parseInt("1"));
        return userService.listUser(sa,seUser);
    }

    /**
     * 添加用户
     * @param
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ApiOperation(value="添加用户",notes = "添加用户",response = St4SysSa.class)
    @PostMapping(value="/insertUser")
    public Result insertUser(@RequestBody  @ApiParam(name="添加用户", value="json格式", required=true)Swagger<St4SysSa> data,
                             HttpServletRequest request, HttpServletResponse response) throws Exception{

        St4SysSa addUser = data.getData();

        if(addUser.getSa008()==null){
            return Result.build(1001,"用户名sa008"+ResultMsg.MSG_1001);
        }
        if(addUser.getSa012()==null){
            return Result.build(1001,"手机号sa012"+ResultMsg.MSG_1001);
        }
        if(addUser.getSa009()==null){
            return Result.build(1001,"密码sa009"+ResultMsg.MSG_1001);
        }
        //绿盾暂且不加角色
       /* if(addUser.getRoleList()==null){
            return Result.build(1001,"角色"+ResultMsg.MSG_1001);
        }*/
       //单位暂且不加
        /*if(addUser.getUnitList()==null){
            return Result.build(1001,"单位"+ResultMsg.MSG_1001);
        }*/


//		//模块权限
//		if (RegUtil.CheckParameter(data.get("suSmjId"), null, null, false)) {
//			addUser.setSuSmjId(data.get("suSmjId").toString());
//		}
		/*//数据权限
		if (RegUtil.CheckParameter(data.get("suDataId"), "Integer", null, false)) {
			addUser.setSuDataId(Integer.parseInt(data.get("suDataId").toString()));
		}
		//空间数据权限
		if (RegUtil.CheckParameter(data.get("suSpacedataId"), null, null, false)) {
			addUser.setSuSpacedataId(data.get("suSpacedataId").toString());
		}*/
        List<Integer> roleList=null;
            /**
             * 这里考虑到以后一个人可能承担多个角色所以这里的角色是一个数组
             */
            roleList=addUser.getRoleList();
        //这里考虑到一个人可能在多个部门所以传递进来的也得是一个数组
        List<Integer> unitList=null;
            /**
             * 这里考虑到以后一个人可能在多个部门所以这里的部门是一个数组
             */
            unitList = addUser.getUnitList();
        addUser.setSa008(addUser.getSa008().toString());
        addUser.setSa009(addUser.getSa009().toString());
        St4SysSa seUser = new St4SysSa();
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId = JWT.decode(token).getAudience().get(0);
        seUser.setSa001(Integer.valueOf(userId));
        if("1".equals(userId)){
            addUser.setSa002(Integer.valueOf(userId));
        }

        Result result = userService.add(addUser,seUser,roleList,unitList);

        return result;
    }

    /**
     * 修改用户
     * @param
     * @return
     * @throws Exception
     */
    @ApiOperation(value="修改用户",notes = "修改用户",response = St4SysSa.class)
    @PostMapping(value="/updateUser")
    public Result updateUser(@RequestBody@ApiParam(name="修改用户", value="json格式", required=true)Swagger<St4SysSa> user,
                             HttpServletRequest request) {

//        String token = request.getHeader("token");// 从 http 请求头中取出 token
//        String userId = JWT.decode(token).getAudience().get(0);
//        St4SysSa seUser = new St4SysSa();
//        seUser.setSa001(Integer.valueOf(userId));
//        //2.获取前端传递的参数
//        St4SysSa addUser = user.getData();
//        //Id
//        if (!RegUtil.CheckParameter(addUser.getSa001(), "Integer", null, false)) {
//            return Result.build(1002, "用户ID不能为空");
//        }
//
//
//        List<Integer> roleList=new ArrayList<Integer>();
//        //绿盾暂不加 产品加
//        if (RegUtil.CheckParameter(addUser.getRoleList(), null, null, false)) {
//
//            roleList = addUser.getRoleList();
//        }
//
//        List<Integer> unitList=new ArrayList<Integer>();
//        if (RegUtil.CheckParameter(addUser.getUnitList(), null, null, false)) {
//            /**
//             * 这里考虑到以后一个人可能在多个部门所以这里的部门是一个数组
//             */
//            unitList = addUser.getUnitList();
//        }
//
//
//
//        Result result = userService.updateUser(addUser,seUser,roleList,unitList);

      //  return result;
        return null;
    }
    @ApiOperation(value="删除用户",notes = "删除用户",response = St4SysSa.class)
    @PostMapping(value="/deleteUser")
    public Result deleteUser(@RequestBody@ApiParam(name="删除用户", value="json格式", required=true)Swagger<St4SysSa> user) {
//        St4SysSa sa = user.getData();
//        if(sa.getSa001()==null){
//            return Result.build(1001,"用户ID"+ResultMsg.MSG_1001);
//        }
//        sa.setSa007(0);
//        St4SysSa seUser = user.getData();
//        return userService.deleteUser(sa,seUser);
        return null;
    }




    /**
     * @explain : 巡查人员一张图展示
     * @author LiuXiong
     * @date 2019/8/5
     * @param request
     * @param data
     * @return com.gistone.util.Ret
     */
    @ApiOperation(value = "(安徽用)巡查人员实时位置,ch003经度，ch004纬度，人员状态 0离线 1在线 2正在巡护中", notes = "巡查人员一张图展示", response = Result.class)
    @PostMapping("/listPhoneUserToView")
    public ResultVO listPhoneUserToView(HttpServletRequest request, @RequestBody Swagger<St4SysSa> data)throws Exception  {

            St4SysSa param = data.getData();
            return   userService.listPhoneUserToView(param);
    }

    /**
     * 角色展示列表
     * @param data
     * @return
     */
//    @PassToken
//    @ApiOperation(value = "角色展示列表(isLimit是否分页必传 0是1否)", notes = "角色展示列表", response = St4SysSb.class)
//    @PostMapping("/listRole")
//    public Result listRole( @RequestBody @ApiParam(name="角色展示列表", value="json格式", required=true) Swagger<St4SysSb> data,HttpServletRequest request)  {
//
//        St4SysSb sb = data.getData();
//        if(sb.getIsLimit()==null){
//            return Result.build(1001,"是否分页isLimit"+ResultMsg.MSG_1001);
//        }
//        if(sb.getIsLimit()==1){
//            return  roleService.listRoleNoLimit(sb,request);
//        }
//        if(!ObjectUtils.isNotNullAndEmpty(sb.getPageNumber())||!ObjectUtils.isNotNullAndEmpty(sb.getPageSize())){
//            return  Result.build(1001,"pageNumber,pageSize均不能为空");
//        }
//        return roleService.listRole(sb,request);
//    }

    @PassToken
    @ApiOperation(value = "角色添加", notes = "角色添加", response = St4SysSb.class)
    @PostMapping("/insertRole")
    public Result insertRole( @RequestBody @ApiParam(name="角色添加", value="json格式", required=true) Swagger<St4SysSb> data,HttpServletRequest request)  {
        St4SysSb sb = data.getData();

        if (sb.getSb002()==null) {
            return  Result.build(1001,"角色名称不能为空");
        }
        if (sb.getPriviledgeIds()==null) {
            return  Result.build(1001,"角色拥有权限不能为空");
        }
        LocalDateTime date = LocalDateTime.now();
        sb.setSb004(date);
        sb.setSb003(1);
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId = JWT.decode(token).getAudience().get(0);
        St4SysSa sa = new St4SysSa();
        sa.setSa001(Integer.valueOf(userId));
        sb.setSb005(sa.getSa001());

        return roleService.insertRole(sb,sa);
    }

    @PassToken
    @ApiOperation(value = "角色修改", notes = "角色修改", response = St4SysSb.class)
    @PostMapping("/updateRole")
    public Result updateRole( @RequestBody @ApiParam(name="角色修改", value="json格式", required=true) Swagger<St4SysSb> data)  {
        St4SysSb sb = data.getData();

        if (sb.getSb001()==null) {
            return  Result.build(1001,"角色ID不能为空");
        }
        LocalDateTime date = LocalDateTime.now();
        sb.setSb006(date);
        //TODO
        St4SysSa sa = new St4SysSa();
        sa.setSa001(1);
        sb.setSb007(sa.getSa001());
        return  roleService.updateRole(sb,sa);
    }
    @PassToken
    @ApiOperation(value = "角色详情", notes = "角色详情", response = St4SysSb.class)
    @PostMapping("/getRoleDetail")
    public Result getRoleDetail( @RequestBody @ApiParam(name="角色详情", value="json格式", required=true) Swagger<St4SysSb> data)  {

        St4SysSb sb = data.getData();
        if(sb.getSb001()==null){
            return  Result.build(1001,"角色主键"+ResultMsg.MSG_1001);
        }

        return roleService.getRoleDetail(sb);
    }
    @PassToken
    @ApiOperation(value = "角色删除", notes = "角色删除", response = St4SysSb.class)
    @PostMapping("/deleteRole")
    public Result deleteRole( @RequestBody @ApiParam(name="角色删除", value="json格式", required=true) Swagger<St4SysSb> data,HttpServletRequest request)  {
        St4SysSb sb = data.getData();
        if (sb.getSb001()==null) {
            return  Result.build(1001,"角色ID不能为空");
        }
        sb.setSb003(0);
        LocalDateTime date = LocalDateTime.now();
        sb.setSb006(date);
        St4SysSa sa = new St4SysSa();
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId = JWT.decode(token).getAudience().get(0);
        sa.setSa001(Integer.valueOf(userId));
        sb.setSb007(sa.getSa001());
        if(roleService.updateById(sb)){
            return Result.build(1001,"删除"+ResultMsg.MSG_1000);
        }else{
            return Result.build(1003,ResultMsg.MSG_1003);
        }
    }


    /**
     * 单位展示列表
     * @param data
     * @return
     */
//    @PassToken
//    @ApiOperation(value = "单位列表(isLimit是否分页必传 0是1否)", notes = "单位列表", response = St4SysSj.class)
//    @PostMapping("/listUnit")
//    public Result listUnit( @RequestBody @ApiParam(name="单位列表", value="json格式", required=true) Swagger<St4SysSj> data,HttpServletRequest request)  {
//        st4syssj sj = data.getData();
//        St4SysSa seUser = new St4SysSa();
//        String token = request.getHeader("token");// 从 http 请求头中取出 token
//        String userId = JWT.decode(token).getAudience().get(0);
//        seUser.setSa001(Integer.valueOf(userId));
//        if(sj.getIsLimit()==null){
//            return Result.build(1001,"是否分页isLimit"+ResultMsg.MSG_1001);
//        }
//        if(sj.getIsLimit()==1){
//            return unitService.listUnitNoLimit(sj,seUser);
//        }
//        if(!ObjectUtils.isNotNullAndEmpty(sj.getPageNumber())||!ObjectUtils.isNotNullAndEmpty(sj.getPageSize())){
//            return  Result.build(1001,"pageNumber,pageSize均不能为空");
//        }
//        return unitService.listUnit(sj,seUser);
//    }

    @PassToken
    @ApiOperation(value = "单位添加", notes = "单位添加", response = St4SysSj.class)
    @PostMapping("/insertUnit")
    public Result insertUnit( @RequestBody @ApiParam(name="单位添加", value="json格式", required=true) Swagger<St4SysSj> data,HttpServletRequest request)  {
        St4SysSj sj = data.getData();
        if(sj.getSj002()==null){
            return Result.build(1001,"单位名称"+ResultMsg.MSG_1001);
        }
        if(sj.getBidList()==null||sj.getBidList().size()<1){
            return Result.build(1001,"所管辖的保护区"+ResultMsg.MSG_1001);
        }
        LocalDateTime date = LocalDateTime.now();
        sj.setSj004(date);

        St4SysSa seUser = new St4SysSa();
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId = JWT.decode(token).getAudience().get(0);
        seUser.setSa001(Integer.valueOf(userId));

        return unitService.insertUnit(sj,seUser);
    }
    @PassToken
    @ApiOperation(value = "单位修改", notes = "单位修改", response = St4SysSj.class)
    @PostMapping("/updateUnit")
    public Result updateUnit( @RequestBody @ApiParam(name="单位修改", value="json格式", required=true) Swagger<St4SysSj> data,HttpServletRequest request)  {
        St4SysSj sj = data.getData();
        if(sj.getSj001()==null){
            return Result.build(1001,"单位主键"+ResultMsg.MSG_1001);
        }
        if(sj.getBidList()==null||sj.getBidList().size()<1){
            return Result.build(1001,"所管辖的保护区"+ResultMsg.MSG_1001);
        }
        LocalDateTime date = LocalDateTime.now();
        sj.setSj004(date);

        St4SysSa seUser = new St4SysSa();
        String token = request.getHeader("token");// 从 http 请求头中取出 token
       // String userId = JWT.decode(token).getAudience().get(0);
        seUser.setSa001(Integer.valueOf(1));

        return unitService.updateUnit(sj,seUser);
    }
    @PassToken
    @ApiOperation(value = "单位详情", notes = "单位详情", response = St4SysSj.class)
    @PostMapping("/getUnitDetail")
    public Result getUnitDetail( @RequestBody @ApiParam(name="单位详情", value="json格式", required=true) Swagger<St4SysSj> data)  {
        St4SysSj sj = data.getData();
        if(sj.getSj001()==null){
            return Result.build(1001,"单位主键"+ResultMsg.MSG_1001);
        }

        return unitService.getUnitDetail(sj);

    }
    @PassToken
    @ApiOperation(value = "单位删除", notes = "单位删除", response = St4SysSj.class)
    @PostMapping("/deleteUnit")
    public Result deleteUnit( @RequestBody @ApiParam(name="单位删除", value="json格式", required=true) Swagger<St4SysSj> data,HttpServletRequest request)  {
        St4SysSj sj = data.getData();
        if(sj.getSj001()==null){
            return Result.build(1001,"单位主键"+ResultMsg.MSG_1001);
        }

        St4SysSa seUser = new St4SysSa();
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId = JWT.decode(token).getAudience().get(0);
        seUser.setSa001(Integer.valueOf(userId));
        return unitService.deleteUnit(sj,seUser);
    }
    @PassToken
    @ApiOperation(value = "日志列表", notes = "日志列表", response = St4SysSh.class)
    @PostMapping("/listLog")
    public Result listLog( @RequestBody @ApiParam(name="日志列表", value="json格式", required=true) Swagger<St4SysSh> data)  {
        St4SysSh sh = data.getData();

        if(!ObjectUtils.isNotNullAndEmpty(sh.getPageNumber())||!ObjectUtils.isNotNullAndEmpty(sh.getPageSize())){
            return  Result.build(1001,"pageNumber,pageSize均不能为空");
        }
        return  st4SysShService.listLog(sh);
    }

    /**
     * 模块权限展示列表
     * @param data
     * @return
     */
    @PassToken
    @ApiOperation(value = "模块权限展示列表", notes = "模块权限展示列表", response = St4SysSc.class)
    @PostMapping("/listModule")
    public Result listModule( @RequestBody @ApiParam(name="模块权限展示列表", value="json格式", required=true) Swagger<St4SysSc> data)  {
        St4SysSc sc = data.getData();

        return moduleService.listModule(sc);
    }



}
