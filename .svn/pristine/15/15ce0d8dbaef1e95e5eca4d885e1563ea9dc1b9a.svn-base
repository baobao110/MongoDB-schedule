package com.hysm.db;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Component;

import com.hysm.bean.DBbean;
import com.hysm.db.mongo.MongoUtil;
import com.hysm.util.MD5;
import com.mongodb.client.model.Filters;

@Component
public class UserDB {
	private MongoUtil mu=MongoUtil.getThreadInstance();

	public void inertOneUser(Document user) {
		mu.clear();
		mu.insertOne(DBbean.T_USERS, user);
		
	}

	public long countUserBymobile(String mobile) {
		mu.clear();
		
		return mu.count(DBbean.T_USERS, Filters.eq("mobile", mobile));
	}

	public Document queryUserByPhone(String phone) {
		mu.clear();
		
		return mu.findOne(DBbean.T_USERS, Filters.eq("mobile", phone));
	}

	public Document queryUserByPhoneAndPwd(String phone, String pwd) {
		mu.clear();
		List<Bson> all=new ArrayList<Bson>();
		all.add(Filters.eq("mobile", phone));
		all.add(Filters.eq("pwd", MD5.md5(pwd)));
		return mu.findOne(DBbean.T_USERS, Filters.and(all));
	}

	
	/**
	 * 更加uid获取用户信息
	 * @param id
	 * @return
	 */
	public Document queryUserByUID(String id) {
		
		return mu.findOne(DBbean.T_USERS, Filters.eq("_id", id));
	}
	

	
}
