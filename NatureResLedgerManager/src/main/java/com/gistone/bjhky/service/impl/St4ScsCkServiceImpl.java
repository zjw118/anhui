package com.gistone.bjhky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.bjhky.entity.*;
import com.gistone.bjhky.mapper.*;
import com.gistone.bjhky.service.*;
import com.gistone.bjhky.util.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xxh
 * @since 2019-08-14
 */
@Service
public class St4ScsCkServiceImpl extends ServiceImpl<St4ScsCkMapper, St4ScsCk> implements ISt4ScsCkService {
    public static final QueryWrapper<St4PoSaCz> CACZ_WRAPPER = new QueryWrapper<>();
    @Autowired
    St4PoCdSaMapper st4PoCdSaMapper;
    @Autowired
    St4ScsCkMapper checkLedgerMapper;
    @Autowired
    ISt4ScsCkService checkLedgerService;
    @Autowired
    ISt4ScsCacService st4ScsCacService;
    @Autowired
    St4ScsCacMapper st4ScsCacMapper;
    @Autowired
    St4ScsCnMapper checkLedgerAttachMapper;
    @Autowired
    ISt4ScsCnService checkLedgerAttachService;
    @Autowired
    St4ScsCmMapper checkChangeTypeMapper;
    @Autowired
    St4ScsCrMapper checkQuestionTypeMapper;
    @Autowired
    St4ScsCqMapper checkFunctionAreaMapper;
    @Autowired
    St4SysSgMapper reserveDataMapper;
    @Autowired
    St4SysSaMapper userMapper;
    @Autowired
    St4SysSdMapper baseAdminRegionMapper;
    @Autowired
    FileUpAndDownServiceImpl fileUpAndDownService;
    @Autowired
    St4ScsCdMapper checkPointMapper;
    @Autowired
    ISt4ScsCdService checkPointService;
    @Autowired
    ISt4PoCdSaService st4PoCdSaService;

    @Autowired
    St4ScsCpMapper st4ScsCpMapper;
    @Autowired
    St4SysSaMapper st4SysSaMapper;
    @Autowired
    St4PoSaCzMapper st4PoSaCzMapper;
    @Autowired
    St4ScsCzMapper st4ScsCzMapper;

    DateTimeFormatter dfMd = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    @Override
    public Result saveLedger( St4ScsCk checkLedger, HttpServletRequest request, St4SysSa seUser) {
        //这里首先判断一些必填的字段是否都填了
        Result result= new Result();
        if(checkLedger.getCd004()==null){
            return Result.build(1001,"点位编号cd004"+ResultMsg.MSG_1001);
        }
        if(checkLedger.getCk002()==null){
            return Result.build(1001,"行政区ck002"+ResultMsg.MSG_1001);
        }
        if(checkLedger.getSg001()==null){
            return Result.build(1001,"保护地sg001"+ResultMsg.MSG_1001);
        }
        QueryWrapper<St4ScsCk> wrapper = new QueryWrapper<>();
        wrapper.eq("CD004",checkLedger.getCd004());
        wrapper.eq("CK085",1);
        St4ScsCk ck = checkLedgerMapper.selectOne(wrapper);
        if(ck!=null){
            return Result.build(1008,"点位编号不能与系统中的重复,请更换");
        }
        int inum = checkLedgerMapper.insert(checkLedger);
        if(inum<1){
            return Result.build(1003,ResultMsg.MSG_1003);
        }
        if(checkLedger.getSt4ScsCn()!=null){
            St4ScsCn cn = new St4ScsCn();
            cn.setCk001(checkLedger.getCk001());
            if(!checkLedgerAttachService.save(cn)){
                checkLedgerMapper.deleteById(checkLedger);
                return Result.build(1003,ResultMsg.MSG_1003);
            }
        }
        St4ScsCac cac = new St4ScsCac();
        cac.setCk001(checkLedger.getCk001());
        if(!st4ScsCacService.save(cac)){
            checkLedgerMapper.deleteById(checkLedger);
            return Result.build(1003,ResultMsg.MSG_1003);
        }
        return Result.build(1000,"添加"+ResultMsg.MSG_1000);
        /*

        //这里其实是不用下面这个list的因为前台传递过来的就是当前角色所绑定过的字段
        QueryWrapper<CheckLedgerSelect> wrapper = new QueryWrapper<>();
        wrapper.eq("cls_role_id",roleId);
        List<CheckLedgerSelect> selectlist=checkLedgerSelectMapper.selectList(wrapper);
        List<Integer> cloumnIdList = new ArrayList<>();//前台传递过来的列的id
        //拿到字段ID
        if(selectlist.size()>0){
            for (CheckLedgerSelect cls : selectlist) {
                cloumnIdList.add(cls.getClsColumnId());
            }
        }
        //要拿到字段名
        List<CheckLedgerColumn> clcList = checkLedgerColumnMapper.batchSelectByColumnId(cloumnIdList);
        List<String> cloumnNameList = new ArrayList<>();
        if(clcList.size()>0){
            for (CheckLedgerColumn clc : clcList) {
                cloumnNameList.add(clc.getCcColumnName());
            }
        }
        //这里是前台传递过来的[{clAdminRegion:"1",clPointSerNumber:"2",C:["d:/1.jpg","e:3.png"]}]

        Integer insertNum = checkLedgerMapper.insert(checkLedger);

        //得到主键ID
        Integer ldid = checkLedger.getCk001();
        List<St4ScsCn> checkLedgerAttachList= checkLedger.getCheckLedgerAttach();
        try {
            CheckLedgerAttach cla = null;
            int batchInsert=0;
            if (checkLedgerAttachList.size()>0){
                batchInsert = checkLedgerAttachMapper.insertList(checkLedgerAttachList);
            }
            if(insertNum>0||(insertNum>0&&batchInsert>0)){
                return Result.build(1000,"添加"+ ResultMsg.MSG_1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  Result.build(1003, ResultMsg.MSG_1003);

        }
*/


    }




    @Override
    public Result updateLedger(St4ScsCk checkLedger, HttpServletRequest request, St4SysSa seUser) {
        /**
         * 这里目前只有环科院的需求会涉及台账表的更改操作，但是
         * 按照环科院的需求来说，并没有涉及整改照片，所以下面的图片的复杂处理应该不需要只需要改ck表和整改进展表就可以了
         * 其实对于环科院项目来说，整改进展和台账其实是一对一的只是为了在产品化的基础上去做，才把整改进展划出去了
         */
        List<St4ScsCn> cList = checkLedger.getCheckLedgerAttach();
        String processStatus = checkLedger.getProcessStatus();//整改状态
        Integer clId = checkLedger.getCk001();
        List<String> claUrlArr = new ArrayList<>();//图片集合
        List<String> descriList = new ArrayList<>();//图片描述集合
        LocalDateTime date = LocalDateTime.now();
        if(cList.size()>0){
            for (St4ScsCn St4ScsCn:cList) {

                claUrlArr.add(St4ScsCn.getCn004());
                descriList.add(St4ScsCn.getCn005());
                //这里是为了下面如果有新图片传递进来的时候没有设置台账id而顺便设置的
                St4ScsCn.setCk001(checkLedger.getCk001());
                St4ScsCn.setCn003(date);
                St4ScsCn.setCn006(seUser.getSa001());
            }
        }
        QueryWrapper<St4ScsCn> wrapper = new QueryWrapper<St4ScsCn>();
        wrapper.eq("CK001",clId);
        wrapper.eq("CN009",1);
        List<St4ScsCn> clasFromDbList = checkLedgerAttachMapper.selectList(wrapper);//数据库中图片表里查询出来的记录
        List<String> clasPhoList = new ArrayList<>();
        if(clasFromDbList.size()>0){
            for (St4ScsCn aa:clasFromDbList) {
                if(aa.getCn004()!=null){
                    clasPhoList.add(aa.getCn004());
                }
            }
        }
        Boolean flag=true;//数据库中存储的是否都把修改的时候上传的都包含在内 true代表都在内 false代表有新的图片
        if(clasPhoList.size()>0&&clasFromDbList.size()>0){
            for(String url:claUrlArr){
                if(!clasPhoList.contains(url)){
                    flag=false;
                    break;
                }
            }
        }else{
            flag=false;
        }
        Boolean flag1 = true;//上传的图片是否包含了数据库中存储的 这里的任意性比较大可以新上传的比之前的图片少所以得判断反包
        if(clasPhoList.size()>0&&claUrlArr.size()>0){
            for(String url:clasPhoList){
                if(!claUrlArr.contains(url)){
                    flag1=false;
                    break;
                }
            }
        }

        Integer updateNum1=0;
        String phoName="";
        List<St4ScsCn> claList = new ArrayList<>();
        if(!flag||!flag1){
            if(!flag){

                //代表修改的时候增加了新的图片
                //先把旧的图片都删掉
                int delNumA = 0;
                if(clasPhoList!=null&&clasPhoList.size()>0){
                    delNumA= checkLedgerAttachMapper.batchUpdateByCid(clasPhoList);
                }

                if(clasPhoList.size()>0){
                    for (String pho:clasPhoList) {
                        fileUpAndDownService.deletePicture(pho);
                    }
                }
                St4ScsCn cla=null;
                int cou=0;
                for(String phourl:claUrlArr){
                    if(phourl.contains(".")){
                        phoName=phourl.substring(phourl.lastIndexOf("\\")+1);
                    }
                    cla = new St4ScsCn();
                    cla.setCn004(phourl);
                    cla.setCn003(date);
                    cla.setCn006(seUser.getSa001());
                    cla.setCk001(clId);
                    cla.setCn005(descriList.get(cou));
                    cla.setCn007(phoName);
                    cla.setCn009(1);
                    cla.setCn010(processStatus);
                    claList.add(cla);
                    cou++;
                }
                int batchInsert = checkLedgerAttachMapper.insertList(claList);
                if(batchInsert>0){
                    return Result.build(1000,"修改"+ResultMsg.MSG_1000);
                }
            }else{
                //修改的时候有删掉的图片
                List<String> claListForDel = new ArrayList<>();//如果第一次的时候上传了5张图修改的时候删掉了2张
                for (String phourl:clasPhoList) {
                    if(!claUrlArr.contains(phourl)){
                        claListForDel.add(phourl);
                    }
                }
                Integer delNum=0;
                if(claListForDel.size()>0){
                    delNum = checkLedgerAttachMapper.batchUpdateByCid(claListForDel);
                    for (String path:claListForDel) {
                        fileUpAndDownService.deletePicture(path);
                    }
                    if(delNum>0){
                        return Result.build(1000,"修改"+ResultMsg.MSG_1000);
                    }
                }

            }
        }else{
            //图片没有修改只改文本信息即可
            updateNum1 = checkLedgerMapper.updateById(checkLedger);
            if(updateNum1>0){
                return Result.build(1000,"修改"+ResultMsg.MSG_1000);
            }
        }
        return Result.build(1005,ResultMsg.MSG_1005);
    }



