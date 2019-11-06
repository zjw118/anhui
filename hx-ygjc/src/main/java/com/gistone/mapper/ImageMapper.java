package com.gistone.mapper;

import com.gistone.entity.Image;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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

}
