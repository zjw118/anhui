package com.gistone.service.impl;

import com.gistone.service.UploadPictureService;
import com.gistone.util.ConfigUtils;
import com.gistone.util.PictureUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zf1017@foxmail.com
 * @date 2019/4/29 0029 17:28
 * @description
 */
@Service
@Slf4j
public class UploadPictureServiceImpl implements UploadPictureService {

    @Autowired
    private ConfigUtils configUtils;

    @Override
    public String uploadPicture(MultipartFile files, String modelName) {
        String filePath = null;
        try {
            //"ktdb/"
            String path = configUtils.getPICTURE_PATH()+modelName;
            filePath = PictureUtils.getPicturePath(path, files);
        } catch (Exception e) {
           log.error("上传图片异常，异常信息：{}",e.getMessage());
            e.printStackTrace();
        }

        return filePath;
    }
}
