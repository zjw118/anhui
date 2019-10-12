package com.gistone.bjhky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.bjhky.entity.St4PoSaCl;
import com.gistone.bjhky.entity.St4ScsCl;
import com.gistone.bjhky.entity.St4SysSa;
import com.gistone.bjhky.mapper.St4PoSaClMapper;
import com.gistone.bjhky.mapper.St4ScsClMapper;
import com.gistone.bjhky.mapper.St4SysSaMapper;
import com.gistone.bjhky.service.ISt4PoSaClService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.bjhky.util.JPushUtil;
import com.gistone.bjhky.util.Result;
import com.gistone.bjhky.util.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 巡护任务表 服务实现类
 * </p>
 *
 * @author zjw
 * @since 2019-08-15
 */
@Service
public class St4PoSaClServiceImpl extends ServiceImpl<St4PoSaClMapper, St4PoSaCl> implements ISt4PoSaClService {
    @Autowired
    private St4ScsClMapper st4ScsClMapper;
    @Autowired
    private St4PoSaClMapper st4PoSaClMapper;
    @Autowired
    private St4SysSaMapper st4SysSaMapper;
    @Override
    public Result insertList  (List<Integer> uidList,List<Integer> taskList) {
        St4PoSaCl spsl = null;
        List<St4PoSaCl> spList = new ArrayList<>();
        if(uidList!=null&&uidList.size()>0&&taskList!=null&&taskList.size()>0){

            for (Integer uid:uidList) {

                for (Integer taskId:taskList) {
                    spsl = new St4PoSaCl();
                    spsl.setCl001(taskId);
                    spsl.setSa001(uid);
                    spList.add(spsl);
                }

            }
            int inNum =  st4PoSaClMapper.insertList(spList);
            if(inNum>0){
                QueryWrapper<St4SysSa> wrapper = new QueryWrapper<>();
                wrapper.in("SA001",uidList);
                List<St4SysSa> list =st4SysSaMapper.selectList(wrapper);
                for (St4SysSa sss:list) {
                    JPushUtil.jiGuangPush(sss.getSa012(), "您有新的任务需要接收！","1");
                }

                return  Result.build(1000,"分配"+ ResultMsg.MSG_1000);
            }else{
                return  Result.build(1006,"分配"+ ResultMsg.MSG_1006);
            }
        }
        return Result.build(1005,"分配"+ ResultMsg.MSG_1005);
    }

    @Override
    public Result sysTaskData(Integer uid) {


        QueryWrapper<St4PoSaCl> wrapper = new QueryWrapper<>();
        wrapper.eq("SA001",uid);
        List<St4PoSaCl> psalist = st4PoSaClMapper.selectList(wrapper);
        List<Integer> taskIdList = new ArrayList<>();
        if(psalist!=null&&psalist.size()>0){

            for (St4PoSaCl sps:psalist) {
                taskIdList.add(sps.getCl001());
            }
        }
        List<St4ScsCl> list=new ArrayList<>();
        if(taskIdList.size()>0){
            QueryWrapper<St4ScsCl> ssc = new QueryWrapper<>();
            ssc.in("CL001",taskIdList);
            list = st4ScsClMapper.selectList(ssc);
        }

        Result result = new Result();
        result.setStatus(1000);
        result.setData(list);
        result.setMsg("任务同步"+ResultMsg.MSG_1000);
        return result;
    }
}
