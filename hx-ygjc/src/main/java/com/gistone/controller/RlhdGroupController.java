package com.gistone.controller;


import com.gistone.VO.ResultVO;
import com.gistone.entity.Iterpretation;
import com.gistone.entity.RlhdGroup;
import com.gistone.service.RlhdGroupService;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 人类活动台账信息表 前端控制器
 * </p>
 *
 * @author zf1017@foxmail.com
 * @version v1.0
 * @since 2019-10-25
 */

@RestController
@RequestMapping("/api/ygjc/rlhdGroup")
public class RlhdGroupController {
    @Autowired
    private RlhdGroupService service;

    @PostMapping("/list")
    public ResultVO getList(@RequestBody Map<String, Object> params) {

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
    public ResultVO getById(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        Integer id = (Integer) params.get("id");
        if (id == null || id < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        List<Iterpretation> result = service.getDetailById(id);
        return ResultVOUtil.success(result);
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultVO add(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        String name = (String) params.get("name");
        if(StringUtils.isBlank(name)){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"台账名称不能为空");
        }
        List<Integer> ids = (List<Integer>) params.get("id");
        if(ids==null||ids.size()<0){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"id不能为空");
        }

        Integer createBy  = (Integer) params.get("createBy");
        if(createBy==null||createBy<=0){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"创建人id不能为空");
        }

        String remark = (String) params.get("remark");



        service.insert(name,createBy,remark,ids);
        return ResultVOUtil.success();
    }


    @RequestMapping(value = "/delete")
    public ResultVO delete(@RequestBody Map<String, Object> params) {
        List<Integer> id = (List<Integer>) params.get("id");
        if (id != null && id.size() > 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        service.delete(id);
        return ResultVOUtil.success();
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultVO update(@RequestBody RlhdGroup entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
//判断更新人加人是否为空
        service.edit(entity);
        return ResultVOUtil.success();
    }

    @PostMapping("addDataToGroup")
    public ResultVO addDataToGroup(@RequestBody Map<String, Object> paramsMap){
        //请求参数格式校验
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        Integer groupId = (Integer) params.get("groupId");
        if(groupId==null||groupId<=0){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"台账id不能为空");
        }
        Integer id = (Integer) params.get("id");
        if(id==null||id<=0){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"数据id不能为空");
        }

        service.addDataToGroup(groupId,id);

        return ResultVOUtil.success();
    }


}
