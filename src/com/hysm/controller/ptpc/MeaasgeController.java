package com.hysm.controller.ptpc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.hysm.controller.base.BaseController;
import com.hysm.service.IMessageService;
import com.hysm.util.StringUtil;
import com.hysm.util.pageUtil.JspToHtml;
/**
 * 消息
 * @author sicheng
 *
 */
@Controller
@RequestMapping("/message")
public class MeaasgeController extends BaseController{
	
	
	@Autowired
	private IMessageService meService;
	
	/**
	 * 
	 * 保存课程
	 * @param req
	 * @return
	 * 
	 */
	@RequestMapping("saveMerMessage")
	@ResponseBody
	public String saveMessage(HttpServletRequest req){
		
		String data=req.getParameter("data");
		try{
		if(StringUtil.bIsNotNull(data)){
			Document mess=Document.parse(data);
		
			
			
			
			int res=meService.saveMessage(mess);
			
			
			
			
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
	 * 保存题目
	 * @param req
	 * @return
	 */
	@RequestMapping("saveTM")
	@ResponseBody
	public String saveTM(HttpServletRequest req){
		
		String data=req.getParameter("data");
		try{
		if(StringUtil.bIsNotNull(data)){
			Document mess=Document.parse(data);
		
			
			
			
			int res=meService.saveTM(mess);
			
			
			
			
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
	 * 生成页面
	 * @param id
	 * @param merid
	 * @param req
	 */
	private void createNewHtml(String id, String merid, HttpServletRequest req) {
		try{
		String path = req.getContextPath();
		String basePath = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+path+"/";
		//生成html页面
		String str=JspToHtml.getCode(basePath+"message/queryOneMessJsp.do?id="+id);
		
		String realpath = req.getServletContext().getRealPath("");
		
		JspToHtml.writeHtml(realpath+"/messages/"+merid+"/",id+".html", str);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 课程列表
	 * @param req
	 * @param resp
	 */
	@RequestMapping("ajaxMessageList")
	@ResponseBody
	public void ajaxMessageList(HttpServletRequest req,HttpServletResponse resp){
		
		Document info= meService.ajaxMessageList(req);
		
		
		if(info!=null){
			
			sendJSON2(info.toJson(), resp);
			
		}
		
		
		
	}
	
	/**
	 * 题目列表
	 * @param req
	 * @param resp
	 */
	@RequestMapping("ajaxTmList")
	@ResponseBody
	public void ajaxTmList(HttpServletRequest req,HttpServletResponse resp){
		
		Document info= meService.ajaxTmList(req);
		
		
		if(info!=null){
			
			sendJSON2(info.toJson(), resp);
			
		}
		
		
		
	}
	/**
	 * 课程目录列表
	 * @param req
	 * @param resp
	 */
	@RequestMapping("ajaxKcList")
	@ResponseBody
	public void ajaxKcList(HttpServletRequest req,HttpServletResponse resp){
		
		JSONArray info= meService.ajaxKcTitleList();
		
		
		if(info!=null){
			
			sendJSON2(info.toString(), resp);
			
		}
		
		
		
	}
	/**
	 * 获取单个消息内容
	 * @param req
	 * @param resp
	 */
	@RequestMapping("ajaxOneMessByid")
	@ResponseBody
	public  void ajaxOneMessByid(HttpServletRequest req,HttpServletResponse resp){
		
		String id=req.getParameter("id");
		Document doc=meService.queryMessById(id);
		
		if(doc!=null){
			sendJSON2(doc.toJson(), resp);
		}
		
	}
	
	
	@RequestMapping("queryOneMessJsp")
	public  String queryOneMessJsp(HttpServletRequest req,Map<String, Object> model){
		
		String id=req.getParameter("id");
		
		Document doc=meService.queryMessDetailById(id);
		
		
		model.put("mess", doc);
		
		return "/sjpc/info/model1.jsp";
	}
	
	@RequestMapping("updateMessState")
	@ResponseBody
	public  void updateMessState(HttpServletRequest req,HttpServletResponse resp){
		
		String id=req.getParameter("id");
		int state=StringUtil.toNum(req.getParameter("state"));
		meService.updateMessState(id,state);
		
		sendJSON2("200", resp);
		
	}
	
	
	
	@RequestMapping("updateTMState")
	@ResponseBody
	public  void updateTMState(HttpServletRequest req,HttpServletResponse resp){
		
		String id=req.getParameter("id");
		int state=StringUtil.toNum(req.getParameter("state"));
		meService.updateTMState(id,state);
		
		sendJSON2("200", resp);
		
	}
	
	
	
	@RequestMapping("deleteTm")
	@ResponseBody
	public  void deleteTm(HttpServletRequest req,HttpServletResponse resp){
		
		String id=req.getParameter("id");
		meService.deleteTm(id);
		
		sendJSON2("200", resp);
		
	}
	
	
	/**
	 * 单个课程详情
	 * @param req
	 * @param resp
	 */
	@RequestMapping("ajaxOneKcInfo")
	@ResponseBody
	public void ajaxOneKcInfo(HttpServletRequest req,HttpServletResponse resp){
		String kcid=req.getParameter("id");
		
		Document kc=meService.ajaxOneKcInfo(kcid);
		
		
		
		if(kc!=null){
			
			sendJSON2(kc.toJson(), resp);
			
		}
		
		
		
	}
	

	/**
	 * 竞答练习
	 * 
	 * 随机抽取十题
	 * @param req
	 * @param resp
	 */
	@RequestMapping("ajaxTestQuestions")
	@ResponseBody
	public void ajaxTestTM(HttpServletRequest req,HttpServletResponse resp){
		
		
		JSONArray json=meService.ajaxTestQuestions(10);
		
		
		
		if(json!=null){
			
			sendJSON2(json.toString(), resp);
			
		}
		
		
		
	}
	
	
	@RequestMapping("editTm")
	public String editTm(HttpServletRequest req,Map<String,Object> model){
		
		String id=req.getParameter("id");
		Document tm=meService.queryTmByid(id);
		
		
		
		if(tm!=null){
			
			model.put("tm", tm);
			
		}
		
		
		return "pt/kc/addTm.jsp";
	}
	
	
	
	@RequestMapping("ajaxnums")
	@ResponseBody
	public void ajaxnums(HttpServletRequest req,HttpServletResponse resp){
		
				sendJSON2(meService.countNum().toJson(), resp);
		
	}
}
