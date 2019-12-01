package com.gistone.controller;


import com.gistone.VO.ResultVO;
import com.gistone.entity.LmPoint;
import com.gistone.entity.Process;
import com.gistone.service.IProcessService;
import com.gistone.util.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zjw
 * @since 2019-11-27
 */
@RestController
@RequestMapping("/api/ktdb/process")
public class ProcessController {

    @Autowired
    private IProcessService service;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping("/list")
    public ResultVO getList(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "请求数据data不能为空！");
        }
        Integer pageNum = (Integer) params.get("pageNum");
        Integer pageSize = (Integer) params.get("pageSize");
        String name = (String) params.get("pName");
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
     * 添加流程
     * @param requestData
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Object save(@RequestBody Map<String, Object> requestData, HttpServletRequest request, HttpServletResponse response) {

        //请求参数格式校验
        Object dataParam = requestData.get("data");
        if (dataParam == null) {
            return Result.build(1333, "请求数据data不能为空！");
        }
        //根据token得到用户信息
        //String user = redisTemplate.opsForValue().get(requestData.get("accessToken"));

        //JSONObject jsStr = JSONObject.fromObject(user);

        Process param = new Process();

        Map<String, Object> data = (Map<String, Object>) requestData.get("data");
        //获取前端传递的参数
        //流程名称
        if (RegUtil.CheckParameter(data.get("pName"), null, null, false)) {
            param.setPName(data.get("pName").toString());
        }else{
            return Result.build(1333, "流程名称不能为空！");
        }
        //流程逻辑
        if (RegUtil.CheckParameter(data.get("pContent"), null, null, false)) {
            param.setPContent(data.get("pContent").toString());
        }else{
            return Result.build(1333, "流程名称不能为空！");
        }
        //流程制作人
        if (RegUtil.CheckParameter(data.get("pPersion"), null, null, false)) {
            param.setPPersion(data.get("pPersion").toString());
        }
        //流程制作单位
        if (RegUtil.CheckParameter(data.get("pUnit"), null, null, false)) {
            param.setPUnit(data.get("pUnit").toString());
        }
        //流程简介
        if (RegUtil.CheckParameter(data.get("pIntroduce"), null, null, false)) {
            param.setPIntroduce(data.get("pIntroduce").toString());
        }

       // Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(jsStr);
        //用户id
        //param.setPAddUid(Integer.parseInt(map.get("id").toString()));
        param.setPAddUid(1);
        param.setPAddTime(LocalDateTime.now());
        boolean res = service.save(param);

        return Result.build(0000, "流程添加成功");
    }

}
