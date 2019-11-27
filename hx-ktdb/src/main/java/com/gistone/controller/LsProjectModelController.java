package com.gistone.controller;


import com.gistone.VO.ResultVO;
import com.gistone.entity.LsProjectModel;
import com.gistone.service.LsProjectModelService;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zf1017@foxmail.com
 * @version v1.0
 * @since 2019-11-26
 */

@RestController
@RequestMapping("/api/ktdb/lsProjectModel")
public class LsProjectModelController {
    @Autowired
    private LsProjectModelService service;

    @PostMapping("/list")
    public ResultVO getList(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "请求数据data不能为空！");
        }
        Integer pageNum = (Integer) dataParam.get("pageNum");
        Integer pageSize = (Integer) dataParam.get("pageSize");
        String name = (String) dataParam.get("name");
        Integer type = (Integer) dataParam.get("type");
        if (pageNum == null) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }

        Map<String, Object> result = service.list(pageNum, pageSize, name,type);
        return ResultVOUtil.success(result);
    }


    @PostMapping("/detail")
    public ResultVO getById(@RequestBody Map<String, Object> params) {
        Integer id = (Integer) params.get("id");
        if (id == null || id < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        LsProjectModel entity = service.getById(id);
        return ResultVOUtil.success(entity);
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultVO add(LsProjectModel entity, BindingResult bindingResult,
                        @RequestParam MultipartFile file) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        if (file == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "文件不能为空");
        }
        //判断添加人是否为空
        service.insert(entity, file);
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
        if (id == null ||id.size() <0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        service.delete(id);
        return ResultVOUtil.success();
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultVO update(LsProjectModel entity, BindingResult bindingResult,MultipartFile file) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        if(entity.getId()==null){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"id不能为空");
        }
        if(entity.getType()==null){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"type不能为空");
        }
        service.edit(entity,file);
        return ResultVOUtil.success();
    }





}
