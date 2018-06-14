package com.hysm.olympic.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

public class File_tool {

	public static String DIR_NAME = "olympic";//文件目录

	public static String SCHOOL_FILEPATH ="school.json";//学校数据文件
	
	public static String STUDENT_FILEPATH ="student.json";//学生数据文件
	
	public static String KNOWLEDGE_FILEPATH = "knowledge.json";//咨询数据文件
	
	public static String QUESTION_FILEPATH= "question.json"; //竞猜数据文件
	
	public static String FILE_CODE ="UTF-8";
	
	
	/**
	 * 读取json文件的全部信息  assets
	 * @param context
	 * @param code
	 */
	public static StringBuilder read_file(Context context,int code){
		
		AssetManager am = context.getAssets();
		
		StringBuilder sb = new StringBuilder();
		 
		try {
			InputStream inputstream = null; 
			if(code == 1){
				inputstream = am.open(SCHOOL_FILEPATH);
			}else if(code == 2){
				inputstream = am.open(STUDENT_FILEPATH);
			}else if(code == 3){
				inputstream = am.open(KNOWLEDGE_FILEPATH);
			}else if(code == 4){
				inputstream = am.open(QUESTION_FILEPATH);
			}
			
			InputStreamReader inputReader = new InputStreamReader(inputstream,FILE_CODE);
			BufferedReader bufReader = new BufferedReader(inputReader);
			
			String line = "";
			
			while((line =bufReader.readLine())!= null){
				sb.append(line);
			}
			 
		} catch (IOException e){ 
			e.printStackTrace();
		}
		
		return sb;
	}
	
	/**
	 * 读取外部存储里面的文件
	 * @param code
	 * @return
	 */
	public static StringBuilder read_file_info(int code){
		
		String root_path = Environment.getExternalStorageDirectory().getAbsolutePath(); 
		String file_path  ="";
		if(code == 1){
			file_path = root_path+"/"+DIR_NAME+"/"+SCHOOL_FILEPATH;
		}else if(code == 2){
			file_path = root_path+"/"+DIR_NAME+"/"+STUDENT_FILEPATH;
		}else if(code == 3){
			file_path = root_path+"/"+DIR_NAME+"/"+KNOWLEDGE_FILEPATH;
		}else if(code == 4){
			file_path = root_path+"/"+DIR_NAME+"/"+QUESTION_FILEPATH;
		}
		
		StringBuilder sb = new StringBuilder();
		
		try {
			FileInputStream fin = new FileInputStream(new File(file_path)); 
			
			InputStreamReader inputReader = new InputStreamReader(fin,FILE_CODE);
			BufferedReader bufReader = new BufferedReader(inputReader);
			
			String line = "";
			
			while((line =bufReader.readLine())!= null){
				sb.append(line);
			}
			
		} catch (Exception e) { 
			e.printStackTrace();
		} 
		
		return sb; 
	}
	
	/**
	 * 写入数据到
	 * @param str
	 * @param code
	 */
	public static void write_file_info(String str,int code){
		
		String root_path = Environment.getExternalStorageDirectory().getAbsolutePath();
		
		File dir = new File(root_path+"/"+DIR_NAME);
		
		if(!dir.exists()){
			//创建文件夹
			dir.mkdir(); 
		}
		
		String file_path  ="";
		if(code == 1){
			file_path = root_path+"/"+DIR_NAME+"/"+SCHOOL_FILEPATH;
		}else if(code == 2){
			file_path = root_path+"/"+DIR_NAME+"/"+STUDENT_FILEPATH;
		}else if(code == 3){
			file_path = root_path+"/"+DIR_NAME+"/"+KNOWLEDGE_FILEPATH;
		}else if(code == 4){
			file_path = root_path+"/"+DIR_NAME+"/"+QUESTION_FILEPATH;
		}
		
		File file = new File(file_path);
		if(!file.exists()){
			try {
				file.createNewFile();
			}catch(IOException e){ 
				e.printStackTrace();
			}
		}
		
		try {
			FileOutputStream fos =new FileOutputStream(file);
			
			fos.write(str.getBytes(FILE_CODE));
			//关闭输出流
            fos.close();
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		 
	}
	
}
