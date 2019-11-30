package com.gistone.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcUtil {
    private static POIFSFileSystem fs;
    private static HSSFWorkbook wb;
    private static HSSFSheet sheet;
    private static HSSFRow row;
    public static String getStandardDate(String date){
        if(date.contains("-")){
            date = date.replace("-", "/");
        }
        int length = date.length();
        String str = date;
        if(length==10){
            return date;
        }else{
            String aa = date.substring(date.indexOf("/")+1,date.lastIndexOf("/"));
            if(aa.length()==1){
                aa=date.substring(0,4)+"/0"+aa;
            }else{
                aa=str.substring(0,str.lastIndexOf("/"));
            }
            String bb = date.substring(date.lastIndexOf("/")+1);
            if(bb.length()==1){

                bb=aa+"/0"+bb;
                return bb;
            }else{
                aa+="/"+bb;
                return aa;
            }

        }
    }
    /**
     * 利用正则表达式判断字符串是否是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){

        try {
            double db=Double.parseDouble(String.valueOf(str));

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 读取Excel数据内容
     * @param is
     * @return Map 包含单元格数据内容的Map对象
     */
    public static List<Map> readExcelContent(InputStream is) {
        Map<Integer, String> content = new HashMap<Integer, String>();
        String str = "";
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        List<Map> list=new ArrayList<Map>();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
            Map map=new HashMap<String, Object>();
            row = sheet.getRow(i);
            int j = 0;
            while (j < colNum) {
                // 每个单元格的数据内容用"-"分割开，以后需要时用String类的replace()方法还原数据
                // 也可以将每个单元格的数据设置到一个javabean的属性中，此时需要新建一个javabean
                // str += getStringCellValue(row.getCell((short) j)).trim() +
                // "-";
                Object aa=sheet.getRow(0).getCell(j);
                if(aa==null){

                }else if(sheet.getRow(0).getCell(j).toString().indexOf("（") == -1){
                    map.put(getStringCellValue(sheet.getRow(0).getCell(j)), removePoint(getCellFormatValue(row.getCell((short) j)).trim().toString()));
                }else{
                    map.put(getStringCellValue(sheet.getRow(0).getCell(j)).substring(0, sheet.getRow(0).getCell(j).toString().indexOf("（")), removePoint(getCellFormatValue(row.getCell((short) j)).trim().toString()));
                }
                j++;
            }
            map.put("row", i);
            list.add(map);
        }
        return list;
    }

    /**
     * 读取Excel数据全部内容
     * @param is
     * @return Map 包含单元格数据内容的Map对象
     */
    public static List<Map<String,Object>> readExcelContentAll(InputStream is) {
        Map<Integer, String> content = new HashMap<Integer, String>();
        String str = "";
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(4);
        int colNum = row.getPhysicalNumberOfCells();
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 0; i <= rowNum; i++) {
            Map<String,Object> map=new HashMap<String, Object>();
            row = sheet.getRow(i);
            int j = 0;
            while (j < colNum) {
                // 每个单元格的数据内容用"-"分割开，以后需要时用String类的replace()方法还原数据
                // 也可以将每个单元格的数据设置到一个javabean的属性中，此时需要新建一个javabean
                // str += getStringCellValue(row.getCell((short) j)).trim() +
                // "-";
                if(sheet.getRow(i).getCell(j)!=null){
                    map.put(j+"", removePoint(getCellFormatValue(row.getCell((short) j)).trim().toString()));
                }else{
                    map.put(j+"", null);
                }
                j++;
            }
            map.put("row", i);
            list.add(map);
        }
        return list;
    }

    /**
     * 获取单元格数据内容为字符串类型的数据
     *
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private static String getStringCellValue(HSSFCell cell) {
        String strCell = "";
        switch (cell.getCellType()) {

            case HSSFCell.CELL_TYPE_STRING:
                strCell = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                strCell = String.valueOf(cell.getNumericCellValue());
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                strCell = "";
                break;
            default:
                strCell = "";
                break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }

    /**
     * 获取单元格数据内容为日期类型的数据
     *
     * @param cell
     *            Excel单元格
     * @return String 单元格数据内容
     */
    private String getDateCellValue(HSSFCell cell) {
        String result = "";
        try {
            int cellType = cell.getCellType();
            if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                Date date = cell.getDateCellValue();
                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)
                        + "-" + date.getDate();
            } else if (cellType == HSSFCell.CELL_TYPE_STRING) {
                String date = getStringCellValue(cell);
                result = date.replaceAll("[年月]", "-").replace("日", "").trim();
            } else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
                result = "";
            }
        } catch (Exception e) {
            System.out.println("日期格式不正确!");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据HSSFCell类型设置数据
     * @param cell
     * @return
     */
    private static String getCellFormatValue(HSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case HSSFCell.CELL_TYPE_NUMERIC: {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cellvalue = cell.getStringCellValue();
                    break;
                }
                case HSSFCell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        // 如果是Date类型则，转化为Data格式

                        //方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                        //cellvalue = cell.getDateCellValue().toLocaleString();

                        //方法2：这样子的data格式是不带带时分秒的：2011-10-12
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        cellvalue = sdf.format(date);

                    }
                    // 如果是纯数字
                    else {
                        // 取得当前Cell的数值
                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case HSSFCell.CELL_TYPE_STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                // 默认的Cell值
                default:
                    cellvalue = "";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }
    public static String removePoint(String data){
        if(data.indexOf(".") == -1){
            return data;
        }else{
            if(data.contains("°")&&data.contains("′")&&data.contains(".")&&data.contains("″")){
                return data;
            }else{
                return data;
            }
        }
    }

    /**
     * 拷贝一个文件到另一个地址
     * @param oldPath
     * @param newPath
     */
    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
//                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }



    /**
     * 导出Excel
     * 注意：模板行下新增一空行，并设置内外边框。
     * API:http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Row.html
     * easyexcel  直接生成Excel
     * https://blog.csdn.net/jianggujin/article/details/80200400
     * @param mburl         模板路径   D:/xx/xx.xlsx
     * @param listMap       替换数据    List<Map<String, Object>>   {{$fe:maplist t.data1|t.data2|...|t.dataN}}
     * @param sign          模板行标识    传maplist    与{{$fe:maplist t.data1|t.data2|...|t.dataN}}其中对应
     * @param response      下载到客户端      允许为null
     * @param name          下载附件名称      允许为null    xx.xlsx
     * @param path          导出到本地目录    允许为null    D:/xx
     * @return              下载返回附件名、生成本地返回全路径名
     */
    public static String exportExcel(String mburl, List<Map<String, Object>> listMap, String sign, HttpServletResponse response, String name, String path){
        OutputStream out = null;
        try{
            TemplateExportParams params = new TemplateExportParams(mburl);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(sign,listMap);
            Workbook workbook = ExcelExportUtil.exportExcel(params,map);

            if(StringUtils.isNotBlank(path)){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                if (!path.endsWith("/")) path = path + File.separator;
                path = path + simpleDateFormat.format(new Date());
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                Date date = new Date();
                path =  path+File.separator+date.getTime()+mburl.substring(mburl.lastIndexOf("."));
                FileOutputStream fos = new FileOutputStream(path);
                workbook.write(fos);
                return path;
            }
            //下载Word到客户端
            if(null!=response){
//                response.setCharacterEncoding("UTF-8");
                response.setContentType("multipart/form-data");
                response.addHeader("Content-Disposition", "attachment;fileName="+ URLEncoder.encode(name,"UTF-8"));
                out = response.getOutputStream();
                workbook.write(out);
                return name;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try{
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }





}
