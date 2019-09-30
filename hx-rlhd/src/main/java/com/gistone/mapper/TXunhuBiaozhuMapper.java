package com.gistone.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.TXunhuBiaozhu;



public interface TXunhuBiaozhuMapper extends BaseMapper<TXunhuBiaozhu> {
    int deleteByPrimaryKey(Integer bzId);

    int insert(TXunhuBiaozhu record);

    int insertSelective(TXunhuBiaozhu record);

    TXunhuBiaozhu selectByPrimaryKey(Integer bzId);

    int updateByPrimaryKeySelective(TXunhuBiaozhu record);

    int updateByPrimaryKey(TXunhuBiaozhu record);
    
 	List<TXunhuBiaozhu> showWayPoint(TXunhuBiaozhu bz);

    Map<String, Object> findAll(Integer pageNum, Integer pageSize, String shengjing );
}