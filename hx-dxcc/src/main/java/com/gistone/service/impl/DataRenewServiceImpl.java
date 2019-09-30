package com.gistone.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.VO.VersionVO;
import com.gistone.entity.DataRenew;
import com.gistone.mapper.DataRenewMapper;
import com.gistone.service.IDataRenewService;
import com.gistone.service.ISysDbVersionService;
import com.gistone.service.ISysShapeVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjc
 * @since 2019-04-19
 */
@Service
public class DataRenewServiceImpl extends ServiceImpl<DataRenewMapper, DataRenew> implements IDataRenewService {

	@Autowired
	private DataRenewMapper dataRenewMapper;

	@Autowired
	private ISysDbVersionService sysDbVersionService;

	@Autowired
	private ISysShapeVersionService sysShapeVersionService;
	@Override
	public List<VersionVO> getRenewVer() {
		

		VersionVO dbFilePath = sysDbVersionService.updateFile();
		List<VersionVO> shapeFilePath = sysShapeVersionService.updateFile();
		shapeFilePath.add(dbFilePath);

		
		return shapeFilePath;
	}

}
