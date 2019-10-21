package com.gistone.controller;


import cn.afterturn.easypoi.entity.ImageEntity;
import cn.afterturn.easypoi.word.WordExportUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.VO.PositionVO;
import com.gistone.VO.ResultVO;
import com.gistone.entity.EXCEL.LmMarkerMobileVO;
import com.gistone.entity.*;
import com.gistone.exception.MarkerException;
import com.gistone.service.*;
import com.gistone.util.*;
import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;


/**
 * <p>
 * 关于界桩相关功能控制器
 * </p>
 *
 * @author xjc
 * @since 2019-03-14
 */
@RestController
@RequestMapping("/api/ktdb/lmMarkerMobile")
@Transactional
@Slf4j
public class LmMarkerMobileController {
    @Autowired
    private ILmPointService iLmPointService;

    @Autowired
    private ILmMarkerMobileService lmMarkerMobileService;

    @Autowired
    private ILmMarkerPhotoService lmMarkerPhotoService;

    @Autowired
    private ILmMarkerRelationPositionService iLmMarkerRelationPositionService;

    @Autowired
    private ConfigUtils configUtils;

    @Autowired
    private LmMarkerMobilerDeviceIDService iLmMarkerDeviceIDService;

    @Autowired
    private ILmBoardService iLmBoardService;

    @Autowired
    private LmMarkerMobilerDeviceIDService lmMarkerMobilerDeviceIDService;

    @Autowired
    private ISysUserService sysUserService;

    @Value("${PATH}")
    private String PATH;
    @Value("${WORD_PATH}")
    private String WORD_PATH;

    /**
     * 采集界桩信息
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/add")
    public ResultVO add(@RequestParam("data") String data, MultipartFile[] file) {

        if (StringUtils.isBlank(data)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "data不能为空");
        }
        JSONObject a = JSONObject.fromObject(data);
        Map<String, Object> map = JsonToMapUtil.parseJSON2Map(a);

        Map<String, Object> dataParam = (Map<String, Object>) map.get("data");
        LmMarkerMobile lmMarkerMobile = new LmMarkerMobile();

        /*String name = (String) dataParam.get("name");
        lmMarkerMobile.setName(name);*/
        List<Object> position = (List<Object>) dataParam.get("position");

        String uuid = (String) dataParam.get("uuid");

        String verifyPerson = (String) dataParam.get("verifyPerson");

