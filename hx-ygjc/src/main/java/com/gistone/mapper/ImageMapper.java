package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.gistone.entity.Image;
import com.gistone.entity.ImageConfig;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.gistone.entity.St4ScsCdVo;

import java.util.List;


/**
 * <p>
 * 影像数据表 Mapper 接口
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-10-21
 */
public interface ImageMapper extends BaseMapper<Image> {
    Image getImageById(Integer id);

    List<St4ScsCdVo> selectISt4ScsCd(Integer id);

    List<Map<String,Object>> selectCount(@Param("code") String code, @Param("currentTime") LocalDate currentTime, @Param("beforeTime") LocalDate beforeTime);

    int selectBeforeCount(@Param("code") String code, @Param("beforeTime") LocalDate beforeTime);

    int getLastDataId();

    List<Map<String,Object>> getAreaGroupByType(int id);

    int updateImage(Image image);

    int insertImage(Image image);

//    获取下一期数据
    Image getImage2(@Param(value="id")Integer id);


}
