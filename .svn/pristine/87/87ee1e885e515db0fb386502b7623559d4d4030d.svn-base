package com.hysm.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.hysm.util.DateUtil;

/**
 * 
 * 严重日志，需要人工处理的记录
 * @author csc
 *
 */
public class LogError {
	
	public static File error=new File("error.log");
	public static void writeLog( String str) {
		try {

			if (!error.exists())
				error.createNewFile();

			FileOutputStream out = new FileOutputStream(error, true); // 如果追加方式用true
			StringBuffer sb = new StringBuffer();

			sb.append(DateUtil.fromDate24H()+str+"\n");
			out.write(sb.toString().getBytes("utf-8"));// 注意需要转换对应的字符集
			out.close();
		} catch (IOException ex) {
			System.out.println(ex.getStackTrace());
		}
	}
}
