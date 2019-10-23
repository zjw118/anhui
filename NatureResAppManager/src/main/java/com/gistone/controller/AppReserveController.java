//todo 安徽红线需不需要保护地

/*
package com.gistone.controller;

import com.gistone.entity.St4SysSg;
import com.gistone.pkname.Swagger;
import com.gistone.service.ISt4ScsCaaService;
import com.gistone.service.ISt4ScsCbService;
import com.gistone.service.ISt4SysSaService;
import com.gistone.service.ISt4SysSgService;
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

*/
/**
 * @author LiuXiong
 * @since 2019-08-06
 * app接口保护地相关
 *//*

@RestController
@RequestMapping("/api/app/reserve")
@Api(value="保护地列表接口",tags = "保护地列表接口")
public class AppReserveController {

    @Autowired
    private ISt4SysSaService userService;

    @Autowired
    private ISt4SysSgService iSt4SysSgService;

    @Autowired
    private ISt4ScsCaaService iSt4ScsCaService;

    @Autowired
    private ISt4ScsCbService iSt4ScsCbService;


    */
/**
     * 保护地数据列表
     * @param
     * @param request
     * @param response
     * @return
     *//*

    @ApiOperation(value="保护地列表",notes = "保护地列表加边界数据",response = St4SysSg.class)
    @RequestMapping(value = "/listReserveData",method = RequestMethod.POST)
    public ResultCp listReserveData(@ApiParam(name="unionId,用户唯一标识", value="json格式", required=true)@RequestBody Swagger<St4SysSg> data, HttpServletRequest request, HttpServletResponse response) {
        try {
            St4SysSg param = data.getData();
            ResultCp list = iSt4SysSgService.appList(param);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultCp.build(1005, ResultMsg.MSG_1005);
    }
    */
/**
     * 保护地数据名称列表（移动端添加自定义问题点使用）
     * @param
     * @param request
     * @param response
     * @return
     *//*

    @ApiOperation(value="保护地数据名称列表（移动端添加自定义问题点使用）",notes = "保护地数据名称列表（移动端添加自定义问题点使用）",response = St4SysSg.class)
    @RequestMapping(value = "/listDataToPoint",method = RequestMethod.POST)
    public ResultCp listDataToPoint(@ApiParam(name="可根据保护地名称查询", value="json格式", required=true)@RequestBody Swagger<St4SysSg> data, HttpServletRequest request, HttpServletResponse response) {
        try {
            St4SysSg param = data.getData();
            ResultCp list = iSt4SysSgService.appListToPoint(param);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultCp.build(1005, ResultMsg.MSG_1005);
    }


}
*/
