package com.gistone.service;

import com.gistone.VO.ResultVO;
import com.gistone.entity.St4ScsCbd;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 移动端提交检测表 服务类
 * </p>
 *
 * @author zjw
 * @since 2019-11-26
 */
public interface ISt4ScsCbdService extends IService<St4ScsCbd> {
    ResultVO listCheckMsg ();
}
