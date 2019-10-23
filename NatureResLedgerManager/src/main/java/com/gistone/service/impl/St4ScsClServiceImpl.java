package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.St4ScsCl;
import com.gistone.entity.St4SysSa;
import com.gistone.entity.SysUser;
import com.gistone.mapper.St4ScsClMapper;
import com.gistone.mapper.St4SysSaMapper;
import com.gistone.mapper.SysUserMapper;
import com.gistone.service.ISt4ScsClService;
import com.gistone.util.Result;
import com.gistone.util.ResultCp;
import com.gistone.util.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 任务批次表 服务实现类
 * </p>
 *
 * @author LiuXiong
 * @since 2019-08-14
 */
@Service
public class St4ScsClServiceImpl extends ServiceImpl<St4ScsClMapper, St4ScsCl> implements ISt4ScsClService {

    @Autowired
    private St4ScsClMapper st4ScsClMapper;

    @Autowired
    private St4SysSaMapper st4SysSaMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public Result listForView(St4ScsCl data) {
        QueryWrapper<St4ScsCl> queryWrapper = new QueryWrapper<>();
        List<St4ScsCl> list = st4ScsClMapper.selectList(queryWrapper);
        Result res = new Result();
        res.setData(list);
        res.setStatus(1000);
        res.setMsg("查询问题点任务批次成功");
        return res;
    }

    @Override
    public ResultCp listTask(St4ScsCl data, SysUser seUser) {
        data.setCl013(seUser.getId());
        if(seUser.getId()==1){
            data.setType(2);
        }else{
            if(seUser.getSA020()==0){
                //代表是管理员权限
                data.setType(1);
            }else {
                data.setType(0);
            }
        }


        Page<St4ScsCl>  page = new Page<>(data.getPageNumber(),data.getPageSize());
        IPage<St4ScsCl> ipage = st4ScsClMapper.listTask(page,data);
        ResultCp res = new ResultCp();
        res.setStatus(1000);
        res.setMsg("加载"+ ResultMsg.MSG_1000);
        res.setRows(ipage.getRecords());;
        res.setPage((int)ipage.getPages());
        res.setTotal((int)ipage.getTotal());
        return res;
    }
}
