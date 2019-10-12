package com.gistone.bjhky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.bjhky.entity.St4ScsCc;
import com.gistone.bjhky.entity.St4SysSa;
import com.gistone.bjhky.mapper.St4ScsCcMapper;
import com.gistone.bjhky.mapper.St4SysSaMapper;
import com.gistone.bjhky.service.ISt4ScsCcService;
import com.gistone.bjhky.util.ObjectUtils;
import com.gistone.bjhky.util.Result;
import com.gistone.bjhky.util.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 航点基础表 服务实现类
 * </p>
 *
 * @author xxh
 * @since 2019-08-14
 */
@Service
public class St4ScsCcServiceImpl extends ServiceImpl<St4ScsCcMapper, St4ScsCc> implements ISt4ScsCcService {

    @Autowired
    private St4ScsCcMapper st4ScsCcMapper;
    @Autowired
    private St4SysSaMapper st4SysSaMapper;
    @Override
    public Result getDetailSailPoint(St4ScsCc cc){

        cc = st4ScsCcMapper.selectById(cc.getCc001());
        Result result = new Result();
        List<St4ScsCc> ccList = new ArrayList<>();
        if(cc.getCc003()==1){
            //就证明查询的是关联台账的详情
            ccList = st4ScsCcMapper.getSailPointLedgerDetail(cc);
        }else if(cc.getCc003()==0){
            //巡护表单里面的详情
            ccList = st4ScsCcMapper.getSailPointRecordDetail(cc);
        }
        try{
            result.setStatus(1000);
            result.setData(ccList);
        }catch (Exception e){
            e.printStackTrace();
            return Result.build(1003,ResultMsg.MSG_1003);
        }
        return result;

    }
    @Override
    public Result getSailPointByReserveId(Integer rid) {

        List<St4ScsCc> list = st4ScsCcMapper.getSailPointDataByReserveId(rid);

        return Result.build(1000,"加载"+ResultMsg.MSG_1000,list);
    }

    @Override
    public Result listSt4ScsCcByPage(St4ScsCc cc, St4SysSa seUser) {
       // QueryWrapper<St4ScsCc> queryWrapper = queryWrapper(st4ScsCc);
        //Integer count = st4ScsCcMapper.selectCount(queryWrapper);
        //queryWrapper.last("limit " + st4ScsCc.getPageSize() * st4ScsCc.getPageNumber() + "," + st4ScsCc.getPageSize());
        /*int size = st4ScsCc.getPageSize();//每页条数
        int number = st4ScsCc.getPageNumber();//开始索引
        int numberReal =0;
        if(0==number){
            numberReal = number;
        }else{
            numberReal= (number-1)*size;
        }
        st4ScsCc.setPageNumber(numberReal);
        st4ScsCc.setPageSize(size);

        Result result = new Result();
        List<St4ScsCc> list = st4ScsCcMapper.getSailList(st4ScsCc);
        st4ScsCc.setPageNumber(null);
        st4ScsCc.setPageSize(null);
        Integer tsize = st4ScsCcMapper.getSailList(st4ScsCc).size();
        result.setPage((int)Math.ceil((double)tsize/size));//共多少页
        result.setRows(list);
        result.setTotal(tsize);
        result.setStatus(1000);*/
        Result result = new Result();
        seUser=st4SysSaMapper.selectById(seUser);
        cc.setCc008(seUser.getSa001());
        if(seUser.getSa001()==1){
            cc.setPtype(2);
        }else {
            if(seUser.getSa020()==0){
                cc.setPtype(0);
            }else {
                cc.setPtype(1);
            }
        }
        /*if(cc.getCc003()!=null){
            wrapper.eq("CC003",cc.getCc003());
        }
        if(cc.getCc012()!=null){
            wrapper.like("CC012",cc.getCc012());
        }*/
        if(ObjectUtils.isNotNullAndEmpty(cc.getEndTime())){
            cc.setEndTime(cc.getEndTime()+" 23:59:59");

        }
        if(ObjectUtils.isNotNullAndEmpty(cc.getStrTime())){
            cc.setStrTime(cc.getStrTime()+" 00:00:00");

        }
        Page<St4ScsCc> page = new Page<>(cc.getPageNumber(),cc.getPageSize());
        IPage<St4ScsCc> ipage = st4ScsCcMapper.getSailPointData(page,cc);
        result.setStatus(1000);
        result.setMsg("加载成功");
        result.setRows(ipage.getRecords());
        result.setTotal((int)ipage.getTotal());
        result.setPage((int)ipage.getPages());
        return result;
    }

    @Override
    public St4ScsCc getSt4ScsCcByCc002(String cc002) {
        QueryWrapper<St4ScsCc> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CC002", cc002);
        List<St4ScsCc> cc = st4ScsCcMapper.selectList(queryWrapper);
        if (cc.size()>0){
            return cc.get(0);
        }
        return null;
    }

    @Override
    public Result listSpeciesToMap(St4ScsCc data) {
        List<St4ScsCc> list = st4ScsCcMapper.listSpeciesToMap(data);
        Result result = new Result();
        result.setData(list);
        result.setStatus(1000);
        result.setMsg("查询物种信息成功");
        return result;
    }

    /**
     * @param st4ScsCc
     * @return com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<com.gistone.bjhky.entity.St4ScsCc>
     * @explain : 封装查询数据方法
     * @author xxh
     * @date 2019/8/15
     */
    public QueryWrapper<St4ScsCc> queryWrapper(St4ScsCc st4ScsCc) {
        QueryWrapper<St4ScsCc> queryWrapper = new QueryWrapper<>();
        if (st4ScsCc.getCc002() != null && "".equals(st4ScsCc.getCc002())) {
            queryWrapper.eq("CC002", st4ScsCc.getCc002());
        }
        if (st4ScsCc.getCc003() != null && "".equals(st4ScsCc.getCc003())) {
            queryWrapper.eq("CC003", st4ScsCc.getCc003());
        }
        if (st4ScsCc.getStrTime() != null && "".equals(st4ScsCc.getStrTime())) {
            queryWrapper.ge("CC007", st4ScsCc.getStrTime());
        }
        if (st4ScsCc.getEndTime() != null && "".equals(st4ScsCc.getEndTime())) {
            queryWrapper.le("CC007", st4ScsCc.getStrTime());
        }
        if (st4ScsCc.getCc012() != null && "".equals(st4ScsCc.getCc012())) {
            queryWrapper.like("CC012", st4ScsCc.getCc012());
        }
        if (st4ScsCc.getCy017() != null && "".equals(st4ScsCc.getCy017())) {
            queryWrapper.eq("CY017", st4ScsCc.getCy017());
        }
        if (st4ScsCc.getCc008() != null && "".equals(st4ScsCc.getCc008())) {
            queryWrapper.eq("CC008", st4ScsCc.getCc008());
        }
        return queryWrapper;
    }
}
