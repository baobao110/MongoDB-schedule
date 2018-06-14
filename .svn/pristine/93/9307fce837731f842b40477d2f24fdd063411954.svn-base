package com.hysm.olympic.tool;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
 

public class Json_tool {
	
	//school.json  [{"id":"1","school_name":"测试中学"},{"id":"2","school_name":"测试大学"}] 
	/**
	 * 获取全部的学校
	 * @return
	 */
	public JSONArray Get_all_school(){
		
		JSONArray jsarr = null;
		try {
			jsarr = new JSONArray(File_tool.read_file_info(1).toString());
		}catch(JSONException e){ 
			e.printStackTrace();
		}
		
		return jsarr;
	}
	
	//student.json [{"schoolid":"001","studentid":"20180001","classname":"高一一班","name":"张三"}]
	/**
	 * 校验学号是否重复
	 * @param schoolid
	 * @param studentid
	 * @return
	 */
	public boolean check_school_student(String schoolid,String studentid){
		
		boolean has = false;
		try {
			JSONArray jsarr = new JSONArray(File_tool.read_file_info(2).toString());
		  
			for(int i=0;i<jsarr.length();i++){
				if(jsarr.getJSONObject(i).getString("schoolid").equals(schoolid)
						&& jsarr.getJSONObject(i).getString("studentid").equals(studentid)){
					has = true;
				}
			}
			
		} catch (JSONException e) { 
			e.printStackTrace();
		} 
		return has;
	}
	
	/**
	 * 保存学生信息
	 * @param schoolid
	 * @param studentid
	 * @param classname
	 * @param name
	 * @param parent
	 * @param phone
	 */
	public void save_student(String schoolid,String studentid,String classname,String name,String parent,String phone){
		
		JSONArray jsarr = null;
		try {
			jsarr = new JSONArray(File_tool.read_file_info(2).toString());
		}catch(JSONException e){ 
			e.printStackTrace();
			jsarr = new JSONArray();
		}
		
		JSONObject json = new JSONObject();
		try {
			json.put("schoolid", schoolid);
			json.put("studentid", studentid);
			json.put("classname", classname);
			json.put("name", name);
			json.put("parent", parent);
			json.put("phone", phone);
			
			jsarr.put(json); 
			
		} catch (JSONException e) { 
			e.printStackTrace();
		}
		
		File_tool.write_file_info(jsarr.toString(), 2);
		 
	}
	
	/**
	 * 校验配置数据文件是否存在，不存在创建 从assets 里获取生成  
	 */ 
	public void crate_file(Context context){
		
		String school_arr = File_tool.read_file_info(1).toString();
		
		if(school_arr.equals("")){
			school_arr = File_tool.read_file(context, 1).toString();
			
			File_tool.write_file_info(school_arr, 1);
		} 
	}
	
	/**
	 * 学生登录校验学号
	 * @param schoolid
	 * @param studentid
	 * @return
	 */
	public Map<String,Object> check_studentid(String schoolid,String studentid){
		
		Map<String,Object> map = new HashMap<String, Object>();
		String student_name = "";
		boolean has = false;
		try {
			JSONArray jsarr = new JSONArray(File_tool.read_file_info(2).toString());
			
			for(int i=0;i<jsarr.length();i++){
				if(jsarr.getJSONObject(i).getString("schoolid").equals(schoolid)
						&& jsarr.getJSONObject(i).getString("studentid").equals(studentid)){
					has = true;
					student_name = jsarr.getJSONObject(i).getString("name");
				}
			}
			
		} catch (JSONException e) { 
			e.printStackTrace();
		} 
		
		map.put("has", has);
		map.put("student_name", student_name);
		
		return map;
	}
}
