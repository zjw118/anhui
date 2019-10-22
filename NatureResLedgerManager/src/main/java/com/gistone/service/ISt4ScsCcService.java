package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.St4ScsCc;
import com.gistone.entity.St4SysSa;
import com.gistone.util.Result;

/**
 * <p>
 * 航点基础表 服务类
 * </p>
 *
 * @author xxh
 * @since 2019-08-14
 */
public interface ISt4ScsCcService extends IService<St4ScsCc> {


    /**
     * @explain : 获取航点基础信息，根据分页查询及字段查询
     * @author xxh
     * @date 2019/8/15
     * @param st4ScsCc
     * @return Result
     */
    Result listSt4ScsCcByPage(St4ScsCc st4ScsCc, St4SysSa seUser);


    Result getDetailSailPoint(St4ScsCc cc);

    /**
     * @explain : 根据航点唯一标识获取航点基础数据信息
     * @author xxh
     * @date 2019/8/16
     * @param cc002
     * @return St4ScsCc
     */
    St4ScsCc getSt4ScsCcByCc002(String cc002);

    /**
     * 查询物种分布
     * @param data
     * @return
     */
    Result listSpeciesToMap(St4ScsCc data);

    /**
     * 根据保护区id查询到航点
     * @param rid
     * @return
     */
    Result getSailPointByReserveId(Integer rid);
}
