package com.gistone.mapper;

import com.gistone.entity.ImageNumber;
import com.gistone.entity.Linshi;
import com.gistone.entity.Linshi2;
import com.gistone.util.PageBean;
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


    //临时1
    int updateLinshi(@Param(value="data")String data);
    //临时2
    String getLinshi();
    //临时-增
    int zAdd(Linshi2 Linshi2);
    //临时-删
    int zDelete(@Param(value="id")Integer id);
    //临时-改
    int zUpdate(Linshi2 Linshi2);
    //临时-分页查询-获取总条量
    int getPoSum(PageBean pageBean);
    //临时-分页查询-获取数据
    List<Object> selectPoList(PageBean pageBean);


}
