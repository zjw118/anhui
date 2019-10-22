package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.EXCEL.LmBoardVO;
import com.gistone.entity.LmBoard;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-04-29
 */
public interface LmBoardMapper extends BaseMapper<LmBoard> {
    String selectPlaceName(@Param("code") String code);
    String selectUserName(@Param("userId") Integer userId);

    List<LmBoardVO> selectPreBoardListForAll(@Param("param")String boardNum, @Param("code")String codes);

    List<LmBoardVO> selectBoardListForAll(@Param("param")String boardNum,@Param("code") String codes);

    void deleteAll();
}
