package com.hysm.game;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONArray;


public class GameUtil {
	public static boolean ZDWORK=true;
	
	public static final long EXTERTIME=10000;//10s，10秒没有响应
	
	public static final String USER_TIME="usertime";

	public static final String USER_NAME = "username";

	public static final String USER_SCORE = "userscore";
	public static final String USER_SUC_NUM = "user_suc_num";
	
	
	public static final String GAMESTATE = "status";

	public static final String USERS = "userlist";

	public static final String USER_ID = "userid";

	public static final String KEY_ITEMS = "itemlist";//题目key

	public static final String userRes = "user_res";//用户答题 结果

	
	//数据key
	public static final String KEY_EXP = "exp";

	public static final String KEY_JF = "jf";

	public static final String KEY_STAR = "star";

	public static final String USER_FROMWHERE = "fromwhere";

	public static final String USER_FROMWHERE_PROVINCE = "province";//省
	
	
	//在线用户
	public static Map<String,Long> OnlineUsers =new HashMap<String,Long>(); 
	
	//已申请，未配对
	public static Map<String,Integer> cusers =new ConcurrentHashMap<String,Integer>(); 
	
	//游戏ji'he
	public static Map<String,GameVO> games =new ConcurrentHashMap<String,GameVO>();

	public static int[] status={0,1,2,3};//0初始1答题开始2答题结束
	
	private static GameThread gt=new GameThread();
	public static Thread gameThread=new Thread(gt);
	
	
	/**
	 * 获取用户等级
	 * @param userexp
	 * @return
	 */
	public static int getRankByEXP(long userexp){
		
		if(userexp<=100){
			return 1;
		}else{
			long nowexp=100;
			int level=2;
			while(userexp>nowexp){
				level++;
				nowexp=countEXP(level);
				if(userexp==nowexp){
					return level;
				}
			}
			
			return level-1;
			
		}
		
		
	}
	//100,150,200,250,300,350,400,450,500,550...每升一级，升级经验+50
	//0,100,250,450,700,1000,1350,1750,2200,2700,3250
	public static long countEXP(int n){
		if(n<=1){
			return 0;
		}else if(n==2){
			
		return 100;
		}else{
			
			return 50*((n+2)*(n-1)/2);
		}
		
	}
	
	
	
	
	public static void main(String[] args) {
		System.out.println(countEXP(7));
	}
	
	
	
	/**
	 * 
	 * @param uid
	 * @return
	 */
	public static GameVO queryGameByuid(String uid) {
		
		
		if(!games.isEmpty()){
			Iterator<String>	it=games.keySet().iterator();
			
			while(it.hasNext()){
				
				String key=it.next();
				
				if(key.contains(uid)){
					GameUtil.cusers.remove(uid);
					GameVO game= games.get(key);
					
					if(!GameUtil.isNoExter(uid)){
						//过期
						game.finished();
						return game;
						
					}else{
						
						
						return game;
					}
					
					
				}
			}
			
			
		}
		
		
		
		
	
		
		return null;
		
		
	}
	/**
	 * 
	 * @param uid
	 * @param rank 第几关
	 */
	public static void addCuser(String uid,int rank){
		
		GameUtil.cusers.put(uid, rank);
		
	}


	public static void removeGame(GameVO game) {
	    
		   JSONArray users=game.getData().getJSONArray(GameUtil.USERS);
		   String id=users.getJSONObject(1).getString(USER_ID)+users.getJSONObject(0).getString(USER_ID);
		   
		   games.remove(id);
		
		   game=null;
		
	}
	public static boolean isNoExter(String uid) {
		  //不超出返回itrue
		//System.out.println("uid==="+uid+",,,"+OnlineUsers.get(uid));
		if(uid!=null&&OnlineUsers.get(uid)!=null){
			long nt=System.currentTimeMillis();
			long yt=OnlineUsers.get(uid);
			
		
			
			if(nt-yt<=EXTERTIME){
				
				return true;
			}else{
				cusers.remove(uid);
				
				return false;
			}
		}

		return false;
		
	}
	
	

}
