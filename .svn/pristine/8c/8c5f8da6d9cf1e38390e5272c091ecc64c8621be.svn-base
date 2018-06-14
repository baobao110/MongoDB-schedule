package com.hysm.controller.util;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hysm.controller.base.BaseController;
import com.hysm.util.DateUtil;
import com.hysm.util.StringUtil;
import com.hysm.util.img.PPTToImageUtil;

@Controller
@RequestMapping("/uploadppt")
public class UploadPptxController extends BaseController {

	private final String filepath = "/assets/img/ppts/";

	private final String imgpath = "/assets/img/1000/";

	

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/uploadPptx" }, method = { RequestMethod.POST })
	public void uploadPptx(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model) {

		
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		
//		 List<MultipartFile> files = multiRequest.getFiles("file");
		
		
		Iterator<String> iter = multiRequest.getFileNames();

		String file_code = DateUtil.getLocalByDate3()+"";

		
		
		
		String path = request.getRealPath("");
		String p_path = "";
		p_path += path;

		p_path += filepath;//ppt文件
		

		String ext = null, file_path = null;
		String fileName = "";
		int num=0;
		MultipartFile realfile=null;
		while (iter.hasNext()) {
			List<MultipartFile> files = multiRequest.getFiles((String) iter.next());
			
			for(MultipartFile file:files){
				num++;
				// 有上传文件的话，容量是大于0的。
				if (file.getSize() > 0) {
					realfile=file;
					
					break;

				}
				
			}
			
			if(realfile!=null&&realfile.getSize()>0){

				break;
			}
			
			
		}

		if (realfile == null) {
			sendJSON2("404", response);
		} else {
			int is_img = 0;
			
			fileName = realfile.getOriginalFilename();// 文件全名
			ext = fileName.substring(fileName.lastIndexOf("."));// 后缀

			File dir = new File(p_path);
			// 如果文件夹不存在则创建
			if (!dir.exists() && !dir.isDirectory()) {
				dir.mkdir();
			}

			if (ext.equals(".pptx")) {
				is_img = 1;
			} else if (ext.equals(".PPTX")) {
				is_img = 1;
			}

			if (is_img == 1) {
				String filenamestr=file_code+StringUtil.getRandomString(3) +num+ ext;
				File localFile = new File(p_path, filenamestr);// 指定文件名称后缀名
				file_path = p_path + filenamestr;

				try {
					
					realfile.transferTo(localFile); // 保存图片到指定文件夹目录中
					
				} catch (Exception e) {

					e.printStackTrace();
					
				} 
				
				
				//ppt 解析
				JSONArray allinfo=PPTToImageUtil.parsePptx(localFile,path,imgpath,file_code);
				
				JSONObject oneppt=new JSONObject();
				oneppt.put("pptx", filepath+filenamestr);
				
				oneppt.put("data", allinfo);
				sendJSON2(oneppt.toString(), response);
			} 
		}

	}

	
	
	
	
}
