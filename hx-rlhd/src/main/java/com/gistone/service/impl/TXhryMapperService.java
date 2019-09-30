package com.gistone.service.impl;

import com.gistone.entity.TXhry;

import java.util.List;

public interface TXhryMapperService {
    int deleteByPrimaryKey(Integer xhryid);

    int insert(TXhry record);

    int insertSelective(TXhry record);

    TXhry selectByPrimaryKey(Integer xhryid);

    int updateByPrimaryKeySelective(TXhry record);

    int updateUserInfoForApp(TXhry record);

    int updateByPrimaryKey(TXhry record);



    int export(List<TXhry> list);
}
