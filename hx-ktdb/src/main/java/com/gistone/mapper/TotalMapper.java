package com.gistone.mapper;

import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TotalMapper {
    int selectRedlineTotal(@Param("code") String code);

    int selectMarkerTotal(@Param("code") String code);

    Double selectSumArea(@Param("code") String code);

    int selectPointTotal(@Param("code") String code);

    int selectBoardTotal(@Param("code") String code);

    int selectSurveyTotal(@Param("code") String code);

    List<Map<String,Object>> selectAreas(@Param("code") String code,@Param("level") Integer level);

    List<Map<String,Object>> selectCount(@Param("code") String code, @Param("currentTime") LocalDate currentTime, @Param("beforeTime") LocalDate beforeTime);

   List<Map<String,Object>> selectBoardCount (@Param("code") String code, @Param("currentTime") LocalDate currentTime, @Param("beforeTime") LocalDate beforeTime);

    List<Map<String, Object>> selectSurveyCount(@Param("code") String code, @Param("currentTime") LocalDate currentTime, @Param("beforeTime") LocalDate beforeTime);

    List<Map<String,Object>> selectMarkerCount(@Param("code") String code,@Param("sub") Integer sub,@Param("level") Integer level);

    List<Map<String,Object>> selectUserArea(@Param("code") String code);

    List<Map<String,Object>> selectMarkerAndBoardCount(@Param("code") String code,@Param("level") Integer level);

    List<Map<String, Object>> selectSurvey(@Param("type") Integer type, @Param("code") String code, @Param("currentTime") LocalDate currentTime, @Param("beforeTime") LocalDate beforeTime);

    int selectBeforeCount(@Param("code") String code, @Param("beforeTime") LocalDate beforeTime);

    int selectBeforeSum(@Param("code") String code, @Param("beforeTime") LocalDate beforeTime);

    int selectSurveySum(@Param("type") Integer type, @Param("code") String code, @Param("beforeTime") LocalDate beforeTime);

    List<Map<String,Object>> getPreMarkerCount();

    List<Map<String,Object>> getRedlineCounr();

    List<Map<String,Object>> getPreBoardCount();

    List<Map<String,Object>> getRedlineCount();
    List<Map<String,Object>> getPointCount();
}
