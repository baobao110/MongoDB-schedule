package com.hysm.bean;



import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.hysm.game.GameUtil;


public class StartListener implements ServletContextListener{
	public static   final String APP_CUSER_KEY="unjd_users";//请求竞技用户
	
	public static   final String APP_GAMES_KEY="games";
	
	

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		
		
		//分配组队线程
		GameUtil.ZDWORK=true;
		GameUtil.gameThread.start();
		System.out.println("=====游戏分配启动=====");
	}

}
