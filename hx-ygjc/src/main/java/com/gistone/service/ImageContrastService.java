package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.VO.ResultVO;
import com.gistone.entity.Image;
import com.gistone.entity.ImageContrast;
import com.gistone.util.PageBean;

public interface ImageContrastService extends IService<ImageContrast> {
    ResultVO add(ImageContrast imageContrast) throws Exception;
    ResultVO list(PageBean PageBean);
    ResultVO like(String name);
    ResultVO delete(Integer id);

}
