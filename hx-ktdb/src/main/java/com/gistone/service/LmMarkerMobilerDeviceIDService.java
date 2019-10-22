package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.VO.ResultVO;
import com.gistone.entity.LmMarkerMobile;
import com.gistone.entity.LmMarkerMobileDeviceID;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ljk

 */
public interface LmMarkerMobilerDeviceIDService extends IService<LmMarkerMobileDeviceID> {


    ResultVO importZipCsv(List<String[]> list,String userId ,String fileNameNoIndex) throws Exception;
}
