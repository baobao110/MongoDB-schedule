package com.hysm.olympic.activity;

import com.hysm.olympic.R;
import com.hysm.olympic.bean.PublicData;
import com.hysm.olympic.tool.Json_tool;
import com.hysm.olympic.tool.Net_tool;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class RegisterAct extends Activity {

	private LinearLayout back_view; 
	
	private EditText class_edit,name_edit,studentid_edit,parent_edit,phone_edit;
	
	private Button reg_button;
	
	private String schoolid,classname,name,studentid,parent,phone;
	
	private SharedPreferences preferences;
	
	private AlertDialog msg_alert,set_alert;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState); 
		BaseApplication.getInstance().addActivity(this); // 添加Activity到堆栈
		setContentView(R.layout.register);
		 
	}
	
	@Override
	protected void onResume(){ 
		super.onResume();
		
		init_data();
		init_view(); 
		init_listener();
		 
	}

	private void init_data() { 
		preferences = getSharedPreferences(PublicData.RobotShare, Context.MODE_PRIVATE); 
		schoolid = preferences.getString("schoolid", ""); 
		
		if(schoolid.equals("")){
			show_set();
		}
	}

	

	private void init_view() { 
		
		back_view = (LinearLayout)findViewById(R.id.reg_back_view);
		
		class_edit = (EditText)findViewById(R.id.reg_class_edit);
		name_edit = (EditText)findViewById(R.id.reg_name_edit);
		studentid_edit = (EditText)findViewById(R.id.reg_studenid_edit);
		parent_edit = (EditText)findViewById(R.id.reg_parent_edit);
		phone_edit = (EditText)findViewById(R.id.reg_phone_edit);
		
		reg_button = (Button)findViewById(R.id.reg_button);
		
		Net_tool.checkNet(RegisterAct.this);
		 
	}

	private void init_listener() {
		back_view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 finish();
			}
		});
		
		reg_button.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				
				classname = class_edit.getText().toString();
				name= name_edit.getText().toString();
				studentid = studentid_edit.getText().toString();
				parent = parent_edit.getText().toString();
				phone = phone_edit.getText().toString();
				
				name = name.trim();
				studentid = studentid.trim();
				
				if(classname.equals("") || name.equals("") || studentid.equals("")){ 
					String msg = "请输入";
					if(classname.equals("")){
						msg = msg+"班级";
					}
					if(name.equals("")){
						msg = msg+"姓名";
					}
					if(studentid.equals("")){
						msg = msg+"学号";
					}
					
					show_msg(msg);
				}else{
					
					//校验学号重复
					
					Json_tool json_tool = new Json_tool();
					
					if(json_tool.check_school_student(schoolid, studentid) == false){ 
						json_tool.save_student(schoolid, studentid, classname, name, parent, phone); 
						finish(); 
					}else{
						show_msg("您输入的学号已存在！");
					} 
				}
			}
		});
	}
	
	protected void show_msg(String msg) {
		  
		msg_alert = new AlertDialog.Builder(RegisterAct.this)
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
	
	private void show_set() {
		set_alert = new AlertDialog.Builder(RegisterAct.this)
		.setTitle("提示")//设置对话框的标题
        .setMessage("您还没有设置机器人的学校编码，前往设置？")//设置对话框的内容
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                
                dialog.dismiss();
                Intent intent = new Intent();
    			intent.setClass(RegisterAct.this, SetAct.class);
    			startActivity(intent);
            }
        }).create();
		
		set_alert.show();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//点击按键事件 
		if(keyCode == KeyEvent.KEYCODE_BACK){ 
			BaseApplication.getInstance().exit();
		}
		return super.onKeyDown(keyCode, event);
	}


	
}
