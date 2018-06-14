package com.hysm.olympic.tool;
 
import com.hysm.olympic.activity.BaseApplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface; 
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Net_tool {

	//判断网络
    public static int getNetType(Context context) {
        //设置一个无网络状态值
        int noState = -1;
        //获取系统网络管理类
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //得到NetWorkInfo
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        //如果activeNetworkInfo为空,说明没网络
        if (activeNetworkInfo == null) {
            return noState;
        }
        //否则就获取网路类型
        int type = activeNetworkInfo.getType();
        if (type == ConnectivityManager.TYPE_WIFI) {//网络类型是WiFi
            return 1;
        } else if (type == connectivityManager.TYPE_MOBILE) {//网络类型是手机网络
            return 0;
        }

        return noState;
    }
    
    
    public static void checkNet(Context context){
    	
    	int nettype = getNetType(context);
    	
    	if(nettype == -1){
    		//弹出无网络链接 
    		AlertDialog alert = new AlertDialog.Builder(context)
    		.setTitle("提示")//设置对话框的标题
            .setMessage("应用需要网络才可以使用，请先开通网络或wifi。")//设置对话框的内容
            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) { 
                    dialog.dismiss();
                    BaseApplication.getInstance().exit();
                }
            }).create();
    		
    		alert.show();
    	} 
    }

}
