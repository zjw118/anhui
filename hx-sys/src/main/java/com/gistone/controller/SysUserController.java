package com.gistone.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.VO.ResultVO;
import com.gistone.entity.SysUser;
import com.gistone.service.ISysUserService;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/sys/sysUser")
public class SysUserController {
    @Autowired
    private ISysUserService iSysUserService;

    @RequestMapping(value = "/getByPhone",method = RequestMethod.POST)
    public SysUser getByPhone(String phone){
        QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper<>();
        sysUserQueryWrapper.eq("username",phone);
        sysUserQueryWrapper.eq("enable",1);
        sysUserQueryWrapper.eq("type",1);
        return iSysUserService.getOne(sysUserQueryWrapper);
    }

    @RequestMapping(value = "/list")
    public ResultVO list(@RequestBody Map<String, Object> paramsMap){
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        String name = (String) dataParam.get("name");
        Integer pageNum = (Integer) dataParam.get("pageNum");
        Integer pageSize = (Integer) dataParam.get("pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }
        Map<String,Object> result = iSysUserService.getList(name,pageNum,pageSize);

        return ResultVOUtil.success(result);
    }

}

