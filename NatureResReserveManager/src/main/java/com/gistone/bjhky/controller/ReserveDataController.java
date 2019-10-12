package com.gistone.bjhky.controller;

import com.auth0.jwt.JWT;
import com.gistone.bjhky.annotation.MyLog;
import com.gistone.bjhky.entity.*;
import com.gistone.bjhky.pkname.Swagger;
import com.gistone.bjhky.service.*;
import com.gistone.bjhky.swagger.GiveReserve;
import com.gistone.bjhky.util.ObjectUtils;
import com.gistone.bjhky.util.RegUtil;
import com.gistone.bjhky.util.Result;
import com.gistone.bjhky.util.ResultMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *bjhky-reserve-manager
 * @author zhaojingwei
 * @since 2019-07-31
 */

@RestController
@RequestMapping("/reserve")
@Api(value="台账接口",tags = "保护地接口的的根目录")
public class ReserveDataController {

    @Autowired
    private ISt4SysSgService iSt4SysSgService;

    @Autowired
    private ISt4ScsCaService iSt4ScsCaService;

    @Autowired
    private ISt4ScsCbService iSt4ScsCbService;
    //行政区划
    @Autowired
    private ISt4SysSdService iSt4SysSdService;
    @Autowired
    private  ISt4PoSaSgService st4PoSaSgService;
    /**
     * 保护地数据列表
     * @param
     * @param request
     * @return
     */
    @ApiOperation(value="保护地表格分页展示",notes = "保护地分页展示",response = St4SysSg.class)
    //@ApiImplicitParam(name="data",value="保护地数据实体对象",required = true,dataType = "")Map<String, Object> requestData
    @RequestMapping(value = "/listSt4SysSg",method = RequestMethod.POST)
    public Result listSt4SysSg(@RequestBody @ApiParam(name="保护地列表", value="json格式", required=true) Swagger<St4SysSg> data, HttpServletRequest request) {
        try {
            St4SysSg param = data.getData();

            //每页条数
            if (!RegUtil.CheckParameter(param.getPageSize(), "Integer", null, false)) {
                return Result.build(1001, "pageSize不能为空");
            }
            //开始索引
            if (!RegUtil.CheckParameter(param.getPageNumber(), "Integer", null, false)) {
                return Result.build(1001, "pageNumber不能为空");
            }
            String token = request.getHeader("token");// 从 http 请求头中取出 token
            String userId = JWT.decode(token).getAudience().get(0);
            St4SysSa seUser = new St4SysSa();
            seUser.setSa001(Integer.valueOf(userId));
            Result list = iSt4SysSgService.list(param,seUser);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.build(1005,ResultMsg.MSG_1005);
    }
    /**
     * 保护地数据添加
     * @param data
     * @param request
     * @param response
     * @return
     */
    @MyLog(value = "添加保护地数据")
    @ApiOperation(value="保护地数据添加",notes = "保护地添加",response = St4SysSg.class)
    @RequestMapping(value = "/insertSt4SysSg",method = RequestMethod.POST)
    public Result insertSt4SysSg(@RequestBody @ApiParam(name="保护地数据添加", value="json格式", required=true) Swagger<St4SysSg> data, HttpServletRequest request, HttpServletResponse response) {
        try {
            St4SysSg param = data.getData();
            //保护地名称
            if (!RegUtil.CheckParameter(param.getSg008(), "String", null, false)) {
                return Result.build(1001, "sg008保护地名称不能为空");
            }
            //所属行政区划
            if (!RegUtil.CheckParameter(param.getSd001(), "Integer", null, false)) {
                return Result.build(1001, "sd001行政区划id不能为空");
            }
            //保护地类型
            if (!RegUtil.CheckParameter(param.getCa001(), "Integer", null, false)) {
                return Result.build(1001, "ca001保护地类型id不能为空");
            }
            //保护地级别
            if (!RegUtil.CheckParameter(param.getCb001(), "Integer", null, false)) {
                return Result.build(1001, "cb001保护地级别不能为空");
            }
            Result list = iSt4SysSgService.insert(param);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.build(1005,ResultMsg.MSG_1005);
    }
    @MyLog(value = "修改保护地数据")
    @ApiOperation(value="保护地数据修改",notes = "保护地修改",response = St4SysSg.class)
   // @ApiImplicitParam(name="data",value="保护地数据实体对象",required = true,dataType = "")
    @RequestMapping(value = "/updateSt4SysSg",method = RequestMethod.POST)
    public Result updateSt4SysSg(@RequestBody @ApiParam(name="保护地数据修改", value="json格式", required=true) Swagger<St4SysSg> data, HttpServletRequest request, HttpServletResponse response) {
        try {
            St4SysSg param = data.getData();
            //保护地数据id
            if (!RegUtil.CheckParameter(param.getSg001(), "Integer", null, false)) {
                return Result.build(1001, "sg001保护地id不能为空");
            }
            //保护地名称
            if (!RegUtil.CheckParameter(param.getSg008(), "String", null, false)) {
                return Result.build(1001, "sg008不能为空");
            }
            //所属行政区划
            if (!RegUtil.CheckParameter(param.getSd001(), "Integer", null, false)) {
                return Result.build(1001, "sd001不能为空");
            }
            //保护地类型
            if (!RegUtil.CheckParameter(param.getCa001(), "Integer", null, false)) {
                return Result.build(1001, "ca001不能为空");
            }
            //保护地级别
            if (!RegUtil.CheckParameter(param.getCb001(), "Integer", null, false)) {
                return Result.build(1001, "cb001不能为空");
            }
            Result list = iSt4SysSgService.update(param);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.build(1005,ResultMsg.MSG_1005);
    }
    @MyLog(value = "删除保护地数据")
    @ApiOperation(value="保护地数据删除",notes = "保护地删除",response = St4SysSg.class)
   // @ApiImplicitParam(name="data",value="保护地数据实体对象",required = true,dataType = "")
    @RequestMapping(value = "/deleteSt4SysSg",method = RequestMethod.POST)
    public Result deleteSt4SysSg(@RequestBody @ApiParam(name="保护地数据删除", value="json格式", required=true) Swagger<St4SysSg> data, HttpServletRequest request, HttpServletResponse response) {
        try {
            St4SysSg param = data.getData();
            //保护地数据id
            if (!RegUtil.CheckParameter(param.getSg001(), "Integer", null, false)) {
                return Result.build(1001, "sg001保护地id不能为空");
            }
            Result list = iSt4SysSgService.delete(param);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.build(1005,ResultMsg.MSG_1005);
    }
    /**
     * 保护地数据级别
     * @param data
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value="保护地级别列表下拉框",notes = "保护地级别列表下拉框",response = St4ScsCb.class)
    @RequestMapping(value = "/listReserveRank",method = RequestMethod.POST)
    public Result listReserveRank(@RequestBody @ApiParam(name="保护地级别下拉", value="json格式", required=true) Swagger<St4ScsCb> data, HttpServletRequest request, HttpServletResponse response) {
        try {
            Result result = new Result();
            List<St4ScsCb> list = iSt4ScsCbService.list();
            result.setRows(list);
            result.setStatus(1000);
            result.setMsg("保护地级别列表数据查询成功！");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.build(1005,ResultMsg.MSG_1005);
    }
    /**
     * 保护地类型列表
     * @param data
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value="保护地类型下拉",notes = "保护地类型下拉",response = St4ScsCa.class)
    @RequestMapping(value = "/listReserveType",method = RequestMethod.POST)
    public Result listReserveType(@RequestBody @ApiParam(name="保护地类型下拉", value="json格式", required=true) Swagger<St4ScsCa> data, HttpServletRequest request, HttpServletResponse response) {
        try {

            Result result = new Result();
            List<St4ScsCa> list = iSt4ScsCaService.list();
            result.setRows(list);
            result.setStatus(1000);
            result.setMsg("保护地类型列表数据查询成功！");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.build(1005,ResultMsg.MSG_1005);
    }

    /**
     * 保护地目录树
     * @param data
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value="保护地目录树",notes = "保护地目录树",response = St4SysSg.class)
    //@ApiImplicitParam(name="data",value="保护地数据实体对象",required = true,dataType = "")
    @RequestMapping(value = "/listTreeSt4SysSg",method = RequestMethod.POST)
    public Result listTreeSt4SysSg(@RequestBody @ApiParam(name="保护地列表", value="json格式", required=true) Swagger<St4SysSg> data, HttpServletRequest request, HttpServletResponse response) {
        try {
            St4SysSg param = new St4SysSg();
            Result list = iSt4SysSgService.listTreeReserveData(param);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.build(1005,ResultMsg.MSG_1005);
    }
    /**
     * 保护地下拉框
     * @param
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value="保护地下拉框",notes = "保护地下拉框",response = St4SysSg.class)
    //@ApiImplicitParam(name="data",value="保护地数据实体对象",required = true,dataType = "")Map<String, Object> requestData
    @RequestMapping(value = "/listToSelect",method = RequestMethod.POST)
    public Result listToSelect(@RequestBody @ApiParam(name="保护地下拉框", value="json格式", required=true) Swagger<St4SysSg> data, HttpServletRequest request, HttpServletResponse response) {
        try {
            St4SysSg param = data.getData();
            Result list = iSt4SysSgService.listByParam(param);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.build(1005,ResultMsg.MSG_1005);
    }
    /**
     * 保护地数据级别
     * @param data
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value="行政区划下拉框数据",notes = "行政区划列表下拉框",response = St4ScsCb.class)
    @RequestMapping(value = "/listAdminRegion",method = RequestMethod.POST)
    public Result listAdminRegion(@RequestBody @ApiParam(name="行政区划列表下拉框", value="json格式", required=true) Swagger<St4SysSd> data, HttpServletRequest request, HttpServletResponse response) {
        try {
            Result result = new Result();
            List<St4SysSd> list = iSt4SysSdService.list();
            result.setRows(list);
            result.setStatus(1000);
            result.setMsg("行政区划列表数据查询成功！");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.build(1005,ResultMsg.MSG_1005);
    }
    /**
     * 保护地数据按行政区划查询
     * @param data
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value="保护地数据按行政区划查询",notes = "保护地数据按行政区划查询",response = St4SysSg.class)
    @RequestMapping(value = "/listReserveByAdminRegion",method = RequestMethod.POST)
    public Result listReserveByAdminRegion(@RequestBody @ApiParam(name="保护地数据按行政区划查询", value="json格式", required=true) Swagger<St4SysSg> data, HttpServletRequest request, HttpServletResponse response) {
        try {
            St4SysSg param = data.getData();
            Result res = iSt4SysSgService.listByParam(param);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.build(1005,ResultMsg.MSG_1005);
    }
    /**
     * 保护地数据详情接口
     * @param data
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value="保护地数据详情接口（修改回显）当传入的是保护区id集合的时候返回值去rows里面取详情数据，" +
            "如果传入的是单个保护区id则去data里面取详情数据",notes = "保护地数据详情接口（修改回显）",response = St4SysSg.class)
    @RequestMapping(value = "/getDataById",method = RequestMethod.POST)
    public Result getDataById(@RequestBody @ApiParam(name="保护地数据详情接口", value="json格式", required=true) Swagger<St4SysSg> data, HttpServletRequest request,
                              HttpServletResponse response) {
        try {
            St4SysSg param = data.getData();
            //保护地数据id
            if (!ObjectUtils.isNotNullAndEmpty(param.getSg001())&&!ObjectUtils.isNotNullAndEmpty(param.getSgList())) {
                return Result.build(1001, "sg001保护地id不能为空");
            }
            Result res = iSt4SysSgService.getDataById(param,request);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.build(1005,ResultMsg.MSG_1005);
    }
    @ApiOperation(value="查询出当前人员已授权的保护地边界（修改回显）此接口的sa001必传，返回信息只需要关注sg001(保护地主键)和sg008(保护地名称)即可",notes = "查询出当前人员已授权的保护地边界（修改回显）",response = St4SysSg.class)
    @RequestMapping(value = "/getReserveDataBySaid",method = RequestMethod.POST)
    public Result getReserveDataBySaid(@RequestBody @ApiParam(name="查询出当前人员已授权的保护地边界（修改回显）", value="json格式", required=true)
                                                   Swagger<St4SysSa> data) {
        St4SysSa sa = data.getData();
        if(sa.getSa001()==null){
            return Result.build(1001,"人员主键sa001"+ResultMsg.MSG_1001);
        }

        return iSt4SysSgService.getReserveDataBySaid(sa.getSa001());
    }
    /**
     * 授权保护地边界
     * @param spSwagger
     * @return
     */
    @ApiOperation(value="授权保护地边界",notes = "授权保护地边界",response = GiveReserve.class)
    @RequestMapping(value="/giveReserve",method = RequestMethod.POST)
    public Result giveReserve(@ApiParam(name="授权保护地边界", value="json格式", required=true)@RequestBody Swagger<GiveReserve> spSwagger) {
        GiveReserve sp = spSwagger.getData();
        List<Integer> reserveList = sp.getReserveIdList();
        Integer uids = sp.uid;

        if(uids==0){
            return Result.build(1001,"下发人员不能为空");
        }
        return  st4PoSaSgService.giveReserve(uids,reserveList);

    }


}


