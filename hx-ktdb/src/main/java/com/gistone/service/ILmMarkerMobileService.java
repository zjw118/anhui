package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.VO.ResultVO;
import com.gistone.entity.EXCEL.LmMarkerMobileVO;
import com.gistone.entity.LmMarkerMobile;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjc
 * @since 2019-03-14
 */
public interface ILmMarkerMobileService extends IService<LmMarkerMobile> {
    String uploadPicture(MultipartFile files);

    ResultVO selectListByRedLineId(Integer redLineId);

    List<LmMarkerMobile> selectMarkerList(String code,String param, Integer pageNum, Integer pageSize);

    List<LmMarkerMobile> selectAllMarkerList();

    int selectTotal(String code,String param);

    List<LmMarkerMobile> selectPreMarkerList(String code, String param,Integer pageNum, Integer pageSize);

    List<LmMarkerMobile> selectAllPreMarkerList();

    int selectPreTotal(String code,String param);

    LmMarkerMobile findMarkerById(Integer id);

    LmMarkerMobile getMarkerByCoordinate(Double longitude,Double latitude);

    List<LmMarkerMobileVO> selectPreMarkerListForAll(String codes, String param);

    List<LmMarkerMobileVO> selectMarkerListForAll(String codes, String param);

    void delete(Integer id);

    void deleteAll();

    Map<String,Object> list(Integer pageNum,Integer pageSize);

    void recover(Integer id);

    void deleteForever(Integer id);
}
