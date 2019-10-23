package com.gistone.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.entity.St4SysSc;
import com.gistone.mapper.St4SysScMapper;
import com.gistone.service.ISt4SysScService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 模块权限表 服务实现类
 * </p>
 *
 * @author zjw
 * @since 2019-08-28
 */
@Service
public class St4SysScServiceImpl extends ServiceImpl<St4SysScMapper, St4SysSc> implements ISt4SysScService {
    @Autowired
    private  St4SysScMapper st4SysScMapper;

    @Override
    public Result listModule(St4SysSc sc){


        QueryWrapper<St4SysSc> wrapper = new QueryWrapper<>();
        wrapper.eq("SC009",1);
        //List<St4SysSc> list = st4SysScMapper.selectList(wrapper);
        List<St4SysSc> list = st4SysScMapper.findAllRecursion();
        Result result = new Result();
        result.setData( buildTree(list,0));
        result.setStatus(1000);
        result.setMsg("加载成功");

        return result;

    }

    @Override
    public Result listModuleBySa001(Integer sa001) {
        List<St4SysSc> list = st4SysScMapper.listModuleBySa001(sa001);
        JSONArray model = new JSONArray();

        Result result = new Result();
        result.setData( buildTree(list,0));
//        result.setStatus(1000);
        result.setMsg("加载成功");

        return result;
    }

    public static List<St4SysSc> buildTree(List<St4SysSc> list, Integer fid) {
        List<St4SysSc> resultList = new ArrayList<St4SysSc>();
        if (list == null || list.size() == 0 || fid < 0) {
            return null;
        }
        for (St4SysSc tree : list) {
            if (tree.getSc003() == fid) {
                resultList.add(tree);
                tree.setChildren(buildTree(list, tree.getSc001()));
            }
        }
        return resultList;
    }







}
