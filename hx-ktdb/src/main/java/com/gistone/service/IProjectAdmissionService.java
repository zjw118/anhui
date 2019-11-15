package com.gistone.service;

import com.gistone.entity.ProjectAdmission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-05-05
 */
public interface IProjectAdmissionService extends IService<ProjectAdmission> {
    Map<String, Object> getProjectList(Integer pageNum, Integer pageSize, String projectName, String shape, String startTime, String endTime,String type,String attribute,String time);

    void deleteById(Integer id);
}
