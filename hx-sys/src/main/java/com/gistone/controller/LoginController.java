package com.gistone.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.VO.ResultVO;
import com.gistone.entity.*;
import com.gistone.service.*;
import com.gistone.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 登陆管理接口
 *
 * @author xxh
 */
@RestController
//@Api(value = "登陆管理接口", tags = "API-Login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "API-UserManage", tags = "登录管理接口", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping("/api/sys/login")
@Slf4j
public class LoginController {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISt4SysSaService st4SysSaService;
    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @Autowired
    private ISysCompanyService sysCompanyService;

    @Autowired
    private ISysRoleResourcesService sysRoleResourcesService;

    @Autowired
    private ISysMobileUserService sysMobileUserService;

    @Autowired
    private ILoginLogService loginLogService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 移动端登录接口开始
     * @return
     */

    @ApiOperation(value = "App登录接口", notes = "此接口返回登录失败与否", response = Result.class)
    @PostMapping("/loginCheck")
    public ResultCp loginCheck(HttpServletRequest request, @RequestBody Swagger<LoginUserSwagger> loginUser) throws Exception {
        if (loginUser.getData() == null || "".equals(loginUser.getData())) {
            return ResultCp.build(1006, "请填写用户名密码");
        }
        if (loginUser.getData().getUsername() == null || "".equals(loginUser.getData().getUsername())) {
            return ResultCp.build(1006, "请填写用户名");
        }
        if (loginUser.getData().getPassword() == null || "".equals(loginUser.getData().getPassword())) {
            return ResultCp.build(1006, "请填写密码");
        }
        //获取私钥
       /* String logname=loginUser.getData().getUsername();
        QueryWrapper<St4SysSa> saWrapper = new QueryWrapper<>();
        saWrapper.eq("SA008",logname).or().eq("SA019",logname);
        List<St4SysSa> saList = st4SysSaService.list(saWrapper);
        Integer userType=0;
        if(saList!=null&&saList.size()>0){
            userType=saList.get(0).getSa016();
        }*/

        Object privateKey = request.getSession().getAttribute("privateKey");
        if (loginUser.getData().getType() == 1) {//如果为手机端，则赋值默认加密字符串
            privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIM2EIOIvLQIbmNg38eAJOPX0OmUVsv4yHl13yr2WZxLNWpAYri9DPO8RPXNL7MpRM0+rMii8+WpMptkDUTlIMlJzS7ey5Bcgc8nlXYRd81/t3dq2Xo3KfADCIW0+Hrls1MdbLR2QziPmP2+j2vHgb1tvapNe7zWdKHp7lmbrdZNAgMBAAECgYASc4fe91uR9Z2QXA8b/ukxISg/sJyEEngenHTFld8EG49nYaaAVBV+MPuBlVkf2K3ULvwdOs0RfrmfIHK9PXten2IH/wn5ZfCgaWY4pslVyqthZhNt7+0FnYGQm7gyNu77yS+CD9gCl1posneaacPtSym6PLMQqi92Zbvz0xpwAQJBAMG8kwCfazdO51mSxHcx0JX0MgCkUHWOqDtOKRMwo34s9ITk00bI3X0Qzp0LMSBZVz3Wq3IS0r24OnZSHaPUuzsCQQCtYUrzAbBCQPOGtxTL/vjNSSh8S7fioq6ZTmoKbMmmmktB/q2l3WYzgRJULMKdbReLtRFYX6eBnw35UqKpOswXAkEArXUeFN+nCgT+RAeRGbsjKy9RGZwEYcyROU/4nLLenti3MMkDlQvAqvpCUv8zQ+hZoQczx1WtE6n/xfItkYKlAwJAXlOLWi/1++WoWAdtChr6s3z0yMLNFUEaqo9tw6QyBTD2dr4fLRkJzEWRejgr3UgHwltNR34q/KtTB+z8UAOMGwJBAIsSaRO1Erve1cofrKAw3LehpiqrytrbpqbnyUC9JzZ1wrgO5tVA+XEz55pcGB2ISgFxjf88vgJ7DrlcX3zTCSA=";
        }
        // Object privateKey="111";
        String username = RSAEncrypt.decrypt(loginUser.getData().getUsername(), privateKey.toString());//用户名解密
        String password = RSAEncrypt.decrypt(loginUser.getData().getPassword(), privateKey.toString());//密码解密*/
        ResultCp ret = st4SysSaService.searchSysUserByLogin(username, password);
        //登录失败
        if (ret.getStatus() != 1000) {
            return ret;
        }
        St4SysSa user = (St4SysSa) ret.getData();
        //用户不存在
        if (user == null) {
            return ResultCp.build(1016, ResultMsg.MSG_1016);
        }
        //判断是(1)手机端用户还是(0)pc端用户，pc验证验证码是否正确
        if (loginUser.getData().getType() == 0) {
            /*if (privateKey == null) {
                return ResultCp.build(1006, "登录异常！");
            }*/
            //04包不需要
           /* String kaptacha=RSAEncrypt.decrypt(loginUser.getData().getKaptcha(),privateKey.toString());//验证码解密
            if (loginUser.getData().getKaptcha()==null||"".equals(loginUser.getData().getKaptcha())) {
                return ResultCp.build(1006, "请填写验证码");
            }
            Object captchaDate = request.getSession().getAttribute("captchaDate");
            Date date = new Date();
            if (captchaDate != null) {
                Long numdate = (date.getTime() - Long.parseLong(captchaDate.toString())) / 1000;
                if (numdate > 60) {
                    return ResultCp.build(1006, "验证码失效，请重新输入！");
                }
            } else {
                return ResultCp.build(1006,  "验证码错误！");
            }
            String s = request.getSession().getAttribute("captcha").toString();
            if (StringUtils.isEmpty(kaptacha) || !s.toLowerCase().equals(kaptacha.toLowerCase())) {
                return ResultCp.build(1006, "验证码错误！");
            }*/
        } else {
            //如果是移动端登录，查看该用户是否是移动端用户，如果不是，直接返回提示
            if (user.getSa016() == 0) {
                return ResultCp.build(1016, ResultMsg.MSG_1016);
            } else {
                //如果是移动端用户，则验证密码是否正确
                if (!(user.getSa009()).equals(Md5Util.md5Encode(password))) {
                    return ResultCp.build(1017, ResultMsg.MSG_1017);
                }
            }
        }
        String lockingUser = "locking_" + username;
        //这里先判断当前用户有没有被锁定如果被锁定则不必进下面的逻辑
        if (stringRedisTemplate.hasKey(lockingUser)) {
            Long seconds = stringRedisTemplate.getExpire(lockingUser, TimeUnit.SECONDS) / 60;//根据key获取过期时间并换算成指定单位
            return ResultCp.build(1006, "当前账号被锁定，请" + (seconds < 1 ? 1 : seconds) + "分钟后重试！");
        }

        if (ret.getStatus() != 1000) {
            //设置同一账号，五分钟内输入用户名错误五次锁定一小时
            if (ret.getStatus() == 2000) {
                //定义redis的用户名
                String keys = "sysuser_" + username;
                if (stringRedisTemplate.hasKey(keys)) {
                    String result1 = stringRedisTemplate.opsForValue().get(keys).toString();
                    if (Integer.parseInt(result1) > 4) {
                        //锁定1小时
                        stringRedisTemplate.opsForValue().set("locking_" + username, "", 3600, TimeUnit.SECONDS);
                    }
                    //增加次数
                    Long seconds = stringRedisTemplate.getExpire(keys, TimeUnit.SECONDS);//根据key获取过期时间并换算成指定单位
                    stringRedisTemplate.opsForValue().set(keys, (Integer.parseInt(result1) + 1) + "", 300 - seconds, TimeUnit.SECONDS);
                } else {
                    LocalTime time = LocalTime.now();
                    LocalTime newTime = time.plusHours(1);
                    stringRedisTemplate.opsForValue().set(keys, "2", 300, TimeUnit.SECONDS);
                }
            }
            return ret;
        }
        //用户名密码正确，存入redis，返回token，权限菜单
        St4SysSa sysUser = (St4SysSa) ret.getData();
        String token = getTokenApp(sysUser);
        //String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxIn0.dQMt-whQl9DmFOo-JIupTPRKZkApwBhUjSvIRuZ9lF8";
        Map<String, Object> map = new HashMap<>();
        map.put("userInfo", JSON.toJSON(sysUser).toString());//用户信息
        map.put("userType", loginUser.getData().getType() + "");//登录类型
       /* QueryWrapper<St4PoSaSj> saSjQueryWrapper = new QueryWrapper();
        saSjQueryWrapper.eq("SA001",sysUser.getSa001());
        if(st4PoSaSjService.list(saSjQueryWrapper).size()>0){
            map.put("userUnit",st4PoSaSjService.list(saSjQueryWrapper).get(0).getSj001().toString());
        }else {
            map.put("userUnit","");
        }*/
        //权限信息，暂时为写
        //权限信息开始
        //先查询出当前人的所承担的角色
        JSONArray jarrResult = new JSONArray();
        JSONObject jsonResult = null;
       /* List<Integer> roleIdList = new ArrayList<>();
        QueryWrapper<St4PoSaSb> wrapper = new QueryWrapper<>();
        wrapper.eq("SA001", sysUser.getSa001());*/


        //根据角色查询该角色所有权限
        /*Result res = st4SysScService.listModuleBySa001(sysUser.getSa001());
        List<St4SysSc> scList = (List<St4SysSc>) res.getData();
        if(scList!=null){
            map.put("powerInfo", new JSONObject().toJSONString(scList));
        }*/


        stringRedisTemplate.opsForHash().putAll(token, map);

        if (loginUser.getData().getType() == 0) {
            stringRedisTemplate.expire(token, 60, TimeUnit.MINUTES);//PC端设置过期时间为1小时
        } else {
            stringRedisTemplate.expire(token, 7, TimeUnit.DAYS);//手机端设置过期时间为7天
        }
        map.put("userInfo", sysUser);//用户信息-存入redis为字符串，给前端为对象信息
        map.put("token", token);
        sysUser.setSa009(null);//密码置空
        //app返回权限的主要功能
        List<St4SysSc> appMain = new ArrayList<St4SysSc>();
        LinkedHashMap<String, String> mainMap = new LinkedHashMap<>();
        mainMap.put("任务管理","main_001");
        mainMap.put("航迹管理","main_002");
        //mainMap.put("航点管理","main_003");
        mainMap.put("人类活动斑块","main_004");
        //mainMap.put("红线边界管理","main_005");
       // mainMap.put("消息中心","main_007");
        St4SysSc sc = new St4SysSc();
        for (String main:mainMap.keySet()) {
            sc = new St4SysSc();
            sc.setSc002(main);
            sc.setSc004(mainMap.get(main));
            appMain.add(sc);
        }
        //app返回权限的基础功能
        List<St4SysSc> appBase = new ArrayList<St4SysSc>();
        LinkedHashMap<String, String> baseMap = new LinkedHashMap<>();
        baseMap.put("个人信息","base_001");
        baseMap.put("地图管理","base_002");
       // baseMap.put("会话列表","base_003");
        baseMap.put("GPX文件管理","base_004");
        baseMap.put("关于","base_005");
        baseMap.put("检查版本升级","base_006");
        for (String base:baseMap.keySet()) {
            sc = new St4SysSc();
            sc.setSc002(base);
            sc.setSc004(baseMap.get(base));
            appBase.add(sc);
        }
        //app返回巡护类型
        List<St4SysSc> appPType = new ArrayList<St4SysSc>();
        LinkedHashMap<String, String> typeMap = new LinkedHashMap<>();
      //  typeMap.put("日常巡护","ptype");
      //  typeMap.put("督查","ptype");
        typeMap.put("巡查","ptype");
       // typeMap.put("领导批示","ptype");
       // typeMap.put("暗访","ptype");
        for (String type:typeMap.keySet()) {
            sc = new St4SysSc();
            sc.setSc002(type);
            sc.setSc004(typeMap.get(type));
            appPType.add(sc);
        }
        //app返回工具箱
        List<St4SysSc> appTool = new ArrayList<St4SysSc>();
        Map<String,String> toolMap = new HashMap<>();
        toolMap.put("指南针","tool_001");
       // toolMap.put("预警","tool_002");
        toolMap.put("测量","tool_003");

        for (String tool:toolMap.keySet()) {
            sc = new St4SysSc();
            sc.setSc002(tool);
            sc.setSc004(toolMap.get(tool));
            appTool.add(sc);
        }
        map.put("main", appMain);//app主要功能
        map.put("base", appBase);//app基础功能
        map.put("tool", appTool);//app工具箱
        map.put("pType", appPType);//巡查类型


        HttpSession session = request.getSession();
        session.setAttribute("user",sysUser);

        return ResultCp.build(1000, "登录成功", map);
    }
    //-----------------------------------------------------------------移动端代码结束
    @RequestMapping("/getToken")
    public Object getToken() {
        Map<String,Object> map=new HashMap<String, Object>();
        try {
            String token = UUID.randomUUID().toString().replaceAll("-", "");
            boolean isExis=stringRedisTemplate.hasKey(token);
            while (isExis){
                token = UUID.randomUUID().toString().replaceAll("-", "");
                isExis=stringRedisTemplate.hasKey(token);
            }
            stringRedisTemplate.opsForValue().set(token,"", 3,TimeUnit.MINUTES);
            map.put("code","0000");
            map.put("msg","成功");
            map.put("accessToken",token);
        } catch (Exception e) {
            log.error("网关获取token错误："+e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Title: generationVerificationCode Description:获取图片路径
     *
     * @param request
     * @param response
     * @throws Exception
     */
    // @ApiOperation(value = "获取验证码图片相对路径", notes = "此接口返回验证码相对路径")
    @RequestMapping(value = "getVerificationCode", method = RequestMethod.GET)
    public void getVerificationCode(HttpServletRequest request, HttpServletResponse response, String accessToken) throws Exception {
        // 调用工具类生成的验证码和验证码图片
        Map<String, Object> codeMap = CodeUtil.generateCodeAndPic();
        // 将四位数字的验证码保存到Session中。
        if (accessToken != "") {
            //改用token，使用redis存储数据
            stringRedisTemplate.opsForValue().set(accessToken, codeMap.get("code").toString(), 60, TimeUnit.MINUTES);
            // 禁止图像缓存。
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", -1);
            response.setContentType("image/jpeg");
            // 将图像输出到Servlet输出流中。
            ServletOutputStream sos;
            try {
                sos = response.getOutputStream();
                ImageIO.write((RenderedImage) codeMap.get("codePic"), "jpeg", sos);
                sos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "/logout")
    public ResultVO logout(@RequestBody LoginParameter login, HttpServletRequest request) {
        try {
            String ipAdress = GetIpUtil.getIpAddress(request);
            if (login.getAccessToken().equals("")) {
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "参数不能为空！");
            }
            String code = stringRedisTemplate.opsForValue().get(login.getAccessToken());
            //删除redis中的缓存
            if (StringUtils.isNotBlank(code)) {
                stringRedisTemplate.delete(login.getAccessToken());
            }

            User user = login.getData();
            List<SysUser> listParam = sysUserService.list(new QueryWrapper<SysUser>().eq("username", user.getUsername()).eq("type", user.getType()));
            LoginLog loginLog = new LoginLog();
            loginLog.setLoginType(1);

            if (listParam.size() > 0 && listParam != null) {
                loginLog.setType(listParam.get(0).getType());
                loginLog.setLoginUserId(listParam.get(0).getId());
            }

            loginLog.setLoginIp(ipAdress);
            loginLog.setCreateTime(new Date());
            loginLog.setLoginName(user.getUsername());


            HttpSession session = request.getSession();
            session.removeAttribute("user");

            //记录登出日志
            loginLogService.save(loginLog);
        } catch (Exception e) {
            log.error("登出失败,异常信息：{}", e.getMessage());
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "登出异常");
        }
        return ResultVOUtil.success();
    }


//    public static void main(String[] args) {
//        System.out.println(Md5Util.md5("PW123456"));
//    }
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public ResultVO getLoginInfo(@RequestBody LoginParameter login, HttpServletRequest request) {
        //先从redis中取出此token的code验证码，然后和登陆参数中做比较，一致后不能更新用户信息及其他需要session的信息到redis中。
        try {
            String ipAdress = GetIpUtil.getIpAddress(request);

            /*if (StringUtils.isBlank(login.getAccessToken())) {
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "token不能为空！");
            }
            String code = redisTemplate.opsForValue().get(login.getAccessToken());*/
            User user = login.getData();
            //验证码判断
		/*	  if (user.getType() == 0) {
                if (!user.getCode().equals(code)) {
                    return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "验证码错误！");
                }
            }*/
            /*Wrapper<SysUser> ew = new Wrapper<SysUser>();
            ew.eq("username", user.getUsername());
            ew.eq("type", user.getType());*/
            List<SysUser> listParam = sysUserService.list(new QueryWrapper<SysUser>().eq("username", user.getUsername()).eq("type", user.getType()).eq("enable", 1));

            //List<SysUser> listParam = sysUserService.selectList( user.getUsername(),user.getType());
            if (listParam.size() > 0) {
                   if (Md5Util.md5(user.getPassword()).equals(listParam.get(0).getPassword())) {
                    if (listParam.get(0).getEnable() != 1) {
                        return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "账号过期，请联系管理员！");
                    }

                    //记录登入日志
                    LoginLog loginLog = new LoginLog();
                    loginLog.setLoginName(user.getUsername());
                    loginLog.setLoginUserId(listParam.get(0).getId());
                    loginLog.setCreateTime(new Date());
                    loginLog.setLoginIp(ipAdress);
                    loginLog.setLoginType(0);


                       HttpSession session = request.getSession();
                       session.setAttribute("user",listParam.get(0));

                       if (user.getType() == 0) {//pc端一小时过期 ，查询该级以下行政区划，查询该级行政区划名称级别

                        String token = getToken(listParam.get(0));
                        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                        stringRedisTemplate.opsForValue().set(uuid, token, 1, TimeUnit.DAYS);
                        Integer userId = listParam.get(0).getId();


                        Map<String, Object> result = new HashMap<>();
                        //获取用户的行政区号
                        SysCompany sysCompany = sysCompanyService.getOne(new QueryWrapper<SysCompany>().eq("COM_CODE", listParam.get(0).getCode()));
                        if (sysCompany != null) {
                            result.put("codeMsg", sysCompany);
                            result.put("comCode", sysCompany.getComCode());
                            result.put("name", sysCompany.getComName());
                        }
                        result.put("userId", userId);
                        result.put("userName", listParam.get(0).getUsername());
                        //通过userId获取用户所拥有的的角色
                        List<SysRole> sysRoleList = sysUserRoleService.selectRoleByUserId(userId);
                        result.put("roles", sysRoleList);

                        List<Integer> roleIds = new ArrayList<>();
                        if (sysRoleList != null && sysRoleList.size() > 0) {
                            for (SysRole sysRole : sysRoleList) {
                                roleIds.add(sysRole.getId());
                            }
                        }
                        //通过用户所拥有的角色id获取角色所拥有的权限
                        List<String> resourcePath = new ArrayList<>();
                        if (roleIds != null && roleIds.size() > 0) {
                            List<SysResources> sysResources = sysRoleResourcesService.selectResourcesByRoleId(roleIds);
                            result.put("resource", sysResources);
                            if (sysResources != null && sysResources.size() > 0) {
                                for (SysResources sysResource : sysResources) {
                                    if (sysResource != null) {
                                        if (sysResource.getApiUrl() != null) {
                                            resourcePath.add(sysResource.getApiUrl());
                                        }
                                    }


                                }
                            }
                        }
                        //把

//                        redisTemplate.opsForValue().set(login.getAccessToken(),  JSONArray.fromObject(resourcePath).toString(), 1, TimeUnit.DAYS);

                        //插入登录日志
                        loginLog.setType(user.getType());
                        loginLogService.save(loginLog);
                        result.put("token", token);
                        result.put("uuid",uuid);
                        return ResultVOUtil.success(result);
                    } else {//手机端七天 ，查询对应的手机端用户的基本信息

                        //生成并返回token
                        String token = UUID.randomUUID().toString().replaceAll("-", "");
                        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                        stringRedisTemplate.opsForValue().set(uuid, token, 7, TimeUnit.DAYS);
                        //移动端用户登录成功以后返回的数据
                        Integer userId = listParam.get(0).getId();
                        SysMobileUser sysMobileUser = sysMobileUserService.selectMobileUserById(userId);

                        Map<String ,Object> result = new HashMap<>();

                        result.put("token", token);
                        result.put("sysMobileUser", sysMobileUser);
                        result.put("uuid",uuid);

                        /*//通过userId获取用户所拥有的的角色
                        List<SysRole> sysRoleList = sysUserRoleService.selectRoleByUserId(userId);

                        List<Integer> roleIds = new ArrayList<>();
                        if (sysRoleList != null && sysRoleList.size() > 0) {
                            for (SysRole sysRole : sysRoleList) {
                                roleIds.add(sysRole.getId());
                            }
                        }
                        //通过用户所拥有的角色id获取角色所拥有的权限
                        List<String> resourcePath = new ArrayList<>();
                        if(roleIds!=null && roleIds.size()>0){
                            List<SysResources> sysResources = sysRoleResourcesService.selectResourcesByRoleId(roleIds);
                            if(sysResources!=null&&sysResources.size()>0){
                                for (SysResources sysResource : sysResources) {
                                    resourcePath.add(sysResource.getApiUrl());
                                }
                            }
                        }
                        redisTemplate.opsForValue().set(login.getAccessToken(),JSONArray.fromObject(resourcePath).toString() , 7, TimeUnit.DAYS);*/
                        //插入登录日志
                        loginLog.setType(user.getType());
                        loginLogService.save(loginLog);
                        return ResultVOUtil.success(result);
                    }

                } else {
                    return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "密码错误");
                }
            } else {
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "账号不存在");
            }
        } catch (Exception e) {
            log.error("登录异常：信息为：" + e.getMessage());
            return ResultVOUtil.error(ResultEnum.INTERFACEERROR.getCode(), "登陆异常，请联系管理员");
        }
    }

