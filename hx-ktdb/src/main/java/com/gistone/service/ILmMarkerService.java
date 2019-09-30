package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.LmMarker;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjc
 * @since 2019-02-28
 */
public interface ILmMarkerService extends IService<LmMarker> {

	Map<String, Object> getLmMarkerList(Map<String, Object> resultMap);

	String upload(MultipartFile files);

	Map<String, Object> save(LmMarker lm, List<Map<String, Object>> lmp);

	boolean deleteByLmId(String lmId);

	Map<String, Object> update(LmMarker lm, List<Map<String, Object>> lmp);

	String exportLmMarkerInfor(Map<String, Object> resultMap, HttpServletRequest request, HttpServletResponse response);

}
