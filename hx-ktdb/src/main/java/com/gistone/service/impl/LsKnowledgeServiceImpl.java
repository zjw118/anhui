package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.LsKnowledge;
import com.gistone.mapper.LsKnowledgeMapper;
import com.gistone.service.LsKnowledgeService;
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
 * 服务实现类
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-11-26
 */
@Service
@Transactional
@Slf4j
public class LsKnowledgeServiceImpl extends ServiceImpl<LsKnowledgeMapper, LsKnowledge> implements LsKnowledgeService {

    @Autowired
    private LsKnowledgeMapper mapper;

    @Override
    public Map<String, Object> list(Integer pageNum, Integer pageSize, String userName) {

        QueryWrapper<LsKnowledge> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userName)) {
            wrapper.likeRight("name",userName);
        }
        wrapper.eq("del_flag",1);
        wrapper.orderByDesc("create_time");
        IPage<LsKnowledge> iPage = mapper.selectPage(new Page<>(pageNum, pageSize), wrapper);


        Map<String, Object> result = new HashMap<>();
        result.put("rows", iPage.getRecords());
        result.put("total", iPage.getTotal());

        return result;
    }

    @Override
    public void delete(List<Integer> ids) {
        //具体逻辑
        for (Integer id : ids) {
            LsKnowledge lsKnowledge = mapper.selectById(id);
            lsKnowledge.setDelFlag(0);
            mapper.updateById(lsKnowledge);
        }
    }

    @Override
    public void insert(LsKnowledge entity) {
        //具体逻辑
        entity.setCreateTime(LocalDateTime.now());
        mapper.insert(entity);

    }


    @Override
    public void edit(LsKnowledge entity) {
        //具体逻辑
        mapper.updateById(entity);
    }

}


