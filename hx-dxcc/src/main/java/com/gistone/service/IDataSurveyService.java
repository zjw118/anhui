package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.DataSurvey;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjc
 * @since 2019-03-01
 */
public interface IDataSurveyService extends IService<DataSurvey> {

	List<Map<String, Object>> getRedLindList(String id);

	boolean save(DataSurvey dataSurvey, String detailsArr);

	Map<String, Object> upload(MultipartFile[] files);



}
