package com.gistone.service.impl;

import com.gistone.entity.TXunhuBiaozhuPho;
import org.springframework.web.multipart.MultipartFile;

public interface ITXunhuBiaozhuPhoService {
    int deleteByPrimaryKey(Long phoId);

    int insert(TXunhuBiaozhuPho record);

    int insertSelective(TXunhuBiaozhuPho record);

    TXunhuBiaozhuPho selectByPrimaryKey(Long phoId);

    int updateByPrimaryKeySelective(TXunhuBiaozhuPho record);

    int updateByPrimaryKey(TXunhuBiaozhuPho record);
    String uploadPicture(MultipartFile files);
    Integer SavePhoMsg(TXunhuBiaozhuPho pho);
}
