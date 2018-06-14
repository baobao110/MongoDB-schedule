package com.hysm.controller.ptpc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hysm.controller.base.BaseController;
import com.hysm.service.IUserService;
import com.hysm.util.mobile.HYSM_TEMPCacher;
import com.hysm.util.mobile.Mobile_Bind;
/**
 * 用户
 * @author sicheng
 *
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	@Autowired
	private IUserService uService;
	/**
	 * 商家注册
	 */
	@RequestMapping(value = { "registWork" }, method = { RequestMethod.POST })
	@ResponseBody
	public void registWork(HttpServletRequest req,HttpServletResponse resp){
		try{
		String pwd=req.getParameter("pwd");
		String mobile=req.getParameter("mobile");
		String code=req.getParameter("code");
		Mobile_Bind mb=(Mobile_Bind)HYSM_TEMPCacher.getCache(mobile+"_MAUTH");
		if(mb!=null&&(mb.getCode()).equals(code)){
			//验证通过
			Document user=uService.regeditUser(mobile,pwd);
			if(user!=null){
				sendJSON2(user.toJson(),resp);
			}else{
				//该手机已存在
				sendJSON2("400",resp);
				
			}
			
		}else{
			HYSM_TEMPCacher.cache(mobile+"_MAUTH", mb);
			
			//验证码出错
			sendJSON2("500",resp);
			
		}
		}catch (Exception e) {
			e.printStackTrace();
			//注册时出错
			sendJSON2("600",resp);
		}
		
	}

}
