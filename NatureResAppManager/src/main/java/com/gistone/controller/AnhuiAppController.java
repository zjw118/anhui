package com.gistone.controller;

import com.auth0.jwt.JWT;
import com.gistone.entity.St4ScsCd;
import com.gistone.entity.St4ScsCk;
import com.gistone.entity.St4ScsCo;
import com.gistone.pkname.Swagger;
import com.gistone.service.ISt4ScsCdService;
import com.gistone.service.ISt4ScsCkService;
import com.gistone.service.ISysUserService;
import com.gistone.util.ObjectUtils;
import com.gistone.util.ResultCp;
import com.gistone.util.ResultMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/app")
@Api(value="安徽红线app接口",tags = "安徽红线app接口的的根目录")
public class AnhuiAppController {
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISt4ScsCdService st4ScsCdService;
    @Autowired
    private ISt4ScsCkService st4ScsCkService;

    @ApiOperation(value="app同步任务下的斑块信息接口",notes = "app同步任务下的斑块信息接口",response = St4ScsCd.class)
    @RequestMapping(value = "/sysSpotData",method = RequestMethod.POST)
    public ResultCp sysSpotData(@ApiParam(name="app同步任务下的斑块信息接口", value="json格式", required=true)@RequestBody Swagger<St4ScsCd> data,
                            HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("token");
        ResultCp cp = new ResultCp();
        cp.setStatus(1000);
        String userId="78";
        try{
           // userId = JWT.decode(token).getAudience().get(0);
        }catch (Exception e){
            e.printStackTrace();
            cp.setMsg("无token，请重新登录");
            return cp;
        }
        return st4ScsCdService.sysSpotData(Integer.valueOf(userId));
    }
    @ApiOperation(value="app提交核查信息接口",notes = "app提交核查信息接口",response = St4ScsCd.class)
    @RequestMapping(value = "/insertSpotDataFromApp",method = RequestMethod.POST)
    public ResultCp insertSpotDataFromApp(@ApiParam(name="app提交核查信息接口", value="json格式", required=true)@RequestBody Swagger<St4ScsCd> data,
                            HttpServletRequest request, HttpServletResponse response) {
        St4ScsCd cd = data.getData();
        String token = request.getHeader("token");
        String userId= JWT.decode(token).getAudience().get(0);
        return  st4ScsCkService.insertLedgerLd(cd,userId);

    }
    /**
     * todo  （安徽暂用使用）台账表提交接口
     * @param
     * @return
     */
    @ApiOperation(value = "（绿盾使用）台账表提交接口", notes = "（绿盾使用）台账表提交接口", response = St4ScsCd.class)
    @PostMapping("/insertLedgerLd")
    public ResultCp insertLedgerLd( @RequestBody @ApiParam(name = "（绿盾使用）台账表提交接口", value = "json格式", required = true)  Swagger<St4ScsCd>
                                            data,HttpServletRequest request
    ) {

        St4ScsCd param = data.getData();
        //问题点类型
        if(!ObjectUtils.isNotNullAndEmpty(param.getCd007())){
            return ResultCp.build(1001,"问题点类型cd007"+ ResultMsg.MSG_1001);
        }
        //问题点编号
        if(!ObjectUtils.isNotNullAndEmpty(param.getCd004())){
            return ResultCp.build(1001,"问题点编号cd004"+ResultMsg.MSG_1001);
        }
        if(!ObjectUtils.isNotNullAndEmpty(param.getCd002())){
            return ResultCp.build(1001,"经度cd002"+ResultMsg.MSG_1001);
        }
        if(!ObjectUtils.isNotNullAndEmpty(param.getCd003())){
            return ResultCp.build(1001,"纬度cd003"+ResultMsg.MSG_1001);
        }
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId = JWT.decode(token).getAudience().get(0);
        ResultCp res = st4ScsCkService.insertLedgerLd(param,userId);

        return res;

    }
}