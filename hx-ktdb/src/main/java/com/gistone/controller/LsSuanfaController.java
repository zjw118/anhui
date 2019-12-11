package com.gistone.controller;


import com.gistone.VO.ResultVO;
import com.gistone.entity.LsSuanfa;
import com.gistone.service.LsSuanfaService;
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
@RequestMapping("/api/ktdb/lsSuanfa")
public class LsSuanfaController {
    @Autowired
    private LsSuanfaService service;

    @PostMapping("/list")
    public ResultVO getList(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "请求数据data不能为空！");
        }
        Integer pageNum = (Integer) params.get("pageNum");
        Integer pageSize = (Integer) params.get("pageSize");
        String name = (String) params.get("name");
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
        LsSuanfa entity = service.getById(id);
        return ResultVOUtil.success(entity);
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultVO add(LsSuanfa entity, BindingResult bindingResult, MultipartFile file) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        //判断添加人是否为空
        if(file==null){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"文件不能为空");
        }
        String name = file.getOriginalFilename();
        String[] split = name.split("\\.");
        if(!"xml".equals(split[1])){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"请上传xml格式的文件");
        }
        service.insert(entity,file);
        return ResultVOUtil.success();
    }


    @RequestMapping(value = "/delete")
    public ResultVO delete(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "请求数据data不能为空！");
        }

        List<Integer> id = (List<Integer>) params.get("id");
        if (id == null || id.size() < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        service.delete(id);
        return ResultVOUtil.success();
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultVO update(LsSuanfa entity, BindingResult bindingResult, MultipartFile file) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        if(entity.getId()==null){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"id不能为空");
        }
        //判断更新人加人是否为空
        service.edit(entity,file);
        return ResultVOUtil.success();
    }

}
