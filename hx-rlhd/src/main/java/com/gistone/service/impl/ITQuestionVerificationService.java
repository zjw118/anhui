package com.gistone.service.impl;

import com.gistone.entity.TQuestionVerification;

import java.util.Map;

public interface ITQuestionVerificationService {

    int insertSelective(TQuestionVerification record);

    Map<String,Object> findAll(String tqv_serival_number,String addUser, String startDate,String endDate,Integer pagenum, Integer pageSize);
}
