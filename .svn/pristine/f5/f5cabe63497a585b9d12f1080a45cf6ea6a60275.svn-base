package com.hysm.olympic.activity;



import java.util.ArrayList;
import java.util.List;

import com.hysm.olympic.R; 

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 进入设置前进行安全密码认证
 * @author songkai
 * 已经设置了安全密码的机器人，进入设置调整设置信息前进行安全密码认证（多次次连续输入错误，密码被锁住）
 *
 */
public class SafePassAct extends Activity {
	
	private LinearLayout back_view; 
	private TextView input1_text,input2_text,input3_text,input4_text,input5_text,input6_text; 
	private TextView error_text;
	private LinearLayout view0,view1,view2,view3,view4,view5,view6,view7,view8,view9; 
	private LinearLayout clean_view,delete_view;
	
	private List<TextView> textarr; 
	
	private String this_password="";
	private boolean IsCheck = false;
	private String pass_point ="●";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		BaseApplication.getInstance().addActivity(this); // 添加Activity到堆栈
		setContentView(R.layout.pass);
		
		init_data();
		init_view();
		
		init_listener();
	}


	private void init_data() {
		 
	}


	private void init_view() {
		textarr = new ArrayList<TextView>(); 
		
		back_view = (LinearLayout)findViewById(R.id.pass_back_view);
		
		input1_text =(TextView)findViewById(R.id.pass_input1_text);
		input2_text =(TextView)findViewById(R.id.pass_input2_text);
		input3_text =(TextView)findViewById(R.id.pass_input3_text);
		input4_text =(TextView)findViewById(R.id.pass_input4_text);
		input5_text =(TextView)findViewById(R.id.pass_input5_text);
		input6_text =(TextView)findViewById(R.id.pass_input6_text);
		
		textarr.add(input1_text);
		textarr.add(input2_text);
		textarr.add(input3_text);
		textarr.add(input4_text);
		textarr.add(input5_text);
		textarr.add(input6_text);
		
		error_text = (TextView)findViewById(R.id.pass_error_text);
		
		view0 = (LinearLayout)findViewById(R.id.pass_view0);
		view1 = (LinearLayout)findViewById(R.id.pass_view1);
		view2 = (LinearLayout)findViewById(R.id.pass_view2);
		view3 = (LinearLayout)findViewById(R.id.pass_view3);
		view4 = (LinearLayout)findViewById(R.id.pass_view4);
		
		view5 = (LinearLayout)findViewById(R.id.pass_view5);
		view6 = (LinearLayout)findViewById(R.id.pass_view6);
		view7 = (LinearLayout)findViewById(R.id.pass_view7);
		view8 = (LinearLayout)findViewById(R.id.pass_view8);
		view9 = (LinearLayout)findViewById(R.id.pass_view9);
		
		clean_view = (LinearLayout)findViewById(R.id.pass_view_clean);
		delete_view = (LinearLayout)findViewById(R.id.pass_view_delete);
		
	}
 
	private void init_listener() {
		
		back_view.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				 finish();
			}
		});
		
		view0.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				 
				Intent intent = new Intent();
				intent.setClass(SafePassAct.this, SetAct.class);
				startActivity(intent);
			}
		});
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
