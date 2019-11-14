package com.gistone.mapper;

import com.gistone.entity.ImageConfig;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ImageConfigMapper{

    List<ImageConfig> getImageConfig();

    int updateImageConfig(ImageConfig imageConfig);

    int insertImageConfig(ImageConfig imageConfig);

    int deleteImageConfig(ImageConfig imageConfig);

    ImageConfig get(ImageConfig imageConfig);


}
