package com.hysm.db;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Component;

import com.hysm.bean.DBbean;
import com.hysm.db.mongo.MongoUtil;
import com.mongodb.client.model.Filters;

@Component
public class AdvertiseDB {
	
	private MongoUtil mu=MongoUtil.getThreadInstance();
	
	public int insertMess(Document doc) {
		try{
		 mu.insertOne(DBbean.T_advertise, doc);
		 return 1;
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return 0;
		
	}
	
	public List<Document> query_db_all(String db_name,Document doc){
		 
		List<Document> list = new ArrayList<Document>();
		
		
		 list= mu.find(db_name,doc);
		
		return list;
		 
	}
	
	public Document query_it_one(String db_name, Document doc) {

		Document back = mu.findOne(db_name, doc);
		return back;
	}
	
	public void replace_db_status(String db_name, Document doc, String _id) {
		mu.replaceOne(db_name, Filters.eq("_id", _id), doc);
	}
}
