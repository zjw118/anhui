package com.gistone.bjhky.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.bjhky.entity.St4SysSd;
import com.gistone.bjhky.mapper.St4SysSdMapper;
import com.gistone.bjhky.service.ISt4SysSdService;
import com.gistone.bjhky.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xxh
 * @since 2019-08-14
 */
@Service
public class St4SysSdServiceImpl extends ServiceImpl<St4SysSdMapper, St4SysSd> implements ISt4SysSdService {

/*    @Autowired
    private St4SysSdMapper st4SysSdMapper;

    @Override
    public Result listTreeToView() {
        List<St4SysSd> list = st4SysSdMapper.listTreeToView();
        Result result = new Result();
        result.setData(list);
        result.setMsg("级联查询问题点数据成功");
        result.setStatus(1000);
        return result;
    }*/
}
