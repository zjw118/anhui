package com.gistone.bjhky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.bjhky.entity.St4ScsCe;

import java.util.List;

/**
 * <p>
 * 航点附件表 服务类
 * </p>
 *
 * @author xxh
 * @since 2019-08-14
 */
public interface ISt4ScsCeService extends IService<St4ScsCe> {


    /**
     * @explain : 根据巡航点唯一标识查询航点附件信息
     * @author xxh
     * @date 2019/8/16
     * @param cc002
     * @return com.gistone.bjhky.entity.St4ScsCe
     */
    List<St4ScsCe> getSt4ScsCeByCc002(String cc002);

}
