package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.ProjectAdmission;
import com.gistone.mapper.ProjectAdmissionMapper;
import com.gistone.service.IProjectAdmissionService;
import com.gistone.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-05-05
 */
@Service
public class ProjectAdmissionServiceImpl extends ServiceImpl<ProjectAdmissionMapper, ProjectAdmission> implements IProjectAdmissionService {

    @Autowired
    private ProjectAdmissionMapper projectAdmissionMapper;

    @Override
    public Map<String, Object> getProjectList(Integer pageNum, Integer pageSize, String projectName, String shape, String startTime, String endTime,String type,String attribute,String time) {
        Map<String, Object> result = new HashMap<>();
        QueryWrapper<ProjectAdmission> wrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(projectName)) {
            wrapper.likeRight("name", projectName);
        }

        if (StringUtils.isNotBlank(shape)) {
            wrapper.likeRight("shape", shape);
        }

        if (StringUtils.isNotBlank(startTime)) {
            Date startDate = DateUtils.stringToDate(startTime, DateUtils.DATE_TIME_PATTERN);
            wrapper.ge("create_date", startDate);
        }

        if (StringUtils.isNotBlank(endTime)) {
            Date endDate = DateUtils.stringToDate(endTime, DateUtils.DATE_TIME_PATTERN);
            wrapper.le("create_date", endDate);
        }

        if(StringUtils.isNotBlank(type)){
            wrapper.likeRight("type",type);
        }

        if(StringUtils.isNotBlank(attribute)){
            wrapper.likeRight("attribute",attribute);
        }

        if(StringUtils.isNotBlank(time)){
            Date endDate = DateUtils.stringToDate(time, DateUtils.DATE_TIME_PATTERN);
            wrapper.eq("time",endDate);
        }

        wrapper.eq("del_flag",1);
        wrapper.orderByDesc("create_date");
        IPage<ProjectAdmission> IPage = projectAdmissionMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        result.put("rows", IPage.getRecords());
        result.put("total", IPage.getTotal());
        return result;
    }

    @Override
    public void deleteById(Integer id) {

        ProjectAdmission projectAdmission = projectAdmissionMapper.selectById(id);
        projectAdmission.setDelFlag(false);
        projectAdmissionMapper.updateById(projectAdmission);

    }
}