    /**
     * @param sysUser
     * @return java.lang.String
     * @explain : 根据用户信息生成token
     * @author xxh
     * @date 2019/7/22
     */
    public static String getToken(SysUser sysUser) {
        StringBuffer token = new StringBuffer();
        token.append(JWT.create().withAudience(sysUser.getId().toString()).sign(Algorithm.HMAC256(sysUser.getPassword())));
        return token.toString();
    }
    /**
     * @explain : 根据用户信息生成token
     * @author xxh
     * @date 2019/7/22
     * @param st4SysSa
     * @return java.lang.String
     */
    public static String getTokenApp (St4SysSa st4SysSa){
        StringBuffer token = new StringBuffer();
        token.append(JWT.create().withAudience(st4SysSa.getSa001().toString()).sign(Algorithm.HMAC256(st4SysSa.getSa008())));
        return token.toString();
    }


    @PostMapping(value = "/list")
    public ResultVO getLoginList(@RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"data结构");
        if(null==params.get("pageNum"))return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"pageNum不能为空");
        if(null==params.get("pageSize"))return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"pageSize不能为空");
        PageBean pageBean = new PageBean();
        pageBean.setPageIndex(Integer.valueOf(params.get("pageNum")+""));
        pageBean.setPageSize(Integer.valueOf(params.get("pageSize")+""));
        pageBean.setStr1(params.get("name")+"");
        return loginLogService.list(pageBean);
    }

}

@Data
class LoginParameter {
    private String accessToken;
    private User data;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

}

class User {
    private String username;
    private String password;
    private String code;
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
