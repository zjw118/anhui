package com.gistone.controller;


import cn.afterturn.easypoi.entity.ImageEntity;
import cn.afterturn.easypoi.word.WordExportUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.VO.ResultVO;
import com.gistone.entity.EXCEL.LmBoardDiagramVO;
import com.gistone.entity.LmBoardDiagram;
import com.gistone.entity.LmBoardDiagramDeviceID;
import com.gistone.entity.LmBoardDiagramPhoto;
import com.gistone.entity.SysUser;
import com.gistone.mapper.LmBoardMapper;
import com.gistone.service.*;
import com.gistone.util.*;
import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-09-04
 */
@RestController
@RequestMapping("/api/ktdb/lmBoardDiagram")
@Slf4j
public class LmBoardDiagramController {

    @Autowired
    private ILmPointService iLmPointService;


    @Autowired
    private LmBoardDiagramDeviceIDService lmBoardDiagramDeviceIDService;

    @Autowired
    private UploadPictureService uploadPicture;


    @Autowired
    private ILmBoardDiagramService lmBoardDiagramService;

    @Autowired
    private ILmPointService lmPointService;

    @Autowired
    private LmBoardMapper lmBoardMapper;

    @Autowired
    private ILmBoardDiagramPhotoService lmBoardDiagramPhotoService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ConfigUtils configUtils;

    @Value("${WORD_PATH}")
    private String WORD_PATH;
    @Value("${PATH}")
    private String PATH;

    /**
     * @param data
     * @param file
     * @return com.gistone.VO.ResultVO
     * @description:标识牌分布图
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/9/4 0004 11:54
     */
    @PostMapping(value = "/add")
    public ResultVO addlmBoardDiagram(@RequestParam("data") String data, MultipartFile[] file) {
        if (StringUtils.isBlank(data)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "data不能为空");
        }
        JSONObject a = JSONObject.fromObject(data);
//        Map<String, Object> map = JsonToMapUtil.parseJSON2Map(a);

        JSONObject dataParam = (JSONObject) a.get("data");

        String uuid = (String) dataParam.get("uuid");

        LmBoardDiagram lmBoard = new LmBoardDiagram();

        String verifyPerson = (String) dataParam.get("verifyPerson");
        if (StringUtils.isBlank(verifyPerson)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "审核人不能为空");
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

        LmBoardDiagramDeviceID one = lmBoardDiagramDeviceIDService.getOne(new QueryWrapper<LmBoardDiagramDeviceID>().eq("id", boardId).eq("device_id", deviceId));
        if (one == null) {

            try {
                boolean insert = lmBoardDiagramService.save(lmBoard);

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
                        LmBoardDiagramPhoto lmBoardPhoto = new LmBoardDiagramPhoto();
                        lmBoardPhoto.setBoardDiagramId(lmBoard.getId());
                        lmBoardPhoto.setUrl(rePath);
                        lmBoardPhoto.setCreateTime(new Date());
                        lmBoardPhoto.setType(type);
                        lmBoardPhoto.setNumber(lmBoard.getNumber() + "-" + type);
                        lmBoardDiagramPhotoService.save(lmBoardPhoto);

                    }
                }

