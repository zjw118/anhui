package com.gistone.controller;


import com.gistone.annotation.PassToken;
import com.gistone.entity.St4ScsCaa;
import com.gistone.entity.St4ScsCab;
import com.gistone.entity.St4SysSa;
import com.gistone.pkname.Swagger;
import com.gistone.service.ISt4ScsCaaService;
import com.gistone.service.ISt4ScsCabService;
import com.gistone.service.ISt4SysSaService;
import com.gistone.util.RegUtil;
import com.gistone.util.Result;
import com.gistone.util.ResultMsg;
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

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiuXiong
 * @since 2019-08-27
 */
@RestController
@RequestMapping("/api/app/manager")
@Api(value="app系统设置相关接口",tags = "版本更新、个人信息、修改密码等                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      ")
public class AppManagerController {
    //版本更新
    @Autowired
    private ISt4ScsCaaService iSt4ScsCaaService;
    //保护地
    @Autowired
    private ISt4SysSaService iSt4SysSaService;
    //党建信息
    @Autowired
    private ISt4ScsCabService iSt4ScsCabService;

    /**
     * app版本更新
     * @param data
     * @param request
     * @param response
     * @return
     */
    @PassToken
    @ApiOperation(value="app版本更新",notes = "app版本更新",response = St4ScsCaa.class)
    @RequestMapping(value = "/getNewVersion",method = RequestMethod.POST)
    public Result getNewVersion(@RequestBody @ApiParam(name="当前APP版本号", value="json格式", required=true) Swagger<St4ScsCaa> data, HttpServletRequest request, HttpServletResponse response) {
        try {
            St4ScsCaa param = data.getData();
            //版本号
            if (!RegUtil.CheckParameter(param.getCaa003(), "Integer", null, false)) {
                return Result.build(1001, "caa003版本号不能为空");
            }
            Result result = iSt4ScsCaaService.getNewVersion(param);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.build(1005, ResultMsg.MSG_1005);
    }
    /**
     * app党建信息列表
     * @param data
     * @param request
     * @param response
     * @return
     */
    @PassToken
    @ApiOperation(value="党建信息列表",notes = "党建信息列表",response = St4ScsCaa.class)
    @RequestMapping(value = "/listForApp",method = RequestMethod.POST)
    public Result listForApp(@RequestBody @ApiParam(name="pageSize页容，pageNumber页码", value="json格式", required=true) Swagger<St4ScsCab> data, HttpServletRequest request, HttpServletResponse response) {
        try {
            St4ScsCab param = data.getData();
            //pageSize页容不能为空
            if (!RegUtil.CheckParameter(param.getPageSize(), "Integer", null, false)) {
                return Result.build(1001, "pageSize页容不能为空");
            }
            //pageNumber页码不能为空
            if (!RegUtil.CheckParameter(param.getPageNumber(), "Integer", null, false)) {
                return Result.build(1001, "pageNumber页码不能为空");
            }
            //cab015数据类型不能为空
            if (!RegUtil.CheckParameter(param.getCab015(), "Integer", null, false)) {
                return Result.build(1001, "cab015数据类型不能为空");
            }
            Result result = iSt4ScsCabService.listForApp(param);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.build(1005, ResultMsg.MSG_1005);
    }
    /**
     * app用户个人信息修改（包括修改密码）
     * @param data
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value="app用户个人信息修改（包括修改密码）",notes = "app用户个人信息修改（包括修改密码）",response = St4ScsCaa.class)
    @RequestMapping(value = "/updateAppUser",method = RequestMethod.POST)
    public Result updateAppUser(@RequestBody @ApiParam(name="app用户主键id,旧密码传：sa009Old", value="json格式", required=true) Swagger<St4SysSa> data, HttpServletRequest request, HttpServletResponse response) {
        try {
            St4SysSa param = data.getData();
            //版本号
            if (!RegUtil.CheckParameter(param.getSa001(), "Integer", null, false)) {
                return Result.build(1001, "sa001用户主键id不能为空");
            }
            Result result = iSt4SysSaService.updateAppUser(param);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.build(1005, ResultMsg.MSG_1005);
    }
}
