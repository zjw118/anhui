package com.gistone.controller;


import cn.afterturn.easypoi.entity.ImageEntity;
import cn.afterturn.easypoi.word.WordExportUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.VO.ResultVO;
import com.gistone.annotation.SysLog;
import com.gistone.entity.EXCEL.LmBoardVO;
import com.gistone.entity.LmBoard;
import com.gistone.entity.LmBoardDeviceID;
import com.gistone.entity.LmBoardPhoto;
import com.gistone.mapper.LmBoardMapper;
import com.gistone.mapper.LmMarkerMobileMapper;
import com.gistone.service.*;
import com.gistone.util.*;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-04-29
 */
@RestController
@RequestMapping("/api/ktdb/lmBoard")
@Slf4j
@Transactional
public class LmBoardController {

    @Autowired
    private ILmBoardService lmBoardService;

    @Autowired
    private UploadPictureService uploadPicture;

    @Autowired
    private ILmBoardPhotoService lmBoardPhotoService;

    @Autowired
    private ILmPointService iLmPointService;

    @Autowired
    private ILmBoardDeviceIDService lmBoardDeviceIDService;

    @Autowired
    private LmMarkerMobileMapper lmMarkerMobileMapper;
    @Autowired
    private ConfigUtils configUtils;

    @Autowired
    private LmBoardMapper lmBoardMapper;

    @Value("${WORD_PATH}")
    private String WORD_PATH;
    @Value("${PATH}")
    private String PATH;

    @PostMapping(value = "/add")
    @SysLog("添加标识牌")
    public ResultVO addBoard(@RequestParam("data") String data, MultipartFile[] file) {
        if (StringUtils.isBlank(data)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "data不能为空");
        }
        JSONObject a = JSONObject.fromObject(data);
//        Map<String, Object> map = JsonToMapUtil.parseJSON2Map(a);

        JSONObject dataParam = (JSONObject) a.get("data");

        String uuid = (String) dataParam.get("uuid");

        LmBoard lmBoard = new LmBoard();

