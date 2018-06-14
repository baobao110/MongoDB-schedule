package com.hysm.service;

import java.util.List;

import org.bson.Document;

public interface AdminService {
	
	public List<Document> getALL(String db_name,Document doc);
	
	public void delete(String db_name,Object _id) throws Exception;
	
	public Document select_one(String db_name,String name,String password);
	
	public void add_db_one(String db_name,Document doc);
	
	
	public Document select_ByName(String db_name,String name);
	
	public Document select_ByPhone(String db_name,String phone);
	
	public void insert(String db_name,Document doc);
	
	public Document select_ById(String db_name,Object _id);
	
	public void update(String db_name,Document doc,Object _id);

}
