package com.hysm.service;

import org.bson.Document;




public interface ILoginService {


	
	/**
	 * 根据手机号获取用户
	 * @param phone
	 * @return
	 */
	Document queryUserByPhone(String phone);

	Document queryUserByPhoneAndPwd(String phone, String pwd);
	
 
	
	
	
}