package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.EXCEL.LmMarkerMobileVO;
import com.gistone.entity.LmMarkerMobile;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xjc
 * @since 2019-03-14
 */
public interface LmMarkerMobileMapper extends BaseMapper<LmMarkerMobile> {
    List<LmMarkerMobile> getListByRedLineId(@Param("redLineId") Integer redLineId);

    List<LmMarkerMobile> getMarkerList(@Param("code") String code,@Param("param") String param,@Param("startNum") Integer startNum,@Param("pageSize") Integer pageSize);

    List<LmMarkerMobile> getAllMarkerList();

    int getTotal(@Param("code") String code,@Param("param") String param);

    List<LmMarkerMobile> getPreMarkerList(@Param("code") String code,@Param("param") String param,@Param("startNum") Integer startNum,@Param("pageSize") Integer pageSize);

    List<LmMarkerMobile> getAllPreMarkerList();

    int getPreTotal(@Param("code") String code,@Param("param") String param);

    LmMarkerMobile getMarkerById(Integer id);

    LmMarkerMobile selectMarkerByCoordinate(@Param("longitude") Double longitude,@Param("latitude") Double latitude);

    List<LmMarkerMobileVO> selectPreMarkerListForAll(@Param("code")String code, @Param("param")String param);

    List<LmMarkerMobileVO> selectMarkerListForAll(@Param("code")String code, @Param("param")String param);

    void deleteAll();

    Map<String,Object> getSysCompany(@Param("code") String code);
}
