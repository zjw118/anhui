package com.gistone.controller;


import com.gistone.VO.ResultVO;
import com.gistone.entity.EXCEL.LmPointVO;
import com.gistone.entity.LmPoint;
import com.gistone.service.ILmPointService;
import com.gistone.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器 拐点管理
 * </p>
 *
 * @author xjc
 * @since 2019-02-28
 */
@RestController
@RequestMapping("/api/ktdb/lmPoint")
public class LmPointController {

    @Autowired
    private ILmPointService iLmPointService;

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ConfigUtils configUtils;

    /**
     * 获取拐点列表
     *
     * @param requestData
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "selectPointList", method = RequestMethod.POST)
    public Object selectLandmarklist(@RequestBody Map<String, Object> requestData, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //请求参数格式校验
        Object dataParam = requestData.get("data");
        if (dataParam == null) {
            return Result.build(1333, "请求数据data不能为空！");
        }

        Map<String, Object> data = (Map<String, Object>) requestData.get("data");
        Map<String, Object> resultMap = new HashMap<>();
        //获取前端传递的参数
        //红线台账名称
        if (RegUtil.CheckParameter(data.get("name"), null, null, false)) {
            resultMap.put("name", data.get("name"));
        }
        //红线台账编码
        if (RegUtil.CheckParameter(data.get("srldcode"), null, null, false)) {
            resultMap.put("srldcode", data.get("srldcode"));
        }
        //每页条数
        if (RegUtil.CheckParameter(data.get("pageSize"), null, null, false)) {
            resultMap.put("pageSize", Integer.parseInt(data.get("pageSize").toString()));
        }
        //开始索引
        if (RegUtil.CheckParameter(data.get("pageNumber"), null, null, false)) {
            resultMap.put("pageNumber", Integer.parseInt(data.get("pageNumber").toString()));
        }

        Map<String, Object> map = iLmPointService.selectLmPointList(resultMap);

        return Result.ok(map);
    }

    @PostMapping("/list")
    public ResultVO getPointList(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        String boardNum = (String) dataParam.get("pointNum");
        Integer pageNum = (Integer) dataParam.get("pageNum");
        Integer pageSize = (Integer) dataParam.get("pageSize");


        if (pageNum == null) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }

        Map<String, Object> result = iLmPointService.getPointList(boardNum, pageNum, pageSize);
        return ResultVOUtil.success(result);

    }

    /**
     * 拐点数据保存
     *
     * @param requestData
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Object save(@RequestBody Map<String, Object> requestData, HttpServletRequest request, HttpServletResponse response) {

        //请求参数格式校验
        Object dataParam = requestData.get("data");
        if (dataParam == null) {
            return Result.build(1333, "请求数据data不能为空！");
        }
        //根据token得到用户信息
        String user = redisTemplate.opsForValue().get(requestData.get("accessToken"));

        JSONObject jsStr = JSONObject.fromObject(user);

        LmPoint lp = new LmPoint();

        Map<String, Object> data = (Map<String, Object>) requestData.get("data");
        //获取前端传递的参数
        //编号
        if (RegUtil.CheckParameter(data.get("lpCode"), null, null, false)) {
            lp.setLpCode(data.get("lpCode").toString());
        }
        //红线台账id
        if (RegUtil.CheckParameter(data.get("lpSrldId"), null, null, false)) {
            lp.setLpSrldId(Integer.parseInt(data.get("lpSrldId").toString()));
        }
        //经度
        if (RegUtil.CheckParameter(data.get("lplon"), null, null, false)) {
            lp.setLpLon(Double.parseDouble(data.get("lplon").toString()));
        }
        //经度
        if (RegUtil.CheckParameter(data.get("lplat"), null, null, false)) {
            lp.setLpLat(Double.parseDouble(data.get("lplat").toString()));
        }
        //平面坐标x
        if (RegUtil.CheckParameter(data.get("lpx"), null, null, false)) {
            lp.setLpX(Double.parseDouble(data.get("lpx").toString()));
        }
        //平面坐标y
        if (RegUtil.CheckParameter(data.get("lpy"), null, null, false)) {
            lp.setLpY(Double.parseDouble(data.get("lpy").toString()));
        }
        //采集时间
        if (RegUtil.CheckParameter(data.get("lpgetTime"), null, null, false)) {
            lp.setLpGettime(DateUtils.StrtoDateYMD(data.get("lpgetTime").toString()));
        }

        Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(jsStr);
        //新增时间
        lp.setLpAddTime(new Date());
        //新增用户id
        lp.setLpAddUid(Integer.parseInt(map.get("id").toString()));
        lp.setLpIdDel("0");

        Map<String, Object> resultMap = iLmPointService.insertLmPointInfor(lp);

        return Result.build(0000, resultMap.get("msg").toString());
    }

    /**
     * 拐点数据修改
     *
     * @param requestData
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Object update(@RequestBody Map<String, Object> requestData, HttpServletRequest request, HttpServletResponse response) {

        //请求参数格式校验
        Object dataParam = requestData.get("data");
        if (dataParam == null) {
            return Result.build(1333, "请求数据data不能为空！");
        }
        //根据token得到用户信息
        String user = redisTemplate.opsForValue().get(requestData.get("accessToken"));

        JSONObject jsStr = JSONObject.fromObject(user);

        LmPoint lp = new LmPoint();

        Map<String, Object> data = (Map<String, Object>) requestData.get("data");
        //获取前端传递的参数
        //主键
        if (RegUtil.CheckParameter(data.get("lpId"), null, null, false)) {
            lp.setLpId(Integer.parseInt(data.get("lpId").toString()));
        }
        //编号
        if (RegUtil.CheckParameter(data.get("lpCode"), null, null, false)) {
            lp.setLpCode(data.get("lpCode").toString());
        }
        //红线台账id
        if (RegUtil.CheckParameter(data.get("lpSrldId"), null, null, false)) {
            lp.setLpSrldId(Integer.parseInt(data.get("lpSrldId").toString()));
        }
        //经度
        if (RegUtil.CheckParameter(data.get("lplon"), null, null, false)) {
            lp.setLpLon(Double.parseDouble(data.get("lplon").toString()));
        }
        //经度
        if (RegUtil.CheckParameter(data.get("lplat"), null, null, false)) {
            lp.setLpLat(Double.parseDouble(data.get("lplat").toString()));
        }
        //平面坐标x
        if (RegUtil.CheckParameter(data.get("lpx"), null, null, false)) {
            lp.setLpX(Double.parseDouble(data.get("lpx").toString()));
        }
        //平面坐标y
        if (RegUtil.CheckParameter(data.get("lpy"), null, null, false)) {
            lp.setLpY(Double.parseDouble(data.get("lpy").toString()));
        }
        //采集时间
        if (RegUtil.CheckParameter(data.get("lpgetTime"), null, null, false)) {
            lp.setLpGettime(DateUtils.StrtoDateYMD(data.get("lpgetTime").toString()));
        }

        Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(jsStr);
        //修改时间
        lp.setLpUpdTime(new Date());
        //修改用户id
        lp.setLpUpdUid(Integer.parseInt(map.get("id").toString()));

        Map<String, Object> resultMap = iLmPointService.updateLmPointInfor(lp);

        return Result.build(0000, resultMap.get("msg").toString());
    }

    /**
     * 删除拐点信息
     *
     * @param requestData
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Object delete(@RequestBody Map<String, Object> requestData, HttpServletRequest request, HttpServletResponse response) {

        //请求参数格式校验
        Object dataParam = requestData.get("data");
        if (dataParam == null) {
            return Result.build(1333, "请求数据data不能为空！");
        }

        Map<String, Object> data = (Map<String, Object>) requestData.get("data");
        //获取前端传递的参数
        //主键
        String lpId = null;
        if (RegUtil.CheckParameter(data.get("lpId"), null, null, false)) {
            lpId = data.get("lpId").toString();
        }

        boolean b = iLmPointService.deleteLmPointInfor(lpId);
        if (b) {
            return Result.build(0000, "删除成功！！");
        } else {
            return Result.build(9999, "删除失败！！");
        }

    }

    /**
     * 拐点数据导出
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "exportLmPointInfor", method = RequestMethod.POST)
    public Object exportLmPointInfor(@RequestBody Map<String, Object> requestData, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //请求参数格式校验
        Object dataParam = requestData.get("data");
        if (dataParam == null) {
            return Result.build(1333, "请求数据data不能为空！");
        }
        Map<String, Object> data = (Map<String, Object>) requestData.get("data");
        Map<String, Object> resultMap = new HashMap<>();
        //红线台账名称
        if (RegUtil.CheckParameter(data.get("name"), null, null, false)) {
            resultMap.put("name", data.get("name"));
        }
        //红线台账编码
        if (RegUtil.CheckParameter(data.get("srldcode"), null, null, false)) {
            resultMap.put("srldcode", data.get("srldcode"));
        }

        String path = iLmPointService.exportLmPointInfor(resultMap, request, response);

        JSONObject dataObj = new JSONObject();
        dataObj.put("filePath", path);

        return Result.ok(dataObj);
    }

    /**
     * 根据红线id查询拐点数据
     *
     * @param requestData
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "getLmPointBysrld", method = RequestMethod.POST)
    public Object getLmPointBysrld(@RequestBody Map<String, Object> requestData, HttpServletRequest request, HttpServletResponse response) {

        //请求参数格式校验
        Object dataParam = requestData.get("data");
        if (dataParam == null) {
            return Result.build(1333, "请求数据data不能为空！");
        }
        Map<String, Object> data = (Map<String, Object>) requestData.get("data");
        Map<String, Object> resultMap = new HashMap<>();
        //红线台账名称
        if (RegUtil.CheckParameter(data.get("lpSrldId"), null, null, false)) {
            resultMap.put("lpSrldId", data.get("lpSrldId"));
        }

        Map<String, Object> map = iLmPointService.getLmPointBysrld(resultMap);

        return Result.ok(map);
    }

    /**
     * 查询拐点集合
     *
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/getPointList", method = RequestMethod.POST)
    public ResultVO getPointListByCode(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        Integer pageNum = (Integer) dataParam.get("pageNum");
        Integer pageSize = (Integer) dataParam.get("pageSize");

        String code = (String) dataParam.get("code");
        String param = (String) dataParam.get("param");
        Integer redlineId = (Integer) dataParam.get("redlineId");

//        Integer level = (Integer) dataParam.get("level");
        String codes = null;
        if (StringUtils.isNotBlank(code)) {
            Integer level = iLmPointService.getLevelByCode(code);
            if (level != null && level > 0) {
                if (level == 1) {
                    codes = code.substring(0, 2);
                } else if (level == 2) {
                    codes = code.substring(0, 4);
                } else {
                    codes = code;
                }
            } else {
                codes = code;
            }
        }

        if (pageNum == null) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }

        List<LmPoint> lmPointList = iLmPointService.selectPointList(codes, param, redlineId, pageNum, pageSize);

        int total = iLmPointService.selectTotal(codes, param, redlineId);

        Map<String, Object> result = new HashMap<>();
        result.put("rows", lmPointList);
        result.put("total", total);

        return ResultVOUtil.success(result);

    }

    /**
     * @param
     * @return
     * @description: 获取所有拐点
     * @author zf1017@foxmail.com
     * @date 2019/5/16 0016 16:30
     */
    @RequestMapping(value = "/getAllPoint")
    public ResultVO getAllPoint() {
        List<LmPoint> lmPoints = iLmPointService.list(null);
        return ResultVOUtil.success(lmPoints);

    }


