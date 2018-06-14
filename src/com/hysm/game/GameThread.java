package com.hysm.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bson.Document;

import net.sf.json.JSONArray;

import com.alibaba.fastjson.JSONObject;
import com.hysm.db.MessageDB;

public class GameThread implements Runnable {

	@Override
	public void run() {
		
		 MessageDB mdb=new MessageDB();
		  Document jdrole=mdb.queryJdRole();
		
		
		//分配组队
		while(GameUtil.ZDWORK){
			
			//等待组合
			
			if(!GameUtil.cusers.isEmpty()&&GameUtil.cusers.size()>=2){
				
				System.out.println("use:::::"+GameUtil.cusers.toString());
				
				Map<String,Integer>  user1=this.findOne();
				Map<String,Integer>  user2=this.findOne();
				
				String uid1=user1.keySet().iterator().next();
				int passnum1=user1.get(uid1);
				
				
				String uid2=user2.keySet().iterator().next();
				int passnum2=user2.get(uid2);

				
				//判断是否登陆中
				/*if(GameUtil.isNoExter(uid1)&&GameUtil.isNoExter(uid2)){}else{
					//有人离开
					//没有离开的，返回重新配对
					if(GameUtil.isNoExter(uid1)){
						GameUtil.addCuser(uid1, passnum1);
					}
					if(GameUtil.isNoExter(uid2)){
						GameUtil.addCuser(uid2, passnum2);
					}
					
				}*/
				
				

				//未离开
				System.out.println("use3:::::"+GameUtil.cusers.toString());
				Document userinfo1=mdb.queryUserInfo(uid1);
				Document userinfo2=mdb.queryUserInfo(uid2);
				
				long ctime=System.currentTimeMillis();
				
				GameVO oneg=new GameVO();
				
				JSONObject onegame=new JSONObject();
				
				JSONArray users=new JSONArray();
				JSONObject user1obj=new JSONObject();
				user1obj.put(GameUtil.USER_ID, uid1);
				user1obj.put(GameUtil.USER_TIME, ctime);
				
				user1obj.put(GameUtil.USER_NAME, userinfo1.getString("name"));
				user1obj.put(GameUtil.USER_FROMWHERE, userinfo1.getString("schoolname"));
				user1obj.put(GameUtil.USER_FROMWHERE_PROVINCE, userinfo1.getString("province"));
				
				user1obj.put(GameUtil.USER_SCORE,0);//得分
				user1obj.put(GameUtil.USER_SUC_NUM,0);//答对0题
				users.add(user1obj);
				
				JSONObject user2obj=new JSONObject();
				user2obj.put(GameUtil.USER_ID, uid2);
				user2obj.put(GameUtil.USER_TIME, ctime);
				user2obj.put(GameUtil.USER_NAME, userinfo2.getString("name"));
				user2obj.put(GameUtil.USER_FROMWHERE, userinfo2.getString("schoolname"));
				user2obj.put(GameUtil.USER_FROMWHERE_PROVINCE, userinfo2.getString("province"));
				
				user2obj.put(GameUtil.USER_SCORE,0);//得分
				user2obj.put(GameUtil.USER_SUC_NUM,0);//答对0题
				users.add(user2obj);
				
				
				onegame.put(GameUtil.USERS, users);
				
				
				onegame.put(GameUtil.GAMESTATE,GameUtil.status[0]);//初始
				oneg.setData(onegame);
				
				
				//规则
				oneg.setJDRole(jdrole);
				
				
				GameUtil.games.put(uid2+uid1, oneg);
				
				
			
			
				
				
				
				
				
			}else{
				
			}
			
			
		}
		
		
		

	}

	private  Map<String,Integer> findOne() {
		
		String[] keys = GameUtil.cusers.keySet().toArray(new String[0]);  
		Random random = new Random();  
		
		
		String key= keys[random.nextInt(keys.length)];  
		
		Integer value=GameUtil.cusers.get(key);
		
		Map<String,Integer> user=new HashMap<String,Integer>();
		user.put(key, value);
		GameUtil.cusers.remove(key);
		return user;
	}

}