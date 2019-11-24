package com.gistone.controller;


import com.gistone.VO.ResultVO;
import com.gistone.annotation.SysLog;
import com.gistone.entity.RlhdGroup;
import com.gistone.entity.St4ScsCd;
import com.gistone.entity.St4ScsCl;
import com.gistone.entity.St4SysSa;
import com.gistone.pkname.Swagger;
import com.gistone.service.ISt4ScsCdService;
import com.gistone.service.RlhdGroupService;
import com.gistone.swagger.SharePoint;
import com.gistone.util.ObjectUtils;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultMsg;
import com.gistone.util.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    @ApiOperation(value="得到台账下的斑块点",notes = "",response = St4ScsCd.class)
    @PostMapping(value="/getPointFromStage")
    public ResultVO getPointFromStage(@RequestBody @ApiParam(name="", value="json格式", required=true)
                                                 Swagger<SharePoint> cdLedger) {
        SharePoint sharePoint =cdLedger.getData();
        if(!ObjectUtils.isNotNullAndEmpty(sharePoint.getTaskId())){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "台账id不能为空！");
        }
        return st4ScsCdService.getPointFromStage(sharePoint.getTaskId());
    }
    @ApiOperation(value="删除已下发的斑块",notes = "",response = St4ScsCd.class)
    @PostMapping(value="/deletePersonAndPoint")
    public ResultVO deletePersonAndPoint(@RequestBody @ApiParam(name="", value="json格式", required=true)
                                              Swagger<SharePoint> cdLedger) {
        SharePoint sp = cdLedger.getData();
        if(!ObjectUtils.isNotNullAndEmpty(sp.getUid())){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "下发人员不能为空！");
        }
        if(!ObjectUtils.isNotNullAndEmpty(sp.getPointIdList())||sp.getPointIdList().size()<1){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "斑块id不能为空！");
        }
        return st4ScsCdService.deletePersonAndPoint(sp.getUid(),sp.getPointIdList());
    }
    @ApiOperation(value="根据任务id查询其下的所有斑块及下发的人",notes = "",response = St4ScsCd.class)
    @PostMapping(value="/getPersonAndPoint")
    public ResultVO getPersonAndPoint(@RequestBody @ApiParam(name="", value="json格式", required=true)
                                            Swagger<St4ScsCl> clSwagger) {
        St4ScsCl sa = clSwagger.getData();
//        if(!ObjectUtils.isNotNullAndEmpty(sa.getSa001())){
//            return  ResultVOUtil.error("1222", "人员ID不能为空!");
//        }
        return st4ScsCdService.getPersonAndPoint( sa.getCl001());

    }
    @ApiOperation(value="根据任务id查询其下的所有斑块及下发的人",notes = "",response = St4ScsCd.class)
    @PostMapping(value="/listReserveData")
    public ResultVO listReserveData(@RequestBody @ApiParam(name="", value="json格式", required=true)
                                            Swagger<St4ScsCd> cdLedger) {

        St4ScsCd cd = cdLedger.getData();
        if(!ObjectUtils.isNotNullAndEmpty(cd.getCl001())){
            return  ResultVOUtil.error("1222", "任务ID不能为空!");
        }
//        if(!ObjectUtils.isNotNullAndEmpty(cd.getPageNumber())||!ObjectUtils.isNotNullAndEmpty(cd.getPageSize())){
//            return  ResultVOUtil.error("1222", ResultMsg.MSG_1018);
//        }

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
    public ResultVO delete(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        List<Integer> id = (List<Integer>) params.get("id");
        if (id == null || id.size() <=0) {
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
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        Integer id = (Integer) params.get("id");
        if(id==null){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"id不能为空");
        }
        RlhdGroup rlhdGroup = service.getById(id);
        String name = (String) params.get("name");
        if(StringUtils.isNotBlank(name)){
            rlhdGroup.setName(name);
        }
        String remark = (String) params.get("remark");
        if (StringUtils.isNotBlank(remark)){
            rlhdGroup.setRemark(remark);
        }

//判断更新人加人是否为空
        service.edit(rlhdGroup);
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
    @SysLog("删除台账中的解译数据")
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
