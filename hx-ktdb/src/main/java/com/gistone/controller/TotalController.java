package com.gistone.controller;

import com.gistone.VO.ResultVO;
import com.gistone.service.ILmPointService;
import com.gistone.service.TotalService;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据总汇控制器
 */
@RestController
@RequestMapping("/api/ktdb/total")
@Slf4j
public class TotalController {

    @Autowired
    private TotalService totalService;

    @Autowired
    private ILmPointService iLmPointService;


    /**
     * @return
     * @description: 获取行政区划下的红线区的总面积数
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/4/29 0029 9:12
     */
    @RequestMapping(value = "/getRedlineSumArea", method = RequestMethod.POST)
    public ResultVO getRedlineSumArea(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        String code = (String) dataParam.get("code");

        if (StringUtils.isBlank(code)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "行政区划编码不能为空");
        }

        Double area = totalService.getRedlineSumArea(code);

        return ResultVOUtil.success(area);

    }

    /**
     * @return
     * @description: 查询统计总数
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/4/25 0025 14:35
     */
    @RequestMapping(value = "/getTotal", method = RequestMethod.POST)
    public ResultVO getBoardTotal(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "请求数据data不能为空！");
        }

        String code = (String) dataParam.get("code");

        if (StringUtils.isBlank(code)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "行政区划编码不能为空");
        }
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

        ResultVO result = totalService.getTotal(codes);

        return result;
    }

    /**
     * @return
     * @description: 各地区红线斑块面积统计
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/4/28 0028 16:35
     */
    @PostMapping(value = "/getAreas")
    public ResultVO getAreas(@RequestBody Map<String, Object> paramsMap) {

        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        String code = (String) dataParam.get("code");
        if (StringUtils.isBlank(code)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "行政区划号不能为空");
        }
        Integer level = iLmPointService.getLevelByCode(code);
        //判断code是哪个级别，分别传递三个参数，级别，
        /*Integer sub = 0;
        Integer levels = 0;
        String codes = "";
        if (level == 1) {
            sub = 4;
            levels = 2;
            if (code.length() > 3) {
                codes = code.substring(0, 2);
            }
        } else if (level == 2) {

            sub = 6;
            levels = 3;
            if (code.length() > 5) {
                codes = code.substring(0, 4);
            }
        }*/


        List<Map<String, Object>> result = totalService.getAreas(code, level);

        return ResultVOUtil.success(result);

    }

    /**
     * @return
     * @description: 界桩和调查表按时间统计
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/4/28 0028 16:35
     */
    @PostMapping(value = "/getCount")
    public ResultVO getCount(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        //获取当前日期和前十五天日期
        LocalDate currentTime = LocalDate.now();
        LocalDate beforeTime = currentTime.minusDays(14);


        String code = (String) dataParam.get("code");
        if (StringUtils.isBlank(code)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "行政区划号不能为空");
        }

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
        List<Map<String, Object>> markerList = totalService.getCount(codes, currentTime, beforeTime);
        //获取标识牌统计
        List<Map<String, Object>> boardList = totalService.getBoardCount(codes, currentTime, beforeTime);

        int count = totalService.getBeforeCount(codes, beforeTime);
        //查詢节点之前的数据
        int sum = totalService.getBeforeSum(codes, beforeTime);

        //获取调查表统计
//        List<Map<String, Object>> surveyList = totalService.getSurveyCount(codes, currentTime, beforeTime);
        result.put("markerCount", markerList);
        result.put("beforeMarkerCount", sum);
        result.put("boardCount", boardList);
        result.put("beforeBoardCount", count);
//        result.put("surveyCount", surveyList);
        return ResultVOUtil.success(result);
    }

    /**
     * @return
     * @description: 界桩数量按地区统计
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/4/28 0028 17:14
     */
    @PostMapping(value = "/getMarkerCount")
    public ResultVO getMarkerCount(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        String code = (String) dataParam.get("code");
        if (StringUtils.isBlank(code)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "行政区划号不能为空");
        }
        Integer level = iLmPointService.getLevelByCode(code);

        List<Map<String, Object>> result = totalService.getMarkerCount(code, level);

        return ResultVOUtil.success(result);
    }

    /**
     * @return
     * @description: 红线斑块功能划分面积统计
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/4/28 0028 18:08
     */
    @PostMapping(value = "/getUseArea")
    public ResultVO getUserArea(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        String code = (String) dataParam.get("code");

        if (StringUtils.isBlank(code)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "行政区划编码不能为空");
        }
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

        List<Map<String, Object>> result = totalService.getUserArea(codes);

        return ResultVOUtil.success(result);
    }

    /**
     * @return
     * @description: 一张图获取调查表数据
     * @params
     * @author zf1017@foxmail.com
     * @date 2019/5/20 0020 16:45
     */
    @RequestMapping(value = "/getSurveyCount")
    public ResultVO getSurveyCount(@RequestBody Map<String, Object> paramsMap) {

        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        String code = (String) dataParam.get("code");

        if (StringUtils.isBlank(code)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "行政区划编码不能为空");
        }
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
        //获取当前日期和前十五天日期
        LocalDate currentTime = LocalDate.now();
        LocalDate beforeTime = currentTime.minusDays(14);

        Map<String, Object> result = totalService.getSurveySum(codes, currentTime, beforeTime);

        return ResultVOUtil.success(result);
    }

    /*@RequestMapping("/test")
    public ResultVO test(){
        Map byPhone = sysUserFeign.getByPhone("17614881958");
        return ResultVOUtil.success(byPhone);
    }

    @PostMapping("/getList")
    public ResultVO testList(){
        List<String> list = new ArrayList<>();
        list.add("111");
        list.add("222");
        String result = sysUserFeign.testList(list);
        return ResultVOUtil.success(result);
    }*/

    public static void main(String[] args) {
        int a = 2560;
        System.out.println(a / 1000);
    }

    /**
     * @param
     * @return com.gistone.VO.ResultVO
     * @description:预设界桩与红线斑块统计
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/10/17 0017 10:18
     */
    @PostMapping("/getPreMarkAndRedlineTotal")
    public ResultVO getPreMarkAndRedlineTotal() {
        Map<String, Object> result = totalService.getPreMarkAndRedlineTotal();
        return ResultVOUtil.success(result);
    }

    /**
     * @param
     * @return com.gistone.VO.ResultVO
     * @description:标识牌与红线斑块统计
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/10/17 0017 10:52
     */
    @PostMapping("/getBoardAndRedlineTotal")
    public ResultVO getBoardAndRedlineTotal() {
        Map<String, Object> result = totalService.getBoardAndRedlineTotal();
        return ResultVOUtil.success(result);
    }

    /**
     * @param
     * @return com.gistone.VO.ResultVO
     * @description:获取预设红线斑块数，按行政区划统计
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/11/14 0014 17:15
     */
    @PostMapping("/getRedlineCount")
    public ResultVO getRedlineCount() {
        List<Map<String,Object>> result = totalService.getRedlineCount();
        return ResultVOUtil.success(result);
    }

    /**
     * @param
     * @return com.gistone.VO.ResultVO
     * @description:获取拐点统计数，按行政区划统计
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/11/14 0014 17:28
     */
    @PostMapping("/getPointCount")
    public ResultVO getPointCount() {
        List<Map<String, Object>> result = totalService.getPointCount();
        return ResultVOUtil.success(result);
    }


}
