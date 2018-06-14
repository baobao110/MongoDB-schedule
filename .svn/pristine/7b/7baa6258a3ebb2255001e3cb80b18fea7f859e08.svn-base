package com.hysm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class Ip_tool {

	 /**
	 * 获取访问ip
	 * @param request
	 * @return
	 */ 
	public static String get_ip(HttpServletRequest request){
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getRemoteAddr();
	    }
	    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}
	
	/**
	 * 创建商家登陆令牌
	 * @param logininfo
	 * @param phone
	 * @param ip
	 * @return
	 */
	public static Map<String,Object> create_token(String logininfo,String phone,String ip){
		
		 long time_milli = System.currentTimeMillis(); 
		 String token = MD5.md5(phone+time_milli);
		 
		 Map<String,Object> map = new HashMap<String, Object>();
		 map.put("time_milli", time_milli);
		 map.put("token", token);
		 map.put("logininfo", logininfo);
		 map.put("phone", phone);
		 map.put("ip", ip);
		 
		 return map;
	}
	
	/**
	 * 查找最早的登录令牌
	 * @param token_list
	 * @return
	 */
	public static int remove_token(List<Map<String,Object>> token_list){
		
		int num = 0; 
		long min_milli = System.currentTimeMillis();
		
		for(int i=0;i<token_list.size();i++){ 
			long time_milli = Long.valueOf(token_list.get(i).get("time_milli").toString()); 
			if(time_milli <= min_milli){
				num = i;
				min_milli = time_milli;
			}
		}
		
		return num;
	}
	
	
	public static void main(String[] args) {
		
		 String keyword = "饭馆；餐饮";
		 
		 keyword = keyword.replaceAll("；", ";"); 
		 
		 String[] key_arr = keyword.split(";");
		 
		 List<String> key_list = new ArrayList<String>();
			for(int i=0;i<key_arr.length;i++){
				key_list.add(key_arr[i]);
			}
		 
		 System.out.println(key_list +"-------------"+key_arr.length);
	}
	
	
}
