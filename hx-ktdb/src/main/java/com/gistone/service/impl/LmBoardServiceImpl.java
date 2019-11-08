package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.VO.ResultVO;
import com.gistone.entity.DataRedlineRegister;
import com.gistone.entity.EXCEL.LmBoardVO;
import com.gistone.entity.LmBoard;
import com.gistone.entity.LmBoardDeviceID;
import com.gistone.entity.LmBoardPhoto;
import com.gistone.mapper.DataRedlineRegisterMapper;
import com.gistone.mapper.LmBoardDeviceIDMapper;
import com.gistone.mapper.LmBoardMapper;
import com.gistone.mapper.LmBoardPhotoMapper;
import com.gistone.service.ILmBoardDeviceIDService;
import com.gistone.service.ILmBoardPhotoService;
import com.gistone.service.ILmBoardService;
import com.gistone.util.ConfigUtils;
import com.gistone.util.DateUtils;
import com.gistone.util.ExcelUtil;
import com.gistone.util.ResultVOUtil;
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
 *
 * @author zf1017@foxmail.com
 * @since 2019-04-29
 */
@Service
public class LmBoardServiceImpl extends ServiceImpl<LmBoardMapper, LmBoard> implements ILmBoardService {

    @Autowired
    private LmBoardMapper lmBoardMapper;

    @Autowired
    private DataRedlineRegisterMapper dataRedlineRegisterMapper;

    @Autowired
    private LmBoardPhotoMapper boardPhotoMapper;
    @Autowired
    private ILmBoardDeviceIDService iLmBoardDeviceIDService;
    @Autowired
    private ILmBoardService iLmBoardService;
    @Autowired
    private ConfigUtils configUtils;
    @Autowired
    private ILmBoardPhotoService iLmBoardPhotoService;
    @Autowired
    private LmBoardDeviceIDMapper lmBoardDeviceIDMapper;

    @Override
    public Map<String, Object> getBoardList(String boardNum, String code, Integer pageNum, Integer pageSize) {

        QueryWrapper<LmBoard> Wrapper = new QueryWrapper<>();
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

        IPage<LmBoard> lmBoardIPage = lmBoardMapper.selectPage(new Page<>(pageNum, pageSize), Wrapper);

        List<LmBoard> lmBoardList = lmBoardIPage.getRecords();
        if (lmBoardList != null && lmBoardList.size() > 0) {

            for (LmBoard lmBoard : lmBoardList) {
                //所属红线名称
                Integer redlineId = lmBoard.getRedlineId();
                String redlineNumber = dataRedlineRegisterMapper.selectRedlineNumber(redlineId);

                lmBoard.setRedlineName(redlineNumber);

                //所在地名称

                String placeName = lmBoardMapper.selectPlaceName(lmBoard.getCode());

                lmBoard.setPlaceName(placeName);
                //填表人

                String createUser = lmBoardMapper.selectUserName(lmBoard.getCreateBy());
                lmBoard.setCreateUser(createUser);

                //审核人
                String verifyUser = lmBoardMapper.selectUserName(lmBoard.getVerifyBy());
                lmBoard.setVerifyUser(verifyUser);


                List<LmBoardPhoto> lmBoardPhotos = boardPhotoMapper.selectList(new QueryWrapper<LmBoardPhoto>().eq("board_id", lmBoard.getId()));

                lmBoard.setLmBoardPhotos(lmBoardPhotos);

            }
        }
        map.put("row", lmBoardList);
        map.put("total", lmBoardIPage.getTotal());
        return map;
    }

    @Override
    public List<LmBoard> getAllBoard() {
        List<LmBoard> lmBoardList = lmBoardMapper.selectList(new QueryWrapper<LmBoard>().eq("del_flag", 1).eq("type", 1));
        return lmBoardList;
    }

