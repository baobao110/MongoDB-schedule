package com.hysm.service.impl;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hysm.db.AdminDB;
import com.hysm.model.Admin;
import com.hysm.service.AdminService;
import com.mongodb.client.model.Filters;

@Service("admin")
public class AdminServiceImpl implements AdminService  {
	
	@Autowired
	private AdminDB admindb;
	
	
	public List<Document> getALL(String db_name,Document doc){
		List<Document> list=admindb.query_db_all(db_name,doc);
		return list;
		
	}
	
	/*public static void main(String args[]){
		AdminServiceImpl admin=new AdminServiceImpl();
		Admin a=admin.getALL("c_manager");
		System.out.println(a);
	}*/
	
	public void delete(String db_name,Object _id) throws Exception{
		Document docu=admindb.query_db_one(db_name, _id);
		if(null==docu){
			System.out.println("失败");
			throw new Exception();
		}
		else{
			docu.put("status", "用户");
			admindb.replace_db_status(db_name, docu,_id);
			System.out.println("成功");
		}
	}
	
	public Document select_one(String db_name,String name,String password){
		Document doc=new Document();
		doc.put("name",name);
		doc.put("password", password);
		Document docu=admindb.query_it_one(db_name, doc);
		return docu;
	}
	
	public void add_db_one(String db_name,Document doc){
		
		admindb.add_db_one(db_name, doc);
	}

	public void add(String db_name,Document doc){
	admindb.inertOneUser(db_name,doc);
}

	@Override
	public Document select_ByName(String db_name, String name) {
		// TODO Auto-generated method stub
		Document doc=new Document();
		doc.put("name",name);
		Document docu=admindb.query_it_one(db_name, doc);
		return docu;
	}

	public Document select_ByPhone(String db_name, String phone) {
		// TODO Auto-generated method stub
		Document doc=new Document();
		doc.put("phone",phone);
		Document docu=admindb.query_it_one(db_name, doc);
		return docu;
	}
	
	
	public void insert(String db_name,Document doc){
		admindb.insert_one(db_name, doc);
	}

	@Override
	public Document select_ById(String db_name, Object _id) {
		// TODO Auto-generated method stub
		
		Document doc=new Document();
		doc.put("_id",_id);
		Document docu=admindb.query_it_one(db_name, doc);
		return docu;
	}

	@Override
	public void update(String db_name, Document doc,Object _id) {
		// TODO Auto-generated method stub
		admindb.replace_db_status(db_name, doc, _id);
	}

	
}
