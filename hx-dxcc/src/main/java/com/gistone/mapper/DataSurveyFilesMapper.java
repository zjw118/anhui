package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.DataSurveyFiles;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xjc
 * @since 2019-03-27
 */
public interface DataSurveyFilesMapper extends BaseMapper<DataSurveyFiles> {

	int insertBatch(List<DataSurveyFiles> list);


}
