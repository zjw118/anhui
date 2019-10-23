package com.gistone.controller;

import com.gistone.VO.ResultVO;
import com.gistone.VO.SurveyTableInfoVO;
import com.gistone.entity.DicIndexItem;
import com.gistone.service.IDicIndexItemService;
import com.gistone.util.RegUtil;
import com.gistone.util.Result;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器  调查采集字典表
 * </p>
 *
 * @author xjc
 * @since 2019-03-01
 */
@RestController
@RequestMapping("/api/dcxx/dicIndexItem")
@Slf4j
public class DicIndexItemController {

    @Autowired
    private IDicIndexItemService iDicIndexItemService;

    @Autowired
    private StringRedisTemplate redisTemplate;
    private Map<String, Object> paramsMap;

    /**
     * 手机端动态得到调查表字段
     *
     * @param requestData
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getAllList", method = RequestMethod.POST)
    public Object getAllList(@RequestBody Map<String, Object> requestData, HttpServletRequest request, HttpServletResponse response) {

        JSONObject jsonobj = new JSONObject();
        JSONArray jarr = new JSONArray();//处理了反斜杠问题

        String list = redisTemplate.opsForValue().get("allList");
        if (list != null && !"".equals(list)) {
            jarr = JSONArray.fromObject(list);
        } else {
            DicIndexItem dit = new DicIndexItem();
            List<Map<String, Object>> allList = iDicIndexItemService.getAllList(dit);
            jarr = JSONArray.fromObject(allList);
        }

        jsonobj.put("rows", jarr);

        return Result.ok(jsonobj);
    }

    /**
     * web端得到调查子表名称
     *
     * @param requestData
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "getSonTableList", method = RequestMethod.POST)
    public Object getSonTableList(@RequestBody Map<String, Object> requestData, HttpServletRequest request, HttpServletResponse response) {

        //请求参数格式校验
        Object dataParam = requestData.get("data");
        if (dataParam == null) {
            return Result.build(1333, "请求数据data不能为空！");
        }

        Map<String, Object> data = (Map<String, Object>) requestData.get("data");
        Map<String, Object> resultMap = new HashMap<>();
        //表名称
        if (RegUtil.CheckParameter(data.get("tableName"), null, null, false)) {
            resultMap.put("tableName", data.get("tableName").toString());
        }
        //每页条数
        if (RegUtil.CheckParameter(data.get("pageSize"), null, null, false)) {
            resultMap.put("pageSize", Integer.parseInt(data.get("pageSize").toString()));
        }
        //开始索引
        if (RegUtil.CheckParameter(data.get("pageNumber"), null, null, false)) {
            resultMap.put("pageNumber", Integer.parseInt(data.get("pageNumber").toString()));
        }

        Map<String, Object> list = iDicIndexItemService.getSonTableList(resultMap);

        return Result.ok(list);
    }

    /**
     * 保存子表信息
     *
     * @param requestData
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "saveSonTable", method = RequestMethod.POST)
    public Object saveSonTable(@RequestBody Map<String, Object> requestData, HttpServletRequest request, HttpServletResponse response) {

        //请求参数格式校验
        Object dataParam = requestData.get("data");
        if (dataParam == null) {
            return Result.build(1333, "请求数据data不能为空！");
        }

        Map<String, Object> data = (Map<String, Object>) requestData.get("data");
        Map<String, Object> resultMap = new HashMap<>();
        //子表名称
        if (RegUtil.CheckParameter(data.get("tableName"), null, null, false)) {
            resultMap.put("tableName", data.get("tableName"));
        }
        //子表说明
        if (RegUtil.CheckParameter(data.get("remark"), null, null, false)) {
            resultMap.put("remark", data.get("remark"));
        }

        boolean flag = iDicIndexItemService.saveSonTable(resultMap);
        if (flag) {
            return Result.build(0000, "保存成功！！");
        } else {
            return Result.build(9999, "保存失败！！");
        }
    }

    /**
     * 修改子表信息
     *
     * @param requestData
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "updateSonTable", method = RequestMethod.POST)
    public Object updateSonTable(@RequestBody Map<String, Object> requestData, HttpServletRequest request, HttpServletResponse response) {

        //请求参数格式校验
        Object dataParam = requestData.get("data");
        if (dataParam == null) {
            return Result.build(1333, "请求数据data不能为空！");
        }

        Map<String, Object> data = (Map<String, Object>) requestData.get("data");
        Map<String, Object> resultMap = new HashMap<>();
        //子表id
        if (RegUtil.CheckParameter(data.get("pkId"), null, null, false)) {
            resultMap.put("pkId", data.get("pkId"));
        }
        //子表名称
        if (RegUtil.CheckParameter(data.get("tableName"), null, null, false)) {
            resultMap.put("tableName", data.get("tableName"));
        }
        //子表说明
        if (RegUtil.CheckParameter(data.get("remark"), null, null, false)) {
            resultMap.put("remark", data.get("remark"));
        }

        boolean flag = iDicIndexItemService.updateSonTable(resultMap);
        if (flag) {
            return Result.build(0000, "修改成功！！");
        } else {
            return Result.build(9999, "修改失败！！");
        }
    }

    /**
     * 删除子表
     *
     * @param requestData
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "deleteSonTable", method = RequestMethod.POST)
    public Object deleteSonTable(@RequestBody Map<String, Object> requestData, HttpServletRequest request, HttpServletResponse response) {

        //请求参数格式校验
        Object dataParam = requestData.get("data");
        if (dataParam == null) {
            return Result.build(1333, "请求数据data不能为空！");
        }

        Map<String, Object> data = (Map<String, Object>) requestData.get("data");
        //主键
        String pkId = null;
        if (RegUtil.CheckParameter(data.get("pkId"), null, null, false)) {
            pkId = data.get("pkId").toString();
        }

        Map<String, Object> flag = iDicIndexItemService.deleteSonTable(pkId);

        return Result.build(0000, flag.get("msg").toString());
    }


    /**
     * 根据子表id查询该表字段信息
     *
     * @param requestData
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "getFiledList", method = RequestMethod.POST)
    public Object getFiledList(@RequestBody Map<String, Object> requestData, HttpServletRequest request, HttpServletResponse response) {

        //请求参数格式校验
        Object dataParam = requestData.get("data");
        if (dataParam == null) {
            return Result.build(1333, "请求数据data不能为空！");
        }

        Map<String, Object> data = (Map<String, Object>) requestData.get("data");
        Map<String, Object> resultMap = new HashMap<>();
        //子表id
        if (RegUtil.CheckParameter(data.get("indexItemId"), null, null, false)) {
            resultMap.put("indexItemId", data.get("indexItemId").toString());
        }
        //每页条数
        if (RegUtil.CheckParameter(data.get("pageSize"), null, null, false)) {
            resultMap.put("pageSize", Integer.parseInt(data.get("pageSize").toString()));
        }
        //开始索引
        if (RegUtil.CheckParameter(data.get("pageNumber"), null, null, false)) {
            resultMap.put("pageNumber", Integer.parseInt(data.get("pageNumber").toString()));
        }

        Map<String, Object> map = iDicIndexItemService.getFiledList(resultMap);

        return Result.ok(map);
    }

    /**
     * 保存字段信息
     *
     * @param requestData
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "saveFiled", method = RequestMethod.POST)
    public Object saveFiled(@RequestBody Map<String, Object> requestData, HttpServletRequest request, HttpServletResponse response) {

        //请求参数格式校验
        Object dataParam = requestData.get("data");
        if (dataParam == null) {
            return Result.build(1333, "请求数据data不能为空！");
        }
        Map<String, Object> data = (Map<String, Object>) requestData.get("data");
        Map<String, Object> resultMap = new HashMap<>();
        //子表主键
        Integer indexItemId = (Integer) data.get("indexItemId");

        if (indexItemId != null && indexItemId > 0) {
            resultMap.put("indexItemId", data.get("indexItemId"));
        }
        //字段名称
        if (RegUtil.CheckParameter(data.get("filedName"), null, null, false)) {
            resultMap.put("filedName", data.get("filedName"));
        }
        //字段类型
        if (RegUtil.CheckParameter(data.get("filedType"), null, null, false)) {
            resultMap.put("filedType", data.get("filedType"));
        }
        //是否必填
        if (RegUtil.CheckParameter(data.get("isMust"), null, null, false)) {
            resultMap.put("isMust", data.get("isMust"));
        }
        //最大值
        if (RegUtil.CheckParameter(data.get("max"), null, null, false)) {
            resultMap.put("max", data.get("max"));
        } else {
            resultMap.put("max", "");
        }
        //最小值
        if (RegUtil.CheckParameter(data.get("min"), null, null, false)) {
            resultMap.put("min", data.get("min"));
        } else {
            resultMap.put("min", "");
        }
        //浮点型，保留小数点位数
        if (RegUtil.CheckParameter(data.get("decimalPoint"), null, null, false)) {
            resultMap.put("decimalPoint", data.get("decimalPoint"));
        } else {
            resultMap.put("decimalPoint", "");
        }
        //字符串限制长度
        if (RegUtil.CheckParameter(data.get("stringLength"), null, null, false)) {
            resultMap.put("stringLength", data.get("stringLength"));
        } else {
            resultMap.put("stringLength", "");
        }
        //单选按钮值
        if (RegUtil.CheckParameter(data.get("rediaoType"), null, null, false)) {
            resultMap.put("rediaoType", data.get("rediaoType"));
        } else {
            resultMap.put("rediaoType", "");
        }
        //单选下拉框值
        if (RegUtil.CheckParameter(data.get("singleSelect"), null, null, false)) {
            resultMap.put("singleSelect", data.get("singleSelect"));
        } else {
            resultMap.put("singleSelect", "");
        }
        //多选下拉框值
        if (RegUtil.CheckParameter(data.get("multipleSelect"), null, null, false)) {
            resultMap.put("multipleSelect", data.get("multipleSelect"));
        } else {
            resultMap.put("multipleSelect", "");
        }
        //时间类型（yyyy-mm-dd，yyyy-mm-dd HH:MM:ss）
        if (RegUtil.CheckParameter(data.get("timeLimit"), null, null, false)) {
            resultMap.put("timeLimit", data.get("timeLimit"));
        } else {
            resultMap.put("timeLimit", "");
        }

        //返回插入数据的id

        Map<String, Object> map = iDicIndexItemService.saveFiled(resultMap);


        return Result.build(0000, map.get("msg").toString());
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @Transactional
    public ResultVO save(@RequestBody Map<String, Object> paramsMap) {

        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        List<Map<String, Object>> updateParam = new ArrayList<>();

        try {
            List<Map<String, Object>> mapList = (List<Map<String, Object>>) dataParam.get("add");
            if (mapList != null && mapList.size() > 0) {
                for (Map<String, Object> data : mapList) {
                    Map<String, Object> resultMap = new HashMap<>();
                    //子表主键
                    Integer indexItemId = (Integer) data.get("indexItemId");

                    if (indexItemId != null && indexItemId > 0) {
                        resultMap.put("indexItemId", data.get("indexItemId"));
                    }

                    Integer sort = (Integer) data.get("sort");
                    if (sort != null && sort > 0) {
                        resultMap.put("sort", sort);
                    }
                    //字段名称
                    if (RegUtil.CheckParameter(data.get("filedName"), null, null, false)) {
                        resultMap.put("filedName", data.get("filedName"));
                    }
                    //字段类型
                    if (RegUtil.CheckParameter(data.get("filedType"), null, null, false)) {
                        resultMap.put("filedType", data.get("filedType"));
                    }
                    //是否必填
                    if (RegUtil.CheckParameter(data.get("isMust"), null, null, false)) {
                        resultMap.put("isMust", data.get("isMust"));
                    }
                    //最大值
                    if (RegUtil.CheckParameter(data.get("max"), null, null, false)) {
                        resultMap.put("max", data.get("max"));
                    } else {
                        resultMap.put("max", "");
                    }
                    //最小值
                    if (RegUtil.CheckParameter(data.get("min"), null, null, false)) {
                        resultMap.put("min", data.get("min"));
                    } else {
                        resultMap.put("min", "");
                    }
                    //浮点型，保留小数点位数
                    if (RegUtil.CheckParameter(data.get("decimalPoint"), null, null, false)) {
                        resultMap.put("decimalPoint", data.get("decimalPoint"));
                    } else {
                        resultMap.put("decimalPoint", "");
                    }
                    //字符串限制长度
                    if (RegUtil.CheckParameter(data.get("stringLength"), null, null, false)) {
                        resultMap.put("stringLength", data.get("stringLength"));
                    } else {
                        resultMap.put("stringLength", "");
                    }
                    //单选按钮值
                    if (RegUtil.CheckParameter(data.get("rediaoType"), null, null, false)) {
                        resultMap.put("rediaoType", data.get("rediaoType"));
                    } else {
                        resultMap.put("rediaoType", "");
                    }
                    //单选下拉框值
                    if (RegUtil.CheckParameter(data.get("singleSelect"), null, null, false)) {
                        resultMap.put("singleSelect", data.get("singleSelect"));
                    } else {
                        resultMap.put("singleSelect", "");
                    }
                    //多选下拉框值
                    if (RegUtil.CheckParameter(data.get("multipleSelect"), null, null, false)) {
                        resultMap.put("multipleSelect", data.get("multipleSelect"));
                    } else {
                        resultMap.put("multipleSelect", "");
                    }
                    //时间类型（yyyy-mm-dd，yyyy-mm-dd HH:MM:ss）
                    if (RegUtil.CheckParameter(data.get("timeLimit"), null, null, false)) {
                        resultMap.put("timeLimit", data.get("timeLimit"));
                    } else {
                        resultMap.put("timeLimit", "");
                    }

                    //返回插入数据的id
                    Map<String, Object> map = iDicIndexItemService.saveFiled(resultMap);

                    Map<String, Object> map1 = new HashMap<>();
                    Integer sort1 = (Integer) data.get("sort");
                    if (sort1 != null && sort1 > 0) {
                        map1.put("id", map.get("msg"));
                        map1.put("sort", sort1);
                    }
                    updateParam.add(map1);

                }
            }


            List<Map<String, Object>> updateData = (List<Map<String, Object>>) dataParam.get("update");
            //合并集合

            if (updateData != null && updateData.size() > 0) {
                updateData.addAll(updateParam);

                boolean result = iDicIndexItemService.editSort(updateData);

                if (!result) {
                    return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "修改字段排序失败");
                }

            }


            //获取删除参数，执行删除
            List<Integer> deleteParam = (List<Integer>) dataParam.get("delete");
            if (dataParam != null && dataParam.size() > 0) {

                for (Integer id : deleteParam) {
                    Boolean result = iDicIndexItemService.deleteFileds(id);
                    if (!result) {
                        return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "删除失败");
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("保存调查表字段修改信息失败，异常信息为：{}", e.getMessage());
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "保存调查表字段修改信息失败");

        }

        return ResultVOUtil.success();

    }

    /**
     * 修改字段信息
     *
     * @param requestData
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "updateFiled", method = RequestMethod.POST)
    public Object updateFiled(@RequestBody Map<String, Object> requestData, HttpServletRequest request, HttpServletResponse response) {

        //请求参数格式校验
        Object dataParam = requestData.get("data");
        if (dataParam == null) {
            return Result.build(1333, "请求数据data不能为空！");
        }

        Map<String, Object> data = (Map<String, Object>) requestData.get("data");
        Map<String, Object> resultMap = new HashMap<>();
        //字段id
        if (RegUtil.CheckParameter(data.get("pkId"), null, null, false)) {
            resultMap.put("pkId", data.get("pkId").toString());
        }
        //子表主键
        if (RegUtil.CheckParameter(data.get("indexItemId"), null, null, false)) {
            resultMap.put("indexItemId", data.get("indexItemId"));
        }
        //字段名称
        if (RegUtil.CheckParameter(data.get("filedName"), null, null, false)) {
            resultMap.put("filedName", data.get("filedName"));
        }
        //字段类型
        if (RegUtil.CheckParameter(data.get("filedType"), null, null, false)) {
            resultMap.put("filedType", data.get("filedType"));
        }
        //是否必填
        if (RegUtil.CheckParameter(data.get("isMust"), null, null, false)) {
            resultMap.put("isMust", data.get("isMust"));
        }
        //最大值
        if (RegUtil.CheckParameter(data.get("max"), null, null, false)) {
            resultMap.put("max", data.get("max"));
        } else {
            resultMap.put("max", "");
        }
        //最小值
        if (RegUtil.CheckParameter(data.get("min"), null, null, false)) {
            resultMap.put("min", data.get("min"));
        } else {
            resultMap.put("min", "");
        }
        //浮点型，保留小数点位数
        if (RegUtil.CheckParameter(data.get("decimalPoint"), null, null, false)) {
            resultMap.put("decimalPoint", data.get("decimalPoint"));
        } else {
            resultMap.put("decimalPoint", "");
        }
        //字符串限制长度
        if (RegUtil.CheckParameter(data.get("stringLength"), null, null, false)) {
            resultMap.put("stringLength", data.get("stringLength"));
        } else {
            resultMap.put("stringLength", "");
        }
        //单选按钮值
        if (RegUtil.CheckParameter(data.get("rediaoType"), null, null, false)) {
            resultMap.put("rediaoType", data.get("rediaoType"));
        } else {
            resultMap.put("rediaoType", "");
        }
        //单选下拉框值
        if (RegUtil.CheckParameter(data.get("singleSelect"), null, null, false)) {
            resultMap.put("singleSelect", data.get("singleSelect"));
        } else {
            resultMap.put("singleSelect", "");
        }
        //多选下拉框值
        if (RegUtil.CheckParameter(data.get("multipleSelect"), null, null, false)) {
            resultMap.put("multipleSelect", data.get("multipleSelect"));
        } else {
            resultMap.put("multipleSelect", "");
        }
        //时间类型（yyyy-mm-dd，yyyy-mm-dd HH:MM:ss）
        if (RegUtil.CheckParameter(data.get("timeLimit"), null, null, false)) {
            resultMap.put("timeLimit", data.get("timeLimit"));
        } else {
            resultMap.put("timeLimit", "");
        }

        boolean flag = iDicIndexItemService.updateFiled(resultMap);
        if (flag) {
            return Result.build(0000, "保存成功！！");
        } else {
            return Result.build(9999, "保存失败！！");
        }

    }

    /**
     * 删除子表字段
     *
     * @param requestData
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "deleteFiled", method = RequestMethod.POST)
    public Object deleteFiled(@RequestBody Map<String, Object> requestData, HttpServletRequest request, HttpServletResponse response) {

        //请求参数格式校验
        Object dataParam = requestData.get("data");
        if (dataParam == null) {
            return Result.build(1333, "请求数据data不能为空！");
        }

        Map<String, Object> data = (Map<String, Object>) requestData.get("data");
        //主键
        String pkId = null;
        if (RegUtil.CheckParameter(data.get("pkId"), null, null, false)) {
            pkId = data.get("pkId").toString();
        }

        boolean b = iDicIndexItemService.deleteFiled(pkId);
        if (b) {
            return Result.build(0000, "删除成功！！");
        } else {
            return Result.build(9999, "删除失败！！");
        }
    }


    /**
     * 获取调查表列表
     *
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/getSurveyTableList", method = RequestMethod.POST)
    public ResultVO getSurveyTableList(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        String redlineName = (String) dataParam.get("redlineName");
        String tableName = (String) dataParam.get("tableName");

        Integer pageNum = (Integer) dataParam.get("pageNum");
        Integer pageSize = (Integer) dataParam.get("pageSize");

        if (pageNum == null) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> list = iDicIndexItemService.getSurveyTableList(redlineName, tableName, pageNum, pageSize);
        int total = iDicIndexItemService.getTotal(redlineName, tableName);
        result.put("rows", list);
        result.put("total", total);

        return ResultVOUtil.success(result);
    }

    /**
     * 获取单个调查表详情
     *
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/getTableInfo", method = RequestMethod.POST)
    public ResultVO getTableInfo(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        Integer id = (Integer) dataParam.get("id");
        if (id == null || id < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        Map<String, Object> result = iDicIndexItemService.getTableInfo(id);

        return ResultVOUtil.success(result);
    }

    /**
     * 获取表字段值信息
     *
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/getFileds", method = RequestMethod.POST)
    public ResultVO getFileds(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        Integer id = (Integer) dataParam.get("id");
        if (id == null || id < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        List<SurveyTableInfoVO> result = iDicIndexItemService.getFileds(id);

        return ResultVOUtil.success(result);
    }

    /**
     * 删除表字段
     *
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/deleteFileds", method = RequestMethod.POST)
    public ResultVO deleteFiled(@RequestBody Map<String, Object> paramsMap) {
        this.paramsMap = paramsMap;
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        Integer id = (Integer) dataParam.get("id");
        if (id == null || id < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }

        Boolean result = iDicIndexItemService.deleteFileds(id);
        if (!result) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "删除失败");
        }

        return ResultVOUtil.success();
    }

    /**
     * 编辑排序值
     *
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/editSort", method = RequestMethod.POST)
    public ResultVO editSort(@RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        List<Map<String, Object>> list = (List<Map<String, Object>>) dataParam.get("param");
        if (list == null || list.size() <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "参数不能为空");
        }
        boolean result = iDicIndexItemService.editSort(list);

        if (!result) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "修改字段排序失败");
        }

        return ResultVOUtil.success();

    }


}

