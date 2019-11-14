package com.gistone.mapper;

import com.gistone.entity.Image;
import com.gistone.entity.ImageConfig;
import com.gistone.entity.ImageNumber;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ImageNumberMapper{

    //影像外键查询列表
    List<ImageNumber> selectImageNumberByImageId(@Param(value="id") Integer id);

    //影像外键查询类型系数列表
    List<ImageConfig> selectImageConfigByImageId(@Param(value="id") Integer id);

    //添加
    int insertImageNumber(ImageNumber imageNumber);

    //影像外键真删
    int deleteImageNumberByImageId(Image image);

    //类型外键删除
    int deleteByImageConfigId(@Param(value="id") Integer id);

}
