package com.gistone.controller;


import com.gistone.VO.ResultVO;
import com.gistone.entity.BottomchartType;
import com.gistone.service.BottomchartTypeService;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zf1017@foxmail.com
 * @version v1.0
 * @since 2019-11-13
 */

@RestController
@RequestMapping("/api/ktdb/bottomchartType")
public class BottomchartTypeController {
    @Autowired
    private BottomchartTypeService service;

    @PostMapping("/list")
    public ResultVO getList() {
        List<BottomchartType> result = service.list();
        return ResultVOUtil.success(result);
    }


    @PostMapping("/detail")
    public ResultVO getById(@RequestBody Map<String, Object> params) {
        Integer id = (Integer) params.get("id");
        if (id == null || id < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        BottomchartType entity = service.getById(id);
        return ResultVOUtil.success(entity);
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultVO add(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "请求数据data不能为空！");
        }
        BottomchartType entity = new BottomchartType();
        String name = (String) dataParam.get("name");
        if (StringUtils.isBlank(name)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "类别名称不能为空");
        }
        entity.setName(name);
        Integer level = (Integer) dataParam.get("level");
        if (level == null && level > 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "类别级别不能为空");
        }
        entity.setLevel(level);
        Integer Fid = (Integer) dataParam.get("fid");
        if (Fid == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "父id不能为空");
        }
        entity.setFId(Fid);
        service.insert(entity);
        return ResultVOUtil.success();
    }


    @RequestMapping(value = "/delete")
    public ResultVO delete(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "请求数据data不能为空！");
        }
        List<Integer> id = (List<Integer>) dataParam.get("id");
        if (id == null || id.size() < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        service.delete(id);
        return ResultVOUtil.success();
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultVO update(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "请求数据data不能为空！");
        }

        Integer id = (Integer) dataParam.get("id");
        if (id == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }

        BottomchartType entity = service.getById(id);
        String name = (String) dataParam.get("name");
        if (StringUtils.isBlank(name)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "类别名称不能为空");
        }
        entity.setName(name);
        Integer level = (Integer) dataParam.get("level");
        if (level != null) {
            entity.setLevel(level);
        }
        entity.setLevel(level);
        Integer Fid = (Integer) dataParam.get("fid");
        if (Fid != null) {
            entity.setFId(Fid);
        }
        entity.setFId(Fid);
        //判断更新人加人是否为空
        service.edit(entity);
        return ResultVOUtil.success();
    }

}
