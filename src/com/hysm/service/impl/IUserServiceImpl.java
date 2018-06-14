package com.hysm.service.impl;

import java.util.Date;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hysm.db.UserDB;
import com.hysm.service.IUserService;
import com.hysm.util.MD5;
@Service("userService")
public class IUserServiceImpl implements IUserService {
	@Autowired
	private UserDB udb;

	@Override
	public Document regeditUser(String mobile, String pwd) {
		//根据mobile获取用户失败
		long usernum=udb.countUserBymobile(mobile);
		if(usernum==0){
			Document user=new Document();
			user.put("mobile", mobile);
			user.put("pwd", MD5.md5(pwd));
			user.put("ctime", new Date());
		    udb.inertOneUser(user);
		    user.put("_id", user.get("_id").toString());
		    
		    return  user;
			
			
		}else{
			return null;//已存在
		}
		
	}
	
	

}
