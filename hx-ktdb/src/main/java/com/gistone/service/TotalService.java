package com.gistone.service;

import com.gistone.VO.ResultVO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TotalService {

    Double getRedlineSumArea(String code);

    ResultVO getTotal(String code);

    List<Map<String, Object>> getAreas(String code, Integer level);

    List<Map<String, Object>> getCount(String code, LocalDate currentTime, LocalDate beforeTime);

    List<Map<String, Object>> getBoardCount(String code, LocalDate currentTime, LocalDate beforeTime);

    List<Map<String,Object>> getSurveyCount(String code, LocalDate currentTime, LocalDate beforeTime);

    List<Map<String, Object>> getMarkerCount(String code, Integer level);

    List<Map<String,Object>> getUserArea(String code);

    Map<String,Object> getSurveySum(String code,LocalDate currentTime,LocalDate beforeTime);

    int getBeforeCount(String code,LocalDate beforeTime);

    int getBeforeSum(String code,LocalDate beforeTime);

    Map<String,Object> getPreMarkAndRedlineTotal();

    Map<String,Object> getBoardAndRedlineTotal();

    Map<String,Object> getRedlineCount();

    Map<String,Object> getPointCount();

}
