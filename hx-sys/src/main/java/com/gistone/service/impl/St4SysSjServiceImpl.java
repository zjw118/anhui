package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gistone.entity.*;
import com.gistone.mapper.St4PoSaSjMapper;
import com.gistone.mapper.St4SysSjMapper;
import com.gistone.service.ISt4SysSjService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.util.Result;
import com.gistone.util.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjw
 * @since 2019-08-28
 */
@Service
public class St4SysSjServiceImpl extends ServiceImpl<St4SysSjMapper, St4SysSj> implements ISt4SysSjService {
    @Autowired
    private St4SysSjMapper st4SysSjMapper;
    @Autowired
    private St4PoSaSjMapper st4PoSaSjMapper;

    @Autowired
    private ISt4SysSjService st4SysSjService;


    @Override
    public Result listUnit(St4SysSj sj, St4SysSa seUser) {

        QueryWrapper<St4SysSj> wrapper = new QueryWrapper<>();
        wrapper.eq("SJ003",1);
        if(sj.getSj002()!=null){
            wrapper.like("SJ002",sj.getSj002());
        }
        wrapper.eq("SJ005",seUser.getSa001());
        Page<St4SysSj> page = new Page<>(sj.getPageNumber(),sj.getPageSize());
        IPage<St4SysSj> ipage = st4SysSjMapper.selectPage(page,wrapper);

        Result result = new Result();
        result.setStatus(1000);
        result.setMsg("加载成功");
        result.setPage((int)ipage.getPages());
        result.setTotal((int)ipage.getTotal());
        result.setRows(ipage.getRecords());

        return result;
    }

    @Override
    public Result listUnitNoLimit(St4SysSj sj, St4SysSa seUser) {

        QueryWrapper<St4SysSj> wrapper = new QueryWrapper<>();
        wrapper.eq("SJ003",1);
        wrapper.eq("SJ007",0);
        wrapper.eq("SJ005",seUser.getSa001());
        List<St4SysSj> list = st4SysSjMapper.selectList(wrapper);

        Result result = new Result();
        result.setStatus(1000);
        result.setMsg("加载成功");
        result.setData(list);

        return result;
    }

    @Override
    public Result insertUnit(St4SysSj sj, St4SysSa seUser) {
        sj.setSj005(seUser.getSa001());
        QueryWrapper<St4PoSaSj> wrapper = new QueryWrapper<>();
        wrapper.eq("SA001",seUser.getSa001());
        List<St4PoSaSj> unitList = st4PoSaSjMapper.selectList(wrapper);
        if(unitList.size()>0){
            /**
             *这里是设置单位的父单位的id如果没有则是空
             * 特别指出这里的每个人对应的单位只能有一个，但是单位是可以有父单位的
             * 比如陈真属于天津规划局单位，但是天津规划局属于天津管理局。陈真就间接属于天津管理局
             * 但是在用户和单位的关联表中是没有记录的只会有陈真和天津规划局的关联记录
             */
            sj.setSj008(unitList.get(0).getSj001());
        }
        int inNum = st4SysSjMapper.insert(sj);
        if(inNum>0){
           /* Integer sjId = sj.getSj001();
            List<Integer> bidList = sj.getBidList();
            St4PoSgSj sgsj = null;
            List<St4PoSgSj> sgSjList = new ArrayList<>();
            for (Integer bid:bidList) {
                sgsj = new St4PoSgSj();
                sgsj.setSg001(bid);
                sgsj.setSj001(sjId);
                sgSjList.add(sgsj);
            }
            boolean flag = st4PoSgSjService.saveBatch(sgSjList);
            if(!flag){
                return Result.build(1003, ResultMsg.MSG_1003);
            }
           */
            return Result.build(1000, "添加"+ResultMsg.MSG_1000);
        }else{
            return Result.build(1003, ResultMsg.MSG_1003);
        }
    }

    @Override
    public Result updateUnit(St4SysSj sj, St4SysSa seUser) {
        sj.setSj005(seUser.getSa001());
        int inNum = st4SysSjMapper.updateById(sj);
        if(inNum>0){
           /* QueryWrapper<St4PoSgSj> wrapper = new QueryWrapper<>();
            wrapper.eq("SJ001",sj.getSj001());
            st4PoSgSjMapper.delete(wrapper);
            Integer sjId = sj.getSj001();
            List<Integer> bidList = sj.getBidList();
            St4PoSgSj sgsj = null;
            List<St4PoSgSj> sgSjList = new ArrayList<>();
            for (Integer bid:bidList) {
                sgsj = new St4PoSgSj();
                sgsj.setSg001(bid);
                sgsj.setSj001(sjId);
                sgSjList.add(sgsj);
            }
            boolean flag = st4PoSgSjService.saveBatch(sgSjList);
            if(!flag){
                return Result.build(1003, ResultMsg.MSG_1003);
            }
           */
            return Result.build(1000, "修改"+ResultMsg.MSG_1000);
        }else{
            return Result.build(1003, ResultMsg.MSG_1003);
        }
    }

    @Override
    public Result deleteUnit(St4SysSj sj, St4SysSa seUser) {
        int inNum = st4SysSjMapper.deleteById(sj);
        if(inNum>0){

            return Result.build(1000, "修改"+ResultMsg.MSG_1000);
        }else{
            return Result.build(1003, ResultMsg.MSG_1003);
        }
    }

    @Override
    public Result getUnitDetail(St4SysSj sj) {
        Result result = new Result();
        St4SysSj sjj = st4SysSjMapper.getUnitDetail(sj);
        sj.setSgList(st4SysSjMapper.getUnitResData(sj));
        result.setStatus(1000);
        result.setMsg("加载成功");
        result.setData(sj);

        return result;
    }
}
