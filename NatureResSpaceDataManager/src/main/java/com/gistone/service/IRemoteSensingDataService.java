package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.VO.ResultVO;
import com.gistone.entity.RemoteSensingData;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lx
 * @since 2019-12-04
 */
public interface IRemoteSensingDataService extends IService<RemoteSensingData> {

    Map<String, Object> list(RemoteSensingData param);
}
