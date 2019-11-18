package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gistone.VO.ResultVO;
import com.gistone.entity.St4ScsCba;
import com.gistone.mapper.St4ScsCbaMapper;
import com.gistone.service.ISt4ScsCbaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.util.ObjectUtils;
import com.gistone.util.Result;
import com.gistone.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 生态保护红线陆地边界数据表 服务实现类
 * </p>
 *
 * @author zjw
 * @since 2019-11-16
 */
@Service
public class St4ScsCbaServiceImpl extends ServiceImpl<St4ScsCbaMapper, St4ScsCba> implements ISt4ScsCbaService {
    @Autowired
    private  St4ScsCbaMapper st4ScsCbaMapper;


    @Override
    public ResultVO listRedLineLedger(St4ScsCba cba){
        Page<St4ScsCba> page = new Page<>(cba.getPageNumber(),cba.getPageSize());
        if(ObjectUtils.isNotNullAndEmpty(cba.getEndTime())){
            cba.setEndTime(cba.getEndTime()+" 23:59:59");

        }
        if(ObjectUtils.isNotNullAndEmpty(cba.getStrTime())){
            cba.setStrTime(cba.getStrTime()+" 00:00:00");

        }
        IPage<St4ScsCba> cbaList = st4ScsCbaMapper.listRedLineLedger(page,cba );
        Result result = new Result();
        result.setRows(cbaList.getRecords());
        result.setTotal((int)cbaList.getTotal());
        result.setPage((int)cbaList.getPages());
        return ResultVOUtil.success(result);
    }
}
