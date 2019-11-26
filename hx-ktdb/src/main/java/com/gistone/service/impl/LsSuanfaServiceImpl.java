package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.LsSuanfa;
import com.gistone.mapper.LsSuanfaMapper;
import com.gistone.service.LsSuanfaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
    * <p>
    *  服务实现类
    * </p>
    *
    * @author zf1017@foxmail.com
    * @since 2019-11-26
    */
    @Service
    @Transactional
    @Slf4j
    public class LsSuanfaServiceImpl extends ServiceImpl<LsSuanfaMapper, LsSuanfa> implements LsSuanfaService {

    @Autowired
    private LsSuanfaMapper mapper;
    @Override
    public Map<String, Object> list(Integer pageNum, Integer pageSize,String userName) {

    QueryWrapper<LsSuanfa> wrapper = new QueryWrapper<>();
    if(StringUtils.isNotBlank(userName)){
    wrapper.likeRight("name",userName);
    }
     wrapper.eq("del_flag",1);
    //wrapper.orderByDesc("SA003");
    IPage<LsSuanfa> iPage = mapper.selectPage(new Page<>(pageNum, pageSize), wrapper);


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
    public void insert(LsSuanfa entity) {
        entity.setCreateTime(LocalDateTime.now());
        mapper.insert(entity);
    //具体逻辑

    }



    @Override
    public void edit(LsSuanfa entity) {
        //具体逻辑
    }

    }


