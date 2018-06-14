package com.hysm.game;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hysm.util.DateUtil;

/**
 * 定时任务
 * @author csc
 *
 */
@Component
public class TimerTask {
	
	
	/**
	 * 学校排行统计
	 */
	@Scheduled(cron="0 0/1 * * * ?")
	public void countPH(){
		
		
		
		
		System.out.println("排行统计:"+DateUtil.fromDate24H());
	}

}
