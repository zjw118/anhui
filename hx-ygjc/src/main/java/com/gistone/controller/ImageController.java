package com.gistone.controller;


import com.gistone.VO.ResultVO;
import com.gistone.entity.Image;
import com.gistone.mapper.ImageMapper;
import com.gistone.service.ILmPointService;
import com.gistone.service.ImageService;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 影像数据表 前端控制器
 * </p>
 *
 * @author zf1017@foxmail.com
 * @version v1.0
 * @since 2019-10-18
 */


@RestController
@RequestMapping("/api/ygjc/image")
public class ImageController {
    @Autowired
    private ImageService service;
    @Autowired
    private ImageMapper mapper;
    @Value("${PATH}")
    private String PATH;
    @Autowired
    private ILmPointService iLmPointService;

    @PostMapping("/list")
    public ResultVO getList(@RequestBody Map<String, Object> paramsMap) {
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
    public ResultVO getById(@RequestBody Map<String, Object> params) {
        Integer id = (Integer) params.get("id");
        if (id == null || id < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        Image entity = service.getById(id);
        entity.setList(mapper.selectISt4ScsCd(id));
        return ResultVOUtil.success(entity);
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
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "名称不能为空");
        }

        String url = (String) params.get("url");
        if (StringUtils.isBlank(url)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "地址不能为空");
        }

        String remark = (String) params.get("remark");

        //判断添加人是否为空
        Integer createBy = (Integer) params.get("createBy");
        if (createBy == null || createBy <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "创建人不能为空");
        }

        service.insert(name, url, createBy, remark);
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
        if (id == null || id.size() < 0) {
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
        if (id == null || id <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "记录id不能为空");
        }

        String name = (String) params.get("name");
        if (StringUtils.isBlank(name)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "名称不能为空");
        }

        String url = (String) params.get("url");
        if (StringUtils.isBlank(url)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "地址不能为空");
        }

        Integer updateBy = (Integer) params.get("updateBy");
        if (updateBy == null || updateBy <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "更新人不能为空");
        }
        String remark = (String) params.get("remark");

        //判断更新人加人是否为空
        service.edit(id, name, url, updateBy, remark);
        return ResultVOUtil.success();
    }

    /**
     * @param
     * @return com.gistone.VO.ResultVO
     * @description:人类活动解译按类型统计面积
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/11/6 0006 10:23
     */
    @PostMapping("/getRlhdTotal")
    public ResultVO getRlhdTotal() {
        List<Map<String, Object>> result = service.getRlhdTotal();
        return ResultVOUtil.success(result);
    }

    /**
     * @param paramsMap
     * @return com.gistone.VO.ResultVO
     * @description:核查点数量统计
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/11/6 0006 9:54
     */
    @PostMapping("/getPointTotal")
    public ResultVO getPointTotal(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        //获取当前日期和前十五天日期
        LocalDate currentTime = LocalDate.now();
        LocalDate beforeTime = currentTime.minusDays(14);


        String code = (String) dataParam.get("code");
        /*if (StringUtils.isBlank(code)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "行政区划号不能为空");
        }*/

        String codes = null;
        if (StringUtils.isNotBlank(code)) {
            Integer level = iLmPointService.getLevelByCode(code);
            if (level != null && level > 0) {
                if (level == 1 && code.length() > 3) {
                    codes = code.substring(0, 2);
                } else if (level == 2 && code.length() > 5) {
                    codes = code.substring(0, 4);
                } else {
                    codes = code;
                }
            } else {
                codes = code;
            }
        }
        Map<String, Object> result = new HashMap<>();
        //获取
        //获取界桩统计
        List<Map<String, Object>> markerList = service.getCount(codes, currentTime, beforeTime);
        int count = service.getBeforeCount(codes, beforeTime);

        //获取调查表统计
//        List<Map<String, Object>> surveyList = totalService.getSurveyCount(codes, currentTime, beforeTime);
        result.put("markerCount", markerList);
        result.put("beforeMarkerCount", count);

        return ResultVOUtil.success(result);
    }
    /**
     * 审核详情页
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/getAudit", method = RequestMethod.POST)
    public ResultVO getAudit(@RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        String id = (String) params.get("id");
        if (StringUtils.isBlank(id)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "影像主键id不能为空");
        }
        return service.getAudit(Integer.valueOf(id));
    }
    /**
     * 开始审核
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public ResultVO audit(@RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        Image image = new Image();
        String id = (String) params.get("id");
        if (StringUtils.isBlank(id)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "影像主键id不能为空");
        }
        if(null!=params.get("evaluation")){
            image.setEvaluation(params.get("evaluation")+"");
        }
        if(null!=params.get("sign")){
            image.setSign(Integer.valueOf(params.get("sign")+""));
        }
        image.setId(Integer.valueOf(id));
        image.setAuditDate(new Date());
        return service.audit(image);
    }


    /**
     * 导入影像
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResultVO upload(@RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");




        return null;
    }





}
