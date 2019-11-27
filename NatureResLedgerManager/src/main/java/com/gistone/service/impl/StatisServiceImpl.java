package com.gistone.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.VO.ResultVO;
import com.gistone.entity.*;
import com.gistone.entity.EXCEL.LmPointVO;
import com.gistone.entity.excel.ActivityVo;
import com.gistone.entity.excel.ExamineVo;
import com.gistone.mapper.St4ScsCcMapper;
import com.gistone.mapper.St4ScsCdMapper;
import com.gistone.mapper.St4ScsCkMapper;
import com.gistone.mapper.St4ScsCyMapper;
import com.gistone.service.ISt4ScsCkService;
import com.gistone.service.StatisService;
import com.gistone.swagger.StaticSwagger;
import com.gistone.util.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 巡护记录表 服务实现类
 * </p>
 *
 * @author wzy
 * @since 2019-08-14
 */
@Service
public class StatisServiceImpl extends ServiceImpl<St4ScsCyMapper, St4ScsCy> implements StatisService {

    @Autowired
    private St4ScsCyMapper st4ScsCyMapper;
    @Autowired
    private St4ScsCdMapper st4ScsCdMapper;
    @Autowired
    private St4ScsCkMapper st4ScsCkMapper;
    @Autowired
    private St4ScsCcMapper st4ScsCcMapper;
    @Autowired
    private ConfigUtils configUtils;

    @Override
    public  ResultVO examineQualityExport(St4ScsCl cl, HttpServletResponse response){
        List<Map> data = st4ScsCkMapper.examineQualityEasyPoi();
        List<ExamineVo> voList = new ArrayList<>();
        ExamineVo ev = null;
        for (Map map:data) {
            ev = new ExamineVo();
            ev.setUname(map.get("uname").toString());
            ev.setUnEmaminedNum(map.get("unEmaminedNum").toString());
            ev.setEmaminedNum(map.get("emaminedNum").toString());
            ev.setBackNum(map.get("backNum").toString());
            ev.setTzNum(map.get("tzNum").toString());
            ev.setPassRate(map.get("passRate").toString());
            voList.add(ev);
        }
        String filepath = ExcelUtil.toXls("问题斑块审核统计表", voList, configUtils.getExcel_PATH(), ExamineVo.class, response);
        Map map1 = new HashMap();
        map1.put("filepath", filepath.substring(2));
        return ResultVOUtil.success(map1);
    }

    @Override
    public ResultVO pointQualityExport(RlhdGroup cl, HttpServletResponse response) {
        List<Map> mapOrign = st4ScsCdMapper.pointQualityOrginExport(cl);
        List<Map> mapNow = st4ScsCdMapper.pointQualityNowExport(cl);
        List<ActivityVo> voList = new ArrayList<>();
        ActivityVo vo = null;
        if(mapOrign!=null&&mapOrign.size()>0){
            for (Map map:mapOrign) {
                vo = new ActivityVo();
                String orgin = map.get("orign")==null?"":map.get("orign").toString();
                vo.setOrign(orgin);
                vo.setOrignCount(map.get("orignCount").toString());
                vo.setNows("");
                vo.setNowsCount("0");
                if(mapNow!=null&&mapNow.size()>0){
                    for (Map map1:mapNow) {
                        String now = map1.get("nows")==null?"":map1.get("nows").toString();
                        if(ObjectUtils.isNotNullAndEmpty(orgin)&&ObjectUtils.isNotNullAndEmpty(now)){
                            if(orgin.equals(now)){
                                vo.setNows(now);
                                vo.setNowsCount(map1.get("nowsCount").toString());
                            }
                        }else {
                            vo.setNows("");
                            vo.setNowsCount("0");
                        }
                    }

                }else {
                    vo.setNows("");
                    vo.setNowsCount("0");
                }
                voList.add(vo);
            }

        }
        String filepath = ExcelUtil.toXls("人类活动巡查结果质量评估统计表", voList, configUtils.getExcel_PATH(), ActivityVo.class, response);
        Map map1 = new HashMap();
        map1.put("filepath", filepath.substring(2));
        return ResultVOUtil.success(map1);
    }

