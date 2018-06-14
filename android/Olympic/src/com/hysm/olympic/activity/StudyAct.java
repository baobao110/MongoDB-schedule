package com.hysm.olympic.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
 


import com.hysm.olympic.R;
import com.hysm.olympic.bean.PublicData;
import com.hysm.olympic.http.HttpTool;
import com.hysm.olympic.http.LoadImg;
import com.hysm.olympic.tool.Net_tool;
import com.hysm.olympic.tool.Player;
import com.hysm.olympic.tool.Speek;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;  
import android.os.Handler;
import android.os.Message; 
import android.view.KeyEvent;
import android.widget.ImageView;

public class StudyAct extends Activity {
	
	private ImageView imgview;
	 
	private Handler handler; 
	
	private Thread msgthread;
	
	private int study_num = 0;
	
	private String catalog_id ="";//章节目录id
	  
	//语音合成对象
    private SpeechSynthesizer speaker; 
    
  //语言开始播放
  	public static final int SAY_START =10;
  	//语言播放结束
  	public static final int SAY_END =11; 
  	
  //资料图片加载成功
  	public static final int IMG_LOAD_OK = 100;
  	//资料图片加载失败
  	public static final int IMG_LOAD_ERR = 101; 
   //数据加载成功
  	public static final int LOAD_OK = 200;
  	//数据加载失败
  	public static final int LOAD_ERR = 404;
  	
  	private int work_code = 0;//0学习  1进入课后练习
  	
  	private Player myplayer=null; //音乐播放器

	@Override
	protected void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		BaseApplication.getInstance().addActivity(this); // 添加Activity到堆栈 
		setContentView(R.layout.study);
		
		Intent myintent = getIntent();
		catalog_id = myintent.getStringExtra("catalog_id");
		
		init_view();
		init_create();
		init_handler(); 
		init_data();
		 
	}

	private void init_data() {
		work_code = 0;
		msgthread = new Thread(msg_runnable);
		
		msgthread.start();
	}

	private void init_view() {
		imgview = (ImageView)findViewById(R.id.study_img);
		Net_tool.checkNet(StudyAct.this);
	}

	private void init_create() {
		Speek speek = new Speek(StudyAct.this); 
		//初始化语音对象 
        speaker = speek.getSpeaker();
	}

	private void init_handler() {
		handler=new Handler(){

			@Override
			public void handleMessage(Message msg) { 
				super.handleMessage(msg);
				int what = msg.what; 
				switch (what) {
				case SAY_END: 
					
					if(work_code == 0){
						//加载下一个界面图片 
						into_nextimg(); 
					}else{
						//进入课后练习 
						speaker.destroy();
						Intent intent = new Intent(); 
						if(PublicData.QuestionList!= null && PublicData.QuestionList.length()> 0){
							intent.setClass(StudyAct.this, GuessAct.class); 
							intent.putExtra("type", "1");
						}else{
							intent.setClass(StudyAct.this, ChooseAct.class); 
						} 
						startActivity(intent);
					} 
					break;
				case SAY_START: 
					break; 
				case IMG_LOAD_ERR: 
					 break;
				case IMG_LOAD_OK:
					
					 Bitmap bmp=(Bitmap)msg.obj;
					 imgview.setImageBitmap(bmp); 
					  
					 //阅读内容 如果有语音，播放音频，没有合成语音 
					 try { 
						 if(PublicData.UiList.getJSONObject(study_num).has("audio")){
							 myplayer = new Player(handler);
							 myplayer.playUrl(HttpTool.Service_url+PublicData.UiList.getJSONObject(study_num).getString("audio"));
						 }else{
							 speaker.startSpeaking(PublicData.UiList.getJSONObject(study_num).getString("info"),synthesizerListener); 
						 } 
					} catch (JSONException e){ 
						e.printStackTrace();
					}
					 break;
				 case LOAD_OK:
					 into_frist();
					 break;
				 case Player.Player_end: 
					 //清空播放器
					 if(myplayer!= null){
						myplayer.stop();
						myplayer = null;
					 } 
					 if(work_code == 0){
							//加载下一个界面图片 
							into_nextimg(); 
					  }else{ 
						//进入课后练习 
						  speaker.destroy();
							Intent intent = new Intent(); 
							if(PublicData.QuestionList!= null && PublicData.QuestionList.length()> 0){
								intent.setClass(StudyAct.this, GuessAct.class); 
								intent.putExtra("type", "1");
							}else{
								intent.setClass(StudyAct.this, ChooseAct.class); 
							} 
							startActivity(intent);
					 } 
					    break; 
				 case Player.Player_err: 
						break;
				 case Player.Player_update: 
						break;
			     case Player.Player_perpare: 
						break;
				 case Player.Player_start: 
						break;
				 case Player.Player_Progress: 
						break;
				}
			} 
			
		};
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
		public void onCompleted(SpeechError error) {
			 
			Message msg=handler.obtainMessage();
			msg.what=SAY_END;
			handler.sendMessage(msg);
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
			Message msg=handler.obtainMessage();
			msg.what=SAY_START;
			handler.sendMessage(msg);
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
		public void onSpeakProgress(int percent, int beginPos, int endPos) {
			  
		}
		/**
         * 恢复播放回调接口
         */
		@Override
		public void onSpeakResumed() {
			 
		}
    	
    };
    
    /**
     * 加载图片线程
     */
    Runnable img_runnable = new Runnable(){ 
		@Override
		public void run(){
			 
			try {
				Bitmap bitmap = LoadImg.getImageBitmap(HttpTool.Service_url+PublicData.UiList.getJSONObject(study_num).getString("img"));
				
				Message msg = handler.obtainMessage();
				msg.what = IMG_LOAD_OK;
				msg.obj = bitmap;
				handler.sendMessage(msg);
				
			} catch (JSONException e) { 
				e.printStackTrace();
			}
			 
		}
	};
	
	/**
	 * 加载章节消息线程
	 */
	Runnable msg_runnable = new Runnable() {
		
		@Override
		public void run() {
			
			Map<String,String> params = new HashMap<String,String>();
			params.put("id", catalog_id); 
			String result = HttpTool.send_Post(HttpTool.Service_url+HttpTool.Study_url, params);
			
			try {
				JSONObject json = new JSONObject(result);
				PublicData.UiList = json.getJSONArray("uilist");
				PublicData.QuestionList = json.getJSONArray("questionlist");
			}catch(JSONException e){ 
				e.printStackTrace();
			}
			
			Message msg = handler.obtainMessage();
			msg.what = LOAD_OK;
			handler.sendMessage(msg);
		}
	};
	
	protected void into_frist() {
		 study_num = 0; 
		 if(PublicData.UiList!=null&& PublicData.UiList.length()>study_num){ 
			 Thread imgthread = new Thread(img_runnable);
			 imgthread.start();
		 }
	}

	
	//进入下一个界面
	protected void into_nextimg() {
		study_num++;
		if(PublicData.UiList!=null&& PublicData.UiList.length()>study_num){ 
			 Thread imgthread = new Thread(img_runnable);
			 imgthread.start();
		}else{
			//全部课程学完，进入课后练习 
			work_code = 1; 
			if(PublicData.QuestionList!= null && PublicData.QuestionList.length()>0){
				speaker.startSpeaking("下面进入课后练习。",synthesizerListener);
			}else{
				speaker.startSpeaking("课程已学完。",synthesizerListener);
			}
			
			 
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
