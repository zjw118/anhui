package com.gistone.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.VO.ResultVO;
import com.gistone.entity.St4ScsCy;
import com.gistone.entity.St4SysSa;
import com.gistone.mapper.St4ScsCyMapper;
import com.gistone.mapper.St4SysSaMapper;
import com.gistone.service.ISt4ScsCyService;
import com.gistone.swagger.TrackDistribution;
import com.gistone.util.ObjectUtils;
import com.gistone.util.Result;
import com.gistone.util.ResultMsg;
import com.gistone.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 巡护记录表 服务实现类
 * </p>
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
    public ResultVO trackDistribution(TrackDistribution td){

        try{
            String taskName = td.getTaskName();
            String ledgerName = td.getLedgerName();
            List<St4ScsCy> list =st4ScsCyMapper.trackDistribution(taskName, ledgerName);
            return ResultVOUtil.success(list);
        }catch (Exception e){
            e.printStackTrace();
            return ResultVOUtil.error("1222","处理结果失败");
        }

    }
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

        List<St4ScsCy> list = st4ScsCyMapper.listSailRecord(sy);
        int size = sy.getPageSize();//每页条数
        int number = sy.getPageNumber();//开始索引
        int numberReal =0;
        if(0==number){
            numberReal = number;
        }else{
            numberReal= (number-1)*size;
        }
        sy.setPageNumber(numberReal);
        sy.setPageSize(size);
        List<St4ScsCy> sylist = st4ScsCyMapper.listSailRecord(sy);
        sy.setPageNumber(null);
        sy.setPageSize(null);
        Result result = new Result();
        result.setStatus(1000);
        Integer tsize =st4ScsCyMapper.listSailRecord(sy).size();
        result.setTotal(tsize);
        result.setPage((int)Math.ceil((double)tsize/size));
        result.setMsg("加载"+ResultMsg.MSG_1000);
        result.setRows(list);
        return result;
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
