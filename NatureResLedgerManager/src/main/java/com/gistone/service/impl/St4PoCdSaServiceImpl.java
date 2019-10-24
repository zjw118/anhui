package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.entity.*;
import com.gistone.mapper.*;
import com.gistone.service.ISt4PoCdSaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.service.ISt4PoClCoService;
import com.gistone.util.JPushUtil;
import com.gistone.util.Result;
import com.gistone.util.ResultCp;
import com.gistone.util.ResultMsg;
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
    private St4ScsCdMapper st4ScsCdMapper;
    @Autowired
    private St4ScsClMapper st4ScsClMapper;
    @Autowired
    private St4PoClCoMapper st4PoClCoMapper;

    @Autowired
    private ISt4PoClCoService st4PoClCoService;
    @Autowired
    private St4PoCdCoMapper st4PoCdCoMapper;

    @Autowired
    private ISt4PoCdSaService st4PoCdSaService;

    @Autowired
    private SysUserMapper st4SysSaMapper;
    @Override
    public ResultCp deleteTaskLedger(List<Integer> ledgerIdList, Integer taskId) {

        ResultCp resultCp = new ResultCp();
        resultCp.setStatus(1000);

        QueryWrapper<St4PoClCo> clCoQueryWrapper = new QueryWrapper<>();
        //这里把之前绑定的旧的台账删除，然后把新的在赋上
        clCoQueryWrapper.eq("cl001",taskId);
        if(st4PoClCoMapper.delete(clCoQueryWrapper)<1){
            return ResultCp.build(1003,ResultMsg.MSG_1003);
        }
        resultCp.setMsg("删除成功");
        return resultCp;

    }
    @Override
    public ResultCp updateTaskLedger(List<Integer> ledgerIdList, Integer taskId) {
        QueryWrapper<St4PoClCo> clCoQueryWrapper = new QueryWrapper<>();
        //这里把之前绑定的旧的台账删除，然后把新的在赋上
        clCoQueryWrapper.eq("cl001",taskId);
        if(st4PoClCoMapper.delete(clCoQueryWrapper)<1){
            return ResultCp.build(1003,ResultMsg.MSG_1003);
        }

        ResultCp resultCp = new ResultCp();
        List<St4PoClCo> clcoList = new ArrayList<>();
        St4PoClCo clco = null;
        for (Integer lid:ledgerIdList) {
            clco = new St4PoClCo();
            clco.setCl001(taskId);
            clco.setCo001(lid);
            clcoList.add(clco);
        }
        if(!st4PoClCoService.saveBatch(clcoList)){
            resultCp.setStatus(1003);
            resultCp.setMsg(ResultMsg.MSG_1003);
            return resultCp;
        }
        resultCp.setStatus(1000);
        resultCp.setMsg("修改"+ResultMsg.MSG_1000);
        return resultCp;
    }
    @Override
    public ResultCp taskLedger(List<Integer> ledgerIdList, Integer taskId) {
        ResultCp resultCp = new ResultCp();
        List<St4PoClCo> clcoList = new ArrayList<>();
        St4PoClCo clco = null;
        for (Integer lid:ledgerIdList) {
            clco = new St4PoClCo();
            clco.setCl001(taskId);
            clco.setCo001(lid);
            clcoList.add(clco);
        }
        if(!st4PoClCoService.saveBatch(clcoList)){
            resultCp.setStatus(1003);
            resultCp.setMsg(ResultMsg.MSG_1003);
            return resultCp;
        }
        resultCp.setStatus(1000);
        resultCp.setMsg("任务绑定台账"+ResultMsg.MSG_1000);
        return resultCp;
    }

    @Override
    public ResultCp givePoint(List<Integer> uids, Integer taskId) {
        /**
         * 这里的业务逻辑是这样的:
         * 1.拿到传递过来的任务id去找到对应的台账(可能是多个)
         * 2.在拿每一个台账的id去拿斑点的的id
         * 3.最终是把斑点下发到人员
         */
        List<St4ScsCd> cds= st4ScsCdMapper.getSpotByTaskId(taskId);
        if(cds==null||cds.size()<1){
            return ResultCp.build(1001,"由于此任务下的台账无绑定的斑点信息,下发失败");
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
                QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
                wrapper.in("id",uids);
                List<SysUser> saList = st4SysSaMapper.selectList(wrapper);
                /*for (SysUser saa:saList) {
                    JPushUtil.jiGuangPush(saa.getSa012(), "您有新的问题点需要接收！","1");
                }*/

            return ResultCp.build(1000,"任务下发"+ResultMsg.MSG_1000);
        }
        return ResultCp.build(1005,ResultMsg.MSG_1005);
    }
}
