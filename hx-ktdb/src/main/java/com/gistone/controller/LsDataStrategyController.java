package com.gistone.controller;


import com.gistone.VO.ResultVO;
import com.gistone.entity.LsDataStrategy;
import com.gistone.service.LsDataStrategyService;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zf1017@foxmail.com
 * @version v1.0
 * @since 2019-11-27
 */

@RestController
@RequestMapping("/api/ktdb/lsDataStrategy")
public class LsDataStrategyController {
    @Autowired
    private LsDataStrategyService service;

    @PostMapping("/list")
    public ResultVO getList(@RequestBody Map<String, Object> paramsMap) {
//请求参数格式校验
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "请求数据data不能为空！");
        }
        Integer pageNum = (Integer) params.get("pageNum");
        Integer pageSize = (Integer) params.get("pageSize");
        String name = (String) params.get("cycle");
        if (pageNum == null) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }

        Map<String, Object> result = service.list(pageNum, pageSize, name);
        return ResultVOUtil.success(result);
    }


    @PostMapping("/detail")
    public ResultVO getById(@RequestBody Map<String, Object> params) {
        Integer id = (Integer) params.get("id");
        if (id == null || id < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        LsDataStrategy entity = service.getById(id);
        return ResultVOUtil.success(entity);
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultVO add(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "请求数据data不能为空！");
        }
        LsDataStrategy entity = new LsDataStrategy();
        //判断添加人是否为空
        String cycle = (String) params.get("cycle");
        if (StringUtils.isBlank(cycle)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "周期不能为空");
        }
        entity.setCycle(cycle);
        String type = (String) params.get("type");
        if (StringUtils.isBlank(type)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "类型不能为空");
        }
        entity.setType(type);

        String timeStr = (String) params.get("timeStr");
        if (StringUtils.isBlank(timeStr)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "时间不能为空");
        }
        entity.setTimeStr(timeStr);


        service.insert(entity);
        return ResultVOUtil.success();
    }


    @RequestMapping(value = "/delete")
    public ResultVO delete(@RequestBody Map<String, Object> paramsMap) {

        //请求参数格式校验
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "请求数据data不能为空！");
        }
        Integer id = (Integer) params.get("id");
        if (id == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        service.delete(id);
        return ResultVOUtil.success();
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultVO update(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "请求数据data不能为空！");
        }
        Integer id = (Integer) params.get("id");
        if (id == null || id < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能");
        }
        LsDataStrategy entity = service.getById(id);
        String cycle = (String) params.get("cycle");
        if (StringUtils.isNotBlank(cycle)) {
            entity.setCycle(cycle);
        }

        String type = (String) params.get("type");
        if (StringUtils.isNotBlank(type)) {
            entity.setType(type);
        }

        String timeStr = (String) params.get("timeStr");
        if (StringUtils.isNotBlank(timeStr)) {
            entity.setTimeStr(timeStr);
        }
        service.edit(entity);
        return ResultVOUtil.success();
    }

}
