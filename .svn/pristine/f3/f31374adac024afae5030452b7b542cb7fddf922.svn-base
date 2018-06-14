package com.hysm.util;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UuidUtil {

	public static String get32UUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}
	
	public static String getId(){ 
		return System.currentTimeMillis()+"";
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
	
	public static void main(String[] args) {
		System.out.println(isNumeric("ehdue56789"));
		System.out.println(isNumeric("00007"));
	}
}

