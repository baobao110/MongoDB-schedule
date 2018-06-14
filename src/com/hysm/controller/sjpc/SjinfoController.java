package com.hysm.controller.sjpc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hysm.controller.base.BaseController; 
import com.hysm.service.IMessageService;
import com.hysm.service.ISjinfoService;
import com.hysm.util.StringUtil;

@Controller
@RequestMapping("/sjinfo")
public class SjinfoController extends BaseController{

	@Autowired
	private ISjinfoService sjinfoService;
	@Autowired
	private IMessageService meService;
	/*
	@RequestMapping("send_code")
	public  void  send_code(HttpServletRequest req,HttpServletResponse resp){
		
		String back_code = sjinfoService.send_reg_sms(req);
		
		JSONObject json = new JSONObject();
		try {
			json.put("back_code", back_code);
		} catch (JSONException e) { 
			e.printStackTrace();
		}
		
		sendJSON2(json, resp);
	}
	
	@RequestMapping("register")
	public  void  register(HttpServletRequest req,HttpServletResponse resp){
		
		Document back = sjinfoService.register_mer(req);
		
		sendJSON2(back.toJson(), resp);
	}
	
	@RequestMapping("check_merchants")
	public void check_merchants(HttpServletRequest req,HttpServletResponse resp){
		
		Document back = sjinfoService.check_mer(req); 
		sendJSON2(back.toJson(), resp);
	}
	
	@RequestMapping("get_merchants")
	public void get_merchants(HttpServletRequest req,HttpServletResponse resp){
		
		Document back = sjinfoService.get_mer(req); 
		sendJSON2(back.toJson(), resp);
	}
	
	@RequestMapping("save_merchants")
	public void save_mer(HttpServletRequest req,HttpServletResponse resp){
		
		Document back = sjinfoService.save_mer(req); 
		sendJSON2(back.toJson(), resp);
	}
	
	@RequestMapping("send_login_code")
	public  void  send_login_code(HttpServletRequest req,HttpServletResponse resp){
		
		String back_code = sjinfoService.send_login_sms(req);
		
		JSONObject json = new JSONObject();
		try {
			json.put("back_code", back_code);
		} catch (JSONException e) { 
			e.printStackTrace();
		}
		
		sendJSON2(json, resp);
	}
	
	@RequestMapping("login_password")
	public void login_password(HttpServletRequest req,HttpServletResponse resp){
		
		Document back = sjinfoService.login_pass(req); 
		sendJSON2(back.toJson(), resp);
	}
	
	@RequestMapping("login_code")
	public void login_code(HttpServletRequest req,HttpServletResponse resp){
		
		Document back = sjinfoService.login_code(req); 
		sendJSON2(back.toJson(), resp);
	}
	
	*//**
	 * 获取商家余额
	 * @param req
	 * @param resp
	 *//*
	@RequestMapping("ajaxMerBalance")
	public void ajaxMerBalance(HttpServletRequest req,HttpServletResponse resp){
		
		Document back = sjinfoService.check_mer(req); 
		int code=Integer.valueOf(String.valueOf(back.get("back_code")));
		if(code==200||code==250){
			
			int balance=sjinfoService.queryMerBalance(req.getParameter("id"));
			
			
			back.put("balance", balance);
			
		}
		
		sendMessage(back.toJson(), resp);
		
	}*/
	
	
}
