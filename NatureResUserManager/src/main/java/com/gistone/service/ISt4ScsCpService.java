package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.St4ScsCp;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xxh
 * @since 2019-08-14
 */
public interface ISt4ScsCpService extends IService<St4ScsCp> {

    /**
     * @explain : 根据角色id，表单对应字段
     * @author xxh
     * @date 2019/8/14
     * @param cp003 任务ID
     * @param cp006 表单类别（ 0台账表  1 巡护表）
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String,Object>> getSt4ScsCpInfo(String cp003, Integer cp006);

}
