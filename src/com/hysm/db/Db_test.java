package com.hysm.db;

import org.bson.Document;

public class Db_test {

	
	public static void set_industry(){
		
		String str ="美食,蛋糕,培训,艺术培训,中小学培训,IT培训,购物,商场促销,产品促销,优惠券,服装,家具,社交,交友,陪游,招聘信息,应用市场,游戏,APP类,"
				+"二手转让,展会,演出,论坛,活动,景点旅游,娱乐,咖啡馆,茶馆,KTV,酒吧,健身,婚庆,周边生活服务,家政,老人健康,母婴服务";
		
		String [] arr = str.split(",");
		
		Base_db base_db = new Base_db();
		
		for(int i=0;i<arr.length;i++){
			
			int count = base_db.query_count("s_industry", null);
			
			int id = count+1;
			
			Document industry = new Document();
			industry.put("id", id);
			industry.put("name", arr[i]);
			industry.put("pId", 0);
			industry.put("state", 1);
			
			base_db.add_db_one("s_industry", industry);
			
		}
		
	}
	
	public static void main(String[] args) {
		 
	}
}
