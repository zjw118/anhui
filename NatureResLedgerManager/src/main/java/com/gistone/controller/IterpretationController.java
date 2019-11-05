package com.gistone.controller;


import com.gistone.VO.ResultVO;
import com.gistone.entity.St4ScsCd;
import com.gistone.entity.SysUser;
import com.gistone.service.ISt4ScsCdService;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 人类活动解译信息表 前端控制器
 * </p>
 *
 * @author zf1017@foxmail.com
 * @version v1.0
 * @since 2019-10-18
 */

@RestController
@RequestMapping("/api/ygjc/iterpretation")
public class IterpretationController {
    @Autowired
    private ISt4ScsCdService service;

    //无分页
    @PostMapping("/list")
    public ResultVO getList(@RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        Integer id = (Integer) params.get("id");
        return ResultVOUtil.success(service.list2(id));
    }
    //有分页
    @PostMapping("/list2")
    public ResultVO getList2(@RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        if(null==params.get("pageNum"))return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"pageNum不能为空");
        if(null==params.get("pageSize"))return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"pageSize不能为空");
        Integer id = (Integer) params.get("id");
        Map<String, Object> result = service.list(Integer.valueOf(params.get("pageNum")+""), Integer.valueOf(params.get("pageSize")+""),id);
        return ResultVOUtil.success(result);
    }


    @PostMapping("/detail")
    public ResultVO getById(@RequestBody Map<String, Object> params) {
        Integer id = (Integer) params.get("id");
        if (id == null || id < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        St4ScsCd entity = service.getById(id);
        return ResultVOUtil.success(entity);
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultVO add(@RequestBody Map<String, Object> paramsMap, HttpServletRequest request) {
        //请求参数格式校验
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        List<Map<String, Object>> data = (List<Map<String, Object>>) params.get("jsondata");
        if (data == null || data.size() <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "解译数据不能为空");
        }
        Integer imageId = (Integer) params.get("imageId");
        if (imageId == null || imageId <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "影像id不能为空");
        }
        Integer createBy = (Integer) params.get("createBy");
        if(createBy==null||createBy<=0){
            HttpSession session = request.getSession();
            SysUser user = (SysUser) session.getAttribute("user");
            if(user!=null){
                createBy = user.getId();
            }

        }
        //判断添加人是否为空
        service.insert(data,imageId,createBy);
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
    public ResultVO update(@RequestBody St4ScsCd entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        //判断更新人加人是否为空
        service.edit(entity);
        return ResultVOUtil.success();
    }






}
