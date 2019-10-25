package com.gistone.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.VO.ResultVO;
import com.gistone.entity.SysUser;
import com.gistone.service.ISysUserService;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/sys/")
@Api(value = "API-UserManage", tags = "用户", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
    @ApiOperation(value = "人员列表", notes = "人员列表", response = ResultVO.class)
    @PostMapping(value = "/list")
    public ResultVO list(@RequestBody Map<String, Object> paramsMap){
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        Integer pageNum = (Integer) dataParam.get("pageNumber");
        Integer pageSize = (Integer) dataParam.get("pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }
        Map<String,Object> result = iSysUserService.getList(pageNum,pageSize);

        return ResultVOUtil.success(result);
    }

}

