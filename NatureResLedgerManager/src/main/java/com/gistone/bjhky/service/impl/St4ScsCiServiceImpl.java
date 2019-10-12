package com.gistone.bjhky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gistone.bjhky.entity.St4PoSaSj;
import com.gistone.bjhky.entity.St4ScsCi;
import com.gistone.bjhky.entity.St4SysSa;
import com.gistone.bjhky.entity.St4SysSj;
import com.gistone.bjhky.mapper.St4PoSaSjMapper;
import com.gistone.bjhky.mapper.St4ScsCiMapper;
import com.gistone.bjhky.service.ISt4ScsCiService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.bjhky.util.Result;
import com.gistone.bjhky.util.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * app预警表 服务实现类
 * </p>
 *
 * @author zjw
 * @since 2019-08-17
 */
@Service
public class St4ScsCiServiceImpl extends ServiceImpl<St4ScsCiMapper, St4ScsCi> implements ISt4ScsCiService {
    @Autowired
    private  St4ScsCiMapper st4ScsCiMapper;

    @Autowired
    private St4PoSaSjMapper st4PoSaSjMapper;

    @Override
    public Result listAppWarn(St4ScsCi ci, St4SysSa seUser) {
        QueryWrapper<St4PoSaSj> sasjWrapper = new QueryWrapper<>();
        sasjWrapper.eq("SA001",seUser.getSa001());
        List<St4PoSaSj> sasjList = st4PoSaSjMapper.selectList(sasjWrapper);
        if(sasjList!=null&&sasjList.size()>0){
            ci.setUnitId(sasjList.get(0).getSj001());
        }
       /* QueryWrapper<St4ScsCi> wrapper = new QueryWrapper<>();
        wrapper.eq("CI006",1);
        wrapper.like("CI002",ci.getCi002()==null?"":ci.getCi002());
        if(ci.getStrTime()!=null){
            wrapper.ge("CI004",ci.getStrTime());
        }
        if(ci.getEndTime()!=null){
            wrapper.le("CI004",ci.getEndTime());
        }
        if(ci.getCi005()!=null){
            wrapper.eq("CI005",ci.getCi005());
        }
        wrapper.orderByDesc("CI004");*/
        Page<St4ScsCi> page = new Page<>(ci.getPageNumber(),ci.getPageSize());

        IPage<St4ScsCi> ipage = st4ScsCiMapper.listAppWarn(page,ci);
        Result result = new Result();
        result.setStatus(1000);
        result.setMsg("加载"+ ResultMsg.MSG_1000);
        result.setRows(ipage.getRecords());
        result.setPage((int)ipage.getPages());
        result.setTotal((int)ipage.getTotal());
        return result;
    }

    @Override
    public Result getWarnDetail(St4ScsCi ci) {
        Result result = new Result();
        result.setStatus(1000);
        result.setMsg("加载成功");
        result.setData(st4ScsCiMapper.getWarnDetail(ci));
        return result;
    }
}
