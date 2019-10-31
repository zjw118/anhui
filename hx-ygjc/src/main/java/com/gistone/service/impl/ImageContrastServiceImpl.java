package com.gistone.service.impl;

import com.gistone.VO.ResultVO;
import com.gistone.entity.Image;
import com.gistone.entity.ImageContrast;
import com.gistone.mapper.ImageContrastMapper;
import com.gistone.mapper.ImageMapper;
import com.gistone.service.ImageContrastService;
import com.gistone.util.*;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Map;


@Service
@Transactional
@Slf4j
@Component
public class ImageContrastServiceImpl implements ImageContrastService {
    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private ImageContrastMapper imageContrastMapper;


    @Value("${PATH}")
    private String PATH;
    @Value("${IMAGE_SERVICE}")
    private String IMAGE_SERVICE;

    @Override
    public ResultVO add(ImageContrast imageContrast) throws Exception {
        Image image1 = imageMapper.getImageById(imageContrast.getImage1Id());
        Image image2 = imageMapper.getImageById(imageContrast.getImage2Id());
        String p1 = "?file1="+PATH+image1.getShpurl();
        String p2 = "&file2="+PATH+image2.getShpurl();
        String p3 = "&f=pjson";
        String res = HttpUtil.GET(IMAGE_SERVICE+"/submitJob"+p1+p2+p3,null);
        JSONObject jsonObject = JSONObject.fromObject(res);
        //异步
        String res2 = "";
        for (int i = 0; i < 5; i++) {
            res2 = HttpUtil.GET(IMAGE_SERVICE+"/jobs/"+jsonObject.get("jobId")+"/results/out_shp?f=pjson",null);
            if(!"".equals(res2)&&-1==res2.indexOf("error")) break;
            Thread.sleep(1500);
        }
        imageContrast.setDate(new Date());
        imageContrast.setData(res2);
        int addres = imageContrastMapper.insertImageContrast(imageContrast);
        if(addres>0){
            return ResultVOUtil.success(res2);
        }
        return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"对比失败");
    }

    @Override
    public ResultVO list(PageBean pageBean){
        pageBean.setPoSum(imageContrastMapper.getPoSum(pageBean));
        pageBean.setPoList(imageContrastMapper.selectPoList(pageBean));
        return ResultVOUtil.success(pageBean);
    }

    @Override
    public ResultVO delete(Integer id) {
        int res = imageContrastMapper.deleteImageContrast(id);
        if(0<res){
            return ResultVOUtil.success();
        }
        return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"删除失败");
    }

    @Override
    public ResultVO get(Integer id) {
        Map res = imageContrastMapper.getImageContrast(id);
        if(0<res.size()){
            return ResultVOUtil.success(res);
        }
        return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"获取失败");
    }
}