                LmBoardDiagramDeviceID lmBoardDeviceID = new LmBoardDiagramDeviceID();
                lmBoardDeviceID.setDevice_id(deviceId);
                lmBoardDeviceID.setId(boardId);
                lmBoardDeviceID.setLmBoard_diagram_id(String.valueOf(lmBoard.getId()));
                lmBoardDeviceID.setType(1);
                lmBoardDiagramDeviceIDService.save(lmBoardDeviceID);
            } catch (Exception e) {
                log.error("添加标识牌失败，异常信息：{}", e.getMessage());
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "添加标识牌失败");
            }
            return ResultVOUtil.success(uuid);

        } else {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "该数据已存在");
        }


    }

    /**
     * @param
     * @return com.gistone.VO.ResultVO
     * @description:获取所有数据列表
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/9/4 0004 16:16
     */
    @PostMapping("/allList")
    public ResultVO AllList() {
        List<LmBoardDiagram> lmBoardDiagramList = lmBoardDiagramService.list(null);
        return ResultVOUtil.success(lmBoardDiagramList);
    }

    /**
     * @param paramsMap
     * @return com.gistone.VO.ResultVO
     * @description:pc端获取分布图列表
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/9/12 0012 10:34
     */
    @PostMapping(value = "/getLmBoardDiagramList")
    public ResultVO getLmBoardDiagramList(@RequestBody Map<String, Object> paramsMap) {
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
            Integer level = lmPointService.getLevelByCode(code);
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

        Map<String, Object> result = lmBoardDiagramService.LmBoardDiagramList(boardNum, codes, pageNum, pageSize);


        return ResultVOUtil.success(result);
    }

    /**
     * @param paramsMap
     * @return com.gistone.VO.ResultVO
     * @description:pc端删除分布图
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/9/12 0012 10:37
     */
    @PostMapping("/delete")
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
        lmBoardDiagramService.delete(id);

        return ResultVOUtil.success();
    }

    @PostMapping("/getById")
    public ResultVO getById(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        Integer id = (Integer) dataParam.get("id");
        if (id == null || id < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        LmBoardDiagram lmBoard = lmBoardDiagramService.getById(id);


//            Map<String, Object> params = new HashMap<>();
           /* if (StringUtils.isBlank(lmBoard.getVerifyPerson())) {
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

        List<LmBoardDiagramPhoto> photos = lmBoardDiagramPhotoService.list(new QueryWrapper<LmBoardDiagramPhoto>().eq("board_diagram_id", lmBoard.getId()));
        lmBoard.setPhotos(photos);

            /*StringBuilder sb = new StringBuilder("");
            if (photos != null && photos.size() > 0) {
                for (LmBoardDiagramPhoto lmMarkerPhoto : photos) {
                    if (StringUtils.isNotBlank(lmMarkerPhoto.getNumber())) {
                        sb.append(lmMarkerPhoto.getNumber()).append(",");
                    }
                }
            }
            int index = sb.lastIndexOf(",");

            params.put("number", sb.substring(0, index));*/
           /* Map<String, Object> photoes = new HashMap<>();
            if (photos != null && photos.size() > 0) {
                for (LmBoardDiagramPhoto lmMarkerPhoto : photos) {
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
            params.put("time", DateUtils.format(lmBoard.getCreateDate()));
*/
        lmBoard.setStrLat(LongitudeUtil.dblToLocation(lmBoard.getProofLat()));
        lmBoard.setStrLon(LongitudeUtil.dblToLocation(lmBoard.getProofLon()));
//            params.put("data", lmBoard);

           /* try {

                XWPFDocument doc = WordExportUtil.exportWord07(
                        "word/boardDiagram.docx", params);
                String fileName = "分布图" + lmBoard.getNumber() + "登记表";
                String lastName = WORD_PATH + fileName + ".docx";
                lmBoard.setFileUrl(lastName.substring(2));
                FileOutputStream fos = new FileOutputStream(lastName);
                doc.write(fos);
                fos.close();

                XWPFDocument doc1 = WordExportUtil.exportWord07(
                        "word/boardDiagramWord.docx", params);
                String fileName1 = "分布图标准" + lmBoard.getNumber() + "登记表";
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
        LmBoardDiagram lmBoard = lmBoardDiagramService.getById(id);

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

            List<LmBoardDiagramPhoto> photos = lmBoardDiagramPhotoService.list(new QueryWrapper<LmBoardDiagramPhoto>().eq("board_diagram_id", lmBoard.getId()));
            lmBoard.setPhotos(photos);

            StringBuilder sb = new StringBuilder("");
            if (photos != null && photos.size() > 0) {
                for (LmBoardDiagramPhoto lmMarkerPhoto : photos) {
                    if (StringUtils.isNotBlank(lmMarkerPhoto.getNumber())) {
                        sb.append(lmMarkerPhoto.getNumber()).append(",");
                    }

                }
            }
            int index = sb.lastIndexOf(",");
            if ("".equals(sb.toString())) {
                params.put("number", "-");
            } else {
                params.put("number", sb.substring(0, index));
            }


            Map<String, Object> photoes = new HashMap<>();
            if (photos != null && photos.size() > 0) {
                for (LmBoardDiagramPhoto lmMarkerPhoto : photos) {
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

            lmBoard.setStrLat(LongitudeUtil.dblToLocation(lmBoard.getProofLat()));
            lmBoard.setStrLon(LongitudeUtil.dblToLocation(lmBoard.getProofLon()));
            params.put("data", lmBoard);

            try {

                XWPFDocument doc = WordExportUtil.exportWord07(
                        "word/boardDiagram.docx", params);
                String fileName = "分布图" + lmBoard.getNumber() + "登记表";
                String lastName = WORD_PATH + fileName + ".docx";
                lmBoard.setFileUrl(lastName.substring(2));
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

    @PostMapping("/exportStandardWord")
    public ResultVO exportStandardWord(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        Integer id = (Integer) dataParam.get("id");
        if (id == null || id < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        LmBoardDiagram lmBoard = lmBoardDiagramService.getById(id);

        if (lmBoard != null && StringUtils.isBlank(lmBoard.getWordUrl())) {


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

            List<LmBoardDiagramPhoto> photos = lmBoardDiagramPhotoService.list(new QueryWrapper<LmBoardDiagramPhoto>().eq("board_diagram_id", lmBoard.getId()));
            lmBoard.setPhotos(photos);

            StringBuilder sb = new StringBuilder("");
            if (photos != null && photos.size() > 0) {
                for (LmBoardDiagramPhoto lmMarkerPhoto : photos) {
                    if (StringUtils.isNotBlank(lmMarkerPhoto.getNumber())) {
                        sb.append(lmMarkerPhoto.getNumber()).append(",");
                    }

                }
            }
            int index = sb.lastIndexOf(",");

            params.put("number", sb.substring(0, index));
            Map<String, Object> photoes = new HashMap<>();
            if (photos != null && photos.size() > 0) {
                for (LmBoardDiagramPhoto lmMarkerPhoto : photos) {
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


                XWPFDocument doc1 = WordExportUtil.exportWord07(
                        "word/boardDiagramWord.docx", params);
                String fileName1 = "分布图标准" + lmBoard.getNumber() + "登记表";
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

    @PostMapping("/importBoardDiagram")
    public ResultVO importBoardDiagram(@RequestParam("phone") String phone, @RequestParam("file") MultipartFile file) {
        SysUser map = sysUserService.getOne(new QueryWrapper<SysUser>().eq("username", phone).eq("enable", 1).eq("type", 1));/*sysUserFeign.getByPhone(phone);*/
        if (map == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "手机用户不存在,请核对");
        }
        String fileNameNoIndex = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
        String fileName = FileUtil.fileUp(file, configUtils.getZIP_PATH(), fileNameNoIndex);
        Map<String, Object> map1 = null;
        try {
            ZipUtils.unZip(configUtils.getZIP_PATH() + fileName, configUtils.getZIP_DECOM_PATH());
//            FileReader fReader = new FileReader(configUtils.getZIP_DECOM_PATH() + fileNameNoIndex + "\\" + fileNameNoIndex + ".csv");
//            CSVReader csvReader = new CSVReader(fReader);
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(configUtils.getZIP_DECOM_PATH() + fileNameNoIndex + "\\" + fileNameNoIndex + ".csv"), "UTF-8");
            CSVReader csvReader = new CSVReader(inputStreamReader);
            List<String[]> list = csvReader.readAll();
            csvReader.close();

            map1 = lmBoardDiagramService.importZipCsv(list, map.getId().toString(), fileNameNoIndex);
        } catch (Exception e) {
            log.error("导入分布图失败，异常信息{}", e.getMessage());
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "导入分布图失败");
        }

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
        List<LmBoardDiagramVO> lmBoardVOList = lmBoardDiagramService.selectBoardDiagramListForAll(boardNum, codes);
        String filepath = FileUtil.toXls("实际分布图", lmBoardVOList, configUtils.getExcel_PATH(), LmBoardDiagramVO.class, response);
        Map map1 = new HashMap();
        map1.put("filepath", filepath.substring(2));
        return ResultVOUtil.success(map1);
    }
}

