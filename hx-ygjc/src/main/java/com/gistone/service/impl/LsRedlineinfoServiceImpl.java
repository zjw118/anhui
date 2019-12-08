package com.gistone.service.impl;

import com.gistone.VO.ResultVO;
import com.gistone.entity.*;
import com.gistone.mapper.LsProjectModelMapper;
import com.gistone.mapper.LsRedlineinfoMapper;
import com.gistone.service.LsRedlineinfoService;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import com.gistone.util.WordUtil;
import com.gistone.util.pictureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class LsRedlineinfoServiceImpl implements LsRedlineinfoService {
    @Value("${PATH}")
    private String PATH;
    @Autowired
    private LsProjectModelMapper lsProjectModelMapper;



    @Autowired
    private LsRedlineinfoMapper lsRedlineinfoMapper;


    @Override
    public ResultVO processInsert(LsRedlineinfoProcess lsRedlineinfoProcess) throws Exception {
        return ResultVOUtil.success((lsRedlineinfoMapper.processInsert(lsRedlineinfoProcess)));
    }

    @Override
    public ResultVO processDelete(LsRedlineinfoProcess lsRedlineinfoProcess) throws Exception {
        return ResultVOUtil.success((lsRedlineinfoMapper.processDelete(lsRedlineinfoProcess)));
    }

    @Override
    public ResultVO processUpdate(LsRedlineinfoProcess lsRedlineinfoProcess) throws Exception {
        return ResultVOUtil.success((lsRedlineinfoMapper.processUpdate(lsRedlineinfoProcess)));
    }

    @Override
    public ResultVO templateInsert(LsRedlineinfoTemplate lsRedlineinfoTemplate) throws Exception {
        return ResultVOUtil.success((lsRedlineinfoMapper.templateInsert(lsRedlineinfoTemplate)));
    }

    @Override
    public ResultVO templateDelete(LsRedlineinfoTemplate lsRedlineinfoTemplate) throws Exception {
        return ResultVOUtil.success((lsRedlineinfoMapper.templateDelete(lsRedlineinfoTemplate)));
    }

    @Override
    public ResultVO templateUpdate(LsRedlineinfoTemplate lsRedlineinfoTemplate) throws Exception {
        return ResultVOUtil.success((lsRedlineinfoMapper.templateUpdate(lsRedlineinfoTemplate)));
    }

    @Override
    public ResultVO versionInsert(LsRedlineinfoVersion lsRedlineinfoVersion) throws Exception {
        return ResultVOUtil.success((lsRedlineinfoMapper.versionInsert(lsRedlineinfoVersion)));
    }

    @Override
    public ResultVO versionDelete(LsRedlineinfoVersion lsRedlineinfoVersion) throws Exception {
        return ResultVOUtil.success((lsRedlineinfoMapper.versionDelete(lsRedlineinfoVersion)));
    }

    @Override
    public ResultVO versionUpdate(LsRedlineinfoVersion lsRedlineinfoVersion) throws Exception {
        return ResultVOUtil.success((lsRedlineinfoMapper.versionUpdate(lsRedlineinfoVersion)));
    }

    @Override
    public ResultVO infoInsert(LsRedlineinfo lsRedlineinfo) throws Exception {
        return ResultVOUtil.success((lsRedlineinfoMapper.infoInsert(lsRedlineinfo)));
    }

    @Override
    public ResultVO infoDelete(LsRedlineinfo lsRedlineinfo) throws Exception {
        return ResultVOUtil.success((lsRedlineinfoMapper.infoDelete(lsRedlineinfo)));
    }

    @Override
    public ResultVO infoUpdate(LsRedlineinfo lsRedlineinfo) throws Exception {
        return ResultVOUtil.success((lsRedlineinfoMapper.infoUpdate(lsRedlineinfo)));
    }



    @Override
    public ResultVO export(String data1, String data2, String data3) throws Exception {
        //获取模板
        LsProjectModel lsProjectModelByType = lsProjectModelMapper.getLsProjectModelByType(3);
        String path = lsProjectModelByType.getUrl();

        //生成图片
        String path1 = pictureUtil.generate(PATH + "/epr/HXFW", data1);
        String path2 = pictureUtil.generate(PATH + "/epr/HXFW", data2);
        String path3 = pictureUtil.generate(PATH + "/epr/HXFW", data3);

        //生成word
        String uuid = UUID.randomUUID().toString();
        String docxPath = PATH+"/epr/HXFW/";
        Map<String,Object> params = new HashMap<>();
        Map<String,String> pictureMap = new HashMap<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        params.put("date",date);
        pictureMap.put("image1",path1);
        pictureMap.put("image2",path2);
        pictureMap.put("image3",path3);
        boolean b = WordUtil.exportWord(PATH+path, docxPath,uuid+".docx", params, pictureMap);
        if(b){
            return ResultVOUtil.success("/epr/HXFW/"+uuid+".docx");
        }
        return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "生成报告失败");
    }





}
