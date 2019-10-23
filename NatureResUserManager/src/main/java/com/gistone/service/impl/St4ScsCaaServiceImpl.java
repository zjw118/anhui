package com.gistone.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.St4ScsCaa;
import com.gistone.mapper.St4ScsCaaMapper;
import com.gistone.service.ISt4ScsCaaService;
import com.gistone.util.Result;
import com.gistone.util.ResultCp;
import com.gistone.util.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LiuXiong
 * @since 2019-08-27
 */
@Service
public class St4ScsCaaServiceImpl extends ServiceImpl<St4ScsCaaMapper, St4ScsCaa> implements ISt4ScsCaaService {

    @Autowired
    private St4ScsCaaMapper st4ScsCaaMapper;

    @Override
    public ResultCp getNewVersion(St4ScsCaa data) {
        St4ScsCaa res = st4ScsCaaMapper.getNewVersion(data);
        ResultCp result = new ResultCp();
        //如果数据库的版本大于app当前版本，则说明有新版本
        if(res.getCaa003() > data.getCaa003()){
            result.setData(res);
            result.setMsg("查询app最新版本成功");
            result.setStatus(1000);
        }else{
            result.setMsg(ResultMsg.MSG_1013);
            result.setStatus(1013);
        }

        return result;
    }
}
