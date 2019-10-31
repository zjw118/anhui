package com.gistone.service;

import com.gistone.entity.ImageContrast;
import com.gistone.util.PageBean;
import com.gistone.util.Result;

public interface ImageContrastService {
    Result add(ImageContrast imageContrast) throws Exception;
    Result list(PageBean PageBean);
    Result delete(Integer id);

}
