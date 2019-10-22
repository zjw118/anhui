package com.gistone.util;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
public class ExcelStyleTools {

    public HSSFFont getFont(HSSFWorkbook workbook,String name,short size,short bold) {

        HSSFFont font = workbook.createFont();
        font.setFontName(name);
        font.setFontHeightInPoints(size);
        font.setBoldweight(bold);

        return font;

    }

    public HSSFCellStyle getTitleStyle(HSSFWorkbook workbook,HSSFFont font) {

        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setLocked(true);
        style.setWrapText(true);

        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);

        return style;
    }

    public HSSFCellStyle getSubTitleStyle(HSSFWorkbook workbook,HSSFFont font,short align) {

        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(align);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setLocked(true);
        style.setWrapText(true);

        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);

        return style;
    }

    public HSSFCellStyle getHeadCellStyle(HSSFWorkbook workbook,HSSFFont font,short top,short left,short right,short bottom) {

        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setLocked(true);
        style.setWrapText(true);

        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);

        style.setBorderTop(top);
        style.setBorderLeft(left);
        style.setBorderRight(right);
        style.setBorderBottom(bottom);

        return style;
    }

    public HSSFCellStyle getCellStyle(HSSFWorkbook workbook,HSSFFont font,short top,short left,short right,short bottom) {

        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setLocked(true);
        style.setWrapText(true);

        style.setBorderTop(top);
        style.setBorderLeft(left);
        style.setBorderRight(right);
        style.setBorderBottom(bottom);

        return style;
    }
}
