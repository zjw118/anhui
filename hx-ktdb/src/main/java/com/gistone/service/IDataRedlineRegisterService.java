package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.VO.DataRedlineRegisterVO;
import com.gistone.entity.DataRedlineRegister;

import java.util.List;
import java.util.Map;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjc
 * @since 2019-02-28
 */
public interface IDataRedlineRegisterService extends IService<DataRedlineRegister> {

	List<Map<String, Object>> listAll(DataRedlineRegister date);

	void updateTarget(Integer id,String target);

	void updateBy(DataRedlineRegister data);

	Map<String, Object> getRedLineList(String redLineName,String code,Integer pageNum,Integer pageSize);

	List<DataRedlineRegisterVO> getList();
}
