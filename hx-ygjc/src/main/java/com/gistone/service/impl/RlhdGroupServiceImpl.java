package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.Iterpretation;
import com.gistone.entity.RlhdGroup;
import com.gistone.mapper.IterpretationMapper;
import com.gistone.mapper.RlhdGroupMapper;
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
    private IterpretationMapper iterpretationMapper;

    @Override
    public Map<String, Object> list(Integer pageNum, Integer pageSize, String userName) {

        QueryWrapper<RlhdGroup> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userName)) {
            //wrapper.likeRight("SA008",userName);
        }
        // wrapper.eq("SA007",1);
        //wrapper.orderByDesc("SA003");
        IPage<RlhdGroup> iPage = mapper.selectPage(new Page<>(pageNum, pageSize), wrapper);


        Map<String, Object> result = new HashMap<>();
        result.put("rows", iPage.getRecords());
        result.put("total", iPage.getTotal());

        return result;
    }

    @Override
    public void delete(List<Integer> ids) {
        //具体逻辑

    }

    @Override
    public void insert(String name, Integer createBy, String remark, List<Integer> ids) {
        //1.首先插入台账信息
        RlhdGroup rlhdGroup = new RlhdGroup().setName(name).setCreateBy(createBy).setCreateDate(LocalDateTime.now());
        if(StringUtils.isNotBlank(remark)){
            rlhdGroup.setRemark(remark);
        }
        mapper.insert(rlhdGroup);
        //2.然后更改分组id
        for (Integer id : ids) {
            Iterpretation iterpretation = iterpretationMapper.selectOne(new QueryWrapper<Iterpretation>().eq("id", id));
            iterpretation.setGroupId(rlhdGroup.getId());
            iterpretationMapper.updateById(iterpretation);
        }

    }


    @Override
    public void edit(RlhdGroup entity) {
        //具体逻辑
    }

    @Override
    public List<Iterpretation> getDetailById(Integer id) {

        List<Iterpretation> group_id = iterpretationMapper.selectList(new QueryWrapper<Iterpretation>().eq("group_id", id));

        return group_id;
    }

    @Override
    public void addDataToGroup(Integer groupId, Integer id) {
        Iterpretation iterpretation = iterpretationMapper.selectById(id);
        iterpretation.setGroupId(groupId);
        iterpretationMapper.updateById(iterpretation);
    }

}


