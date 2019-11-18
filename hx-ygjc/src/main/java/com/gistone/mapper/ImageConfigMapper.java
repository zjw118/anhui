package com.gistone.mapper;

import com.gistone.entity.ImageConfig;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ImageConfigMapper{
    //查询第三层
    List<ImageConfig> getImageConfig3();
    //查询所有层
    List<ImageConfig> getImageConfigAll();


    //查询所有
    List<ImageConfig> getImageConfig();
    //修改
    int updateImageConfig(ImageConfig imageConfig);
    //添加
    int insertImageConfig(ImageConfig imageConfig);
    //删除
    int deleteImageConfig(ImageConfig imageConfig);
    //主键获取
    ImageConfig get(ImageConfig imageConfig);


}
