package com.gistone.mapper;


import com.gistone.entity.LsX;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LsXMapper{

    int add(LsX LsX);
    int delete(LsX LsX);
    int update(LsX LsX);
    LsX get(LsX LsX);



}
