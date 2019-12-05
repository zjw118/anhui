package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.St4ScsCbd;
import com.gistone.mapper.St4ScsCbdMapper;
import com.gistone.service.St4ScsCbdService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 移动端提交检测表 服务实现类
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-12-04
 */
@Service
@Transactional
@Slf4j
public class ISt4ScsCbdServiceImpl extends ServiceImpl<St4ScsCbdMapper, St4ScsCbd> implements St4ScsCbdService {

    @Autowired
    private St4ScsCbdMapper mapper;

    @Override
    public Map<String, Object> list(Integer pageNum, Integer pageSize, String userName,String time,String content) {

        QueryWrapper<St4ScsCbd> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userName)) {
            wrapper.like("CBD002",userName);
        }
       /* if(){
            
        }*/
        wrapper.eq("del_flag", 1);
        wrapper.orderByDesc("CBD003");
        IPage<St4ScsCbd> iPage = mapper.selectPage(new Page<>(pageNum, pageSize), wrapper);


        Map<String, Object> result = new HashMap<>();
        result.put("rows", iPage.getRecords());
        result.put("total", iPage.getTotal());

        return result;
    }

    @Override
    public void delete(Integer ids) {
        //具体逻辑
        St4ScsCbd st4ScsCbd = mapper.selectById(ids);
        st4ScsCbd.setDelFlag(0);
        mapper.updateById(st4ScsCbd);
    }

    @Override
    public void insert(St4ScsCbd entity) {
        //具体逻辑

    }


    @Override
    public void edit(St4ScsCbd entity) {
        //具体逻辑
        mapper.updateById(entity);
    }

}


