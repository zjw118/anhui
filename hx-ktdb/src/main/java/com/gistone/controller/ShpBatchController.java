package com.gistone.controller;


import com.gistone.VO.ResultVO;
import com.gistone.annotation.SysLog;
import com.gistone.entity.ShpBatch;
import com.gistone.service.ShpBatchService;
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
 * 预设数据文件批次表 前端控制器
 * </p>
 *
 * @author zf1017@foxmail.com
 * @version v1.0
 * @since 2019-10-29
 */

@RestController
@RequestMapping("/api/ktdb/shpBatch")
public class ShpBatchController {
    @Autowired
    private ShpBatchService service;

    /**
     * @param params
     * @return com.gistone.VO.ResultVO
     * @description:预置数据批次列表
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/11/1 0001 14:31
     */
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

    /**
     * @param
     * @return com.gistone.VO.ResultVO
     * @description:获取每个分类最新的批次数据
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/11/1 0001 14:36
     */
    @PostMapping("/getNewList")
    public ResultVO getNewList() {
        List<ShpBatch> result = service.getNewList();
        return ResultVOUtil.success(result);
    }


    @PostMapping("/detail")
    @SysLog("预设数据批次明细")
    public ResultVO getById(@RequestBody Map<String, Object> params) {
        Integer id = (Integer) params.get("id");
        if (id == null || id < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        ShpBatch entity = service.getById(id);
        return ResultVOUtil.success(entity);
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @SysLog("添加预设数据批次")
    public ResultVO add(@RequestBody ShpBatch entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        //判断添加人是否为空
        service.insert(entity);
        return ResultVOUtil.success();
    }


    @RequestMapping(value = "/delete")
    @SysLog("删除预设数据批次")
    public ResultVO delete(@RequestBody Map<String, Object> params) {
        List<Integer> id = (List<Integer>) params.get("id");
        if (id != null && id.size() > 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        service.delete(id);
        return ResultVOUtil.success();
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @SysLog("编辑预设数据批次")
    public ResultVO update(@RequestBody ShpBatch entity, BindingResult bindingResult) {
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
     * @description:通过服务导入预置红线数据
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/10/30 0030 10:55
     */
    @PostMapping("/importPreRedlineData")
    @SysLog("导入预置红线数据")
    public ResultVO importPreRedlineDate(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "请求数据data不能为空！");
        }
        //服务地址
        String url = (String) dataParam.get("url");
        if (StringUtils.isBlank(url)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "服务地址不能为空");
        }
        String remark = (String) dataParam.get("remark");

        service.importPreRedlineDate(url, remark);
        return ResultVOUtil.success();
    }

    /***
     * @description:通过服务导入预置界桩数据
     * @param paramsMap
     * @return com.gistone.VO.ResultVO
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/10/30 0030 15:04
     */
    @PostMapping("/importPreMarkerData")
    @SysLog("导入预置界桩数据")
    public ResultVO importPreMarkerData(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "请求数据data不能为空！");
        }
        //服务地址
        String url = (String) dataParam.get("url");
        if (StringUtils.isBlank(url)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "服务地址不能为空");
        }
        String remark = (String) dataParam.get("remark");

        service.importPreMarkerData(url, remark);
        return ResultVOUtil.success();
    }

    /**
     * @param paramsMap
     * @return com.gistone.VO.ResultVO
     * @description:通过服务导入标识牌预置数据
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/10/30 0030 17:31
     */
    @PostMapping("/importPreBoardData")
    @SysLog("导入标识牌预置数据")
    public ResultVO importPreBoardData(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "请求数据data不能为空！");
        }
        //服务地址
        String url = (String) dataParam.get("url");
        if (StringUtils.isBlank(url)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "服务地址不能为空");
        }
        String remark = (String) dataParam.get("remark");

        service.importPreBoardData(url, remark);
        return ResultVOUtil.success();
    }

    /**
     * @param paramsMap
     * @return com.gistone.VO.ResultVO
     * @description:导入预置数据统一接口
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/10/30 0030 17:53
     */
    @PostMapping("/importPreData")
    @SysLog("导入预置数据")
    public ResultVO importPreData(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "请求数据data不能为空！");
        }
        //服务地址
        String url = (String) dataParam.get("url");
        if (StringUtils.isBlank(url)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "服务地址不能为空");
        }
        String remark = (String) dataParam.get("remark");

        Integer type = (Integer) dataParam.get("type");
        if (type == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "类型不能为空");
        }

        if (type == 1) {
            service.importPreRedlineDate(url, remark);
        } else if (type == 2) {
            service.importPreMarkerData(url, remark);
        }
        if (type == 3) {
            service.importPreBoardData(url, remark);
        } else if (type == 4) {

        } else if (type == 5) {
            service.importPreVector(url, remark);
        }
        if (type == 6) {
            service.importPreImage(url, remark);
        }

        return ResultVOUtil.success();
    }


}
