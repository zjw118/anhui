package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.LsProjectModel;
import com.gistone.mapper.LsProjectModelMapper;
import com.gistone.service.LsProjectModelService;
import com.gistone.util.PictureUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-11-26
 */
@Service
@Transactional
@Slf4j
public class LsProjectModelServiceImpl extends ServiceImpl<LsProjectModelMapper, LsProjectModel> implements LsProjectModelService {

    @Autowired
    private LsProjectModelMapper mapper;
    @Value("${PATH}")
    private String PATH;

    @Override
    public Map<String, Object> list(Integer pageNum, Integer pageSize, String userName,Integer type) {

        QueryWrapper<LsProjectModel> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userName)) {
            wrapper.likeRight("name",userName);
        }
        if(type!=null){
            wrapper.eq("type",type);
        }
         wrapper.eq("del_flag",1);
        //wrapper.orderByDesc("SA003");
        IPage<LsProjectModel> iPage = mapper.selectPage(new Page<>(pageNum, pageSize), wrapper);


        Map<String, Object> result = new HashMap<>();
        result.put("rows", iPage.getRecords());
        result.put("total", iPage.getTotal());

        return result;
    }

    @Override
    public void delete(List<Integer> ids) {
        for (Integer id : ids) {
            LsProjectModel lsProjectModel = mapper.selectById(id);
            lsProjectModel.setDelFlag(0);
            mapper.updateById(lsProjectModel);
        }

    }

    @Override
    public void insert(LsProjectModel entity, MultipartFile file) {
        //上传文件返回地址
        String path = "/epr/word/"+entity.getName()+"-"+entity.getType()+".docx";
        PictureUtils.uploadFile(path, file);
        entity.setUrl(path);
        mapper.insert(entity);

    }


    @Override
    public void edit(LsProjectModel entity,MultipartFile file) {

        //具体逻辑
        if(file!=null){
            String path = PATH+"/epr/word/"+entity.getName()+"-"+entity.getType()+".docx";
            PictureUtils.uploadFile(path, file);
        }
        mapper.updateById(entity);
    }

}


