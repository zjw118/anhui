package com.gistone.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gistone.entity.St4ScsCl;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 任务批次表 Mapper 接口
 * </p>
 *
 * @author LiuXiong
 * @since 2019-08-14
 */
public interface St4ScsClMapper extends BaseMapper<St4ScsCl> {

    List<St4ScsCl> listTask(@Param("cl") St4ScsCl cl);


    St4ScsCl getTaskDetail(St4ScsCl cl);

    List<St4ScsCl> getTaskSign(@Param("list") List<Integer> list);

    List<Map> listCdByTask(St4ScsCl cl);

    List<St4ScsCl> getExportData(St4ScsCl ids);

}
