package com.gistone.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.VO.ResultVO;
import com.gistone.entity.Image;
import com.gistone.entity.Iterpretation;
import com.gistone.mapper.ImageMapper;
import com.gistone.mapper.IterpretationMapper;
import com.gistone.service.IterpretationService;
import com.gistone.util.*;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
    * <p>
    * 人类活动解译信息表 服务实现类
    * </p>
    *
    * @author zf1017@foxmail.com
    * @since 2019-10-18
    */
    @Service
    @Transactional
    @Slf4j
    public class IterpretationServiceImpl extends ServiceImpl<IterpretationMapper, Iterpretation> implements IterpretationService {

    @Autowired
    private IterpretationMapper iterpretationMapper;
    @Autowired
    private ImageMapper imageMapper;

    @Override
    public ResultVO sysSpotData (Integer id){

        try {
            List<Iterpretation> cdList = iterpretationMapper.sysSpotData(id);
            ResultCp cp = new ResultCp();
            cp.setData(cdList);
            return ResultVOUtil.success(cp);
        }catch (Exception e){
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.HANDLEFAIL.getCode(), "同步数据失败，服务器异常！");
        }
    }
    @Override
    public Map<String, Object> list(Integer pageNum, Integer pageSize,Integer id) {
        QueryWrapper<Iterpretation> wrapper = new QueryWrapper<>();
        wrapper.eq("image_id",id);
        Map<String, Object> result = new HashMap<>();
        result.put("data", iterpretationMapper.selectList(wrapper));
        return result;
    }

    @Override
    public void delete(List<Integer> ids) {
    //具体逻辑

        }

    @Override
    public void insert(List<Map<String, Object>> data,Integer imageId,Integer createBy) {
    //通过影像id先删除记录然后再插入,然后再写入shp文件，将地址更新到影像表中！
        iterpretationMapper.delete(new QueryWrapper<Iterpretation>().eq("image_id",imageId));
        //从data中构造属性
        for (Map<String, Object> datum : data) {
           Map<String,Object> attributes = (Map<String, Object>) datum.get("attributes");
           //通过属性构造参数
            Iterpretation iterpretation = new Iterpretation();
            if(null!=attributes.get("activeName")){
                iterpretation.setActiveName(attributes.get("activeName")+"");
            }
            if(null!=attributes.get("activeType")){
                iterpretation.setActiveType(attributes.get("activeType")+"");
            }
            if(null!=attributes.get("area")){
                iterpretation.setArea(Double.valueOf(attributes.get("area")+""));
            }
            if(null!=attributes.get("descri")){
                iterpretation.setDescri(attributes.get("descri")+"");
            }
            if(null!=attributes.get("remark")){
                iterpretation.setRemark(attributes.get("remark")+"");
            }
            Map<String,Object> rings = (Map<String, Object>) datum.get("geometry");
            iterpretation.setGeometry(rings.get("rings")+"");
            iterpretation.setImageId(imageId);
            iterpretation.setCreateBy(createBy);//创建人
            iterpretation.setCreateDate(new Date());
            iterpretation.setUpdateDate(new Date());
            iterpretationMapper.insert(iterpretation);
        }


        //执行写入shp文件操作，返回的地址插入到影像表中
        String url = PathUtile.getRandomPath("D:/epr/image/","x.shp");
        String res = ShpUtil.handleWebData(JSONArray.parseArray(net.sf.json.JSONArray.fromObject(data)+""),url);
        if("0".equals(res)){
            Image image = new Image();
            image.setId(imageId);
            image.setShpurl(url.split(":")[1]);
            imageMapper.updateById(image);
        }




    }



    @Override
    public void edit(Iterpretation entity) {
        //具体逻辑
    }

    }


