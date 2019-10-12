package com.gistone.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.VO.ResultVO;
import com.gistone.entity.*;
import com.gistone.service.*;
import com.gistone.util.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 登陆管理接口
 *
 * @author xxh
 */
@RestController
//@Api(value = "登陆管理接口", tags = "API-Login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping("/api/sys/login")
@Slf4j
public class LoginController {


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ISysUserService sysUserService;

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

    @RequestMapping("/getToken")
    public Object getToken() {
        Map<String,Object> map=new HashMap<String, Object>();
        try {
            String token = UUID.randomUUID().toString().replaceAll("-", "");
            boolean isExis=redisTemplate.hasKey(token);
            while (isExis){
                token = UUID.randomUUID().toString().replaceAll("-", "");
                isExis=redisTemplate.hasKey(token);
            }
            redisTemplate.opsForValue().set(token,"", 3,TimeUnit.MINUTES);
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
            redisTemplate.opsForValue().set(accessToken, codeMap.get("code").toString(), 60, TimeUnit.MINUTES);
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

            String code = redisTemplate.opsForValue().get(login.getAccessToken());

            //删除redis中的缓存
            if (StringUtils.isNotBlank(code)) {
                redisTemplate.delete(login.getAccessToken());
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

            //记录登出日志
            loginLogService.save(loginLog);
        } catch (Exception e) {
            log.error("登出失败,异常信息：{}", e.getMessage());
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "登出异常");

        }

        return ResultVOUtil.success();


    }

    @RequestMapping(value = "check", method = RequestMethod.POST)
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
//            List<SysUser> listParam = sysUserService.selectList( user.getUsername(),user.getType());
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

                    if (user.getType() == 0) {//pc端一小时过期 ，查询该级以下行政区划，查询该级行政区划名称级别

                        String token = getToken(listParam.get(0));
                        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                        redisTemplate.opsForValue().set(uuid, token, 1, TimeUnit.DAYS);
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
                        redisTemplate.opsForValue().set(uuid, token, 7, TimeUnit.DAYS);
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