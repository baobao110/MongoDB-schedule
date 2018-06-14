package com.hysm.service.impl;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hysm.db.UserDB;
import com.hysm.service.ILoginService;
@Service("loginService")
public class ILoginServiceImpl implements ILoginService {
	@Autowired
	private UserDB udb;
	@Override
	public Document queryUserByPhone(String phone) {
		// TODO Auto-generated method stub
		return udb.queryUserByPhone(phone);
	}
	@Override
	public Document queryUserByPhoneAndPwd(String phone, String pwd) {
		// TODO Auto-generated method stub
		return udb.queryUserByPhoneAndPwd(phone,pwd);
	}
	
	
	

}