        if (StringUtils.isBlank(verifyPerson)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "审核人不能为空");
        }

        lmMarkerMobile.setVerifyPerson(verifyPerson);

        Integer isBs = (Integer) dataParam.get("isBs");
        if (isBs == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "是否附标识牌不能为空");
        }

        lmMarkerMobile.setIsBs(isBs);

        Integer userId = (Integer) dataParam.get("userId");
        if (userId == null || userId <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "用户id不能为空");
        }
        lmMarkerMobile.setCreateUser(userId);

        String jzNumber = (String) dataParam.get("jzNumber");
        if (StringUtils.isBlank(jzNumber)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "界桩编号不能为空");
        }
        lmMarkerMobile.setJzNumber(jzNumber);

        String jzKh = (String) dataParam.get("jzKh");
        if (StringUtils.isBlank(jzKh)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "界桩刻号不能为空");
        }
        lmMarkerMobile.setJzKh(jzKh);


        String landform = (String) dataParam.get("landform");
        if (StringUtils.isBlank(landform)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "界桩地貌不能为空");
        }

        lmMarkerMobile.setLandform(landform);

        String code = (String) dataParam.get("code");
        if (StringUtils.isBlank(code)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "所在地不能为空");
        }
        lmMarkerMobile.setCode(code);

        //校对经纬度
        Double proofLog = (Double) dataParam.get("proofLog");
        if (proofLog == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "校对经度不能未为空");
        }
        lmMarkerMobile.setProofLon(proofLog);


        Double proofLat = (Double) dataParam.get("proofLat");
        if (proofLat == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "校对纬度不能为空");
        }
        lmMarkerMobile.setProofLat(proofLat);

        Double longitude = (Double) dataParam.get("longitude");
        if (longitude != null) {
            lmMarkerMobile.setLongitude(longitude);
        }


        Double latitude = (Double) dataParam.get("latitude");
        if (latitude != null) {
            lmMarkerMobile.setLatitude(latitude);
        }


        Integer redlineId = (Integer) dataParam.get("redlineId");
        if (redlineId == null || redlineId < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "所属红线不能为空");
        }
        lmMarkerMobile.setRedlineId(redlineId);

        String remark = (String) dataParam.get("remark");
        if (StringUtils.isNotBlank(remark)) {
            lmMarkerMobile.setRemark(remark);
        }

        String createTime = (String) dataParam.get("createTime");
        if (StringUtils.isBlank(createTime)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "填表时间不能为空");
        }
        lmMarkerMobile.setCreateTime(DateUtils.StrtoDateYMD(createTime));

        String updateTime = (String) dataParam.get("updateTime");
        if (StringUtils.isNotBlank(updateTime)) {
            lmMarkerMobile.setUpdateTime(DateUtils.StrtoDateYMD(updateTime));
        }
        lmMarkerMobile.setSaveTime(new Date());
        lmMarkerMobile.setType(1);

        String markerId = (String) dataParam.get("dataId");
        if (StringUtils.isBlank(markerId)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "数据id不能为空");
        }
        String deviceId = (String) dataParam.get("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "设备id不能为空");
        }
        lmMarkerMobile.setSaveTime(new Date());

        LmMarkerMobileDeviceID one = lmMarkerMobilerDeviceIDService.getOne(new QueryWrapper<LmMarkerMobileDeviceID>().eq("id", markerId).eq("device_id", deviceId));

        if (file == null || file.length == 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "缩略图不能为空");
        }
        if (one == null) {
            Boolean flag = true;
            try {

                // 图片上传
                if (file != null && file.length > 0) {
                    boolean insert = lmMarkerMobileService.save(lmMarkerMobile);
                    //此循环判断有没有缩略图
                    for (MultipartFile multipartFile : file) {
                        //获取到图片名称
                        String originalFilename = multipartFile.getOriginalFilename();
                        //1.上传图片返回地址
                        Integer type = 1;
                        if (originalFilename.length() > 1) {
                            type = Integer.parseInt(originalFilename.substring(0, 1));
                        }
                        if (type == 6) {
                            flag = false;
                        }
                    }

                    if (flag) {
                        return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "位置缩略图不能为空");
                    }


                    //如果图片不为空，则把图片存到
                    for (MultipartFile file1 : file) {
                        //获取到图片名称
                        String originalFilename = file1.getOriginalFilename();

                        //1.上传图片返回地址
                        String path = lmMarkerMobileService.uploadPicture(file1);
                        Integer type = 1;
                        if (originalFilename.length() > 1) {
                            type = Integer.parseInt(originalFilename.substring(0, 1));
                        }
                        //去掉盘符前缀
                        String rePath = null;
                        if (path.length() > 3) {
                            rePath = path.substring(2);
                        }
                        //构造文件名不唯一
                        /*String fileName = rePath.substring(rePath.lastIndexOf("/")+1,rePath.indexOf("."));
                        String key = UUID.randomUUID().toString().replaceAll("-", "");
                        String lastFileName = fileName+"_"+key;
                        String string = rePath.substring(0,rePath.lastIndexOf("/"));
                        String last = rePath.substring(rePath.indexOf("."));

                        String finalPath = string+"/"+lastFileName+last;
                        System.out.println("====finalPath===="+finalPath);*/
                        //2.把图片关联入库
                        prePhotoInfo(lmMarkerMobile, rePath, type);

                    }


                }
                //添加界桩相关方位物信息
                if (position != null && position.size() > 0) {
                    for (Object map1 : position) {
                        Map<String, Object> positionInfo = (Map<String, Object>) map1;

                        LmMarkerRelationPosition lmMarkerRelationPosition = new LmMarkerRelationPosition();
                        lmMarkerRelationPosition.setJzId(lmMarkerMobile.getId());
                        if ((Double) positionInfo.get("distance") == null) {

                            throw new MarkerException(ResultEnum.DISTANCE_EMPTY);
                        }
                        if ((String) positionInfo.get("direction") == null) {
                            throw new MarkerException(ResultEnum.DRECTION_EMPTY);
                        }

                        if ((String) positionInfo.get("reference") == null) {
                            throw new MarkerException(ResultEnum.REFERENCE_EMPTY);
                        }
                        lmMarkerRelationPosition.setDistance((Double) positionInfo.get("distance"));
                        lmMarkerRelationPosition.setDirection((String) positionInfo.get("direction"));
                        lmMarkerRelationPosition.setOfReference((String) positionInfo.get("reference"));
                        //界桩方位物相关信息入库
                        iLmMarkerRelationPositionService.save(lmMarkerRelationPosition);
                    }
                }
                LmMarkerMobileDeviceID lmMarkerMobileDeviceID = new LmMarkerMobileDeviceID();
                lmMarkerMobileDeviceID.setDevice_id(deviceId);
                lmMarkerMobileDeviceID.setId(markerId);
                lmMarkerMobileDeviceID.setLm_id(String.valueOf(lmMarkerMobile.getId()));
                lmMarkerMobileDeviceID.setType(1);
                lmMarkerMobilerDeviceIDService.save(lmMarkerMobileDeviceID);

            } catch (Exception e) {
                log.error("添加界桩信息失败，错误信息为：{}", e.getMessage());
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), e.getMessage());
            }

            return ResultVOUtil.success(uuid);
        } else {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "该数据已经存在");
        }


    }

    public void prePhotoInfo(LmMarkerMobile lmMarkerMobile, String rePath, Integer type) {
        LmMarkerPhoto lmMarkerPhoto = new LmMarkerPhoto();
        lmMarkerPhoto.setJzId(lmMarkerMobile.getId());
        lmMarkerPhoto.setUrl(rePath);
        lmMarkerPhoto.setCreateTime(new Date());
        lmMarkerPhoto.setType(type);
        lmMarkerPhoto.setNumber(lmMarkerMobile.getJzNumber() + "-" + type);
        lmMarkerPhotoService.save(lmMarkerPhoto);
    }

    /**
     * 获取所属红线的界桩列表
     *
     * @param paramsMap
     * @return
     */
    @RequestMapping("/getMarkerListByRedLineId")
    public ResultVO list(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        Integer redLineId = (Integer) dataParam.get("redLineId");

        if (redLineId == null || redLineId == 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "红线id不能为空");
        }
        ResultVO result = lmMarkerMobileService.selectListByRedLineId(redLineId);
        return result;
    }

    /**
     * 获取所属区域的红线列表
     *
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/getMarkerList", method = RequestMethod.POST)
    public ResultVO getMarkerList(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        String code = (String) dataParam.get("code");
        //截取code做模糊查询
        String codes = null;
        if (StringUtils.isNotBlank(code)) {
            Integer level = iLmPointService.getLevelByCode(code);
            if (level != null && level > 0) {
                if (level == 1) {
                    codes = code.substring(0, 2);
                } else if (level == 2) {
                    codes = code.substring(0, 4);
                } else {
                    codes = code;
                }
            } else {
                codes = code;
            }
        }

        String param = (String) dataParam.get("param");

//        if (StringUtils.isBlank(code)) {
//            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "code不能为空");
//        }

        Integer pageNum = (Integer) dataParam.get("pageNum");
        Integer pageSize = (Integer) dataParam.get("pageSize");

        if (pageNum == null) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }

        List<LmMarkerMobile> lmMarkerMobileList = lmMarkerMobileService.selectMarkerList(codes, param, pageNum, pageSize);

        int total = lmMarkerMobileService.selectTotal(codes, param);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", lmMarkerMobileList);
        result.put("total", total);

        return ResultVOUtil.success(result);
    }

    /**
     * 获取与之界桩列表
     *
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/getPreMarkerList", method = RequestMethod.POST)
    public ResultVO getPreMarkerList(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        String code = (String) dataParam.get("code");
        String param = (String) dataParam.get("param");

//        Integer level = (Integer) dataParam.get("level");
        String codes = null;
        if (StringUtils.isNotBlank(code)) {
            Integer level = iLmPointService.getLevelByCode(code);
            if (level != null && level > 0) {
                if (level == 1) {
                    codes = code.substring(0, 2);
                } else if (level == 2) {
                    codes = code.substring(0, 4);
                } else {
                    codes = code;
                }
            } else {
                codes = code;
            }
        }

       /* if (StringUtils.isBlank(code)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "code不能为空");
        }*/

        Integer pageNum = (Integer) dataParam.get("pageNum");
        Integer pageSize = (Integer) dataParam.get("pageSize");

        if (pageNum == null) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }
        List<LmMarkerMobile> lmMarkerMobileList = lmMarkerMobileService.selectPreMarkerList(codes, param, pageNum, pageSize);

        int total = lmMarkerMobileService.selectPreTotal(codes, param);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", lmMarkerMobileList);
        result.put("total", total);

        return ResultVOUtil.success(result);
    }

    /**
     * 获取某个界桩的信息
     *
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/getMarkerById")
    public ResultVO getMarkerById(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        Integer id = (Integer) dataParam.get("id");

        if (id == null || id <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        //1.通过id查询界桩信息
        LmMarkerMobile lmMarkerMobile = lmMarkerMobileService.findMarkerById(id);
        if (lmMarkerMobile == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "查询记录不存在");
        }

        //2.通过id查询界桩图片

        List<LmMarkerPhoto> lmMarkerPhotos = lmMarkerPhotoService.list(new QueryWrapper<LmMarkerPhoto>().eq("jz_id", id));
        lmMarkerMobile.setLmMarkerPhotos(lmMarkerPhotos);

        //3.通过id查询界桩方位物
        List<LmMarkerRelationPosition> positions = iLmMarkerRelationPositionService.list(new QueryWrapper<LmMarkerRelationPosition>().eq("jz_id", id));
        lmMarkerMobile.setLmMarkerRelationPositions(positions);


        //导出word文档并返回地址
        //解决方位物的展示
       /* Map<String, Object> params = new HashMap<>();
        StringBuilder sb = new StringBuilder("");
        if (lmMarkerPhotos != null && lmMarkerPhotos.size() > 0) {
            for (LmMarkerPhoto lmMarkerPhoto : lmMarkerPhotos) {
                if (StringUtils.isNotBlank(lmMarkerPhoto.getNumber())) {
                    sb.append(lmMarkerPhoto.getNumber()).append(",");
                }

            }
        }
        int index = sb.lastIndexOf(",");
        if (!"".equals(sb.toString())) {
            params.put("number", sb.substring(0, index));
        } else {
            params.put("number", "-");
        }

        if (lmMarkerMobile.getVerifyPerson() == null) {
            params.put("verifyPerson", "-");
        } else {
            params.put("verifyPerson", lmMarkerMobile.getVerifyPerson());
        }


        if (lmMarkerMobile.getLmMarkerRelationPositions() == null || lmMarkerMobile.getLmMarkerRelationPositions().size() <= 0) {

            PositionVO positionVO = new PositionVO("-", "-", "-");
            params.put("list1", positionVO);
            params.put("list2", positionVO);
            params.put("list3", positionVO);
        } else if (lmMarkerMobile.getLmMarkerRelationPositions().size() == 1) {
            PositionVO positionVO = new PositionVO("-", "-", "-");
            params.put("list1", lmMarkerMobile.getLmMarkerRelationPositions().get(0));
            params.put("list2", positionVO);
            params.put("list3", positionVO);
        } else if (lmMarkerMobile.getLmMarkerRelationPositions().size() == 2) {
            PositionVO positionVO = new PositionVO("-", "-", "-");
            params.put("list1", lmMarkerMobile.getLmMarkerRelationPositions().get(0));
            params.put("list2", lmMarkerMobile.getLmMarkerRelationPositions().get(1));
            params.put("list3", positionVO);
        } else if (lmMarkerMobile.getLmMarkerRelationPositions().size() == 3) {
            params.put("list1", lmMarkerMobile.getLmMarkerRelationPositions().get(0));
            params.put("list2", lmMarkerMobile.getLmMarkerRelationPositions().get(1));
            params.put("list3", lmMarkerMobile.getLmMarkerRelationPositions().get(2));
        }
        //图片
        Map<String, Object> photoes = new HashMap<>();
        if (lmMarkerPhotos != null && lmMarkerPhotos.size() > 0) {
            for (LmMarkerPhoto lmMarkerPhoto : lmMarkerPhotos) {
                if (lmMarkerPhoto.getType() == 6) {
                    photoes.put("type6", PATH + lmMarkerPhoto.getUrl());
                } else if (lmMarkerPhoto.getType() == 5) {
                    photoes.put("type5", PATH + lmMarkerPhoto.getUrl());
                } else if (lmMarkerPhoto.getType() == 4) {
                    photoes.put("type4", PATH + lmMarkerPhoto.getUrl());
                } else if (lmMarkerPhoto.getType() == 3) {
                    photoes.put("type3", PATH + lmMarkerPhoto.getUrl());
                } else if (lmMarkerPhoto.getType() == 2) {
                    photoes.put("type2", PATH + lmMarkerPhoto.getUrl());
                } else if (lmMarkerPhoto.getType() == 1) {
                    photoes.put("type1", PATH + lmMarkerPhoto.getUrl());

                }
            }

        }

        log.info("获取图片信息", photoes);
        System.out.println(photoes);

        ImageEntity image1 = getImageEntity("type1", photoes);
        if (image1.getUrl() == null) {
            params.put("image1", "-");
            params.put("imagew1", " ");
        } else {
            params.put("image1", image1);
            params.put("imagew1", image1);
        }

        ImageEntity image2 = getImageEntity("type2", photoes);
        if (image2.getUrl() == null) {
            params.put("image2", "-");
            params.put("imagew2", " ");
        } else {
            params.put("image2", image2);
            params.put("imagew2", image2);

        }

        ImageEntity image3 = getImageEntity("type3", photoes);
        if (image3.getUrl() == null) {
            params.put("image3", "-");
            params.put("imagew3", " ");
        } else {
            params.put("image3", image3);
            params.put("imagew3", image3);

        }

        ImageEntity image4 = getImageEntity("type4", photoes);
        if (image4.getUrl() == null) {
            params.put("image4", "-");
            params.put("imagew4", " ");
        } else {
            params.put("image4", image4);
            params.put("imagew4", image4);

        }

        ImageEntity image5 = getImageEntity("type5", photoes);
        if (image5.getUrl() == null) {
            params.put("image5", "-");
            params.put("imagew5", " ");
        } else {
            params.put("image5", image5);
            params.put("imagew5", image5);

        }

        ImageEntity image6 = getImageEntity("type6", photoes);
        if (image6.getUrl() == null) {
            params.put("image6", "-");
            params.put("imagew6", " ");
        } else {
            params.put("image6", image6);
            params.put("imagew6", image6);

        }*/

        lmMarkerMobile.setStrLat(LongitudeUtil.dblToLocation(lmMarkerMobile.getProofLat()));
        lmMarkerMobile.setStrLon(LongitudeUtil.dblToLocation(lmMarkerMobile.getProofLon()));

        /* params.put("data", lmMarkerMobile);
         *//* if(lmMarkerMobile.getJzNumber()==null||"".equals(lmMarkerMobile.getJzNumber())){
            params.put("jzNumber","-");
        }else{
            params.put("jzNumber",lmMarkerMobile.getJzNumber());
        }

        if(lmMarkerMobile.getJzKh()==null||"".equals(lmMarkerMobile.getJzKh())){
            params.put("jzKh","-");
        }else {
            params.put("jzKh",lmMarkerMobile.getJzKh());
        }*//*

        if (lmMarkerMobile.getPlaceName() == null || "".equals(lmMarkerMobile.getPlaceName())) {
            params.put("placeName", "-");
        } else {
            params.put("placeName", lmMarkerMobile.getPlaceName());
        }


        if (lmMarkerMobile.getRemark() == null || "".equals(lmMarkerMobile.getRemark())) {
            params.put("remark", "-");
        } else {
            params.put("remark", lmMarkerMobile.getRemark());
        }
        params.put("time", DateUtils.format(lmMarkerMobile.getCreateTime()));
        if (lmMarkerMobile.getIsBs() == 0) {
            params.put("no", " ☑ ");
            params.put("yes", " 口 ");
        } else {
            params.put("yes", " ☑ ");
            params.put("no", " 口 ");
        }
        try {

            XWPFDocument doc = WordExportUtil.exportWord07(
                    "word/marker.docx", params);
            String fileName = "界碑界桩" + lmMarkerMobile.getJzNumber() + "登记表";
            String lastName = WORD_PATH + fileName + ".docx";
            lmMarkerMobile.setFileUrl(lastName.substring(2));
            FileOutputStream fos = new FileOutputStream(lastName);
            XWPFDocument doc1 = WordExportUtil.exportWord07(
                    "word/markerword.docx", params);
            String fileName1 = "界桩统一" + lmMarkerMobile.getJzNumber() + "登记表";
            String lastName1 = WORD_PATH + fileName1 + ".docx";
            lmMarkerMobile.setWordUrl(lastName1.substring(2));
            FileOutputStream fos1 = new FileOutputStream(lastName1);
            doc1.write(fos1);
            doc.write(fos);
            fos.close();
            fos1.close();


        } catch (Exception e) {
            e.printStackTrace();
        }*/

        return ResultVOUtil.success(lmMarkerMobile);

    }

    /**
     * @param paramsMap
     * @return com.gistone.VO.ResultVO
     * @description:导出word
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/9/26 0026 15:28
     */
    @PostMapping("/exportWord")
    public ResultVO exportWord(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        Integer id = (Integer) dataParam.get("id");
        if (id == null || id <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        //1.通过id查询界桩信息
        LmMarkerMobile lmMarkerMobile = lmMarkerMobileService.findMarkerById(id);


        System.out.println(lmMarkerMobile.toString());
        //如果之前导出过word则不再导出 && StringUtils.isBlank(lmMarkerMobile.getFileUrl())
        if (lmMarkerMobile != null) {
            //2.通过id查询界桩图片

            List<LmMarkerPhoto> lmMarkerPhotos = lmMarkerPhotoService.list(new QueryWrapper<LmMarkerPhoto>().eq("jz_id", id));
            lmMarkerMobile.setLmMarkerPhotos(lmMarkerPhotos);



            //3.通过id查询界桩方位物
            List<LmMarkerRelationPosition> positions = iLmMarkerRelationPositionService.list(new QueryWrapper<LmMarkerRelationPosition>().eq("jz_id", id));
            lmMarkerMobile.setLmMarkerRelationPositions(positions);


            //导出word文档并返回地址
            //解决方位物的展示
            //声明导出word需要的参数

            Map<String, Object> params = new HashMap<>();
            //构造照片编号
            long l = System.currentTimeMillis();
            StringBuilder sb = new StringBuilder("");
            if (lmMarkerPhotos != null && lmMarkerPhotos.size() > 0) {
                for (LmMarkerPhoto lmMarkerPhoto : lmMarkerPhotos) {
                    if (StringUtils.isNotBlank(lmMarkerPhoto.getNumber())) {
                        sb.append(lmMarkerPhoto.getNumber()).append(",");
                    }

                }
            }
            int index = sb.lastIndexOf(",");
            if (!"".equals(sb.toString())) {
                params.put("number", sb.substring(0, index));
            } else {
                params.put("number", "-");
            }
            System.out.println("构造照片编号耗时：" + (l - System.currentTimeMillis()));

            if (lmMarkerMobile.getVerifyPerson() == null) {
                params.put("verifyPerson", "-");
            } else {
                params.put("verifyPerson", lmMarkerMobile.getVerifyPerson());
            }

            long l1 = System.currentTimeMillis();
            if (lmMarkerMobile.getLmMarkerRelationPositions() == null || lmMarkerMobile.getLmMarkerRelationPositions().size() <= 0) {

                PositionVO positionVO = new PositionVO("-", "-", "-");
                params.put("list1", positionVO);
                params.put("list2", positionVO);
                params.put("list3", positionVO);
            } else if (lmMarkerMobile.getLmMarkerRelationPositions().size() == 1) {
                PositionVO positionVO = new PositionVO("-", "-", "-");
                params.put("list1", lmMarkerMobile.getLmMarkerRelationPositions().get(0));
                params.put("list2", positionVO);
                params.put("list3", positionVO);
            } else if (lmMarkerMobile.getLmMarkerRelationPositions().size() == 2) {
                PositionVO positionVO = new PositionVO("-", "-", "-");
                params.put("list1", lmMarkerMobile.getLmMarkerRelationPositions().get(0));
                params.put("list2", lmMarkerMobile.getLmMarkerRelationPositions().get(1));
                params.put("list3", positionVO);
            } else if (lmMarkerMobile.getLmMarkerRelationPositions().size() == 3) {
                params.put("list1", lmMarkerMobile.getLmMarkerRelationPositions().get(0));
                params.put("list2", lmMarkerMobile.getLmMarkerRelationPositions().get(1));
                params.put("list3", lmMarkerMobile.getLmMarkerRelationPositions().get(2));
            }
            System.out.println("构造方位物耗时" + (l1 - System.currentTimeMillis()));

            //构造图片参数

            long l2 = System.currentTimeMillis();
            Map<String, Object> photoes = new HashMap<>();
            if (lmMarkerPhotos != null && lmMarkerPhotos.size() > 0) {
                for (LmMarkerPhoto lmMarkerPhoto : lmMarkerPhotos) {
                    if (lmMarkerPhoto.getType() == 6 && StringUtils.isNotBlank(lmMarkerPhoto.getUrl())) {
                        photoes.put("type6", PATH + lmMarkerPhoto.getUrl());
                    } else if (lmMarkerPhoto.getType() == 5 && StringUtils.isNotBlank(lmMarkerPhoto.getUrl())) {
                        photoes.put("type5", PATH + lmMarkerPhoto.getUrl());
                    } else if (lmMarkerPhoto.getType() == 4 && StringUtils.isNotBlank(lmMarkerPhoto.getUrl())) {
                        photoes.put("type4", PATH + lmMarkerPhoto.getUrl());
                    } else if (lmMarkerPhoto.getType() == 3 && StringUtils.isNotBlank(lmMarkerPhoto.getUrl())) {
                        photoes.put("type3", PATH + lmMarkerPhoto.getUrl());
                    } else if (lmMarkerPhoto.getType() == 2 && StringUtils.isNotBlank(lmMarkerPhoto.getUrl())) {
                        photoes.put("type2", PATH + lmMarkerPhoto.getUrl());
                    } else if (lmMarkerPhoto.getType() == 1 && StringUtils.isNotBlank(lmMarkerPhoto.getUrl())) {
                        photoes.put("type1", PATH + lmMarkerPhoto.getUrl());

                    }
                }

            }

            log.info("获取图片信息", photoes);

            ImageEntity image1 = getImageEntity("type1", photoes);

            if (image1.getUrl() == null) {
                params.put("image1", "-");
                params.put("imagew1", " ");
            } else {
                params.put("image1", image1);
                params.put("imagew1", image1);
            }

            ImageEntity image2 = getImageEntity("type2", photoes);
            if (image2.getUrl() == null) {
                params.put("image2", "-");
                params.put("imagew2", " ");
            } else {
                params.put("image2", image2);
                params.put("imagew2", image2);

            }

            ImageEntity image3 = getImageEntity("type3", photoes);
            if (image3.getUrl() == null) {
                params.put("image3", "-");
                params.put("imagew3", " ");
            } else {
                params.put("image3", image3);
                params.put("imagew3", image3);

            }

            ImageEntity image4 = getImageEntity("type4", photoes);
            if (image4.getUrl() == null) {
                params.put("image4", "-");
                params.put("imagew4", " ");
            } else {
                params.put("image4", image4);
                params.put("imagew4", image4);

            }

            ImageEntity image5 = getImageEntity("type5", photoes);
            if (image5.getUrl() == null) {
                params.put("image5", "-");
                params.put("imagew5", " ");
            } else {
                params.put("image5", image5);
                params.put("imagew5", image5);

            }

            ImageEntity image6 = getImageEntity("type6", photoes);
            if (image6.getUrl() == null) {
                params.put("image6", "-");
                params.put("imagew6", " ");
            } else {
                params.put("image6", image6);
                params.put("imagew6", image6);

            }


            System.out.println("构造图片耗时：" + (l2 - System.currentTimeMillis()));

            lmMarkerMobile.setStrLat(LongitudeUtil.dblToLocation(lmMarkerMobile.getProofLat()));
            lmMarkerMobile.setStrLon(LongitudeUtil.dblToLocation(lmMarkerMobile.getProofLon()));

            params.put("data", lmMarkerMobile);
       /* if(lmMarkerMobile.getJzNumber()==null||"".equals(lmMarkerMobile.getJzNumber())){
            params.put("jzNumber","-");
        }else{
            params.put("jzNumber",lmMarkerMobile.getJzNumber());
        }

        if(lmMarkerMobile.getJzKh()==null||"".equals(lmMarkerMobile.getJzKh())){
            params.put("jzKh","-");
        }else {
            params.put("jzKh",lmMarkerMobile.getJzKh());
        }*/

            if (lmMarkerMobile.getPlaceName() == null || "".equals(lmMarkerMobile.getPlaceName())) {
                params.put("placeName", "-");
            } else {
                params.put("placeName", lmMarkerMobile.getPlaceName());
            }


            if (lmMarkerMobile.getRemark() == null || "".equals(lmMarkerMobile.getRemark())) {
                params.put("remark", "-");
            } else {
                params.put("remark", lmMarkerMobile.getRemark());
            }
            params.put("time", DateUtils.format(lmMarkerMobile.getCreateTime()));
            if (lmMarkerMobile.getIsBs() == 0) {
                params.put("no", " ☑ ");
                params.put("yes", " 口 ");
            } else {
                params.put("yes", " ☑ ");
                params.put("no", " 口 ");
            }

            System.out.println("准备参数总耗时：" + (l - System.currentTimeMillis()));
            try {
                long l4 = System.currentTimeMillis();
                XWPFDocument doc = WordExportUtil.exportWord07("word/markerword.docx", params);


                String fileName = "界桩" + lmMarkerMobile.getJzNumber() + "登记表";
                String lastName = WORD_PATH + fileName + ".docx";
                lmMarkerMobile.setFileUrl(lastName.substring(2));
                FileOutputStream fos = new FileOutputStream(lastName);

                lmMarkerMobileService.updateById(lmMarkerMobile);

                doc.write(fos);
                fos.close();
                System.out.println("导出word总耗时" + (l4 - System.currentTimeMillis()));


            } catch (Exception e) {
                e.printStackTrace();
            }

            return ResultVOUtil.success(lmMarkerMobile.getFileUrl());
        }
        return ResultVOUtil.success(lmMarkerMobile.getFileUrl());
    }

    /**
     * @param paramsMap
     * @return com.gistone.VO.ResultVO
     * @description:导出统一的word
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/9/26 0026 15:41
     */
    @PostMapping("/exportStandardWord")
    public ResultVO exportStandardWord(@RequestBody Map<String, Object> paramsMap) {

        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        Integer id = (Integer) dataParam.get("id");
        if (id == null || id <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        //1.通过id查询界桩信息
        LmMarkerMobile lmMarkerMobile = lmMarkerMobileService.findMarkerById(id);


        System.out.println(lmMarkerMobile.toString());
        //如果之前导出过word则不再导出
        if (lmMarkerMobile != null && StringUtils.isNotBlank(lmMarkerMobile.getWordUrl())) {
            //2.通过id查询界桩图片

            List<LmMarkerPhoto> lmMarkerPhotos = lmMarkerPhotoService.list(new QueryWrapper<LmMarkerPhoto>().eq("jz_id", id));
            lmMarkerMobile.setLmMarkerPhotos(lmMarkerPhotos);

            //3.通过id查询界桩方位物
            List<LmMarkerRelationPosition> positions = iLmMarkerRelationPositionService.list(new QueryWrapper<LmMarkerRelationPosition>().eq("jz_id", id));
            lmMarkerMobile.setLmMarkerRelationPositions(positions);


            //导出word文档并返回地址
            //解决方位物的展示
            //声明导出word需要的参数

            Map<String, Object> params = new HashMap<>();
            //构造照片编号
            long l = System.currentTimeMillis();
            StringBuilder sb = new StringBuilder("");
            if (lmMarkerPhotos != null && lmMarkerPhotos.size() > 0) {
                for (LmMarkerPhoto lmMarkerPhoto : lmMarkerPhotos) {
                    if (StringUtils.isNotBlank(lmMarkerPhoto.getNumber())) {
                        sb.append(lmMarkerPhoto.getNumber()).append(",");
                    }

                }
            }
            int index = sb.lastIndexOf(",");
            if (!"".equals(sb.toString())) {
                params.put("number", sb.substring(0, index));
            } else {
                params.put("number", "-");
            }
            System.out.println("构造照片编号耗时：" + (l - System.currentTimeMillis()));

            if (lmMarkerMobile.getVerifyPerson() == null) {
                params.put("verifyPerson", "-");
            } else {
                params.put("verifyPerson", lmMarkerMobile.getVerifyPerson());
            }

            long l1 = System.currentTimeMillis();
            if (lmMarkerMobile.getLmMarkerRelationPositions() == null || lmMarkerMobile.getLmMarkerRelationPositions().size() <= 0) {

                PositionVO positionVO = new PositionVO("-", "-", "-");
                params.put("list1", positionVO);
                params.put("list2", positionVO);
                params.put("list3", positionVO);
            } else if (lmMarkerMobile.getLmMarkerRelationPositions().size() == 1) {
                PositionVO positionVO = new PositionVO("-", "-", "-");
                params.put("list1", lmMarkerMobile.getLmMarkerRelationPositions().get(0));
                params.put("list2", positionVO);
                params.put("list3", positionVO);
            } else if (lmMarkerMobile.getLmMarkerRelationPositions().size() == 2) {
                PositionVO positionVO = new PositionVO("-", "-", "-");
                params.put("list1", lmMarkerMobile.getLmMarkerRelationPositions().get(0));
                params.put("list2", lmMarkerMobile.getLmMarkerRelationPositions().get(1));
                params.put("list3", positionVO);
            } else if (lmMarkerMobile.getLmMarkerRelationPositions().size() == 3) {
                params.put("list1", lmMarkerMobile.getLmMarkerRelationPositions().get(0));
                params.put("list2", lmMarkerMobile.getLmMarkerRelationPositions().get(1));
                params.put("list3", lmMarkerMobile.getLmMarkerRelationPositions().get(2));
            }
            System.out.println("构造方位物耗时" + (l1 - System.currentTimeMillis()));

            //构造图片参数

            long l2 = System.currentTimeMillis();
            Map<String, Object> photoes = new HashMap<>();
            if (lmMarkerPhotos != null && lmMarkerPhotos.size() > 0) {
                for (LmMarkerPhoto lmMarkerPhoto : lmMarkerPhotos) {
                    if (lmMarkerPhoto.getType() == 6) {
                        photoes.put("type6", PATH + lmMarkerPhoto.getUrl());
                    } else if (lmMarkerPhoto.getType() == 5) {
                        photoes.put("type5", PATH + lmMarkerPhoto.getUrl());
                    } else if (lmMarkerPhoto.getType() == 4) {
                        photoes.put("type4", PATH + lmMarkerPhoto.getUrl());
                    } else if (lmMarkerPhoto.getType() == 3) {
                        photoes.put("type3", PATH + lmMarkerPhoto.getUrl());
                    } else if (lmMarkerPhoto.getType() == 2) {
                        photoes.put("type2", PATH + lmMarkerPhoto.getUrl());
                    } else if (lmMarkerPhoto.getType() == 1) {
                        photoes.put("type1", PATH + lmMarkerPhoto.getUrl());

                    }
                }

            }

            log.info("获取图片信息", photoes);

            ImageEntity image1 = getImageEntity("type1", photoes);
            if (image1.getUrl() == null) {
                params.put("imagew1", " ");
            } else {
                params.put("imagew1", image1);
            }

            ImageEntity image2 = getImageEntity("type2", photoes);
            if (image2.getUrl() == null) {
                params.put("imagew2", " ");
            } else {
                params.put("imagew2", image2);

            }

            ImageEntity image3 = getImageEntity("type3", photoes);
            if (image3.getUrl() == null) {
                params.put("imagew3", " ");
            } else {
                params.put("imagew3", image3);

            }

            ImageEntity image4 = getImageEntity("type4", photoes);
            if (image4.getUrl() == null) {
                params.put("imagew4", " ");
            } else {
                params.put("imagew4", image4);

            }

            ImageEntity image5 = getImageEntity("type5", photoes);
            if (image5.getUrl() == null) {
                params.put("imagew5", " ");
            } else {
                params.put("imagew5", image5);

            }

            ImageEntity image6 = getImageEntity("type6", photoes);
            if (image6.getUrl() == null) {
                params.put("imagew6", " ");
            } else {
                params.put("imagew6", image6);

            }

            System.out.println("构造图片耗时：" + (l2 - System.currentTimeMillis()));

            lmMarkerMobile.setStrLat(LongitudeUtil.dblToLocation(lmMarkerMobile.getProofLat()));
            lmMarkerMobile.setStrLon(LongitudeUtil.dblToLocation(lmMarkerMobile.getProofLon()));

            params.put("data", lmMarkerMobile);
       /* if(lmMarkerMobile.getJzNumber()==null||"".equals(lmMarkerMobile.getJzNumber())){
            params.put("jzNumber","-");
        }else{
            params.put("jzNumber",lmMarkerMobile.getJzNumber());
        }

        if(lmMarkerMobile.getJzKh()==null||"".equals(lmMarkerMobile.getJzKh())){
            params.put("jzKh","-");
        }else {
            params.put("jzKh",lmMarkerMobile.getJzKh());
        }*/

            if (lmMarkerMobile.getPlaceName() == null || "".equals(lmMarkerMobile.getPlaceName())) {
                params.put("placeName", "-");
            } else {
                params.put("placeName", lmMarkerMobile.getPlaceName());
            }


            if (lmMarkerMobile.getRemark() == null || "".equals(lmMarkerMobile.getRemark())) {
                params.put("remark", "-");
            } else {
                params.put("remark", lmMarkerMobile.getRemark());
            }
            params.put("time", DateUtils.format(lmMarkerMobile.getCreateTime()));
            if (lmMarkerMobile.getIsBs() == 0) {
                params.put("no", " ☑ ");
                params.put("yes", " 口 ");
            } else {
                params.put("yes", " ☑ ");
                params.put("no", " 口 ");
            }

            System.out.println("准备参数总耗时：" + (l - System.currentTimeMillis()));
            try {
                long l4 = System.currentTimeMillis();
              /*  XWPFDocument doc = WordExportUtil.exportWord07(
                        "word/marker.docx", params);
                String fileName = "界碑界桩" + lmMarkerMobile.getJzNumber() + "登记表";
                String lastName = WORD_PATH + fileName + ".docx";
                lmMarkerMobile.setFileUrl(lastName.substring(2));
                FileOutputStream fos = new FileOutputStream(lastName);*/
                XWPFDocument doc1 = WordExportUtil.exportWord07(
                        "word/markerword.docx", params);
                String fileName1 = "界桩统一" + lmMarkerMobile.getJzNumber() + "登记表";
                String lastName1 = WORD_PATH + fileName1 + ".docx";
                lmMarkerMobile.setWordUrl(lastName1.substring(2));
                FileOutputStream fos1 = new FileOutputStream(lastName1);
                doc1.write(fos1);
               /* doc.write(fos);
                fos.close();*/
                System.out.println("导出word总耗时" + (l4 - System.currentTimeMillis()));
                fos1.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return ResultVOUtil.success(lmMarkerMobile.getWordUrl());
        }
        return ResultVOUtil.success(lmMarkerMobile.getWordUrl());
    }

    public ImageEntity getImageEntity(String type, Map<String, Object> photoes) {
        ImageEntity image = new ImageEntity();
        float height = 0;
        if (photoes.get(type) != null) {
            //拿到图片，并且获取图片大小
            String url = (String) photoes.get(type);
            File picture = new File(url);

            try {
                BufferedImage sourceImg = ImageIO.read(new FileInputStream(picture));

                System.out.println(sourceImg.getHeight());
                System.out.println(sourceImg.getWidth());
                float proportion = (float) sourceImg.getHeight() / sourceImg.getWidth();
                height = proportion * 85;
                System.out.println("height===="+height);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        image.setHeight((int) height);
        image.setWidth(85);
        image.setUrl((String) photoes.get(type));
        image.setType(ImageEntity.URL);
        return image;
    }

    /**
     * 移动端，获取总界桩数据
     *
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/getAllMarkerList", method = RequestMethod.POST)
    public ResultVO getAllMarkerList(@RequestBody Map<String, Object> paramsMap) {

        List<LmMarkerMobile> lmMarkerMobileList = lmMarkerMobileService.selectAllMarkerList();

        return ResultVOUtil.success(lmMarkerMobileList);
    }

    @RequestMapping(value = "/getAllPreMarkerList", method = RequestMethod.POST)
    public ResultVO getAllPreMarkerList() {
        List<LmMarkerMobile> lmMarkerMobileList = lmMarkerMobileService.selectAllPreMarkerList();

        return ResultVOUtil.success(lmMarkerMobileList);
    }

    /**
     * 通过经纬度查询最近点界桩信息
     *
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/getMarkerByCoordinate", method = RequestMethod.POST)
    public ResultVO getMarkerByCoordinate(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        Double longitude = (Double) dataParam.get("longitude");
        if (longitude == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "经度不能为空");
        }

        Double latitude = (Double) dataParam.get("latitude");
        if (latitude == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "纬度不能为空");
        }

        LmMarkerMobile lmMarkerMobile = lmMarkerMobileService.getMarkerByCoordinate(longitude, latitude);
        if (lmMarkerMobile != null) {
            //2.通过id查询界桩图片
            List<LmMarkerPhoto> lmMarkerPhotos = lmMarkerPhotoService.list(new QueryWrapper<LmMarkerPhoto>().eq("jz_id", lmMarkerMobile.getId()));
            lmMarkerMobile.setLmMarkerPhotos(lmMarkerPhotos);

            //3.通过id查询界桩方位物
            List<LmMarkerRelationPosition> positions = iLmMarkerRelationPositionService.list(new QueryWrapper<LmMarkerRelationPosition>().eq("jz_id", lmMarkerMobile.getId()));
            lmMarkerMobile.setLmMarkerRelationPositions(positions);
        }


        return ResultVOUtil.success(lmMarkerMobile);
    }

    /**
     * @param paramsMap
     * @return com.gistone.VO.ResultVO
     * @description:判断用户是否存在
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/9/26 0026 13:59
     */
    @PostMapping("/exitUserByPhone")
    public ResultVO exitUserByPhone(@RequestBody Map<String, Object> paramsMap) {
        String phone = (String) paramsMap.get("phone");
        if (StringUtils.isBlank(phone)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "手机号不能为空");
        }
        SysUser user = sysUserService.getOne(new QueryWrapper<SysUser>().eq("username", phone).eq("enable", 1).eq("type", 1));
        if (user == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "用户不存在,请核对手机号");
        }

        return ResultVOUtil.success();
    }


    /**
     * ljk
     */
    @RequestMapping(value = "/importZipCsv", method = RequestMethod.POST)
    public ResultVO importZipCsv(@RequestParam("phone") String phone, @RequestParam("type") Integer type, @RequestParam("file") MultipartFile file) throws Exception {
        ResultVO resultVO = ResultVOUtil.success();
        try {

            SysUser map = sysUserService.getOne(new QueryWrapper<SysUser>().eq("username", phone).eq("enable", 1).eq("type", 1));/*sysUserFeign.getByPhone(phone);*/

            String fileNameNoIndex = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
            String fileName = FileUtil.fileUp(file, configUtils.getZIP_PATH(), fileNameNoIndex);
            String s = ZipUtils.unZip(configUtils.getZIP_PATH() + fileName, configUtils.getZIP_DECOM_PATH());
//            FileReader fReader = new FileReader(configUtils.getZIP_DECOM_PATH() + fileNameNoIndex + "\\" + fileNameNoIndex + ".csv");
//            CSVReader csvReader = new CSVReader(fReader);
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(configUtils.getZIP_DECOM_PATH() + fileNameNoIndex + "\\" + fileNameNoIndex + ".csv"), "UTF-8");
            CSVReader csvReader = new CSVReader(inputStreamReader);
            List<String[]> list = csvReader.readAll();
            csvReader.close();
            List<String> stringList = Arrays.asList(list.get(0));
            if (stringList.size() == 18) {
                if (type == 1) {
                    resultVO = iLmMarkerDeviceIDService.importZipCsv(list, map.getId().toString(), fileNameNoIndex);
                } else {
                    return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "请选择正确的标识牌文件!");
                }
            } else {
                if (stringList.size() == 15) {
                    if (type == 2) {
                        resultVO = iLmBoardService.importZipCsv(list, map.getId().toString(), fileNameNoIndex);
                    } else {
                        return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "请选择正确的界桩文件!");
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "文件出现未知错误!");
        }
        return resultVO;
    }

    /**
     * @return
     * @description: 获取行政区划级别
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/5/15 0015 11:17
     */
    @RequestMapping(value = "/getLevel")
    public Integer gerLevel(@RequestParam(value = "code", required = false) String code) {
        Integer level = iLmPointService.getLevelByCode(code);
        return level;
    }

    @RequestMapping(value = "/import")
    public void importMarkerData() {
/*
        String path = "D:/Work/gistone/static/shapefile/redline_p_jbjz.shp";
        ReadShapeFile readShapeFile = new ReadShapeFile();
        ArrayList<LmMarkerMobile> lmMarkerMobiles = readShapeFile.readShapeFile(path);

        if (lmMarkerMobiles != null && lmMarkerMobiles.size() > 0) {

            for (int i = 0; i < lmMarkerMobiles.size(); i++) {
                String ss = "";
                if ((i + "").length() == 1) {
                    ss = "000" + i;
                } else if ((i + "").length() == 2) {
                    ss = "00" + i;
                } else if ((i + "").length() == 3) {
                    ss = "0" + i;
                }
                lmMarkerMobiles.get(i).setType(0);
//                lmMarkerMobiles.get(i).setJzNumber(lmMarkerMobiles.get(i).getJzNumber() + "-" + ss);
                lmMarkerMobiles.get(i).setSaveTime(new Date());
            }
        }

        System.out.println(lmMarkerMobiles);

        //先将之前的数据删除
        lmMarkerMobileService.deleteAll();

        lmMarkerMobileService.saveBatch(lmMarkerMobiles);*/
    }

    @RequestMapping("export_PreExcel")
    public ResultVO export_PreExcel(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) map.get("data");
        String code = (String) dataParam.get("code");
        String param = (String) dataParam.get("param");
        String codes = null;
        if (StringUtils.isNotBlank(code)) {
            Integer level = iLmPointService.getLevelByCode(code);
            if (level != null && level > 0) {
                if (level == 1) {
                    codes = code.substring(0, 2);
                } else if (level == 2) {
                    codes = code.substring(0, 4);
                } else {
                    codes = code;
                }
            } else {
                codes = code;
            }
        }
        List<LmMarkerMobileVO> lmMarkerMobileList = lmMarkerMobileService.selectPreMarkerListForAll(codes, param);
        String filepath = FileUtil.toXls("预设界桩", lmMarkerMobileList, configUtils.getExcel_PATH(), LmMarkerMobileVO.class, response);
        Map map1 = new HashMap();
        map1.put("filepath", filepath.substring(2));
        return ResultVOUtil.success(map1);
    }

    @RequestMapping("export_Excel")
    public ResultVO export_Excel(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) map.get("data");
        String code = (String) dataParam.get("code");
        String param = (String) dataParam.get("param");
        String codes = null;
        if (StringUtils.isNotBlank(code)) {
            Integer level = iLmPointService.getLevelByCode(code);
            if (level != null && level > 0) {
                if (level == 1) {
                    codes = code.substring(0, 2);
                } else if (level == 2) {
                    codes = code.substring(0, 4);
                } else {
                    codes = code;
                }
            } else {
                codes = code;
            }
        }
        List<LmMarkerMobileVO> lmMarkerMobileList = lmMarkerMobileService.selectMarkerListForAll(codes, param);
        String filepath = FileUtil.toXls("实际界桩", lmMarkerMobileList, configUtils.getExcel_PATH(), LmMarkerMobileVO.class, response);
        Map map1 = new HashMap();
        map1.put("filepath", filepath.substring(2));
        return ResultVOUtil.success(map1);
    }

    @PostMapping(value = "/delete")
    public ResultVO delete(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        Integer id = (Integer) dataParam.get("id");

        if (id == null && id < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "界桩id不能为空");
        }

        lmMarkerMobileService.delete(id);
        return ResultVOUtil.success();


    }

    @PostMapping("/deletedList")
    public ResultVO deletedList(@RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }


        Integer pageNum = (Integer) dataParam.get("pageNum");
        Integer pageSize = (Integer) dataParam.get("pageSize");

        if (pageNum == null) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }

        Map<String, Object> result = lmMarkerMobileService.list(pageNum, pageSize);

        return ResultVOUtil.success(result);
    }

    @PostMapping("/recover")
    public ResultVO recoverData(@RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        Integer id = (Integer) dataParam.get("id");

        if (id == null || id < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }

        lmMarkerMobileService.recover(id);
        return ResultVOUtil.success();
    }

    @PostMapping("/deleteForever")
    public ResultVO deleteForever(@RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        Integer id = (Integer) dataParam.get("id");

        if (id == null || id < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }

        lmMarkerMobileService.deleteForever(id);

        return ResultVOUtil.success();
    }

}

