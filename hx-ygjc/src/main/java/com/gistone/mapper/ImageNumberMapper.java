package com.gistone.mapper;

import com.gistone.entity.ImageNumber;
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
    //获取所有批次名
    List<String> selectName();
    //修改
    int updateImageNumber(ImageNumber imageNumber);

    //批次名查询列表1
    List<ImageNumber> selectImageNumber(ImageNumber imageNumber);
    //批次名查询列表2
    List<ImageNumber> selectImageNumber2(@Param(value="name")String name);

    //批次名修改
    int updateNum(ImageNumber imageNumber);

}
