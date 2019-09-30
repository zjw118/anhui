package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.VO.ResultVO;
import com.gistone.entity.DataRedlineRegister;
import com.gistone.entity.EXCEL.LmMarkerMobileVO;
import com.gistone.entity.LmBoard;
import com.gistone.entity.LmMarkerMobile;
import com.gistone.entity.LmMarkerMobileDeviceID;
import com.gistone.mapper.DataRedlineRegisterMapper;
import com.gistone.mapper.LmMarkerMobileDeviceIDMapper;
import com.gistone.mapper.LmMarkerMobileMapper;
import com.gistone.service.ILmMarkerMobileService;
import com.gistone.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xjc
 * @since 2019-03-14
 */
@Service
public class LmMarkerMobileServiceImpl extends ServiceImpl<LmMarkerMobileMapper, LmMarkerMobile> implements ILmMarkerMobileService {

    @Autowired
    private LmMarkerMobileMapper lmMarkerMobileMapper;

    @Autowired
    private ConfigUtils configUtils;

    @Autowired
    private DataRedlineRegisterMapper dataRedlineRegisterMapper;

    @Autowired
    private LmMarkerMobileDeviceIDMapper lmMarkerMobileDeviceIDMapper;


//    private List<LmMarkerMobile> lmMarkerMobileList;

    @Override
    public String uploadPicture(MultipartFile files) {

        String filePath = null;
        try {
            String path = configUtils.getPICTURE_PATH() + "ktdb/";
            filePath = PictureUtils.getPicturePath(path, files);
        } catch (Exception e) {
//           log.error("上传图片异常，异常信息："+e.getMessage());
            e.printStackTrace();
        }

        return filePath;
    }

    @Override
    public ResultVO selectListByRedLineId(Integer redLineId) {
        List<LmMarkerMobile> lmMarkerMobileList = lmMarkerMobileMapper.getListByRedLineId(redLineId);


        return ResultVOUtil.success(lmMarkerMobileList);
    }

    @Override
    public List<LmMarkerMobile> selectMarkerList(String code, String param, Integer pageNum, Integer pageSize) {

        List<LmMarkerMobile> lmMarkerMobileList = lmMarkerMobileMapper.getMarkerList(code, param, (pageNum - 1) * pageSize, pageSize);
        for (LmMarkerMobile lmMarkerMobile : lmMarkerMobileList) {
            Integer jzId = lmMarkerMobile.getId();
            //1.根据界桩id查询相关方位物信息
            //2.根据界桩id查询界桩图片

        }
        return lmMarkerMobileList;
    }

    @Override
    public List<LmMarkerMobile> selectAllMarkerList() {

        List<LmMarkerMobile> lmMarkerMobileList = lmMarkerMobileMapper.getAllMarkerList();
        return lmMarkerMobileList;
    }

    @Override
    public int selectTotal(String code, String param) {

        int total = lmMarkerMobileMapper.getTotal(code, param);
        return total;
    }

    @Override
    public List<LmMarkerMobile> selectPreMarkerList(String code, String param, Integer pageNum, Integer pageSize) {
        List<LmMarkerMobile> lmMarkerMobileList = lmMarkerMobileMapper.getPreMarkerList(code, param, (pageNum - 1) * pageSize, pageSize);
        for (LmMarkerMobile lmMarkerMobile : lmMarkerMobileList) {
            Integer jzId = lmMarkerMobile.getId();
            //1.根据界桩id查询相关方位物信息
            //2.根据界桩id查询界桩图片

        }
        return lmMarkerMobileList;
    }

    @Override
    public List<LmMarkerMobile> selectAllPreMarkerList() {
        List<LmMarkerMobile> lmMarkerMobileList = lmMarkerMobileMapper.getAllPreMarkerList();
        return lmMarkerMobileList;
    }

    @Override
    public int selectPreTotal(String code, String param) {
        int total = lmMarkerMobileMapper.getPreTotal(code, param);
        return total;
    }

