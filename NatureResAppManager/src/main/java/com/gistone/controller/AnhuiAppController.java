package com.gistone.controller;

import com.auth0.jwt.JWT;
import com.gistone.VO.ResultVO;
import com.gistone.entity.St4ScsCd;
import com.gistone.pkname.Swagger;
import com.gistone.service.ISt4ScsCdService;
import com.gistone.service.ISt4ScsCkService;
import com.gistone.service.ISysUserService;
import com.gistone.util.ObjectUtils;
import com.gistone.util.ResultCp;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
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

}
