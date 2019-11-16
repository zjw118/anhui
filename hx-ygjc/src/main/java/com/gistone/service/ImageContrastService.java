package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.VO.ResultVO;
import com.gistone.entity.ImageContrast;
import com.gistone.util.PageBean;
import java.util.Map;

public interface ImageContrastService extends IService<ImageContrast> {
    ResultVO add(ImageContrast imageContrast) throws Exception;
    ResultVO list(PageBean PageBean);
    ResultVO like(String name);
    ResultVO delete(Integer id);
    ResultVO get(Map<String, Object> params);

}
