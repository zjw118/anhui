package com.gistone.bjhky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.bjhky.entity.St4PoCdSa;
import com.gistone.bjhky.util.Result;

import java.util.List;

/**
 * <p>
 * 核查点分配记录表 服务类
 * </p>
 *
 * @author zjw
 * @since 2019-08-15
 */
public interface ISt4PoCdSaService extends IService<St4PoCdSa> {
    Result givePoint(List<Integer> uids, List<Integer> pointList);
}
