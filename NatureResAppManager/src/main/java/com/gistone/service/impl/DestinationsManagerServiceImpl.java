package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.entity.*;
import com.gistone.mapper.St4ScsCeMapper;
import com.gistone.service.*;
import com.gistone.util.FileUtil;
import com.gistone.util.ReadJson;
import com.gistone.util.ResultCp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

    @Autowired
    private St4ScsCeMapper st4ScsCeMapper;

    @Autowired
    private  MessageProperties config;

//    @Autowired
//    private Config

    @Override
    @Transactional
    public ResultCp insertDestinationsManager(List<St4ScsCc> ccList, List<St4ScsCk> ckList,
                                            List<St4ScsCf> cfList, List<St4ScsCe> ceList) {
        //先判断基础信息表中的所有cc002是否存在，都不存在的情况下添加
        List<String> ccoo2s = ccList.stream().map(St4ScsCc::getCc002).collect(Collectors.toList());
        /*QueryWrapper<St4ScsCc> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CC002", ccoo2s);
        if (ccService.count(queryWrapper) > 0) {
            return ResultCp.build(1001, "添加航点重复");
        }
        List<String> sailNames = ccList.stream().map(St4ScsCc::getCc012).collect(Collectors.toList());
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CC012", sailNames);
        if (ccService.count(queryWrapper) > 0) {
            return ResultCp.build(1001, "航点名称不能重复");
        }*/

        Boolean cc = ccService.saveBatch(ccList);
        if (!cc) {
            new RuntimeException("插入数据库航点信息错误");
        }
        Boolean ck = ckService.saveBatch(ckList);

        //这里是新加的需求
        //移动端提交上来之后要往cbd里面插入数据
        Set<String> cbds = new HashSet<>();
        cbds = ckList.stream().map(St4ScsCk::getCd004).collect(Collectors.toSet());
        List<St4ScsCbd> cbdList = new ArrayList<>();
        St4ScsCbd cbd=null;

        for (St4ScsCk ck1:ckList) {
            cbd = new St4ScsCbd();
            cbd.setCbd002(ck1.getCk049());
            cbd.setCbd004(Integer.valueOf(ck1.getCd004()));
            cbd.setCbd003(LocalDateTime.now());
            cbd.setCbd005("提交了关于“");
            cbd.setCbd006("”问题斑块的核查信息");
        }
        if (!ck) {
            new RuntimeException("插入数据库航点信息错误");
        }else {
            St4ScsCn cnn = null;
            List<St4ScsCn> cnList = new ArrayList<>();
            if(ckList!=null&&ckList.size()>0){
                for (St4ScsCk ck1:ckList) {
                    String repairProcess=ck1.getCn010()== null?"":ck1.getCn010();
                    if(!(ck1.getCn010()==null&&ck1.getCn004()==null)){
                        List<String> urls = ck1.getCn004();
                        if(urls!=null&&urls.size()>0){
                            for (String phourl:urls) {
                                cnn = new St4ScsCn();
                                cnn.setCn004(phourl);
                                cnn.setCn010(repairProcess);
                                //这里插入台账表主键
                                cnn.setCk001(ck1.getCk001());
                                cnList.add(cnn);
                            }
                        }else{
                            cnn = new St4ScsCn();
                            cnn.setCn010(repairProcess);
                            //这里插入台账表主键
                            cnn.setCk001(ck1.getCk001());
                            cnList.add(cnn);
                        }

                        if(!cnService.saveBatch(cnList)){
                            new RuntimeException("插入数据库入整改照片及进展信息错误");
                        };
                    }
                }
            }
        }
        //安徽没有巡护所以注释掉这里
        Boolean cf = cfService.saveBatch(cfList);
        if (!cf) {
            new RuntimeException("插入数据库入航点信息错误");
        }
        if(ceList!=null&&ceList.size()>0){
            QueryWrapper<St4ScsCe> ceQueryWrapper = new QueryWrapper<>();
            String sign = ceList.get(0).getCe002();
            ceQueryWrapper.eq("CE002", sign);
            List<St4ScsCe> ceExistList = st4ScsCeMapper.selectList(ceQueryWrapper);
            if(ceExistList!=null&&ceExistList.size()>0) {
                List<String> attachUrls = ceExistList.stream().map(St4ScsCe::getCe003).collect(Collectors.toList());
                for (String url:attachUrls) {
                    FileUtil.deleteFile(config.getUpPath()+url);
                }
                st4ScsCeMapper.delete(ceQueryWrapper);
            }
        }
        Boolean ce = ceService.saveBatch(ceList);
        if (!ce) {
            new RuntimeException("插入数据库航点信息错误");
        }

        return ResultCp.build(1000, "添加成功");
    }

    @Override
    @Transactional
    public ResultCp insertDestinationsRecordManager(St4ScsCy scsCy, List<St4ScsCg> st4ScsCgs, List<St4ScsCc> ccList, List<St4ScsCk> ckList, List<St4ScsCf> cfList, List<St4ScsCe> ceList) {
        QueryWrapper<St4ScsCy> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CY017", scsCy.getCy017());
        Integer cyCount = cyService.count(queryWrapper);
        // 年月日文件夹
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        DateTimeFormatter sdf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String baseDateDir = sdf.format(new Date());
        if (cyCount > 0) {
            return ResultCp.build(1001, "添加巡护重复");
        }

        //巡护人id
        scsCy.setCy012(scsCy.getSa001());
        Boolean cy = cyService.save(scsCy);
        if (!cy) {
            new RuntimeException("插入数据库巡护信息错误");
        }
        //遍历路段，将坐标转为json文件
        for(St4ScsCg da : st4ScsCgs){
            //将路段集合转成文件
            try{
                //将文件写在本地
                ReadJson.writeGeoJson(da.getCg003().split(","),config.getUpPath() + "/nr_line/" + baseDateDir + "/" + da.getCy017() +"_" +sdf2.format(da.getCg004()) + ".json");
                da.setCg003("/nr_line/" + baseDateDir + "/" + da.getCy017() +"_" + sdf2.format(da.getCg004()) + ".json");
            }catch (Exception e) {
                e.printStackTrace();
                return ResultCp.build(1003, "轨迹数据处理异常");
            }
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
        }else {
            St4ScsCn cnn = null;
            List<St4ScsCn> cnList = new ArrayList<>();
            if(ckList!=null&&ckList.size()>0){
                for (St4ScsCk ck1:ckList) {
                    String repairProcess=ck1.getCn010()== null?"":ck1.getCn010();
                    if(!(ck1.getCn010()==null&&ck1.getCn004()==null)){
                        List<String> urls = ck1.getCn004();
                        if(urls!=null&&urls.size()>0){
                            for (String phourl:urls) {
                                cnn = new St4ScsCn();
                                cnn.setCn004(phourl);
                                cnn.setCn010(repairProcess);
                                //这里插入台账表主键
                                cnn.setCk001(ck1.getCk001());
                                cnList.add(cnn);
                            }
                        }else{
                            cnn = new St4ScsCn();
                            cnn.setCn010(repairProcess);
                            //这里插入台账表主键
                            cnn.setCk001(ck1.getCk001());
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
        if(ceList!=null&&ceList.size()>0){
            QueryWrapper<St4ScsCe> ceQueryWrapper = new QueryWrapper<>();
            String sign = ceList.get(0).getCe002();
            ceQueryWrapper.eq("CE002", sign);
            List<St4ScsCe> ceExistList = st4ScsCeMapper.selectList(ceQueryWrapper);
            if(st4ScsCeMapper.selectCount(ceQueryWrapper)>0) {
                List<String> attachUrls = ceExistList.stream().map(St4ScsCe::getCe003).collect(Collectors.toList());
                for (String url:attachUrls) {
                    FileUtil.deleteFile(config.getUpPath()+url);
                }
                st4ScsCeMapper.delete(ceQueryWrapper);
            }
        }
        Boolean ce = ceService.saveBatch(ceList);
        if (!ce) {
            new RuntimeException("插入数据库航点信息错误");
        }
        return ResultCp.build(1000, "添加成功");
    }


}
