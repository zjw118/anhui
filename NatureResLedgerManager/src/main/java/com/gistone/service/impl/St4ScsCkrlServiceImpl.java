package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.VO.ResultVO;
import com.gistone.entity.St4ScsCkrl;
import com.gistone.entity.St4SysSa;
import com.gistone.entity.excel.ReportVo;
import com.gistone.entity.excel.St4ScsCkrlVO;
import com.gistone.mapper.St4ScsCkrlMapper;
import com.gistone.service.ISt4ScsCkrlService;
import com.gistone.service.ISt4ScsCsService;
import com.gistone.util.*;
import io.netty.handler.codec.http2.Http2Settings;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjw
 * @since 2019-11-07
 */
@Service
public class St4ScsCkrlServiceImpl extends ServiceImpl<St4ScsCkrlMapper, St4ScsCkrl> implements ISt4ScsCkrlService {
    @Autowired
    private St4ScsCkrlMapper st4ScsCkrlMapper;
    @Autowired
    private ISt4ScsCkrlService st4ScsCkrlService;
    @Autowired
    private  ConfigUtils configUtils;

    @Override
    public Result listHumanStage(St4ScsCkrl ckrl) {
        long pageNumber=ckrl.getPageNumber();
        long pageSize=ckrl.getPageSize();
        QueryWrapper<St4ScsCkrl> st4ScsCkrlQueryWrapper = new QueryWrapper<>();
        st4ScsCkrlQueryWrapper.eq("ck085",1);
        Page<St4ScsCkrl> page = new Page<St4ScsCkrl>(pageNumber,pageSize);
        IPage<St4ScsCkrl> list = st4ScsCkrlMapper.selectPage(page,st4ScsCkrlQueryWrapper);
        Result result = new Result();
        result.setStatus(1000);
        result.setMsg("加载成功");
        result.setPage((int)list.getPages());
        result.setTotal((int)list.getTotal());
        result.setRows(list.getRecords());
        return result;
    }
    @Override
    public Result importHumanStage(Map<String, MultipartFile> items, St4SysSa seUser) {

        Result result = new Result();
        try{
            Iterator<MultipartFile> itr = items.values().iterator();
            while (itr.hasNext()) {
                Map<String,String> map=new HashMap<String, String>();
                MultipartFile item = itr.next ();
                InputStream in=item.getInputStream();
                boolean flag=true;//这个开关控制能不能执行插入操作
                List<Map> list = ExcUtil.readExcelContent(in);
                //这里要验证点位序号不能重复

//                for (Map mapRe:list) {
//                    //首先判断自身是否重复
//                    if(pointNumList.size()>0&&pointNumList.contains(mapRe.get("点位序号").toString())){
//                        //这里的逻辑改变针对北京环科院是更新原有的
//
//                        flag=false;
//                        result=Result.build(1008,ResultMsg.MSG_1008);
//                        break;
//                    }
//                    pointNumList.add(mapRe.get("点位序号").toString());
//                }
               St4ScsCkrl  checkLedger=null;
                List<St4ScsCkrl> ckrllist = new ArrayList<>();
                Integer i=2;
                for(Map data:list){
                    checkLedger = new St4ScsCkrl();
                    checkLedger.setCk085(1);
//                    if(!ObjectUtils.isNotNullAndEmpty(data.get("行政区名称"))){
//                        flag = false;
//                        result =  Result.build(1001,"第"+i+"行行政区名称"+ResultMsg.MSG_1001);
//                        break;
//                    };


                    if(ObjectUtils.isNotNullAndEmpty(data.get("点位序号"))){
                        checkLedger.setCd004(data.get("点位序号").toString());
                    }


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
                        checkLedger.setCk014(data.get("是否违法违规").toString().trim());
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

                    if(ObjectUtils.isNotNullAndEmpty(data.get("建设时间"))){
                            checkLedger.setCk020(data.get("建设时间").toString().trim());
                    }
                    if(ObjectUtils.isNotNullAndEmpty(data.get("是否处罚"))){
                        checkLedger.setCk039(data.get("是否处罚").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("处罚形式"))){
                        checkLedger.setCk040(data.get("处罚形式").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("罚款(万元)"))){
                        checkLedger.setCk041(data.get("罚款(万元)").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("整改时限"))){
                        checkLedger.setCk042(data.get("整改时限").toString());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("整改措施"))){
                        checkLedger.setCk043(data.get("整改措施").toString().trim());
                    };


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
                        checkLedger.setCk048(data.get("核查时间").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("核查人"))){
                        checkLedger.setCk049(data.get("核查人").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("联系方式"))){
                        checkLedger.setCk050(data.get("联系方式").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("参与核查人数"))){
                        checkLedger.setCk051(data.get("参与核查人数").toString().trim());
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
                        checkLedger.setCk080(data.get("是否四类聚焦").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("是否巡查台账"))){
                        checkLedger.setCk081(data.get("是否巡查台账").toString().trim());
                    };

                    if(ObjectUtils.isNotNullAndEmpty(data.get("是否市级巡查"))){
                        checkLedger.setCk082(data.get("是否市级巡查").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("是否国家点"))){
                        checkLedger.setCk083(data.get("是否国家点").toString().trim());
                    };
                    if(ObjectUtils.isNotNullAndEmpty(data.get("是否重点台账"))){
                        checkLedger.setCk084(data.get("是否重点台账").toString().trim());
                    };

                    LocalDateTime date = LocalDateTime.now();
                    checkLedger.setCk086(date);
                    checkLedger.setCk087(seUser.getSa001());
                    ckrllist.add(checkLedger);
                    i++;
                }
                if(flag){
                    if(ckrllist!=null&&ckrllist.size()>0){
                        if(st4ScsCkrlService.saveBatch(ckrllist))
                            return Result.build(1000,"导入"+ResultMsg.MSG_1000);
                        return Result.build(1003,"服务器处理结果失败,请稍后再试");
                    }
                    return Result.build(1003,"服务器未读取到数据，请确认所上传excel是否有信息");
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
    public ResultVO importExcel(String fileName) throws Exception {
//
//        //这里得对台账进行map的处理因为任务导入的时候是有可能填的台账是系统里存在了的
//
//        InputStream is = new FileInputStream(new File(fileName));
//        Workbook hssfWorkbook = null;
//        if (fileName.endsWith("xlsx")){
//            hssfWorkbook = new XSSFWorkbook(is);//Excel 2007
//        }else if (fileName.endsWith("xls")){
//            hssfWorkbook = new HSSFWorkbook(is);//Excel 2003
//
//        }
//        // HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
//        // XSSFWorkbook hssfWorkbook = new XSSFWorkbook(is);
//        St4ScsCkrl ckrl = null;
//        // 循环工作表Sheet
//        for (int numSheet = 0; numSheet <hssfWorkbook.getNumberOfSheets(); numSheet++) {
//            //HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
//            Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
//            if (hssfSheet == null) {
//                continue;
//            }
//            // 循环行Row
//            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
//                //HSSFRow hssfRow = hssfSheet.getRow(rowNum);
//                Row hssfRow = hssfSheet.getRow(rowNum);
//                if (hssfRow != null) {
//                    ckrl = new St4ScsCkrl();
//                    ckrl.setCl012(1);
//                    ckrl.setCl003("1");//todo 这里写死成1原因是目前没有没有对任务进行核查字段不同情况的处理
//                    ckrl.setCl015(0);//因为是导入所以默认是系统创建
//                    //HSSFCell name = hssfRow.getCell(0);
//                    //HSSFCell pwd = hssfRow.getCell(1);
//                    Cell taskName = hssfRow.getCell(0);  //任务批次名称
//                    Cell taskDescri = hssfRow.getCell(1);//任务描述
//                    Cell taskYear = hssfRow.getCell(2);//任务年份
//                    Cell taskLedger = hssfRow.getCell(3);//任务台账
//
//                    Cell taskName = hssfRow.getCell(4);  //任务批次名称
//                    Cell taskDescri = hssfRow.getCell(5);//任务描述
//                    Cell taskYear = hssfRow.getCell(6);//任务年份
//                    Cell taskLedger = hssfRow.getCell(7);//任务台账
//
//                    Cell taskName = hssfRow.getCell(8);  //任务批次名称
//                    Cell taskDescri = hssfRow.getCell(9);//任务描述
//                    Cell taskYear = hssfRow.getCell(10);//任务年份
//                    Cell taskLedger = hssfRow.getCell(11);//任务台账
//
//                    Cell taskName = hssfRow.getCell(12);  //任务批次名称
//                    Cell taskName = hssfRow.getCell(13);  //任务批次名称
//
//                    //这里是自己的逻辑
//                    ckrl.setCl002(taskName==null?"":taskName.toString());
//                    ckrl.setCl009(taskDescri==null?"":taskDescri.toString());
//                    ckrl.setCl010(taskYear==null?"":taskYear.toString());
//                    if(ObjectUtils.isNotNullAndEmpty(taskLedger)){
//                        if(ObjectUtils.isNotNullAndEmpty(map.get(taskLedger.toString().trim()))){
//                            cl.setLedgerId(map.get(taskLedger.toString()).toString());
//                        }
//                    }
//                    list.add(cl);
//                }
//            }
//        }

        return ResultVOUtil.error("1444","服务器未读取到数据，请确认所上传excel是否有信息");
    }

    @Override
    public ResultVO exportHumanStage(St4ScsCkrl ckrl, HttpServletResponse response) {
        List<St4ScsCkrlVO> voList = st4ScsCkrlMapper.exportHumanStage(ckrl);
        String filepath = ExcelUtil.toXls("人类台账资料管理统计表", voList,
                configUtils.getExcel_PATH(), ReportVo.class, response);
        Map map1 = new HashMap();
        map1.put("filepath", filepath.substring(2));
        return ResultVOUtil.success(map1);
    }
}
