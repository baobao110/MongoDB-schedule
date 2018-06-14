package com.hysm.util;

import java.util.Random;
import java.util.UUID;

/**
 * 字符串相关方法
 *
 */
public class StringUtil {
	/***
	 * 判读字符串是否为空值
	 * @param str
	 * @return 为true 表示不是空值，否则是空值
	 */
	public static boolean bIsNotNull(Object obj) {
		if(obj==null){
			return false;
		}
		String str=String.valueOf(obj);
		if(str.trim().equalsIgnoreCase("null") || str.trim().equals(""))
			return false;
		else 
			return true;
	}
	
	
	/**
	 * 将以逗号分隔的字符串转换成字符串数组
	 * @param valStr
	 * @return String[]
	 */
	public static String[] StrList(String valStr){
	    int i = 0;
	    String TempStr = valStr;
	    String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
	    valStr = valStr + ",";
	    while (valStr.indexOf(',') > 0)
	    {
	        returnStr[i] = valStr.substring(0, valStr.indexOf(','));
	        valStr = valStr.substring(valStr.indexOf(',')+1 , valStr.length());
	        
	        i++;
	    }
	    return returnStr;
	}
	
	/**获取字符串编码
	 * @param str
	 * @return
	 */
	public static String getEncoding(String str) {      
	       String encode = "GB2312";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s = encode;      
	              return s;      
	           }      
	       } catch (Exception exception) {      
	       }      
	       encode = "ISO-8859-1";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s1 = encode;      
	              return s1;      
	           }      
	       } catch (Exception exception1) {      
	       }      
	       encode = "UTF-8";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s2 = encode;      
	              return s2;      
	           }      
	       } catch (Exception exception2) {      
	       }      
	       encode = "GBK";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s3 = encode;      
	              return s3;      
	           }      
	       } catch (Exception exception3) {      
	       }      
	      return "";      
	   } 
	
	/**
	 * 如果字符串为null，则替换为空字符
	 * @param str
	 * @return
	 */
	public static String replaceNull(String str) {
		if(str==null || str.trim().equalsIgnoreCase("null") || str.trim().equals(""))
			return "";
		else 
			return str;
	}
	
	/**
	 * JAVA生成的唯一号，用于记录的监测终端唯一编码,去掉“-”
	 * 
	 * @return 32位字符
	 */
	
	public static String getT_UUID() {
		String s = UUID.randomUUID().toString();
		// 去掉“-”符号
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
				+ s.substring(19, 23) + s.substring(24);
	}
	
	public static String getRandomString(int length){ //length表示生成字符串的长度  
	    String base = "0123456789";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
	 }
	
	public static String get_sjid(){
		
		String code = getRandomString(4);
		String str = System.currentTimeMillis()+code;
		
		return str;
	}

	public static int toNum(Object time) {
		if(bIsNotNull(time)){
			int num=0;
			try{
				
				num=Integer.valueOf(String.valueOf(time));
				return num;
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		return 0;
	}
	public static int toNumFromfloat(Object time) {
		if(time!=null){
			int num=0;
			try{
				num=Float.valueOf(String.valueOf(time)).intValue();
				return num;
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		return 0;
	}
	public static int toMoney(String curprice) {
		int i=0;
		try{
			if(bIsNotNull(curprice)){
				i=(int)(Double.valueOf(curprice)*100);
			}
			
			
			
		}catch (Exception e) {
			
		}
		return i;
	}  
	public static void main(String[] args) {
		System.out.println(StringUtil.getT_UUID());
	}
}
