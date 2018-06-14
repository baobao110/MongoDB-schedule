package com.hysm.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hysm.db.Base_db;

public class File_tool {

	/**
	 * 创建json文件
	 * @param file_path
	 * @param file_name
	 * @param context
	 * @return
	 */
	public static void create_write(String file_path,String file_name,String context){
		
		int back = 0;
		
		File file=new File(file_path+"/"+file_name+".json");
		
		FileOutputStream out = null;
		  
		 try {
			 if(!file.exists()){ 
				 file.createNewFile();
			 }
			 out=new FileOutputStream(file); 
			
			 StringBuffer sb=new StringBuffer();
             sb.append(context);
             out.write(sb.toString().getBytes("utf-8")); 
             out.close();
			
		} catch (IOException e) { 
			e.printStackTrace();
		} 
		 
	}
	
	/**
	 * 创建行业文件
	 */
	public static void create_industry(){ 
		Base_db base_db = new Base_db();
		
		Document doc = new Document();
		doc.put("state", 1); 
		List<Document>  list = base_db.query_db_all("s_industry", doc);
		
		JSONArray arr = new JSONArray();
		
		for(int i=0;i<list.size();i++){
			JSONObject json = new JSONObject();
			try {
				json.put("id", list.get(i).getInteger("id"));
				json.put("pId", list.get(i).getInteger("pId"));
				json.put("name", list.get(i).getString("name"));
				json.put("name", list.get(i).getString("name"));
				json.put("open", true);
			} catch (JSONException e) { 
				e.printStackTrace();
			}
			
			arr.put(json);
		}
		
		String context = arr.toString();
		
		create_write("D://Workspace/shangxin/WebRoot/assets/file", "industry", context);
		
		System.out.println("--------------------------------");
	}
	
	public static void test(){
		JSONObject json = new JSONObject();
		try {
			json.put("test", 1234);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String context = json.toString();
		
		create_write("D://Workspace/shangxin/WebRoot/assets/file", "aaa", context);
		
		System.out.println("--------------------------------");
	}
	
	
	public static void main(String[] args) {
		
		create_industry();
		
	}
	
}
