package com.gistone.bjhky.service;

import com.gistone.bjhky.entity.St4SysSa;
import com.gistone.bjhky.entity.St4SysSe;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.bjhky.util.Result;

import java.util.List;

/**
 * <p>
 * 数据备份表 服务类
 * </p>
 *
 * @author zhaojingwei
 * @since 2019-09-04
 */
public interface ISt4SysSeService extends IService<St4SysSe> {
    /**
     * 数据备份列表
      * @param se
     * @return
     */
    Result listSeData(St4SysSe se);


    //自动备份数据库
    void autoBackup() throws Exception;


    //查询备份数据库名是否存在
    List<St4SysSe> listByName(St4SysSe da) ;

    //手动备份数据库
    Result manualBackup(St4SysSe da, St4SysSa sysUser)  ;
}
