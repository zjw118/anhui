package com.gistone.bjhky.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
/*import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;*/

import java.io.File;
import java.text.DecimalFormat;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 校验EXCEL文件
 * @author LuoShuai
 * @time 2017年9月25日 下午5:28:12
 *
 */
public class ExcelUploadUtil {
	/**
	 * 
	 *@description TODO 校验字符串不能为空
	 *@method  isNotNull
	 *@return  boolean
	 *@auto LuoShuai
	 *@date 2017年9月25日 下午5:27:44
	 */
	public static boolean isNotNull(String param){
		if(param.length()>0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 
	 *@description TODO 校验是否享受字段格式
	 *@method  isNotNull
	 *@return  boolean
	 *@auto LuoShuai
	 *@date 2017年9月25日 下午5:27:44
	 */
	public static boolean isYesNotNull(String param){
		if("是".equals(param) || "否".equals(param)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 
	 *@description TODO 校验身份证号码
	 *@method  isCard
	 *@return  boolean
	 *@auto LuoShuai
	 *@date 2017年9月25日 下午5:45:45
	 */
	public static boolean isCard(String card){
		String reg="^[1-9][0-9]{5}(19[0-9]{2}|200[0-9]|2010)(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])[0-9]{3}[0-9xX]$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(card);
		return matcher.matches();
	}
	
	/**
	 * 获取单元格数据内容为字符串类型的数据
	 * 
	 * @param cell
	 *            Excel单元格
	 * @return String 单元格数据内容，若为字符串的要加单引号
	 */
	private static String getCellValue(HSSFCell cell) {
        DecimalFormat df = new DecimalFormat("0"); //防止excel数字 科学计数法显示   
		String strCell = "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			strCell = "" + cell.getStringCellValue() + "";
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			strCell = df.format(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			strCell = "''";
			break;
		default:
			strCell = "''";
			break;
		}
		if (strCell.equals("''") || strCell == null) {
			return "";
		}
		if (cell == null) {
			return "";
		}
		return strCell;
	}
	/**
	 * 
	 *@description TODO EXCEL列校验
	 *@method  excelColumnsValid
	 *@return  void
	 *@auto sqj
	 *@date 2017年10月19日 上午11:11:46
	 */
	public static Set<Integer> excelColumnsValids(String column, HSSFRow row, int rowth, int colth, HSSFCellStyle style, Set<Integer> delRows){
		HSSFCell cell = row.getCell(colth);
		String columnValue = "";
		if(row.getCell(colth)!=null){
			columnValue = getCellValue(row.getCell(colth)).trim();
		}
		
		if(column.equals("县")){
			
		}else if(column.equals("乡")){
			
		}else if(column.equals("村")){
			
		}else if(column.equals("家庭成员姓名")){
			if(!ExcelUploadUtil.isNotNull(columnValue)){//如果姓名为空 记录某行某列
				cell = row.createCell(colth);
				cell.setCellStyle(style);
				cell.setCellValue("此列信息必填!");
				delRows.add(rowth);//添加需要保留的数据
			}
		}else if(column.equals("身份证号码")){
			if(columnValue.length()==20){
				columnValue=columnValue.substring(0, 17);
			}
			if(!ExcelUploadUtil.isNotNull(columnValue)){//如果身份证为空 记录某行某列
				cell = row.createCell(colth);
				cell.setCellStyle(style);
				cell.setCellValue("此列信息必填!");
				delRows.add(rowth);
			}else if(!ExcelUploadUtil.isCard(columnValue)){///身份证不正确
				cell = row.getCell(colth);
				cell.setCellStyle(style);
				cell.setCellValue(columnValue+"(格式不正确!)");
				delRows.add(rowth);
			}
		}else if(column.equals("与户主关系")){
			
		}else if(column.equals("年度")){
			
		}else if(column.equals("是否享受")){
			if(!ExcelUploadUtil.isNotNull(columnValue)){//如果身份证为空 记录某行某列
				cell = row.createCell(colth);
				cell.setCellStyle(style);
				cell.setCellValue("此列信息必填!");
				delRows.add(rowth);
			}else if(!ExcelUploadUtil.isYesNotNull(columnValue)){///身份证不正确
				cell = row.getCell(colth);
				cell.setCellStyle(style);
				cell.setCellValue(columnValue+"(格式不正确!)");
				delRows.add(rowth);
			}
		}else{
			delRows.add(1);
			return delRows;
		}
		return delRows;
	}
	/**
	 * 
	 *@description TODO EXCEL列校验
	 *@method  excelColumnsValid
	 *@return  void
	 *@auto sqj
	 *@date 2017年10月19日 上午11:11:46
	 */
	public static Set<Integer> excelColumnsValid(String column, HSSFRow row, int rowth, int colth, HSSFCellStyle style, Set<Integer> delRows, String type){
		HSSFCell cell = row.getCell(colth);
		String columnValue = "";
		if(row.getCell(colth)!=null){
			columnValue = getCellValue(row.getCell(colth)).trim();
		}
		if(type.equals("0")){
			if(column.equals("所属地区")){
				if(!ExcelUploadUtil.isNotNull(columnValue)){//如果姓名为空 记录某行某列
					cell = row.createCell(colth);
					cell.setCellStyle(style);
					cell.setCellValue("此列信息必填!");
					delRows.add(rowth);//添加需要保留的数据
				}
			}else if(column.equals("户主姓名")){
				if(!ExcelUploadUtil.isNotNull(columnValue)){//如果姓名为空 记录某行某列
					cell = row.createCell(colth);
					cell.setCellStyle(style);
					cell.setCellValue("此列信息必填!");
					delRows.add(rowth);//添加需要保留的数据
				}
			}else if(column.equals("户主身份证号码")){
				if(columnValue.length()==20){
					columnValue=columnValue.substring(0, 17);
				}
				if(!ExcelUploadUtil.isNotNull(columnValue)){//如果身份证为空 记录某行某列
					cell = row.createCell(colth);
					cell.setCellStyle(style);
					cell.setCellValue("此列信息必填!");
					delRows.add(rowth);
				}else if(!ExcelUploadUtil.isCard(columnValue)){///身份证不正确
					cell = row.getCell(colth);
					cell.setCellStyle(style);
					cell.setCellValue(columnValue+"(格式不正确!)");
					delRows.add(rowth);
				}
			}else if(column.equals("享受人员姓名")){
				
			}else if(column.equals("享受人员身份证")){

				if(columnValue.length()==20){
					columnValue=columnValue.substring(0, 17);
				}
				if(!ExcelUploadUtil.isNotNull(columnValue)){//如果身份证为空 记录某行某列
					cell = row.createCell(colth);
					cell.setCellStyle(style);
					cell.setCellValue("此列信息必填!");
					delRows.add(rowth);
				}else if(!ExcelUploadUtil.isCard(columnValue)){///身份证不正确
					cell = row.getCell(colth);
					cell.setCellStyle(style);
					cell.setCellValue(columnValue+"(格式不正确!)");
					delRows.add(rowth);
				}
			}else if(column.equals("低保类型")){
			}else if(column.equals("低保证号")){
			}else if(column.equals("成员享受金额")){
			}else if(column.equals("是否享受")){
				if(!ExcelUploadUtil.isNotNull(columnValue)){//如果身份证为空 记录某行某列
					cell = row.createCell(colth);
					cell.setCellStyle(style);
					cell.setCellValue("此列信息必填!");
					delRows.add(rowth);
				}else if(!ExcelUploadUtil.isYesNotNull(columnValue)){///身份证不正确
					cell = row.getCell(colth);
					cell.setCellStyle(style);
					cell.setCellValue(columnValue+"(格式不正确!)");
					delRows.add(rowth);
				}
			}else{
				delRows.add(1);
				return delRows;
			}
		}else if(type.equals("1")){
			if(column.equals("旗县市区")){
				if(!ExcelUploadUtil.isNotNull(columnValue)){//如果姓名为空 记录某行某列
					cell = row.createCell(colth);
					cell.setCellStyle(style);
					cell.setCellValue("此列信息必填!");
					delRows.add(rowth);//添加需要保留的数据
				}
			}else if(column.equals("保险机构名称")){
				if(!ExcelUploadUtil.isNotNull(columnValue)){//如果姓名为空 记录某行某列
					cell = row.createCell(colth);
					cell.setCellStyle(style);
					cell.setCellValue("此列信息必填!");
					delRows.add(rowth);//添加需要保留的数据
				}
			}else if(column.equals("被保人姓名")){
				if(!ExcelUploadUtil.isNotNull(columnValue)){//如果姓名为空 记录某行某列
					cell = row.createCell(colth);
					cell.setCellStyle(style);
					cell.setCellValue("此列信息必填!");
					delRows.add(rowth);//添加需要保留的数据
				}
			}else if(column.equals("被保人证件号码")){
				if(columnValue.length()==20){
					columnValue=columnValue.substring(0, 17);
				}
				if(!ExcelUploadUtil.isNotNull(columnValue)){//如果身份证为空 记录某行某列
					cell = row.createCell(colth);
					cell.setCellStyle(style);
					cell.setCellValue("此列信息必填!");
					delRows.add(rowth);
				}else if(!ExcelUploadUtil.isCard(columnValue)){///身份证不正确
					cell = row.getCell(colth);
					cell.setCellStyle(style);
					cell.setCellValue(columnValue+"(格式不正确!)");
					delRows.add(rowth);
				}
			}else if(column.equals("出险日期")){
				
			}else if(column.equals("结案日期")){
				
			}else if(column.equals("赔付金额")){
				
			}else if(column.equals("住院补贴金额")){
				
			}else{
				delRows.add(1);
				return delRows;
			}
		}
		
		return delRows;
	}
	/**
	 * 
	 *@description TODO 获取json文件中的表头
	 *@method  getModelTitle
	 *@return  String
	 *@auto LuoShuai
	 *@date 2017年9月26日 下午6:04:16
	 */
	public JSONArray getModelTitle(String table_name) {
        String path = getClass().getClassLoader().getResource("table_model.json").toString();
        JSONArray jsonArray = null;
        path = path.replace("\\", "/");
        if (path.contains(":")) {
        	path = path.replace("file:/","");
        }
        try {
            String input = FileUtils.readFileToString(new File(path), "UTF-8");
            JSONObject jsonObject = JSONObject.fromObject(input);
            if (jsonObject != null) {
                jsonArray = jsonObject.getJSONArray(table_name);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonArray = null;
        }
        return jsonArray;
    }
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(ExcelValidUtil.validInterest("10000.01"));
	}*/
	
}