    @Override
    public ResultVO exportRecordStatic(St4ScsCy cy){

        List<St4ScsCy> list = st4ScsCyMapper.selectPoList(cy);
        JSONObject json = new JSONObject();
        String path = ExportExcel(list);
        if(ObjectUtils.isNotNullAndEmpty(path)){
            json.put("excelPath", path);
            return ResultVOUtil.success(json);
        }else{
            return ResultVOUtil.error("1222","处理结果失败");
        }

    }
    public String ExportExcel(List<St4ScsCy> clsData){
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        String fileName = df.format(time)+"人员航迹汇总信息.xls";
        String dir = "D:\\recordStatic\\";
        String fileFinalPath=dir+fileName;
        File file = new File(fileFinalPath);
        if(!file.exists()){
            File parent = file.getParentFile();
            if(!parent.exists()) {
                parent.mkdirs();
            }
        }
        // 新建文件
        try {
            file.createNewFile();
            FileOutputStream os = new FileOutputStream(file);
            // 创建工作薄
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 创建新的一页
            HSSFSheet sheet = workbook.createSheet("人员航迹汇总表");
            /**
             * zjw自己写的代码开始
             *
             */
            // 这里是要导出7列
            sheet.setColumnWidth(0, (int) ((20.5 + 0.72) * 256));
            sheet.setColumnWidth(1, (int) ((20.5 + 0.72) * 256));
            sheet.setColumnWidth(2, (int) ((25.5 + 0.72) * 256));
            sheet.setColumnWidth(3, (int) ((20.5 + 0.72) * 256));
            sheet.setColumnWidth(4, (int) ((20.5 + 0.72) * 256));
            sheet.setColumnWidth(5, (int) ((20.5 + 0.72) * 256));
            sheet.setColumnWidth(6, (int) ((20.5 + 0.72) * 256));

            // 设置单元格宽度
            sheet.setDefaultColumnWidth(30);

            // 设置标题样式
//            HSSFFontExcelStyleTools  headfont = workbook.createFont();
//            headfont.setFontName("宋体");
//            headfont.setFontHeightInPoints((short) 12);// 字体大小
            //headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗

            HSSFCellStyle headstyle = workbook.createCellStyle();
            //headstyle.setFont(headfont);
            //headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
            //headstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
            headstyle.setLocked(true);
            headstyle.setWrapText(true);// 自动换行

            // 设置数据内容样式
            HSSFFont headfont1 = workbook.createFont();
            headfont1.setFontName("宋体");
            headfont1.setFontHeightInPoints((short) 12);// 字体大小

            HSSFCellStyle headstyle1 = workbook.createCellStyle();
            headstyle1.setFont(headfont1);
            //headstyle1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
            headstyle1.setLocked(true);
            headstyle1.setWrapText(true);// 自动换行
            // 填写表格内容
            loadTaskDataMsg(clsData, sheet, workbook);
            // 把创建的内容写入到输出流中，并关闭输出流
            workbook.write(os);
            os.close();
            return fileFinalPath;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("创建excel文件失败");
        }
        return "";
    }
    public  void loadTaskDataMsg(List<St4ScsCy> clsData,HSSFSheet sheet,HSSFWorkbook workbook){
//        HSSFFont font = tools.getFont(workbook, "宋体", (short) 12, (short) 0);
//
//        HSSFCellStyle style = tools.getCellStyle(workbook, font,
//                HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN,
//                HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN);
//        HSSFCellStyle rstyle = tools.getCellStyle(workbook, font,
//                HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN,
//                (short) 2, HSSFCellStyle.BORDER_THIN);
//        HSSFCellStyle bstyle = tools.getCellStyle(workbook, font,
//                HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN,
//                HSSFCellStyle.BORDER_THIN, (short) 2);
//        HSSFCellStyle brstyle = tools.getCellStyle(workbook, font,
//                HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN,
//                (short) 2, (short) 2);
//
//        HSSFCellStyle ustyle = style;
//        HSSFCellStyle urstyle = rstyle;

        HSSFRow row = sheet.createRow(0);

        HSSFCell cell = null;

        cell = row.createCell(0);
        cell.setCellValue("任务名称");

        cell = row.createCell(1);
        cell.setCellValue("巡查人");
        //cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("航迹名称");
        // cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("开始时间");
        //  cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue("结束时间");

        cell = row.createCell(5);
        cell.setCellValue("巡查时长");

        cell = row.createCell(6);
        cell.setCellValue("轨迹里程");

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        int i = 1;

        for (St4ScsCy cl : clsData) {

            row = sheet.createRow(i);

            row.setHeightInPoints(30);
            cell = row.createCell(0);
            if(cl.getSt4ScsCl()!=null){
                cell.setCellValue(cl.getSt4ScsCl().getCl002() == null ? "" : cl.getSt4ScsCl().getCl002() .toString());// 任务名称
            }else {
                cell.setCellValue("");
            }

            cell = row.createCell(1);
            cell.setCellValue(cl.getUname() == null ? "" : cl.getUname().toString());// 巡查人

            cell = row.createCell(2);
            cell.setCellValue(cl.getCy002() == null ? "" : cl.getCy002().toString());// 航迹名称

            cell = row.createCell(3);
            cell.setCellValue(cl.getCy003() == null ? "" : df.format(cl.getCy003())); // 开始时间

            cell = row.createCell(4);
            cell.setCellValue(cl.getCy004()==null?"":df.format(cl.getCy004()));// 结束时间

            cell = row.createCell(5);
            cell.setCellValue(cl.getRwcs()==0?0:cl.getRwcs());// 巡查时长

            cell = row.createCell(6);
            cell.setCellValue(cl.getGjlc()==null?"":cl.getGjlc());// 轨迹里程

            i++;

        }


    }


