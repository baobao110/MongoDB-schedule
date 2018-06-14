package com.hysm.olympic.activity;

import java.util.HashMap;
import java.util.Map;
 
import org.json.JSONException;
import org.json.JSONObject;

import com.hysm.olympic.R;
import com.hysm.olympic.bean.PublicData;
import com.hysm.olympic.http.HttpTool; 
import com.hysm.olympic.tool.Net_tool;
import com.hysm.olympic.tool.Word_tool;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout; 

public class SetAct extends Activity {

	private LinearLayout back_view;  
	//private TextView school_text; 
	private EditText pass1_edit,pass2_edit,robitid_edit,school_edit;
	private Button save_button;
	
	private AlertDialog msg_alert;
	
	private String rid,robotid,schoolcode,schoolname;
	
	private SharedPreferences preferences;
	
	private Handler handler;
	
	private static final int Load_ok = 100;
	private static final int Load_err = 101;
	
	
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		BaseApplication.getInstance().addActivity(this); // 添加Activity到堆栈
		setContentView(R.layout.set);
		
		init_data();
		init_view();
		
		init_handler();
		
		init_listener();
		
		
	}
 

	private void init_data() {
		preferences = getSharedPreferences(PublicData.RobotShare, Context.MODE_PRIVATE);
	}

	private void init_view() {
		back_view = (LinearLayout)findViewById(R.id.set_back_view);
		robitid_edit = (EditText)findViewById(R.id.set_robitid_edit);
		school_edit = (EditText)findViewById(R.id.set_school_edit);
		pass1_edit = (EditText)findViewById(R.id.set_pass_edit);
		pass2_edit = (EditText)findViewById(R.id.set_pass2_edit);
		
		save_button = (Button)findViewById(R.id.set_save_button);
		
		Net_tool.checkNet(SetAct.this);
		
		if(preferences!= null){
			rid = preferences.getString("rid", "");
			robotid = preferences.getString("robotid", "");
			schoolcode = preferences.getString("schoolcode", "");
			schoolname = preferences.getString("schoolname", "");
			
			robitid_edit.setText(robotid);
			school_edit.setText(schoolcode);
		}
	}
	
	
	private void init_handler(){
		 
		handler = new Handler(){

			@Override
			public void handleMessage(Message msg){ 
				super.handleMessage(msg);
				
				int what = msg.what;
				
				switch (what) {
				case Load_ok:
					
					SharedPreferences.Editor editor = preferences.edit();
					editor.putString("rid", rid);
					editor.putString("robotid", robotid);
					editor.putString("schoolcode", schoolcode);
					editor.putString("schoolname", schoolname);
					editor.commit();
					
					finish();//返回
					
					break; 
				case Load_err:
					
					String err = msg.obj.toString();
					show_msg(err); 
					break;
				}
				
			}
			
		};
	}

	private void init_listener() {
		back_view.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(SetAct.this, MainActivity.class);
				startActivity(intent);
			}
		});
		
		save_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				robotid = robitid_edit.getText().toString();
				schoolcode = school_edit.getText().toString();
				robotid= robotid.trim();
				schoolcode = schoolcode.trim();
				
				if(robotid.equals("")){
					show_msg("请输入机器人编号!"); 
				}else{
					if(Word_tool.isNumeric(robotid)==false){
						show_msg("机器人编号必须为数字!");  
					}else{
						if(schoolcode.equals("")){
							show_msg("请输入学校编号!"); 
						}else{
							if(Word_tool.isNumeric(schoolcode)==false){
								show_msg("学校编号必须为数字!");  
							}else{
								
								//开始线程，请求服务器
								Thread msgThread = new Thread(msgrunnable);
								msgThread.start();
							}
						}
					}
				}
				  
			}
		});
	}
	 
	
	protected void show_msg(String msg) {
		  
		msg_alert = new AlertDialog.Builder(SetAct.this)
		.setTitle("提示")//设置对话框的标题
        .setMessage(msg)//设置对话框的内容
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                
                dialog.dismiss();
            }
        }).create();
		
		msg_alert.show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//点击按键事件 
		if(keyCode == KeyEvent.KEYCODE_BACK){ 
			BaseApplication.getInstance().exit();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	Runnable msgrunnable = new Runnable() { 
		@Override
		public void run() {
			Map<String,String> params = new HashMap<String,String>();
			params.put("rid", rid);
			params.put("robotid", robotid);
			params.put("schoolcode", schoolcode);
			
			String result = HttpTool.send_Post(HttpTool.Service_url+HttpTool.Set_url, params); 
			try {
				JSONObject json = new JSONObject(result);
				
				if(json.getString("backcode").equals("200")){
					
					rid = json.getString("rid");
					robotid = json.getString("robotid");
					schoolcode = json.getString("schoolcode");
					schoolname = json.getString("schoolname");
					
					Message msg = handler.obtainMessage();
					msg.what = Load_ok;
					handler.sendMessage(msg);
					
				}else{
					Message msg = handler.obtainMessage();
					msg.what = Load_err;
					msg.obj = json.getString("backmsg");
					handler.sendMessage(msg);
				}
				
			}catch(JSONException e){ 
				e.printStackTrace();
			}
		}
	};


	
}
