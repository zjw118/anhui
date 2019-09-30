package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.LmFiles;

import java.util.List;

import org.apache.ibatis.annotations.Param;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xjc
 * @since 2019-02-28
 */
public interface LmFilesMapper extends BaseMapper<LmFiles> {

	int insertBatch(List<LmFiles> list);

	void deleteBatch(@Param("lfBoardId") String lfBoardId);

}
