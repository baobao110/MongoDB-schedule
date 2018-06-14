package com.hysm.bean;

public class SYS_code {

	//每个手机号每日发送短信限制
	public static int SMS_MAX = 10;
	
	//每个商家允许登录的最多人数
	public static int MER_LOGIN_MAX = 2;
	
	public static long MER_TOKEN_MILLI = 60*60*1000;
	
	//商家注册session 
	public static String SJ_REG_PHONE ="sj_reg_phone"; 
	public static String SJ_REG_CODE ="sj_reg_code";
	
	//商家登录session 
	public static String SJ_LOGIN_PHONE ="sj_login_phone"; 
	public static String SJ_LOGIN_CODE ="sj_login_code";
	
	
	
	//发送成功
	public static final String SEND_STATE_SUCCESS="200";
	
	//发送时间小于60s
	public static final String SEND_STATE_UNSEND="203";
	
	//超过今日发送上限
	public static final String SEND_STATE_EXTER="300";
	
}
