package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.St4SysSh;
import com.gistone.mapper.St4SysShMapper;
import com.gistone.service.ISt4SysShService;
import com.gistone.util.ObjectUtils;
import com.gistone.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统日志表 服务实现类
 * </p>
 *
 * @author xxh
 * @since 2019-08-12
 */
@Service
public class St4SysShServiceImpl extends ServiceImpl<St4SysShMapper, St4SysSh> implements ISt4SysShService {
    @Autowired
    private St4SysShMapper st4SysShMapper;
    @Override
    public Result listLog(St4SysSh sh) {
        if(ObjectUtils.isNotNullAndEmpty(sh.getStrTime())){
            sh.setStrTime(sh.getStrTime()+" 00:00:00");
        }
        if(ObjectUtils.isNotNullAndEmpty(sh.getEndTime())){
            sh.setEndTime(sh.getEndTime()+" 23:59:59");
        }
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("SH007",1);

        Page<St4SysSh> page = new Page<>(sh.getPageNumber(),sh.getPageSize());
        IPage<St4SysSh> ipage = st4SysShMapper.listLog(page,sh);
        Result result = new Result();
        result.setStatus(1000);
        result.setMsg("加载成功");
        result.setRows(ipage.getRecords());
        result.setTotal((int)ipage.getTotal());
        result.setPage((int)ipage.getPages());

        return result;
    }

}
