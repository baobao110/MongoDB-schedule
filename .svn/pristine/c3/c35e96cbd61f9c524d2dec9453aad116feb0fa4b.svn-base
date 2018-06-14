package com.hysm.db.mongo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 配置文件读取
 * 1、工作流模板和工作流表格
 * 2、根据模板找到对应接口，找不到，取默认实现
 * 
 * @author sicheng
 *
 */
public class MongoBase {
	
	public static String ROOT="WEB-INF";
	/**
	 * 
	 * 配置文件路径
	 * 
	 */
	private  static  String prop_Path=null;
	
	/**
	 * 
	 * 数据库配置文件路径
	 * 
	 */
	private   static String dbpath=null;
	
	
	
		private   String getURL() {
		 
	        return this.getClass().getResource("").getPath().replaceAll("%20", " ");
	        
	    }

	 
	    public  String setPname(String pName) {
	    	
	    	
	    	String  url=getURL();
	           
	               
	        return url.substring(0, getURL().indexOf(ROOT)).replaceAll("file:", "") + ROOT+"/" + pName;
	    }

	

	

	public static String getProp_Path() {
		return prop_Path;
	}

	/**
	 * 项目中class路径
	 * @param str
	 */
	public  void setProp_Path(String str) {
		
		MongoBase.prop_Path = this.setPname(str);
	}

	/**
	 * 真实路径
	 * @param str
	 */
	public  void setRealProp_Path(String str) {
		
		MongoBase.prop_Path = str;
	}
	public static String getDbpath() {
		return dbpath;
	}

	/**
	 * 项目class路径下
	 * @param dbpath
	 */
	public  void setDbpath(String dbpath) {
		MongoBase.dbpath = setPname(dbpath);
		
	}
	/**
	 * 绝对路径
	 * @param dbpath
	 */
	public  void setRealDbpath(String dbpath) {
		MongoBase.dbpath = dbpath;
	}


	
	
	public Map<String, String> dbconURI() {
	        Map<String, String> properties = new HashMap<String, String>();
	        Properties prop = new Properties();
	        FileInputStream fis =null;
	        try {
	        	if(dbpath==null){
	        		this.setDbpath("classes/properties/jdbc.properties");
	        	}
	        	
	        	
	        	
	             fis=new FileInputStream(dbpath);
	            prop.load(fis);
	            String URI = prop.getProperty("dburi");
	            properties.put("uri", URI);
	            return properties;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally{
	        	try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        return null;
	    }

	    public Map<String, Object> getProperty() {
	        Map<String, Object> properties = new HashMap<String, Object>();
	        Properties prop = new Properties();
	        FileInputStream fis =null;
	        try {
	        	if(dbpath==null){
	        		this.setDbpath("classes/properties/jdbc.properties");
	        	}
	        	
	        	
	        	
	            fis=new FileInputStream(dbpath);
	            prop.load(fis);
	            String erpImageSrc = prop.getProperty("erpImageSrc");
	            properties.put("erpImageSrc", erpImageSrc);
	            return properties;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally{
	        	try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        return null;
	    }

	     public Map<String, String> dbconManual2Mongo() {
	        Map<String, String> properties = new HashMap<String, String>();
	        Properties prop = new Properties();
	        FileInputStream fis =null;
	        try {
	        	if(dbpath==null){
	        		this.setDbpath("classes/properties/jdbc.properties");
	        	}
	        	
	        	
	        	
	            fis=new FileInputStream(dbpath);
	            prop.load(fis);
	            String mongoDbName = prop.getProperty("mongo.dbName");
	            String ipadr = prop.getProperty("mongo.dbIp");
	            String port = prop.getProperty("mongo.dbPort");
	            String username = prop.getProperty("mongo.username");
	            String password = prop.getProperty("mongo.password");
	            
	            properties.put("database", mongoDbName);
	            properties.put("ip", ipadr);
	            properties.put("port", port);
	            properties.put("username", username);
	            properties.put("password", password);
	            
	           
	         
	          
	        
	        	
	        	
	        
	        
	        	
	        
	           
	            
	            String cursorFinalizerEnabled = prop.getProperty("mongo.cursorFinalizerEnabled");
	            //最小连接池
	            String minConnectionsPerHost = prop.getProperty("mongo.minConnectionsPerHost");
	            //#最大链接时间
	            String maxConnectionIdleTime = prop.getProperty("mongo.maxConnectionIdleTime");
	            String maxConnectionLifeTime = prop.getProperty("mongo.maxConnectionLifeTime");
	            
	            //连接池设置为300个连接,默认为100
	            String connectionsPerHost = prop.getProperty("mongo.connectionsPerHost");
	            //  #连接超时，推荐>3000毫秒
	            String connectTimeout = prop.getProperty("mongo.connectTimeout");
	            //   #最大等待时间
	            String maxWaitTime = prop.getProperty("mongo.maxWaitTime");
	            //套接字超时时间，0无限制
	            String socketTimeout = prop.getProperty("mongo.socketTimeout");
	            
	            String threadsAllowedToBlockForConnectionMultiplier = prop.getProperty("mongo.threadsAllowedToBlockForConnectionMultiplier");
	            
	            
	            properties.put("cursorFinalizerEnabled", cursorFinalizerEnabled);
	            properties.put("minConnectionsPerHost", minConnectionsPerHost);
	            properties.put("maxConnectionIdleTime", maxConnectionIdleTime);
	            properties.put("maxConnectionLifeTime", maxConnectionLifeTime);
	            properties.put("connectionsPerHost", connectionsPerHost);
	            properties.put("connectTimeout", connectTimeout);
	            properties.put("maxWaitTime", maxWaitTime);
	            properties.put("socketTimeout", socketTimeout);
	            properties.put("threadsAllowedToBlockForConnectionMultiplier", threadsAllowedToBlockForConnectionMultiplier);
	            
	            return properties;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally{
	        	try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        return null;
	    }

	   

		
		
}
