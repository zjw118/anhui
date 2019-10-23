package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gistone.entity.St4ScsCad;
import com.gistone.mapper.St4ScsCadMapper;
import com.gistone.service.ISt4ScsCadService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息列表主键 服务实现类
 * </p>
 *
 * @author zhaojingwei
 * @since 2019-09-27
 */
@Service
public class St4ScsCadServiceImpl extends ServiceImpl<St4ScsCadMapper, St4ScsCad> implements ISt4ScsCadService {
    @Autowired
    private St4ScsCadMapper st4ScsCadMapper;
    @Autowired
    private ISt4ScsCadService st4ScsCadService;

    @Override
    public Result getMessageData(St4ScsCad cad) {

        Result result = new Result();
        result.setStatus(1000);
        result.setMsg("加载成功");
        QueryWrapper<St4ScsCad> wrapper = new QueryWrapper<>();
        wrapper.eq("CAD008",1);
        if(cad.getStrTime()!=null && cad.getStrTime() != ""){
            wrapper.ge("CAD006",cad.getStrTime()+" 00:00:00");
        }
        if(cad.getEndTime()!=null && cad.getEndTime() != ""){
            wrapper.le("CAD006",cad.getEndTime()+" 23:59:59");
        }
        if(cad.getCad002()!=null){
            wrapper.eq("CAD002",cad.getCad002());
        }
        if(cad.getCad004()!=null){
            wrapper.eq("CAD004",cad.getCad004());
        }

        Page<St4ScsCad> page = new Page<>(cad.getPageNumber(),cad.getPageSize());

        if(cad.getIsApp()==1){
            wrapper.eq("CAD007",cad.getCad007());
            wrapper.orderByDesc("CAD006");
            IPage<St4ScsCad> iPage = st4ScsCadMapper.selectPage(page,wrapper);
            result.setData(iPage.getRecords());

        }else {
            wrapper.orderByDesc("CAD006");
            IPage<St4ScsCad> iPage = st4ScsCadMapper.selectPage(page,wrapper);
            result.setRows(iPage.getRecords());
            result.setPage((int)iPage.getPages());
            result.setTotal((int)iPage.getTotal());
        }
        return result;
    }

    @Override
    public Result setMessageRead(St4ScsCad cad) {
        return null;
    }
}
