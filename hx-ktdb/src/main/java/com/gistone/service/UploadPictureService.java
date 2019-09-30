package com.gistone.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author zf1017@foxmail.com
 * @date 2019/4/29 0029 17:26
 * @description
 */
public interface UploadPictureService {
    String uploadPicture(MultipartFile file,String modelName);
}
