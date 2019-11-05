package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.VO.ResultVO;
import com.gistone.entity.LoginLog;
import com.gistone.util.PageBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjc
 * @since 2019-04-03
 */
public interface ILoginLogService extends IService<LoginLog> {
    ResultVO list(PageBean pageBean);



}
