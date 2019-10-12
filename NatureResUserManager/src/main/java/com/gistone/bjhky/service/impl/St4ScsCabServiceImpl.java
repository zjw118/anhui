package com.gistone.bjhky.service.impl;

import com.gistone.bjhky.entity.St4ScsCab;
import com.gistone.bjhky.mapper.St4ScsCabMapper;
import com.gistone.bjhky.service.ISt4ScsCabService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.bjhky.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjw
 * @since 2019-09-02
 */
@Service
public class St4ScsCabServiceImpl extends ServiceImpl<St4ScsCabMapper, St4ScsCab> implements ISt4ScsCabService {

    @Autowired
    private St4ScsCabMapper st4ScsCabMapper;

    @Override
    public Result listForApp(St4ScsCab data) {
        int page = (data.getPageNumber() / data.getPageSize()) + 1;//当前页码
        data.setPageSize(data.getPageSize());
        data.setPageNumber((data.getPageNumber()-1)*data.getPageSize());
        List<St4ScsCab> res = st4ScsCabMapper.listForApp(data);
        //int total = st4ScsCabMapper.total(data);

        Result result = new Result();
        result.setData(res);
        //result.setTotal(total);
        result.setPage(page);
        result.setStatus(1000);
        return result;
    }

    @Override
    public Result getByIdForApp(St4ScsCab data) {
        St4ScsCab res = st4ScsCabMapper.getByIdForApp(data);
        Result result = new Result();
        result.setData(res);
        //result.setTotal(total);
        result.setStatus(1000);
        return result;
    }
}
