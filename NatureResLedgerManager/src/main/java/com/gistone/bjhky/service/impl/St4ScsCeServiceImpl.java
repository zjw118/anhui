package com.gistone.bjhky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.bjhky.entity.St4ScsCe;
import com.gistone.bjhky.mapper.St4ScsCeMapper;
import com.gistone.bjhky.service.ISt4ScsCeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 航点附件表 服务实现类
 * </p>
 *
 * @author xxh
 * @since 2019-08-14
 */
@Service
public class St4ScsCeServiceImpl extends ServiceImpl<St4ScsCeMapper, St4ScsCe> implements ISt4ScsCeService {


    @Autowired
    private  St4ScsCeMapper scsCeMapper;

    @Override
    public List<St4ScsCe> getSt4ScsCeByCc002(String cc002) {
        QueryWrapper<St4ScsCe> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("CE002",cc002);
        List<St4ScsCe> ceList=scsCeMapper.selectList(queryWrapper);
        return ceList;
    }
}
