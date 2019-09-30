package com.gistone.controller;


import com.gistone.VO.ResultVO;
import com.gistone.entity.SysMobileUser;
import com.gistone.entity.SysUser;
import com.gistone.entity.SysUserRole;
import com.gistone.service.ISysMobileUserService;
import com.gistone.service.ISysUserRoleService;
import com.gistone.service.ISysUserService;
import com.gistone.util.Md5Util;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 * 移动用户管理控制器
 * </p>
 *
 * @author zf
 * @since 2019-03-05
 */
@RestController
@RequestMapping("/api/sys/sysMobileUser")
@Transactional
@Slf4j
public class SysMobileUserController {

    @Autowired
    private ISysMobileUserService sysMobileUserService;

    @Autowired
    private ISysUserService iSysUserService;

    @Autowired
    private ISysUserRoleService sysUserRoleService;



    /**
     * 移动端用户-查询
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResultVO userList(@RequestBody Map<String, Object> paramsMap) {
        //准备参数
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        Integer page = (Integer) params.get("page");
        Integer limit = (Integer) params.get("limit");
        String realName = (String) params.get("realName");
        String phoneNumber = (String) params.get("phoneNumber");
        String code = (String) params.get("code");
        String department = (String) params.get("department");

        String codes = null;
        if (org.apache.commons.lang.StringUtils.isNotBlank(code)) {


            Integer level = sysMobileUserService.getLevelByCode(code);
            if (level != null && level > 0) {
                if (level == 1) {
                    codes = code.substring(0, 2);
                } else if (level == 2) {
                    codes = code.substring(0, 4);
                } else {
                    codes = code;
                }
            } else {
                codes = code;
            }
        }
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 10;
        }
        //查询
        List<SysMobileUser> sysMobileUserList = sysMobileUserService.selectMobileUserList(page, limit, realName, phoneNumber, codes, department);
        if(sysMobileUserList!=null&&sysMobileUserList.size()>0){
            for (SysMobileUser sysMobileUser : sysMobileUserList) {
                sysMobileUser.setRoles(sysMobileUserService.getRoles(sysMobileUser.getUserId()));
            }
        }
        int total = sysMobileUserService.selectTotal(page, limit, realName, phoneNumber, codes, department);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", sysMobileUserList);
        result.put("total", total);
        return ResultVOUtil.success(result);
    }

    /**
     * 移动端用户-删除
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultVO deleteUser(@RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> param = (Map<String, Object>) paramsMap.get("data");
        Integer userId = (Integer) param.get("userId");

        if (userId == null || userId == 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "参数错误");
        }

        SysMobileUser sysMobileUser = sysMobileUserService.selectMobileUserById(userId);
        if (sysMobileUser == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "用户不存在");
        }

        ResultVO result = sysMobileUserService.deleteUser(userId);

        return result;

    }

    /**
     * 移动端用户-编辑
     *
     * @param
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public ResultVO editUser(@RequestBody Map<String, Object> paramsMap) {
        //准备参数
        Map<String, Object> param = (Map<String, Object>) paramsMap.get("data");
        SysMobileUser user = new SysMobileUser();
        user.setId((Integer) param.get("id"));
        user.setCode((String) param.get("code"));
        user.setDepartmentId((Integer) param.get("department"));
        user.setRealName((String) param.get("realName"));
        user.setPhoneNumber((String) param.get("phoneNumber"));

        Integer updateBy = (Integer) param.get("updateBy");

//        Set<Integer> roleIds = (Set<Integer>) param.get("roleIds");

        if (updateBy == null || updateBy <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "更新人id不能为空");
        }

        if (user.getId() == 0 || user.getId() == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "用户Id不为空");
        }

       /* if (user.getPhoneNumber() != null && user.getPhoneNumber() != "") {
            SysMobileUser sysMobileUser = sysMobileUserService.selectMobileUserByPhoneNumber(user.getPhoneNumber());

            if (sysMobileUser != null) {
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "该手机号已经被其他用户占用");
            }
        }*/

        SysUser sysUser = new SysUser();

        SysMobileUser sysMobileUser = sysMobileUserService.getById(user.getId());
        sysUser.setId(sysMobileUser.getUserId());
        sysUser.setUpdateBy(updateBy);
        sysUser.setUpdateDate(new Date());
//        user.setRoleIds(roleIds);

        iSysUserService.updateById(sysUser);
        sysMobileUserService.updateUser(user);


