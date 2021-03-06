package com.gistone.service;

    import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.LsDataStrategy;

import java.util.Map;

    /**
    * <p>
    *  服务类
    * </p>
    *
    * @author zf1017@foxmail.com
    * @since 2019-11-27
    */
    public interface LsDataStrategyService extends IService<LsDataStrategy> {


    Map<String, Object> list(Integer pageNum,Integer pageSize,String Name);

    void delete(Integer id);

    void insert(LsDataStrategy entity);

    void edit(LsDataStrategy entity);
    }
