package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.SysLog;
import com.gistone.mapper.SysLogMapper;
import com.gistone.service.SysLogService;
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
 * 系统日志 服务实现类
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-10-31
 */
@Service
@Transactional
@Slf4j
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Autowired
    private SysLogMapper mapper;

    @Override
    public Map<String, Object> list(Integer pageNum, Integer pageSize, String userName, String type,String category) {

        QueryWrapper<SysLog> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userName)) {
            wrapper.likeRight("title", userName);
        }
        if (StringUtils.isNotBlank(type)) {
            wrapper.likeRight("type", type);
        }
        if(StringUtils.isNotBlank(category)){
            wrapper.likeRight("http_method",category);
        }
        wrapper.eq("del_flag", 1);
        wrapper.orderByDesc("create_date");
        IPage<SysLog> iPage = mapper.selectPage(new Page<>(pageNum, pageSize), wrapper);


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
    public void insert(SysLog entity) {
        //具体逻辑

    }


    @Override
    public void edit(SysLog entity) {
        //具体逻辑
    }

}