   /* @Override
    public Result listWaypointFY(St4ScsCc st4ScsCc) throws Exception {
        int pageNumber = st4ScsCc.getPageNumber();  		//当前页
        int pageSize = st4ScsCc.getPageSize();  			//每页条数
        int poSum = st4ScsCcMapper.getPoSum(st4ScsCc); 		//总量
        int zys = (poSum+pageSize-1)/pageSize;	 			//总页数     （总条数+每页条数-1）/每页条数
        st4ScsCc.setFirstLimit((pageNumber-1)*pageSize); 	//开始索引    (当前页-1)*每页数量
        List list = st4ScsCcMapper.selectPoList(st4ScsCc);

        Result result = new Result();
        result.setRows(list);
        result.setTotal(poSum);
        result.setPage(pageNumber);
        result.setMsg("查询成功");
        result.setStatus(1000);
        return result;
    }*/

   /* @Override
    public Result listWaypoint(St4ScsCc st4ScsCc) throws Exception {
        *//**
         * 这里存在一个问题就是航点虽然分了巡护和核查航点2类是巡护航点的时候是有保护区行政区的信息的
         * 而在巡护航点的情况下是没有行政区及保护地信息的所以这里在统计的时候就会失真 简单来说就是有的航点会根本不在任意一个行政区下
         * 而没有被统计到 还有诸多%的值的数也要返回吧
         *//*
        String start = st4ScsCc.getStrTime();
        String end = st4ScsCc.getEndTime();
        if(st4ScsCc.getStrTime()!=null && st4ScsCc.getStrTime() != ""){
            st4ScsCc.setStrTime(st4ScsCc.getStrTime()+" 00:00:00");
        }
        if(st4ScsCc.getEndTime()!=null && st4ScsCc.getEndTime() != ""){
            st4ScsCc.setEndTime(st4ScsCc.getEndTime()+" 23:59:59");
        }
        return Result.build(1000,"查询成功",st4ScsCcMapper.selectSt4ScsCc(st4ScsCc));
    }*/

    @Override
    public ResultVO listPatrol(St4ScsCy st4ScsCy){

        List<St4ScsCy> list = st4ScsCyMapper.selectPoList(st4ScsCy);
        return ResultVOUtil.success(list);
    }

