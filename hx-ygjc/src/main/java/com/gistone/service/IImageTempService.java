package com.gistone.service;

import com.gistone.VO.ResultVO;
import com.gistone.entity.ImageTemp;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.ImageTemp;

/**
 * <p>
 * 影像数据表 服务类
 * </p>
 *
 * @author zjw
 * @since 2019-11-24
 */
public interface IImageTempService extends IService<ImageTemp> {
    ResultVO listImageTemp(ImageTemp it);
}
