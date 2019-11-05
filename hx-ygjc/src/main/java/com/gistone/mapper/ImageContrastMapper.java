package com.gistone.mapper;

import com.gistone.entity.Image;
import com.gistone.entity.ImageContrast;
import com.gistone.util.PageBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ImageContrastMapper {
    int insertImageContrast(ImageContrast imageContrast);
    int deleteImageContrast(Integer id);
    Map getImageContrast(Integer id);

    List likeList(Image image);


    // 	分页查询-获取总条量
    int getPoSum(PageBean pageBean);
    //	分页查询-获取数据
    List<Object> selectPoList(PageBean pageBean);

}
