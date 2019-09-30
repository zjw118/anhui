package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.EXCEL.LmBoardDiagramVO;
import com.gistone.entity.LmBoardDiagram;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-09-04
 */
public interface LmBoardDiagramMapper extends BaseMapper<LmBoardDiagram> {

    List<LmBoardDiagramVO> selectBoardDiagramListForAll(@Param("param")String boardNum, @Param("code") String codes);

}
