package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.EXCEL.LmBoardDiagramVO;
import com.gistone.entity.LmBoardDiagram;
import com.gistone.entity.LmBoardDiagramDeviceID;
import com.gistone.entity.LmBoardDiagramPhoto;
import com.gistone.entity.LmBoardPhoto;
import com.gistone.mapper.*;
import com.gistone.service.ILmBoardDiagramService;
import com.gistone.service.LmBoardDiagramDeviceIDService;
import com.gistone.util.ConfigUtils;
import com.gistone.util.DateUtils;
import com.gistone.util.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-09-04
 */
@Service
@Transactional
@Slf4j
public class LmBoardDiagramServiceImpl extends ServiceImpl<LmBoardDiagramMapper, LmBoardDiagram> implements ILmBoardDiagramService {

    @Autowired
    private LmBoardDiagramMapper lmBoardDiagramMapper;
    @Autowired
    private DataRedlineRegisterMapper dataRedlineRegisterMapper;

    @Autowired
    private LmBoardMapper lmBoardMapper;

    @Autowired
    private LmBoardPhotoMapper boardPhotoMapper;

    @Autowired
    private LmBoardDeviceIDMapper lmBoardDeviceIDMapper;

    @Autowired
    private LmBoardDiagramDeviceIDMapper lmBoardDiagramDeviceIDMapper;

    @Autowired
    private LmBoardDiagramDeviceIDService lmBoardDiagramDeviceIDService;

    @Autowired
    private ConfigUtils configUtils;

    @Autowired
    private LmBoardDiagramPhotoMapper lmBoardDiagramPhotoMapper;

    @Override
    public Map<String, Object> LmBoardDiagramList(String boardNum, String code, Integer pageNum, Integer pageSize) {
        //业务
        QueryWrapper<LmBoardDiagram> Wrapper = new QueryWrapper<>();
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(boardNum)) {
            Wrapper.eq("number", boardNum);
        }

        if (StringUtils.isNotBlank(code)) {
            Wrapper.likeRight("code", code);
        }

        Wrapper.eq("del_flag", 1);
        Wrapper.eq("type", 1);
        Wrapper.orderByDesc("create_date");

        IPage<LmBoardDiagram> lmBoardIPage = lmBoardDiagramMapper.selectPage(new Page<>(pageNum, pageSize), Wrapper);

