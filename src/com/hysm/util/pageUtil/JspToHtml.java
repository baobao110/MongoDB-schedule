package com.hysm.util.pageUtil;
import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileOutputStream;  
import java.io.IOException;
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.OutputStreamWriter;
import java.io.PrintWriter;  
import java.net.HttpURLConnection;  
import java.net.URL; 
/**
 * 页面静态化
 * @author sicheng
 *
 */
public class JspToHtml {
	
	public static String getCode(String httpUrl ){ //参数是一个具体的http服务器的地址
		StringBuffer htmlcode=new StringBuffer();//定义返回的具体代码
		InputStream in=null;
		 InputStreamReader inputStreamReader=null;
		  BufferedReader reader=null;
		    try{
		        
		        URL url = new URL(httpUrl);
		        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		        connection.setRequestProperty("User-Agent", "Mozilla/4.0");
		        connection.connect();
		        in = connection.getInputStream();
		         inputStreamReader = new InputStreamReader(in, "UTF-8");
		         reader = new BufferedReader(inputStreamReader);
		        String currentLine = "";
		        while((currentLine = reader.readLine()) != null ){
		        	htmlcode.append(currentLine + "\n");
		        }
		        
		    }catch(Exception e){
		        e.printStackTrace();
		    }finally{
		    	try{
		    		
		    		if(reader!=null){
		    			reader.close();
		    		}
		    		if(inputStreamReader!=null){
		    			inputStreamReader.close();
		    		}
		            if(in!=null){
		            	 in.close();
		            }
		       
		    	}catch (Exception e) {
					e.printStackTrace();
				}
		    }
		    return htmlcode.toString();
		}
		//以上代码实现了读取JSP文件内容并取到代码的过程，接下来要做的是将这些代码写入到一个HTML文件里，请开下面的具体方法：
		public static synchronized void writeHtml(String filePath,String fname,String info){
		      
		    PrintWriter writer = null;
		    OutputStreamWriter outs=null;
		    try {
		        File file = new File(filePath);
		       
		        
		        if(!file.exists()){
		        	file.mkdirs();
		           
		        }
		        
		        File file2 = new File(filePath+fname);
		        if(!file2.exists()){
		        	file2.createNewFile();
		        } else{
		        	file2.delete();
		        	file2.createNewFile();
		        }
		        
		        outs=new OutputStreamWriter(new FileOutputStream(file2,false), "utf-8");
		        writer = new PrintWriter(outs);
		        writer.print(info);
		        writer.close();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }finally{
		    	if(writer!=null){
		    		writer.close();
		    	}
		    	if(outs!=null){
		    		try {
						outs.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	}
		        
		    }
		}

}