        return ResultVOUtil.success();
    }

    /**
     * @return
     * @description: 添加移动端用户
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/4/22 0022 15:45
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultVO saveUser(@RequestBody Map<String, Object> paramsMap) {
        // 准备参数 涉及到另一张表的初始化
        Map<String, Object> param = (Map<String, Object>) paramsMap.get("data");
        SysMobileUser sysMobileUser = new SysMobileUser();
        sysMobileUser.setPhoneNumber((String) param.get("phoneNumber"));
        sysMobileUser.setRealName((String) param.get("realName"));
        sysMobileUser.setCode((String) param.get("code"));
        sysMobileUser.setDepartmentId((Integer) param.get("department"));

        Integer createBy = (Integer) param.get("createBy");

        if (createBy == null || createBy <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "创建人id不能为空");
        }

        if (StringUtils.isBlank(sysMobileUser.getRealName())) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "真实姓名不能为空");
        }
        if (StringUtils.isBlank(sysMobileUser.getCode())) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "行政区划不能为空");
        }

        if (sysMobileUser.getDepartmentId() == null || sysMobileUser.getDepartmentId() == 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "所属部门不能为空");
        }

        if (StringUtils.isBlank(sysMobileUser.getPhoneNumber())) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "手机号码不能为空");
        }

        SysMobileUser sysMobileUser1 = sysMobileUserService.selectMobileUserByPhoneNumber(sysMobileUser.getPhoneNumber());
        if (sysMobileUser1 != null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "该手机号已被使用");
        }

        try {
            //初始化User表
            SysUser sysUser = new SysUser();
            sysUser.setUsername(sysMobileUser.getPhoneNumber());
            sysUser.setEnable(1);
            sysUser.setType(1);

            String str = sysMobileUser.getPhoneNumber().substring(5);
            //密码MD5加密
            sysUser.setPassword(Md5Util.md5(str));
            sysUser.setCreateDate(new Date());
            sysUser.setCreateBy(createBy);

            boolean insert1 = iSysUserService.save(sysUser);

            if (!insert1) {
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "添加失败");
            }

            sysMobileUser.setUserId(sysUser.getId());

            boolean insert = sysMobileUserService.save(sysMobileUser);

            if (!insert) {
                return ResultVOUtil.error(ResultEnum.SUCCESS.getCode(), "添加失败");
            }

            //初始化用户权限，默认角色采集作业用户
            SysUserRole sysUserRole = new SysUserRole(sysUser.getId(), 7);

            boolean insert2 = sysUserRoleService.save(sysUserRole);

            if (!insert2) {
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "添加失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("添加移动端用户失败，异常信息为：{}", e.getMessage());
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "添加失败");
        }


        return ResultVOUtil.success();
    }

    /**
     * @return
     * @description: 编辑用户密码
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/4/22 0022 16:24
     */
    @RequestMapping(value = "/editPassword")
    public ResultVO editPassword(@RequestBody Map<String, Object> paramsMap) {
        //1.准备参数
        Map<String, Object> param = (Map<String, Object>) paramsMap.get("data");
        Integer userId = (Integer) param.get("userId");
        String oldPassword = (String) param.get("oldPassword");
        String newPassword = (String) param.get("newPassword");

        if (userId == null || userId == 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "用户id不能为空");
        }

        if (oldPassword == null || oldPassword == "") {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "旧密码不能为空");
        }

        if (newPassword == null || newPassword == "") {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "新密码不能为空");
        }

        //2.验证老密码是否输入正确
        SysUser sysUser = iSysUserService.getById(userId);
        if (!(Md5Util.md5(oldPassword)).equals(sysUser.getPassword())) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "输入的旧密码有误");
        }

        //3.修改新密码
        sysUser.setPassword(Md5Util.md5(newPassword));
        boolean b = iSysUserService.updateById(sysUser);
        if (!b) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "修改失败");
        }

        return ResultVOUtil.success();
    }

    /**
     * @return
     * @description: 重置移动端用户密码
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/4/22 0022 15:43
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ResultVO resetPassword(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        Integer id = (Integer) dataParam.get("id");

        //通过id查询用户
        SysMobileUser sysMobileUser = sysMobileUserService.selectMobileUserById(id);
        //获取手机号后六位加密重置密码
        if (sysMobileUser == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "重置密码失败");
        }
        SysUser sysUser = new SysUser();

        String str = sysMobileUser.getPhoneNumber().substring(5);
        //密码MD5加密
        sysUser.setPassword(Md5Util.md5(str));
        sysUser.setId(sysMobileUser.getUserId());

        boolean insert1 = iSysUserService.updateById(sysUser);

        if (!insert1) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "重置密码失败");
        }
        return ResultVOUtil.success();
    }

    /**
     * @return
     * @description: 获取单个用户信息
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/4/22 0022 16:23
     */
    @RequestMapping(value = "/getUserInfo")
    public ResultVO getUserInfo(@RequestBody Map<String, Object> paramsMap) {
        //1.准备参数
        Map<String, Object> param = (Map<String, Object>) paramsMap.get("data");
        Integer userId = (Integer) param.get("userId");

        if (userId == null || userId == 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "用户id不能为空");
        }

        SysMobileUser sysMobileUser = sysMobileUserService.selectMobileUserById(userId);
        if (sysMobileUser == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "获取用户信息失败");
        }
        return ResultVOUtil.success(sysMobileUser);
    }


}

