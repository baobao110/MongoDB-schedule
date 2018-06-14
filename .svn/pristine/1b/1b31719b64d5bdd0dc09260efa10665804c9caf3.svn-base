package com.hysm.olympic.activity;

import com.hysm.olympic.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AdvertAct extends Activity {
	
	private LinearLayout bg_view;
	
	private Handler handler;
	
	private static final int CHANGE_IMG= 1;
	
	private Thread thread;
	
	private boolean open = false;
	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
 
		super.onCreate(savedInstanceState);
		BaseApplication.getInstance().addActivity(this); // Ìí¼ÓActivityµ½¶ÑÕ»
		
		setContentView(R.layout.advert);
		
		bg_view =(LinearLayout)findViewById(R.id.advert_bg_view);
		
		bg_view.setBackground(getResources().getDrawable(get_img_id()));
		 
		bg_view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 finish();
			}
		});
		
		init_handler();
		
		thread = new Thread(runnable);
		open= true;
		thread.start();
	}


	private void init_handler() {
		handler = new Handler(){

			@Override
			public void handleMessage(Message msg){ 
				super.handleMessage(msg);
				
				if(msg.what == CHANGE_IMG){
					bg_view.setBackground(getResources().getDrawable(get_img_id()));
				}
			}
			 
		};
	}


	private int get_img_id() {
		
		int n = (int)(Math.random()*4+1);
		
		if(n==1){
			return R.drawable.a1;
		}else if(n==2){
			return R.drawable.a2;
		}else if(n==3){
			return R.drawable.a3;
		}else {
			return R.drawable.a4;
		}
		 
	}
	
	
	Runnable runnable = new Runnable() {
		
		@Override
		public void run() {
			try {
				
				while(open){
					new Thread().sleep(10*1000);
					
					Message msg = handler.obtainMessage();
					msg.what = CHANGE_IMG;
					
					handler.sendMessage(msg);
				}  
			} catch (InterruptedException e) { 
				e.printStackTrace();
			}
		}
	};
	
	
	

	
}
