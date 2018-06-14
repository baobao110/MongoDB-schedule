package com.hysm.olympic.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 语音的文本内容校验
 * @author songkai 
 */
public class Word_tool {

	/**
	 * 去除字符串中的空格
	 * @param str
	 * @return
	 */
	public static String RemoveAllKong(String str){ 
		return str.replaceAll("\\s*", "");
	}
	
	/**
	 * 提取字符串中的汉字
	 * @param str
	 * @return
	 */
	public static String GetHanZi(String str){
		String reg = "[^\u4e00-\u9fa5]";
		return str.replaceAll(reg, "");  
	}
	
	/**
	 * 提取字符串中的英文字母
	 * @param str
	 * @return
	 */
	public static String GetChar(String str){
		String reg = "[^a-zA-Z]";
		return str.replaceAll(reg, ""); 
	}
	
	/**
	 * 提取字符串中的数字
	 * @param str
	 * @return
	 */
	public static String GetNum(String str){
		String reg = "[^0-9]";
		return str.replaceAll(reg, ""); 
	}
	
	/**
	 * 校验数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){ 
	   Pattern pattern = Pattern.compile("[0-9]*"); 
	   Matcher isNum = pattern.matcher(str);
	   if( !isNum.matches() ){
	       return false; 
	   } 
	   return true; 
	}
	
	/**
	 * 校验学生姓名(2-20个长度)
	 * @param str
	 * @return
	 */
	public static boolean CheckStudentName(String str){ 
		 str = GetHanZi(str); 
		 if(str.length()>= 2 && str.length()<= 20){
			 return true;
		 }else{
			return false; 
		 }
	}
	
	/**
	 * 校验竞猜答案
	 * @param str
	 * @return
	 */
	public static String CheckStudentChoose(String str){ 
		str = GetChar(str);  
		if(str.length() == 1){
			str = str.toUpperCase(); 
			return str;
		}else{
			return "";
		}
	}
}
