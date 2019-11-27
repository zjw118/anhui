package com.gistone.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.VO.ResultVO;
import com.gistone.entity.*;
import com.gistone.mapper.St4PoClCoMapper;
import com.gistone.mapper.St4ScsClMapper;
import com.gistone.mapper.SysUserMapper;
import com.gistone.service.ISt4PoClCoService;
import com.gistone.service.ISt4ScsClService;
import com.gistone.service.RlhdGroupService;
import com.gistone.util.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 任务批次表 服务实现类
 * </p>
 *
 * @author LiuXiong
 * @since 2019-08-14
 */
@Service
public class St4ScsClServiceImpl extends ServiceImpl<St4ScsClMapper, St4ScsCl> implements ISt4ScsClService {

    @Autowired
    private St4ScsClMapper st4ScsClMapper;

    @Autowired
    private ISt4ScsClService st4ScsClService;
    @Autowired
    private RlhdGroupService rlhdGroupService;
    @Autowired
    private SysUserMapper st4SysSaMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private ISt4PoClCoService st4PoClCoService;
    @Autowired
    private St4PoClCoMapper st4PoClCoMapper;
    private ExcelStyleTools tools;
    @Autowired
    private ConfigUtils configUtils;
    @Override
    public ResultVO getTaskDetail(St4ScsCl data) {

        St4ScsCl list = st4ScsClMapper.getTaskDetail(data);
        ResultCp res = new ResultCp();
        res.setData(list);
        return ResultVOUtil.success(res);
    }
    @Override
    public ResultVO listCdByTask(St4ScsCl data) {

        List<Map> list = st4ScsClMapper.listCdByTask(data);
        ResultCp res = new ResultCp();
        res.setData(list);
        return ResultVOUtil.success(res);
    }

