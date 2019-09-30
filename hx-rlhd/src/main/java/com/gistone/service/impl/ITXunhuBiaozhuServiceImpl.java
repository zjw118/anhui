package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.TQuestionVerification;
import com.gistone.entity.TXunhuBiaozhu;
import com.gistone.entity.TXunhuBiaozhuPho;
import com.gistone.mapper.TQuestionVerificationMapper;
import com.gistone.mapper.TXunhuBiaozhuMapper;
import com.gistone.mapper.TXunhuBiaozhuPhoMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ITXunhuBiaozhuServiceImpl extends ServiceImpl<TXunhuBiaozhuMapper, TXunhuBiaozhu> implements ITXunhuBiaozhuService{
    @Autowired
    private TXunhuBiaozhuMapper tXunhuBiaozhuMapper;
    @Autowired
    private TXunhuBiaozhuPhoMapper tXunhuBiaozhuPhoMapper;
    @Autowired
    private TQuestionVerificationMapper tQuestionVerificationMapper;
    @Override
    public int insertSelective(TXunhuBiaozhu record) {

        return tXunhuBiaozhuMapper.insertSelective(record);
    }
    @Override
    public Map<String,Object> findAll(Integer pageNum,Integer pageSize,String shengjing ) {
        QueryWrapper<TXunhuBiaozhu> wrapper = new QueryWrapper<>();
        //请求参数格式校验
        if(StringUtils.isNotBlank(shengjing)){
            wrapper.like("bz_shengjing",shengjing);
        }
        IPage<TXunhuBiaozhu> bz = tXunhuBiaozhuMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        Map<String,Object> result = new HashMap<>();
        result.put("rows",bz.getRecords());
        result.put("total",bz.getTotal());
        return result;
    }

    @Override
    public TXunhuBiaozhu selectByPrimaryKey(Integer bzId) {

        Map<TXunhuBiaozhu, List<TXunhuBiaozhuPho>> map= new HashMap<TXunhuBiaozhu, List<TXunhuBiaozhuPho>>();

        TXunhuBiaozhu bz = tXunhuBiaozhuMapper.selectByPrimaryKey(bzId);

        if(bz!=null){

            QueryWrapper<TXunhuBiaozhuPho> wrapper = new QueryWrapper<TXunhuBiaozhuPho>();
            wrapper.eq("pho_bz_id",bz.getBzId());
            List<TXunhuBiaozhuPho> txbpList = tXunhuBiaozhuPhoMapper.selectList(wrapper);
            bz.setList(txbpList);

            QueryWrapper<TQuestionVerification> wrapper1 = new QueryWrapper<TQuestionVerification>();
            wrapper1.eq("tqv_bz_id",bz.getBzId());
            List<TQuestionVerification> tqvList = tQuestionVerificationMapper.selectList(wrapper1);
            if(tqvList.size()>0){
                bz.setTQuestionVerification(tqvList.get(0));
            }
        }
        return bz;
    }
}
