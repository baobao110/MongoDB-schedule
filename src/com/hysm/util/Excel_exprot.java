package com.hysm.util;

 
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook; 
/**
 * excel 表格导出
 * @author songkai
 *
 */
public class Excel_exprot {

	public static HSSFWorkbook exportfile() {
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("学生信息");
		
		/* HSSFCellStyle style = wb.createCellStyle();
		 style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		 style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		 style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		 style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		 style.setBorderBottom(HSSFCellStyle.BORDER_THIN);*/
		
		 HSSFRow tempRow = sheet.createRow(0);
		 
		 for (int j = 0; j < 10; j++) {
			 HSSFCell cell_temp = tempRow.createCell((short) j);
		 }
  
		 sheet.setColumnWidth((short)0, (short)2000);
		 sheet.setColumnWidth((short)1, (short)5000);
		 sheet.setColumnWidth((short)2, (short)5000);
		 sheet.setColumnWidth((short)3, (short)5000);
		 sheet.setColumnWidth((short)4, (short)5000);
		 sheet.setColumnWidth((short)5, (short)5000); 
		 sheet.setColumnWidth((short)6, (short)7000); 
		 
		HSSFRow row = sheet.getRow(0);
		row.getCell((short) 0).setCellValue("序号");
		row.getCell((short) 1).setCellValue("学生姓名");
		row.getCell((short) 2).setCellValue("学号");
		row.getCell((short) 3).setCellValue("年级");
		row.getCell((short) 4).setCellValue("班级");
		row.getCell((short) 5).setCellValue("家长");
		row.getCell((short) 6).setCellValue("联系方式"); 
		
		row.setHeight((short) (500));
		
		return wb;
		 
	}
}
