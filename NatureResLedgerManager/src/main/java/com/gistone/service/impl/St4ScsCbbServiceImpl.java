package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gistone.VO.ResultVO;
import com.gistone.entity.St4ScsCbb;
import com.gistone.mapper.St4ScsCbbMapper;
import com.gistone.service.ISt4ScsCbbService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.util.ObjectUtils;
import com.gistone.util.Result;
import com.gistone.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhaojingwei
 * @since 2019-11-16
 */
@Service
public class St4ScsCbbServiceImpl extends ServiceImpl<St4ScsCbbMapper, St4ScsCbb> implements ISt4ScsCbbService {
    @Autowired
    private  St4ScsCbbMapper st4ScsCbbMapper;


    @Override
    public ResultVO listReserveData(St4ScsCbb cbb){
        Page<St4ScsCbb> page = new Page<>(cbb.getPageNumber(),cbb.getPageSize());
        if(ObjectUtils.isNotNullAndEmpty(cbb.getEndTime())){
            cbb.setEndTime(cbb.getEndTime()+" 23:59:59");

        }
        if(ObjectUtils.isNotNullAndEmpty(cbb.getStrTime())){
            cbb.setStrTime(cbb.getStrTime()+" 00:00:00");

        }
        
        IPage<St4ScsCbb> cbbList = st4ScsCbbMapper.listReserveData(page,cbb );
        
        Result result = new Result();
        result.setRows(cbbList.getRecords());
        result.setTotal((int)cbbList.getTotal());
        result.setPage((int)cbbList.getPages());
        return ResultVOUtil.success(result);
    }
}
