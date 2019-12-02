package com.gistone.mapper;

import com.gistone.entity.ImageNumber;
import com.gistone.entity.Linshi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ImageNumberMapper{
    //添加
    int insertImageNumber(ImageNumber imageNumber);
    //主键真删
    int deleteImageNumber(@Param(value="id")Integer id);
    //批次真删
    int deleteImageName(@Param(value="name")String name);
    //获取所有批次名12
    List<String> selectName();
    //获取所有批次名2
    List<ImageNumber> selectName2();
    //修改
    int updateImageNumber(ImageNumber imageNumber);
    //批次名查询列表1
    List<ImageNumber> selectImageNumber(ImageNumber imageNumber);
    //批次名查询列表2
    List<ImageNumber> selectImageNumber2(@Param(value="name")String name);
    //批次名修改
    int updateNum(ImageNumber imageNumber);
    //设置默认系数
    int defaultNumber1(@Param(value="name") String name);
    //设置默认系数
    int defaultNumber2(@Param(value="name") String name);


    int updateLinshi(@Param(value="data")String data);
    String getLinshi();

}
