package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.VO.ResultVO;
import com.gistone.entity.EXCEL.LmBoardVO;
import com.gistone.entity.LmBoard;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-04-29
 */
public interface ILmBoardService extends IService<LmBoard> {

    Map<String,Object> getBoardList(String boardNum,String code,Integer pageNum,Integer pageSize);

    List<LmBoard> getAllBoard();

    ResultVO importZipCsv(List<String[]> list, String userId, String fileNameNoIndex) throws Exception;

    Map<String,Object> getPreBoardList(String boardNum,String code,Integer pageNum,Integer pageSize);

    List<LmBoard> getPreAllBoard();

    List<LmBoardVO> selectPreBoardListForAll(String boardNum, String codes);

    List<LmBoardVO> selectBoardListForAll(String boardNum, String codes);

    void delete(Integer id);

    void deleteAll();

    Map<String,Object> getDeletedList(Integer pageNum,Integer pageSize);

    void recover(Integer id);

    void deleteForever(Integer id);
}