        String verifyPerson = (String) dataParam.get("verifyPerson");
        if (StringUtils.isBlank(verifyPerson)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "审核人id不能为空");
        }

        lmBoard.setVerifyPerson(verifyPerson);
        String redlineNum = (String) dataParam.get("redlineNum");
        if (StringUtils.isBlank(redlineNum)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "红线编码不能为空");
        }
        lmBoard.setRedlineNum(redlineNum);

        Integer redlineId = (Integer) dataParam.get("redlineId");
        if (redlineId == null || redlineId < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "所属红线id不能为空");
        }

        lmBoard.setRedlineId(redlineId);

        String boardNum = (String) dataParam.get("boardNum");

        if (StringUtils.isBlank(boardNum)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "标识牌编号不能为空");
        }
        lmBoard.setNumber(boardNum);

        String code = (String) dataParam.get("code");
        if (StringUtils.isBlank(code)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "行政区划号不能为空");
        }
        lmBoard.setCode(code);

        String content = (String) dataParam.get("content");
        if (StringUtils.isBlank(content)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "标识牌内容不能为空");
        }
        lmBoard.setContent(content);

        Double proofLog = (Double) dataParam.get("proofLon");
        if (proofLog == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "校对经度不能未为空");
        }
        lmBoard.setProofLon(proofLog);


        Double proofLat = (Double) dataParam.get("proofLat");
        if (proofLat == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "校对纬度不能为空");
        }
        lmBoard.setProofLat(proofLat);

        Double longitude = (Double) dataParam.get("longitude");
        if (longitude != null) {
            lmBoard.setLongitude(longitude);
        }


        Double latitude = (Double) dataParam.get("latitude");
        if (latitude != null) {
            lmBoard.setLatitude(latitude);
        }


        String remark = (String) dataParam.get("remark");
        if (StringUtils.isNotBlank(remark)) {
            lmBoard.setRemarks(remark);
        }

        Integer createId = (Integer) dataParam.get("userId");
        if (createId == null || createId <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "用户id不能为空");
        }
        lmBoard.setCreateBy(createId);

        String createTime = (String) dataParam.get("createTime");

        if (StringUtils.isBlank(createTime)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "填表时间不能为空");
        }
        lmBoard.setCreateDate(DateUtils.StrtoDateYMD(createTime));

        String updateTime = (String) dataParam.get("updateTime");
        if (StringUtils.isNotBlank(updateTime)) {
            lmBoard.setUpdateDate(DateUtils.StrtoDateYMD(updateTime));
        }
        lmBoard.setSaveDate(new Date());
        lmBoard.setType(1);

        //1.判断参数是否为空
        //2.判断数据是否存在
        //3.如果存在则不添加，否则添加，并且插入记录

        String boardId = (String) dataParam.get("dataId");
        if (StringUtils.isBlank(boardId)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "数据id不能为空");
        }
        String deviceId = (String) dataParam.get("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "设备id不能为空");
        }

        LmBoardDeviceID one = lmBoardDeviceIDService.getOne(new QueryWrapper<LmBoardDeviceID>().eq("id", boardId).eq("device_id", deviceId));
        if (one == null) {

            try {
                boolean insert = lmBoardService.save(lmBoard);

                // 图片上传
                if (file != null && file.length > 0) {
                    //如果图片不为空，则把图片存到
                    for (MultipartFile file1 : file) {

                        //获取到图片名称
                        String originalFilename = file1.getOriginalFilename();
                        String modelName = "ktdb/";
                        //1.上传图片返回地址
                        String path = uploadPicture.uploadPicture(file1, modelName);
                        Integer type = 1;
                        if (originalFilename.length() > 1) {
                            type = Integer.parseInt(originalFilename.substring(0, 1));
                        }
                        //去掉盘符前缀
                        String rePath = null;
                        if (path.length() > 3) {
                            rePath = path.substring(2);
                        }
                        //2.把图片关联入库
                        LmBoardPhoto lmBoardPhoto = new LmBoardPhoto();
                        lmBoardPhoto.setBoardId(lmBoard.getId());
                        lmBoardPhoto.setUrl(rePath);
                        lmBoardPhoto.setCreateTime(new Date());
                        lmBoardPhoto.setType(type);
                        lmBoardPhoto.setNumber(lmBoard.getNumber() + "-" + type);
                        lmBoardPhotoService.save(lmBoardPhoto);

                    }
                }

                LmBoardDeviceID lmBoardDeviceID = new LmBoardDeviceID();
                lmBoardDeviceID.setDevice_id(deviceId);
                lmBoardDeviceID.setId(boardId);
                lmBoardDeviceID.setLmBoard_id(String.valueOf(lmBoard.getId()));
                lmBoardDeviceID.setType(1);
                lmBoardDeviceIDService.save(lmBoardDeviceID);
            } catch (Exception e) {
                log.error("添加标识牌失败，异常信息：{}", e.getMessage());
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "添加标识牌失败");
            }
            return ResultVOUtil.success(uuid);

        } else {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "该数据已存在");
        }


    }


    @PostMapping(value = "/getLmBoardList")
    public ResultVO getLmBoardList(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        String boardNum = (String) dataParam.get("boardNum");

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

        Integer pageNum = (Integer) dataParam.get("pageNum");
        Integer pageSize = (Integer) dataParam.get("pageSize");


        if (pageNum == null) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }

        Map<String, Object> result = lmBoardService.getBoardList(boardNum, codes, pageNum, pageSize);


        return ResultVOUtil.success(result);
    }

    @PostMapping("/getLmboardById")
    public ResultVO getLmboardById(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        Integer id = (Integer) dataParam.get("id");
        if (id == null || id < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        LmBoard lmBoard = lmBoardService.getById(id);

        /*Map<String, Object> params = new HashMap<>();


        if (StringUtils.isBlank(lmBoard.getVerifyPerson())) {
            params.put("verifyPerson", "-");
        } else {
            params.put("verifyPerson", lmBoard.getVerifyPerson());
        }*/
        String createUser = lmBoardMapper.selectUserName(lmBoard.getCreateBy());
        lmBoard.setCreateUser(createUser);
        String placeName = lmBoardMapper.selectPlaceName(lmBoard.getCode());
        lmBoard.setPlaceName(placeName);
       /* if (lmBoard.getPlaceName() == null || "".equals(lmBoard.getPlaceName())) {
            params.put("placeName", "-");
        } else {
            params.put("placeName", lmBoard.getPlaceName());
        }*/

        List<LmBoardPhoto> photos = lmBoardPhotoService.list(new QueryWrapper<LmBoardPhoto>().eq("board_id", lmBoard.getId()));
        lmBoard.setLmBoardPhotos(photos);
      /*  //设置照片编号
        StringBuilder sb = new StringBuilder("");
        if (photos != null && photos.size() > 0) {
            for (LmBoardPhoto lmMarkerPhoto : photos) {
                if (StringUtils.isNotBlank(lmMarkerPhoto.getNumber())) {
                    sb.append(lmMarkerPhoto.getNumber()).append(",");
                }

            }
        }
        int index = sb.lastIndexOf(",");
        System.out.println(photos);*/
       /* if (!"".equals(sb)) {
            params.put("number", sb.substring(0, index));
        } else {
            params.put("number", "-");
        }*/

       /* Map<String, Object> photoes = new HashMap<>();
        if (photos != null && photos.size() > 0) {
            for (LmBoardPhoto lmMarkerPhoto : photos) {
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

        if (lmBoard.getRemarks() == null || "".equals(lmBoard.getRemarks())) {
            params.put("remark", "-");
        } else {
            params.put("remark", lmBoard.getRemarks());
        }
        params.put("time", DateUtils.format(lmBoard.getCreateDate()));*/
        lmBoard.setStrLat(LongitudeUtil.dblToLocation(lmBoard.getProofLat()));
        lmBoard.setStrLon(LongitudeUtil.dblToLocation(lmBoard.getProofLon()));
//        params.put("data", lmBoard);

        /*try {

            XWPFDocument doc = WordExportUtil.exportWord07(
                    "word/board.docx", params);
            String fileName = "标识牌" + lmBoard.getNumber() + "登记表";
            String lastName = WORD_PATH + fileName + ".docx";
            lmBoard.setFileUrl(lastName.substring(2));
            FileOutputStream fos = new FileOutputStream(lastName);
            doc.write(fos);
            fos.close();

            XWPFDocument doc1 = WordExportUtil.exportWord07(
                    "word/boardWord.docx", params);
            String fileName1 = "标识牌标准" + lmBoard.getNumber() + "登记表";
            String lastName1 = WORD_PATH + fileName1 + ".docx";
            lmBoard.setWordUrl(lastName1.substring(2));
            FileOutputStream fos1 = new FileOutputStream(lastName1);
            doc1.write(fos1);
            fos1.close();


        } catch (Exception e) {
            e.printStackTrace();
        }*/


        return ResultVOUtil.success(lmBoard);
    }

    @PostMapping("/exportWord")
    @SysLog("导出word登记表")
    public ResultVO exportWord(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        Integer id = (Integer) dataParam.get("id");
        if (id == null || id < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        LmBoard lmBoard = lmBoardService.getById(id);

        if (lmBoard != null && StringUtils.isBlank(lmBoard.getFileUrl())) {


            Map<String, Object> params = new HashMap<>();


            if (StringUtils.isBlank(lmBoard.getVerifyPerson())) {
                params.put("verifyPerson", "-");
            } else {
                params.put("verifyPerson", lmBoard.getVerifyPerson());
            }
            String createUser = lmBoardMapper.selectUserName(lmBoard.getCreateBy());
            lmBoard.setCreateUser(createUser);
            String placeName = lmBoardMapper.selectPlaceName(lmBoard.getCode());
            lmBoard.setPlaceName(placeName);
            if (lmBoard.getPlaceName() == null || "".equals(lmBoard.getPlaceName())) {
                params.put("placeName", "-");
            } else {
                params.put("placeName", lmBoard.getPlaceName());
            }

            List<LmBoardPhoto> photos = lmBoardPhotoService.list(new QueryWrapper<LmBoardPhoto>().eq("board_id", lmBoard.getId()));
            lmBoard.setLmBoardPhotos(photos);
            //设置照片编号
            StringBuilder sb = new StringBuilder("");
            if (photos != null && photos.size() > 0) {
                for (LmBoardPhoto lmMarkerPhoto : photos) {
                    if (StringUtils.isNotBlank(lmMarkerPhoto.getNumber())) {
                        sb.append(lmMarkerPhoto.getNumber()).append(",");
                    }

                }
            }
            int index = sb.lastIndexOf(",");
            System.out.println(photos);
            if (!"".equals(sb.toString())) {
                params.put("number", sb.substring(0, index));
            } else {
                params.put("number", "-");
            }

            Map<String, Object> photoes = new HashMap<>();
            if (photos != null && photos.size() > 0) {
                for (LmBoardPhoto lmMarkerPhoto : photos) {
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

            ImageEntity image1 = getImageEntity("type1", photoes);
            if (image1.getUrl() == null) {
                params.put("image1", "-");
            } else {
                params.put("image1", image1);
            }

            ImageEntity image2 = getImageEntity("type2", photoes);
            if (image2.getUrl() == null) {
                params.put("image2", "-");
            } else {
                params.put("image2", image2);
            }

            ImageEntity image3 = getImageEntity("type3", photoes);
            if (image3.getUrl() == null) {
                params.put("image3", "-");
            } else {
                params.put("image3", image3);
            }

            ImageEntity image4 = getImageEntity("type4", photoes);
            if (image4.getUrl() == null) {
                params.put("image4", "-");
            } else {
                params.put("image4", image4);
            }

            ImageEntity image5 = getImageEntity("type5", photoes);
            if (image5.getUrl() == null) {
                params.put("image5", "-");
            } else {
                params.put("image5", image5);
            }

            ImageEntity image6 = getImageEntity("type6", photoes);
            if (image6.getUrl() == null) {
                params.put("image6", "-");
            } else {
                params.put("image6", image6);
            }

            if (lmBoard.getRemarks() == null || "".equals(lmBoard.getRemarks())) {
                params.put("remark", "-");
            } else {
                params.put("remark", lmBoard.getRemarks());
            }
            params.put("time", DateUtils.format(lmBoard.getCreateDate()));
           /* lmBoard.setStrLat(LongitudeUtil.dblToLocation(lmBoard.getProofLat()));
            lmBoard.setStrLon(LongitudeUtil.dblToLocation(lmBoard.getProofLon()));*/
            params.put("data", lmBoard);

            try {

                XWPFDocument doc = WordExportUtil.exportWord07(
                        "word/board.docx", params);
                String fileName = "标识牌" + lmBoard.getNumber() + "登记表";
                String lastName = WORD_PATH + fileName + ".docx";
                lmBoard.setFileUrl(lastName.substring(2));

                lmBoardService.updateById(lmBoard);
                FileOutputStream fos = new FileOutputStream(lastName);
                doc.write(fos);
                fos.close();


            } catch (Exception e) {
                e.printStackTrace();
            }


            return ResultVOUtil.success(lmBoard.getFileUrl());

        }
        return ResultVOUtil.success(lmBoard.getFileUrl());
    }

    @PostMapping("/exportStandardExport")
    @SysLog("导出word标准登记表")
    public ResultVO exportStandardExport(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        Integer id = (Integer) dataParam.get("id");
        if (id == null || id < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        LmBoard lmBoard = lmBoardService.getById(id);

        if (lmBoard != null && StringUtils.isNotBlank(lmBoard.getWordUrl())) {

            Map<String, Object> params = new HashMap<>();

            if (StringUtils.isBlank(lmBoard.getVerifyPerson())) {
                params.put("verifyPerson", "-");
            } else {
                params.put("verifyPerson", lmBoard.getVerifyPerson());
            }
            String createUser = lmBoardMapper.selectUserName(lmBoard.getCreateBy());
            lmBoard.setCreateUser(createUser);
            String placeName = lmBoardMapper.selectPlaceName(lmBoard.getCode());
            lmBoard.setPlaceName(placeName);
            if (lmBoard.getPlaceName() == null || "".equals(lmBoard.getPlaceName())) {
                params.put("placeName", "-");
            } else {
                params.put("placeName", lmBoard.getPlaceName());
            }

            List<LmBoardPhoto> photos = lmBoardPhotoService.list(new QueryWrapper<LmBoardPhoto>().eq("board_id", lmBoard.getId()));
            lmBoard.setLmBoardPhotos(photos);
            //设置照片编号
            StringBuilder sb = new StringBuilder("");
            if (photos != null && photos.size() > 0) {
                for (LmBoardPhoto lmMarkerPhoto : photos) {
                    if (StringUtils.isNotBlank(lmMarkerPhoto.getNumber())) {
                        sb.append(lmMarkerPhoto.getNumber()).append(",");
                    }

                }
            }
            int index = sb.lastIndexOf(",");
            System.out.println(photos);
            if (!"".equals(sb)) {
                params.put("number", sb.substring(0, index));
            } else {
                params.put("number", "-");
            }

            Map<String, Object> photoes = new HashMap<>();
            if (photos != null && photos.size() > 0) {
                for (LmBoardPhoto lmMarkerPhoto : photos) {
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

            if (lmBoard.getRemarks() == null || "".equals(lmBoard.getRemarks())) {
                params.put("remark", "-");
            } else {
                params.put("remark", lmBoard.getRemarks());
            }
            params.put("time", DateUtils.format(lmBoard.getCreateDate()));
            lmBoard.setStrLat(LongitudeUtil.dblToLocation(lmBoard.getProofLat()));
            lmBoard.setStrLon(LongitudeUtil.dblToLocation(lmBoard.getProofLon()));
            params.put("data", lmBoard);

            try {

                /*XWPFDocument doc = WordExportUtil.exportWord07(
                        "word/board.docx", params);
                String fileName = "标识牌" + lmBoard.getNumber() + "登记表";
                String lastName = WORD_PATH + fileName + ".docx";
                lmBoard.setFileUrl(lastName.substring(2));
                FileOutputStream fos = new FileOutputStream(lastName);
                doc.write(fos);
                fos.close();*/

                XWPFDocument doc1 = WordExportUtil.exportWord07(
                        "word/boardWord.docx", params);
                String fileName1 = "标识牌标准" + lmBoard.getNumber() + "登记表";
                String lastName1 = WORD_PATH + fileName1 + ".docx";
                lmBoard.setWordUrl(lastName1.substring(2));
                FileOutputStream fos1 = new FileOutputStream(lastName1);
                doc1.write(fos1);
                fos1.close();


            } catch (Exception e) {
                e.printStackTrace();
            }


            return ResultVOUtil.success(lmBoard.getWordUrl());

        }
        return ResultVOUtil.success(lmBoard.getWordUrl());
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
                float proportion = (float) sourceImg.getHeight() / sourceImg.getWidth();

                height = proportion * 85;
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
     * @return
     * @description: 获取预置标识牌
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/5/16 0016 13:33
     */
    @PostMapping(value = "/getPreLmBoardList")
    public ResultVO getPreLmBoardList(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        String boardNum = (String) dataParam.get("boardNum");

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

        Integer pageNum = (Integer) dataParam.get("pageNum");
        Integer pageSize = (Integer) dataParam.get("pageSize");


        if (pageNum == null) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }

        Map<String, Object> result = lmBoardService.getPreBoardList(boardNum, codes, pageNum, pageSize);


        return ResultVOUtil.success(result);
    }

    /**
     * @return
     * @description: 获取全部采集的标识牌
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/5/14 0014 11:56
     */
    @RequestMapping(value = "/getAllBoard")
    public ResultVO getAllBoard() {
        List<LmBoard> lmBoardList = lmBoardService.getAllBoard();
        return ResultVOUtil.success(lmBoardList);
    }

    /**
     * @return
     * @description: 获取预设标识牌
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/5/14 0014 15:10
     */
    @RequestMapping(value = "/getAllPreBoard")
    public ResultVO getAllPreBoard() {
        List<LmBoard> preBoardList = lmBoardService.getPreAllBoard();
        return ResultVOUtil.success(preBoardList);
    }

    @RequestMapping(value = "/import")
    public void importBoardData() {
        String path = "D:/Work/gistone/static/shapefile/redline_p_bsp.shp";
        ImportBoardData importRedlineData = new ImportBoardData();
        ArrayList<LmBoard> lmMarkerMobiles = importRedlineData.readShapeFile(path);
        int num = 1;
        for (LmBoard lmMarkerMobile : lmMarkerMobiles) {
            lmMarkerMobile.setType(0);
            lmMarkerMobile.setSaveDate(new Date());
            lmMarkerMobile.setNumber(String.valueOf(num));
            num++;
        }
        lmBoardService.deleteAll();
        lmBoardService.saveBatch(lmMarkerMobiles);

    }


    @PostMapping(value = "/export_PreExcel")
    public ResultVO export_PreExcel(@RequestBody Map<String, Object> paramsMap, HttpServletResponse response) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        String boardNum = (String) dataParam.get("boardNum");
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
        List<LmBoardVO> lmBoardVOList = lmBoardService.selectPreBoardListForAll(boardNum, codes);
        String filepath = ExcelUtil.toXls("预设标识牌", lmBoardVOList, configUtils.getExcel_PATH(), LmBoardVO.class, response);
        Map map1 = new HashMap();
        map1.put("filepath", filepath.substring(2));
        return ResultVOUtil.success(map1);
    }

    @PostMapping(value = "/export_Excel")
    public ResultVO export_Excel(@RequestBody Map<String, Object> paramsMap, HttpServletResponse response) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        String boardNum = (String) dataParam.get("boardNum");
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
        List<LmBoardVO> lmBoardVOList = lmBoardService.selectBoardListForAll(boardNum, codes);
        String filepath = ExcelUtil.toXls("实际标识牌", lmBoardVOList, configUtils.getExcel_PATH(), LmBoardVO.class, response);
        Map map1 = new HashMap();
        map1.put("filepath", filepath.substring(2));
        return ResultVOUtil.success(map1);
    }

    @PostMapping(value = "/delete")
    @SysLog("删除标识牌")
    public ResultVO delete(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        Integer id = (Integer) dataParam.get("id");
        if (id == null && id < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "标识牌id不能为空");
        }
        lmBoardService.delete(id);
        return ResultVOUtil.success();
    }

    @PostMapping("/deletedList")
    public ResultVO deletedList(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
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

        Map<String, Object> result = lmBoardService.getDeletedList(pageNum, pageSize);

        return ResultVOUtil.success(result);
    }

    @PostMapping("/recover")
    @SysLog("恢复删除的标识牌")
    public ResultVO recover(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        Integer id = (Integer) dataParam.get("id");

        if (id == null || id < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }

        lmBoardService.recover(id);
        return ResultVOUtil.success();
    }

    @PostMapping("/deleteForever")
    @SysLog("永久删除标识牌")
    public ResultVO deleteForever(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        Integer id = (Integer) dataParam.get("id");

        if (id == null || id < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        lmBoardService.deleteForever(id);

        return ResultVOUtil.success();
    }
}

