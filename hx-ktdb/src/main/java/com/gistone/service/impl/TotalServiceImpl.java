package com.gistone.service.impl;

import com.gistone.VO.ResultVO;
import com.gistone.mapper.TotalMapper;
import com.gistone.service.TotalService;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class TotalServiceImpl implements TotalService {

    @Autowired
    private TotalMapper totalMapper;

    @Override
    public Double getRedlineSumArea(String code) {
        Double sunArea = totalMapper.selectSumArea(code);
        //保留两位小数

        DecimalFormat df = new DecimalFormat("#.00");

        return Double.parseDouble(df.format(sunArea));
    }


    @Override
    public ResultVO getTotal(String code) {
        Map<String, Object> result = new HashMap<>();
        try {
            int redline = totalMapper.selectRedlineTotal(code);

            int marker = totalMapper.selectMarkerTotal(code);

            int point = totalMapper.selectPointTotal(code);

            int board = totalMapper.selectBoardTotal(code);

//            int survey = totalMapper.selectSurveyTotal(code);

            result.put("redlineCount", redline);
            result.put("markerCount", marker);
            result.put("pointCount", point);
            result.put("boardCount", board);
//            result.put("surveyCount", survey);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询统计总数异常，异常信息为：", e.getMessage());
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "查询统计总数失败");
        }

        return ResultVOUtil.success(result);
    }

    @Override
    public List<Map<String, Object>> getAreas(String code, Integer level) {

        List<Map<String, Object>> mapList = totalMapper.selectAreas(code, level);
        return mapList;
    }

    @Override
    public List<Map<String, Object>> getCount(String code, LocalDate currentTime, LocalDate beforeTime) {

        List<Map<String, Object>> mapList = totalMapper.selectCount(code, currentTime, beforeTime);
        return mapList;
    }

    @Override
    public List<Map<String, Object>> getBoardCount(String code, LocalDate currentTime, LocalDate beforeTime) {
        List<Map<String, Object>> mapList = totalMapper.selectBoardCount(code, currentTime, beforeTime);
        return mapList;
    }

    @Override
    public List<Map<String, Object>> getSurveyCount(String code, LocalDate currentTime, LocalDate beforeTime) {
        List<Map<String, Object>> mapList = totalMapper.selectSurveyCount(code, currentTime, beforeTime);
        return mapList;
    }

    @Override
    public List<Map<String, Object>> getMarkerCount(String code, Integer level) {
        List<Map<String, Object>> mapList = totalMapper.selectMarkerAndBoardCount(code, level);

        return mapList;
    }

    @Override
    public List<Map<String, Object>> getUserArea(String code) {

        List<Map<String,Object>> mapList = totalMapper.selectUserArea(code);
        return mapList;
    }

    @Override
    public Map<String, Object> getSurveySum(String code,LocalDate currentTime,LocalDate beforeTime) {
        //自然背景调查
        List<Map<String,Object>> naturalBackgroundSurvey = totalMapper.selectSurvey(1,code,currentTime,beforeTime);
        int naturalBackgroundCount = totalMapper.selectSurveySum(1,code,beforeTime);
        //环境质量调查
        int environmentalQualityCount = totalMapper.selectSurveySum(2,code,beforeTime);
        List<Map<String,Object>> environmentalQualitySurvey = totalMapper.selectSurvey(2,code,currentTime,beforeTime);
        //生态系统状况调查
        int ecosystemCount = totalMapper.selectSurveySum(3,code,beforeTime);
        List<Map<String,Object>> ecosystemSurvey = totalMapper.selectSurvey(3,code,currentTime,beforeTime);
        //人为活动调查
        int humanActivityCount = totalMapper.selectSurveySum(4,code,beforeTime);
        List<Map<String,Object>> humanActivity = totalMapper.selectSurvey(4,code,currentTime,beforeTime);
        Map<String,Object> result = new HashMap<>();
        result.put("NBSurveyCount",naturalBackgroundSurvey);
        result.put("NBSurveyBeforeCount",naturalBackgroundCount);
        result.put("EQSurveyCount",environmentalQualitySurvey);
        result.put("EQSurveyBeforeCount",environmentalQualityCount);
        result.put("ecosystemSurvey",ecosystemSurvey);
        result.put("ecosystemSurveyBeforeCount",ecosystemCount);
        result.put("humanActivity",humanActivity);
        result.put("humanActivityBeforeCount",humanActivityCount);

        return result;
    }

    @Override
    public int getBeforeCount(String code, LocalDate beforeTime) {

        int sum = totalMapper.selectBeforeCount(code,beforeTime);
        return sum;
    }

    @Override
    public int getBeforeSum(String code, LocalDate beforeTime) {
        int sum = totalMapper.selectBeforeSum(code,beforeTime);
        return sum;
    }
}
