package com.gistone.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.VO.ResultVO;
import com.gistone.entity.SysResources;
import com.gistone.service.ISysResourcesService;
import com.gistone.service.ISysRoleResourcesService;
import com.gistone.service.ISysRoleService;
import com.gistone.util.ResourceUtil;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 权限(资源)角色控制器，用于为角色增删权限
 */
@RestController
@RequestMapping("/api/sys/resource")
@Transactional
@Slf4j
public class ResourceController {
    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysResourcesService sysResourcesService;

    @Autowired
    private ISysRoleResourcesService sysRoleResourcesService;

    /**
     * @return
     * @description: 获取权限列表
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/5/6 0006 11:12
     */
    @RequestMapping(value = "/list")
    public ResultVO resourceList() {

        QueryWrapper<SysResources> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        List<SysResources> sysRoleResources = sysResourcesService.list(wrapper);
        Set<SysResources> result = ResourceUtil.buildTree(sysRoleResources, 0);
        //对新的集合进行按照sort值排序
        Set<SysResources> newList =  result.stream().sorted(Comparator.comparing(SysResources::getSort))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        return ResultVOUtil.success(newList);

    }

    /**
     * @return
     * @description: 资源增加
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/5/6 0006 13:58
     */
    @PostMapping(value = "/add")
    public ResultVO addResource(@RequestBody Map<String, Object> paramsMap) {
        SysResources sysResources = new SysResources();
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        String name = (String) dataParam.get("name");
        if (StringUtils.isBlank(name)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "权限名称不能为空");
        }
        sysResources.setName(name);

        SysResources resources = sysResourcesService.getOne(new QueryWrapper<SysResources>().eq("name", name));
        if (resources != null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "权限名称已经存在");
        }

        String url = (String) dataParam.get("url");
        if (StringUtils.isBlank(url)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "资源路径不能为空");
        }
        sysResources.setResUrl(url);

        String apiPath = (String) dataParam.get("paiPath");
        if (StringUtils.isBlank(apiPath)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "api地址不能为空");
        }

        Integer category = (Integer) dataParam.get("category");
        if (category == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "资源类型不能为空");
        }

        Integer parantId = (Integer) dataParam.get("parentId");

        if (parantId == null) {
            sysResources.setLevel(1);
            Integer maxSort = sysResourcesService.getMaxSort();
            int sort = 0;
            if (maxSort != null) {
                sort = maxSort + 10;
            }
            sysResources.setSort(sort);
        } else {
            SysResources sysResources1 = sysResourcesService.getById(parantId);
            if (sysResources1 == null) {
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "父资源不存在");
            }

            sysResources.setParentId(parantId);
            sysResources.setLevel(sysResources1.getLevel() + 1);

            int sort = 0;
            Integer maxSort = sysResourcesService.selectMaxSort(sysResources.getParentId());
            if (maxSort != null) {
                sort = maxSort + 10;
            }

            sysResources.setSort(sort);

        }


        sysResourcesService.save(sysResources);

        return ResultVOUtil.success();
    }

    /**
     * @return
     * @description: 资源编辑
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/5/6 0006 14:34
     */
    @PostMapping(value = "/edit")
    public ResultVO editResource(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        SysResources sysResources = new SysResources();
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        Integer id = (Integer) dataParam.get("id");
        if (id == null || id <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "资源id不能为空");
        }
        sysResources.setId(id);

        String name = (String) dataParam.get("name");
        if (StringUtils.isBlank(name)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "资源名称不能为空");
        }
        sysResources.setName(name);

        SysResources sysResources1 = sysResourcesService.getById(id);
        if (!name.equals(sysResources1.getName())) {
            SysResources sysResources2 = sysResourcesService.getOne(new QueryWrapper<SysResources>().eq("name", name));
            if (sysResources2 != null) {
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "资源名称已经存在");
            }

        }

        String url = (String) dataParam.get("url");
        if (StringUtils.isBlank(url)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "资源路径不能为空");
        }
        sysResources.setResUrl(url);

        String apiPath = (String) dataParam.get("paiPath");
        if (StringUtils.isBlank(apiPath)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "api地址不能为空");
        }

        Integer category = (Integer) dataParam.get("category");
        if (category == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "资源类型不能为空");
        }

        sysResourcesService.updateById(sysResources);


        return ResultVOUtil.success();
    }

    @PostMapping(value = "/delete")
    public ResultVO deleteResource(@RequestBody Map<String, Object> paramsMap){
        //请求参数格式校验

        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        Integer id = (Integer) dataParam.get("id");
        if (id == null || id <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "资源id不能为空");
        }

        SysResources sysResources = sysResourcesService.getById(id);

        sysResources.setDelFlag(0);

        sysResourcesService.updateById(sysResources);
        return ResultVOUtil.success();
    }

}
