package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.LsDataStrategy;
import com.gistone.mapper.LsDataStrategyMapper;
import com.gistone.service.LsDataStrategyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-11-27
 */
@Service
@Transactional
@Slf4j
public class LsDataStrategyServiceImpl extends ServiceImpl<LsDataStrategyMapper, LsDataStrategy> implements LsDataStrategyService {

    @Autowired
    private LsDataStrategyMapper mapper;

    @Override
    public Map<String, Object> list(Integer pageNum, Integer pageSize, String userName) {

        QueryWrapper<LsDataStrategy> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userName)) {
            wrapper.like("cycle",userName);
        }
         wrapper.eq("del_flag",1);
        wrapper.orderByDesc("create_time");
        IPage<LsDataStrategy> iPage = mapper.selectPage(new Page<>(pageNum, pageSize), wrapper);


        Map<String, Object> result = new HashMap<>();
        result.put("rows", iPage.getRecords());
        result.put("total", iPage.getTotal());

        return result;
    }

    @Override
    public void delete(Integer id) {
        //具体逻辑

            LsDataStrategy lsDataStrategy = mapper.selectById(id);
            lsDataStrategy.setDelFlag(0);
            mapper.updateById(lsDataStrategy);

    }

    @Override
    public void insert(LsDataStrategy entity) {
        //具体逻辑
        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = null;
        try {
            date2 = format2.parse(entity.getTimeStr());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        entity.setTime(date2).setCreateTime(new Date());
        mapper.insert(entity);


    }


    @Override
    public void edit(LsDataStrategy entity) {
        //具体逻辑
        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = null;
        try {
            date2 = format2.parse(entity.getTimeStr());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        entity.setTime(date2).setCreateTime(new Date());
        mapper.updateById(entity);
    }

}


