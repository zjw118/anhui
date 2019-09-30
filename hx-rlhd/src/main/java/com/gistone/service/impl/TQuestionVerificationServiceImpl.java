package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gistone.entity.TQuestionVerification;
import com.gistone.mapper.TQuestionVerificationMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class TQuestionVerificationServiceImpl implements ITQuestionVerificationService {
    @Autowired
    private TQuestionVerificationMapper tQuestionVerificationMapper;

    @Override
    public int insertSelective(TQuestionVerification record) {
        return tQuestionVerificationMapper.insertSelective(record);
    }
    @Override
    public Map<String,Object> findAll(String tqv_serival_number,String addUser, String startDate,String endDate,Integer pageNum, Integer pageSize) {
        QueryWrapper<TQuestionVerification> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(tqv_serival_number)){
            wrapper.eq("tqv_serival_number",tqv_serival_number);
        }
        if(StringUtils.isNotBlank(addUser)){
           wrapper.likeRight("tqv_preparer",addUser);
        }
        if(StringUtils.isNotBlank(startDate)){
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(startDate, fmt);
            wrapper.ge("tqv_check_time",date);
        }
        if(StringUtils.isNotBlank(endDate)){
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(endDate, fmt);
            wrapper.le("tqv_check_time",date);
        }


        IPage<TQuestionVerification> tQuestionVerification = tQuestionVerificationMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        Map<String,Object> result = new HashMap<>();
        result.put("rows",tQuestionVerification.getRecords());
        result.put("total",tQuestionVerification.getTotal());
        return result;
    }

}
