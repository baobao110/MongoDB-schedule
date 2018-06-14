package com.hysm.controller.ptpc;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hysm.controller.base.BaseController;
import com.hysm.service.IMessageService;
import com.hysm.util.StringUtil;
@Controller
@RequestMapping("/jd")
public class JdController  extends BaseController{
	
	@Autowired
	private IMessageService meService;
	/**
	 * 
	 * 保存课程
	 * @param req
	 * @return
	 * 
	 */
	@RequestMapping("saveJD")
	@ResponseBody
	public String saveJD(HttpServletRequest req){
		
		String data=req.getParameter("data");
		try{
		if(StringUtil.bIsNotNull(data)){
			Document mess=Document.parse(data);
		
			
			
			
			int res=meService.saveJD(mess);
			
			
			
			
			if(res>0){
				
//				this.createNewHtml(mess.getString("_id"),mess.getString("merid"),req);
				
				return "200";
				
				
			}
			
		}
		
		
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return "400";
		
	}
	
	
	/**
	 * 课程列表
	 * @param req
	 * @param resp
	 */
	@RequestMapping("ajaxJdList")
	@ResponseBody
	public void ajaxMessageList(HttpServletRequest req,HttpServletResponse resp){
		
		Document info= meService.ajaxJdList(req);
		
		
		if(info!=null){
			
			sendJSON2(info.toJson(), resp);
			
		}
		
		
		
	}
	
	
	
	/**
	 * 获取单个消息内容
	 * @param req
	 * @param resp
	 */
	@RequestMapping("ajaxOneJDByid")
	@ResponseBody
	public  void ajaxOneJDByid(HttpServletRequest req,HttpServletResponse resp){
		
		String id=req.getParameter("id");
		Document doc=meService.ajaxOneJDByid(id);
		
		if(doc!=null){
			sendJSON2(doc.toJson(), resp);
		}
		
	}
	
	
	@RequestMapping("updateJdState")
	@ResponseBody
	public  void updateJdState(HttpServletRequest req,HttpServletResponse resp){
		
		String id=req.getParameter("id");
		int state=StringUtil.toNum(req.getParameter("state"));
		meService.updateJdState(id,state);
		
		sendJSON2("200", resp);
		
	}
	
	
	/**
	 * 上传MP3音频文件
	 */
	@RequestMapping("uploadmp3")
	@ResponseBody
	public  void uploadmp3(HttpServletRequest request,HttpServletResponse response){
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iter = multiRequest.getFileNames();
		
		String file_code = String.valueOf(System.currentTimeMillis());
		
		String path = request.getRealPath(""); 
		String p_path = path + "/assets/audio/"; 
		String ext = "";
		String file_path ="";
		
		String back_code = "200";
		
		while (iter.hasNext()) {
			
			MultipartFile file = multiRequest.getFile((String) iter.next());
			
			// 有上传文件的话，容量是大于0的。
			if (file.getSize() > 0) {

				String fileName = file.getOriginalFilename();// 文件全名
				 
				//ext = fileName.substring(fileName.lastIndexOf("."));// 后缀
				ext=".mp3";
				 
				File dir = new File(p_path);
				// 如果文件夹不存在则创建
				if (!dir.exists() && !dir.isDirectory()){ 
					dir.mkdir();
				}
				
				//保存文件 
				File localFile = new File(p_path, file_code + ext);// 指定文件名称后缀名  存原图
				file_path = "/assets/audio/"+ file_code + ext;
				
				if(ext.equals(".mp3")){
					
					try {
						file.transferTo(localFile);
					}catch (Exception e) { 
						e.printStackTrace();
					} 
					back_code = "200";
					map.put("file_path", file_path);
					
				}else{
					back_code = "300";
				}
				
			}else{
				back_code = "300";
			}
		}
		
		map.put("back_code", back_code);
		
		sendjson(map, response);
		
	}
	
	
	/**
	 * 上传MP3音频文件
	 */
	@RequestMapping("upload_mymp3")
	@ResponseBody
	public  void upload_mymp3(HttpServletRequest request,HttpServletResponse response){
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iter = multiRequest.getFileNames();
		
		String file_code = String.valueOf(System.currentTimeMillis());
		
		String path = request.getRealPath(""); 
		String p_path = path + "/assets/audio/"; 
		String ext = "";
		String file_path ="";
		
		String back_code = "200";
		
		while (iter.hasNext()) {
			
			MultipartFile file = multiRequest.getFile((String) iter.next());
			
			// 有上传文件的话，容量是大于0的。
			if (file.getSize() > 0) {

				String fileName = file.getOriginalFilename();// 文件全名
				 
				ext = fileName.substring(fileName.lastIndexOf("."));// 后缀
				 
				 
				File dir = new File(p_path);
				// 如果文件夹不存在则创建
				if (!dir.exists() && !dir.isDirectory()){ 
					dir.mkdir();
				}
				
				//保存文件 
				File localFile = new File(p_path, file_code + ext);// 指定文件名称后缀名  存原图
				file_path = "/assets/audio/"+ file_code + ext;
				
				if(ext.equals(".mp3")){
					
					try {
						file.transferTo(localFile);
					}catch (Exception e) { 
						e.printStackTrace();
					} 
					back_code = "200";
					map.put("file_path", file_path);
					
				}else{
					back_code = "300";
				}
				
			}else{
				back_code = "300";
			}
		}
		
		map.put("back_code", back_code);
		
		sendjson(map, response);
		
	}
	 
	
}
