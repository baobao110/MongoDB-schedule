package com.hysm.olympic.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hysm.olympic.R;
import com.hysm.olympic.adapter.CatalogAdapter;
import com.hysm.olympic.bean.PublicData;
import com.hysm.olympic.http.HttpTool;
import com.hysm.olympic.tool.Date_tool;
import com.hysm.olympic.tool.Net_tool;
import com.hysm.olympic.tool.Speek;
import com.hysm.olympic.tool.Word_tool;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent; 
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView; 
import android.widget.TextView;

public class CatalogAct extends Activity {
	
	private TextView name_text,studentid_text;
	
	private ListView listview;
	
	private CatalogAdapter adapter;
	
	 
    //语音合成对象
    private SpeechSynthesizer speaker; 
    
    //handler
 	private Handler mHandler;
 	
 	private Thread msg_thread; 
 	  
  	//语言开始播放
  	public static final int SAY_START =10;
  	//语言播放结束
  	public static final int SAY_END =11;
  	
  	//数据加载成功
  	public static final int LOAD_OK = 200;
  	//数据加载失败
  	public static final int LOAD_ERR = 404;
  	 
  	private String student_name="",student_id="";
  	
  	private SharedPreferences preferences;
   
  	List<String> title_list =null,id_list= null; 
  	
  	private long old_time = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState){ 
		super.onCreate(savedInstanceState);
		BaseApplication.getInstance().addActivity(this); // 添加Activity到堆栈 
		setContentView(R.layout.catalog);
		
		init_view(); 
		init_listener(); 
		init_crate(); 
		init_handler();
		
		msg_thread = new Thread(msg_runnable);
	}

	private void init_view() {
		name_text = (TextView)findViewById(R.id.catalog_name_text);
		studentid_text = (TextView)findViewById(R.id.catalog_studentid_text);
		listview = (ListView)findViewById(R.id.catalog_list);
		
		Net_tool.checkNet(CatalogAct.this);
	}

	private void init_listener() {
		listview.setOnItemClickListener(new OnItemClickListener(){ 
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				if(speaker.isSpeaking()){
					speaker.stopSpeaking();
				}
				speaker.destroy();
				
				String catalog_id = id_list.get(position);
				Intent intent = new Intent();
				intent.setClass(CatalogAct.this, StudyAct.class);
				intent.putExtra("catalog_id", catalog_id);
				
				startActivity(intent);
				
			}
        });
	}

	private void init_crate() {
		Speek speek = new Speek(CatalogAct.this);
		 
		//初始化语音对象 
        speaker = speek.getSpeaker();
	}

	private void init_handler() {
		mHandler = new Handler() { 
			@Override
			public void handleMessage(Message msg){ 
				super.handleMessage(msg); 
				int what = msg.what;
				
				switch (what){
				   
				case SAY_END:
					 
					if(old_time == 0){
						//重置时间
						old_time = Date_tool.get_time();
						
						speaker.startSpeaking("请选择学习内容。",synthesizerListener);
						 
					}else{
						
						//检验时间是否超过2分钟，进入广告
						if(Date_tool.check_time(old_time)){ 
							 
							speaker.stopSpeaking(); 
							
							Intent intent = new Intent();
							intent.setClass(CatalogAct.this, AdvertAct.class);
							startActivity(intent); 
						}else{
							speaker.startSpeaking("请选择学习内容。",synthesizerListener);
						}
					}
					break;
				case SAY_START:  
					break;
				case LOAD_OK:
					
					//数据加载成功
					show_speek();
					
					break;
				}
			}
 
        	
        };
	}
	
	 

	@Override
	protected void onResume(){ 
		super.onResume();
		init_data(); 
		
		//数据加载成功后再阅读
		 
	}

	private void init_data() {
		preferences = getSharedPreferences(PublicData.RobotShare, Context.MODE_PRIVATE);
		student_id = preferences.getString("studentid", "");
		student_name = preferences.getString("studentname", "");
		
		name_text.setText("姓名："+student_name);
		studentid_text.setText("学号："+student_id);
		 
		
		//数据加载成功后显示
		msg_thread.start();
		 
	}
	 
  //语音合成监听器
    private SynthesizerListener synthesizerListener = new SynthesizerListener() {

    	 /**
         * 缓冲进度回调
         */
		@Override
		public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {
			 
		}
		/**
         * 会话结束回调接口，没有错误时，error为null
         */
		@Override
		public void onCompleted(SpeechError arg0) {
			Message msg=mHandler.obtainMessage();
			msg.what=SAY_END;
			mHandler.sendMessage(msg);
		}

		 /**
	     * 会话事件回调接口
	     */
		@Override
		public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
			 
		}

		/**
         * 开始播放
         */
		@Override
		public void onSpeakBegin() {
			Message msg=mHandler.obtainMessage();
			msg.what=SAY_START;
			mHandler.sendMessage(msg);
		}
		/**
         * 暂停播放
         */
		@Override
		public void onSpeakPaused() {
			 
		}

		 /**
         * 播放进度回调
         */
		@Override
		public void onSpeakProgress(int arg0, int arg1, int arg2) {
			 
		}
		/**
         * 恢复播放回调接口
         */
		@Override
		public void onSpeakResumed() {
			 
		}
    	
    };
    
    
    Runnable msg_runnable = new Runnable(){ 
		@Override
		public void run() {
			 
			load_data();
		}
	};
	
	 protected void load_data(){
		 
		 Map<String,String> params = new HashMap<String,String>(); 
		 String result = HttpTool.send_Post(HttpTool.Service_url+HttpTool.Catalog_url, params);
		 
		 try {
			PublicData.CatalogList = new JSONArray(result);
		} catch (JSONException e){ 
			e.printStackTrace();
		} 
		 Message msg = mHandler.obtainMessage();
		 msg.what = LOAD_OK;
		 
		 mHandler.sendMessage(msg);
		 
	 }
	 
	 protected void show_speek() {
		
		if(PublicData.CatalogList != null){
			
			title_list = new ArrayList<String>();
			id_list = new ArrayList<String>();
			
			String str = "";
			
			try{ 
				for(int i=0;i<PublicData.CatalogList.length();i++){
					str = str +PublicData.CatalogList.getJSONObject(i).getString("title");
					title_list.add(PublicData.CatalogList.getJSONObject(i).getString("title"));
					id_list.add(PublicData.CatalogList.getJSONObject(i).getString("_id"));
				} 
			}catch(Exception e){
				
			} 
			adapter = new CatalogAdapter(CatalogAct.this, title_list); 
			listview.setAdapter(adapter);
			
			speaker.startSpeaking("欢迎您进入奥运知识学习，请选择学习内容。"+str,synthesizerListener);
		}
		 
		
	 }
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//点击按键事件 
		if(keyCode == KeyEvent.KEYCODE_BACK){ 
			speaker.destroy();
			BaseApplication.getInstance().exit();
		}
		return super.onKeyDown(keyCode, event);
	}
 
}
