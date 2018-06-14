package com.hysm.controller.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hysm.controller.base.BaseController;

@Controller
@RequestMapping("/down")
public class DownFileController extends BaseController{
	
	@RequestMapping("apk")
	public void apk(HttpServletRequest req,HttpServletResponse resp){
		String path="/assets/";
		String rpath=req.getRealPath(path);
	
		File file=new File(rpath+"/pda.apk");
		
		try{
		String	fname = java.net.URLEncoder.encode("pda.apk", "UTF-8");
			
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("APPLICATION/OCTET-STREAM");  
			resp.setHeader("Content-Disposition","attachment; filename="+fname);
			System.out.println((int) file.length());
			resp.setContentLength((int) file.length());
			resp.setContentType("application/*");// 定义输出类型
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream buff = new BufferedInputStream(fis);
			byte[] b = new byte[1024];// 相当于我们的缓存
			long k = 0;// 该值用于计算当前实际下载了多少字节
			OutputStream myout = resp.getOutputStream();// 从response对象中得到输出流,准备下载
			// 开始循环下载
			while (k < file.length()) {
				int j = buff.read(b, 0, 1024);
				k += j;
				myout.write(b, 0, j);
			}
			myout.flush();
	        
	        
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
