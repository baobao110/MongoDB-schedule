package com.hysm.db;

import java.util.List;

import org.bson.Document;
 

import org.springframework.stereotype.Component;

import com.hysm.bean.DBbean;
import com.hysm.db.mongo.MongoUtil;
import com.hysm.util.UuidUtil;
import com.mongodb.client.model.Filters;

@Component
public class SchoolDB {

	MongoUtil mu = MongoUtil.getThreadInstance();
	
	/**
	 * 新增一条记录
	 */
	public void add_db_one(String db_name,Document doc){
		
		mu.insertOne(db_name, doc);
	}
	
	/**
	 * 修改一条记录
	 * @param db_name
	 * @param doc
	 * @param _id
	 */
	public void replace_db_byid(String db_name,Document doc,String _id){
		mu.replaceOne(db_name, Filters.eq("_id", _id), doc);
	}
	
	/**
	 * 删除一条记录
	 */
	public void delete_db_one(String db_name,String _id){
		mu.deleteMany(db_name, Filters.eq("_id", _id));
	}
	
	/**
	 * 查询一条记录根据_id
	 * @param db_name
	 * @param _id
	 * @return
	 */
	
	public Document query_db_one(String db_name,String _id){ 
		Document back = mu.findOne(db_name, Filters.eq("_id", _id));
		
		return back;
	}
	
	/**
	 * 查询一条数据
	 * @param db_name
	 * @param doc
	 * @return
	 */
	public Document query_it_one(String db_name,Document doc){
		
		Document back = mu.findOne(db_name, doc); 
		return back;
	}
	
	/**
	 * 
	 * @param db_name
	 * @return
	 */
	public Document get_one(String db_name){
		
		Document back = mu.findOne(db_name, null); 
		return back;
	}
	
	/**
	 * 查询全部
	 * @param db_name
	 * @param doc
	 * @return
	 */
	public List<Document> query_db_all(String db_name,Document doc){
		 
		return mu.find(db_name, doc);
		 
	}
	 
	
	public int query_count(String db_name,Document doc){
		
		long count = mu.count(db_name, doc);
		
		return (int)count;
	}
	
	
	public List<Document> query_db_page(String db_name,Document doc,int page,int limit){
		 
		return mu.findLimit(db_name, doc, page, limit);
		 
	}
	
	
	
	public List<Document> query_db_page_sort(String db_name,Document doc,int page,int limit,Document sort){
		 
		return mu.findLimitSort(db_name, doc, page, limit, sort);
		 
	}
	
	public List<Document> query_all_sort(String db_name,Document doc,Document sort){
		 
		return  mu.findSort(db_name,  doc, sort); 
		 
	}
	
	
	public String query_school_code(){
		
		long count = mu.count("s_school", null);
		
		int num = (int)(count+1);
		
		String code = num+"";
		
		if(code.length()==1){
			code = "00000"+code;
		}else if(code.length()==2){
			code = "0000"+code;
		}else if(code.length()==3){
			code = "000"+code;
		}else if(code.length()==4){
			code = "00"+code;
		}else if(code.length()==5){
			code = "0"+code;
		}  
		
		return code;
	}
	
	
	public static void main(String[] args) {
		
		/*Document pass = new Document();
		pass.put("_id", UuidUtil.getId());
		pass.put("pass","第二关");
		pass.put("passnum",2);
		pass.put("star",50);
		pass.put("state",1);
		
		new SchoolDB().add_db_one(DBbean.T_pass, pass);*/
		
		Document sort = new Document();
		sort.put("passnum", 1);
		
		Document doc = new Document();
		doc.put("state", 1);
		
		List<Document> list = new SchoolDB().query_all_sort(DBbean.T_PASS, doc, sort);
		
		System.out.println(list);
	}
	
}
