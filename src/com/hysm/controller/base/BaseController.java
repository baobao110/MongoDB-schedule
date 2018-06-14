package com.hysm.controller.base;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.hysm.bean.Const;
import com.hysm.util.CookieDo;
import com.hysm.util.Logger;
import com.hysm.util.PageData;
import com.hysm.util.UuidUtil;

/**
 * @author FH Q313596790 修改时间：2015、12、11
 */
public class BaseController {

	protected Logger logger = Logger.getLogger(this.getClass());

	private static final long serialVersionUID = 6357869213649815390L;

	/**
	 * new PageData对象
	 * 
	 * @return
	 */
	public PageData getPageData() {
		return new PageData(this.getRequest());
	}

	/**
	 * 得到ModelAndView
	 * 
	 * @return
	 */
	public ModelAndView getModelAndView() {
		return new ModelAndView();
	}

	/**
	 * 得到request对象
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request;
	}

	public  void logBefore(Logger logger, String interfaceName) {
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}

	public  void logAfter(Logger logger) {
		logger.info("end");
		logger.info("");
	}
	
	/**
	 * 得到32位的uuid
	 * 
	 * @return
	 */
	public String get32UUID() {
		return UuidUtil.get32UUID();
	}

	


	protected HttpSession getSession() {
		HttpSession session = null;
		try {
			session = getRequest().getSession();
		} catch (Exception e) {
		}
		return session;
	}

	/**
	 * @param response
	 * @param
	 */
	protected void printerJson(HttpServletResponse response, String str) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(str);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	protected void toPrinterJson(HttpServletResponse response, int res_type)
			throws Exception {
		try {
			JSONObject jsonObj = new JSONObject();
			jsonObj.accumulate("RES_TYPE", res_type);
			response.setCharacterEncoding("utf-8");
			String json = JSON.toJSONStringWithDateFormat(jsonObj,
					"yyyy-MM-dd HH:mm:ss");
			response.setContentType("text/html;charset=utf-8");
			this.printerJson(response, json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void toPrinterJson(HttpServletResponse response, int res_type,
			String res_msg) throws Exception {
		try {
			JSONObject jsonObj = new JSONObject();
			jsonObj.accumulate("RES_TYPE", res_type);
			jsonObj.accumulate("RES_MSG", res_msg);
			response.setCharacterEncoding("utf-8");
			String json = JSON.toJSONStringWithDateFormat(jsonObj,
					"yyyy-MM-dd HH:mm:ss");
			response.setContentType("text/html;charset=utf-8");
			this.printerJson(response, json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	


	/**
	 * Description 向前台发送数据
	 * @author
	 * @return 
	 */
	public void sendMessage(String str, HttpServletResponse response) {
		response.setContentType("text/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(str);
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			out.close();
			out = null;

		}

	}
	
	  /**
		 * @discription 向前台发送json数据
		 * @author
		 */
	    protected void sendjson(Object obj, HttpServletResponse response) {
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");

			JSONArray json = JSONArray.fromObject(obj);
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.print(json);
			} catch (IOException e) {

				e.printStackTrace();
			} finally {
				out.close();
				out = null;
			}
		}
	    
	    protected void sendJSON2(Object obj, HttpServletResponse response) {
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");

			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.print(obj);
			} catch (IOException e) {

				e.printStackTrace();
			} finally {
				out.close();
				out = null;
			}
		}
	    
	    
	   /**
	    * jsonp
	     * 解决跨域
	    * @param obj
	    * @param response
	    * @param fun
	    * @param type1 单个2数组0字符
	    */
	    protected void sendJSONP(Object obj, HttpServletResponse response,String fun,int type) {
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = null;
			if(type==1){
				JSONObject json = JSONObject.fromObject(obj);
				try {
					out = response.getWriter();
					out.print(fun+"("+json.toString()+");");
				} catch (IOException e) {

					e.printStackTrace();
				} finally {
					out.close();
					out = null;
				}
			}else if(type==2){
				JSONArray json=JSONArray.fromObject(obj);
				try {
					out = response.getWriter();
					out.print(fun+"("+json.toString()+");");
				} catch (IOException e) {

					e.printStackTrace();
				} finally {
					out.close();
					out = null;
				}
			}else{
				
				try {
					out = response.getWriter();
					out.print(fun+"("+obj.toString()+");");
				} catch (IOException e) {

					e.printStackTrace();
				} finally {
					out.close();
					out = null;
				}
			}
			
		
			
		}
	   
	    
	    protected void sendToWeb(String msg, String socketId) {
	        /*try {
	            new MyWebSocket().hello(msg, socketId);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }*/
	    }
	    
	    
	    
	    /**
	     * 获取openid
	     */
	   public String querycookieOpenid(HttpServletRequest request){
		   
		   String openid=null;
			openid=CookieDo.getValue(request, 	Const.LOGINKEY1);
			if(openid!=null){
				
				return openid;
			}else{
				
			
				
				return "1";
			}
			
	    	
	   }
	    
}
