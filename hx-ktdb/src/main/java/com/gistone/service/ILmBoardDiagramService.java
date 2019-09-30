package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.EXCEL.LmBoardDiagramVO;
import com.gistone.entity.LmBoardDiagram;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-09-04
 */
public interface ILmBoardDiagramService extends IService<LmBoardDiagram> {
    Map<String,Object> LmBoardDiagramList(String boardNum,String code,Integer pageNum,Integer pageSize);

    void delete(Integer id);

    Map<String,Object> importZipCsv( List<String[]> list,String userId ,String fileNameNoIndex);

    List<LmBoardDiagramVO> selectBoardDiagramListForAll(String boardNum,String code);
}
