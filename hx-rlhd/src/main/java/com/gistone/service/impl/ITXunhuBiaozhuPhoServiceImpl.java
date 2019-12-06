package com.gistone.service.impl;

import com.gistone.entity.TXunhuBiaozhuPho;
import com.gistone.mapper.TXunhuBiaozhuPhoMapper;
import com.gistone.util.ConfigUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ITXunhuBiaozhuPhoServiceImpl implements ITXunhuBiaozhuPhoService {
    @Autowired
    private TXunhuBiaozhuPhoMapper tXunhuBiaozhuPhoMapper;
    @Autowired
    private ConfigUtils configUtils;
    @Override
    public int deleteByPrimaryKey(Long phoId) {
        return 0;
    }

    @Override
    public int insert(TXunhuBiaozhuPho record) {
        return 0;
    }

    @Override
    public int insertSelective(TXunhuBiaozhuPho record) {
        return 0;
    }

    @Override
    public TXunhuBiaozhuPho selectByPrimaryKey(Long phoId) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(TXunhuBiaozhuPho record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(TXunhuBiaozhuPho record) {
        return 0;
    }
    @Override
    public String uploadPicture(MultipartFile files) {

        String filePath = null;
        try {
           // ConfigUtils uil = new ConfigUtils();
            String path = configUtils.getPICTURE_PATH()+"qshx/";
                    //"D:/epr/attached/2/"+  ;
            filePath = PictureUtils.getPicturePath(path, files);
        } catch (Exception e) {
//           log.error("上传图片异常，异常信息："+e.getMessage());
            e.printStackTrace();
        }

        return filePath;
    }
    @Override
    public Integer SavePhoMsg(TXunhuBiaozhuPho pho) {

       return tXunhuBiaozhuPhoMapper.insertSelective(pho);
    }
}
