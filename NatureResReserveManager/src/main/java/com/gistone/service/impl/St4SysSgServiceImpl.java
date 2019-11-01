package com.gistone.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.*;
import com.gistone.mapper.St4ScsCaMapper;
import com.gistone.mapper.St4ScsCsMapper;
import com.gistone.mapper.St4SysSgMapper;
import com.gistone.service.ISt4SysSgService;
import com.gistone.util.ReadJson;
import com.gistone.util.Result;
import com.gistone.util.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xxh
 * @since 2019-08-14
 */
@Service
public class St4SysSgServiceImpl extends ServiceImpl<St4SysSgMapper, St4SysSg> implements ISt4SysSgService {

    @Autowired
    private St4SysSgMapper st4SysSgMapper;

    @Autowired
    private St4ScsCaMapper st4ScsCaMapper;

    @Autowired
    private St4ScsCsMapper st4ScsCsMapper;
    @Autowired
    private MessageProperties config;

//    @Override
//    public Result list(St4SysSg data, St4SysSa seUser) {
//        Page<St4SysSg> page = new Page<>(data.getPageNumber(),data.getPageSize());
//     /*   //保护地名称
//        if (RegUtil.CheckParameter(data.getRdName(), "String", null, false)) {
//            param.setRdName(data.getRdName());
//        }
//        //保护地行政区划
//        if (RegUtil.CheckParameter(data.getRdAdminRegion(), "Integer", null, false)) {
//            param.setRdAdminRegion(data.getRdAdminRegion());
//        }
//        //保护地类型
//        if (RegUtil.CheckParameter(data.getRdType(), "Integer", null, false)) {
//            param.setRdType(data.getRdType());
//        }
//        //保护地级别
//        if (RegUtil.CheckParameter(data.getRdRank(), "Integer", null, false)) {
//            param.setRdRank(data.getRdRank());
//        }*/
//        QueryWrapper<St4PoSaSj> sasjWrapper = new QueryWrapper<>();
//        sasjWrapper.eq("SA001",seUser.getSa001());
//        List<St4PoSaSj> sjList = st4PoSaSjMapper.selectList(sasjWrapper);
//        if(sjList!=null&&sjList.size()>0){
//            //这里暂时视作用户的单位视作唯一
//            Integer unitId = sjList.get(0).getSj001();
//            if(seUser.getSa001()==1){
//                data.setUnitId(null);
//            }else {
//                data.setUnitId(unitId);
//            }
//        }
//        IPage<St4SysSg> res = st4SysSgMapper.lists(page,data);
//
//        Result result = new Result();
//        result.setRows(res.getRecords());
//        result.setTotal((int)res.getTotal());
//        result.setPage((int)res.getPages());
//        result.setStatus(1000);
//        return result;
//    }

    @Override
    public Result listByParam(St4SysSg data) {
        List<St4SysSg> nameList = st4SysSgMapper.listByParam(data);
        Result result = new Result();
        result.setData(nameList);
        result.setMsg("查询保护地下拉列表成功");
        result.setStatus(1000);
        return result;
    }

    @Override
    public Result insert(St4SysSg data) {
        //根据保护地名称查询是否有此保护地
        List<St4SysSg> nameList = st4SysSgMapper.listByParam(data);
        if(nameList.size() == 0 ){
            int row = st4SysSgMapper.insert(data);
            if(row > 0){
                data.getReserveUrl().setSg001(data.getSg001());
                st4ScsCsMapper.insert(data.getReserveUrl());
            }
        }else{
            return Result.build(1008,"保护地名称"+ ResultMsg.MSG_1008);
        }
        return Result.build(1000,"添加"+ ResultMsg.MSG_1000);
    }

    @Override
    public Result update(St4SysSg data) {
        //根据保护地名称查询是否有此保护地
        List<St4SysSg> nameList = st4SysSgMapper.listByParam(data);
        if(nameList.size() > 0){
            if(nameList.get(0).getSg001().equals(data.getSg001())){
                int row = st4SysSgMapper.update(data);
                if(row > 0){
                    if(data.getReserveUrl() != null){
                        //版本号加1
                        data.getReserveUrl().setCs003(data.getReserveUrl().getCs003() + 1);
                        st4ScsCsMapper.insert(data.getReserveUrl());
                    }
                }
            }else{
                return Result.build(1008,"保护地名称"+ ResultMsg.MSG_1008);
            }
        }else{
            int row = st4SysSgMapper.update(data);
        }
        return Result.build(1000,"修改"+ ResultMsg.MSG_1000);
    }

    @Override
    public Result delete(St4SysSg data) {
        int row = st4SysSgMapper.delete(data);
        return Result.build(1000,"删除"+ ResultMsg.MSG_1000);
    }

    @Override
    public Result appList(St4SysSg data) {
            List<St4SysSg> res = st4SysSgMapper.appList(data);
            Result result = new Result();
            result.setData(res);
            result.setMsg("查询保护地列表成功");
            result.setStatus(1000);
            return result;
        }
    @Override
    public Result appListToPoint(St4SysSg data) {
            int page = (data.getPageNumber() / data.getPageSize()) + 1;//当前页码
            data.setPageSize(data.getPageSize());
            data.setPageNumber((data.getPageNumber()-1)*data.getPageSize());
            List<St4SysSg> res = st4SysSgMapper.appListToPoint(data);
            Result result = new Result();
            result.setData(res);
            result.setMsg("查询保护地列表成功");
            result.setStatus(1000);
            return result;
        }

    @Override
    public Result getDataById(St4SysSg data, HttpServletRequest request) throws IOException {
        Result result = new Result();
        if(data.getStrTime()!=null && data.getStrTime() != ""){
            data.setStrTime(data.getStrTime()+" 00:00:00");
        }
        if(data.getEndTime()!=null && data.getEndTime() != ""){
            data.setEndTime(data.getEndTime()+" 23:59:59");
        }
        List<St4SysSg> resList = st4SysSgMapper.getDataById(data);
        JSONObject borderJson = null;
        if(resList.size()>0){
            for (St4SysSg res:resList) {

                if(res != null &&  res.getReserveUrl() != null && res.getReserveUrl().getCs004() != null && !"".equals(res.getReserveUrl().getCs004())){
                    borderJson = ReadJson.redaJson(config.getUpPath() + res.getReserveUrl().getCs004(),request);
                }
                if(borderJson!=null){
                    res.setBorderData(borderJson.toJSONString());
                }else{
                    res.setBorderData("");
                }
            }
        }
        if(resList.size()>0){
            result.setData(resList.get(0));
        }
        result.setRows(resList);
        result.setMsg("查询保护地详情成功");
        result.setStatus(1000);
        return result;
    }

    @Override
    public Result getReserveDataBySaid(Integer said) {
        Result result = new Result();

        List<St4SysSg> list = st4SysSgMapper.getReserveDataBySaid(said) ;
        result.setStatus(1000);
        result.setMsg("加载成功");
        result.setData(list);

        return result;
    }

    @Override
    public Result listTreeReserveData(St4SysSg data) {

        St4ScsCa param = new St4ScsCa();
        List<St4ScsCa> res = st4ScsCaMapper.listTreeSt4SysSg(param);
        Result result = new Result();
        result.setData(res);
        result.setMsg("查询保护地列表成功");
        result.setStatus(1000);
        return result;
    }

}
