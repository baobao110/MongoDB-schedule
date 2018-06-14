package com.hysm.controller.util;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import com.hysm.bean.Const;



/**
 * 权限处理
 */
public class Jurisdiction {

	/**
	 * 访问权限及初始化按钮权限(控制按钮的显示 增删改查)
	 * 
	 * @param menuUrl
	 *            菜单路径
	 * @return
	 */
	

	/**
	 * 获取当前登录的用户名
	 * 
	 * @return
	 */
	public static String getUsername() {
		return getSession().getAttribute(Const.SESSION_USERNAME).toString();
	}

	/**
	 * 获取当前按钮权限(增删改查)
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getHC() {
		return (Map<String, String>) getSession().getAttribute(
				getUsername() + Const.SESSION_QX);
	}

	/**
	 * shiro管理的session
	 * 
	 * @return
	 */
	public static Session getSession() {
		// Subject currentUser = SecurityUtils.getSubject();
		return SecurityUtils.getSubject().getSession();
	}
}
