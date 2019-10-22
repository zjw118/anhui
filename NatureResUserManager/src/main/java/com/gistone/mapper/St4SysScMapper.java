package com.gistone.mapper;

import com.gistone.entity.St4SysSc;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 模块权限表 Mapper 接口
 * </p>
 *
 * @author LiuXiong
 * @since 2019-08-22
 */
public interface St4SysScMapper extends BaseMapper<St4SysSc> {
    /**
     * 递归查找所有
     *
     * @return
     */
     List<St4SysSc> findAllRecursion();

    /**
     * 根据用户id查询该用户的所有权限
     * @return
     */
     List<St4SysSc> listModuleBySa001(Integer pid);


     List<St4SysSc> findTypeInfoByParentIdAndSortNum(@Param("pid") Integer pid);


}
