package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.BottomChart;

import java.util.List;
import java.util.Map;

/**
    * <p>
    * 底图服务表 服务类
    * </p>
    *
    * @author zf1017@foxmail.com
    * @since 2019-10-14
    */
    public interface BottomChartService extends IService<BottomChart> {


    Map<String, Object> list(Integer pageNum, Integer pageSize, String Name);

    void delete(List<Integer> id);

    void insert(BottomChart entity);

    void edit(BottomChart entity);
    }
