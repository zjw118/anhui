package com.gistone.controller;

import com.gistone.VO.ResultVO;
import com.gistone.entity.St4ScsCba;
import com.gistone.entity.St4ScsCbb;
import com.gistone.pkname.Swagger;
import com.gistone.service.ISt4ScsCbaService;
import com.gistone.service.ISt4ScsCbbService;
import com.gistone.util.ObjectUtils;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultMsg;
import com.gistone.util.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhaojingwei
 * @since 2019-07-26
 */
@Api(value="(江苏用)红线台账接口",tags = "(江苏用)红线台账接口")
@RestController
@RequestMapping("/api/redLineLedger")
public class RedLineController {
    @Autowired
    private ISt4ScsCbaService st4ScsCbaService;

    @Autowired
    private ISt4ScsCbbService st4ScsCbbService;
    /**
     * 台账表的插入2产品化
     * @param cbaLedger
     * @return
     */
    @ApiOperation(value="生态保护红线边界数据列表(按照标识码BMS查询模糊查询按照调整时间查询起止时间还是传递strTime和endTime)",notes = "生态保护红线边界数据列表",response = St4ScsCba.class)
    @RequestMapping(value="/listRedLineLedger",method = RequestMethod.POST)
    public ResultVO listRedLineLedger(@RequestBody @ApiParam(name="生态保护红线边界数据列表", value="json格式", required=true)
                                      Swagger<St4ScsCba> cbaLedger) {
        St4ScsCba cba = cbaLedger.getData();
        if(!ObjectUtils.isNotNullAndEmpty(cba.getPageNumber())||!ObjectUtils.isNotNullAndEmpty(cba.getPageSize())){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), ResultMsg.MSG_1018);
        }
        return st4ScsCbaService.listRedLineLedger(cba);
    }
    /**
     * 添加红线基础台账数据
     * @param
     * @return
     */
    @RequestMapping(value = "/addRedLine", method = RequestMethod.POST)
    public ResultVO addRedLine(@RequestBody @ApiParam(name = "红线边界数据添加", value = "json格式", required = true) Swagger<St4ScsCba> data,
                        HttpServletRequest request) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //请求参数格式校验
        St4ScsCba param = data.getData();
        if (param == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        //要素代码
        if(param.getYsdm() == null){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "ysdm(要素代码)不能为空！");
        }
        //红线类型
        if(param.getHxlx() == null){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "hxlx(红线类型)不能为空！");
        }
        //红线命名
        if(param.getHxmm() == null){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "hxmm(红线命名)不能为空！");
        }
        //红线编码
        if(param.getHxbm() == null){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "hxbm(红线编码)不能为空！");
        }
        param.setAdd_time(LocalDateTime.now());
        st4ScsCbaService.save(param);
        return ResultVOUtil.success();
    }
    /**
     * 修改红线基础台账数据
     * @param
     * @return
     */
    @RequestMapping(value = "/updateRedLine", method = RequestMethod.POST)
    public ResultVO updateRedLine(@RequestBody @ApiParam(name = "红线基础台账修改", value = "json格式", required = true) Swagger<St4ScsCba> data,
                           HttpServletRequest request) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //请求参数格式校验
        St4ScsCba param = data.getData();
        if (param == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        //数据id
        if(param.getId() == null){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "id(红线基础台账)不能为空！");
        }
        st4ScsCbaService.updateById(param);
        return ResultVOUtil.success();
    }
    /**
     * 删除红线基础台账数据
     * @param
     * @return
     */
    @RequestMapping(value = "/deleteRedLine", method = RequestMethod.POST)
    public ResultVO deleteRedLine(@RequestBody @ApiParam(name = "红线基础台账删除", value = "json格式", required = true) Swagger<St4ScsCba> data,
                           HttpServletRequest request) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //请求参数格式校验
        St4ScsCba param = data.getData();
        if (param == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        //数据id
        if(param.getId() == null){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "Id不能为空！");
        }
        param.setDel("0");
        st4ScsCbaService.updateById(param);
        return ResultVOUtil.success();
    }
    /** 保护地台账数据列表
     * @param cbbLedger
     * @return
     */
    @ApiOperation(value="各类保护地数据列表 (按照标识码BMS查询模糊查询按照调整时间查询起止时间还是传递strTime和endTime)",notes = "列表数据列表",response = St4ScsCbb.class)
    @RequestMapping(value="/listReserveData",method = RequestMethod.POST)
    public ResultVO listReserveData(@RequestBody @ApiParam(name="各类保护地数据 列表", value="json格式", required=true)
                                        Swagger<St4ScsCbb> cbbLedger) {
        St4ScsCbb cbb = cbbLedger.getData();
        if(!ObjectUtils.isNotNullAndEmpty(cbb.getPageNumber())||!ObjectUtils.isNotNullAndEmpty(cbb.getPageSize())){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), ResultMsg.MSG_1018);
        }
        return st4ScsCbbService.listReserveData(cbb);

    }
    /**
     * 添加保护地台账数据
     * @param
     * @return
     */
    @RequestMapping(value = "/addReserveData", method = RequestMethod.POST)
    public ResultVO addReserveData(@RequestBody @ApiParam(name = "保护地基础台账数据添加", value = "json格式", required = true) Swagger<St4ScsCbb> data,
                        HttpServletRequest request) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //请求参数格式校验
        St4ScsCbb param = data.getData();
        if (param == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        //标识码
        if(param.getBms() == null){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "bms(标识码)不能为空！");
        }
        //名称
        if(param.getMc() == null){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "mc(名称)不能为空！");
        }
        param.setAdd_time(LocalDateTime.now());
        st4ScsCbbService.save(param);
        return ResultVOUtil.success();
    }
    /**
     * 修改保护地台账数据
     * @param
     * @return
     */
    @RequestMapping(value = "/updateReserveData", method = RequestMethod.POST)
    public ResultVO updateReserveData(@RequestBody @ApiParam(name = "保护地基础台账数据修改", value = "json格式", required = true) Swagger<St4ScsCbb> data,
                           HttpServletRequest request) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //请求参数格式校验
        St4ScsCbb param = data.getData();
        if (param == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        //数据id
        if(param.getId() == null){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "Id不能为空！");
        }
        st4ScsCbbService.updateById(param);
        return ResultVOUtil.success();
    }
    /**
     * 删除保护地基础台账数据
     * @param
     * @return
     */
    @RequestMapping(value = "/deleteReserveData", method = RequestMethod.POST)
    public ResultVO deleteReserveData(@RequestBody @ApiParam(name = "遥感影像数据删除", value = "json格式", required = true) Swagger<St4ScsCbb> data,
                           HttpServletRequest request) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //请求参数格式校验
        St4ScsCbb param = data.getData();
        if (param == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        //数据id
        if(param.getId() == null){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "Id不能为空！");
        }
        param.setDel(0);
        st4ScsCbbService.updateById(param);
        return ResultVOUtil.success();
    }
}
