package com.gistone.bjhky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.bjhky.entity.St4PoSaSg;
import com.gistone.bjhky.entity.St4SysSa;
import com.gistone.bjhky.mapper.St4PoSaSgMapper;
import com.gistone.bjhky.service.ISt4PoSaSgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.bjhky.util.Result;
import com.gistone.bjhky.util.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 巡查人员授权保护地数据关联表 服务实现类
 * </p>
 *
 * @author zjw
 * @since 2019-08-30
 */
@Service
public class St4PoSaSgServiceImpl extends ServiceImpl<St4PoSaSgMapper, St4PoSaSg> implements ISt4PoSaSgService {
    @Autowired
    private St4PoSaSgMapper st4PoSaSgMapper;
    @Autowired
    private ISt4PoSaSgService st4PoSaSgService;

    @Override
    public Result giveReserve(Integer uid, List<Integer> reserveList) {


        //首先删除掉当前人之前的全部授权的保护地信息要重新去授权
        QueryWrapper<St4PoSaSg> wrapper = new QueryWrapper<>();
        wrapper.eq("SA001",uid);
        int num = st4PoSaSgMapper.delete(wrapper);
        if(reserveList==null||reserveList.size()==0){
            return Result.build(1000,"授权保护地边界成功");
        }
        St4PoSaSg sasg = new St4PoSaSg();
        List<St4PoSaSg> list = new ArrayList<>();
        for (Integer rid:reserveList) {
            sasg = new St4PoSaSg();
            sasg.setSa001(uid);
            sasg.setSg001(rid);
            list.add(sasg);
        }
        if(st4PoSaSgService.saveBatch(list)){
            return Result.build(1000,"授权保护地边界成功");
        }else{
            return Result.build(1003,ResultMsg.MSG_1003);
        }
    }
}
