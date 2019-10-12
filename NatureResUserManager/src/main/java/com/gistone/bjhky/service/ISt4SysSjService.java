package com.gistone.bjhky.service;

import com.gistone.bjhky.entity.St4SysSa;
import com.gistone.bjhky.entity.St4SysSj;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.bjhky.util.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjw
 * @since 2019-08-28
 */
public interface ISt4SysSjService extends IService<St4SysSj> {
    /**
     * 单位展示列表
     * @param sj
     * @param seUser
     * @return
     */
    Result listUnit(St4SysSj sj, St4SysSa seUser);
    /**
     * 单位展示列表不分页
     * @param sj
     * @param seUser
     * @return
     */
    Result listUnitNoLimit(St4SysSj sj, St4SysSa seUser);

    /**
     * 添加单位
     * @param sj
     * @param seUser
     * @return
     */
    Result insertUnit(St4SysSj sj, St4SysSa seUser);
    /**
     *修改单位
     * @param sj
     * @param seUser
     * @return
     */
    Result updateUnit(St4SysSj sj, St4SysSa seUser);
    /**
     *删除单位
     * @param sj
     * @param seUser
     * @return
     */
    Result deleteUnit(St4SysSj sj, St4SysSa seUser);

    /**
     * 单位详情
     * @param sj
     * @return
     */
    Result getUnitDetail(St4SysSj sj);

}
