package com.hysm.util.mobile;

import java.util.Hashtable;

/*
 *  说明，由于微信的cookie与sessionID可能不支持
 *  作为第三方，采取统一认证的方式来给出处理。
 * */

public class HYSM_TEMPCacher 
{
	
	public static Hashtable<String,Object> cache = new Hashtable<String,Object>();
	
	public static String cache(Object obj)
	{
		String key = randomKey();
		cache.put(key, obj);
		return key;
	}
	
	public static void cache(String key,Object obj)
	{
		cache.put(key, obj);
	}
	
	public static String randomKey()
	{
		return Long.toString(System.currentTimeMillis(), 64);
	}
	
	public static Object getCache(String key)
	{
		return cache.remove(key);
	} 

}