    @Override
    public Result listLedger(St4ScsCk checkLedger,St4SysSa seUser) {

        seUser = st4SysSaMapper.selectById(seUser);
        checkLedger.setCk049(String.valueOf(seUser.getSa001()));
        if(seUser.getSa001()==1){
            checkLedger.setPtype(2);
        }else{
            if(seUser.getSa020()==0){
                checkLedger.setPtype(0);
            }else{
                checkLedger.setPtype(1);
            }
        }
        Page<St4ScsCk> page = new Page<>(checkLedger.getPageNumber(),checkLedger.getPageSize());
        IPage<St4ScsCk> list = checkLedgerMapper.listLedger(page,checkLedger);
        Result result = new Result();
        result.setRows(list.getRecords());

        result.setStatus(1000);
        result.setTotal((int)list.getTotal());
        result.setPage((int)list.getPages());
        result.setMsg("加载"+ResultMsg.MSG_1000);
        return result;


    }

    @Override
    public Result listHkyLedger(St4ScsCk checkLedger, St4SysSa seUser) {

        if(checkLedger.getStrTime()!=null && checkLedger.getStrTime() != ""){
            checkLedger.setStrTime(checkLedger.getStrTime()+" 00:00:00");
        }
        if(checkLedger.getEndTime()!=null && checkLedger.getEndTime() != ""){
            checkLedger.setEndTime(checkLedger.getEndTime()+" 23:59:59");
        }

        seUser = st4SysSaMapper.selectById(seUser);
        checkLedger.setCk049(String.valueOf(seUser.getSa001()));
        if(seUser.getSa001()==1){
            checkLedger.setPtype(2);
        }else{
            if(seUser.getSa020()==0){
                checkLedger.setPtype(0);
            }else{
                checkLedger.setPtype(1);
            }
        }
        Page<St4ScsCk> page = new Page<>(checkLedger.getPageNumber(),checkLedger.getPageSize());
        IPage<St4ScsCk> list = checkLedgerMapper.listHkyLedger(page,checkLedger);
        Result result = new Result();
        result.setRows(list.getRecords());

        result.setStatus(1000);
        result.setTotal((int)list.getTotal());
        result.setPage((int)list.getPages());
        result.setMsg("加载"+ResultMsg.MSG_1000);
        return result;
    }

    @Override
    public Result listLedgerSpace(Integer roleId,St4ScsCk checkLedger) {
        Page<St4ScsCk> page = new Page<>(checkLedger.getPageNumber(),checkLedger.getPageSize());
        IPage<St4ScsCk> list = checkLedgerMapper.listLedger(page,checkLedger);
        Result result = new Result();
        result.setStatus(1000);
        result.setTotal((int)list.getTotal());
        result.setPage((int)list.getPages());
        result.setMsg("加载"+ResultMsg.MSG_1000);
        result.setRows(list.getRecords());
        return result;
    }




    @Override
    public Result getDetail(St4ScsCk ck) {
        Result result = new Result();
        List<St4ScsCk> list = checkLedgerMapper.getStageDetail(ck);
        if(list.size()>0){
            for (St4ScsCk checkLedger:list) {

                QueryWrapper<St4ScsCn>    wrapper1 = new QueryWrapper<>();
                wrapper1.eq("CK001",checkLedger.getCk001());
                checkLedger.setCheckLedgerAttach(checkLedgerAttachMapper.selectList(wrapper1));
            }

        }
        result.setStatus(1000);
        result.setRows(list);
        result.setMsg("加载"+ResultMsg.MSG_1000);
        return result;
    }
    @Override
    public Result deleteLedger(Integer cld) {
        St4ScsCk cl = new St4ScsCk();
        cl.setCk085(0);
        cl.setCk001(cld);
        Integer del = checkLedgerMapper.updateById(cl);
        if(del>0){
            return  Result.build(1000,"删除"+ResultMsg.MSG_1000);
        }
        return Result.build(1003,ResultMsg.MSG_1003);
    }

