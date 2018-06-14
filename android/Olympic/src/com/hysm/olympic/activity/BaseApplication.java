package com.hysm.olympic.activity;

import java.util.ArrayList;
import java.util.List;
  


import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import android.app.Activity;
import android.app.Application;

public class BaseApplication extends Application {

	private static String  APPID ="5486584a";//讯飞的应用
	
	@Override
	public void onCreate() {
		
		//创建应用
		SpeechUtility.createUtility(this, SpeechConstant.APPID +"="+APPID);
		 
		super.onCreate();
		instance = this;
	}
	
	/**打开的activity**/
    private List<Activity> activities = new ArrayList<Activity>();
    /**应用实例**/
    private static BaseApplication instance;
    /**
     *  获得实例
     * @return
     */
    public static BaseApplication getInstance(){
        return instance;
    }
    /**
     * 新建了一个activity
     * @param activity
     */
    public void addActivity(Activity activity){
        activities.add(activity);
    }
    /**
     *  结束指定的Activity
     * @param activity
     */
    public void finishActivity(Activity activity){
        if (activity!=null) {
            this.activities.remove(activity);
            activity.finish();
            activity = null;
        }
    }
    /**
     * 应用退出，结束所有的activity
     */
    public void exit(){
        for (Activity activity : activities) {
            if (activity!=null) {
                activity.finish();
            }
        }
        System.exit(0);
    }
    /**
     * 关闭Activity列表中的所有Activity*/
    public void finishActivity(){
        for (Activity activity : activities) {
            if (null != activity) {
                activity.finish();
            }
        }
        //杀死该应用进程  
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
