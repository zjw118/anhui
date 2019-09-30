package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.DataSurvey;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;
import java.util.Map;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xjc
 * @since 2019-03-01
 */
public interface DataSurveyMapper extends BaseMapper<DataSurvey> {

	List<Map<String, Object>> getRedLindList(@Param("id") String id);

	String getPkid(DataSurvey dataSurvey);




}
