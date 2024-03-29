package com.gistone.aspect;

import com.alibaba.fastjson.JSONObject;
import com.gistone.entity.SysLog;
import com.gistone.service.SysLogService;
import com.gistone.util.ToolUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * Created by wangl on 2018/1/13.
 * todo:
 */
@Aspect
@Order(5)
@Component
public class WebLogAspect {

    private ThreadLocal<Long> startTime = new ThreadLocal<>();

//    @Autowired
//    private ISysUserService sysUserService;

    @Autowired
    private SysLogService sysLogService;


    private SysLog sysLog = null;

    @Pointcut("@annotation(com.gistone.annotation.SysLog)")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {

        startTime.set(System.currentTimeMillis());
        // 接收到请求，记录请求内容

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = (HttpSession) attributes.resolveReference(RequestAttributes.REFERENCE_SESSION);
        String token = request.getHeader("token");
//        String userId = JWT.decode(token).getAudience().get(0);
        sysLog = new SysLog();
//        sysLog.setCreateBy(userId);
        sysLog.setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        sysLog.setHttpMethod(request.getMethod());
        //获取传入目标方法的参数
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            Object o = args[i];
            if(o instanceof ServletRequest || (o instanceof ServletResponse) || o instanceof MultipartFile){
                args[i] = o.toString();
            }
        }
        String str = JSONObject.toJSONString(args);
        sysLog.setParams(str.length()>5000? JSONObject.toJSONString("请求参数数据过长不与显示"):str);
        String ip = ToolUtil.getClientIp(request);
        if("0.0.0.0".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip) || "localhost".equals(ip) || "127.0.0.1".equals(ip)){
            ip = "127.0.0.1";
        }
        sysLog.setRemoteAddr(ip);
        sysLog.setRequestUri(request.getRequestURL().toString());
        if(session != null){
            sysLog.setSessionId(session.getId());
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        com.gistone.annotation.SysLog mylog = method.getAnnotation(com.gistone.annotation.SysLog.class);
        if(mylog != null){
            //注解上的描述
            sysLog.setTitle(mylog.value());
            //TODO 按模块业务划分，加日志类型
        }

        Map<String,String> browserMap = ToolUtil.getOsAndBrowserInfo(request);
        sysLog.setBrowser(browserMap.get("os")+"-"+browserMap.get("browser"));

//        if(!"127.0.0.1".equals(ip)){
//            Map<String,String> map = ToolUtil.getAddressByIP(ToolUtil.getClientIp(request));
//            sysLog.setArea(map.get("area"));
//            sysLog.setProvince(map.get("province"));
//            sysLog.setCity(map.get("city"));
//            sysLog.setIsp(map.get("isp"));
//        }
        sysLog.setType(ToolUtil.isAjax(request)?"Ajax请求":"普通请求");
        sysLog.setCategory("用户日志"); //临时
        //获取用户
       /* if(MySysUser.ShiroUser() != null) {
            sysLog.setUsername(StringUtils.isNotBlank(MySysUser.nickName()) ? MySysUser.nickName() : MySysUser.loginName());
        }*/
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            Object obj = proceedingJoinPoint.proceed();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            sysLog.setException(e.getMessage());
            throw e;
        }
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) {
       /* if(MySysUser.ShiroUser() != null) {
            sysLog.setUsername(StringUtils.isNotBlank(MySysUser.nickName()) ? MySysUser.nickName() : MySysUser.loginName());
        }*/
        String retString = JSONObject.toJSONString(ret);
        sysLog.setResponse(retString.length()>5000? JSONObject.toJSONString("请求参数数据过长不与显示"):retString);
        sysLog.setUseTime(System.currentTimeMillis() - startTime.get());
//        System.out.println(sysLog.toString());
        sysLog.setCreateDate(new Date());
        sysLogService.save(sysLog);
    }
}
