package com.hysm.db;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Component;

import com.hysm.bean.DBbean;
import com.hysm.db.mongo.MongoUtil;
import com.mongodb.client.model.Filters;

@Component
public class HistroryDB {
	
	MongoUtil mu = MongoUtil.getThreadInstance();
	
	/**
	 * 查询全部
	 * @param db_name
	 * @param doc
	 * @return
	 */
	public List<Document> query_db_all(String db_name,Document doc){
		 
		return mu.find(db_name, doc);
		 
	}
	
	public List<Document> ajaxMessageList(Document map) {
		mu.clear();

		List<Bson> list = new ArrayList<Bson>();

	/*	int state=map.getInteger("state");
		if(state==99){
			//全部
			
		}else{
			
			list.add(Filters.eq("state", state));
		}
		*/
		
		if (map.get("username") != null) {
			/*Pattern pattern = Pattern.compile("^.*" + map.getString("username")
					+ ".*$", Pattern.CASE_INSENSITIVE);*/

			/*list.add(Filters.regex("username", pattern));*/
			list.add(Filters.regex("username",map.getString("username")));
		}

		
		/*Document sort = new Document();
		sort.put("usertime", -1); // 按时间 倒序
*/
		int pn = (Integer) map.get("page");
		int ps = (Integer) map.get("limit");


		return mu.findLimitSort(DBbean.T_student_jdres,
				Filters.and(list), (pn - 1) * ps, ps, null);

	}
	
	public int ajaxMessageListNum(Document map) {

		mu.clear();

		List<Bson> list = new ArrayList<Bson>();

		/*int state=map.getInteger("state");
		if(state==99){
			//全部
			
		}else{
			
			list.add(Filters.eq("state", state));
		}
		*/
		
		if (map.get("username") != null) {
			Pattern pattern = Pattern.compile("^.*" + map.getString("username")
					+ ".*$", Pattern.CASE_INSENSITIVE);

			list.add(Filters.regex("username", pattern));
		}
		
		return (int) mu.count(DBbean.T_student_jdres, Filters.and(list));
		
	}

}