    @Override
    public Result importExcelCommon(Map<String, MultipartFile> items, St4SysSa seUser,Integer taskId,List<Integer> uidList) {
        Result result = new Result();
        try{
            Iterator<MultipartFile> itr = items.values().iterator();
            while (itr.hasNext()) {
                Map<String,String> map=new HashMap<String, String>();
                MultipartFile item = itr.next();
                InputStream in=item.getInputStream();
                if(in==null){
                    return Result.build(1014,ResultMsg.MSG_1014);
                }
                boolean flag=true;//这个开关控制能不能执行插入操作
                List<Map> list = null;
                try {
                    list = ExcUtil.readExcelContent(in);
                }catch (Exception e){
                    e.printStackTrace();
                    return Result.build(1014,ResultMsg.MSG_1014);
                }

                //这里要验证问题编码不能重复
                List<String> pointNumList = new ArrayList<>();
                /**
                 * 这里的导入逻辑改变，编号可以重复，并且对于编号重复的问题点，先在问题点的表里进行基础信息的修改比如可能修改的值有分组信息所属保护地经纬度信息等等，然后
                 * 新增该问题点的。
                 */
                int jj=2;
                for (Map mapRe:list) {
                    //首先判断excel里面的问题点的序号不能为空且不能重复
                    Object pointCode = mapRe.get("问题编码");

                    if(!ObjectUtils.isNotNullAndEmpty(pointCode)){
                        flag=false;
                        result=Result.build(1001,ResultMsg.MSG_1001);
                        break;

                    }
                    St4ScsCd cd = new St4ScsCd();
                    cd.setCd004(pointCode.toString());
                    List<St4ScsCd> resData = checkPointMapper.getDataByCd004(cd);
                    //todo 后续避免循环中查询........
                    if(resData!=null&&resData.size()>0){
                        flag=false;
                        result=Result.build(1008,"第"+jj+"行问题编码与系统数据"+ResultMsg.MSG_1008);
                        break;
                    }
                    if(pointNumList!=null&&pointNumList.size()>0&&pointNumList.contains(mapRe.get("问题编码").toString())){

                        flag=false;
                        result=Result.build(1008,"第"+jj+"行问题编码"+ResultMsg.MSG_1008);
                        break;
                    }
                    pointNumList.add(mapRe.get("问题编码").toString());
                    jj++;
                }
                if(!flag){
                    return result;
                }
                List<String> repeatCodeList = new ArrayList<>();//这个list盛放的是重复的问题点的编号信息
                List<St4ScsCd> repeatPointList = new ArrayList<>();//这个list盛放的是重复的问题点的要修改的基础信息
                Map<String,Integer> pointIdCodeMap = new HashMap<>();//存放重复的问题点编号和id对应的集合后面做更改操作使用
                //重复的核查点的信息
                if(pointNumList.size()>0){
                    //这里要拿到和数据库里面对比之后编号重复的问题点信息
                    List<String> codeList = new ArrayList<>();
                    QueryWrapper<St4ScsCd> wrapper1 = new QueryWrapper<>();
                    wrapper1.eq("CD009",1);
                    wrapper1.in("CD004",pointNumList);
                    repeatPointList = checkPointMapper.selectList(wrapper1);
                    if(repeatPointList!=null&&repeatPointList.size()>0){
                        for (St4ScsCd cd:repeatPointList) {
                            repeatCodeList.add(cd.getCd004());
                        }
                    }

                }
                if(repeatPointList!=null&&repeatPointList.size()>0){
                    for (St4ScsCd cd:repeatPointList) {
                        pointIdCodeMap.put(cd.getCd004(),cd.getCd001());
                    }
                }

/*
                //这里的台账信息是没有用的因为只认编号，台账信息只涉及新增不再涉及更改
                Collection<Integer> collection = new ArrayList<>();
                if(repeatCodeList.size()>0){
                    for (String code:repeatCodeList) {
                        QueryWrapper<St4ScsCd> wrapperPo = new QueryWrapper<>();
                        wrapperPo.eq("CD004",code);
                        wrapperPo.eq("CD009",1);
                        collection.add(checkPointMapper.selectList(wrapperPo).get(0).getCd001());
                    }
                }

                //这里要存放重复了的台账信息供后面进行更新操作
                List<St4ScsCk> repeatLedgerList=new ArrayList<>();

                if(collection.size()>0){
                    QueryWrapper<St4ScsCk> wrapper = new QueryWrapper<>();
                    wrapper.in("CD004",collection);
                    repeatLedgerList = checkLedgerMapper.selectList(wrapper);

                }
*/
                Integer errRow = 0;
                St4ScsCk checkLedger = null;
                St4ScsCd checkPoint=null;
                int i = 2;
                //行政区集合
                Map<String,Integer> adminRegionMap = new HashMap<>();
                QueryWrapper<St4SysSd> adminWrapper = new QueryWrapper<>();
                adminWrapper.eq("SD007",1);
                List<St4SysSd> adminList = baseAdminRegionMapper.selectList(adminWrapper);
                for (St4SysSd bar:adminList) {
                    adminRegionMap.put(bar.getSd008(),bar.getSd001());
                }
                //保护区集合
                Map<String,Integer> reserveMap = new HashMap<>();
                QueryWrapper<St4SysSg> reserveDataWrapper = new QueryWrapper<>();
                reserveDataWrapper.eq("SG007",1);
                List<St4SysSg> reserveDataList = reserveDataMapper.getIdAndValue();
                for (St4SysSg rd:reserveDataList) {
                    reserveMap.put(rd.getSg008(),rd.getSg001());
                }
                //功能区
                Map<String,Integer> funAreaMap = new HashMap<>();
                QueryWrapper<St4ScsCq> funAreaWrapper = new QueryWrapper<>();
                List<St4ScsCq> funAreaDataList = checkFunctionAreaMapper.selectList(funAreaWrapper);
                for (St4ScsCq cfa:funAreaDataList) {
                    funAreaMap.put(cfa.getCq002(),cfa.getCq001());
                }
                //变化类型
                Map<String,Integer> checkChangeTypeMap = new HashMap<>();
                QueryWrapper<St4ScsCm> checkChangeTypeWrapper = new QueryWrapper<>();
                List<St4ScsCm> CheckChangeTypeList = checkChangeTypeMapper.selectList(checkChangeTypeWrapper);
                for (St4ScsCm cfa:CheckChangeTypeList) {
                    checkChangeTypeMap.put(cfa.getCm002(),cfa.getCm001());
                }
                //问题类型
                Map<String,Integer> checkQuestionTypeMap = new HashMap<>();
                QueryWrapper<St4ScsCr> checkQuestionTypeWrapper = new QueryWrapper<>();
                List<St4ScsCr> checkQuestionTypeList = checkQuestionTypeMapper.selectList(checkQuestionTypeWrapper);
                for (St4ScsCr cfa:checkQuestionTypeList) {
                    checkQuestionTypeMap.put(cfa.getCr002(),cfa.getCr001());
                }

                List<St4ScsCd> importPointList = new ArrayList<>();//要导入的核查点的信息
                List<St4ScsCk> importLedgerList = new ArrayList<>();//即将插入的台账信息

                List<St4ScsCd> hasPointFromDb = new ArrayList<>();//存放已经有了问题点的台账信息

                List<String> repairText = new ArrayList<>();
                for(Map data:list){
                    checkLedger = new St4ScsCk();
                    checkPoint = new St4ScsCd();
                    checkPoint.setCd007(1);
                    checkPoint.setCl001(taskId);
                    checkPoint.setCd011(LocalDateTime.now());
                    checkLedger.setCk091(taskId);//设置所属任务
                    checkLedger.setCk085(1);
                    checkLedger.setCk088(0);
                    checkLedger.setCk089(0);
                    Object aa = data.get("行政区");
                    if(!ObjectUtils.isNotNullAndEmpty(data.get("行政区"))){
                        flag = false;
                        result =  Result.build(1001,"第"+i+"行行政区"+ResultMsg.MSG_1001);
                        break;
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("行政区"))){
                        checkPoint.setSd001(adminRegionMap.get(data.get("行政区").toString()));
                    }
                    if(!ObjectUtils.isNotNullAndEmpty(data.get("问题编码"))){
                        flag = false;
                        result =  Result.build(1001,"第"+i+"行问题编码"+ResultMsg.MSG_1001);
                        break;
                    };
                    Object code = data.get("问题编码");
                    checkPoint.setCd004(code.toString());
                    checkLedger.setCd004(code.toString());
                    //这里得先批量插入问题点后拿到ID再去往台账表中插入数据
                    if(!ObjectUtils.isNotNullAndEmpty(data.get("保护区名称"))){
                        flag = false;
                        result =  Result.build(1001,"第"+i+"行保护区名称"+ResultMsg.MSG_1001);
                        break;
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("保护区名称"))){
                        Integer bid = reserveMap.get(data.get("保护区名称").toString());
                        if(bid == null){
                            flag = false;
                            result =  Result.build(1001,"平台中未找到"+"保护区");
                            break;
                        }
                        checkPoint.setSg001(reserveMap.get(data.get("保护区名称").toString()));
                        checkLedger.setSg001(reserveMap.get(data.get("保护区名称").toString()));
                    }

                    if(!ObjectUtils.isNotNullAndEmpty(data.get("所在功能区"))){
                        flag = false;
                        result =  Result.build(1001,"第"+i+"行所在功能区"+ResultMsg.MSG_1001);
                        break;
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("所在功能区"))){
                        checkLedger.setCk005(data.get("所在功能区").toString());
                    }
                    /*if(!ObjectUtils.isNotNullAndEmpty(data.get("实际功能区"))){
                        flag = false;
                        result =  Result.build(1001,"第"+i+"行实际功能区"+ResultMsg.MSG_1001);
                        break;
                    };*/
                    if(ObjectUtils.isNotNullAndEmpty(data.get("实际功能区"))){
                        checkLedger.setCk005(data.get("所在功能区").toString());
                    }
                    if(!ObjectUtils.isNotNullAndEmpty(data.get("经度"))){
                        flag = false;
                        result =  Result.build(1001,"第"+i+"行经度"+ResultMsg.MSG_1001);
                        break;
                    };
                    String lon =data.get("经度").toString().trim();
                    if(!LedgerHelp.checkLongude(lon)){
                        if(!LedgerHelp.IsRulelon(lon)){

                            flag = false;
                            result =  Result.build(1007,"第"+i+"行经度"+ResultMsg.MSG_1007);
                            break;
                        }
                    }

                    if(lon.contains("°")){
                        checkPoint.setCd013(lon);
                        checkPoint.setCd015(0);
                        //将度分秒格式转经纬度存
                        checkPoint.setCd002(PointHelp.Dms2D(lon));
                    }else{
                        checkPoint.setCd002(lon);
                        checkPoint.setCd015(1);
                        //将经纬度转成度分秒
                        checkPoint.setCd013(PointHelp.toDfm(lon));
                    }

                    checkLedger.setCk007(lon);
                    if(!ObjectUtils.isNotNullAndEmpty(data.get("纬度"))){
                        flag = false;
                        result =  Result.build(1001,"第"+i+"行纬度"+ResultMsg.MSG_1001);
                        break;
                    };
                    String lat =data.get("纬度").toString().trim();
                    if(!LedgerHelp.checkItude(lat)){
                        if(!LedgerHelp.IsRulelat(lat)){
                            flag = false;
                            result =  Result.build(1007,"第"+i+"行纬度"+ResultMsg.MSG_1007);
                            break;
                        }
                    }
                    if(lon.contains("°")){
                        checkPoint.setCd014(lat);
                        //将度分秒格式转经纬度存
                        checkPoint.setCd003(PointHelp.Dms2D(lat));
                    }else{
                        checkPoint.setCd003(lat);
                        //将经纬度转度分秒
                        checkPoint.setCd014(PointHelp.toDfm(lat));
                    }
                    checkLedger.setCk008(lat);
                    if(ObjectUtils.isNotNullAndEmpty(data.get("位置"))){
                        checkLedger.setCk009(data.get("位置").toString().trim());
                    };

                    if(ObjectUtils.isNotNullAndEmpty(data.get("占地面积(㎡)"))){
                        checkLedger.setCk011(data.get("占地面积(㎡)").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("面积(㎡)"))){
                        checkLedger.setCk011(data.get("面积(㎡)").toString().trim());
                    };

                    if(ObjectUtils.isNotNullAndEmpty(data.get("活动设施现状"))){
                        checkLedger.setCk012(data.get("活动设施现状").toString().trim());
                    };

                    if(ObjectUtils.isNotNullAndEmpty(data.get("变化类型"))){
                        checkLedger.setCk013(data.get("变化类型").toString());
                    };

                    if(ObjectUtils.isNotNullAndEmpty(data.get("是否违法违规"))){
                        checkLedger.setCk014(LedgerHelp.setValueId(data.get("是否违法违规").toString().trim()));
                    };

                    if(ObjectUtils.isNotNullAndEmpty(data.get("问题描述"))){
                        checkLedger.setCk015(data.get("问题描述").toString().trim());
                    };

                    if(ObjectUtils.isNotNullAndEmpty(data.get("问题类型(活动/设施类型)"))){
                        checkLedger.setCk016(data.get("问题类型(活动/设施类型)").toString());
                    };

                    if(ObjectUtils.isNotNullAndEmpty(data.get("下发问题类型"))){
                        checkLedger.setCk016(data.get("下发问题类型").toString());
                    };


                    if(ObjectUtils.isNotNullAndEmpty(data.get("实际问题类型(描述)"))){
                        checkLedger.setCk017(data.get("实际问题类型(描述)").toString());
                    };

                    if(ObjectUtils.isNotNullAndEmpty(data.get("存在问题及主要生态影响"))){
                        checkLedger.setCk018(data.get("存在问题及主要生态影响").toString().trim());
                    };

                    if(ObjectUtils.isNotNullAndEmpty(data.get("建设单位"))){
                        checkLedger.setCk019(data.get("建设单位").toString().trim());
                    };
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    if(ObjectUtils.isNotNullAndEmpty(data.get("建设时间"))){
                        Object aaa=data.get("建设时间");
                        try{
                            checkLedger.setCk020(data.get("建设时间").toString().trim());
                            //checkLedger.setCk020(LocalDateTi me.from(LocalDate.parse(data.get("建设时间").toString().trim(),dfMd).atStartOfDay()));
                        }catch (Exception e){
                            e.printStackTrace();
                            flag=false;
                            result=Result.build(1002,ResultMsg.MSG_1002);
                            break;
                        }

                    }

                    if(ObjectUtils.isNotNullAndEmpty(data.get("有无环评手续"))){
                        checkLedger.setCk021(LedgerHelp.setValueId(data.get("有无环评手续").toString().trim()));
                    }



                    if(ObjectUtils.isNotNullAndEmpty(data.get("环评手续批复及验收文号"))){
                        checkLedger.setCk022(data.get("环评手续批复及验收文号").toString().trim());
                    };

                    if(ObjectUtils.isNotNullAndEmpty(data.get("有无林业相关审批手续"))){
                        checkLedger.setCk023(LedgerHelp.setValueId(data.get("有无林业相关审批手续").toString().trim()));
                    };

                    if(ObjectUtils.isNotNullAndEmpty(data.get("林业手续批复及验收文号"))){
                        checkLedger.setCk024(data.get("林业手续批复及验收文号").toString().trim());
                    };

                    if(ObjectUtils.isNotNullAndEmpty(data.get("有无养殖业相关审批手续"))){
                        checkLedger.setCk025(LedgerHelp.setValueId(data.get("有无养殖业相关审批手续").toString().trim()));
                    };

                    if(ObjectUtils.isNotNullAndEmpty(data.get("养殖业手续批复及验收文号"))){
                        checkLedger.setCk026(data.get("养殖业手续批复及验收文号").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("有无发改部门相关手续"))){
                        checkLedger.setCk027(LedgerHelp.setValueId(data.get("有无发改部门相关手续").toString().trim()));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("发改部门相关手续"))){
                        checkLedger.setCk028((data.get("发改部门相关手续").toString().trim()));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("有无规划相关审批手续"))){
                        checkLedger.setCk029(LedgerHelp.setValueId(data.get("有无规划相关审批手续").toString().trim()));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("规划手续批复及验收文号"))){
                        checkLedger.setCk030(data.get("规划手续批复及验收文号").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("有无旅游相关手续"))){
                        checkLedger.setCk031(LedgerHelp.setValueId(data.get("有无旅游相关手续").toString().trim()));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("旅游手续批复及验收文号"))){
                        checkLedger.setCk032(data.get("旅游手续批复及验收文号").toString().trim());
                    };

                    if(ObjectUtils.isNotNullAndEmpty(data.get("有无财政部门相关资金手续"))){
                        checkLedger.setCk033(LedgerHelp.setValueId(data.get("有无财政部门相关资金手续").toString().trim()));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("财政部门相关资金手续批复文号"))){
                        checkLedger.setCk034(data.get("财政部门相关资金手续批复文号").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("有无工商营业执照"))){
                        checkLedger.setCk035(LedgerHelp.setValueId(data.get("有无工商营业执照").toString().trim()));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("营业执照注册号或统一社会信用代码"))){
                        checkLedger.setCk036(data.get("营业执照注册号或统一社会信用代码").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("有无其他相关审批手续或行政许可手续"))){
                        checkLedger.setCk037(LedgerHelp.setValueId(data.get("有无其他相关审批手续或行政许可手续").toString().trim()));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("其他相关审批手续或行政许可手续"))){
                        checkLedger.setCk038(data.get("其他相关审批手续或行政许可手续").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("是否处罚"))){
                        checkLedger.setCk039(LedgerHelp.setValueId(data.get("是否处罚").toString().trim()));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("处罚形式"))){
                        checkLedger.setCk040(data.get("处罚形式").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("罚款(万元)"))){
                        checkLedger.setCk041(data.get("罚款(万元)").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("整改措施"))){
                        checkLedger.setCk043(data.get("整改措施").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("整改时限"))){

                        String repair = data.get("整改时限").toString().trim();
                        if(ExcUtil.isNumeric(repair)){
                            flag=false;
                            result = Result.build(1007,"第"+i+"行整改时限格式"+ResultMsg.MSG_1007);
                            break;
                        }
                        try {
                            String rDate=ExcUtil.getStandardDate(data.get("整改时限").toString().trim());
                            LocalDate beginDateTime = LocalDate.parse(rDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            checkLedger.setCk042(beginDateTime);
                        }catch (Exception e){
                            flag=false;
                            result = Result.build(1007,"第"+i+"行整改时限格式"+ResultMsg.MSG_1007);
                            break;
                        }


                    };
                    repairText.add(data.get("整改进展")==null?"":data.get("整改进展").toString());

                    if(ObjectUtils.isNotNullAndEmpty(data.get("拆除建筑面积(㎡)"))){
                        checkLedger.setCk044(data.get("拆除建筑面积(㎡)").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("处理情况"))){
                        checkLedger.setCk045(data.get("处理情况").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("是否销号"))){
                        checkLedger.setCk046(data.get("是否销号").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("核查单位"))){
                        checkLedger.setCk047(data.get("核查单位").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("核查时间"))){
                        checkLedger.setCk048(LocalDateTime.from(LocalDate.parse(data.get("核查时间").toString().trim(),dfMd).atStartOfDay()));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("核查人"))){
                        checkLedger.setCk049(data.get("核查人").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("联系方式"))){
                        checkLedger.setCk050(data.get("联系方式").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("参与核查人数"))){
                        checkLedger.setCk051(LedgerHelp.setValueId(data.get("参与核查人数").toString().trim()));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("填表人"))){
                        checkLedger.setCk052(data.get("填表人").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("审核人"))){
                        checkLedger.setCk053(data.get("审核人").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("是否填写问责情况"))){
                        checkLedger.setCk054(LedgerHelp.setValueId(data.get("是否填写问责情况").toString().trim()));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("省级追责问责人数"))){
                        checkLedger.setCk055(LedgerHelp.setValueId(data.get("省级追责问责人数").toString().trim()));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("厅级追责问责人数"))){
                        checkLedger.setCk056(LedgerHelp.setValueId(data.get("厅级追责问责人数").toString().trim()));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("县级追责问责人数"))){
                        checkLedger.setCk057(LedgerHelp.setValueId(data.get("县级追责问责人数").toString().trim()));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("科级追责问责人数"))){
                        checkLedger.setCk058(LedgerHelp.setValueId(data.get("科级追责问责人数").toString().trim()));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("其他追责问责人数"))){
                        checkLedger.setCk059(LedgerHelp.setValueId(data.get("其他追责问责人数").toString().trim()));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("党纪政纪处分人数"))){
                        checkLedger.setCk060(LedgerHelp.setValueId(data.get("党纪政纪处分人数").toString().trim()));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("诫勉谈话人数"))){
                        checkLedger.setCk061(LedgerHelp.setValueId(data.get("诫勉谈话人数").toString().trim()));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("通报批评人数"))){
                        checkLedger.setCk062(LedgerHelp.setValueId(data.get("通报批评人数").toString().trim()));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("追责问责情况表备注"))){
                        checkLedger.setCk063(data.get("追责问责情况表备注").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("追责问责情况表填表人"))){
                        checkLedger.setCk064(data.get("追责问责情况表填表人").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("追责问责情况表联系方式"))){
                        checkLedger.setCk065(data.get("追责问责情况表联系方式").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("追责问责情况表审核人"))){
                        checkLedger.setCk066(data.get("追责问责情况表审核人").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("备注"))){
                        checkLedger.setCk076(data.get("备注").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("问责问题描述"))){
                        checkLedger.setCk077(data.get("问责问题描述").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("台账来源"))){
                        checkLedger.setCk078(data.get("台账来源").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("自用备注"))){
                        checkLedger.setCk079(data.get("自用备注").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("是否四类聚焦"))){
                        checkLedger.setCk080(LedgerHelp.setValueId(data.get("是否四类聚焦").toString().trim()));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("是否巡查台账"))){
                        checkLedger.setCk081(LedgerHelp.setValueId(data.get("是否巡查台账").toString().trim()));
                    };

                    if(ObjectUtils.isNotNullAndEmpty(data.get("是否市级巡查"))){
                        checkLedger.setCk082(LedgerHelp.setValueId(data.get("是否市级巡查").toString().trim()));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("是否国家点"))){
                        checkLedger.setCk083(LedgerHelp.setValueId(data.get("是否国家点").toString().trim()));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("是否重点台账"))){
                        checkLedger.setCk084(LedgerHelp.setValueId((data.get("是否重点台账").toString().trim())));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("所属任务"))){
                        checkPoint.setCl001(LedgerHelp.setValueId(data.get("所属任务").toString().trim()));
                    };
                    LocalDateTime date = LocalDateTime.now();
                    checkLedger.setCk086(date);
                    checkLedger.setCk087(seUser.getSa001());
                    checkPoint.setCd009(1);

                    if(repeatCodeList.contains(data.get("问题编码").toString())){
                        checkPoint.setCd001(pointIdCodeMap.get(data.get("问题编码").toString().trim()));
                        hasPointFromDb.add(checkPoint);
                    }else{
                        importPointList.add(checkPoint);
                    }
                    importLedgerList.add(checkLedger);
                    i++;
                }
                if(flag){

                    //如果有要更新的问题点信息则先进行更新操作
                    int upnum=0;
                    int batch=0;
                    int psize = hasPointFromDb.size();
                    int isize=importPointList.size();
                    int lsize=importLedgerList.size();
                    Boolean upPointFlag=false;//问题点批量修改结果
                    Boolean inPointFlag=false;//问题点批量插入结果
                    Boolean inLedgerFlag=false;//问题点台账批量插入结果
                    Boolean inLedgerAttachFlag=false;//问题点台账附件表批量插入结果
                    if(psize>0){
                        //如果有重复的问题点的信息，先进行问题点信息的批量更改操作
                        //checkPointMapper.u
                        upPointFlag=checkPointService.updateBatchById(hasPointFromDb);
                        if(!upPointFlag){
                            return Result.build(1006,"导入"+ResultMsg.MSG_1006);
                        }
                    }
                    if(lsize>0){
                        int k = 0;
                        //这里得首先批量插入核查点  再拿着核查点给每一个台账的对象
                        //批量插入
                        if(importPointList!=null&&importPointList.size()>0){
                            inPointFlag =checkPointService.saveBatch(importPointList);
                            if(inPointFlag){
                                for (St4ScsCd cp:importPointList) {
                                    importLedgerList.get(k).setCd004(cp.getCd004());
                                    k++;
                                }
                            }
                        }


                        inLedgerFlag =checkLedgerService.saveBatch(importLedgerList);
                        St4ScsCn cla = null;
                        int j =0;
                        List<St4ScsCn> claList = new ArrayList<>();//进度附件表的插入
                        LocalDateTime date = LocalDateTime.now();
                        for (St4ScsCk cl:importLedgerList) {
                            cla= new St4ScsCn();
                            cla.setCn003(date);
                            cla.setCn006(seUser.getSa001());
                            cla.setCn010(repairText.get(j));
                            cla.setCk001(cl.getCk001());
                            claList.add(cla);
                            j++;
                        }
                        if(inLedgerFlag){

                        }
                        inLedgerAttachFlag = checkLedgerAttachService.saveBatch(claList);
                    }
                    if(inLedgerFlag&&inLedgerFlag){
                        return Result.build(1000,"导入"+ResultMsg.MSG_1000);
                    }else {
                        return Result.build(1006,"导入"+ResultMsg.MSG_1006);
                    }

                }else {
                    return result;
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
    @Override
    public Result  importExcelHky(Map<String, MultipartFile> items, St4SysSa seUser,Integer taskId,List<Integer> uidList) {
        Result result = new Result();
        try{
            Iterator<MultipartFile> itr = items.values().iterator();
            while (itr.hasNext()) {
                Map<String,String> map=new HashMap<String, String>();
                MultipartFile item = itr.next();
                InputStream in=item.getInputStream();
                boolean flag=true;//这个开关控制能不能执行插入操作
                List<Map> list = ExcUtil.readExcelContent(in);
                //这里要验证点位序号不能重复
                List<String> pointNumList = new ArrayList<>();

                for (Map mapRe:list) {
                    //首先判断自身是否重复
                    if(pointNumList.size()>0&&pointNumList.contains(mapRe.get("点位序号").toString())){
                        //这里的逻辑改变针对北京环科院是更新原有的

                        flag=false;
                        result=Result.build(1008,ResultMsg.MSG_1008);
                        break;
                    }
                    pointNumList.add(mapRe.get("点位序号").toString());
                }
                List<String> repeatCodeList = new ArrayList<>();//这个用来存放code重复的集合
                //重复的干扰点的信息
                if(pointNumList.size()>0){

                    for (String code:pointNumList) {
                        QueryWrapper<St4ScsCk> ckWrapper = new QueryWrapper<>();
                        ckWrapper.eq("CD004",code);
                        ckWrapper.eq("CK085",1);
                        List<St4ScsCk> pointList = checkLedgerMapper.selectList(ckWrapper);
                        if(pointList.size()>0){
                            //这种情况代表数据库的有和excel里面的有重复的
                            repeatCodeList.add(pointList.get(0).getCd004());
                        }
                    }

                }


                Collection<String> collection = new ArrayList<>();
                if(repeatCodeList.size()>0){
                    for (String code:repeatCodeList) {
                        QueryWrapper<St4ScsCk> wrapperPo = new QueryWrapper<>();
                        wrapperPo.eq("CD004",code);
                        wrapperPo.eq("CK085",1);
                        collection.add(checkLedgerMapper.selectList(wrapperPo).get(0).getCd004());
                    }
                }

                //这里要存放重复了的台账信息供后面进行更新操作!!!!!!!!!!!!!!!
                List<St4ScsCk> repeatLedgerList=new ArrayList<>();

                if(collection.size()>0){
                    QueryWrapper<St4ScsCk> wrapper = new QueryWrapper<>();
                    wrapper.in("CD004",collection);
                    repeatLedgerList = checkLedgerMapper.selectList(wrapper);

                }
                //这个是以后比较方便取出来的
                Map<String,St4ScsCk> repeatledgerMap = new HashMap<>();
                if(repeatLedgerList.size()>0){
                    for (St4ScsCk ckk:repeatLedgerList) {
                        repeatledgerMap.put(ckk.getCd004(),ckk);
                    }
                }


                Integer errRow = 0;
                St4ScsCk checkLedger = null;
                int i = 2;
                //行政区集合
                Map<String,Integer> adminRegionMap = new HashMap<>();
                QueryWrapper<St4SysSd> adminWrapper = new QueryWrapper<>();
                adminWrapper.eq("SD007",1);
                List<St4SysSd> adminList = baseAdminRegionMapper.selectList(adminWrapper);
                for (St4SysSd bar:adminList) {
                    adminRegionMap.put(bar.getSd008(),bar.getSd001());
                }
                //保护区集合
                Map<String,Integer> reserveMap = new HashMap<>();
                QueryWrapper<St4SysSg> reserveDataWrapper = new QueryWrapper<>();
                reserveDataWrapper.eq("SG007",1);
                List<St4SysSg> reserveDataList = reserveDataMapper.selectList(reserveDataWrapper);
                for (St4SysSg rd:reserveDataList) {
                    reserveMap.put(rd.getSg008(),rd.getSg001());
                }
                //这里有差异的台账是更新原有的还是新增还未定下
                List<St4ScsCk> importLedgerList = new ArrayList<St4ScsCk>();//即将插入的台账信息
                List<St4ScsCk> updateLedgerList = new ArrayList<>();//要更新的台账信息

                List<St4ScsCac> cacUpdateList = new ArrayList<>();//要更新的时间提示信息

                List<String> repairText = new ArrayList<>();
                /**
                 * 这里开始进行导入操作的核心处理逻辑 这里就不存问题点表这个概念，所有的信息都在ck表里面
                 * 需求:
                 * 实现导入更新的台账与原有台账内容的自动比对，如内容无差异则不更新，
                 * 如内容有差异的填报内容则在原填报内容后自动增加更新台账的内容，
                 * 并在更新内容后附更新时间。如第一次导入台账中整改措施内容为“2019年1月完成”，
                 * 2019年1月份进行第二次更新台账时内容为“2019年2月完成”，
                 * 则更新后整改措施一栏内容应为“2019年1月完成；2019年2月完成（2019年1月更新）”
                 */
                for(Map data:list){
                    checkLedger = new St4ScsCk();
                    checkLedger.setCk091(taskId);//设置所属任务
                    checkLedger.setCk085(1);
                    if(!ObjectUtils.isNotNullAndEmpty(data.get("行政区名称"))){
                        flag = false;
                        result =  Result.build(1001,"第"+i+"行行政区名称"+ResultMsg.MSG_1001);
                        break;
                    };
                    checkLedger.setCk002(adminRegionMap.get(data.get("行政区名称").toString().trim()));
                    if(!ObjectUtils.isNotNullAndEmpty(data.get("点位序号"))){
                        flag = false;
                        result =  Result.build(1001,"第"+i+"点位序号"+ResultMsg.MSG_1001);
                        break;
                    };
                    Object code = data.get("点位序号");
                    checkLedger.setCd004(code.toString());
                    //这里得拿着这个点位序号去和数据库查出来的比较如果没有差异那就不更新，否则更新掉之前的
                    if(!ObjectUtils.isNotNullAndEmpty(data.get("保护区名称"))){
                        flag = false;
                        result =  Result.build(1001,"第"+i+"行保护区名称"+ResultMsg.MSG_1001);
                        break;
                    };
                    checkLedger.setSg001(reserveMap.get(data.get("保护区名称").toString().trim()));

                    if(!ObjectUtils.isNotNullAndEmpty(data.get("所在功能区"))){
                        flag = false;
                        result =  Result.build(1001,"第"+i+"行所在功能区"+ResultMsg.MSG_1001);
                        break;
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("所在功能区"))){
                        checkLedger.setCk005(data.get("所在功能区").toString());
                    }
                    if(ObjectUtils.isNotNullAndEmpty(data.get("实际功能区"))){
                        checkLedger.setCk006(data.get("实际功能区").toString());
                    }
                    if(!ObjectUtils.isNotNullAndEmpty(data.get("经度"))){
                        flag = false;
                        result =  Result.build(1001,"第"+i+"行经度"+ResultMsg.MSG_1001);
                        break;
                    };
                    String lon =data.get("经度").toString().trim();
                    if(!LedgerHelp.checkLongude(lon)){
                        if(!LedgerHelp.IsRulelon(lon)){

                            flag = false;
                            result =  Result.build(1007,"第"+i+"行经度"+ResultMsg.MSG_1007);
                            break;
                        }
                    }
                    checkLedger.setCk007(lon);
                    if(!ObjectUtils.isNotNullAndEmpty(data.get("纬度"))){
                        flag = false;
                        result =  Result.build(1001,"第"+i+"行纬度"+ResultMsg.MSG_1001);
                        break;
                    };
                    String lat =data.get("纬度").toString().trim();
                    if(!LedgerHelp.checkItude(lat)){
                        if(!LedgerHelp.IsRulelat(lat)){
                            flag = false;
                            result =  Result.build(1007,"第"+i+"行纬度"+ResultMsg.MSG_1007);
                            break;
                        }
                    }
                    checkLedger.setCk008(lat);
                    if(ObjectUtils.isNotNullAndEmpty(data.get("位置"))){
                        checkLedger.setCk009(data.get("位置").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("活动/设施名称"))){
                        checkLedger.setCk010(data.get("活动/设施名称").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("占地面积(㎡)"))){
                        checkLedger.setCk011(data.get("占地面积(㎡)").toString().trim());
                    };

                    if(ObjectUtils.isNotNullAndEmpty(data.get("面积(㎡)"))){
                        checkLedger.setCk011(data.get("面积(㎡)").toString().trim());
                    };

                    if(ObjectUtils.isNotNullAndEmpty(data.get("活动设施现状"))){
                        checkLedger.setCk012(data.get("活动设施现状").toString().trim());
                    };

                    if(ObjectUtils.isNotNullAndEmpty(data.get("变化类型"))){
                        checkLedger.setCk013(data.get("变化类型").toString());
                    };

                    if(ObjectUtils.isNotNullAndEmpty(data.get("是否违法违规"))){
                        checkLedger.setCk014(LedgerHelp.setValueId(data.get("是否违法违规").toString().trim()));
                    };

                    if(ObjectUtils.isNotNullAndEmpty(data.get("问题描述"))){
                        checkLedger.setCk015(data.get("问题描述").toString().trim());
                    };

                    if(ObjectUtils.isNotNullAndEmpty(data.get("问题类型(活动/设施类型)"))){
                        checkLedger.setCk016(data.get("问题类型(活动/设施类型)").toString());
                    };

                    if(ObjectUtils.isNotNullAndEmpty(data.get("下发问题类型"))){
                        checkLedger.setCk016(data.get("下发问题类型").toString());
                    };


                    if(ObjectUtils.isNotNullAndEmpty(data.get("实际问题类型(描述)"))){
                        checkLedger.setCk017(data.get("实际问题类型(描述)").toString());
                    };

                    if(ObjectUtils.isNotNullAndEmpty(data.get("存在问题及主要生态影响"))){
                        checkLedger.setCk018(data.get("存在问题及主要生态影响").toString().trim());
                    };

                    if(ObjectUtils.isNotNullAndEmpty(data.get("建设单位"))){
                        checkLedger.setCk019(data.get("建设单位").toString().trim());
                    };
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    if(ObjectUtils.isNotNullAndEmpty(data.get("建设时间"))){
                        try{

                            checkLedger.setCk020(data.get("建设时间").toString().trim());
                            //checkLedger.setCk020(LocalDateTime.from(LocalDate.parse(data.get("建设时间").toString().trim(),dfMd).atStartOfDay()));
                        }catch (Exception e){
                            e.printStackTrace();
                            flag=false;
                            result=Result.build(1002,ResultMsg.MSG_1002);
                            break;
                        }

                    }

                    if(ObjectUtils.isNotNullAndEmpty(data.get("是否处罚"))){
                        checkLedger.setCk039(LedgerHelp.setValueId(data.get("是否处罚").toString().trim()));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("处罚形式"))){
                        checkLedger.setCk040(data.get("处罚形式").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("罚款(万元)"))){
                        checkLedger.setCk041(data.get("罚款(万元)").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("整改时限"))){
                        String rDate=ExcUtil.getStandardDate(data.get("整改时限").toString().trim());
                        LocalDate beginDateTime = LocalDate.parse(rDate, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                        checkLedger.setCk042(beginDateTime);
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("整改措施"))){
                        checkLedger.setCk043(data.get("整改措施").toString().trim());
                    };

                    repairText.add(data.get("整改进展")==null?"":data.get("整改进展").toString());

                    if(ObjectUtils.isNotNullAndEmpty(data.get("拆除建筑面积(㎡)"))){
                        checkLedger.setCk044(data.get("拆除建筑面积(㎡)").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("处理情况"))){
                        checkLedger.setCk045(data.get("处理情况").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("是否销号"))){
                        checkLedger.setCk046(data.get("是否销号").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("核查单位"))){
                        checkLedger.setCk047(data.get("核查单位").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("核查时间"))){
                        checkLedger.setCk048(LocalDateTime.from(LocalDate.parse(data.get("核查时间").toString().trim(),dfMd).atStartOfDay()));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("核查人"))){
                        checkLedger.setCk049(data.get("核查人").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("联系方式"))){
                        checkLedger.setCk050(data.get("联系方式").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("参与核查人数"))){
                        checkLedger.setCk051(LedgerHelp.setValueId(data.get("参与核查人数").toString().trim()));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("填表人"))){
                        checkLedger.setCk052(data.get("填表人").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("审核人"))){
                        checkLedger.setCk053(data.get("审核人").toString().trim());
                    };

                    if(ObjectUtils.isNotNullAndEmpty(data.get("备注"))){
                        checkLedger.setCk076(data.get("备注").toString().trim());
                    };

                    if(ObjectUtils.isNotNullAndEmpty(data.get("台账来源"))){
                        checkLedger.setCk078(data.get("台账来源").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("自用备注"))){
                        checkLedger.setCk079(data.get("自用备注").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("是否四类聚焦"))){
                        checkLedger.setCk080(LedgerHelp.setValueId(data.get("是否四类聚焦").toString().trim()));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("是否巡查台账"))){
                        checkLedger.setCk081(LedgerHelp.setValueId(data.get("是否巡查台账").toString().trim()));
                    };

                    if(ObjectUtils.isNotNullAndEmpty(data.get("是否市级巡查"))){
                        checkLedger.setCk082(LedgerHelp.setValueId(data.get("是否市级巡查").toString().trim()));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("是否国家点"))){
                        checkLedger.setCk083(LedgerHelp.setValueId(data.get("是否国家点").toString().trim()));
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("是否重点台账"))){
                        checkLedger.setCk084(LedgerHelp.setValueId((data.get("是否重点台账").toString().trim())));
                    };

                    LocalDateTime date = LocalDateTime.now();
                    checkLedger.setCk086(date);
                    checkLedger.setCk087(seUser.getSa001());
                    St4ScsCac ssc = new St4ScsCac();

                    if(repeatCodeList.contains(data.get("点位序号").toString())){
                        //这里就在这里比较现在的这个checkLedger和数据库查询的是否有差异有则更新
                        St4ScsCk ck1 = repeatledgerMap.get(data.get("点位序号").toString());
                        ssc.setCk001(ck1.getCk001());
                        St4ScsCn cn1 = checkLedgerAttachMapper.selectCn010ByCk001(ck1.getCk001());
                        //ck1.equals(checkLedger)&&cn1.getCn010().equals(data.get("整改进展").toString())
                        boolean repairflag=true;//代表无差异
                        Object repObj = data.get("整改进展");
                        if(ObjectUtils.isNotNullAndEmpty(repObj)&&cn1==null){
                            repairflag=false;//代表有差异
                        }else if(cn1!=null&&repObj==null){
                            repairflag=false;//代表有差异
                        }else if(!cn1.getCn010().equals(repObj.toString())){
                            repairflag=false;//代表有差异
                        }
                        if(!ck1.equals(checkLedger)||!repairflag){
                            //这里代表前后的值有差异
                            //这里还得考虑初始值是  核心区，缓冲区(2019年1月更新)的截取的情况
                            checkLedger.setCk001(ck1.getCk001());
                            Calendar cal = Calendar.getInstance();
                            Integer year = cal.get(Calendar.YEAR);
                            Integer month = cal.get(Calendar.MONTH)+1;
                            Integer mill = cal.get(Calendar.SECOND);
                            String updateMsg="("+year+"年"+month+"月"+mill+"秒更新)";
                            ssc.setCac003(updateMsg);//所在功能区
                            ssc.setCac004(updateMsg);//实际功能区
                            ssc.setCac007(updateMsg);//所在位置描述
                            ssc.setCac012(updateMsg);//问题类型（活动/设施类型）
                            ssc.setCac009(updateMsg);//变化类型
                            ssc.setCac008(updateMsg);//面积(m²)
                            ssc.setCac026(updateMsg);//台账来源
                            ssc.setCac011(updateMsg);//问题描述
                            ssc.setCac013(updateMsg);//实际问题类型
                            ssc.setCac014(updateMsg);//建设单位
                            ssc.setCac015(updateMsg);//建设时间
                            ssc.setCac017(updateMsg);//处罚形式
                            ssc.setCac018(updateMsg);//罚款（万元）
                            ssc.setCac021(updateMsg);//拆除建筑面积
                            ssc.setCac022(updateMsg);//是否销号
                            ssc.setCac025(updateMsg);//备注
                            ssc.setCac027(updateMsg);//自用备注
                            ssc.setCac019(updateMsg);//整改时限
                            ssc.setCac016(updateMsg);//是否处罚
                            ssc.setCac010(updateMsg);//是否违法违规
                            ssc.setCac028(updateMsg);//是否四类聚焦
                            ssc.setCac029(updateMsg);//是否巡查台账
                            ssc.setCac030(updateMsg);//是否市级巡查
                            ssc.setCac031(updateMsg);//是否国家点
                            ssc.setCac032(updateMsg);//是否重点台账*/
                            ssc.setCac036(updateMsg);//整改进展*/

                            cacUpdateList.add(ssc);
                            updateLedgerList.add(checkLedger);
                        }else {

                        }
                        //这里如果是第一次导入的没有整改进展，那么进度表里面是没有数据，第二次导入的时候还是这个点位序号，但是有整改进展了，应该怎么处理???
                    }else{
                        importLedgerList.add(checkLedger);

                    }
                    i++;
                }
                if(flag){

                    //这里先进行更新操作
                    Boolean upCnNum=false;
                    int batch=0;
                    int usize = updateLedgerList.size();
                    int cacsize = cacUpdateList.size();
                    boolean uflag=false;//台账表的更改操作结果
                    boolean cacflag=false;//台账差异字段时间更新的更改操作结果
                    if(usize>0&&cacsize>0){
                        uflag=checkLedgerService.saveOrUpdateBatch(updateLedgerList);
                        if(!uflag){
                            return Result.build(1003,"导入失败,"+ResultMsg.MSG_1003);
                        }
                        for (St4ScsCac cac:cacUpdateList) {
                            int num = st4ScsCacMapper.updateByCk001(cac);
                            if(num<1){
                                return Result.build(1003,"导入失败,"+ResultMsg.MSG_1003);
                            }
                        }
                    }
                    St4ScsCn cla = null;
                    St4ScsCac cac = null;
                    int j =0;
                    List<St4ScsCn> claList = new ArrayList<>();//进度附件表的插入
                    LocalDateTime date = LocalDateTime.now();
                    if(repairText.size()>0&&updateLedgerList.size()>0){
                        for (St4ScsCk cl:updateLedgerList) {
                            cla= new St4ScsCn();
                            cla.setCn003(date);
                            cla.setCn006(seUser.getSa001());
                            cla.setCn010(repairText.get(j));
                            cla.setCk001(cl.getCk001());
                            claList.add(cla);
                            j++;
                        }
                        upCnNum = checkLedgerAttachService.saveOrUpdateBatch(claList);
                        if(!upCnNum){
                            return Result.build(1003,"导入失败,"+ResultMsg.MSG_1003);
                        }
                    }
                    Integer isize=importLedgerList.size();
                    if(isize>0){
                        //这里得首先批量插入核查点  再拿着核查点给每一个台账的对象
                        //批量插入
                        int k = 0;
                        boolean ckFlag=checkLedgerService.saveBatch(importLedgerList);
                        if(!ckFlag){
                            return Result.build(1003,"导入失败,"+ResultMsg.MSG_1003);
                        }
                        St4ScsCn cn = null;
                        int jj =0;
                        int mm =0;
                        List<St4ScsCn> cnList = new ArrayList<>();//进度附件表的插入
                        List<St4ScsCac> cacList = new ArrayList<>();//台账字段更新表的插入
                        LocalDateTime date1 = LocalDateTime.now();

                        if(importLedgerList.size()>0){
                            if(repairText.size()>0){

                                for (St4ScsCk cl:importLedgerList) {
                                    cn= new St4ScsCn();
                                    cn.setCn003(date1);
                                    cn.setCn006(seUser.getSa001());
                                    cn.setCn010(repairText.get(j));
                                    cn.setCk001(cl.getCk001());
                                    cnList.add(cn);
                                    jj++;
                                }
                                boolean cnFlag = checkLedgerAttachService.saveBatch(cnList);
                                if(!cnFlag){
                                    return Result.build(1003,"导入失败,"+ResultMsg.MSG_1003);
                                }
                            }
                            for (St4ScsCk cl:importLedgerList) {
                                cac= new St4ScsCac();
                                cac.setCk001(cl.getCk001());
                                cacList.add(cac);

                            }
                            boolean cacFlag= st4ScsCacService.saveBatch(cacList);
                            if(!cacFlag){
                                return Result.build(1003,"导入失败,"+ResultMsg.MSG_1003);
                            }

                        }
                        return Result.build(1000,"导入"+ResultMsg.MSG_1000);
                    }
                    return Result.build(1000,"导入"+ResultMsg.MSG_1000);
                }else {
                    return result;
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public Result sysPointData(Integer roleId,Integer uid) {
        Map<String,String> checkChangeTypeMap = new HashMap<>();
        QueryWrapper<St4ScsCm> checkChangeTypeWrapper = new QueryWrapper<>();
        List<St4ScsCm> CheckChangeTypeList = checkChangeTypeMapper.selectList(checkChangeTypeWrapper);
        for (St4ScsCm cfa:CheckChangeTypeList) {
            checkChangeTypeMap.put(cfa.getCm001().toString(),cfa.getCm002());
        }
        Result result = new Result();
        List<St4ScsCk> ckList = checkLedgerMapper.sysPointAndLedgerData(uid);
        if(roleId == null){
            result.setData(ckList);
            result.setStatus(1000);
            result.setMsg("同步问题点"+ResultMsg.MSG_1000);
            return result;
        }
        QueryWrapper<St4ScsCp> wrapper = new QueryWrapper<>();
        wrapper.eq("CP003",roleId);
        List<St4ScsCp> list = st4ScsCpMapper.selectList(wrapper);
        St4ScsCp cp = null;
        if(list!=null&&list.size()>0){
            cp=list.get(0);
        }
        String data = cp.getCp002();
        Map<String,Object> dataMap = new net.sf.json.JSONObject().fromObject(data);
        List dataList = (List) dataMap.get("data");
        Map<String, String> mapledger = null;
        Map<String, String> mapCn = null;
        JSONArray jarr = new JSONArray();
        JSONArray jarrLedger =null;

        try {
            JSONObject newDataJson =null;
            JSONObject json = new JSONObject();
            JSONObject newDataLedger =null;

            JSONObject newDataLedgerJson = new JSONObject();

            St4ScsCd cd = null;
            List<St4ScsCn> cnList = null;
            if(ckList!=null&&ckList.size()>0){
                for (St4ScsCk ck:ckList) {
                    cnList=ck.getSt4ScsCnList();
                    //这里之所以做这个操作是为了得到map因为这是2张表的数据，ck表中的数据在这里已经查询出来了但是要取哪些值是存在cp表中的
                    //这里就必须把ck表的数据整理成hashMap再拿cp表中的key去取value值
                    mapledger = BeanUtils.describe(ck);
                    List<String> phoList = new ArrayList<>();
                    if(cnList!=null){
                        for (St4ScsCn cn:cnList) {
                            if(ObjectUtils.isNotNullAndEmpty(cn.getCn004())){
                                phoList.add(cn.getCn004());
                            }
                        }
                    }
                    if(dataList!=null&&dataList.size()>0 ){
                        jarrLedger = new JSONArray();
                        newDataJson =new JSONObject();
                        for (Object key:dataList) {

                            newDataLedgerJson = new JSONObject();
                            newDataLedger= parseObject(key.toString());
                            newDataLedgerJson = newDataLedger;
                            Object key1 = newDataLedger.get("id").toString().toLowerCase();
                            Object value = mapledger.get(newDataLedger.get("id").toString().toLowerCase());
                            //这里可以根据type的来控制返回的value是字符串还是数组
                            if(key1.toString().contains("cn")&&cnList.size()>0){
                                if(ObjectUtils.isNotNullAndEmpty(cnList)){
                                    Object type = newDataLedger.get("type");
                                    if(Integer.valueOf(newDataLedger.get("type").toString())==9){
                                        newDataLedgerJson.put("value",phoList);
                                    }else if(Integer.valueOf(newDataLedger.get("type").toString())==1){
                                        //newDataLedgerJson.put("value",mapCn==null||mapCn.get(key1.toString())==null?"":mapCn.get(key1.toString()));
                                        newDataLedgerJson.put("value",BeanUtils.describe(cnList.get(0)).get(key1.toString()));
                                    }
                                }
                            }else if(Integer.valueOf(newDataLedger.get("type").toString())==5){
                                Object aaa =mapledger.get(key1);
                                List<String> cList = new ArrayList<>();
                                String valList = mapledger.get(key1.toString());
                                if(valList.contains(";")){
                                    String[] vals=valList.split(";");
                                    for (String val:vals) {
                                        cList.add(val);
                                    }
                                }else {
                                    cList.add(valList);
                                }
                                newDataLedgerJson.put("value",cList);
                            }/*else if(key1.toString().contains("ck005")){
                                Object aaa =mapledger.get(key1);
                                JSONArray ck005Array = new JSONArray();
                                JSONObject ck005Json = new JSONObject();
                                ck005Array.add(mapledger.get("ck005"));
                                newDataLedgerJson.put("value",ck005Array);
                            }*/
                            else if(Integer.valueOf(newDataLedger.get("type").toString())==10){
                                List<String> cList = new ArrayList<>();
                                String valList = mapledger.get(key1.toString());
                                if(valList.contains(";")){
                                    String[] vals=valList.split(";");
                                    for (String val:vals) {
                                        cList.add(val);
                                    }
                                }else {
                                    cList.add(valList);
                                }

                               newDataLedgerJson.put("value",cList);
                            }else{
                                newDataLedgerJson.put("value",value==null?"":value);
                            }

                            jarrLedger.add(newDataLedgerJson);
                        }
                        newDataJson.put("ledger",jarrLedger);
                        cd = ck.getSt4ScsCd();
                        cd.setTaskName(ck.getSt4ScsCl()==null?"":ck.getSt4ScsCl().getCl002());
                        cd.setReserveName(ck.getSt4SysSg()==null?"":ck.getSt4SysSg().getSg008());
                        cd.setAdminRegionName(ck.getSt4SysSd()==null?"":ck.getSt4SysSd().getSd008());
                        newDataJson.put("point",BeanUtils.describe(cd));
                        jarr.add(newDataJson);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            new RuntimeException("同步错误");
        }
        result.setData(JSON.toJSON(jarr));
        result.setStatus(1000);
        result.setMsg("同步问题点"+ResultMsg.MSG_1000);

        return result;
    }

    @Override
    public Result selectReserveNumTiew(St4SysSd data) {
        List<Map> res = checkLedgerMapper.selectReserveNumTiew(data);
        Result result = new Result();
        result.setData(res);
        result.setStatus(1000);
        result.setMsg("查询保护地、问题点、巡护次数、巡护里程"+ResultMsg.MSG_1000);

        return result;
    }
    @Override
    public St4ScsCk getSt4ScsCkByCc002(String cc002) {
        QueryWrapper<St4ScsCk> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("CC002",cc002);
        List<St4ScsCk> scsCk=checkLedgerMapper.selectList(queryWrapper);
        if (scsCk.size()>0){
            return scsCk.get(0);
        }
        return null;
    }

    @Override
    public Result pointStatistics() {
        Result result = new Result();
        List<Map> map1 = checkLedgerMapper.pointStatistics();
        QueryWrapper<St4ScsCd> wrapper = new QueryWrapper<>();
        wrapper.eq("CD009",1);
        //List<St4ScsCd>
        List<St4ScsCd> list2 = checkPointMapper.selectList(wrapper);
        Integer pNum = list2.size();
        Map<String,Object> map = new HashMap<>();
        map.put("pointNum",pNum);
        map.put("checkNum",map1);
        result.setStatus(1000);
        result.setData(map);

        return result;
    }

    @Override
    public Result pointStatisticsToday(St4ScsCd cd) {
        cd = new St4ScsCd();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime time = LocalDateTime.now();
        String localTime = df.format(time);
        Result result = new Result();
        QueryWrapper<St4ScsCd> wrapper = new QueryWrapper<>();
        wrapper.eq("CD009",1);
        wrapper.like("CD011",localTime);
        List<St4ScsCd> list =  checkPointMapper.selectList(wrapper);
        Integer num = 0;
        if(list!=null&&list.size()>0){
            num = list.size();
        }
        Map<String,Integer> map = new HashMap<>();
        map.put("pointNum",list.size());
        List<Map<String,Integer>> listLast = new ArrayList<>();
        listLast.add(map);
        result.setData(listLast);
        result.setMsg("加载成功");
        result.setStatus(1000);
        return result;
    }

    @Override
    public Result pointStatisticsRepairCon() {
        Result result = new Result();
        List<Map> map = checkLedgerMapper.pointStatisticsRepairCon();
        result.setData(map);
        result.setMsg("加载成功");
        result.setStatus(1000);
        return result;
    }
    @Override
    public Result pointStatisRecentAdd(){
        Result result = new Result();
        QueryWrapper<St4ScsCd> wrapper = new QueryWrapper<>();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime time = LocalDateTime.now();

        String localTime = df.format(time);

        wrapper.like("CD011",localTime);
        wrapper.eq("CD007",0);
        wrapper.eq("CD009",1);

        List<St4ScsCd> list = checkPointMapper.selectList(wrapper);
        result.setMsg("加载成功");
        result.setStatus(1000);
        result.setData(list.size());
        return  result;
    }
    @Override
    public Result pointStageExamine(St4ScsCk ck){

        if(checkLedgerMapper.updateById(ck)>0){
            boolean flag= false;
            boolean flagPush= false;
            if(ck.getCk067()==2){
                List<Map> list = checkLedgerMapper.getTaskMemberUnpass(ck);
                if(list!=null&&list.size()>0){
                    for (Map map:list) {
                        flagPush=JPushUtil.jiGuangPush(map.get("name")==null?"":map.get("name").toString(),"您负责的"+map.get("value")+"任务下的核查信息审核未通过，请重新提交","4");
                        if(!flagPush){
                            break;
                        }
                    }

                }

            }
            if(flag){

                return Result.build(1000,"审核成功");
            }else {
                return Result.build(1006,"审核"+ResultMsg.MSG_1006);
            }
        }

        return Result.build(1006,"审核"+ResultMsg.MSG_1006);
    }

    @Override
    public Result insertLedgerLd(St4ScsCd param,String userId) {
        boolean hasTask=true;
        if(param.getCl001()==null){
            St4ScsCz cz = st4ScsCzMapper.getRecentTask(Integer.valueOf(userId));
            if(cz!=null){
                param.setCl001(cz.getCl001());
            }else {
                //代表是新增的且没有任务这种情况不用推送
                hasTask=false;
            }

        }

        LocalDateTime date = LocalDateTime.now();
        param.setCd011(date);
        //如果是移动端提交，就插入问题点
        boolean isInsert = false;
        //这里是针对于提交的是先前已经存在的点的情况
        Integer cdId=0;
        boolean cdFlag = false;//这个控制是小组内是不是第一个人提交 true代表是
        St4ScsCd cdCode = new St4ScsCd();
        cdCode.setCd004(param.getCd004());
        //如果是移动端提交，则先去查看该编号是否存在数据库
        List<St4ScsCd> resData = checkPointMapper.getDataByCd004(cdCode);
        if(resData.size() > 0){
            QueryWrapper<St4PoCdSa> wrapper1 = new QueryWrapper<>();
            //这里得确定导入的时候是不是编号是否真的是不能重复 必须保证这点
            wrapper1.eq("CD001",resData.get(0).getCd001());
            List<St4PoCdSa> list1 = st4PoCdSaMapper.selectList(wrapper1);
            if(list1!=null&&list1.size()<1){
                return  Result.build(1008,"问题点编号"+ResultMsg.MSG_1008);
            }
            cdFlag=true;
        }
        param.setCd010(Integer.valueOf(userId));
        param.setCd009(1);
        param.setCd013(PointHelp.toDfm(param.getCd002()));//经纬度转换
        param.setCd014(PointHelp.toDfm(param.getCd003()));
        if(!cdFlag){
            isInsert =  checkPointService.save(param);
            cdId = param.getCd001();
            if(isInsert){
                List<St4PoSaCz> saCzList = new ArrayList<>();
                St4PoCdSa cdsa = null;
                if(param.getCl001()!=null&&param.getCl001()!=0){

                    St4ScsCz cz1 = st4ScsCzMapper.getUserShare(Integer.valueOf(userId),param.getCl001());
                    if(cz1!=null){
                        QueryWrapper<St4PoSaCz> saczWrapper = new QueryWrapper<>();
                        saczWrapper.eq("CZ001",cz1.getCz001());
                        saCzList = st4PoSaCzMapper.selectList(saczWrapper);
                        List<Integer> saList= saCzList.stream().map(St4PoSaCz::getSa001).collect(Collectors.toList());
                        List<St4PoCdSa> cdsaList = new ArrayList<>();

                        for (Integer said:saList) {
                            cdsa= new St4PoCdSa();
                            cdsa.setSa001(said);
                            if(said==Integer.valueOf(userId)){
                                cdsa.setCdsa001(1);
                            }else {
                                cdsa.setCdsa001(0);
                            }

                            cdsa.setCd001(param.getCd001());
                            cdsaList.add(cdsa);
                        }
                        if(!st4PoCdSaService.saveBatch(cdsaList)){
                            //代表插入新的点失败
                            return Result.build(1003,ResultMsg.MSG_1003);
                        };
                    }

                }else{
                    //新增的点，而且这个人之前没有最新任务的情况也就是没有cl001
                    cdsa = new St4PoCdSa();
                    cdsa.setSa001(Integer.valueOf(userId));
                    cdsa.setCd001(cdId);
                    cdsa.setCdsa001(1);
                    if(!st4PoCdSaService.save(cdsa)){
                        return Result.build(1003,ResultMsg.MSG_1003);
                    };
                }
            }
        }else{
            cdId=resData.get(0).getCd001();
            St4PoCdSa cdsa = new St4PoCdSa();
            cdsa.setCdsa001(1);
            cdsa.setCd001(cdId);
            cdsa.setSa001(Integer.valueOf(userId));
            if(st4PoCdSaMapper.submitPoint(cdsa)<1){
                return Result.build(1003,ResultMsg.MSG_1003);
            };

        }
        //这里哪怕是一个点也得状态值为1 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        param.setCd009(1);//删除状态


        //构造查询参数，根据人员id与问题点id查询该 点位所在任务的小组的组员
        Map map = new HashMap();
        map.put("sa001",param.getSt4ScsCk().getCk049());//核查人id
        map.put("cd001",cdId);//问题点id

        List<St4SysSa> userList = userMapper.getUserByidAndCd001(map);


        //根据点位编号查询所提交的台账
        QueryWrapper<St4ScsCk> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CD004",param.getCd004());//问题点编号
        queryWrapper.eq("CK088",1);//是移动端提交
        queryWrapper.eq("CK085",1);//删除状态为未删除
        queryWrapper.groupBy("CK049");//根据用户id分组
        List<St4ScsCk> ckList = checkLedgerService.list(queryWrapper);//这里按照核查人id分组的话就能知道有多少个人提交了（每个人只能提交一次）

        //如果台账的记录大于等于小组人员的数量，说明该小组内成员都提交了台账，则不允许移动端再提交
        if(ckList.size() != 0 && userList.size() != 0 && ckList.size() >= userList.size()){
            return Result.build(1008,ResultMsg.MSG_1008+"(,该分组下所有成员都已提交台账，请勿重复提交)");
        }
        //将问题点编号赋值到台账数据里面
        param.getSt4ScsCk().setCd004(param.getCd004());
        param.getSt4ScsCk().setCk086(date);
        if(param.getCl001()!=null){
            param.getSt4ScsCk().setCk091(param.getCl001());
        }
        //app提交的如果是不详，则传递是空字符串，存数据库的时候存0001-00-00，标识为不详
        /*if(param.getSt4ScsCk().getCk020() == ""){
            param.getSt4ScsCk().setCk020("0001-00-00");
        }*/
        //移动端提交的台账
        if(cdFlag){
            param.getSt4ScsCk().setCk088(1);
        }else{
            param.getSt4ScsCk().setCk088(0);
        }

        //插入台账信息
        param.getSt4ScsCk().setCk048(date);//核查时间
        //台账信息在cd包裹的ck里面
        St4ScsCk ck = param.getSt4ScsCk();
        if(!ObjectUtils.isNotNullAndEmpty(ck.getCk049())){
            return Result.build(1003,"核查人ID不能为空");
        }
        isInsert = checkLedgerService.save(param.getSt4ScsCk());
        if(isInsert){
            //插入整改进展信息,将台账主键添加到进度表
            List<St4ScsCn> st4ScsCnList = param.getSt4ScsCk().getSt4ScsCnList();
            if(st4ScsCnList != null){
                for(int i = 0 ; i < st4ScsCnList.size() ; i ++){
                    //台账主键
                    st4ScsCnList.get(i).setCk001(param.getSt4ScsCk().getCk001());
                    //台账整改进展
                    //st4ScsCnList.get(i).setCn010(param.getSt4ScsCk().getCk090());
                    st4ScsCnList.get(i).setCn003(date);
                    isInsert = checkLedgerAttachService.save(st4ScsCnList.get(i));
                }
            }
            if(isInsert){
                //这里不论是旧的点的新的核查信息还是新提交的点的信息都应该把点人关联表里面的提交状态值
                St4PoCdSa cdsa = new St4PoCdSa();
                cdsa.setCd001(param.getCd001());
                cdsa.setSa001(Integer.valueOf(userId));
                if(hasTask){
                    if(st4PoCdSaMapper.submitPoint(cdsa)<1){
                        return Result.build(1003,ResultMsg.MSG_1003);
                    };
                    /**
                     * 这里比如张三李四王五是在A任务下，当张三提交了核查信息之后，是必须要推送给李四和王五的
                     */
                    St4SysSa saUser = new St4SysSa();
                    saUser.setSa001(Integer.valueOf(ck.getCk049()));
                    saUser=st4SysSaMapper.selectById(saUser);
                    QueryWrapper<St4PoCdSa> wrapperPocd = new QueryWrapper<>();
                    wrapperPocd.eq("CD001",param.getCd001());
                    List<St4PoCdSa> saList = st4PoCdSaMapper.selectList(wrapperPocd);
                    if(saList!=null&&saList.size()>0){
                        List<Integer> saids = saList.stream().map(St4PoCdSa::getSa001).collect(Collectors.toList());
                        QueryWrapper<St4SysSa> userwrapper = new QueryWrapper<>();
                        userwrapper.in("SA001",saids);
                        List<St4SysSa> usersList = st4SysSaMapper.selectList(userwrapper);
                        St4SysSa saa = new St4SysSa();
                        saa.setSa001(Integer.valueOf(userId));
                        saa=st4SysSaMapper.selectById(saa);
                        if(usersList!=null&&usersList.size()>0&&hasTask){
                            try{
                                System.out.println("上传成功");
                                return  Result.build(1000,"上传"+ResultMsg.MSG_1000);
                            }
                            catch (Exception e){
                                System.out.println("上传成功");
                                return  Result.build(1000,"上传"+ResultMsg.MSG_1000);
                            }finally {

                                for (St4SysSa sa:usersList) {
                                    if(!sa.getSa001().toString().equals(userId)){

                                        JPushUtil.jiGuangPush(sa.getSa012(), saa.getSa019()+"提交了新的台账信息，请确认","1");
                                    }
                                }
                            }

                        }
                    }
                }else {
                    System.out.println("上传成功");
                    return  Result.build(1000,"上传"+ResultMsg.MSG_1000);
                }
            }else{
                return  Result.build(1003,ResultMsg.MSG_1003+",上传失败");
            }

        };



        return  Result.build(1003,ResultMsg.MSG_1003+",上传失败");
    }

    @Override
    public void doExcel(St4ScsCk ck,HttpServletResponse response) {
        //台账的导入逻辑
        List<St4ScsCk> dataList = checkLedgerMapper.getExportData(ck);
        if(dataList!=null&&dataList.size()>0){
            ck = dataList.get(0);
        }
        //创建poi导出数据对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        ExcelStyleTools tools = new ExcelStyleTools();
        //创建sheet页
        HSSFSheet sheet = workbook.createSheet("监管台账Excel表");

        sheet.setColumnWidth(0, (int) ((16+0.72)*256));
        sheet.setColumnWidth(1, (int) ((16+0.72)*256));
        sheet.setColumnWidth(2, (int) ((16+0.72)*256));
        sheet.setColumnWidth(3, (int) ((16+0.72)*256));
        sheet.setColumnWidth(4, (int) ((16+0.72)*256));
        sheet.setColumnWidth(5, (int) ((16+0.72)*256));
        sheet.setColumnWidth(6, (int) ((16+0.72)*256));
        sheet.setColumnWidth(7, (int) ((16+0.72)*256));
        sheet.setColumnWidth(8, (int) ((16+0.72)*256));
        sheet.setColumnWidth(9, (int) ((16+0.72)*256));
        sheet.setColumnWidth(10, (int) ((16+0.72)*256));
        sheet.setColumnWidth(11, (int) ((16+0.72)*256));
        sheet.setColumnWidth(12, (int) ((16+0.72)*256));
        sheet.setColumnWidth(13, (int) ((16+0.72)*256));
        sheet.setColumnWidth(14, (int) ((16+0.72)*256));
        sheet.setColumnWidth(15, (int) ((16+0.72)*256));
        sheet.setColumnWidth(16, (int) ((16+0.72)*256));
        sheet.setColumnWidth(17, (int) ((16+0.72)*256));
        sheet.setColumnWidth(18, (int) ((16+0.72)*256));
        sheet.setColumnWidth(19, (int) ((16+0.72)*256));
        sheet.setColumnWidth(20, (int) ((16+0.72)*256));
        sheet.setColumnWidth(21, (int) ((16+0.72)*256));
        sheet.setColumnWidth(22, (int) ((16+0.72)*256));
        sheet.setColumnWidth(23, (int) ((16+0.72)*256));
        sheet.setColumnWidth(24, (int) ((16+0.72)*256));
        sheet.setColumnWidth(25, (int) ((16+0.72)*256));
        sheet.setColumnWidth(26, (int) ((16+0.72)*256));
        sheet.setColumnWidth(27, (int) ((16+0.72)*256));


        //CellRangeAddress region1 = new CellRangeAddress(0, 0, (short) 0, (short) 27);

        HSSFFont font = tools.getFont(workbook, "宋体", (short)12, HSSFFont.BOLDWEIGHT_BOLD);
        //继续设置副标题的对齐方式是左对齐
        HSSFCellStyle style = tools.getSubTitleStyle(workbook, font, HSSFCellStyle.ALIGN_LEFT);
        //参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列
        //sheet.addMergedRegion(region1);

        //CellRangeAddress a
        //创建表头
        HSSFRow headRow = sheet.createRow(0);
        //设置表头信息
        HSSFCell cell=headRow.createCell(0);
        cell.setCellStyle(style);
        cell.setCellValue("问题点位编号");
        cell=headRow.createCell(1);
        cell.setCellValue("所属保护地");
        cell.setCellStyle(style);
        cell=headRow.createCell(2);
        cell.setCellValue("所属省份");
        cell.setCellStyle(style);
        cell=headRow.createCell(3);
        cell.setCellValue("经度");
        cell.setCellStyle(style);
        cell=headRow.createCell(4);
        cell.setCellValue("纬度");
        cell.setCellStyle(style);
        cell=headRow.createCell(5);
        cell.setCellValue("年度");
        cell.setCellStyle(style);
        cell=headRow.createCell(6);
        cell.setCellValue("级别");
        cell.setCellStyle(style);
        cell=headRow.createCell(7);
        cell.setCellValue("名称");
        cell.setCellStyle(style);
        cell=headRow.createCell(8);
        cell.setCellValue("所在功能区");
        cell.setCellStyle(style);
        cell=headRow.createCell(9);
        cell.setCellValue("问题类型");
        cell.setCellStyle(style);
        cell=headRow.createCell(10);
        cell.setCellValue("占地面积");
        cell.setCellStyle(style);
        cell=headRow.createCell(11);
        cell.setCellValue("建设单位");
        cell.setCellStyle(style);
        cell=headRow.createCell(12);
        cell.setCellValue("整改时限");
        cell.setCellStyle(style);
        cell=headRow.createCell(13);
        cell.setCellValue("建设日期");
        cell.setCellStyle(style);
        cell=headRow.createCell(14);
        cell.setCellValue("问题描述");
        cell.setCellStyle(style);
        cell=headRow.createCell(15);
        cell.setCellValue("整改措施");
        cell.setCellStyle(style);
        cell=headRow.createCell(16);
        cell.setCellValue("整改进展");
        cell.setCellStyle(style);
        cell=headRow.createCell(17);
        cell.setCellValue("是否违法违规");
        cell.setCellStyle(style);
        cell=headRow.createCell(18);
        cell.setCellValue("是否处罚");
        cell.setCellStyle(style);
        cell=headRow.createCell(19);
        cell.setCellValue("处罚形式");
        cell.setCellStyle(style);
        cell=headRow.createCell(20);
        cell.setCellValue("罚款");
        cell.setCellStyle(style);
        cell=headRow.createCell(21);
        cell.setCellValue("拆除面积");
        cell.setCellStyle(style);
        cell=headRow.createCell(22);
        cell.setCellValue("是否销号");
        cell.setCellStyle(style);
        cell=headRow.createCell(23);
        cell.setCellValue("问题分类");
        cell.setCellStyle(style);
        cell=headRow.createCell(24);
        cell.setCellValue("备注");
        cell.setCellStyle(style);
        cell=headRow.createCell(25);
        cell.setCellValue("台账来源");
        cell.setCellStyle(style);
        cell=headRow.createCell(26);
        cell.setCellValue("是否为巡查点");
        cell.setCellStyle(style);
        cell=headRow.createCell(27);
        cell.setCellValue("核查人");
        cell.setCellStyle(style);

        int i=1;
        for (St4ScsCk ckk:dataList) {

            String illegal = "";
            if(ck.getCk014()==1){
                illegal="是";
            }
            if(ck.getCk014()==0){
                illegal="否";
            }
            String ispush = "";
            if(ck.getCk039()==1){
                ispush="是";
            }
            if(ck.getCk039()==0){
                ispush="否";
            }

            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            HSSFRow dataRow = sheet.createRow(i );

            dataRow.createCell(0).setCellValue(ck.getCd004());
            dataRow.createCell(1).setCellValue(ck.getSt4SysSg().getSg008());
            dataRow.createCell(2).setCellValue(ck.getSt4SysSd().getSd008());
            dataRow.createCell(3).setCellValue(ck.getSt4ScsCd().getCd002());
            dataRow.createCell(4).setCellValue(ck.getSt4ScsCd().getCd003());
            dataRow.createCell(5).setCellValue(ck.getSt4ScsCl()==null?"":ck.getSt4ScsCl().getCl002());
            dataRow.createCell(6).setCellValue(ck.getSt4ScsCb().getCb002());
            dataRow.createCell(7).setCellValue("名称后定");
            dataRow.createCell(8).setCellValue(ck.getCk006());
            dataRow.createCell(9).setCellValue(ck.getCk016());
            dataRow.createCell(10).setCellValue(ck.getCk011());
            dataRow.createCell(11).setCellValue(ck.getCk019());
            dataRow.createCell(12).setCellValue(ck.getCk042()==null?"":df.format(ck.getCk042()));
            dataRow.createCell(13).setCellValue(ck.getCk020());
            dataRow.createCell(14).setCellValue(ck.getCk015());
            dataRow.createCell(15).setCellValue(ck.getCk043());
            dataRow.createCell(16).setCellValue(ck.getSt4ScsCn()==null?"":ck.getSt4ScsCn().getCn010());
            dataRow.createCell(17).setCellValue(illegal);
            dataRow.createCell(18).setCellValue(ispush);
            dataRow.createCell(19).setCellValue(ck.getCk040());
            dataRow.createCell(20).setCellValue(ck.getCk041());
            dataRow.createCell(21).setCellValue(ck.getCk044());
            dataRow.createCell(22).setCellValue(ck.getCk046());
            dataRow.createCell(23).setCellValue(ck.getCk016());
            dataRow.createCell(24).setCellValue(ck.getCk076());
            dataRow.createCell(25).setCellValue(ck.getCk078());
            dataRow.createCell(26).setCellValue("后定");
            dataRow.createCell(27).setCellValue(ck.getCk049());
            i++;
        }
        // 下载导出

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime date = LocalDateTime.now();
        String filename = df.format(date)+"台账信息信息表";

        // 设置头信息
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        //一定要设置成xlsx格式
        //创建一个输出流
        ServletOutputStream outputStream =null;
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename + ".xlsx", "UTF-8"));
             outputStream = response.getOutputStream();
            //写入数据
            workbook.write(outputStream);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 关闭流
            if(outputStream!=null){
                try {
                    outputStream.close();
                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        }

    }


}