    @RequestMapping(value = "/export_Excel")
    public ResultVO export_Excel(@RequestBody Map<String, Object> paramsMap, HttpServletResponse response) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");

        String code = (String) dataParam.get("code");
        String param = (String) dataParam.get("param");
        Integer redlineId = (Integer) dataParam.get("redlineId");

        String codes = null;
        if (StringUtils.isNotBlank(code)) {
            Integer level = iLmPointService.getLevelByCode(code);
            if (level != null && level > 0) {
                if (level == 1) {
                    codes = code.substring(0, 2);
                } else if (level == 2) {
                    codes = code.substring(0, 4);
                } else {
                    codes = code;
                }
            } else {
                codes = code;
            }
        }
        List<LmPointVO> lmPointVOList = iLmPointService.selectPointListForAll(codes, param, redlineId);
        String filepath = ExcelUtil.toXls("拐点坐标", lmPointVOList, configUtils.getExcel_PATH(), LmPointVO.class, response);
        Map map1 = new HashMap();
        map1.put("filepath", filepath.substring(2));
        return ResultVOUtil.success(map1);
    }

    /**
     * @param paramsMap
     * @return com.gistone.VO.ResultVO
     * @description:拐点编辑
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/11/14 0014 20:00
     */
    @PostMapping("/updatePoint")
    public ResultVO updatePoint(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        LmPoint lmPoint = new LmPoint();
        Integer id = (Integer) dataParam.get("id");
        lmPoint.setLpId(id);
        if (id == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        String pointNum = (String) dataParam.get("pointNum");
        if (StringUtils.isNotBlank(pointNum)) {
            lmPoint.setLpCode(pointNum);
        }
        iLmPointService.updateById(lmPoint);
        return ResultVOUtil.success();
    }
}

