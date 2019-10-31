package com.gistone.service.impl;

import com.gistone.entity.Image;
import com.gistone.entity.ImageContrast;
import com.gistone.mapper.ImageContrastMapper;
import com.gistone.mapper.ImageMapper;
import com.gistone.service.ImageContrastService;
import com.gistone.util.HttpUtil;
import com.gistone.util.PageBean;
import com.gistone.util.Result;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;


@Service
@Transactional
@Slf4j
public class ImageContrastServiceImpl implements ImageContrastService {
    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private ImageContrastMapper imageContrastMapper;

    //服务地址
    private String url = "http://192.168.1.91:6080/arcgis/rest/services/ahhx/insectqufan/GPServer/insectqufan";



    @Override
    public Result add(ImageContrast imageContrast) throws Exception {
        Image image1 = imageMapper.getImageById(imageContrast.getImage1Id());
        Image image2 = imageMapper.getImageById(imageContrast.getImage2Id());
        String p1 = "?file1=D:"+image1.getShpurl();
        String p2 = "&file2=D:"+image2.getShpurl();
        String p3 = "&f=pjson";
        String res = HttpUtil.GET(url+"/submitJob"+p1+p2+p3,null);
        JSONObject jsonObject = JSONObject.fromObject(res);

        //异步
        String res2 = "";
        for (int i = 0; i < 5; i++) {
            res2 = HttpUtil.GET(url+"/jobs/"+jsonObject.get("jobId")+"/results/out_shp?f=pjson",null);
            if(!"".equals(res2)&&-1==res2.indexOf("error")) break;
            Thread.sleep(1500);
        }
        imageContrast.setDate(new Date());
        imageContrast.setData(res2);
        int addres = imageContrastMapper.insertImageContrast(imageContrast);
        if(addres>0){
            return Result.ok(res2);
        }
        return Result.build(2,"对比失败");
    }

    @Override
    public Result list(PageBean pageBean){
        pageBean.setPoSum(imageContrastMapper.getPoSum(pageBean));
        pageBean.setPoList(imageContrastMapper.selectPoList(pageBean));
        return Result.ok(pageBean);
    }

    @Override
    public Result delete(Integer id) {
        int res = imageContrastMapper.deleteImageContrast(id);
        if(0<res){
            return Result.ok();
        }
        return Result.build(2,"删除失败");
    }
}
