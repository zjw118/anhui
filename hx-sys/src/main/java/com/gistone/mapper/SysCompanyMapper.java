package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.SysCompany;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xjc
 * @since 2019-03-07
 */
public interface SysCompanyMapper extends BaseMapper<SysCompany> {

     SysCompany getCodeMsg(@Param("userId") Integer userId);

}
