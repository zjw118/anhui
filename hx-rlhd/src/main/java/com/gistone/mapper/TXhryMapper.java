package com.gistone.mapper;

import com.gistone.entity.TXhry;

import java.util.List;


public interface TXhryMapper {
    int deleteByPrimaryKey(Integer xhryid);

    int insert(TXhry record);

    int insertSelective(TXhry record);

    TXhry selectByPrimaryKey(Integer xhryid);

    int updateByPrimaryKeySelective(TXhry record);

    int updateUserInfoForApp(TXhry record);
    
    int updateByPrimaryKey(TXhry record);
    


    int export(List<TXhry> list);
}

