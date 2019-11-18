package com.gistone.service;

import com.gistone.VO.ResultVO;
import com.gistone.entity.St4ScsCbb;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhaojingwei
 * @since 2019-11-16
 */
public interface ISt4ScsCbbService extends IService<St4ScsCbb> {
    ResultVO listReserveData(St4ScsCbb cba);
}
