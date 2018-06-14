package com.hysm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.bson.Document;

public interface HistoryService {
	List<Document>  query_db_all(String db_name,Document doc);
	
	Document ajaxJdList(HttpServletRequest req);
}
