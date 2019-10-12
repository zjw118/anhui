package com.gistone.bjhky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.bjhky.entity.*;

import com.gistone.bjhky.service.*;
import com.gistone.bjhky.util.Result;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName DestinationsManagerService
 * @Description TODO
 * @Author xxh
 * @Date 2019/8/15 9:09
 * @Version 1.0
 **/
@Service
public class DestinationsManagerServiceImpl implements IDestinationsManagerService {


    @Autowired
    private ISt4ScsCcService ccService;

    @Autowired
    private ISt4ScsCkService ckService;
    @Autowired
    private ISt4ScsCnService cnService;

    @Autowired
    private ISt4ScsCfService cfService;

    @Autowired
    private ISt4ScsCeService ceService;

    @Autowired
    private ISt4ScsCyService cyService;

    @Autowired
    private ISt4ScsCgService cgService;


    @Override
    @Transactional
    public Result insertDestinationsManager(List<St4ScsCc> ccList, List<St4ScsCk> ckList,
                                            List<St4ScsCf> cfList, List<St4ScsCe> ceList) {
        //先判断基础信息表中的所有cc002是否存在，都不存在的情况下添加
        List<String> ccoo2s = ccList.stream().map(St4ScsCc::getCc002).collect(Collectors.toList());
        QueryWrapper<St4ScsCc> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CC002", ccoo2s);
        if (ccService.count(queryWrapper) > 0) {
            return Result.build(1001, "添加航点重复");
        }
        List<String> sailNames = ccList.stream().map(St4ScsCc::getCc012).collect(Collectors.toList());
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CC012", sailNames);
        if (ccService.count(queryWrapper) > 0) {
            return Result.build(1001, "航点名称不能重复");
        }

        Boolean cc = ccService.saveBatch(ccList);
        if (!cc) {
            new RuntimeException("插入数据库航点信息错误");
        }
        Boolean ck = ckService.saveBatch(ckList);

        if (!ck) {
            new RuntimeException("插入数据库航点信息错误");
        }else {
            St4ScsCn cnn = null;
            List<St4ScsCn> cnList = new ArrayList<>();
            if(ckList!=null&&ckList.size()>0){
                for (St4ScsCk ck1:ckList) {
                    String repairProcess=ck1.getRepairProcess()== null?"":ck1.getRepairProcess();
                    if(ck1.getPhos()!=null){
                        List<String> urls = ck1.getPhos();
                        for (String phourl:urls) {
                            cnn = new St4ScsCn();
                            cnn.setCn010(phourl);
                            cnn.setCn004(repairProcess);
                            cnList.add(cnn);
                        }
                        if(!cnService.saveBatch(cnList)){
                            new RuntimeException("插入数据库入整改照片及进展信息错误");
                        };
                    }
                }
            }
        }
        Boolean cf = cfService.saveBatch(cfList);
        if (!cf) {
            new RuntimeException("插入数据库入航点信息错误");
        }
        Boolean ce = ceService.saveBatch(ceList);
        if (!ce) {
            new RuntimeException("插入数据库航点信息错误");
        }
        return Result.build(1000, "添加成功");
    }

    @Override
    @Transactional
    public Result insertDestinationsRecordManager(St4ScsCy scsCy, List<St4ScsCg> st4ScsCgs, List<St4ScsCc> ccList, List<St4ScsCk> ckList, List<St4ScsCf> cfList, List<St4ScsCe> ceList) {
        QueryWrapper<St4ScsCy> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CY017", scsCy.getCy017());
        Integer cyCount = cyService.count(queryWrapper);
        if (cyCount > 0) {
            return Result.build(1001, "添加巡护重复");
        }
        //先判断基础信息表中的所有cc002是否存在，都不存在的情况下添加
        if(ccList!=null&&ccList.size()>0){
            List<String> ccoo2s = ccList.stream().map(St4ScsCc::getCc002).collect(Collectors.toList());
            QueryWrapper<St4ScsCc> queryWrapperCC = new QueryWrapper<>();
            queryWrapperCC.in("CC002", ccoo2s);
            if (ccService.count(queryWrapperCC) > 0) {
                return Result.build(1001, "添加航点重复");
            }
        }
        Boolean cy = cyService.save(scsCy);
        if (!cy) {
            new RuntimeException("插入数据库巡护信息错误");
        }
        Boolean cg = cgService.saveBatch(st4ScsCgs);
        if (!cg) {
            new RuntimeException("插入数据库巡护路段信息错误");
        }
        Boolean cc = ccService.saveBatch(ccList);
        if (!cc) {
            new RuntimeException("插入数据库航点信息错误");
        }
        Boolean ck = ckService.saveBatch(ckList);
        if (!ck) {
            new RuntimeException("插入数据库航点信息错误");
        }
        Boolean cf = cfService.saveBatch(cfList);
        if (!cf) {
            new RuntimeException("插入数据库入航点信息错误");
        }
        Boolean ce = ceService.saveBatch(ceList);
        if (!ce) {
            new RuntimeException("插入数据库航点信息错误");
        }
        return Result.build(1000, "添加成功");
    }


}
