package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.VO.ResultVO;
import com.gistone.entity.LmMarkerMobile;
import com.gistone.entity.LmMarkerMobileDeviceID;
import com.gistone.entity.LmMarkerPhoto;
import com.gistone.entity.LmMarkerRelationPosition;
import com.gistone.exception.MarkerException;
import com.gistone.mapper.LmMarkerMobileDeviceIDMapper;
import com.gistone.service.ILmMarkerMobileService;
import com.gistone.service.ILmMarkerPhotoService;
import com.gistone.service.ILmMarkerRelationPositionService;
import com.gistone.service.LmMarkerMobilerDeviceIDService;
import com.gistone.util.*;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 */
@Service
@Slf4j
public class LmMarkerMobileDeviceIDServiceImpl extends ServiceImpl<LmMarkerMobileDeviceIDMapper, LmMarkerMobileDeviceID> implements LmMarkerMobilerDeviceIDService {


    @Autowired
    private ConfigUtils configUtils;
    @Autowired
    private LmMarkerMobilerDeviceIDService iLmMarkerDeviceIDService;
    @Autowired
    private ILmMarkerMobileService lmMarkerMobileService;
    @Autowired
    private ILmMarkerPhotoService lmMarkerPhotoService;
    @Autowired
    private ILmMarkerRelationPositionService iLmMarkerRelationPositionService;