    @Override
    public ResultVO importTask(Map<String, MultipartFile> items, St4SysSa seUser) {
        try{
            Iterator<MultipartFile> itr = items.values().iterator();
            while (itr.hasNext()) {
                Map<String,String> map=new HashMap<String, String>();
                MultipartFile item = itr.next ();
                InputStream in=item.getInputStream();
                boolean flag=true;//这个开关控制能不能执行插入操作
                List<Map> list = ExcUtil.readExcelContent(in);

                St4ScsCl  cl=null;
                List<St4ScsCl> cllist = new ArrayList<>();
                Integer i=2;
                for(Map data:list){
                    cl = new St4ScsCl();
                    cl.setCl012(1);
                    cl.setCl003("1");//todo 这里写死成1原因是目前没有没有对任务进行核查字段不同情况的处理
                    cl.setCl015(0);//因为是导入所以默认是系统创建
                    if(ObjectUtils.isNotNullAndEmpty(data.get("任务批次名称"))){
                        cl.setCl002(data.get("任务批次名称").toString());
                    }

                    if(ObjectUtils.isNotNullAndEmpty(data.get("任务类型"))){
                        String ttype=data.get("任务类型").toString().trim();
                        if("0".equals(ttype)){
                            cl.setCl004(0);
                        }else if("1".equals(ttype)){
                            cl.setCl004(1);
                        }
                    }
                    if(ObjectUtils.isNotNullAndEmpty(data.get("批次年份"))){
                        cl.setCl010(data.get("批次年份").toString());
                    }
                    if(ObjectUtils.isNotNullAndEmpty(data.get("任务描述"))){
                        cl.setCl009(data.get("任务描述").toString());
                    }

                    LocalDateTime date = LocalDateTime.now();
                    cl.setCl013(seUser.getSa001());
                    cl.setCl014(date);
                    cllist.add(cl);
                    i++;
                }
                if(cllist!=null&&cllist.size()>0){
                    if(st4ScsClService.saveBatch(cllist))
                        return ResultVOUtil.success();
                     return ResultVOUtil.error("1222","处理结果失败");
                }
                return ResultVOUtil.error("1444","服务器未读取到数据，请确认所上传excel是否有信息");


            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * poi3.9导入excel
     * @throws Exception
     */
    public  ResultVO readExcel(String fileName) throws Exception{

        //这里得对台账进行map的处理因为任务导入的时候是有可能填的台账是系统里存在了的
        QueryWrapper<RlhdGroup> rlhdGroupQueryWrapper = new QueryWrapper<>();
        rlhdGroupQueryWrapper.eq("del_flag", 1);
        List<RlhdGroup> rlhdGroups = rlhdGroupService.list(rlhdGroupQueryWrapper);
        Map<String,Integer> map =new HashMap<>();
        if(rlhdGroups!=null&&rlhdGroups.size()>0){
            for (RlhdGroup rg:rlhdGroups) {
                //得到台账的id和name对应的map集合
                map.put(rg.getName(),rg.getId());
            }
        }
        InputStream is = new FileInputStream(new File(fileName));
        Workbook hssfWorkbook = null;
        if (fileName.endsWith("xlsx")){
            hssfWorkbook = new XSSFWorkbook(is);//Excel 2007
        }else if (fileName.endsWith("xls")){
            hssfWorkbook = new HSSFWorkbook(is);//Excel 2003

        }
        // HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        // XSSFWorkbook hssfWorkbook = new XSSFWorkbook(is);
        St4ScsCl cl = null;
        St4PoClCo clCo = null;
        List<St4ScsCl> list = new ArrayList<St4ScsCl>();
        List<St4PoClCo> clColist = new ArrayList<St4PoClCo>();
        // 循环工作表Sheet
        for (int numSheet = 0; numSheet <hssfWorkbook.getNumberOfSheets(); numSheet++) {
            //HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // 循环行Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                //HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                Row hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    cl = new St4ScsCl();
                    cl.setCl012(1);
                    cl.setCl003("1");//todo 这里写死成1原因是目前没有没有对任务进行核查字段不同情况的处理
                    cl.setCl015(0);//因为是导入所以默认是系统创建
                    //HSSFCell name = hssfRow.getCell(0);
                    //HSSFCell pwd = hssfRow.getCell(1);
                    Cell taskName = hssfRow.getCell(0);  //任务批次名称
                    Cell taskDescri = hssfRow.getCell(1);//任务描述
                    Cell taskYear = hssfRow.getCell(2);//任务年份
                    Cell taskLedger = hssfRow.getCell(3);//任务台账

                    //这里是自己的逻辑
                    cl.setCl002(taskName==null?"":taskName.toString());
                    cl.setCl009(taskDescri==null?"":taskDescri.toString());
                    cl.setCl010(taskYear==null?"":taskYear.toString());
                    cl.setCl014(LocalDateTime.now());
                    if(ObjectUtils.isNotNullAndEmpty(taskLedger)){
                        if(ObjectUtils.isNotNullAndEmpty(map.get(taskLedger.toString().trim()))){
                            cl.setLedgerId(map.get(taskLedger.toString()).toString());
                        }
                    }
                    list.add(cl);
                }
            }
        }
        if(list!=null&&list.size()>0){
            if(st4ScsClService.saveBatch(list)){
                for (St4ScsCl cll:list) {
                    if(ObjectUtils.isNotNullAndEmpty(cl.getLedgerId())){
                        clCo= new St4PoClCo();
                        clCo.setCo001(Integer.valueOf(cll.getLedgerId()));
                        clCo.setCl001(cll.getCl001());
                        clColist.add(clCo);
                    }
                }
                if(clColist!=null&&clColist.size()>0){
                    if(!st4PoClCoService.saveBatch(clColist)){
                        return ResultVOUtil.error("1222","处理结果失败");
                    }
                }
                return ResultVOUtil.success();
            }
            return ResultVOUtil.error("1222","处理结果失败");
        }
        return ResultVOUtil.error("1444","服务器未读取到数据，请确认所上传excel是否有信息");
    }
    @Override
    public ResultVO exportTask(St4ScsCl data) {

        List<St4ScsCl> clsData = st4ScsClMapper.getExportData(data);


        JSONObject json = new JSONObject();
        String path = ExportExcel(clsData);/**/
        if(ObjectUtils.isNotNullAndEmpty(path)){
            json.put("excelPath", path);
            return ResultVOUtil.success(json);
        }else{
            return ResultVOUtil.error("1222","处理结果失败");
        }

    }


    public String ExportExcel(List<St4ScsCl> clsData){
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String fileName = df.format(time)+"任务信息.xls";
        String dir = configUtils.getExcel_PATH();
        String fileFinalPath=dir.substring(dir.indexOf(":")+1)+fileName;
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
            HSSFSheet sheet = workbook.createSheet("任务信息");
            /**
             * zjw自己写的代码开始
             *
             */
            // 这里是要导出7列
            sheet.setColumnWidth(0, (int) ((20.5 + 0.72) * 256));
            sheet.setColumnWidth(1, (int) ((20.5 + 0.72) * 256));
            sheet.setColumnWidth(2, (int) ((20.5 + 0.72) * 256));
            sheet.setColumnWidth(3, (int) ((12 + 0.72) * 256));


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
            tools = new ExcelStyleTools();
            // 填写表格内容
            loadTaskDataMsg(clsData, sheet, tools, workbook);
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
    public  void loadTaskDataMsg(List<St4ScsCl> clsData,HSSFSheet sheet,ExcelStyleTools tools,HSSFWorkbook workbook){
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
         //cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("任务描述");
        // cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("任务年份");
        //  cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("任务台账");
        // cell.setCellStyle(style);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        int i = 1;
        for (St4ScsCl cl : clsData) {

            row = sheet.createRow(i);

            row.setHeightInPoints(30);

            cell = row.createCell(0);
            cell.setCellValue(cl.getCl002() == null ? "" : cl.getCl002().toString());// 任务名称
            // cell.setCellStyle(ustyle);

            cell = row.createCell(1);
            cell.setCellValue(cl.getCl009() == null ? "" : cl.getCl009().toString());// 任务描述
            // cell.setCellStyle(ustyle);

            cell = row.createCell(2);
            cell.setCellValue(cl.getCl010() == null ? "" : cl.getCl010().toString()); // 任务年份
            // cell.setCellStyle(ustyle);

            cell = row.createCell(3);
            if(cl.getRlhdGroupList()!=null&&cl.getRlhdGroupList().size()>0){
                List<RlhdGroup> lists = cl.getRlhdGroupList();
                String value ="" ;
                List<String> rls = lists.stream().map(RlhdGroup::getName).collect(Collectors.toList());
                value+=rls;
                cell.setCellValue(value);
            }else{
                cell.setCellValue("");
            }
            //cell.setCellValue(cl.getRlhdGroupList()==null?"":cl.getRlhdGroupList().get(0).getName());// 任务台账
            //cell.setCellStyle(ustyle);

            i++;

        }


    }


//    @Override
//    public Result listForView(St4ScsCl data) {
//        QueryWrapper<St4ScsCl> queryWrapper = new QueryWrapper<>();
//        List<St4ScsCl> list = st4ScsClMapper.selectList(queryWrapper);
//        Result res = new Result();
//        res.setData(list);
//        res.setStatus(1000);
//        res.setMsg("查询问题点任务批次成功");
//        return res;
//    }

    @Override
    public ResultVO listTask(St4ScsCl data, SysUser seUser) {
        int size = data.getPageSize();//每页条数
        int number = data.getPageNumber();//开始索引
        int numberReal =0;
        if(0==number){
            numberReal = number;
        }else{
            numberReal= (number-1)*size;
        }
        data.setPageNumber(numberReal);
        data.setPageSize(size);
        List<St4ScsCl> list = st4ScsClMapper.listTask(data);
        data.setPageNumber(null);
        data.setPageSize(null);
        ResultCp res = new ResultCp();

        Integer tsize =st4ScsClMapper.listTask(data).size();
        res.setTotal(tsize);
        res.setRows(list);
        res.setPage((int)Math.ceil((double)tsize/size));
        return ResultVOUtil.success(res);
    }

    @Override
    public ResultVO insertTask(St4ScsCl data, SysUser seUser) {
        List<Integer> ledgerIdList = data.getLedgerIdList();
        ResultCp resultCp = new ResultCp();
        List<St4PoClCo> clcoList = new ArrayList<>();
        St4PoClCo clco = null;
        if(st4ScsClService.save(data)){
            Integer taskId =data.getCl001();
            for (Integer lid:ledgerIdList) {
                clco = new St4PoClCo();
                clco.setCl001(taskId);
                clco.setCo001(lid);
                clcoList.add(clco);
            }
            if(!st4PoClCoService.saveBatch(clcoList)){
                return ResultVOUtil.error(ResultEnum.HANDLEFAIL.getCode(), "服务器异常！");
            }
            return ResultVOUtil.success();
        }

        return ResultVOUtil.success();
    }

    @Override
    public ResultVO updateTask(St4ScsCl data, SysUser seUser) {
        ResultCp resultCp = new ResultCp();

        if(st4ScsClService.updateById(data)){
            QueryWrapper<St4PoClCo> clCoQueryWrapper = new QueryWrapper<>();
            //这里把之前绑定的旧的台账删除，然后把新的在赋上
            clCoQueryWrapper.eq("cl001",data.getCl001());
            if(st4PoClCoMapper.delete(clCoQueryWrapper)<1){
                return ResultVOUtil.error(ResultEnum.HANDLEFAIL.getCode(), "服务器异常！");
            }
            List<Integer> ledgerIdList = data.getLedgerIdList();

            List<St4PoClCo> clcoList = new ArrayList<>();
            St4PoClCo clco = null;
            for (Integer lid:ledgerIdList) {
                clco = new St4PoClCo();
                clco.setCl001(data.getCl001());
                clco.setCo001(lid);
                clcoList.add(clco);
            }
            if(!st4PoClCoService.saveBatch(clcoList)){
                return ResultVOUtil.error(ResultEnum.HANDLEFAIL.getCode(), "服务器异常！");
            }
            return ResultVOUtil.success();
        }else{
            return ResultVOUtil.error(ResultEnum.HANDLEFAIL.getCode(), "服务器异常！");
        }

    }

    @Override
    public ResultVO deleteTask(St4ScsCl data, SysUser seUser) {


        if(st4ScsClService.updateById(data)){
            QueryWrapper<St4PoClCo> clCoQueryWrapper = new QueryWrapper<>();
            //这里把之前绑定的旧的台账删除，然后把新的在赋上
            clCoQueryWrapper.eq("cl001",data.getCl001());
            if(st4PoClCoMapper.delete(clCoQueryWrapper)<1){
                return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "服务器异常！");
            }
            return ResultVOUtil.success();
        }else{
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "服务器异常！");
        }

    }
}
