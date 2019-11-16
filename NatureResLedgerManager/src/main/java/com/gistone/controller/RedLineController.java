package com.gistone.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.VO.ResultVO;
import com.gistone.entity.St4ScsCba;
import com.gistone.entity.St4ScsCbb;
import com.gistone.entity.St4ScsCk;
import com.gistone.pkname.Swagger;
import com.gistone.service.ISt4ScsCbaService;
import com.gistone.service.ISt4ScsCbbService;
import com.gistone.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
    public ResultVO doSelfStage(@RequestBody @ApiParam(name="生态保护红线边界数据列表", value="json格式", required=true)
                                      Swagger<St4ScsCba> cbaLedger) {
        St4ScsCba cba = cbaLedger.getData();
        if(!ObjectUtils.isNotNullAndEmpty(cba.getPageNumber())||!ObjectUtils.isNotNullAndEmpty(cba.getPageSize())){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), ResultMsg.MSG_1018);
        }
        return st4ScsCbaService.listRedLineLedger(cba);
    }
    /**
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
}
