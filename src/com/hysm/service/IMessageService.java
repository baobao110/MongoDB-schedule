package com.hysm.service;

import javax.servlet.http.HttpServletRequest;




import org.bson.Document;

import com.alibaba.fastjson.JSONArray;
import com.hysm.game.GameVO;

public interface IMessageService {

	int saveMessage(Document mess);

	Document ajaxMessageList(HttpServletRequest req);

	Document queryMessById(String id);

	
	/**
	 * 生成消息详情页，获取消息详情
	 * @param id
	 * @return
	 */
	Document queryMessDetailById(String id);

	
	/**
	 * 目录
	 * @return
	 */
	JSONArray ajaxKcTitleList();

	void updateMessState(String id, int state);

	
	int saveTM(Document mess);

	
	/**
	 * 题目
	 * @param req
	 * @return
	 */
	Document ajaxTmList(HttpServletRequest req);

	
	/**
	 * 课程内容
	 * @param kcid
	 * @return
	 */
	Document ajaxOneKcInfo(String kcid);

	
	
	JSONArray ajaxTestQuestions(int i);

	void updateTMState(String id, int state);

	void deleteTm(String id);

	Document queryTmByid(String id);

	int saveJD(Document mess);

	Document ajaxJdList(HttpServletRequest req);

	Document ajaxOneJDByid(String id);

	void updateJdState(String id, int state);

	Document countNum();

	//竞答持久化
	void addJDres(GameVO game);
	



}
