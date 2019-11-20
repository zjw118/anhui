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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
            DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
            Date date2 = null;

            try {
                date2 = format2.parse(startTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date date = DateUtils.addDateDays(date2, 1);
            wrapper.ge("time", date);
        }

        if (StringUtils.isNotBlank(endTime)) {
            DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
            Date date2 = null;

            try {
                date2 = format2.parse(endTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date date = DateUtils.addDateDays(date2, 1);
            wrapper.le("time", date);
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
