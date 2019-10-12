package com.gistone.bjhky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.bjhky.entity.St4ScsCf;
import com.gistone.bjhky.mapper.St4ScsCfMapper;
import com.gistone.bjhky.service.ISt4ScsCfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 航点巡护表单 服务实现类
 * </p>
 *
 * @author xxh
 * @since 2019-08-14
 */
@Service
public class St4ScsCfServiceImpl extends ServiceImpl<St4ScsCfMapper, St4ScsCf> implements ISt4ScsCfService {

    @Autowired
    private St4ScsCfMapper cfMapper;

    @Override
    public St4ScsCf getSt4ScsCfByCc002(String cc002) {
        QueryWrapper<St4ScsCf> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("CC002",cc002);
        List<St4ScsCf> scsCf=cfMapper.selectList(queryWrapper);
        if(scsCf.size()>0){
            return scsCf.get(0);
        }
        return null;
    }

    @Override
    public List<St4ScsCf> listSailPoint(St4ScsCf cf) {

        return null;
    }
}
