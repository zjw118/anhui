package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.Process;
import com.gistone.mapper.ProcessMapper;
import com.gistone.service.IProcessService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjw
 * @since 2019-11-27
 */
@Service
public class ProcessServiceImpl extends ServiceImpl<ProcessMapper, Process> implements IProcessService {

    @Autowired
    private ProcessMapper processMapper;

    @Override
    public Map<String, Object> list(Integer pageNum, Integer pageSize, String pName) {
        QueryWrapper<Process> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(pName)) {
            wrapper.likeRight("p_name", pName);
        }
        wrapper.eq("p_del_flag", 1);
        wrapper.orderByDesc("p_add_time");
        IPage<Process> iPage = processMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", iPage.getRecords());
        result.put("total", iPage.getTotal());
        return result;
    }

}
