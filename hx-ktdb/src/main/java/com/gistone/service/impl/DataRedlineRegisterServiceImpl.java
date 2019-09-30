package com.gistone.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.DataRedlineRegister;
import com.gistone.mapper.DataRedlineRegisterMapper;
import com.gistone.service.IDataRedlineRegisterService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjc
 * @since 2019-02-28
 */
@Service
public class DataRedlineRegisterServiceImpl extends ServiceImpl<DataRedlineRegisterMapper, DataRedlineRegister> implements IDataRedlineRegisterService {

	@Autowired
	private DataRedlineRegisterMapper dataRedlineRegisterMapper;
	
	@Override
	public List<Map<String, Object>> listAll(DataRedlineRegister date) {
		
		List<Map<String, Object>> listAll = dataRedlineRegisterMapper.listAll(date);
		
		return listAll;
	}

	@Override
	public void updateTarget(Integer id,String target) {
		dataRedlineRegisterMapper.updateTarget(id,target);
	}

	@Override
	public void updateBy(DataRedlineRegister data) {
		dataRedlineRegisterMapper.updateBy(data);
	}

}
