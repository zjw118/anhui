package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.SysCompany;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjc
 * @since 2019-03-07
 */
public interface ISysCompanyService extends IService<SysCompany> {
    SysCompany selectCodeMsg(Integer userId);
}
