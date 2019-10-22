package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gistone.entity.St4ScsCy;
import com.gistone.entity.St4SysSa;
import com.gistone.mapper.St4ScsCyMapper;
import com.gistone.mapper.St4SysSaMapper;
import com.gistone.service.ISt4ScsCyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.util.ObjectUtils;
import com.gistone.util.Result;
import com.gistone.util.ResultMsg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 巡护记录表 服务实现类
 * </p>
 *
 * @author zhaojingwei
 * @since 2019-08-17
 */
@Service
public class St4ScsCyServiceImpl extends ServiceImpl<St4ScsCyMapper, St4ScsCy> implements ISt4ScsCyService {
    @Autowired
    private St4ScsCyMapper st4ScsCyMapper;
    @Autowired
    private St4SysSaMapper st4SysSaMapper;
    @Override
    public Result getSailRecordDetail(St4ScsCy sy){

        List<St4ScsCy> list = st4ScsCyMapper.getSailRecordDetail(sy);

        Result result = new Result();
        result.setStatus(1000);
        result.setData(list);
        result.setMsg("加载"+ResultMsg.MSG_1000);

        return result;
    }
    @Override
    public Result listRecord(St4ScsCy sy, St4SysSa seUser){

        seUser = st4SysSaMapper.selectById(seUser);
        sy.setSa001(seUser.getSa001());
        if(seUser.getSa001()==1){
            sy.setType(2);
        }else {
            if(seUser.getSa020()==0){
                sy.setType(0);
            }else {
                sy.setType(1);
            }
        }

        if(ObjectUtils.isNotNullAndEmpty(sy.getStrTime())){
            sy.setStrTime(sy.getStrTime()+" 00:00:00");
        }
        if(ObjectUtils.isNotNullAndEmpty(sy.getEndTime())){
            sy.setEndTime(sy.getEndTime()+" 23:59:59");
        }
        Page<St4ScsCy> page = new Page<>(sy.getPageNumber(),sy.getPageSize());
        IPage<St4ScsCy> list = st4ScsCyMapper.listSailRecord(page,sy);
        Result result = new Result();
        result.setStatus(1000);
        result.setTotal((int)list.getTotal());
        result.setPage((int)list.getPages());
        result.setMsg("加载"+ResultMsg.MSG_1000);
        result.setRows(list.getRecords());

        return result;
    }
    @Override
    public Result listPatrol(St4ScsCy st4ScsCy)throws Exception{
        String kssj = st4ScsCy.getKssj();
        String jssj = st4ScsCy.getJssj();
        if(!StringUtils.isBlank(kssj)){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            st4ScsCy.setKssj(simpleDateFormat.format(new Date(Long.valueOf(kssj))));
        }
        if(!StringUtils.isBlank(jssj)){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            st4ScsCy.setJssj(simpleDateFormat.format(new Date(Long.valueOf(jssj))));
        }
        List list = st4ScsCyMapper.selectPoList(st4ScsCy);
//        Result result = new Result();
//        result.setRows(list);
//        result.setTotal(zys);
//        result.setPage(pageNumber);
//        result.setMsg("查询成功");
//        result.setStatus(1000);
        return Result.build(1000,"查询成功",list);
    }

    @Override
    public Result listPatrolBySA001(St4ScsCy st4ScsCy) throws Exception {
//        String kssj = st4ScsCy.getKssj();
//        String jssj = st4ScsCy.getJssj();
//        if(!StringUtils.isBlank(kssj)){
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            st4ScsCy.setKssj(simpleDateFormat.format(new Date(Long.valueOf(kssj))));
//        }
//        if(!StringUtils.isBlank(jssj)){
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            st4ScsCy.setJssj(simpleDateFormat.format(new Date(Long.valueOf(jssj))));
//        }


        int pageNumber = st4ScsCy.getPageNumber();  		//当前页
        int pageSize = st4ScsCy.getPageSize();  			//每页条数
        int poSum = st4ScsCyMapper.getPoSum2(st4ScsCy); 		//总量
        int zys = (poSum+pageSize-1)/pageSize;	 			//总页数     （总条数+每页条数-1）/每页条数
        st4ScsCy.setPageNumber((pageNumber-1)*pageSize); 	//开始索引    (当前页-1)*每页数量
        List list = st4ScsCyMapper.selectPoList2(st4ScsCy);

        Result result = new Result();
        result.setRows(list);
        result.setTotal(poSum);
        result.setPage(pageNumber);
        result.setMsg("查询成功");
        result.setStatus(1000);
        return result;
    }

    @Override
    public Result getSailRouteByReserveId(Integer rid) {

        List<St4ScsCy> list = st4ScsCyMapper.getSailRouteDataByReserveId(rid);
        Result result = new Result();
        result.setMsg("加载成功");
        result.setRows(list);
        return result;//Result.build(1000,"加载"+ResultMsg.MSG_1000,list);
    }
}
