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
    private St4ScsCdMapper iterpretationMapper;


    @Override
    public ResultVO givePoint(List<Integer> uids, Integer taskId) {
        /**
         * 这里的业务逻辑是这样的:
         * 1.拿到传递过来的任务id去找到对应的台账(可能是多个)
         * 2.在拿每一个台账的id去拿斑点的的id
         * 3.最终是把斑点下发到人员
         */
        /**
         * 这里逻辑改变因为cd表中有groupId做关联所以之前的关联表cd_co没有用了
         *
         */

        List<St4ScsCd> cds= iterpretationMapper.getSpotByTaskId(taskId);
        if(cds==null||cds.size()<1){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "由于此任务下的台账无绑定的斑点信息,下发失败！");
        }

        List<Integer> pointList = cds.stream().map(St4ScsCd::getCd001).collect(Collectors.toList());
        List<St4PoCdSa> existsaList = null;
        St4PoCdSa saSg=null;
        boolean flag=true;
        //准备判断这个点是否被重新复下发给同一个人
        for (Integer rid:pointList) {
            List<St4PoCdSa> list = new ArrayList<>();
            existsaList = new ArrayList<>();
            QueryWrapper<St4PoCdSa> sasgWrapper = new QueryWrapper<>();  //关联表
            sasgWrapper.eq("CD001",rid);
            existsaList = st4PoCdSaMapper.selectList(sasgWrapper);
            List<Integer> ridExist=null;
            if(existsaList!=null&&existsaList.size()>0){
                ridExist = new ArrayList<>();

                if(existsaList.size()>0){
                    for (St4PoCdSa sasg:existsaList) {
                        ridExist.add(sasg.getSa001());
                    }
                }
                if(ridExist!=null&&ridExist.size()>0){
                    for (Integer reid:uids) {
                        if(!ridExist.contains(reid)){
                            ridExist.add(reid);
                        }
                    }
                }
                if(st4PoCdSaMapper.delete(sasgWrapper)>0){
                    if(ridExist!=null&&ridExist.size()>0){
                        for (Integer rids:ridExist) {
                            saSg = new St4PoCdSa();
                            saSg.setSa001(rids);
                            saSg.setCd001(rid);
                            list.add(saSg);
                        }
                    }
                    if(!st4PoCdSaService.saveBatch(list)){
                        flag=false;
                        break;
                    }
                }
            }else {
                if(uids!=null&&uids.size()>0){
                    for (Integer uid:uids) {
                        saSg = new St4PoCdSa();
                        saSg.setSa001(uid);
                        saSg.setCd001(rid);
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
                /*for (SysUser saa:saList) {
                    JPushUtil.jiGuangPush(saa.getSa012(), "您有新的问题点需要接收！","1");
                }*/

            return ResultVOUtil.success();
        }else {
            return ResultVOUtil.error(ResultEnum.HANDLEFAIL.getCode(), "服务器异常");
        }
    }
}
