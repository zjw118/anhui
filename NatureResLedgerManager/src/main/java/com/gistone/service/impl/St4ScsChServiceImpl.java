package com.gistone.service.impl;

import com.gistone.entity.St4ScsCh;
import com.gistone.mapper.St4ScsChMapper;
import com.gistone.service.ISt4ScsChService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 巡查人员实时位置表 服务实现类
 * </p>
 *
 * @author zjw
 * @since 2019-08-22
 */
@Service
public class St4ScsChServiceImpl extends ServiceImpl<St4ScsChMapper, St4ScsCh> implements ISt4ScsChService {
    @Autowired
    private St4ScsChMapper st4ScsChMapper;
    @Override
    public Result getPersonNumber() {

        List<Map<String,String>> map = st4ScsChMapper.getPersonNumber();

        Result result = new Result();
        result.setStatus(1000);
        result.setMsg("加载成功");
        result.setData(map);

        return result;
    }
}
