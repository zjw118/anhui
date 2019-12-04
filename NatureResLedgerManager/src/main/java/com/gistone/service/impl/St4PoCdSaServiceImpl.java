package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.VO.ResultVO;
import com.gistone.entity.*;
import com.gistone.mapper.*;
import com.gistone.service.ISt4PoCdSaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.service.ISt4PoClCoService;
import com.gistone.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 核查点分配记录表 服务实现类
 * </p>
 *
 * @author zjw
 * @since 2019-08-15
 */
@Service
public class St4PoCdSaServiceImpl extends ServiceImpl<St4PoCdSaMapper, St4PoCdSa> implements ISt4PoCdSaService {
    @Autowired
    private St4PoCdSaMapper st4PoCdSaMapper;
    @Autowired
    private ISt4PoCdSaService st4PoCdSaService;
    @Autowired
    private St4SysSaMapper st4SysSaMapper;
    @Autowired
    private St4ScsCdMapper st4ScsCdMapper;


    @Override
    public ResultVO givePoint(List<Integer> uids, List<Integer> pointList,Integer taskId) {
        /**
         * 这里的业务逻辑是这样的:
         * 这里是按照任务走的，一个人对于同样一个斑块是可以下发两次的，app端也是可以同步到2个的因为是不同的任务下
         *
         *
         * **/

      //  List<St4ScsCd> cds= st4ScsCdMapper.getSpotByTaskId(taskId);

        List<St4PoCdSa> existsaList = null;
        St4PoCdSa saSg=null;
        boolean flag=true;
        //准备判断这个点是否被重新复下发给同一个人
        QueryWrapper<St4PoCdSa> sasgWrapper=null;
        for (Integer rid:pointList) {
            List<St4PoCdSa> list = new ArrayList<>();
            existsaList = new ArrayList<>();
            sasgWrapper = new QueryWrapper<>();  //关联表
            sasgWrapper.eq("CD001",rid);
            sasgWrapper.eq("CL001",taskId);
            //查询到这个点下发了没，及已经下发给了谁
            existsaList = st4PoCdSaMapper.selectList(sasgWrapper);
            List<Integer> ridExist=null;
            if(existsaList!=null&&existsaList.size()>0){
                ridExist = new ArrayList<>();
                //要得到已经拿到了这个点的人的集合
                if(existsaList.size()>0){
                    for (St4PoCdSa sasg:existsaList) {
                        ridExist.add(sasg.getSa001());
                    }
                }
                //
                if(ridExist!=null&&ridExist.size()>0){
                    for (Integer reid:uids) {
                        //如果人的集合里面没有包含传递进来的人就把这个人加进去 就得到了最新的应该拿到这个点的人的集合
                        if(!ridExist.contains(reid)){
                            ridExist.add(reid);
                        }
                    }
                }
                sasgWrapper = new QueryWrapper<>();  //关联表
                sasgWrapper.eq("CL001",taskId);
                //删除这个点原来的下发信息 准备进行最新一批人下发当前点的操作
                if(st4PoCdSaMapper.delete(sasgWrapper)>0){
                    if(ridExist!=null&&ridExist.size()>0){
                        for (Integer rids:ridExist) {
                            saSg = new St4PoCdSa();
                            saSg.setSa001(rids);
                            saSg.setCd001(rid);
                            saSg.setCl001(taskId);
                            list.add(saSg);
                        }
                    }
                    if(!st4PoCdSaService.saveBatch(list)){
                        flag=false;
                        break;
                    }
                }
            }else {
                //说明这个点还没有被下发过 就可以直接进行分配下发操作即可
                if(uids!=null&&uids.size()>0){
                    for (Integer uid:uids) {
                        saSg = new St4PoCdSa();
                        saSg.setSa001(uid);
                        saSg.setCd001(rid);
                        saSg.setCl001(taskId);
                        list.add(saSg);
                    }
                }
                if(!st4PoCdSaService.saveBatch(list)){
                    flag=false;
                    break;
                }
            }

        }



        if(flag){
                QueryWrapper<St4SysSa> wrapper = new QueryWrapper<>();
                wrapper.in("sa001",uids);
                List<St4SysSa> saList = st4SysSaMapper.selectList(wrapper);
                for (St4SysSa saa:saList) {
                    try{
                        JPushUtil.jiGuangPush(saa.getSa012(), "您有新的问题斑块点需要接收！","1");
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

            return ResultVOUtil.success();
        }else {
            return ResultVOUtil.error(ResultEnum.HANDLEFAIL.getCode(), "服务器异常");
        }
    }
}
