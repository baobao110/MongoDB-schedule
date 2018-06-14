package com.hysm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hysm.bean.DBbean;
import com.hysm.bean.SYS_code;
import com.hysm.db.Base_db;
import com.hysm.service.ISjinfoService;
import com.hysm.util.DateUtil;
import com.hysm.util.Ip_tool;
import com.hysm.util.MD5;
import com.hysm.util.StringUtil;
import com.hysm.util.mobile.AuthCodeGenerator;
import com.hysm.util.mobile.HYSM_TEMPCacher;
import com.hysm.util.mobile.Mobile_Bind;
import com.hysm.util.mobile.SendMsg;

@Service("sjinfoService")
public class ISjinfoServiceImpl implements ISjinfoService {

	@Autowired
	private Base_db base_db;
	
/*	
	*//**
	 * 用户注册
	 *//*
	@Override
	public String sendMobile(String mobile, String codestr,String ip) {
		String date = DateUtil.fromDateY();
		Document doc3 = new Document();
		doc3.put("phone", mobile);
		doc3.put("date", date); 
		
		int count = base_db.query_count(DBbean.T_SMS_LOG, doc3);
		
		if(count <= SYS_code.SMS_MAX){
		
		Mobile_Bind mb = (Mobile_Bind) HYSM_TEMPCacher.getCache(mobile
				+ "_MAUTH");
		
		if (mb != null) {
			long cTime = mb.getCtime();
			long nowTime = System.currentTimeMillis();
			long dtime = nowTime - cTime;
			if (dtime > 60000) {
				mb.setCheckSubmit(0);
			}
		}

		if (mb == null || mb.getCheckSubmit() == 0) {

			long ctime = System.currentTimeMillis();
			Mobile_Bind mb2 = new Mobile_Bind();
			String code = AuthCodeGenerator.randomAuthCode();
			mb2.setCode(code);
			// mb2.setOpenid(openid);
			mb2.setPhone(mobile);
			mb2.setCheckSubmit(1);
			mb2.setCtime(ctime);
			HYSM_TEMPCacher.cache(mobile + "_MAUTH", mb2);
			String codes = codestr + code ;
			
			
			
			System.out.println(codes);

			
		
			  //发送短信
			  SendMsg sm=new SendMsg(mobile,
			 codes, 8, ip);
			  Thread thread=new Thread(sm);
			  thread.start();
			 

			return SYS_code.SEND_STATE_SUCCESS;

		} else {

			HYSM_TEMPCacher.cache(mobile + "_MAUTH", mb);
			return SYS_code.SEND_STATE_UNSEND;

		}
		}else{
			//超过今日上限
			return SYS_code.SEND_STATE_EXTER;
			
		}
	}
	*//**
	 * 商家注册，获取短信验证码
	 *//*
	@Override
	public String send_reg_sms(HttpServletRequest request) {
		
		String phone = request.getParameter("phone");
		String ip = Ip_tool.get_ip(request); 
		String date = DateUtil.fromDateY();
		
		//查询是否有商家管理员
		Document doc = new Document();
		doc.put("phone", phone);
		
		int num = base_db.query_count(DBbean.T_PT_MANAGERS, doc); 
		String back_code ="404"; 
		if(num < 1){ 
			
			//查询短信今日获取次数 
			Document doc2 = new Document();
			doc2.put("phone", phone);
			doc2.put("date", date); 
			int count = base_db.query_count(DBbean.T_SMS_LOG, doc2);
			
			if(count <= SYS_code.SMS_MAX){
				
				String code =StringUtil.getRandomString(6);
				
				request.getSession().setAttribute(SYS_code.SJ_REG_PHONE, phone);
				request.getSession().setAttribute(SYS_code.SJ_REG_CODE, code);
				
				String context = "【商信科技】尊敬的用户，您的手机验证码是："+code+",请勿泄露给他人。";
				
				System.out.println(context); 
				//发送短信
				  SendMsg sm=new SendMsg(phone,
						  context, 8, ip);
							  Thread thread=new Thread(sm);
							  thread.start();
				
				
				
				back_code ="200";
				
			}else{
				back_code ="300";
			}
			
		}else{
			back_code ="100";
		}
		
		
		return back_code;
	}

	*//**
	 * 商家注册
	 *//*
	@Override
	public Document register_mer(HttpServletRequest request){
		
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		String code = request.getParameter("code");
		
		String id = StringUtil.get_sjid();
		
		String ip = Ip_tool.get_ip(request); 
		
		//判断session是否存在 
		String back_code ="404";
		
		String token ="";
		
		if(request.getSession().getAttribute(SYS_code.SJ_REG_PHONE) != null 
				&& request.getSession().getAttribute(SYS_code.SJ_REG_CODE)!= null){
			
			String sava_phone = request.getSession().getAttribute(SYS_code.SJ_REG_PHONE).toString();
			String sava_code = request.getSession().getAttribute(SYS_code.SJ_REG_CODE).toString();
			
			if(phone.equals(sava_phone) && code.equals(sava_code)){
				 
				//查询是否有商家管理员
				Document doc = new Document();
				doc.put("phone", phone); 
				int num = base_db.query_count(DBbean.T_PT_MANAGERS, doc);
				
				if(num < 1){ 
					
					request.getSession().removeAttribute(SYS_code.SJ_REG_PHONE);
					request.getSession().removeAttribute(SYS_code.SJ_REG_CODE);
					
					String md5pass = MD5.md5(phone+password);
					
					//新增商家
					Document merchants = new Document();
					merchants.put("_id", id);
					merchants.put("state", 1);
					merchants.put("name", name);
					merchants.put("phone", phone);
					merchants.put("ctime", DateUtil.fromDate24H());
					merchants.put("cdate", new Date()); 
					
					Map<String,Object> login_token = Ip_tool.create_token("电脑浏览器登录！", phone, ip);
					
					token = login_token.get("token").toString();
					
					List<Map<String,Object>> token_list = new ArrayList<Map<String,Object>>();
					token_list.add(login_token);
					
					merchants.put("token_list", token_list); 
					
					base_db.add_db_one(DBbean.T_MERS, merchants);
					 
					//新增商家管理员
					Document merchants_manager = new Document();
					merchants_manager.put("id", id);
					merchants_manager.put("phone", phone);
					merchants_manager.put("password", md5pass);
					merchants_manager.put("state", 1);
					merchants_manager.put("root", 1);
					base_db.add_db_one(DBbean.T_PT_MANAGERS, merchants_manager);
					 
					//新增登录记录
					Document login_log = new Document();
					login_log.put("id", id);
					login_log.put("phone", phone);
					login_log.put("ip", ip);
					login_log.put("password", password);
					login_log.put("type", 1);
					login_log.put("ctime", DateUtil.fromDate24H());
					
					base_db.add_db_one(DBbean.T_LOGIN_LOG, login_log);
					
					back_code ="200";
					
				}else{
					back_code ="300";
				} 
			}else{
				back_code ="100";
			} 
		}else{
			back_code ="404";
		}
		
		Document back = new Document();
		back.put("back_code", back_code);
		back.put("id", id); 
		back.put("token", token); 
		return back;
	}

	*//**
	 * 商家信息Cookie 校验
	 *//*
	@Override
	public Document check_mer(HttpServletRequest request) {
		
		String id = request.getParameter("id");
		String phone = request.getParameter("phone");
		String token = request.getParameter("token");
		
		String back_code ="404";
		 
		
		Document doc = new Document();
		doc.put("phone", phone);
		
		Document back = new Document();
		
		Document mer_manager = base_db.query_it_one(DBbean.T_PT_MANAGERS, doc);
		
		if(mer_manager!= null){  
			
			if(mer_manager.getString("id").equals(id)){
				
				Document doc2 = new Document();
				doc2.put("_id", id); 
				Document merchants =  base_db.query_it_one(DBbean.T_MERS, doc2);
				
				if(merchants.getInteger("state") != -1){
					List<Map<String,Object>> token_list = (List<Map<String,Object>>)merchants.get("token_list");
					
					//校验令牌
					boolean check_ok = false;
					int token_num = 0;
					for(int i=0;i<token_list.size();i++){ 
						if((token_list.get(i).get("token").toString()).equals(token)){
							token_num = i;
							check_ok = true; 
							break;
						} 
					}
					
					if(check_ok){
						
						//校验令牌是否过期 
						long now_milli = System.currentTimeMillis();
						
						long time_mille = Long.valueOf(token_list.get(token_num).get("time_milli").toString());
						
						if((now_milli - time_mille) > SYS_code.MER_TOKEN_MILLI){
							
							//令牌 过期，重置令牌
							
							String ip = Ip_tool.get_ip(request); 
							
							Map<String,Object> login_token = Ip_tool.create_token("电脑浏览器登录！", phone,ip); 
							token = login_token.get("token").toString();
							
							token_list.set(token_num, login_token);
							
							merchants.put("token_list", token_list); 
							
							back.put("token", token);
							
							base_db.replace_db_byid(DBbean.T_MERS, merchants, id);
							
							back_code ="250"; 
						}else{
							back_code ="200";
						}  
				}else{
					back_code ="500"; //该商家被封号
				} 
				}else{
					back_code ="100";
				}
				 
			}else{
				back_code ="300";
			} 
			 
		}else{
			back_code ="404";
		}
		
		back.put("back_code", back_code);
		
		return back;
	}
	
	*//**
	 * 商家资料完善
	 *//*
	@Override
	public Document save_mer(HttpServletRequest request){
		
		String name = request.getParameter("name");
		String shortname = request.getParameter("shortname");
		String industry_arr = request.getParameter("industry_arr");
		String info = request.getParameter("info");
		String logo = request.getParameter("logo");
		String customer_arr = request.getParameter("customer_arr");
		String keyword = request.getParameter("keyword");
		
		String id = request.getParameter("id");
		String phone = request.getParameter("phone");
		 
		
		String back_code ="404";
		 
		
		Document doc = new Document();
		doc.put("phone", phone);
		
		Document back = new Document();
		
		Document mer_manager = base_db.query_it_one(DBbean.T_PT_MANAGERS, doc);
		
		if(mer_manager!= null){
			 
			if(mer_manager.getString("id").equals(id)){
				 
				Document doc2 = new Document();
				doc2.put("_id", id); 
				Document merchants =  base_db.query_it_one(DBbean.T_MERS, doc2);
				
				merchants.put("name", name);
				if(!shortname.equals("")){
					merchants.put("shortname", shortname);
				}
				if(!logo.equals("")){
					merchants.put("logo", logo);
				} 
				if(!info.equals("")){
					merchants.put("info", info);
				} 
				 
				if(!keyword.equals("")){ 
					keyword = keyword.replaceAll("；", ";"); 
					String[] key_arr = keyword.split(";");
					
					List<String> key_list = new ArrayList<String>();
					for(int i=0;i<key_arr.length;i++){
						key_list.add(key_arr[i]);
					}
					
					merchants.put("keyword", key_list);
				}
				
				try {
					JSONArray c_arr = new JSONArray(customer_arr); 
					if(c_arr.length()>0){ 
						List<Integer> c_list = new ArrayList<Integer>();
						for(int i=0;i<c_arr.length();i++){
							c_list.add(c_arr.getInt(i));
						} 
						merchants.put("customer_arr", c_list);
					}
					
					
					JSONArray i_arr = new JSONArray(industry_arr);
					
					if(i_arr.length()>0){
						List<Integer> industry = new ArrayList<Integer>();
						List<Map<String,Object>> i_list = new ArrayList<Map<String,Object>>();
						for(int i=0;i<i_arr.length();i++){
							
							industry.add(i_arr.getJSONObject(i).getInt("id")); 
							
							Map<String,Object> map = new HashMap<String, Object>(); 
							map.put("id", i_arr.getJSONObject(i).getInt("id")); 
							map.put("name", i_arr.getJSONObject(i).getString("name"));
							
							i_list.add(map);
						}
						
						merchants.put("industry", industry);
						merchants.put("industry_arr", i_list);
					}
					
					
				} catch (JSONException e){ 
					e.printStackTrace();
				}
				
				
				base_db.replace_db_byid(DBbean.T_MERS, merchants, id);
				
				back_code ="200"; 
				 
			}else{
				back_code ="100";
			} 
		}else{
			back_code ="404";
		}
		
		back.put("back_code", back_code); 
		return back;
	}

	*//**
	 * 商家信息获取
	 *//*
	@Override
	public Document get_mer(HttpServletRequest request) {
		
		String id = request.getParameter("id");
		String phone = request.getParameter("phone");
		
		String back_code ="404";
		 
		
		Document doc = new Document();
		doc.put("phone", phone);
		
		Document back = new Document();
		
		Document mer_manager = base_db.query_it_one(DBbean.T_PT_MANAGERS, doc);
		
		if(mer_manager!= null){
			 
			if(mer_manager.getString("id").equals(id)){
				 
				Document doc2 = new Document();
				doc2.put("_id", id); 
				Document merchants =  base_db.query_it_one(DBbean.T_MERS, doc2);
				
				back.put("merchants", merchants); 
				
				back_code ="200"; 
				
			}else{
				back_code ="100";
			} 
		}else{
			back_code ="404";
		}
			
		back.put("back_code", back_code); 
		return back;
	}

	*//**
	 * 登陆获取短信验证码
	 *//*
	@Override
	public String send_login_sms(HttpServletRequest request){
		
		String phone = request.getParameter("phone");
		String ip = Ip_tool.get_ip(request); 
		String date = DateUtil.fromDateY();
		
		//查询是否有商家管理员
		Document doc = new Document();
		doc.put("phone", phone);
		
		Document mer_manager = base_db.query_it_one(DBbean.T_PT_MANAGERS, doc);
		String back_code ="404"; 
		
		if(mer_manager != null){  
			String id = mer_manager.getString("id");
			
			Document doc2 = new Document();
			doc2.put("_id", id); 
			Document merchants =  base_db.query_it_one(DBbean.T_MERS, doc2);
			
			if(merchants != null){ 
				if(merchants.getInteger("state") != -1){
					
					//查询短信今日获取次数 
					Document doc3 = new Document();
					doc3.put("phone", phone);
					doc3.put("date", date); 
					int count = base_db.query_count(DBbean.T_SMS_LOG, doc3);
					
					if(count <= SYS_code.SMS_MAX){
						String code =StringUtil.getRandomString(6);
						
						request.getSession().setAttribute(SYS_code.SJ_LOGIN_PHONE, phone);
						request.getSession().setAttribute(SYS_code.SJ_LOGIN_CODE, code);
						
						String context = "【商信科技】尊敬的用户，您的手机验证码是"+code+",请勿泄露给他人。";
						
						System.out.println(context); 
						//发送短信
						  SendMsg sm=new SendMsg(phone,
								  context, 8, ip);
									  Thread thread=new Thread(sm);
									  thread.start();
						
						
						back_code ="200";
						
					}else{
						back_code ="300";
					}
					
				}else{
					back_code ="500"; 
				} 
			}else{
				back_code ="400"; 
			} 
		}else{
			back_code ="404"; 
		}
		 
		return back_code;
	}

	*//**
	 * 密码登陆
	 *//*
	@Override
	public Document login_pass(HttpServletRequest request) {
		
		
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		
		String ip = Ip_tool.get_ip(request); 
		
		//查询是否有商家管理员
		Document doc = new Document();
		doc.put("phone", phone);
		
		Document mer_manager = base_db.query_it_one(DBbean.T_PT_MANAGERS, doc);
		String back_code ="404";
		Document back = new Document();
		
		if(mer_manager != null){  
			
			String md5pass = MD5.md5(phone+password);
			
			if((mer_manager.getString("password")).equals(md5pass)){
				
				String id = mer_manager.getString("id");
				
				Document doc2 = new Document();
				doc2.put("_id", id); 
				Document merchants =  base_db.query_it_one(DBbean.T_MERS, doc2);
				
				if(merchants != null){ 
					if(merchants.getInteger("state") != -1){ 
						
						//用户登录成功，重置令牌/或者校验令牌数目是否超出 
						List<Map<String,Object>> token_list = (List<Map<String,Object>>)merchants.get("token_list");
						  
						Map<String,Object> login_token = Ip_tool.create_token("电脑浏览器登录！", phone,ip); 
						String token = login_token.get("token").toString();
						  
						//判断令牌 超出，不超出新增 
						if(token_list.size() < SYS_code.MER_LOGIN_MAX){ 
							token_list.add(login_token); 
						}else if(token_list.size() > SYS_code.MER_LOGIN_MAX){  
							//令牌超出，将最早的去掉 
							int cha=token_list.size()-SYS_code.MER_LOGIN_MAX;
							List<Map<String,Object>> removes=new ArrayList<Map<String,Object>>();
							for(int i=0;i<cha;i++){
								removes.add(token_list.get(i));
							}
							
							token_list.removeAll(removes);
							
							token_list.set(0, login_token); 
						}else{
							
							token_list.set(0, login_token); 
						}
						System.out.println(login_token.toString());
						merchants.put("token_list", token_list);  
						base_db.replace_db_byid(DBbean.T_MERS, merchants, id);
						
						//新增登录记录
						Document login_log = new Document();
						login_log.put("id", id);
						login_log.put("phone", phone);
						login_log.put("ip", ip);
						login_log.put("password", password);
						login_log.put("type", 1);
						login_log.put("ctime", DateUtil.fromDate24H());
						
						base_db.add_db_one(DBbean.T_LOGIN_LOG, login_log);
						
						back.put("token", token);
						back.put("id", id); 
						back.put("phone", phone);
						
						back_code ="200"; 
						
					}else{
						back_code ="500";
					}
				}else{
					back_code ="300";
				}
			}else{
				back_code ="100";
			} 
		}else{
			back_code ="404";
		}
		
		back.put("back_code", back_code); 
		return back;
	}

	*//**
	 * 短信验证码登陆
	 *//*
	@Override
	public Document login_code(HttpServletRequest request) {
		
		String phone = request.getParameter("phone");
		String code = request.getParameter("code");
		
		String ip = Ip_tool.get_ip(request); 
		
		String back_code ="404";
		Document back = new Document();
		
		if(request.getSession().getAttribute(SYS_code.SJ_LOGIN_PHONE) != null && request.getSession().getAttribute(SYS_code.SJ_LOGIN_CODE) != null){
			
			String old_phone = request.getSession().getAttribute(SYS_code.SJ_LOGIN_PHONE).toString();
			String old_code = request.getSession().getAttribute(SYS_code.SJ_LOGIN_CODE).toString();
			
			if(phone.equals(old_phone) && code.equals(old_code)){
				
				//查询是否有商家管理员
				Document doc = new Document();
				doc.put("phone", phone);
				
				Document mer_manager = base_db.query_it_one(DBbean.T_PT_MANAGERS, doc);
				
				if(mer_manager != null){
					
					String id = mer_manager.getString("id");
					
					Document doc2 = new Document();
					doc2.put("_id", id); 
					Document merchants =  base_db.query_it_one(DBbean.T_MERS, doc2);
					
					if(merchants != null){ 
						if(merchants.getInteger("state") != -1){ 
							
							
							//用户登录成功，重置令牌/或者校验令牌数目是否超出 
							List<Map<String,Object>> token_list = (List<Map<String,Object>>)merchants.get("token_list");
							 
							Map<String,Object> login_token = Ip_tool.create_token("电脑浏览器登录！", phone,ip); 
							String token = login_token.get("token").toString();
							  
							//判断令牌 超出，不超出新增 
							if(token_list.size() < SYS_code.MER_LOGIN_MAX){ 
								token_list.add(login_token); 
							}else if(token_list.size() > SYS_code.MER_LOGIN_MAX){  
								//令牌超出，将最早的去掉 
								int cha=token_list.size()-SYS_code.MER_LOGIN_MAX;
								List<Map<String,Object>> removes=new ArrayList<Map<String,Object>>();
								for(int i=0;i<cha;i++){
									removes.add(token_list.get(i));
								} 
								token_list.removeAll(removes);
								
								token_list.set(0, login_token); 
							}else{
								 
								token_list.set(0, login_token); 
							}
							
							
							System.out.println(login_token.toString());
							merchants.put("token_list", token_list);  
							base_db.replace_db_byid(DBbean.T_MERS, merchants, id);
							
							//新增登录记录
							Document login_log = new Document();
							login_log.put("id", id);
							login_log.put("phone", phone);
							login_log.put("ip", ip);
							login_log.put("code", code);
							login_log.put("type", 1);
							login_log.put("ctime", DateUtil.fromDate24H());
							
							base_db.add_db_one(DBbean.T_LOGIN_LOG, login_log);
							
							back.put("token", token);
							back.put("id", id); 
							back.put("phone", phone);
							
							back_code ="200"; 
							
						}else{
							back_code ="500";
						}
					}else{
						back_code ="300";
					}
					
				}else{
					back_code ="404";
				} 
			}else{
				back_code ="150";
			} 
		}else{
			back_code ="100";
		}
		
		back.put("back_code", back_code); 
		return back;
	}
	@Override
	public int queryMerBalance(String id) {
		Document mer=base_db.queryMer(id);
		
		return  mer.getInteger("balance", 0);
	}
	
	
	 */
}
