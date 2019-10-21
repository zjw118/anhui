package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.Image;

import java.util.List;
import java.util.Map;

/**
    * <p>
    * 影像数据表 服务类
    * </p>
    *
    * @author zf1017@foxmail.com
    * @since 2019-10-18
    */
    public interface ImageService extends IService<Image> {


    Map<String, Object> list(Integer pageNum, Integer pageSize, String Name);

    void delete(List<Integer> id);

    void insert(Image entity);

    void edit(Image entity);
    }
