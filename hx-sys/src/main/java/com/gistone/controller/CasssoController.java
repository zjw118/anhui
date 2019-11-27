package com.gistone.controller;


import com.gistone.entity.SysRole;
import com.gistone.mapper.SysUserMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/cassso")
public class CasssoController {

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 单点登陆对外接口
     * @param request
     * @param response
     * @param accessToken
     * @throws Exception
     */
    @RequestMapping(value = "/getuseraccount", method = RequestMethod.GET)
    public void getVerificationCode(HttpServletRequest request, HttpServletResponse response, String accessToken) throws Exception {
        PrintWriter pw = response.getWriter();
        String userId = request.getParameter("account");
        if(StringUtils.isBlank(userId)){
            pw.print("");
        }
        SysRole sysRole = sysUserMapper.getRoleOfUser(Integer.valueOf(userId));
        if(null!=sysRole){
            HttpSession session = request.getSession();
            session.setAttribute("user",sysRole);
            pw.print(userId);
        }else{
            pw.print("");
        }
    }

}