    /*@Override
    public Result listPatrolBySA001(St4ScsCy st4ScsCy) throws Exception {
        int pageNumber = st4ScsCy.getPageNumber();  		//当前页
        int pageSize = st4ScsCy.getPageSize();  			//每页条数
        int poSum = st4ScsCyMapper.getPoSum2(st4ScsCy); 		//总量
        int zys = (poSum+pageSize-1)/pageSize;	 			//总页数     （总条数+每页条数-1）/每页条数
        st4ScsCy.setPageNumber((pageNumber-1)*pageSize); 	//开始索引    (当前页-1)*每页数量
        List list = st4ScsCyMapper.selectPoList2(st4ScsCy);

        Result result = new Result();
        result.setRows(list);
        result.setTotal(poSum);
        result.setPage(pageNumber);
        result.setMsg("查询成功");
        result.setStatus(1000);
        return result;
    }
*/
    @Override
    public ResultVO listLedger(St4ScsCk ck) throws Exception {
        if(ck.getStrTime()!=null && ck.getStrTime() != ""){
            ck.setStrTime(ck.getStrTime()+" 00:00:00");
        }
        if(ck.getEndTime()!=null && ck.getEndTime() != ""){
            ck.setEndTime(ck.getEndTime()+" 23:59:59");
        }
        List<St4ScsCk> list = st4ScsCkMapper.selectSt4ScsCk(ck);
        return ResultVOUtil.success(list);
    }

   /* @Override
    public Result listLedgerFY(St4ScsCk St4ScsCk) throws Exception {
        int pageNumber = St4ScsCk.getPageNumber();  		//当前页
        int pageSize = St4ScsCk.getPageSize();  			//每页条数
        Integer poSum = st4ScsCkMapper.getPoSum(St4ScsCk); 		//总量
        int zys = (poSum+pageSize-1)/pageSize;	 			//总页数     （总条数+每页条数-1）/每页条数
        St4ScsCk.setFirstLimit((pageNumber-1)*pageSize); 	//开始索引    (当前页-1)*每页数量
        List<St4ScsCk> list = st4ScsCkMapper.selectPoList(St4ScsCk);

        Result result = new Result();
        result.setRows(list);
        result.setTotal(poSum);
        result.setPage(pageNumber);
        result.setMsg("查询成功");
        result.setStatus(1000);
        return result;
    }*/

    @Override
    public Result listLedgerzg(St4ScsCd st4ScsCd) throws Exception {
        List<Map> resList = st4ScsCkMapper.selectSt4ScsCkzg(st4ScsCd);
        return Result.build(1000,"查询成功",resList);
    }

    @Override
    public Result statisDw(St4ScsCc cc) {
        if(cc.getStrTime()!=null && cc.getStrTime() != ""){
            cc.setStrTime(cc.getStrTime()+" 00:00:00");
        }
        if(cc.getEndTime()!=null && cc.getEndTime() != ""){
            cc.setEndTime(cc.getEndTime()+" 23:59:59");
        }
        List<Map> resList = st4ScsCcMapper.statisDw(cc);
        return Result.build(1000,"查询成功",resList);
    }

    @Override
    public Result statisZw(St4ScsCc cc) {
        if(cc.getStrTime()!=null && cc.getStrTime() != ""){
            cc.setStrTime(cc.getStrTime()+" 00:00:00");
        }
        if(cc.getEndTime()!=null && cc.getEndTime() != ""){
            cc.setEndTime(cc.getEndTime()+" 23:59:59");
        }
        List<Map> resList = st4ScsCcMapper.statisZw(cc);
        return Result.build(1000,"查询成功",resList);
    }
    @Override
    public ResultVO pointStatistics(StaticSwagger ss) {

        St4ScsCd cd = new St4ScsCd();
        cd.setCl001(ss.cl001);
        cd.setSg001(ss.sg001);
        cd.setSd001(ss.sd001);
        cd.setGroupByName(ss.getGroupByName());
        St4SysSa sa = new St4SysSa();
        sa.setSa001(ss.sa001);

        cd.setSt4SysSa(sa);

        List<St4ScsCd> list2 = st4ScsCdMapper.getStaticPoint(cd);

        return ResultVOUtil.success(list2);
    }
}
