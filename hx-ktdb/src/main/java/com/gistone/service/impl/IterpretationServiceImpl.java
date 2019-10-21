package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.Iterpretation;
import com.gistone.mapper.IterpretationMapper;
import com.gistone.service.IterpretationService;
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
    * 人类活动解译信息表 服务实现类
    * </p>
    *
    * @author zf1017@foxmail.com
    * @since 2019-10-18
    */
    @Service
    @Transactional
    @Slf4j
    public class IterpretationServiceImpl extends ServiceImpl<IterpretationMapper, Iterpretation> implements IterpretationService {

    @Autowired
    private IterpretationMapper mapper;
    @Override
    public Map<String, Object> list(Integer pageNum, Integer pageSize,String userName) {

    QueryWrapper<Iterpretation> wrapper = new QueryWrapper<>();
    if(StringUtils.isNotBlank(userName)){
    //wrapper.likeRight("SA008",userName);
    }
    // wrapper.eq("SA007",1);
    //wrapper.orderByDesc("SA003");
    IPage<Iterpretation> iPage = mapper.selectPage(new Page<>(pageNum, pageSize), wrapper);


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
    public void insert(List<Map<String, Object>> data) {
    //通过影像id先删除记录然后再插入
        mapper.delete(new QueryWrapper<Iterpretation>().eq("image_id",""));
        //从data中构造属性
        for (Map<String, Object> datum : data) {
           Map<String,Object> attributes = (Map<String, Object>) datum.get("attributes");
           //通过属性构造参数
            Iterpretation iterpretation = new Iterpretation();
            mapper.insert(iterpretation);
        }


    }



    @Override
    public void edit(Iterpretation entity) {
        //具体逻辑
    }

    }


