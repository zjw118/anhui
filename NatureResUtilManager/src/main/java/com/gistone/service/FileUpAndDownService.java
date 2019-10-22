package com.gistone.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileUpAndDownService {

    Map<String, Object> uploadPicture(MultipartFile[] files,String phoType) ;

    void deletePicture(String path);

}
