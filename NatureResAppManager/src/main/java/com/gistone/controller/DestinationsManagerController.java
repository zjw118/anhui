package com.gistone.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.SawggerModel.Destination;
import com.gistone.entity.*;
import com.gistone.pkname.Swagger;
import com.gistone.service.IDestinationsManagerService;
import com.gistone.service.ISt4ScsChService;
import com.gistone.service.ISt4ScsCpService;
import com.gistone.util.ObjectUtils;
import com.gistone.util.ResultCp;
import com.gistone.util.ResultMsg;
import io.swagger.annotations.*;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @ClassName DestinationsManagerController
 * @Description 巡护APP调用接口
 * @Author xxh
 * @Date 2019/8/14 15:38
 * @Version 1.0
 **/
@RestController
@Api(value = "航点数据APP添加管理", tags = "航点数据管理", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping("/api/app")
public class DestinationsManagerController {

    @Autowired
    private ISt4ScsCpService cpService;//获取任务与航点数据关系字段

    @Autowired
    private IDestinationsManagerService destinationsManagerService;
    @Autowired
    private ISt4ScsChService iSt4ScsChService;

    /**
     * todo NPM航点是否能重复提交
     * @param request
     * @param data
     * @return
     */

    @ApiOperation(value = "插入航点信息", notes = "此接口返回插入成功与否", response = ResultCp.class)
    @PostMapping("/insertDestinations")
    public ResultCp insertDestinations(HttpServletRequest request,
                                     @RequestBody @ApiParam(name = "巡护信息添加", value = "json格式", required = true)
                                             Swagger<Destinations> data) {

        Destinations des  = data.getData();
        //获取用户id
        Integer userId = 0;
        try {
            String token = request.getHeader("token");// 从 http 请求头中取出 token
            String UserId = JWT.decode(token).getAudience().get(0);
            userId = Integer.parseInt(UserId);
        } catch (Exception e) {
            request.setAttribute("attrname", "token无效，请重新登录");
            throw new RuntimeException("token无效，请重新登录");
        }
        if (data == null) {
            return ResultCp.build(1001, "添加信息不能为空");
        }
        Destinations destinations = data.getData();
        if (destinations == null) {
            return ResultCp.build(1001, "添加信息不能为空");
        }
        if (destinations.getDestination() == null) {
            return ResultCp.build(1001, "添加信息不能为空");
        }
        if (destinations.getDestination().size() <1) {
            return ResultCp.build(1001, "添加信息不能为空");
        }
        List<St4ScsCc> scsCcList = new ArrayList<>();
        List<St4ScsCf> scsCfList = new ArrayList<>();
        List<St4ScsCk> scsCkList = new ArrayList<>();
        List<St4ScsCe> sysceList = new ArrayList<>();
        LocalDateTime date = LocalDateTime.now();//获取当前时间
        List<Destination> destinationlist = destinations.getDestination();
        for (int i = 0; i < destinationlist.size(); i++) {
            Destination destination = destinationlist.get(i);
            //航点基础数据表
            St4ScsCc scscc = destination.getSt4ScsCc();
            if (scscc == null) {
                return ResultCp.build(1001, "添加信息不能为空");
            }
            if (scscc.getCc002() == null) {
                return ResultCp.build(1001, "添加航点信息错误");
            }
            if (scscc.getCc003() == null) {
                return ResultCp.build(1001, "航点类型不能为空");
            }
            if (scscc.getCc012() == null || "".equals(scscc.getCc012())) {
                return ResultCp.build(1001, "航点名称不能为空");
            }
            //先判断巡航点类型0巡护（st4_scs_cf）1核查(st4_scs_ck)
            if (scscc.getCc003() == 0) {//巡护表
                St4ScsCf scsCf = new St4ScsCf();
                St4ScsCf cf = destination.getSt4ScsCf();
                if (cf == null) {
                    return ResultCp.build(1001, "添加巡护信息不能为空");
                }
                if (cf.getCc002() == null || "".equals(cf.getCc002())) {
//                    new RuntimeException("插入航点信息错误");
                    return ResultCp.build(1001, "添加航点信息错误");
                }
                if (!cf.getCc002().equals(scscc.getCc002())) {
//                    new RuntimeException("插入航点信息错误");
                    return ResultCp.build(1001, "添加航点信息错误");
                }
                /*List<Map<String, Object>> fields = cpService.getSt4ScsCpInfo(taskId==null?"":taskId.toString(), 1);
                //判断必填字段是否填写
                AtomicReference<String> ret = new AtomicReference<>("");
                if(fields != null){
                    fields.stream().forEach(objectMap -> ret.set(fieldJudge(cf, null, objectMap)));
                    if (!"".equals(ret.get())) {
                        return ResultCp.build(1001, ret.get());
                    }
                    //设置字符串为空
                    scsCf = fieldIsEmpty(cf, null, fields, cf.getClass());*/
                scsCfList.add(cf);
                //}
            } else if (scscc.getCc003() == 1) { //核查(台账表)
                St4ScsCk scsCk = new St4ScsCk();
                St4ScsCk ck = destination.getSt4ScsCk();
                if (ck == null) {
                    return ResultCp.build(1001, "添加核查信息不能为空");
                }
                if (ck.getCd004() == null || "".equals(ck.getCd004())) {
//                    new RuntimeException("插入航点信息错误");
                    return ResultCp.build(1001, "问题点编号不能为空，添加航点信息错误");
                }
                Object taskId = destination.getTaskId();
                if(ObjectUtils.isNotNullAndEmpty(taskId)){
                    List<Map<String, Object>> fields = cpService.getSt4ScsCpInfo(taskId.toString(), 0);
                    //判断必填字段是否填写
                    if(fields==null){
                        return ResultCp.build(1001,"未查询到您所提交的核查任务绑定的核查项，请重新选择核查任务");
                    }
                    AtomicReference<String> ret = new AtomicReference<>("");
                    fields.stream().forEach(objectMap -> ret.set(fieldJudge(null, ck, objectMap)));
                    if (!"".equals(ret.get())) {
                        return ResultCp.build(1001, ret.get());
                    }
                    scsCk = fieldIsEmpty(null, ck, fields, ck.getClass());
                    ck.setCk086(date);
                    ck.setCk087(userId);
                    scsCkList.add(scsCk);
                }else {
                    return ResultCp.build(1001,"核查任务taskId"+ResultMsg.MSG_1001);
                }

            } else {
//                new RuntimeException("插入航点信息错误");
                return ResultCp.build(1001, "添加航点信息错误");
            }
            scscc.setCc007(date);
            scscc.setCc011(1);
            scscc.setCc008(userId);
            scsCcList.add(scscc);
            AtomicReference<String> ret = new AtomicReference<>("");
            //附件不为空
            if( destination.getSt4ScsCe() != null){
                destination.getSt4ScsCe().forEach(ce -> {
                    if (!ce.getCe002().equals(scscc.getCc002())) {
                        ret.set("添加航点信息错误，请联系管理员");
                    }
                });
                sysceList.addAll(destination.getSt4ScsCe());
            }

            if (!"".equals(ret.get())) {
                return ResultCp.build(1001, ret.get());
            }
        }
        Integer finalUserId = userId;
        sysceList.forEach(ce -> {
            ce.setCe005(date);
            ce.setCe006(finalUserId);
        });

        return destinationsManagerService.insertDestinationsManager(scsCcList, scsCkList, scsCfList, sysceList);
    }

    @ApiOperation(value = "插入巡护记录信息", notes = "此接口返回插入成功与否", response = ResultCp.class)
    @PostMapping("/insertDestinationsRecord")
    public ResultCp insertDestinationsRecord(HttpServletRequest request, @RequestBody @ApiParam(name = "巡护信息添加", value = "json格式", required = true) Swagger<Destinations> data) {
        Destinations des  = data.getData();
        //获取用户id
        Integer userId = 0;
        try {
            String token = request.getHeader("token");// 从 http 请求头中取出 token
            String UserId =JWT.decode(token).getAudience().get(0);
            userId = Integer.parseInt(UserId);
        } catch (Exception e) {
            request.setAttribute("attrname", "token无效，请重新登录");
            throw new RuntimeException("token无效，请重新登录");
        }
        if (data == null) {
            return ResultCp.build(1001, "添加信息不能为空");
        }
        Destinations destinations = data.getData();
        if (destinations == null) {
            return ResultCp.build(1001, "添加信息不能为空");
        }
        //巡航信息判断
        St4ScsCy scsCy = destinations.getSt4ScsCy();
        if (scsCy == null) {
            return ResultCp.build(1001, "添加巡护信息不能为空");
        }
        if (scsCy.getCy002() == null || "".equals(scsCy.getCy002())) {
            return ResultCp.build(1001, "添加巡护记录名称不能为空");
        }
        if (scsCy.getCy003() == null || "".equals(scsCy.getCy003())) {
            return ResultCp.build(1001, "添加巡护记录开始时间不能为空");
        }
        if (scsCy.getCy004() == null || "".equals(scsCy.getCy004())) {
            return ResultCp.build(1001, "添加巡护记录结束时间不能为空");
        }
        if (scsCy.getCy005() == null || "".equals(scsCy.getCy005())) {
            return ResultCp.build(1001, "添加巡护记录类型不能为空");
        }
        if (scsCy.getSa001() ==null) {
            return ResultCp.build(1001, "添加巡护记录巡护人员信息不能为空");
        }
        if (scsCy.getCy009() == null || "".equals(scsCy.getCy009())) {
            return ResultCp.build(1001, "添加巡护记录数据提交类型不能为空");
        }
        if (scsCy.getCy011() == null || "".equals(scsCy.getCy011())) {
            return ResultCp.build(1001, "添加巡护记录巡护任务类型不能为空");
        }
        LocalDateTime date = LocalDateTime.now();//获取当前时间
        scsCy.setCy012(userId);
        scsCy.setCy013(date);
        scsCy.setCy016(1);
        List<St4ScsCc> scsCcList = new ArrayList<>();
        List<St4ScsCf> scsCfList = new ArrayList<>();
        List<St4ScsCk> scsCkList = new ArrayList<>();
        List<St4ScsCe> sysceList = new ArrayList<>();
        //航点信息不为空的情况下，判断航点信息内容
        if (destinations.getDestination() != null) {
            List<Destination> destinationlist = destinations.getDestination();
            for (int i = 0; i < destinationlist.size(); i++) {
                Destination destination = destinationlist.get(i);
                //航点基础数据表
                St4ScsCc scscc = destination.getSt4ScsCc();
                if (scscc == null) {
                    return ResultCp.build(1001, "添加信息不能为空");
                }
                if (scscc.getCc002() == null) {
                    new RuntimeException("插入航点信息错误");
                }
                if (scscc.getCc003() == null) {
                    return ResultCp.build(1001, "航点类型不能为空");
                }
                if (scscc.getCc012() == null || "".equals(scscc.getCc012())) {
                    return ResultCp.build(1001, "航点名称不能为空");
                }
                //先判断巡航点类型1巡护（st4_scs_cf）2核查(st4_scs_ck)
                if (scscc.getCc003() == 0) {//巡护表
                    St4ScsCf scsCf = new St4ScsCf();
                    St4ScsCf cf = destination.getSt4ScsCf();
                    scsCfList.add(cf);
                    /**
                     * 这个下面的注释掉因为在航迹里面是不一定要有航点信息的
                     */
                    if (cf == null) {
                        return ResultCp.build(1001, "添加巡护信息不能为空");
                    }
                    if (cf.getCc002() == null || "".equals(cf.getCc002())) {
//                    new RuntimeException("插入航点信息错误");
                        return ResultCp.build(1001, "添加航点信息错误");
                    }
                    if (!cf.getCc002().equals(scscc.getCc002())) {
//                    new RuntimeException("插入航点信息错误");
                        return ResultCp.build(1001, "添加航点信息错误");
                    }
                    //巡护的自动不用动态配置所以不用判断字段的必填项
                   /* if(ObjectUtils.isNotNullAndEmpty(taskId)){
                        List<Map<String, Object>> fields = cpService.getSt4ScsCpInfo(taskId.toString(), 1);
                        //判断必填字段是否填写
                        AtomicReference<String> ret = new AtomicReference<>("");
                        fields.stream().forEach(objectMap -> ret.set(fieldJudge(cf, null, objectMap)));
                        if (!"".equals(ret.get())) {
                            return ResultCp.build(1001, ret.get());
                        }
                        //设置字符串为空
                        scsCf = fieldIsEmpty(cf, null, fields, cf.getClass());
                        scsCfList.add(scsCf);
                    }*/


                } else if (scscc.getCc003() == 1) {//核查(台账表)
                    St4ScsCk scsCk = new St4ScsCk();
                    St4ScsCk ck = destination.getSt4ScsCk();
                    if (ck == null) {
                        return ResultCp.build(1001, "添加核查信息不能为空");
                    }
                    /**
                     * 下面这段代码注掉因为核查航点是通过cd004关联到cd表再关联到ck表的所以ck表里的cc002的字段也是无用的这里只是注释掉后续业务大改的话再考虑
                     */
                    if (ck.getCc002() == null || "".equals(ck.getCc002())) {
//                    new RuntimeException("插入航点信息错误");
                        return ResultCp.build(1001, "添加航点信息错误");
                    }
                    if(ck!=null){
                        if (!ck.getCc002().equals(scscc.getCc002())) {
                            //                    new RuntimeException("插入航点信息错误");
                            return ResultCp.build(1001, "添加航点信息错误");
                        }
                    }
                    Object taskId = destination.getTaskId();
                    if(ObjectUtils.isNotNullAndEmpty(taskId)){
                        List<Map<String, Object>> fields = cpService.getSt4ScsCpInfo(taskId.toString(), 0);//1代表巡护0代表核查
                        //判断必填字段是否填写
                        if(fields==null){
                            return ResultCp.build(1001,"未查询到您所提交的核查任务所匹配的核查字段");
                        }
                        AtomicReference<String> ret = new AtomicReference<>("");
                        fields.stream().forEach(objectMap -> ret.set(fieldJudge(null, ck, objectMap)));
                        if (!"".equals(ret.get())) {
                            return ResultCp.build(1001, ret.get());
                        }
                        scsCk = fieldIsEmpty(null, ck, fields, ck.getClass());
                        scsCk.setCk086(date);
                        scsCk.setCk087(userId);
                        scsCk.setCc002(ck.getCc002());//航点信息唯一标识
                        scsCk.setCd004(ck.getCd004());//问题点的编码
                        scsCkList.add(scsCk);
                    }else {
                        ResultCp.build(1001,"核查任务taskId"+ResultMsg.MSG_1001);
                    }

                } else {
//                new RuntimeException("插入航点信息错误");
                    return ResultCp.build(1001, "添加航点信息错误");
                }
                scscc.setCc007(date);
                scscc.setCc011(1);
                scscc.setCc008(userId);
                scscc.setCy017(scsCy.getCy017());
                scsCcList.add(scscc);
                if(destination.getSt4ScsCe()!=null){

                    AtomicReference<String> ret = new AtomicReference<>("");
                    destination.getSt4ScsCe().forEach(ce -> {
                        if (!ce.getCe002().equals(scscc.getCc002())) {
                            ret.set("添加航点信息错误");
                        }
                    });
                    if (!"".equals(ret.get())) {
                        return ResultCp.build(1001, ret.get());
                    }
                    sysceList.addAll(destination.getSt4ScsCe());
                }
            }
            Integer finalUserId = userId;
            sysceList.forEach(ce -> {
                ce.setCe005(date);
                ce.setCe006(finalUserId);
            });
        }
        return destinationsManagerService.insertDestinationsRecordManager(scsCy, destinations.getSt4ScsCgs(), scsCcList, scsCkList, scsCfList, sysceList);
    }


    /**
     * @param cf   巡护
     * @param ck   台账
     * @param map1 对应字段
     * @return java.lang.String
     * @explain : 判断必填字段是否填写
     * @author xxh
     * @date 2019/8/14
     */
    public static String fieldJudge(St4ScsCf cf, St4ScsCk ck, Map<String, Object> map1) {
        Map<String, String> map = null;
        try {
            map = BeanUtils.describe(cf == null ? ck : cf);
        } catch (Exception e) {
            e.printStackTrace();
            new RuntimeException("插入航点信息错误");
        }
        AtomicReference<String> ret = new AtomicReference<>("");
        Map<String, String> finalMap = map;
        map1.forEach((key, value) -> {
            if (key.equals("isMust") || value.equals("1")) {//判断必填与否情况，1是必填
                String filedLower = map1.get("id").toString().toLowerCase();//小写判断
                String filedUpper = map1.get("id").toString().toUpperCase();//大写判断
                if (finalMap.get(filedLower) == null && finalMap.get(filedUpper) == null) {
                    ret.set(map1.get("name") + "不能为空");
                }
            }
        });
        return ret.get();
    }

    /**
     * @param cf
     * @param ck
     * @param fileds
     * @param class1
     * @return T
     * @explain : 设置填写多余的字段为空，返回对应实体map
     * @author xxh
     * @date 2019/8/14
     */
    public static <T> T fieldIsEmpty(St4ScsCf cf, St4ScsCk ck, List<Map<String, Object>> fileds, Class<T> class1) {
        Map<String, String> map = null;
        try {
            map = BeanUtils.describe(cf == null ? ck : cf);
        } catch (Exception e) {
            e.printStackTrace();
            new RuntimeException("插入航点信息错误");
        }
        Map<String, String> mapss = new HashMap<>();
        Map<String, String> finalMap = map;
        finalMap.forEach((key, value) -> {
            fileds.forEach(map1 ->
                    map1.forEach((key1, value1) -> {
                        String filedLower = map1.get("id").toString().toLowerCase();//小写判断
                        String filedUpper = map1.get("id").toString().toUpperCase();//大写判断
                        if (key.equals(filedLower) || key.equals(filedUpper)) {
                            mapss.put(key, value);
                        }
                    }));

        });
        T bean = (cf == null ? (T) St4ScsCk.class : (T) St4ScsCf.class);
        bean = JSON.parseObject(JSONObject.toJSONString(mapss), (cf == null ? St4ScsCk.class : St4ScsCf.class));
        return bean;
    }
    /**
     * 实时位置上传接口
     * @param
     * @return
     */
    @ApiOperation(value = "实时位置上传接口", notes = "实时位置上传接口", response = St4ScsCh.class)
    @PostMapping("/sendRealPositon")
    public ResultCp sendRealPositon( @RequestBody @ApiParam(name = "", value = "json格式", required = true)  Swagger<St4ScsCh> data
    ) {

        St4ScsCh param = data.getData();

        if(!ObjectUtils.isNotNullAndEmpty(param.getCh002())){
            return ResultCp.build(1001,"巡查人员ch002"+ResultMsg.MSG_1001);
        }
        if(!ObjectUtils.isNotNullAndEmpty(param.getCh003())){
            return ResultCp.build(1001,"经度CH003"+ResultMsg.MSG_1001);
        }
        if(!ObjectUtils.isNotNullAndEmpty(param.getCh004())){
            return ResultCp.build(1001,"纬度CH004"+ResultMsg.MSG_1001);
        }
        if(!ObjectUtils.isNotNullAndEmpty(param.getCh005())){
            return ResultCp.build(1001,"人员状态ch005"+ResultMsg.MSG_1001);
        }
        QueryWrapper<St4ScsCh> chWrapper = new QueryWrapper<>();
        chWrapper.eq("CH002",param.getCh002());
        List<St4ScsCh> list = iSt4ScsChService.list(chWrapper);
        LocalDateTime date = LocalDateTime.now();
        if(list!=null&&list.size()>0){
            St4ScsCh ch = list.get(0);
            ch.setCh006(date);
            ch.setCh003(param.getCh003());
            ch.setCh004(param.getCh004());
            ch.setCh005(param.getCh005());
            if(iSt4ScsChService.updateById(ch)){

                return  ResultCp.build(1000,"上传"+ResultMsg.MSG_1000);
            }
        }else {
            param.setCh006(date);
            if(iSt4ScsChService.save(param)){
                return  ResultCp.build(1000,"上传"+ResultMsg.MSG_1000);
            }
        }


        return  ResultCp.build(1003,ResultMsg.MSG_1003+",上传失败");
    }

}

@ApiModel(value = "Destinations对象", description = "航巡护信息")
class Destinations {





    @ApiModelProperty(value = "巡护记录")
    private St4ScsCy st4ScsCy;

    @ApiModelProperty(value = "巡护路段记录集合")
    private List<St4ScsCg> st4ScsCgs;

    @ApiModelProperty(value = "航点信息")
    private List<Destination> destination;





    public St4ScsCy getSt4ScsCy() {
        return st4ScsCy;
    }

    public void setSt4ScsCy(St4ScsCy st4ScsCy) {
        this.st4ScsCy = st4ScsCy;
    }

    public List<St4ScsCg> getSt4ScsCgs() {
        return st4ScsCgs;
    }

    public void setSt4ScsCgs(List<St4ScsCg> st4ScsCgs) {
        this.st4ScsCgs = st4ScsCgs;
    }

    public List<Destination> getDestination() {
        return destination;
    }

    public void setDestination(List<Destination> destination) {
        this.destination = destination;
    }
}
