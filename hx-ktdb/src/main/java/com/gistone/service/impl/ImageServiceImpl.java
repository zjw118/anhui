package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.Image;
import com.gistone.mapper.ImageMapper;
import com.gistone.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
    * <p>
    * 影像数据表 服务实现类
    * </p>
    *
    * @author zf1017@foxmail.com
    * @since 2019-10-18
    */
    @Service
    @Transactional
    @Slf4j
    public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements ImageService {

    @Autowired
    private ImageMapper mapper;
    @Override
    public Map<String, Object> list(Integer pageNum, Integer pageSize,String userName) {

    QueryWrapper<Image> wrapper = new QueryWrapper<>();
    if(StringUtils.isNotBlank(userName)){
    wrapper.likeRight("name",userName);
    }
    wrapper.eq("del_flag",1);
    wrapper.orderByDesc("create_date");
    IPage<Image> iPage = mapper.selectPage(new Page<>(pageNum, pageSize), wrapper);


    Map<String, Object> result = new HashMap<>();
    result.put("rows", iPage.getRecords());
    result.put("total", iPage.getTotal());

    return result;
}

    @Override
    public void delete(List<Integer> ids) {
    //具体逻辑

        }

    @Override
    public void insert(Image entity) {
    //具体逻辑

    }



    @Override
    public void edit(Image entity) {
        //具体逻辑
    }

    }