    @Override
    public ResultVO importZipCsv(List<String[]> list, String userId, String fileNameNoIndex) throws Exception {
        LmMarkerMobile lmMarkerMobile = new LmMarkerMobile();
        //导入总数
        int num = list.size() - 1;
        //没有入库数据
        int isNotIn=0;

        for (int i = 1; i < list.size(); i++) {
            List<String> stringList = Arrays.asList(list.get(i));
            QueryWrapper<LmMarkerMobileDeviceID> queryWrapper = new QueryWrapper<>();


            queryWrapper.eq("id", stringList.get(0));
            queryWrapper.eq("device_id", stringList.get(1));
            List isData = iLmMarkerDeviceIDService.listObjs(queryWrapper);
            if (isData.size() > 0) {
                //return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "您已添加过该数据!");
                isNotIn += 1;
                continue;
            }
            lmMarkerMobile.setRedlineId(Integer.valueOf(stringList.get(2)));
            lmMarkerMobile.setLandform(stringList.get(4));
            List sjzb = Arrays.asList(stringList.get(5).split(","));
            lmMarkerMobile.setProofLon(Double.valueOf(sjzb.get(0).toString()));
            lmMarkerMobile.setProofLat(Double.valueOf(sjzb.get(1).toString()));
            List yszb = Arrays.asList(stringList.get(6).split(","));
            lmMarkerMobile.setLongitude(Double.valueOf(yszb.get(0).toString()));
            lmMarkerMobile.setLatitude(Double.valueOf(yszb.get(1).toString()));
            lmMarkerMobile.setJzNumber(stringList.get(7));
            lmMarkerMobile.setJzKh(stringList.get(8));

            BigDecimal b = new BigDecimal(stringList.get(9));
            lmMarkerMobile.setCreateTime(DateUtils.Millisecond2Date(b.toPlainString()));
            BigDecimal b1 = new BigDecimal(stringList.get(10));
            lmMarkerMobile.setUpdateTime(DateUtils.Millisecond2Date(b1.toPlainString()));
            lmMarkerMobile.setCode(stringList.get(11));
            //lmMarkerMobile.setLmMarkerRelationPositions();
            lmMarkerMobile.setRemark(stringList.get(15));
            lmMarkerMobile.setVerifyPerson(stringList.get(16));
            lmMarkerMobile.setSaveTime(new Date());

            String isBs = stringList.get(17);
            if("否".equals(isBs)){
                lmMarkerMobile.setIsBs(0);
            }else{
                lmMarkerMobile.setIsBs(1);
            }
            lmMarkerMobile.setType(1);
            lmMarkerMobile.setCreateUser(Integer.valueOf(userId));
            boolean insert = lmMarkerMobileService.save(lmMarkerMobile);
            //if (i == 1) {
            //向中间表保存消息

            LmMarkerMobileDeviceID lmMarkerDeviceID = new LmMarkerMobileDeviceID();
            lmMarkerDeviceID.setLm_id(lmMarkerMobile.getId().toString());
            lmMarkerDeviceID.setId(stringList.get(0));
            lmMarkerDeviceID.setDevice_id(stringList.get(1));
            lmMarkerDeviceID.setBatch_id(String.valueOf(System.currentTimeMillis()));
            lmMarkerDeviceID.setType(0);
            iLmMarkerDeviceIDService.save(lmMarkerDeviceID);
            // }
            //缩略图上传
            if (StringUtils.isNotBlank(stringList.get(13))) {
                String mbwj = configUtils.getPICTURE_PATH() + "ktdb/" + DateUtils.format(new Date()) + "/ZipThumbnail/";
                FileUtil.mkdirsmy(mbwj, "数据库关联文件,且勿删除");
                System.out.println(stringList.get(13).substring(stringList.get(13).lastIndexOf("/") + 1));
                FileUtil.copyFile(configUtils.getZIP_DECOM_PATH() + fileNameNoIndex + "\\thumbnail\\" + stringList.get(13).substring(stringList.get(13).lastIndexOf("/") + 1), mbwj);
                Integer type = 6;
                prePhotoInfo(lmMarkerMobile, mbwj.substring(2) + stringList.get(13).substring(stringList.get(13).lastIndexOf("/") + 1), type);
            }
            //现场图上传
            if (StringUtils.isNotBlank(stringList.get(14))) {
                String mbwj = configUtils.getPICTURE_PATH() + "ktdb/" + DateUtils.format(new Date()) + "\\ZiplocalPicture\\";
                FileUtil.mkdirsmy(mbwj, "数据库关联文件,且勿删除");

              /*  System.out.println("------"+stringList.get(14)+"---------");
                String str = new String(stringList.get(14).getBytes("ISO-8859-1"),"ISO-8859-1");
                System.out.println("---str--"+str+"---str--");*/
                String newStr = stringList.get(14).replaceAll("，", ",");
                JSONArray jsonArray = JSONArray.fromObject(newStr);
                for (int j = 0; j < jsonArray.size(); j++) {
                    JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(j));

                    if (jsonObject.get("path") != null) {
                        String paths = jsonObject.get("path").toString();
                        FileUtil.copyFile(configUtils.getZIP_DECOM_PATH() + fileNameNoIndex + "\\localPicture\\" + paths.substring(paths.lastIndexOf("/") + 1), mbwj);
                        prePhotoInfo(lmMarkerMobile, mbwj.substring(2) + paths.substring(paths.lastIndexOf("/") + 1), Integer.valueOf(jsonObject.get("type").toString()));
                    }
                }
            }
            //添加界桩相关方位物信息
            if (StringUtils.isNotBlank(stringList.get(12))) {
                JSONArray jsonArray = JSONArray.fromObject(stringList.get(12).replaceAll("，", ","));
                for (int a = 0; a < jsonArray.size(); a++) {
                    JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(a));
                    LmMarkerRelationPosition lmMarkerRelationPosition = new LmMarkerRelationPosition();
                    lmMarkerRelationPosition.setJzId(lmMarkerMobile.getId());
                    if (StringUtils.isBlank(jsonObject.get("distance").toString())) {
                        throw new MarkerException(ResultEnum.DISTANCE_EMPTY);
                    }
                    if (StringUtils.isBlank(jsonObject.get("location").toString())) {
                        throw new MarkerException(ResultEnum.DRECTION_EMPTY);
                    }

                    if (StringUtils.isBlank(jsonObject.get("desc").toString())) {
                        throw new MarkerException(ResultEnum.REFERENCE_EMPTY);
                    }
                    lmMarkerRelationPosition.setDistance(Double.valueOf(jsonObject.get("distance").toString()));
                    lmMarkerRelationPosition.setDirection(String.valueOf(jsonObject.get("location")));
                    lmMarkerRelationPosition.setOfReference(String.valueOf(jsonObject.get("desc")));
                    //界桩方位物相关信息入库
                    iLmMarkerRelationPositionService.save(lmMarkerRelationPosition);
                }
            }
        }
        Map map = new HashMap();
        map.put("num", num);
        map.put("isNotIn", isNotIn);
        return ResultVOUtil.success(map);

    }

    public void prePhotoInfo(LmMarkerMobile lmMarkerMobile, String rePath, Integer type) {
        LmMarkerPhoto lmMarkerPhoto = new LmMarkerPhoto();
        lmMarkerPhoto.setJzId(lmMarkerMobile.getId());
        lmMarkerPhoto.setUrl(rePath);
        lmMarkerPhoto.setCreateTime(new Date());
        lmMarkerPhoto.setType(type);
        lmMarkerPhoto.setNumber(lmMarkerMobile.getJzNumber() + "_" + type);
        lmMarkerPhotoService.save(lmMarkerPhoto);
    }
}
