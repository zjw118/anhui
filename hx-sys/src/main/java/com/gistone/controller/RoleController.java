package com.gistone.controller;


import com.gistone.VO.ResultVO;
import com.gistone.entity.SysRole;
import com.gistone.service.ISysRoleService;
import com.gistone.service.ISysUserRoleService;
import com.gistone.service.ISysUserService;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户权限控制器
 */
@RestController
@RequestMapping("/api/sys/role")
@Slf4j
public class RoleController {
    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @Autowired
    private ISysUserService userService;

    /**
     * @return
     * @description: 用户角色列表
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/4/26 0026 13:41
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public ResultVO roleList(@RequestBody Map<String, Object> paramsMap) {
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

        Map<String, Object> result = roleService.getRoleList(name, pageNum, pageSize);

        return ResultVOUtil.success(result);
    }

    /**
     * @return
     * @description: 添加角色
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/4/29 0029 9:22
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultVO addRole(@RequestBody Map<String, Object> paramsMap) {
        SysRole role = new SysRole();

        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        String name = (String) dataParam.get("name");

        Integer createBy = (Integer) dataParam.get("id");

        if (StringUtils.isBlank(name)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "角色名称不能为空");
        }
        if (createBy == null || createBy <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "创建人不能为空");
        }
        List<Integer> resourceIds = (List<Integer>) dataParam.get("resourceIds");

        String remark = (String) dataParam.get("remarks");
        if(StringUtils.isNotBlank(remark)){
            role.setRemarks(remark);
        }


        try {
            SysRole sysRole = roleService.selectByName(name);

            if (sysRole!=null) {
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "角色名称已经存在");
            }


            role.setName(name);
            role.setCreateBy(createBy);
            role.setCreateDate(new Date());
            role.setResourceIds(resourceIds);
            roleService.saveRole(role);
        } catch (Exception e) {

            log.error("添加角色失败，异常信息:{}", e.getMessage());
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "添加角色失败");
        }
        return ResultVOUtil.success();

    }

    /**
     * @return
     * @description: 删除角色
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/4/29 0029 9:22
     */
    @PostMapping(value = "/delete")
    public ResultVO deleteRole(@RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        Integer id = (Integer) dataParam.get("id");
        if (id == null || id <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "角色id不能为空");
        }

        try {
            SysRole sysRole = roleService.getById(id);

            roleService.deleteRole(sysRole);
        } catch (Exception e) {
            log.error("删除角色失败，异常信息:{}", e.getMessage());
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "删除角色失败");

        }

        return ResultVOUtil.success();

    }

    /**
     * @return
     * @description: 编辑角色
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/4/29 0029 9:24
     */
    @PostMapping(value = "/edit")
    public ResultVO editRole(@RequestBody Map<String, Object> paramsMap) {


        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        Integer id = (Integer) dataParam.get("id");

        if (id == null || id <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "角色id不能为空");
        }
        String name = (String) dataParam.get("name");
        if (StringUtils.isBlank(name)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "角色名称不能为空");
        }

        Integer updateBy = (Integer) dataParam.get("updateBy");
        if (updateBy == null || updateBy <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "修改人id不能为空");
        }

        List<Integer> resourceIds = (List<Integer>) dataParam.get("resourceIds");


        try {
           /* SysRole sysRole = roleService.getById(id);

            if (name.equals(sysRole.getName())) {
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "角色名称已经存在");
            }*/
            SysRole sysRoleParam = new SysRole();
            sysRoleParam.setId(id);
            sysRoleParam.setName(name);
            sysRoleParam.setUpdateBy(updateBy);
            sysRoleParam.setUpdateDate(new Date());
            sysRoleParam.setResourceIds(resourceIds);

            roleService.updateRoles(sysRoleParam);


        } catch (Exception e) {
            log.error("编辑角色失败，异常信息:{}", e.getMessage());
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "编辑角色失败");
        }
        return ResultVOUtil.success();
    }


    /**
     * @description: 编辑用角色权限
     * @param:
     * @return
     * @author zf1017@foxmail.com
     * @date 2019/4/29 0029 13:28
     */
    @PostMapping(value = "/editRoleResoure")
    public ResultVO editRoleResource(@RequestBody Map<String, Object> paramsMap){
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        //需要两个参数1.角色id，2权限id集合

        Integer roleId = (Integer) dataParam.get("roleId");
        List<Integer> sysResources = (List<Integer>) dataParam.get("resourcesIds");
        if(roleId==null ||roleId<=0){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"角色id不能为空");
        }

        if(sysResources==null ||sysResources.size()<=0){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"无要添加的权限");
        }


       roleService.saveRoleResource(roleId,sysResources);

        return ResultVOUtil.success();

    }



}
