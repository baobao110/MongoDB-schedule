package com.hysm.olympic.activity;

  

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
  


import com.hysm.olympic.R; 
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
import android.widget.LinearLayout;
import android.widget.TextView;  
/**
 * 首页 Activity
 * @author songkai
 * 用户语音登录账户   ——> 姓名，学号   ——> 进入问答/竞猜页面
 * 语音设置   ——> 账户安全认证  ——> 进入设置页面
 * 退出  ——> 退出app
 *
 */
public class MainActivity extends Activity{
	
	private LinearLayout login_view; 
	//private ImageView set_img;
	private TextView prompt_text; 
	private TextView name_text,studentid_text; 
	private TextView say_text,set_text;
	
	
	// 语音识别对象
    private SpeechRecognizer recognizer; 
    //语音合成对象
    private SpeechSynthesizer speaker; 
    //识别出来的句子
    private StringBuilder sentence = new StringBuilder() ; 
    //handler
 	private Handler mHandler;
 	
 	// 识别成功
 	public static final int SPEECH_SUCCESS = 0;
 	// 识别失败
 	public static final int SPEECH_FAIL = -1;
 	// 开始识别
 	public static final int SPEECH_START = 1;
 	// 识别出错
 	public static final int SPEECH_ERROR = 2; 
    // 识别结束
  	public static final int SPEECH_END = 3;
  	// 识别进度
  	public static final int SPEECH_Progress = 4;
  	 
  	//语言开始播放
  	public static final int SAY_START =10;
  	//语言播放结束
  	public static final int SAY_END =11;
  	//语音播放进度
  	public static final int SAY_Progress =12;
  	
  	public static final int Login_ok =100;
  	public static final int Login_err =101;
  	
  
  	
  	public static String say_info;
  	 
  	 
  	//语音工作编号   0 欢迎词   1说姓名  2 说学号  3 已登录
  	private int work_code = 1;
  	
  	private String student_name="",student_id="";
  	
  	private String rid,robotid,schoolcode,schoolname;
  	
  	private SharedPreferences preferences; 
	private AlertDialog set_alert;
	
