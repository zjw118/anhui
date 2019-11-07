package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.St4ScsCkrl;
import com.gistone.mapper.St4ScsCkrlMapper;
import com.gistone.service.ISt4ScsCkrlService;
import com.gistone.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjw
 * @since 2019-11-07
 */
@Service
public class St4ScsCkrlServiceImpl extends ServiceImpl<St4ScsCkrlMapper, St4ScsCkrl> implements ISt4ScsCkrlService {
@Autowired
private St4ScsCkrlMapper st4ScsCkrlMapper;

    @Override
    public Result listHumanStage(St4ScsCkrl ckrl) {
        long pageNumber=ckrl.getPageNumber();
        long pageSize=ckrl.getPageSize();
        QueryWrapper<St4ScsCkrl> st4ScsCkrlQueryWrapper = new QueryWrapper<>();
        st4ScsCkrlQueryWrapper.eq("ck085",1);
        Page<St4ScsCkrl> page = new Page<St4ScsCkrl>(pageNumber,pageSize);
        IPage<St4ScsCkrl> list = st4ScsCkrlMapper.selectPage(page,st4ScsCkrlQueryWrapper);
        Result result = new Result();
        result.setStatus(1000);
        result.setMsg("加载成功");
        result.setPage((int)list.getPages());
        result.setTotal((int)list.getTotal());
        result.setRows(list.getRecords());
        return result;
    }
}
