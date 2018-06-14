package com.hysm.controller.util;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hysm.controller.base.BaseController;
import com.hysm.service.ISjinfoService;
import com.hysm.util.Ip_tool;

@Controller
@RequestMapping("/mutil")
public class MobileUtilController extends BaseController {

	@Autowired
	private ISjinfoService sjinfoService;
	
	@RequestMapping(value = { "sendMess" }, method = { RequestMethod.POST })
	@ResponseBody
	public String sendMess(HttpServletRequest req, HttpServletResponse resp) {

		try {
			
			String mobile = req.getParameter("mobile");
			String code="【商信科技】尊敬的商信用户，您的验证码是:";
			String ip = Ip_tool.get_ip(req); 
			//return sjinfoService.sendMobile(mobile,code,ip);

			
			
		} catch (Exception e) {

			e.printStackTrace();
		}

		
		return "400";
	}

}
