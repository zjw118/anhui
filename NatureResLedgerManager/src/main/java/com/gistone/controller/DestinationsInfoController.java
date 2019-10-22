package com.gistone.controller;

        import com.auth0.jwt.JWT;
        import com.gistone.annotation.PassToken;
        import com.gistone.entity.*;
        import com.gistone.pkname.Swagger;
        import com.gistone.service.ISt4ScsCcService;
        import com.gistone.service.ISt4ScsCeService;
        import com.gistone.service.ISt4ScsCfService;
        import com.gistone.service.ISt4ScsCkService;
        import com.gistone.util.RegUtil;
        import com.gistone.util.Result;
        import io.swagger.annotations.Api;
        import io.swagger.annotations.ApiOperation;
        import io.swagger.annotations.ApiParam;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.MediaType;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestBody;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;

        import javax.servlet.http.HttpServletRequest;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

/**
 * @ClassName DestinationsInfoController
 * @Description TODO
 * @Author xxh
 * @Date 2019/8/15 17:55
 * @Version 1.0
 **/
@RestController
@Api(value = "航点数据信息查询", tags = "航点数据信息查询", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping("/api/ledger")
public class DestinationsInfoController {

    @Autowired
    private ISt4ScsCcService ccService;

    @Autowired
    private ISt4ScsCeService ceService;

    @Autowired
    private ISt4ScsCfService cfService;

    @Autowired
    private ISt4ScsCkService ckService;


    @PassToken
    @ApiOperation(value = "查询航点信息", notes = "此接口返回航点信息分页数据", response = Result.class)
    @PostMapping("/listDestinationsByPage")
    public Result listDestinationsByPage(HttpServletRequest request, @RequestBody @ApiParam(name = "巡护信息分页查询", value = "json格式", required = true)
            Swagger<St4ScsCc> data) {
        St4ScsCc st4ScsCc = data.getData();
        if (st4ScsCc == null) {
            return Result.build(1001, "查询参数不能为空");
        }
        //每页条数
        if (!RegUtil.CheckParameter(st4ScsCc.getPageSize(), "Integer", null, false)) {
            return Result.build(1001, "查询pageSize不能为空");
        }
        //开始索引
        if (!RegUtil.CheckParameter(st4ScsCc.getPageNumber(), "Integer", null, false)) {
            return Result.build(1001, "查询pageNumber不能为空");
        }
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId = JWT.decode(token).getAudience().get(0);
        St4SysSa seUser = new St4SysSa();
        seUser.setSa001(Integer.valueOf(userId));
        return ccService.listSt4ScsCcByPage(st4ScsCc,seUser);
    }

    @PassToken
    @ApiOperation(value = "根据航点标识查询航点详情信息", notes = "此接口返回航点详情信息数据", response = Result.class)
    @PostMapping("/getDestinationsByCC002")
    public Result getDestinationsByCC002(HttpServletRequest request,@RequestBody @ApiParam(name = "航点唯一标识cc002", value = "json格式", required = true) Swagger<String> data) {
        String cc002 =data.getData();
        Map<String,Object> map=new HashMap<>();
        St4ScsCc cc=ccService.getSt4ScsCcByCc002(cc002);
        if(cc!=null) {
            List<St4ScsCe> ceList = ceService.getSt4ScsCeByCc002(cc002);
            map.put("ceList",ceList);
            map.put("cfInfo",new HashMap());
            map.put("ckInfo",new HashMap());
            if(cc.getCc003()==1) {
                St4ScsCf cf = cfService.getSt4ScsCfByCc002(cc002);
                map.put("cfInfo",cf==null?new HashMap():cf);
            }else if(cc.getCc003()==2){
                St4ScsCk ck = ckService.getSt4ScsCkByCc002(cc002);
                map.put("ckInfo",ck==null?new HashMap():ck);
            }
        }else{
            return Result.build(1001,"航点数据错误");
        }
        return Result.build(1000,"查询成功",map);
    }

}
