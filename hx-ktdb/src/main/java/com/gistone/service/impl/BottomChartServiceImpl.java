package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.BottomChart;
import com.gistone.mapper.BottomChartMapper;
import com.gistone.service.BottomChartService;
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
 * 底图服务表 服务实现类
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-10-14
 */
@Service
@Transactional
@Slf4j
public class BottomChartServiceImpl extends ServiceImpl<BottomChartMapper, BottomChart> implements BottomChartService {

    @Autowired
    private BottomChartMapper mapper;

    @Override
    public Map<String, Object> list(Integer pageNum, Integer pageSize, String name) {

        QueryWrapper<BottomChart> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            wrapper.likeRight("name", name);
        }
        //按创建日期倒序
        wrapper.orderByDesc("createDate");
        wrapper.eq("del_flag", 1);

        IPage<BottomChart> iPage = mapper.selectPage(new Page<>(pageNum, pageSize), wrapper);


        Map<String, Object> result = new HashMap<>();
        result.put("rows", iPage.getRecords());
        result.put("total", iPage.getTotal());

        return result;
    }

    @Override
    public void delete(List<Integer> ids) {
        for (Integer id : ids) {
            BottomChart bottomChart = mapper.selectById(id);
            bottomChart.setDelFlag(0);
            mapper.updateById(bottomChart);
        }
    }

    @Override
    public void insert(BottomChart entity) {
        mapper.insert(entity);

    }

    @Override
    public void edit(BottomChart entity) {
        mapper.updateById(entity);
    }

}