        List<LmBoardDiagram> lmBoardList = lmBoardIPage.getRecords();
        if (lmBoardList != null && lmBoardList.size() > 0) {

            for (LmBoardDiagram lmBoard : lmBoardList) {
                //所属红线名称
                Integer redlineId = lmBoard.getRedlineId();
                String redlineNumber = dataRedlineRegisterMapper.selectRedlineNumber(redlineId);

//                lmBoard.setRedlineName(redlineNumber);

                //所在地名称

                String placeName = lmBoardMapper.selectPlaceName(lmBoard.getCode());

//                lmBoard.setPlaceName(placeName);
                //填表人

                String createUser = lmBoardMapper.selectUserName(lmBoard.getCreateBy());
//                lmBoard.setCreateUser(createUser);

                //审核人
                String verifyUser = lmBoardMapper.selectUserName(lmBoard.getVerifyBy());
//                lmBoard.setVerifyUser(verifyUser);


                List<LmBoardPhoto> lmBoardPhotos = boardPhotoMapper.selectList(new QueryWrapper<LmBoardPhoto>().eq("board_id", lmBoard.getId()));

//                lmBoard.setLmBoardPhotos(lmBoardPhotos);

            }
        }
        map.put("row", lmBoardList);
        map.put("total", lmBoardIPage.getTotal());
        return map;
    }

    @Override
    public void delete(Integer id) {
        lmBoardDiagramDeviceIDMapper.delete(new QueryWrapper<LmBoardDiagramDeviceID>().eq("lmBoard_diagram_id", id));
        LmBoardDiagram lmBoard = lmBoardDiagramMapper.selectById(id);
        lmBoard.setDelFlag(0);
        lmBoardDiagramMapper.updateById(lmBoard);
    }

    @Override
    public Map<String,Object> importZipCsv(List<String[]> list, String userId, String fileNameNoIndex) {
        LmBoardDiagram lmBoard = new LmBoardDiagram();
        Map map = null;
        try {
            //导入总数
            int num = list.size() - 1;
            //没有入库数据
            int isNotIn = 0;
            for (int i = 1; i < list.size(); i++) {
                List<String> stringList = Arrays.asList(list.get(i));
                QueryWrapper<LmBoardDiagramDeviceID> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id", stringList.get(0));
                queryWrapper.eq("device_id", stringList.get(1));
                List isData = lmBoardDiagramDeviceIDService.listObjs(queryWrapper);
                if (isData.size() > 0) {
                    //return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "您已添加过该数据!");
                    isNotIn += 1;
                    continue;
                }
                lmBoard.setRedlineId(Integer.valueOf(stringList.get(2)));
                lmBoard.setRedlineNum(stringList.get(3));
                List sjzb = Arrays.asList(stringList.get(4).split(","));
                lmBoard.setProofLon(Double.valueOf(sjzb.get(0).toString()));
                lmBoard.setProofLat(Double.valueOf(sjzb.get(1).toString()));
                List yszb = Arrays.asList(stringList.get(5).split(","));
                lmBoard.setLongitude(Double.valueOf(yszb.get(0).toString()));
                lmBoard.setLatitude(Double.valueOf(yszb.get(1).toString()));
                lmBoard.setNumber(stringList.get(6));

                BigDecimal b = new BigDecimal(stringList.get(7));
                lmBoard.setCreateDate(DateUtils.Millisecond2Date(b.toPlainString()));
                BigDecimal b1 = new BigDecimal(stringList.get(8));
                lmBoard.setUpdateDate(DateUtils.Millisecond2Date(b.toPlainString()));
                lmBoard.setCode(stringList.get(9));
                lmBoard.setRemarks(stringList.get(12));
                lmBoard.setCreateBy(Integer.valueOf(userId));
                lmBoard.setDelFlag(1);
                lmBoard.setType(1);
                lmBoard.setContent(stringList.get(13));
                lmBoard.setVerifyPerson(stringList.get(14));
                lmBoard.setSaveDate(new Date());
                lmBoardDiagramMapper.insert(lmBoard);
                // if(i==1){
                //向中间表保存消息
                LmBoardDiagramDeviceID lmBoardDeviceID = new LmBoardDiagramDeviceID();
                lmBoardDeviceID.setLmBoard_diagram_id(lmBoard.getId().toString());
                lmBoardDeviceID.setId(stringList.get(0));
                lmBoardDeviceID.setDevice_id(stringList.get(1));
                lmBoardDeviceID.setBatch_id(String.valueOf(System.currentTimeMillis()));
                lmBoardDeviceID.setType(0);
                lmBoardDiagramDeviceIDMapper.insert(lmBoardDeviceID);
                //  }
                //缩略图上传
                if (StringUtils.isNotBlank(stringList.get(10))) {
                    String mbwj = configUtils.getPICTURE_PATH() + "ktdb/" + DateUtils.format(new Date()) + "/ZipThumbnail/";
                    ExcelUtil.mkdirsmy(mbwj, "数据库关联文件,且勿删除");
                    System.out.println(stringList.get(10).substring(stringList.get(10).lastIndexOf("/") + 1));
                    ExcelUtil.copyFile(configUtils.getZIP_DECOM_PATH() + fileNameNoIndex + "\\thumbnail\\" + stringList.get(10).substring(stringList.get(10).lastIndexOf("/") + 1), mbwj);
                    Integer type = 6;
                    prePhotoInfo(lmBoard, mbwj.substring(2) + stringList.get(10).substring(stringList.get(10).lastIndexOf("/") + 1), type);
                }
                //现场图上传
                if (StringUtils.isNotBlank(stringList.get(11))) {
                    String mbwj = configUtils.getPICTURE_PATH() + "ktdb/" + DateUtils.format(new Date()) + "/ZiplocalPicture/";
                    ExcelUtil.mkdirsmy(mbwj, "数据库关联文件,且勿删除");
                    JSONArray jsonArray = JSONArray.fromObject(stringList.get(11).replaceAll("，", ","));
                    for (int j = 0; j < jsonArray.size(); j++) {
                        JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(j));
                        if (jsonObject.get("path") != null) {
                            String paths = jsonObject.get("path").toString();
                            String srcPath = configUtils.getZIP_DECOM_PATH() + fileNameNoIndex + "\\localPicture\\" + paths.substring(paths.lastIndexOf("/") + 1);
                            ExcelUtil.copyFile(srcPath, mbwj);
                            prePhotoInfo(lmBoard, mbwj.substring(2) + paths.substring(paths.lastIndexOf("/") + 1), Integer.valueOf(jsonObject.get("type").toString()));
                        }
                    }
                }
            }
            map = new HashMap();
            map.put("num", num);
            map.put("isNotIn", isNotIn);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("导入异常，异常信息为：{}",e.getMessage());
        }

        return map;
    }

    @Override
    public List<LmBoardDiagramVO> selectBoardDiagramListForAll(String boardNum, String code) {
        return lmBoardDiagramMapper.selectBoardDiagramListForAll(boardNum, code);
    }

    public void prePhotoInfo(LmBoardDiagram lmBoardDiagram, String rePath, Integer type) {
       LmBoardDiagramPhoto lmMarkerPhoto = new LmBoardDiagramPhoto();
        lmMarkerPhoto.setBoardDiagramId(lmBoardDiagram.getId());
        lmMarkerPhoto.setUrl(rePath);
        lmMarkerPhoto.setCreateTime(new Date());
        lmMarkerPhoto.setType(type);
        lmMarkerPhoto.setNumber(lmBoardDiagram.getNumber()+"_"+type);
        lmBoardDiagramPhotoMapper.insert(lmMarkerPhoto);
    }
}
