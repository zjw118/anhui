package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.DataRedlineRegister;
import com.gistone.mapper.DataRedlineRegisterMapper;
import com.gistone.service.IDataRedlineRegisterService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Override
	public Map<String, Object> getRedLineList(String redLineName, String code, Integer pageNum, Integer pageSize) {
		QueryWrapper<DataRedlineRegister> wrapper = new QueryWrapper<>();
		Map<String, Object> result = new HashMap<>();
		if (StringUtils.isNotBlank(redLineName)) {
			wrapper.likeRight("srld_number", redLineName);
		}
		if (StringUtils.isNotBlank(code)) {
			wrapper.likeRight("srld_code", code);
		}

		IPage<DataRedlineRegister> IPage = dataRedlineRegisterMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);

		result.put("rows", IPage.getRecords());
		result.put("total", IPage.getTotal());
		return result;
	}

}
