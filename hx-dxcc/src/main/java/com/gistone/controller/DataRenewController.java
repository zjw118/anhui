package com.gistone.controller;


import com.gistone.VO.ResultVO;
import com.gistone.VO.VersionVO;
import com.gistone.service.IDataRenewService;
import com.gistone.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xjc
 * @since 2019-04-19
 */
@RestController
@RequestMapping("/api/dcxx/dataRenew")
public class DataRenewController {

	@Autowired
	private IDataRenewService iDataRenewService;
	
	@Autowired
    private StringRedisTemplate redisTemplate;
	
	/**
	 * 根据传输的版本比较是否更新
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "getRenewVer",method = RequestMethod.POST)
	public ResultVO getRenewVer() {

        List<VersionVO> result = iDataRenewService.getRenewVer();
		
		return ResultVOUtil.success(result);
	}
	
	
}

