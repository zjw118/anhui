package com.gistone.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.SysCompany;
import com.gistone.mapper.SysCompanyMapper;
import com.gistone.service.ISysCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjc
 * @since 2019-03-07
 */
@Service
public class SysCompanyServiceImpl extends ServiceImpl<SysCompanyMapper, SysCompany> implements ISysCompanyService {
    @Autowired
    private SysCompanyMapper sysCompanyMapper;

    @Override
    public SysCompany selectCodeMsg(Integer userId) {
        SysCompany sysCompany = sysCompanyMapper.getCodeMsg(userId);
        return sysCompany;
    }
}
