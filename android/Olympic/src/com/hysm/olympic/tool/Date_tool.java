package com.hysm.olympic.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Date_tool {

	public static long get_time(){
		return System.currentTimeMillis();
	}
	
	
	public static boolean check_time(long old){
		
		long now = System.currentTimeMillis();
		
		long second = (now-old)/1000;
		
		if(second>60){
			return true;
		}else{
			return false;
		}
	}
	
	public static String change_date(String str){
		
		SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		SimpleDateFormat f2 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒"); 
		
		Date d = null;
		try {
			d = f1.parse(str);
		} catch (ParseException e){ 
			e.printStackTrace();
		}
		
		return f2.format(d);
	}
	
	public static int compare_date(String str){
		
		SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date d = null;
		try {
			d = f1.parse(str);
		} catch (ParseException e){ 
			e.printStackTrace();
		}
		
		long time = d.getTime(); 
		long now = new Date().getTime();
		
		if(now> time){
			return 1;
		}else if(now == time){
			return 0;
		}else{
			return -1;
		}
	}
}
