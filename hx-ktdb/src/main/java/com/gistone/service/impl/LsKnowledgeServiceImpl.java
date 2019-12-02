package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.LsKnowledge;
import com.gistone.mapper.LsKnowledgeMapper;
import com.gistone.service.LsKnowledgeService;
import com.gistone.util.PictureUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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
public class LsKnowledgeServiceImpl extends ServiceImpl<LsKnowledgeMapper, LsKnowledge> implements LsKnowledgeService {

    @Autowired
    private LsKnowledgeMapper mapper;

    @Value("${PATH}")
    private String PATH;

    @Override
    public Map<String, Object> list(Integer pageNum, Integer pageSize, String userName) {

        QueryWrapper<LsKnowledge> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userName)) {
            wrapper.likeRight("name",userName);
        }
        wrapper.eq("del_flag",1);
        wrapper.orderByDesc("create_time");
        IPage<LsKnowledge> iPage = mapper.selectPage(new Page<>(pageNum, pageSize), wrapper);


        Map<String, Object> result = new HashMap<>();
        result.put("rows", iPage.getRecords());
        result.put("total", iPage.getTotal());

        return result;
    }

    @Override
    public void delete(List<Integer> ids) {
        //具体逻辑
        for (Integer id : ids) {
            LsKnowledge lsKnowledge = mapper.selectById(id);
            lsKnowledge.setDelFlag(0);
            mapper.updateById(lsKnowledge);
        }
    }

    @Override
    public void insert(LsKnowledge entity, MultipartFile file) {
        //具体逻辑
        if(entity.getFlag()==1&&entity.getFlag()==1){
            mapper.updateFlag();
        }
        String path = PATH+"/epr/attached/";
        String picturePath = PictureUtils.getPicturePath(path, file);
        entity.setCreateTime(LocalDateTime.now()).setUrl(picturePath);
        mapper.insert(entity);

    }


    @Override
    public void edit(LsKnowledge entity,MultipartFile file) {
        //具体逻辑
        if(entity.getFlag()!=null&&entity.getFlag()==1){
            mapper.updateFlag();
        }
        if(file!=null){
            String path = PATH+"/epr/attached/";
            String picturePath = PictureUtils.getPicturePath(path, file);
            entity.setUrl(picturePath);
        }

        mapper.updateById(entity);
    }

}


