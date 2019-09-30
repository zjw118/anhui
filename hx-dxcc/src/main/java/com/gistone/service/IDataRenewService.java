package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.VO.VersionVO;
import com.gistone.entity.DataRenew;

import java.util.List;



/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjc
 * @since 2019-04-19
 */
public interface IDataRenewService extends IService<DataRenew> {

	List<VersionVO> getRenewVer();

}
