package com.hysm.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hysm.bean.DBbean;
import com.hysm.controller.base.BaseController;
import com.hysm.dao.ajaxDAO;
import com.hysm.db.SchoolDB;
import com.hysm.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController{
	
	@Autowired
	private AdminService admin;
	
	@Autowired
	private SchoolDB schoolDB;
	
	@RequestMapping(value = "/toPage")
	public String toPage(){
		return "/admin/adminShow.html";
		
	}
	
	@RequestMapping(value = "/todelete")
	public String todelete(){
		return "/admin/del.html";
	}
	
	
	/*
	 * 这里是显示管理员信息的情况，这里需要注意的一点就是JSON的格式问题
	 * 这里因为要在前端以表格的形式展示，我们如果不在这里做一下JSON的转化
	 * 在前端会显示数据异常的情况，这里如果刚开始用这个框架如果熟悉这套前端的
	 * 框架很容易会出现这个问题
	 */
	
	@RequestMapping(value = "/admin")
	@ResponseBody
	public ajaxDAO admin(String uuid,HttpSession session){
		String UUID=session.getAttribute("uuid").toString();
		if(!UUID.equals(uuid)){
			return ajaxDAO.failure();
		}
		
		Document doc=new Document();
		doc.put("status", "管理员");
		List<Document> list=admin.getALL(DBbean.T_user,doc);
		return ajaxDAO.success(list);
		
	}
	
	
	@RequestMapping(value = "/delete")
	@ResponseBody
	public ajaxDAO delete(String uuid,String name,HttpSession session){
		String UUID=session.getAttribute("uuid").toString();
		if(!UUID.equals(uuid)){
			return ajaxDAO.failure();
		}
		if("admin".equalsIgnoreCase(name))
		{
			return ajaxDAO.failure("超级用户无权删除");
		}
		Document doc=admin.select_ByName(DBbean.T_user, name);
		if(null==doc){
			/*System.out.println("空值");*/
			return ajaxDAO.failure("删除失败");
		}
		else{
			try {
				doc.put("status","用户");
				Object _id=doc.get("_id");
				admin.update(DBbean.T_user, doc, _id);
				/*System.out.println("44444");*/
				return ajaxDAO.success();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return ajaxDAO.failure("删除失败");
			}
		}
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/add")
	@ResponseBody
	public ajaxDAO add(String name,String phone,String uuid,HttpSession session){
		String UUID=session.getAttribute("uuid").toString();
		if(!UUID.equals(uuid)){
			return ajaxDAO.failure();
		}
		Document doc=admin.select_ByName(DBbean.T_user, name);
		if(null!=doc){
			if(doc.getString("status").equals("管理员")){
				return ajaxDAO.failure("该用户已经拥有管理员权限");
			}
			Object _id=doc.get("_id");
			doc.put("status","管理员");
			admin.update(DBbean.T_user, doc, _id);
			return ajaxDAO.success();
			}
			else{
				try {
					//docu.put("name", doc.getString("name"));
					doc=new Document();
					doc.put("name", name);
					doc.put("phone", phone);
					doc.put("status", "管理员");
					admin.add_db_one(DBbean.T_user, doc);;
					/*System.out.println("44444");*/
					return ajaxDAO.success();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return ajaxDAO.failure("添加失败");
					}
			}
}
	
	@RequestMapping(value = "/flag")
	@ResponseBody
	public ajaxDAO flag(String uuid,HttpSession session){
	try{
		String UUID=(String)session.getAttribute("uuid");
		if(UUID.equals(uuid)){ 
			return ajaxDAO.success();
		}
		else{
			return ajaxDAO.failure();
			}
		}catch(Exception e){
			return ajaxDAO.failure();
		}
	}
	
	@RequestMapping(value = "/register")
	@ResponseBody
	public ajaxDAO register(String name,String phone,String password,String password2){
		if(!password.equals(password2)){
			return ajaxDAO.failure("密码输入不正确，请重新输入");
		}
		Document doc=null;
		doc=admin.select_ByName(DBbean.T_user, name);
		if(null!=doc){
			return ajaxDAO.failure("用户名已存在");
		}
		doc=null;
		doc=admin.select_ByPhone(DBbean.T_user, phone);
		if(null!=doc){
			return ajaxDAO.failure("该手机号已经注册");
		}
		doc=null;
		String pwd=DigestUtils.md5Hex(password);
		doc=new Document();
		doc.put("name", name);
		doc.put("password", pwd);
		doc.put("phone",phone);
		doc.put("status", "用户");
		try{
			admin.insert("c_user", doc);
			return ajaxDAO.success();
			
		}catch(Exception e){
			return ajaxDAO.failure("注册失败");
		}
		
	}
	
	
	@RequestMapping(value = "/admin_info")
	@ResponseBody
	public ajaxDAO admin_info(String uuid,String name,HttpSession session){
		String UUID=session.getAttribute("uuid").toString();
		if(!UUID.equals(uuid)){
			return ajaxDAO.failure();
		}
		else{
			Document doc=admin.select_ByName(DBbean.T_user, name);
			if(null==doc){
				return ajaxDAO.failure();
			}
			else{
				return ajaxDAO.success(doc);
			}
		}
		
	}
	
	@RequestMapping(value = "/userinfo")
	@ResponseBody
	public void userinfo(String uuid,String name,HttpSession session,HttpServletResponse response){
		 
		Document doc=admin.select_ByName(DBbean.T_user, name);
		
		doc.put("user_id", doc.get("_id").toString());
		
		sendJSON2(doc.toJson(), response);
			 
		
	}
	
	
	@RequestMapping(value = "/save_user")
	@ResponseBody
	public void save_user(HttpServletRequest request,HttpServletResponse response){
		 
		 
		String user_id = request.getParameter("user_id");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		
		String back = "100";
		
		if(user_id.equals("")){
			
			//新增
			
			Document doc  = new Document();
			doc.put("name", name);
			
			int count = schoolDB.query_count(DBbean.T_user, doc);
			
			if(count < 1){
				
				Document user= new Document();
				user.put("name", name);
				user.put("phone", phone);
				user.put("password", DigestUtils.md5Hex(password));
				user.put("status", "管理员");
				
				schoolDB.add_db_one(DBbean.T_user, user);
				
				back = "200";
			}
			
		}else{
			//修改
			
			Document user = admin.select_ByName(DBbean.T_user, name);
			user.put("phone", phone);
			user.put("password", DigestUtils.md5Hex(password));
			user.put("status", "管理员");
			
			Object _id=user.get("_id");
			admin.update(DBbean.T_user, user, _id);
			
			back = "200";
			
		}
			 
		sendMessage(back, response);
	}
	
	
	
	
	@RequestMapping(value = "/save")
	@ResponseBody
	public ajaxDAO save(String name,String phone,String password,String password2){
		if(!password.equals(password2)){
			return ajaxDAO.failure();
		}
		Document doc=admin.select_ByName(DBbean.T_user, name);
		if(null==doc){
			return ajaxDAO.failure();
		}
		else{
			doc.put("phone", phone);
			doc.put("password",DigestUtils.md5Hex(password));
		try{
			Object _id=doc.get("_id");
			admin.update(DBbean.T_user, doc, _id);
			return ajaxDAO.success();
		}
		catch(Exception e){
			return ajaxDAO.failure();
		}
			
		}
	}
	
	@RequestMapping(value = "/search")
	@ResponseBody
	public ajaxDAO Search(String name,String uuid,HttpSession session){
		String UUID=session.getAttribute("uuid").toString();
		if(!UUID.equals(uuid)){
			return ajaxDAO.failure();
		}
		else{
			Document doc=new Document();
			doc.put("name", name);
			doc.put("status","管理员");
			List<Document> list=admin.getALL(DBbean.T_user,doc);
			return ajaxDAO.success(list);
		}
	}
	
	@RequestMapping(value = "/auto")
	@ResponseBody
	public ajaxDAO auto(String name,String uuid,HttpSession session){
		String UUID=session.getAttribute("uuid").toString();
		if(!UUID.equals(uuid)){
			return ajaxDAO.failure();
		}
		else{
			try{
				Document doc=admin.select_ByName(DBbean.T_user, name);
				doc.put("status", "管理员");
				Object _id=doc.get("_id");
				admin.update(DBbean.T_user, doc,_id);
				return ajaxDAO.success();
			}catch(Exception e){
				return ajaxDAO.failure("操作有误");
			}
		}
	}
}
