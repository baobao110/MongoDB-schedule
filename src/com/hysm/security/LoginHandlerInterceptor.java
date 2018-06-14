package com.hysm.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hysm.bean.Const;


/**
 * 登录过滤，权限验证
 * 
 * @author baobao
 * 
 * Mar 2, 2016 1:26:46 PM
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		String path = request.getServletPath();
		if (path.matches(Const.NO_INTERCEPTOR_PATH)) {
			return true;
		} else {
			Document user = (Document) Jurisdiction.getSession().getAttribute(
					Const.SESSION_Manager);
			if (user == null) {
				// 登陆过滤
				response.sendRedirect(request.getContextPath() + Const.LOGIN);
				return false;
			} else
				return true;
		}
	}

}
