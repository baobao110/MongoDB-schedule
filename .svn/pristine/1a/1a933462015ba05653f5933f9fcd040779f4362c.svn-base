package com.hysm.util.mobile;


import java.util.Date;

import org.bson.Document;

import com.hysm.bean.DBbean;
import com.hysm.db.mongo.MongoUtil;
import com.hysm.util.DateUtil;




public class SendMsg implements Runnable {
  
private String mobile,code,ip;
private int action;//1提交保修单2派送保修单3分配保修单4维修提交5完成报修6用户不满意，返修7派送错误，召回8验证9报修单取消


    @Override
    public void run() {
    	 try{
    	  	
    		 String result="1";
    	       // String result = SMSender.zj_send(mobile, code);
    	      
    	       smlog(mobile,code,result,DateUtil.fromDateY());
    	  
      
      
   
       }catch (Exception e) {
		// TODO: handle exception
       }
      
    }

    public SendMsg(String mobile,String code,int action,String ip) {
    	this.mobile=mobile;
    	this.code=code;
    	this.action=action;
    	this.ip=ip;
    	
    }
   
	
	public  void smlog(String mobile, String code, String result,String date) {
		
		Document  bson=new Document();
		
		bson.append("phone", mobile);
		bson.append("code", code);
		bson.append("result", result);
		bson.append("action", action);
		bson.append("ip", ip);
		bson.append("date", date);
		bson.append("ctime", new Date());
		
		
		
		MongoUtil mu=MongoUtil.getThreadInstance();
		mu.insertOne(DBbean.T_SMS_LOG, bson);
		
	}
	
    public static void main(String[] args) {
    	SendMsg sm=new SendMsg("15900848421", "ceshi123", 4, "192.168.0.1");
	}
  
}
