package com.gistone.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.TQuestionVerification;


public interface TQuestionVerificationMapper extends BaseMapper<TQuestionVerification> {

    int insertSelective(TQuestionVerification record);

    List<TQuestionVerification> findAll(TQuestionVerification tqv);

}