package com.gistone.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.TXunhuBiaozhu;
import com.gistone.entity.TXunhuBiaozhuPho;

public interface TXunhuBiaozhuPhoMapper extends BaseMapper<TXunhuBiaozhuPho> {
    int deleteByPrimaryKey(Long phoId);

    int insert(TXunhuBiaozhuPho record);

    int insertSelective(TXunhuBiaozhuPho record);

    TXunhuBiaozhuPho selectByPrimaryKey(Long phoId);

    int updateByPrimaryKeySelective(TXunhuBiaozhuPho record);

    int updateByPrimaryKey(TXunhuBiaozhuPho record);
}