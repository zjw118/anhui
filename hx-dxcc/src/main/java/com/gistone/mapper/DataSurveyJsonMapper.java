package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.DataSurveyJson;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xjc
 * @since 2019-03-07
 */
public interface DataSurveyJsonMapper extends BaseMapper<DataSurveyJson> {

	int insertBatch(List<DataSurveyJson> list);

}
