package com.gistone.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.VO.ResultVO;
import com.gistone.entity.*;
import com.gistone.mapper.St4ScsCcMapper;
import com.gistone.mapper.St4ScsCdMapper;
import com.gistone.mapper.St4ScsCkMapper;
import com.gistone.mapper.St4ScsCyMapper;
import com.gistone.service.StatisService;
import com.gistone.swagger.StaticSwagger;
import com.gistone.util.Result;
import com.gistone.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 巡护记录表 服务实现类
 * </p>
 *
 * @author wzy
 * @since 2019-08-14
 */
@Service
public class StatisServiceImpl extends ServiceImpl<St4ScsCyMapper, St4ScsCy> implements StatisService {

    @Autowired
    private St4ScsCyMapper st4ScsCyMapper;
    @Autowired
    private St4ScsCdMapper st4ScsCdMapper;
    @Autowired
    private St4ScsCkMapper st4ScsCkMapper;
    @Autowired
    private St4ScsCcMapper st4ScsCcMapper;


   /* @Override
    public Result listWaypointFY(St4ScsCc st4ScsCc) throws Exception {
        int pageNumber = st4ScsCc.getPageNumber();  		//当前页
        int pageSize = st4ScsCc.getPageSize();  			//每页条数
        int poSum = st4ScsCcMapper.getPoSum(st4ScsCc); 		//总量
        int zys = (poSum+pageSize-1)/pageSize;	 			//总页数     （总条数+每页条数-1）/每页条数
        st4ScsCc.setFirstLimit((pageNumber-1)*pageSize); 	//开始索引    (当前页-1)*每页数量
        List list = st4ScsCcMapper.selectPoList(st4ScsCc);

        Result result = new Result();
        result.setRows(list);
        result.setTotal(poSum);
        result.setPage(pageNumber);
        result.setMsg("查询成功");
        result.setStatus(1000);
        return result;
    }*/

   /* @Override
    public Result listWaypoint(St4ScsCc st4ScsCc) throws Exception {
        *//**
         * 这里存在一个问题就是航点虽然分了巡护和核查航点2类是巡护航点的时候是有保护区行政区的信息的
         * 而在巡护航点的情况下是没有行政区及保护地信息的所以这里在统计的时候就会失真 简单来说就是有的航点会根本不在任意一个行政区下
         * 而没有被统计到 还有诸多%的值的数也要返回吧
         *//*
        String start = st4ScsCc.getStrTime();
        String end = st4ScsCc.getEndTime();
        if(st4ScsCc.getStrTime()!=null && st4ScsCc.getStrTime() != ""){
            st4ScsCc.setStrTime(st4ScsCc.getStrTime()+" 00:00:00");
        }
        if(st4ScsCc.getEndTime()!=null && st4ScsCc.getEndTime() != ""){
            st4ScsCc.setEndTime(st4ScsCc.getEndTime()+" 23:59:59");
        }
        return Result.build(1000,"查询成功",st4ScsCcMapper.selectSt4ScsCc(st4ScsCc));
    }*/

   /* @Override
    public Result listPatrol(St4ScsCy st4ScsCy)throws Exception{

        if(st4ScsCy.getStrTime()!=null && st4ScsCy.getStrTime() != ""){
            st4ScsCy.setStrTime(st4ScsCy.getStrTime()+" 00:00:00");
        }
        if(st4ScsCy.getEndTime()!=null && st4ScsCy.getEndTime() != ""){
            st4ScsCy.setEndTime(st4ScsCy.getEndTime()+" 23:59:59");
        }
        List<St4ScsCy> list = st4ScsCyMapper.selectPoList(st4ScsCy);
        return Result.build(1000,"查询成功",list);
    }
*/
    /*@Override
    public Result listPatrolBySA001(St4ScsCy st4ScsCy) throws Exception {
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
*/
    @Override
    public ResultVO listLedger(St4ScsCk ck) throws Exception {
        if(ck.getStrTime()!=null && ck.getStrTime() != ""){
            ck.setStrTime(ck.getStrTime()+" 00:00:00");
        }
        if(ck.getEndTime()!=null && ck.getEndTime() != ""){
            ck.setEndTime(ck.getEndTime()+" 23:59:59");
        }
        List<St4ScsCk> list = st4ScsCkMapper.selectSt4ScsCk(ck);
        return ResultVOUtil.success(list);
    }

   /* @Override
    public Result listLedgerFY(St4ScsCk St4ScsCk) throws Exception {
        int pageNumber = St4ScsCk.getPageNumber();  		//当前页
        int pageSize = St4ScsCk.getPageSize();  			//每页条数
        Integer poSum = st4ScsCkMapper.getPoSum(St4ScsCk); 		//总量
        int zys = (poSum+pageSize-1)/pageSize;	 			//总页数     （总条数+每页条数-1）/每页条数
        St4ScsCk.setFirstLimit((pageNumber-1)*pageSize); 	//开始索引    (当前页-1)*每页数量
        List<St4ScsCk> list = st4ScsCkMapper.selectPoList(St4ScsCk);

        Result result = new Result();
        result.setRows(list);
        result.setTotal(poSum);
        result.setPage(pageNumber);
        result.setMsg("查询成功");
        result.setStatus(1000);
        return result;
    }*/

    @Override
    public Result listLedgerzg(St4ScsCd st4ScsCd) throws Exception {
        List<Map> resList = st4ScsCkMapper.selectSt4ScsCkzg(st4ScsCd);
        return Result.build(1000,"查询成功",resList);
    }

    @Override
    public Result statisDw(St4ScsCc cc) {
        if(cc.getStrTime()!=null && cc.getStrTime() != ""){
            cc.setStrTime(cc.getStrTime()+" 00:00:00");
        }
        if(cc.getEndTime()!=null && cc.getEndTime() != ""){
            cc.setEndTime(cc.getEndTime()+" 23:59:59");
        }
        List<Map> resList = st4ScsCcMapper.statisDw(cc);
        return Result.build(1000,"查询成功",resList);
    }

    @Override
    public Result statisZw(St4ScsCc cc) {
        if(cc.getStrTime()!=null && cc.getStrTime() != ""){
            cc.setStrTime(cc.getStrTime()+" 00:00:00");
        }
        if(cc.getEndTime()!=null && cc.getEndTime() != ""){
            cc.setEndTime(cc.getEndTime()+" 23:59:59");
        }
        List<Map> resList = st4ScsCcMapper.statisZw(cc);
        return Result.build(1000,"查询成功",resList);
    }
    @Override
    public ResultVO pointStatistics(StaticSwagger ss) {

        St4ScsCd cd = new St4ScsCd();
        cd.setCl001(ss.cl001);
        cd.setSg001(ss.sg001);
        cd.setSd001(ss.sd001);
        cd.setGroupByName(ss.getGroupByName());
        St4SysSa sa = new St4SysSa();
        sa.setSa001(ss.sa001);

        cd.setSt4SysSa(sa);

        List<St4ScsCd> list2 = st4ScsCdMapper.getStaticPoint(cd);

        return ResultVOUtil.success(list2);
    }
}
