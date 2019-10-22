package com.gistone.service;

import com.gistone.entity.St4PoSaCl;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.util.Result;

import java.util.List;

/**
 * <p>
 * 巡护任务表 服务类
 * </p>
 *
 * @author zjw
 * @since 2019-08-15
 */
public interface ISt4PoSaClService extends IService<St4PoSaCl> {

    Result insertList (List<Integer> uidList, List<Integer> taskList);

    Result sysTaskData(Integer uid);

}
