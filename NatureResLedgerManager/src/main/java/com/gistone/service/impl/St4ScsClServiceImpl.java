package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.St4PoClCo;
import com.gistone.entity.St4ScsCl;
import com.gistone.entity.St4SysSa;
import com.gistone.entity.SysUser;
import com.gistone.mapper.*;
import com.gistone.service.ISt4PoClCoService;
import com.gistone.service.ISt4ScsClService;
import com.gistone.util.Result;
import com.gistone.util.ResultCp;
import com.gistone.util.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private ISt4ScsClService st4ScsClService;

    @Autowired
    private SysUserMapper st4SysSaMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private ISt4PoClCoService st4PoClCoService;
    @Autowired
    private St4PoCdCoMapper st4PoCdCoMapper;
    @Autowired
    private St4PoClCoMapper st4PoClCoMapper;

    @Override
    public ResultCp getTaskDetail(St4ScsCl data) {

        St4ScsCl list = st4ScsClMapper.getTaskDetail(data);
        ResultCp res = new ResultCp();
        res.setData(list);
        res.setStatus(1000);
        res.setMsg("查询成功");
        return res;
    }

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

      /*  QueryWrapper<St4ScsCl> wrapper = new QueryWrapper<>();
        Page<St4ScsCl>  page = new Page<>(data.getPageNumber(),data.getPageSize());
        wrapper.eq("CL012",1);
        wrapper.like("CL002",data.getCl002()==null?"":data.getCl002());
        wrapper.like("CL010",data.getCl010()==null?"":data.getCl010());
        wrapper.eq("CL013",seUser.getSa001());*/
       /* seUser = st4SysSaMapper.selectById(seUser);
        data.setCl013(seUser.getSa001());
        if(seUser.getSa001()==1){
=======
        data.setCl013(seUser.getId());
        if(seUser.getId()==1){
>>>>>>> 9fa8b2c89aba5ada1c610716367e0a5c6ec9be2f
            data.setType(2);
        }else{
            if(seUser.getSA020()==0){
                //代表是管理员权限
                data.setType(1);
            }else {
                data.setType(0);
            }
        }*/

        int size = data.getPageSize();//每页条数
        int number = data.getPageNumber();//开始索引
        int numberReal =0;
        if(0==number){
            numberReal = number;
        }else{
            numberReal= (number-1)*size;
        }
        data.setPageNumber(numberReal);
        data.setPageSize(size);
        List<St4ScsCl> list = st4ScsClMapper.listTask(data);
        data.setPageNumber(null);
        data.setPageSize(null);
        ResultCp res = new ResultCp();
        Integer tsize =st4ScsClMapper.listTask(data).size();
        res.setTotal(tsize);
        res.setStatus(1000);
        res.setMsg("加载"+ ResultMsg.MSG_1000);
        res.setRows(list);;
        res.setPage((int)Math.ceil((double)tsize/size));
        return res;
    }

    @Override
    public ResultCp insertTask(St4ScsCl data, SysUser seUser) {
        List<Integer> ledgerIdList = data.getLedgerIdList();
        ResultCp resultCp = new ResultCp();
        List<St4PoClCo> clcoList = new ArrayList<>();
        St4PoClCo clco = null;
        if(st4ScsClService.save(data)){
            Integer taskId =data.getCl001();
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
            resultCp.setMsg("创建"+ResultMsg.MSG_1000);
        }

        return resultCp;
    }

    @Override
    public ResultCp updateTask(St4ScsCl data, SysUser seUser) {
        ResultCp resultCp = new ResultCp();
        resultCp.setStatus(1000);

        if(st4ScsClService.updateById(data)){
            QueryWrapper<St4PoClCo> clCoQueryWrapper = new QueryWrapper<>();
            //这里把之前绑定的旧的台账删除，然后把新的在赋上
            clCoQueryWrapper.eq("cl001",data.getCl001());
            if(st4PoClCoMapper.delete(clCoQueryWrapper)<1){
                return ResultCp.build(1003,ResultMsg.MSG_1003);
            }
            List<Integer> ledgerIdList = data.getLedgerIdList();

            List<St4PoClCo> clcoList = new ArrayList<>();
            St4PoClCo clco = null;
            for (Integer lid:ledgerIdList) {
                clco = new St4PoClCo();
                clco.setCl001(data.getCl001());
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
        }else{
            resultCp.setStatus(1003);
            resultCp.setMsg("服务器异常");
        }

        return resultCp;
    }

    @Override
    public ResultCp deleteTask(St4ScsCl data, SysUser seUser) {


        ResultCp resultCp = new ResultCp();
        resultCp.setStatus(1000);
        if(st4ScsClService.updateById(data)){
            QueryWrapper<St4PoClCo> clCoQueryWrapper = new QueryWrapper<>();
            //这里把之前绑定的旧的台账删除，然后把新的在赋上
            clCoQueryWrapper.eq("cl001",data.getCl001());
            if(st4PoClCoMapper.delete(clCoQueryWrapper)<1){
                return ResultCp.build(1003,ResultMsg.MSG_1003);
            }
            resultCp.setMsg("删除成功");
        }else{
            resultCp.setStatus(1003);
            resultCp.setMsg(ResultMsg.MSG_1003);
        }

        return resultCp;
    }
}
