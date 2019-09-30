package com.gistone.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.TXunhuBiaozhu;

import java.util.Map;

public interface ITXunhuBiaozhuService  extends IService<TXunhuBiaozhu> {

    int insertSelective(TXunhuBiaozhu record);
    Map<String,Object> findAll(Integer pageNum,Integer pageSize,String shengjing);
    TXunhuBiaozhu selectByPrimaryKey(Integer bzId);
}
