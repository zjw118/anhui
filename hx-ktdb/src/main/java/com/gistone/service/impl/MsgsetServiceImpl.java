package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.Msgset;
import com.gistone.mapper.MsgsetMapper;
import com.gistone.mapper.ISt4ScsCbdMapper;
import com.gistone.service.MsgsetService;
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
    *  服务实现类
    * </p>
    *
    * @author zf1017@foxmail.com
    * @since 2019-12-05
    */
    @Service
    @Transactional
    @Slf4j
    public class MsgsetServiceImpl extends ServiceImpl<MsgsetMapper, Msgset> implements MsgsetService {

    @Autowired
    private ISt4ScsCbdMapper st4ScsCbdMapper;
    @Autowired
    private MsgsetMapper mapper;
    @Override
    public Map<String, Object> list(Integer pageNum, Integer pageSize,String userName) {

    QueryWrapper<Msgset> wrapper = new QueryWrapper<>();
    if(StringUtils.isNotBlank(userName)){
    //wrapper.likeRight("SA008",userName);
    }
    // wrapper.eq("SA007",1);
    //wrapper.orderByDesc("SA003");
    IPage<Msgset> iPage = mapper.selectPage(new Page<>(pageNum, pageSize), wrapper);


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
    public void insert(Msgset entity) {
    //具体逻辑

    }



    @Override
    public void edit(Msgset entity) {
        //具体逻辑
        if(entity.getVerify()==1){
            //消息改为审核状态
            st4ScsCbdMapper.updateByType();
        }else{
            //消息改为审核状态
            st4ScsCbdMapper.updateByType2();
        }

        mapper.updateById(entity);
    }

    }


