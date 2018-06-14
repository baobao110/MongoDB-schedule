package com.hysm.db.mongo;

import com.mongodb.*;
import com.mongodb.MongoClientOptions.Builder;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by eldanote on 2017/2/9.
 */
public class MongoConn {

    private static String _connIP;
    private static String _user;
    private static String _database;
    private static String _password;
    private static int _port;

    public static String get_database() {
    	if(_database==null){
    		MongoBase mp = new MongoBase();
            Map<String, String> Manualmaps = mp.dbconManual2Mongo();
            if (Manualmaps.containsKey("database"))
                if (!Manualmaps.get("database").equals("")) {
                    _database = Manualmaps.get("database");
                }
    	}
    	
        return _database;
    }

    public static void set_database(String _database) {
        MongoConn._database = _database;
    }

    static MongoClient getConn() {
        String dburi = "";
        MongoBase mp = new MongoBase();
        Map<String, String> URImaps = mp.dbconURI();
        Map<String, String> Manualmaps = mp.dbconManual2Mongo();
        _connIP= Manualmaps.get("ip");
        _port = Integer.valueOf(Manualmaps.get("port"));
        _user=Manualmaps.get("username");
        _password=Manualmaps.get("password");
        _database=Manualmaps.get("database");
        
        if (URImaps != null)
            dburi = URImaps.get("uri");
        try {
            MongoClientOptions options = getDefaultOptions(Manualmaps);
            MongoClient _mongoClient;
            if (_user!=null&&!_user.equals("")) {
                if (dburi!=null&&!dburi.equals("")) {
                    MongoClientURI muri = new MongoClientURI(dburi);
                    _mongoClient = new MongoClient(muri);
                    
                   
                } else {
                    MongoCredential credential = MongoCredential.createCredential(_user, _database, _password.toCharArray());

                    _mongoClient = new MongoClient(new ServerAddress(_connIP, _port), Arrays.asList(credential),
                            options);
                }
            } else {
                _mongoClient = new MongoClient(new ServerAddress(_connIP, _port), options);
            }
            return _mongoClient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void set_user(String _user) {
        MongoConn._user = _user;
    }

    public static void set_connIP(String _connIP) {
        MongoConn._connIP = _connIP;
    }

    public static void set_password(String _password) {
        MongoConn._password = _password;
    }

    public static void set_port(int _port) {
        MongoConn._port = _port;
    }

    private static MongoClientOptions getDefaultOptions(Map<String, String> manualmaps) {
    	
    	
    	
    	Builder options = new MongoClientOptions.Builder();
    	try{
    	if(manualmaps.get("cursorFinalizerEnabled")!=null){
    		boolean cursorFinalizerEnabled=Boolean.parseBoolean(manualmaps.get("cursorFinalizerEnabled"));
    		 options.cursorFinalizerEnabled(cursorFinalizerEnabled);// 
    	}
    	if(manualmaps.get("minConnectionsPerHost")!=null){
    		int minConnectionsPerHost=Integer.valueOf(manualmaps.get("minConnectionsPerHost"));
    		options.minConnectionsPerHost(minConnectionsPerHost);
    	}
    	if(manualmaps.get("maxConnectionIdleTime")!=null){
    		int maxConnectionIdleTime=Integer.valueOf(manualmaps.get("maxConnectionIdleTime"));
    		options.maxConnectionIdleTime(maxConnectionIdleTime);
    	}
    	if(manualmaps.get("maxConnectionLifeTime")!=null){
    		int maxConnectionLifeTime=Integer.valueOf(manualmaps.get("maxConnectionLifeTime"));
    		  options.maxConnectionLifeTime(maxConnectionLifeTime);
    	}
    	if(manualmaps.get("connectionsPerHost")!=null){
    		int connectionsPerHost=Integer.valueOf(manualmaps.get("connectionsPerHost"));
    		options.connectionsPerHost(connectionsPerHost);
    	}
    	
    	
    	if(manualmaps.get("maxWaitTime")!=null){
    		int maxWaitTime=Integer.valueOf(manualmaps.get("maxWaitTime"));
    		options.maxWaitTime(maxWaitTime);
    	}
    	if(manualmaps.get("connectTimeout")!=null){
    		int connectTimeout=Integer.valueOf(manualmaps.get("connectTimeout"));
    		  options.connectTimeout(connectTimeout);
    	}
    	if(manualmaps.get("socketTimeout")!=null){
    		int socketTimeout=Integer.valueOf(manualmaps.get("socketTimeout"));
    		options.socketTimeout(socketTimeout);
    	}
    	if(manualmaps.get("threadsAllowedToBlockForConnectionMultiplier")!=null){
    		int threadsAllowedToBlockForConnectionMultiplier=Integer.valueOf(manualmaps.get("threadsAllowedToBlockForConnectionMultiplier"));
    		 options.threadsAllowedToBlockForConnectionMultiplier(threadsAllowedToBlockForConnectionMultiplier);// 线程队列数，如果连接线程排满了队列就会抛出“Out of semaphores to get db”错误。
    	       
    	}
        
       
        
        options.writeConcern(WriteConcern.SAFE);//
        
    	
       
    	
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    	
        return options
                .sslEnabled(false)
                .build();
    }

}
