package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.Image;
import com.gistone.entity.ImageConfig;
import com.gistone.entity.RlhdGroup;
import com.gistone.entity.St4ScsCd;
import com.gistone.mapper.ImageConfigMapper;
import com.gistone.mapper.ImageMapper;
import com.gistone.mapper.RlhdGroupMapper;
import com.gistone.mapper.St4ScsCdMapper;
import com.gistone.service.RlhdGroupService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 人类活动台账信息表 服务实现类
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-10-25
 */
@Service
@Transactional
@Slf4j
public class RlhdGroupServiceImpl extends ServiceImpl<RlhdGroupMapper, RlhdGroup> implements RlhdGroupService {

    @Autowired
    private RlhdGroupMapper mapper;

    @Autowired
    private St4ScsCdMapper iterpretationMapper;

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private ImageConfigMapper imageConfigMapper;

    public Map<String, Object> list(Integer pageNum, Integer pageSize, String userName) {

        QueryWrapper<RlhdGroup> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userName)) {
            wrapper.likeRight("name",userName);
        }
         wrapper.eq("del_flag",1);
        wrapper.orderByDesc("create_date");
        IPage<RlhdGroup> iPage = mapper.selectPage(new Page<>(pageNum, pageSize), wrapper);


        Map<String, Object> result = new HashMap<>();

        if(iPage.getRecords()!=null&&iPage.getRecords().size()>0){
            for (RlhdGroup record : iPage.getRecords()) {
                List<St4ScsCd> list = iterpretationMapper.selectList(new QueryWrapper<St4ScsCd>().eq("group_id", record.getId()));
                record.setSonCount(list.size());
                double sum = 0.0;
                if(list!=null&&list.size()>0){
                    for (St4ScsCd st4ScsCd : list) {
                        String area = st4ScsCd.getArea();
                       sum+= Double.parseDouble(area);
                    }
                }

                record.setSonArea(sum);
            }
        }
        result.put("rows", iPage.getRecords());
        result.put("total", iPage.getTotal());

        return result;
    }

    public void delete(List<Integer> ids) {

        //具体逻辑1.先删除台账信息。2.删除关联信息
        for (Integer id : ids) {
            RlhdGroup rlhdGroup = mapper.selectById(id);
            rlhdGroup.setDelFlag(0);
            mapper.updateById(rlhdGroup);

            St4ScsCd iterpretation = iterpretationMapper.selectOne(new QueryWrapper<St4ScsCd>().eq("group_id",id));
            if(iterpretation!=null){
                iterpretation.setGroupId(0);
                iterpretationMapper.updateById(iterpretation);
            }

        }

    }

    public void insert(String name, Integer createBy, String remark, List<Integer> ids) {
        //1.首先插入台账信息
        RlhdGroup rlhdGroup = new RlhdGroup().setName(name).setCreateBy(createBy).setCreateDate(LocalDateTime.now());
        if(StringUtils.isNotBlank(remark)){
            rlhdGroup.setRemark(remark);
        }
        mapper.insert(rlhdGroup);
        //2.然后更改分组id
        if(ids!=null&&ids.size()>0){
            for (Integer id : ids) {
                St4ScsCd iterpretation = iterpretationMapper.selectOne(new QueryWrapper<St4ScsCd>().eq("CD001", id));
                iterpretation.setGroupId(rlhdGroup.getId());
                iterpretationMapper.updateById(iterpretation);
            }
        }
    }


    public void edit(RlhdGroup entity) {
        //具体逻辑
        mapper.updateById(entity);
    }

    public Map<String,Object> getDetailById(Integer pageNum,Integer pageSize,Integer id) {
        Map<String,Object> result = new HashMap<>();
        IPage<St4ScsCd> iPage = iterpretationMapper.selectPage(new Page<>(pageNum, pageSize), new QueryWrapper<St4ScsCd>().eq("group_id", id));
        if(iPage.getRecords()!=null&&iPage.getRecords().size()>0){
            for (St4ScsCd record : iPage.getRecords()) {
                Integer imageId = record.getImageId();

                Image image = imageMapper.selectById(imageId);
                if(image!=null){
                    record.setImageName(image.getName());
                }
                ImageConfig getone = imageConfigMapper.getone(Integer.parseInt(record.getActiveType()));
                record.setActiveTypeName(getone.getName());

            }
        }
        result.put("rows",iPage.getRecords());
        result.put("total",iPage.getTotal());
        return result;
    }

    public void addDataToGroup(Integer groupId, List<Integer> id) {
        for (Integer integer : id) {
            St4ScsCd iterpretation = iterpretationMapper.selectById(integer);
            iterpretation.setGroupId(groupId);
            iterpretationMapper.updateById(iterpretation);
        }

    }

    public void deleteDataFromGroup(Integer groupId, List<Integer> ids) {
        for (Integer id : ids) {
            St4ScsCd iterpretation = iterpretationMapper.selectById(id);
            iterpretation.setGroupId(0);
            iterpretationMapper.updateById(iterpretation);
        }
    }

}


