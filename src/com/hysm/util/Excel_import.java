package com.hysm.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;

public class Excel_import {

	 private final static String xls = "xls";  
	 private final static String xlsx = "xlsx";
	 
	 public  Workbook getWorkBook(String fileName) {  
         
        //创建Workbook工作薄对象，表示整个excel  
        Workbook workbook = null;  
        try {  
            //获取excel文件的io流  
        	FileInputStream is = new FileInputStream(fileName); 
        	
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象  
            if(fileName.endsWith(xls)){  
                //2003  
                workbook = new HSSFWorkbook(is);  
            }else if(fileName.endsWith(xlsx)){  
                //2007  
                workbook = new XSSFWorkbook(is);  
            }  
        } catch (IOException e) {  
              
        }  
        return workbook;  
    }
	 
	 
	 
	 
    public  String getCellValue(Cell cell){  
        String cellValue = "";  
        if(cell == null){  
            return cellValue;  
        }  
       
        
        //判断数据的类型  
        switch (cell.getCellType()){  
            case HSSFCell.CELL_TYPE_NUMERIC: //数字  
            	  
            	cellValue = String.valueOf(cell.getNumericCellValue()); 
            	
            	if(cellValue.contains("E")&& cellValue.contains(".")){
            		
            		String str[] = cellValue.split("E"); 
            		
            		String reg = "[^0-9]";
            		
            		cellValue = str[0].replaceAll(reg, "");
            	}
            	
            	if(!cellValue.contains("E")&& cellValue.contains(".")){
            		 
            		cellValue = cellValue.substring(0,cellValue.indexOf("."));;
            	}
            	
            	//System.out.println(cellValue);
            	 
                break;  
            case Cell.CELL_TYPE_STRING: //字符串  
            	 
            	cellValue = String.valueOf(cell.getStringCellValue());
            	 
                break;  
            case Cell.CELL_TYPE_BOOLEAN: //Boolean  
                cellValue = String.valueOf(cell.getBooleanCellValue());  
                break;  
            case Cell.CELL_TYPE_FORMULA: //公式  
                cellValue = String.valueOf(cell.getCellFormula());  
                break;  
            case Cell.CELL_TYPE_BLANK: //空值   
                cellValue = "";  
                break;  
            
            case Cell.CELL_TYPE_ERROR: //故障  
                cellValue = "非法字符";  
                break;  
            default:  
                cellValue = "未知类型";  
                break;  
        }  
        return cellValue;  
    }
    
    
    public Map<String,Object> read_student(String filepath){
    	
    	Map<String,Object> map = new HashMap<String, Object>();
    	
    	//获得Workbook工作薄对象  
     	 Workbook workbook = getWorkBook(filepath);
     	 
     	 //获得当前sheet的开始行  
     	 Sheet sheet = workbook.getSheetAt(0);  
        if(sheet == null){  
            return null;  
        } 
        
        //获得当前sheet的结束行  
        int lastRowNum = sheet.getLastRowNum(); 
        
        String back_code = "200";
        
        
        List<Document> data_list = new ArrayList<Document>();
        
        for(int rowNum = 1 ;rowNum <= lastRowNum;rowNum++){  
            //获得当前行  
            Row row = sheet.getRow(rowNum);  
            if(row == null){  
                continue;  
            }  
            
            Document doc = new Document();
            
            String num = getCellValue(row.getCell(0)).trim();
            String name = getCellValue(row.getCell(1)).trim();
            String studentid = getCellValue(row.getCell(2)).trim(); 
            String grade = getCellValue(row.getCell(3)).trim();
            String classname = getCellValue(row.getCell(4)).trim();
            String parent = getCellValue(row.getCell(5)).trim();
            String phone = getCellValue(row.getCell(6)).trim();
            
           
	       	
	       	if(name== null || name.equals("")){
	       		back_code = "300"; 
	       		map.put("back_code", back_code);
	       		map.put("err_row", rowNum);
	       		
	       		return map;
	       	}
	       	
	       	if(studentid== null || studentid.equals("")){
	       		back_code = "400"; 
	       		map.put("back_code", back_code);
	       		map.put("err_row", rowNum);
	       		
	       		return map;
	       	}
	       	
	       	//校验学号格式（全为数字）
	       	if(isNumeric(studentid) == false){
	       		back_code = "450"; 
	       		map.put("back_code", back_code);
	       		map.put("err_row", rowNum);
	       		
	       		return map;
	       	}
	       	
	       	if(grade== null || grade.equals("")){
	       		back_code = "550"; 
	       		map.put("back_code", back_code);
	       		map.put("err_row", rowNum);
	       		
	       		return map;
	       	}
	       	
	       	if(classname== null || classname.equals("")){
	       		back_code = "500"; 
	       		map.put("back_code", back_code);
	       		map.put("err_row", rowNum);
	       		
	       		return map;
	       	}
	       	
	        doc.put("num", num);
	       	doc.put("name", name);
	       	doc.put("studentid", studentid);
	     	doc.put("grade", grade);
	       	doc.put("classname", classname);
	       	doc.put("parent", parent);
	       	doc.put("phone", phone);
	       	 
	       	data_list.add(doc); 
	       	
        }
        
        //校验学号重复
        
        for(int i=0;i<data_list.size();i++){
        	
        	String  sid= data_list.get(i).getString("studentid");
        	for(int j=0;j<data_list.size();j++){
        		
        		if(j!=i){
        			String  sid2= data_list.get(j).getString("studentid");
        			
        			if(sid.equals(sid2)){
        				back_code = "600"; 
        	       		map.put("back_code", back_code);  
        	       		return map;
        			}
        		}
        		 
        	}
        }
        
        back_code = "200"; 
        map.put("back_code", back_code);
   		map.put("err_row", 0);
   		
   		
   		
   	    map.put("list", data_list);
        
        return map;
        
    }
    
    
    public static boolean isNumeric(String str) {  
        Pattern pattern = Pattern.compile("[0-9]*");  
        Matcher isNum = pattern.matcher(str);  
        if (!isNum.matches()) {  
            return false;  
        }  
        return true;  
    }
    
    
    public static void main(String[] args) {
		
    	String number = "123.456";
    	String intNumber = number.substring(0,number.indexOf("."));
    	System.out.println(intNumber); 
	}
    
}
