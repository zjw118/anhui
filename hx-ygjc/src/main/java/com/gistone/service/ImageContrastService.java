package com.gistone.service;

import com.gistone.VO.ResultVO;
import com.gistone.entity.ImageContrast;
import com.gistone.util.PageBean;
import com.gistone.util.Result;

public interface ImageContrastService {
    ResultVO add(ImageContrast imageContrast) throws Exception;
    ResultVO list(PageBean PageBean);
    ResultVO like(String name);
    ResultVO delete(Integer id);
    ResultVO get(Integer id);

}
