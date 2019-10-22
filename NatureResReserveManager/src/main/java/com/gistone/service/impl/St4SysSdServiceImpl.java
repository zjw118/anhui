package com.gistone.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.St4SysSd;
import com.gistone.mapper.St4SysSdMapper;
import com.gistone.service.ISt4SysSdService;
import org.springframework.stereotype.Service;

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
