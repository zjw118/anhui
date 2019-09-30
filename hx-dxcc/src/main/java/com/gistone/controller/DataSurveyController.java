package com.gistone.controller;


import com.alibaba.fastjson.JSON;
import com.gistone.entity.DataSurvey;
import com.gistone.service.IDataSurveyService;
import com.gistone.util.RegUtil;
import com.gistone.util.Result;
import com.gistone.util.ResultEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器  调查采集表
 * </p>
 *
 * @author xjc
 * @since 2019-03-01
 */
@RestController
@RequestMapping("/api/dcxx/dataSurvey")
public class DataSurveyController {

    @Autowired
    private IDataSurveyService iDataSurveyService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 根据token 获取所属红线块
     *
     * @param requestData
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "getRedLindList", method = RequestMethod.POST)
    public Object getRedLindList(@RequestBody Map<String, Object> requestData, HttpServletRequest request, HttpServletResponse response) {

        //根据token得到用户信息
        String user = redisTemplate.opsForValue().get(requestData.get("accessToken"));

        JSONObject jsStr = JSONObject.fromObject(user);
        //json串转化为map对象
        Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(jsStr);
        //用户id
        String id = map.get("id").toString();

        List<Map<String, Object>> resultMap = iDataSurveyService.getRedLindList(id);
        JSONObject jsonobj = new JSONObject();
        //处理了反斜杠问题
        JSONArray jarr = JSONArray.fromObject(resultMap);

        jsonobj.put("rows", jarr);

        if (resultMap != null && resultMap.size() > 0) {
            return Result.build(ResultEnum.SUCCESS, jsonobj);
        } else {
            return Result.build(ResultEnum.SUCCESS, "该用户没有红线斑块！！");
        }

    }

    /**
     * 保存调查信息
     *
     * @param requestData
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings({"unchecked"})
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Object save(@RequestBody Map<String, Object> requestData, HttpServletRequest request, HttpServletResponse response) {

        //请求参数格式校验
        Object dataParam = requestData.get("data");
        if (dataParam == null) {
            return Result.build(ResultEnum.PARAMETEREMPTY, "请求数据data不能为空！");
        }

        Map<String, Object> data = (Map<String, Object>) requestData.get("data");

        DataSurvey dataSurvey = new DataSurvey();
        //红线块id
        if (RegUtil.CheckParameter(data.get("lmSrldId"), null, null, false)) {
            dataSurvey.setUserId(Integer.parseInt(data.get("lmSrldId").toString()));
        }
        //调查点名称
        if (RegUtil.CheckParameter(data.get("surveyName"), null, null, false)) {
            dataSurvey.setSurveyName(data.get("surveyName").toString());
        }
        //调查点时间
        if (RegUtil.CheckParameter(data.get("surveyTime"), null, null, false)) {
            dataSurvey.setSurveyTime(data.get("surveyTime").toString());
        }
        //经度
        if (RegUtil.CheckParameter(data.get("surveyLon"), null, null, false)) {
            dataSurvey.setSurveyLon(data.get("surveyLon").toString());
        }
        //纬度
        if (RegUtil.CheckParameter(data.get("surveyLat"), null, null, false)) {
            dataSurvey.setSurveyLat(data.get("surveyLat").toString());
        }
        //调查点海拔
        if (RegUtil.CheckParameter(data.get("surveyHeight"), null, null, false)) {
            dataSurvey.setSurveyAltitude(data.get("surveyHeight").toString());
        }
        //调查点湿度
        if (RegUtil.CheckParameter(data.get("surveyTemperature"), null, null, false)) {
            dataSurvey.setSurveyTemperature(data.get("surveyTemperature").toString());
        }
        //图片标识
        if (RegUtil.CheckParameter(data.get("identification"), null, null, false)) {
            dataSurvey.setSurveyData(data.get("identification").toString());
        }

        //解析json
        Object dataArrParam = data.get("subSurveyData");
        String detailsArr = null;
        if (null != dataArrParam) {
            detailsArr = JSON.toJSONString(dataArrParam);
        }

        boolean b = iDataSurveyService.save(dataSurvey, detailsArr);
        if (b) {
            return Result.success("保存成功！！");
        } else {
            return Result.error("保存失败！！");
        }

    }

    /**
     * 调查点图片上传
     *
     * @param files
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public Object upload(@RequestParam(value = "files") MultipartFile[] files, HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> resultMap = iDataSurveyService.upload(files);

        return Result.build(resultMap.get("code").toString(), resultMap.get("msg").toString(), resultMap.get("data").toString());
    }

}

