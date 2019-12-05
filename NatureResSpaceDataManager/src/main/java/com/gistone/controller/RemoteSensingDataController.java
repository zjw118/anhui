package com.gistone.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.VO.ResultVO;
import com.gistone.entity.LsSuanfa;
import com.gistone.entity.RemoteSensingData;
import com.gistone.entity.St4ScsCt;
import com.gistone.pkname.Swagger;
import com.gistone.service.IRemoteSensingDataService;
import com.gistone.util.JavaBeanUtil;
import com.gistone.util.Result;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lx
 * @since 2019-12-04
 */
@RestController
@RequestMapping("/api/remoteSensingData")
public class RemoteSensingDataController {

    @Autowired
    private IRemoteSensingDataService iRemoteSensingDataService;

    @PostMapping("/list")
    public ResultVO list(@RequestBody @ApiParam(name = "遥感影像数据列表", value = "json格式", required = true) Swagger<RemoteSensingData> data,
                         HttpServletRequest request) {
        //请求参数格式校验
        RemoteSensingData param = data.getData();
        if (param == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        //页码
        if(param.getPageNumber() == null){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "pageNumber不能为空！");
        }
        //每页条数
        if(param.getPageSize() == null){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "pageSize！");
        }
        //标识码
        if(param.getRsdBsm() == null){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "pageSize！");
        }
        Map<String, Object> result = iRemoteSensingDataService.list( param);


        return ResultVOUtil.success(result);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultVO add(@RequestBody @ApiParam(name = "遥感影像数据添加", value = "json格式", required = true) Swagger<RemoteSensingData> data,
                        HttpServletRequest request) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //请求参数格式校验
        RemoteSensingData param = data.getData();
        if (param == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        //标识码
        if(param.getRsdBsm() == null){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "rsdBsm不能为空！");
        }
        //要素代码
        if(param.getRsdYsdm() == null){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "rsdYsdm不能为空！");
        }
        //数据名称
        if(param.getRsdSjmc() == null){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "rsdSjmc不能为空！");
        }
        param.setRsdAddTime(LocalDateTime.now());
        iRemoteSensingDataService.save(param);
        return ResultVOUtil.success();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultVO update(@RequestBody @ApiParam(name = "遥感影像数据修改", value = "json格式", required = true) Swagger<RemoteSensingData> data,
                        HttpServletRequest request) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //请求参数格式校验
        RemoteSensingData param = data.getData();
        if (param == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        //数据id
        if(param.getRsdId() == null){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "rsdId不能为空！");
        }
        iRemoteSensingDataService.updateById(param);
        return ResultVOUtil.success();
    }
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultVO delete(@RequestBody @ApiParam(name = "遥感影像数据删除", value = "json格式", required = true) Swagger<RemoteSensingData> data,
                        HttpServletRequest request) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //请求参数格式校验
        RemoteSensingData param = data.getData();
        if (param == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        //数据id
        if(param.getRsdId() == null){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "rsdId不能为空！");
        }
        param.setRsdDelFlag(0);
        iRemoteSensingDataService.updateById(param);
        return ResultVOUtil.success();
    }
}
