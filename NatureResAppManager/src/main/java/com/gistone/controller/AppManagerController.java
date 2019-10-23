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
import com.gistone.util.ResultCp;
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
    public ResultCp getNewVersion(@RequestBody @ApiParam(name="当前APP版本号", value="json格式", required=true) Swagger<St4ScsCaa> data, HttpServletRequest request, HttpServletResponse response) {
        try {
            St4ScsCaa param = data.getData();
            //版本号
            if (!RegUtil.CheckParameter(param.getCaa003(), "Integer", null, false)) {
                return ResultCp.build(1001, "caa003版本号不能为空");
            }
            ResultCp result = iSt4ScsCaaService.getNewVersion(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultCp.build(1005, ResultMsg.MSG_1005);
    }

    /**
     * //todo 这里得走安徽红线自己的修改密码的方法
     * app用户个人信息修改（包括修改密码）
     * @param data
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value="app用户个人信息修改（包括修改密码）",notes = "app用户个人信息修改（包括修改密码）",response = St4ScsCaa.class)
    @RequestMapping(value = "/updateAppUser",method = RequestMethod.POST)
    public ResultCp updateAppUser(@RequestBody @ApiParam(name="app用户主键id,旧密码传：sa009Old", value="json格式", required=true) Swagger<St4SysSa> data, HttpServletRequest request, HttpServletResponse response) {
        try {
            St4SysSa param = data.getData();
            //版本号
            if (!RegUtil.CheckParameter(param.getSa001(), "Integer", null, false)) {
                return ResultCp.build(1001, "sa001用户主键id不能为空");
            }
            ResultCp result = iSt4SysSaService.updateAppUser(param);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultCp.build(1005, ResultMsg.MSG_1005);
    }
}