    @Override
    public ResultVO importZipCsv(List<String[]> list, String userId, String fileNameNoIndex) throws Exception {
        LmBoard lmBoard = new LmBoard();
        //导入总数
        int num = list.size() - 1;
        //没有入库数据
        int isNotIn = 0;
        for (int i = 1; i < list.size(); i++) {
            List<String> stringList = Arrays.asList(list.get(i));
            QueryWrapper<LmBoardDeviceID> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", stringList.get(0));
            queryWrapper.eq("device_id", stringList.get(1));
            List isData = iLmBoardDeviceIDService.listObjs(queryWrapper);
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
            lmBoard.setUpdateDate(DateUtils.Millisecond2Date(b1.toPlainString()));
            lmBoard.setCode(stringList.get(9));
            lmBoard.setRemarks(stringList.get(12));
            lmBoard.setCreateBy(Integer.valueOf(userId));
            lmBoard.setDelFlag(1);
            lmBoard.setType(1);
            lmBoard.setContent(stringList.get(13));
            lmBoard.setVerifyPerson(stringList.get(14));
            boolean insert = iLmBoardService.save(lmBoard);
            // if(i==1){
            //向中间表保存消息
            LmBoardDeviceID lmBoardDeviceID = new LmBoardDeviceID();
            lmBoardDeviceID.setLmBoard_id(lmBoard.getId().toString());
            lmBoardDeviceID.setId(stringList.get(0));
            lmBoardDeviceID.setDevice_id(stringList.get(1));
            lmBoardDeviceID.setBatch_id(String.valueOf(System.currentTimeMillis()));
            lmBoardDeviceID.setType(0);
            iLmBoardDeviceIDService.save(lmBoardDeviceID);
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
                String mbwj = configUtils.getPICTURE_PATH() + "ktdb/" + DateUtils.format(new Date()) + "\\ZiplocalPicture\\";
                ExcelUtil.mkdirsmy(mbwj, "数据库关联文件,且勿删除");
                JSONArray jsonArray = JSONArray.fromObject(stringList.get(11).replaceAll("，", ","));
                for (int j = 0; j < jsonArray.size(); j++) {
                    JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(j));
                    if (jsonObject.get("path") != null) {
                        String paths = jsonObject.get("path").toString();
                        ExcelUtil.copyFile(configUtils.getZIP_DECOM_PATH() + fileNameNoIndex + "\\localPicture\\" + paths.substring(paths.lastIndexOf("/") + 1), mbwj);
                        prePhotoInfo(lmBoard, mbwj.substring(2) + paths.substring(paths.lastIndexOf("/") + 1), Integer.valueOf(jsonObject.get("type").toString()));
                    }
                }
            }
        }
        Map map = new HashMap();
        map.put("num", num);
        map.put("isNotIn", isNotIn);
        return ResultVOUtil.success(map);
    }

    @Override
    public Map<String, Object> getPreBoardList(String boardNum, String code, Integer pageNum, Integer pageSize) {

        QueryWrapper<LmBoard> Wrapper = new QueryWrapper<>();
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(boardNum)) {
            Wrapper.eq("number", boardNum);
        }

        if (StringUtils.isNotBlank(code)) {
            Wrapper.likeRight("code", code);
        }

        Wrapper.eq("del_flag", 1);
        Wrapper.eq("type", 0);


        IPage<LmBoard> lmBoardIPage = lmBoardMapper.selectPage(new Page<>(pageNum, pageSize), Wrapper);

        List<LmBoard> lmBoardList = lmBoardIPage.getRecords();
        if (lmBoardList != null && lmBoardList.size() > 0) {

            for (LmBoard lmBoard : lmBoardList) {
                //所属红线名称
                Integer redlineId = lmBoard.getRedlineId();

                String redlineNumber = dataRedlineRegisterMapper.selectRedlineNumber(redlineId);

                lmBoard.setRedlineName(redlineNumber);

                //所在地名称

                String placeName = lmBoardMapper.selectPlaceName(lmBoard.getCode());

                lmBoard.setPlaceName(placeName);
                //填表人

                String createUser = lmBoardMapper.selectUserName(lmBoard.getCreateBy());
                lmBoard.setCreateUser(createUser);

                //审核人
                String verifyUser = lmBoardMapper.selectUserName(lmBoard.getVerifyBy());
                lmBoard.setVerifyUser(verifyUser);


                List<LmBoardPhoto> lmBoardPhotos = boardPhotoMapper.selectList(new QueryWrapper<LmBoardPhoto>().eq("board_id", lmBoard.getId()));

                lmBoard.setLmBoardPhotos(lmBoardPhotos);

            }
        }
        map.put("row", lmBoardList);
        map.put("total", lmBoardIPage.getTotal());
        return map;
    }

    @Override
    public List<LmBoard> getPreAllBoard() {
        List<LmBoard> lmBoardList = lmBoardMapper.selectList(new QueryWrapper<LmBoard>().eq("del_flag", 1).eq("type", 0));
        return lmBoardList;
    }

    @Override
    public List<LmBoardVO> selectPreBoardListForAll(String boardNum, String codes) {
        return lmBoardMapper.selectPreBoardListForAll(boardNum, codes);
    }

    @Override
    public List<LmBoardVO> selectBoardListForAll(String boardNum, String codes) {
        return lmBoardMapper.selectBoardListForAll(boardNum, codes);
    }

    @Override
    public void delete(Integer id) {


        lmBoardDeviceIDMapper.delete(new QueryWrapper<LmBoardDeviceID>().eq("lmBoard_id", id));
        LmBoard lmBoard = lmBoardMapper.selectById(id);
        lmBoard.setDelFlag(0);
        lmBoardMapper.updateById(lmBoard);
    }

    @Override
    public void deleteAll() {
        lmBoardMapper.deleteAll();
    }

    @Override
    public Map<String, Object> getDeletedList(Integer pageNum, Integer pageSize) {
        QueryWrapper<LmBoard> wrapper = new QueryWrapper<>();
        wrapper.eq("del_flag", 0);

        IPage<LmBoard> lmBoardIPage = lmBoardMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);

        Map<String, Object> result = new HashMap<>();

        if (lmBoardIPage.getRecords() != null && lmBoardIPage.getRecords().size() > 0) {
            for (LmBoard record : lmBoardIPage.getRecords()) {
                DataRedlineRegister dataRedlineRegister = dataRedlineRegisterMapper.selectOne(new QueryWrapper<DataRedlineRegister>().eq("srld_id", record.getRedlineId()));
                if (dataRedlineRegister != null) {
                    record.setRedlineNum(dataRedlineRegister.getSrldNumber());
                }
            }
        }

        result.put("rows", lmBoardIPage.getRecords());
        result.put("total", lmBoardIPage.getTotal());

        return result;

    }

    @Override
    public void recover(Integer id) {
        LmBoard lmBoard = lmBoardMapper.selectById(id);
        lmBoard.setDelFlag(1);
        lmBoardMapper.updateById(lmBoard);
    }

    @Override
    public void deleteForever(Integer id) {
        LmBoard lmBoard = lmBoardMapper.selectById(id);
        lmBoard.setDelFlag(2);
        lmBoardMapper.updateById(lmBoard);
    }

    public void prePhotoInfo(LmBoard lmBoard, String rePath, Integer type) {
        LmBoardPhoto lmBoardPhoto = new LmBoardPhoto();
        lmBoardPhoto.setBoardId(lmBoard.getId());
        lmBoardPhoto.setUrl(rePath);
        lmBoardPhoto.setCreateTime(new Date());
        lmBoardPhoto.setType(type);
        lmBoardPhoto.setNumber(lmBoard.getNumber()+"_"+type);
        iLmBoardPhotoService.save(lmBoardPhoto);
    }
}
