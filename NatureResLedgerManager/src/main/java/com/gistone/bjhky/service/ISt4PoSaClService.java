package com.gistone.bjhky.service;

import com.gistone.bjhky.entity.St4PoSaCl;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.bjhky.entity.St4ScsCl;
import com.gistone.bjhky.entity.St4SysSa;
import com.gistone.bjhky.util.Result;

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

    Result insertList (List<Integer> uidList,List<Integer> taskList);

    Result sysTaskData(Integer uid);

}
