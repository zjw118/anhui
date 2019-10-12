package com.gistone.bjhky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.bjhky.entity.St4ScsCf;

import java.util.List;

/**
 * <p>
 * 航点巡护表单 服务类
 * </p>
 *
 * @author xxh
 * @since 2019-08-14
 */
public interface ISt4ScsCfService extends IService<St4ScsCf> {

    /**
     * @explain :根据航点唯一标识获取巡航点数据
     * @author xxh
     * @date 2019/8/16
     * @param cc002
     * @return com.gistone.bjhky.entity.St4ScsCf
     */
    St4ScsCf getSt4ScsCfByCc002(String cc002);

    List<St4ScsCf> listSailPoint(St4ScsCf cf);

}