	private long old_time = 0;
  	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BaseApplication.getInstance().addActivity(this); // 添加Activity到堆栈
		setContentView(R.layout.activity_main);

		
		init_view(); 
		init_crate(); 
		init_handler(); 
		init_listener(); 
		
		
	} 
	 

	
	
	/**
	 * 初始化页面
	 */
	private void init_view() {
		login_view = (LinearLayout)findViewById(R.id.main_login_view); 
		   
		prompt_text = (TextView)findViewById(R.id.main_prompt_text);
		 
		name_text = (TextView)findViewById(R.id.main_name_text);
		studentid_text = (TextView)findViewById(R.id.main_studentid_text); 
		say_text = (TextView)findViewById(R.id.main_say_text);
		 
		set_text = (TextView)findViewById(R.id.main_set_text);
		
		Net_tool.checkNet(MainActivity.this);
	}
	/**
	 * 初始化语音识别/语音合成
	 */
	private void init_crate() {
		
		Speek speek = new Speek(MainActivity.this);
		 
		//初始化语音对象
        recognizer = speek.getRecognizer();
        speaker = speek.getSpeaker();
		 
	}
	 
	
	@Override
	protected void onResume(){ 
		super.onResume();
		
		init_data();
		
		work_code = 1; 
		prompt_text.setText("");
		student_name = "";
		student_id = "";
		name_text.setText(student_name);
		studentid_text.setText(student_id);
		 
	}
	
	/**
	 * 初始化数据
	 */
	private void init_data() {
		//判断是否设置 
		preferences = getSharedPreferences(PublicData.RobotShare, Context.MODE_PRIVATE); 
		rid = preferences.getString("rid", "");
		robotid = preferences.getString("robotid", "");
		schoolcode = preferences.getString("schoolcode", "");
		schoolname = preferences.getString("schoolname", "");
		
		if(schoolcode.equals("")){ 
			show_set(); 
		}else{
			//取消以前登录保存的用户信息
			SharedPreferences.Editor editor = preferences.edit();  
			 
			editor.putString("studentid", "");
			editor.putString("studentname", "");
			editor.putString("grade", "");
			editor.putString("classname", "");
			editor.putString("parent", "");
			editor.putString("phone", ""); 
			editor.putString("sid", "");
			 
			editor.commit();
			
			start_work();
		}
	}


	/**
	 * 初始化监听事件
	 */
	private void init_listener(){
		
		set_text.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				
				if(recognizer.isListening()){
					recognizer.stopListening();
				}
				if(speaker.isSpeaking()){
					speaker.stopSpeaking();
				}
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, SetAct.class);
				startActivity(intent);
			}
		});
		 
	}
	
	
	private void init_handler() {
		mHandler = new Handler() { 
			@Override
			public void handleMessage(Message msg){ 
				super.handleMessage(msg); 
				int what = msg.what;
				
				switch (what) {
				case SPEECH_SUCCESS:// 识别成功 
					//重置时间
					if(work_code != 3){
						old_time = Date_tool.get_time(); 
						String result = (String) msg.obj;  
						word_check(result);
					} 
					break; 
				case SPEECH_FAIL:// 识别失败 
					//开始录音 
					recognizer.startListening(recognizerListener); 
					break; 
				case SPEECH_START: 
					
					break;
				case SPEECH_END:
					 
					break;
				case SPEECH_ERROR: 
					String error = (String) msg.obj;  
					//开始录音  
					if(old_time == 0){
						//重置时间
						old_time = Date_tool.get_time();
						recognizer.startListening(recognizerListener); 
					}else{
						
						//检验时间是否超过2分钟，进入广告
						if(Date_tool.check_time(old_time)){ 
							if(recognizer.isListening()){
								recognizer.stopListening();
							}
							if(speaker.isSpeaking()){
								speaker.stopSpeaking();
							}
							
							Intent intent = new Intent();
							intent.setClass(MainActivity.this, AdvertAct.class);
							startActivity(intent); 
						}else{
							recognizer.startListening(recognizerListener); 
						}
					} 
					break;
				case SPEECH_Progress:
					
					if(work_code == 1){
						name_text.setText((String) msg.obj);
					}else if(work_code == 2){
						studentid_text.setText((String) msg.obj);
					}
					
					break;
				case SAY_END:
					
					//重置时间
					old_time = Date_tool.get_time();
					 
					if(work_code != 3){
						//开始录音 
						recognizer.startListening(recognizerListener);
					}else{
						//登录进入
						if(recognizer.isListening()){
							recognizer.stopListening();
						}
						if(speaker.isSpeaking()){
							speaker.stopSpeaking();
						}
						
						Intent intent = new Intent();
						intent.setClass(MainActivity.this, ChooseAct.class);
						startActivity(intent); 
					}
					 
					break;
				case SAY_START: 
					 
					break;
				case SAY_Progress:
					 
					String say_str = say_info.substring(0, (Integer)msg.obj); 
					prompt_text.setText(say_str);
					
					break;
				case Login_ok:
					
					work_code = 3;
					name_text.setText(student_name);
					speaker.startSpeaking(say_info,synthesizerListener);
					break;
				case Login_err:
					speaker.startSpeaking(say_info,synthesizerListener);
					break;
				}
			}
 
        	
        };
	}
	 

	//听写监听器
    private RecognizerListener recognizerListener = new RecognizerListener(){

    	/**
    	* 开始录音
    	*/
		@Override
		public void onBeginOfSpeech() {
			Message msg=mHandler.obtainMessage();
			msg.what=SPEECH_START;
			mHandler.sendMessage(msg);
		}

		 /**
		* 结束录音
		*/
		@Override
		public void onEndOfSpeech() {
			Message msg=mHandler.obtainMessage();
			msg.what=SPEECH_END;
			mHandler.sendMessage(msg);
		}

		/**
		* 会话发生错误回调接口
		* @param speechError
		*/
		@Override
		public void onError(SpeechError error) {
			// 回话发生错误时回调的函数
			String merror = error.getPlainDescription(true);// 获取错误码描述
			Message msg=mHandler.obtainMessage();
			msg.what=SPEECH_ERROR;
			msg.obj=merror;
			mHandler.sendMessage(msg);
		}

		/**
		* 扩展用接口
		*/
		@Override
		public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
			 
		}

		 /**
		* 听写结果回调接口 , 返回Json格式结果
		* @param recognizerResult  json结果对象
		* @param b                 等于true时会话结束
		*/
		@Override
		public void onResult(RecognizerResult recognizerResult, boolean isLast) {
			// 一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
			// 关于解析Json的代码可参见MscDemo中JsonParser类；
			// isLast等于true时会话结束。
			/***
			 * 结果--{"sn":1,"ls":false,"bg":0,"ed":0,"ws":[{"bg":0,"cw":[
			 * {"w":"两百","sc":0.00}]}]}
			 * 结果--{"sn":2,"ls":true,"bg":0,"ed":0,"ws"
			 * :[{"bg":0,"cw":[{"w":"。","sc":0.00}]}]}
			 */
			try{
				//多次获取结果，读写语音内容
				JSONObject mRsult = new JSONObject(recognizerResult.getResultString());
				
				if (!mRsult.getBoolean("ls")) {
					
					JSONArray data = mRsult.getJSONArray("ws");
					for (int i = 0; i < data.length(); i++) {
						JSONObject w = data.getJSONObject(i);
						JSONArray array = w.getJSONArray("cw");
						for (int k = 0; k < array.length(); k++) {
							JSONObject cwdata = array.getJSONObject(k);
							sentence.append(cwdata.getString("w"));
						}
					} 
					String rusult = sentence.toString();
					Message msgs = mHandler.obtainMessage();
					msgs.what = SPEECH_Progress;
					msgs.obj = rusult;
					mHandler.sendMessage(msgs);
					
					
				}else{
					//停止监听
					recognizer.stopListening(); 
					//发布发布监听内容
					 
					String rusult = sentence.toString();
					
					//语音读写区重置
					sentence= new StringBuilder();
					
					if(rusult!= null && !rusult.equals("")){
						Message msgs = mHandler.obtainMessage();
						msgs.what = SPEECH_SUCCESS;
						msgs.obj = rusult;
						mHandler.sendMessage(msgs);
					}else{
						Message msgs = mHandler.obtainMessage();
						msgs.what = SPEECH_FAIL;
						msgs.obj = rusult;
						mHandler.sendMessage(msgs);
					}
					 
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onVolumeChanged(int arg0) {
			// 音量值变化
		}
  
    };
    
    
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
		public void onSpeakProgress(int percent, int beginPos, int endPos){
			Message msg=mHandler.obtainMessage();
			msg.what=SAY_Progress;
			msg.obj = endPos;
			mHandler.sendMessage(msg);
		}
		/**
         * 恢复播放回调接口
         */
		@Override
		public void onSpeakResumed() {
			 
		}
    	
    };
 
	 

    /**
     * 应用开始工作
     */
    private void start_work(){ 
		say_samething();
	}


	private void say_samething(){
		if(work_code == 1){
			say_info = "您好！小奥带您体验奥运知识学习与竞答pk。首先请登录您的账户,请说出您的姓名。"; 
		}else if(work_code == 2){
			say_info = "请您告诉小奥，您的学号。"; 
		} 
		 
		speaker.startSpeaking(say_info,synthesizerListener);
	}
	
	 protected void word_check(String str){
		  
		if(str.contains("设置")){
			if(recognizer.isListening()){
				recognizer.stopListening();
			}
			if(speaker.isSpeaking()){
				speaker.stopSpeaking();
			}
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, SetAct.class);
			startActivity(intent);
		}else if(str.contains("退出")){
			recognizer.stopListening();
			speaker.stopSpeaking();
			BaseApplication.getInstance().exit();
		}else{
			
			if(work_code == 1){
				//说出姓名 
				if(Word_tool.CheckStudentName(str)){ 
					student_name = Word_tool.GetHanZi(str);
					student_id = "";
					name_text.setText(student_name);
					studentid_text.setText(student_id);
					
					work_code = 2; 
					say_samething();
					
				}else{
					say_info = "您说的姓名不准确，请重新说。";  
					speaker.startSpeaking(say_info,synthesizerListener);
				} 
			}else if(work_code == 2){
				
				//学号校验
				str = Word_tool.GetNum(str);
				
				if(str.equals("")){
					say_info = "您说的学号不准确，请重新说。"; 
					speaker.startSpeaking(say_info,synthesizerListener);
				}else{ 
					//根据学号登陆  
					student_id = str; 
					studentid_text.setText(student_id);
					new Thread(msgrunnable).start();
				}
				 
			}
			
		}
		 
	 }
	  	
	private void show_set() {
		set_alert = new AlertDialog.Builder(MainActivity.this)
		.setTitle("提示")//设置对话框的标题
        .setMessage("您还没有设置机器人的学校编码，前往设置？")//设置对话框的内容
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                
                dialog.dismiss();
                if(recognizer.isListening()){
    				recognizer.stopListening();
    			}
    			if(speaker.isSpeaking()){
    				speaker.stopSpeaking();
    			}
                
                Intent intent = new Intent();
    			intent.setClass(MainActivity.this, SetAct.class);
    			startActivity(intent);
            }
        }).create();
		
		set_alert.show();
	}
	 
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//点击按键事件 
		if(keyCode == KeyEvent.KEYCODE_BACK){ 
			if(recognizer.isListening()){
				recognizer.stopListening();
			}
			if(speaker.isSpeaking()){
				speaker.stopSpeaking();
			}
			recognizer.destroy();
			speaker.destroy();
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
			params.put("studentid", student_id);
			
			String result = HttpTool.send_Post(HttpTool.Service_url+HttpTool.Login_url, params); 
			try {
				JSONObject json = new JSONObject(result);
				
				if(json.getString("backcode").equals("200")){
					
					//保存登陆信息
					SharedPreferences.Editor editor = preferences.edit();
					
					editor.putString("studentid", json.getString("studentid"));
					editor.putString("studentname", json.getString("name"));
					editor.putString("grade", json.getString("grade"));
					editor.putString("classname", json.getString("classname"));
					editor.putString("parent", json.getString("parent"));
					editor.putString("phone", json.getString("phone"));
					
					editor.putString("sid", json.getString("sid"));
					editor.putString("schoolname", json.getString("schoolname"));
					
					editor.putString("rank", json.getInt("rank")+"");//等级
					editor.putString("score", json.getInt("score")+"");//积分
					editor.putString("star", json.getInt("star")+"");//星
					editor.putString("experience", json.getInt("experience")+"");//经验
					
					editor.putString("pass",    json.getString("pass"));
					editor.putString("passid",  json.getString("passid"));
					editor.putString("passnum", json.getInt("passnum")+""); 
					
					editor.commit();
					
					student_name = json.getString("name");
					 
					Message msg = mHandler.obtainMessage();
					msg.what = Login_ok;
					mHandler.sendMessage(msg);
					
					work_code = 3;  
					say_info = "恭喜"+student_name+"登录成功。";
					
				}else{ 
					Message msg = mHandler.obtainMessage();
					msg.what = Login_err;
					msg.obj = json.getString("backmsg");
					say_info = json.getString("backmsg");
					mHandler.sendMessage(msg);
				}
				
			}catch(JSONException e){ 
				e.printStackTrace();
			}
		}
	};


  
}
