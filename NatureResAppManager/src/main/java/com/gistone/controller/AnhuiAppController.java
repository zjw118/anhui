package com.gistone.controller;

import com.auth0.jwt.JWT;
import com.gistone.VO.ResultVO;
import com.gistone.entity.St4ScsCab;
import com.gistone.entity.St4ScsCd;
import com.gistone.pkname.Swagger;
import com.gistone.service.ISt4ScsCabService;
import com.gistone.service.ISt4ScsCdService;
import com.gistone.service.ISt4ScsCkService;
import com.gistone.service.ISysUserService;
import com.gistone.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/app")
@Api(value="安徽红线app问题反馈接口",tags = "安徽红线app问题反馈接口")
public class AnhuiAppController {
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISt4ScsCdService st4ScsCdService;
    @Autowired
    private ISt4ScsCkService st4ScsCkService;
    //党建信息
    @Autowired
    private ISt4ScsCabService iSt4ScsCabService;
    /**
     * app上传反馈问题接口
     * @param data
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value="app上传反馈问题接口",notes = "app上传反馈问题接口",response = St4ScsCd.class)
    @RequestMapping(value = "/insertBackProblem",method = RequestMethod.POST)
    public ResultVO insertBackProblem(@ApiParam(name="app上传反馈问题接口", value="json格式", required=true)@RequestBody Swagger<St4ScsCd> data,
                            HttpServletRequest request, HttpServletResponse response) {
        St4ScsCd cd = data.getData();
        String token = request.getHeader("token");
        try{
            String userId= JWT.decode(token).getAudience().get(0);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;//  st4ScsCkService.insertLedgerLd(cd,userId);

    }
    /**
     * 党建信息
     *
     * @param data
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "党建信息详情", notes = "党建信息详情", response = St4ScsCab.class)
    @RequestMapping(value = "/getByIdForApp", method = RequestMethod.POST)
    public Result getByIdForApp(@RequestBody @ApiParam(name = "党建信息id", value = "json格式", required = true) Swagger<St4ScsCab> data, HttpServletRequest request, HttpServletResponse response) {
        try {
            St4ScsCab param = data.getData();
            //党建信息id
            if (!RegUtil.CheckParameter(param.getCab001(), "Integer", null, false)) {
                return Result.build(1001, "cab001党建信息id不能为空");
            }
            Result result = iSt4ScsCabService.getByIdForApp(param);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.build(1005, ResultMsg.MSG_1005);
    }

    @ApiOperation(value="党建信息列表",notes = "党建信息列表",response = St4ScsCab.class)
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
}
