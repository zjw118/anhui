package com.gistone.controller;


import com.gistone.VO.ResultVO;
import com.gistone.entity.RlhdGroup;
import com.gistone.entity.St4ScsCb;
import com.gistone.entity.St4ScsCbb;
import com.gistone.entity.St4ScsCd;
import com.gistone.pkname.Swagger;
import com.gistone.service.ISt4ScsCdService;
import com.gistone.service.RlhdGroupService;
import com.gistone.util.ObjectUtils;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultMsg;
import com.gistone.util.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(value="人类活动台账信息表",tags = "人类活动台账信息表")
@RestController
@RequestMapping("/api/ygjc/rlhdGroup")
public class RlhdGroupController {
    @Autowired
    private RlhdGroupService service;

    @Autowired
    private ISt4ScsCdService st4ScsCdService;

    @ApiOperation(value="根据任务id查询其下的所有斑块及下发的人(斑块是否核查看st4ScsCk对象里的ck088的字段是0代表未核查否则是已核查)",notes = "列表数据列表",response = St4ScsCd.class)
    @PostMapping(value="/listReserveData")
    public ResultVO listReserveData(@RequestBody @ApiParam(name="各类保护地数据 列表", value="json格式", required=true)
                                            Swagger<St4ScsCd> cdLedger) {

        St4ScsCd cd = cdLedger.getData();
        if(!ObjectUtils.isNotNullAndEmpty(cd.getCl001())){
            return  ResultVOUtil.error("1222", "任务ID不能为空!");
        }
        if(!ObjectUtils.isNotNullAndEmpty(cd.getPageNumber())||!ObjectUtils.isNotNullAndEmpty(cd.getPageSize())){
            return  ResultVOUtil.error("1222", ResultMsg.MSG_1018);
        }

        return st4ScsCdService.getProblemPlaque(cd);
    }

    @PostMapping("/list")
    public ResultVO getList(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
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
        Integer pageNum = (Integer) params.get("pageNum");
        Integer pageSize = (Integer) params.get("pageSize");
        String name = (String) params.get("name");
        if (pageNum == null) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }
       Map<String,Object> result = service.getDetailById(pageNum,pageSize,id);
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
        if (StringUtils.isBlank(name)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "台账名称不能为空");
        }
        List<Integer> ids = (List<Integer>) params.get("id");


        Integer createBy = (Integer) params.get("createBy");
        if (createBy == null || createBy <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "创建人id不能为空");
        }

        String remark = (String) params.get("remark");


        service.insert(name, createBy, remark, ids);
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

    /**
     * @param paramsMap
     * @return com.gistone.VO.ResultVO
     * @description:为台账添加数据
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/10/25 0025 14:08
     */
    @PostMapping("/addDataToGroup")
    public ResultVO addDataToGroup(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        Integer groupId = (Integer) params.get("groupId");
        if (groupId == null || groupId <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "台账id不能为空");
        }
        List<Integer> id = (List<Integer>) params.get("id");
        if (id == null || id.size() <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "数据id不能为空");
        }

        service.addDataToGroup(groupId, id);

        return ResultVOUtil.success();
    }

    @PostMapping("/deleteDataFromGroup")
    public ResultVO deleteDataFromGroup(@RequestBody Map<String, Object> paramsMap){
        //请求参数格式校验
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        Integer groupId = (Integer) params.get("groupId");
        if (groupId == null || groupId <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "台账id不能为空");
        }
        List<Integer> id = (List<Integer>) params.get("id");
        if (id == null || id.size() <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "数据id不能为空");
        }

        service.deleteDataFromGroup(groupId,id);
        return ResultVOUtil.success();
    }


}
