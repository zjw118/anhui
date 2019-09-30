package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.LmXJCBoard;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xjc
 * @since 2019-02-28
 */
public interface LmXJCBoardMapper extends BaseMapper<LmXJCBoard> {

	List<Map<String, Object>> seleteLmBoardList(@Param("map") Map<String, Object> resultMap);

	int selectLmBoardListCount(@Param("map") Map<String, Object> resultMap);

	int saveLmBoard(LmXJCBoard lm);

	String getPkid(LmXJCBoard lm);

	List<Map<String, Object>> seleteLmBoardInfo(@Param("lbCode") String lbCode, @Param("lbSrldId") Integer lbSrldId);

	int updateLmBoard(LmXJCBoard lm);

	int deleteLmBoard(@Param("lbId") String lbId);

}