    @Override
    public LmMarkerMobile findMarkerById(Integer id) {
        LmMarkerMobile lmMarkerMobile = lmMarkerMobileMapper.getMarkerById(id);
        return lmMarkerMobile;
    }

    @Override
    public LmMarkerMobile getMarkerByCoordinate(Double longitude, Double latitude) {
        LmMarkerMobile lmMarkerMobile = lmMarkerMobileMapper.selectMarkerByCoordinate(longitude, latitude);
        return lmMarkerMobile;
    }

    @Override
    public List<LmMarkerMobileVO> selectPreMarkerListForAll(String codes, String param) {
        List<LmMarkerMobileVO> lmMarkerMobileList = lmMarkerMobileMapper.selectPreMarkerListForAll(codes, param);
        for (LmMarkerMobileVO lmMarkerMobile : lmMarkerMobileList) {
            String jzId = lmMarkerMobile.getId();
            //1.根据界桩id查询相关方位物信息
            //2.根据界桩id查询界桩图片
        }
        return lmMarkerMobileList;
    }

    @Override
    public List<LmMarkerMobileVO> selectMarkerListForAll(String codes, String param) {

        List<LmMarkerMobileVO> lmMarkerMobileList = lmMarkerMobileMapper.selectMarkerListForAll(codes, param);
        for (LmMarkerMobileVO lmMarkerMobile : lmMarkerMobileList) {
            String jzId = lmMarkerMobile.getId();
            //1.根据界桩id查询相关方位物信息
            //2.根据界桩id查询界桩图片

        }
        return lmMarkerMobileList;
    }

    @Override
    public void delete(Integer id) {
        lmMarkerMobileDeviceIDMapper.delete(new QueryWrapper<LmMarkerMobileDeviceID>().eq("lmMobile_id", id));
        LmMarkerMobile lmMarkerMobile = lmMarkerMobileMapper.selectById(id);
        lmMarkerMobile.setDelFlag(0);
        lmMarkerMobileMapper.updateById(lmMarkerMobile);
    }

    @Override
    public void deleteAll() {
        lmMarkerMobileMapper.deleteAll();
    }

    @Override
    public Map<String, Object> list(Integer pageNum, Integer pageSize) {
        QueryWrapper<LmMarkerMobile> wrapper = new QueryWrapper<>();
        wrapper.eq("del_flag", 0);
        IPage<LmMarkerMobile> lmMarkerMobileIPage = lmMarkerMobileMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        Map<String, Object> result = new HashMap<>();
        if (lmMarkerMobileIPage.getRecords() != null && lmMarkerMobileIPage.getRecords().size() > 0) {
            for (LmMarkerMobile record : lmMarkerMobileIPage.getRecords()) {
                //为每条数据添加所属红线编码
                DataRedlineRegister dataRedlineRegister = dataRedlineRegisterMapper.selectOne(new QueryWrapper<DataRedlineRegister>().eq("srld_id", record.getRedlineId()));
                if (dataRedlineRegister != null) {
                    record.setRedlineName(dataRedlineRegister.getSrldNumber());
                }
                //为每条数据添加所在行政区划
                Map<String, Object> sysCompany = lmMarkerMobileMapper.getSysCompany(record.getCode());
                if (sysCompany != null && sysCompany.size() > 0) {
                    record.setPlaceName((String) sysCompany.get("COM_NAME"));
                }

            }
        }
        result.put("rows", lmMarkerMobileIPage.getRecords());
        result.put("total", lmMarkerMobileIPage.getTotal());
        return result;
    }

    @Override
    public void recover(Integer id) {
        LmMarkerMobile lmMarkerMobile = lmMarkerMobileMapper.selectById(id);
        lmMarkerMobile.setDelFlag(1);
        lmMarkerMobileMapper.updateById(lmMarkerMobile);
    }

    @Override
    public void deleteForever(Integer id) {
        LmMarkerMobile lmMarkerMobile = lmMarkerMobileMapper.selectById(id);
        lmMarkerMobile.setDelFlag(2);
        lmMarkerMobileMapper.updateById(lmMarkerMobile);
    }
}
