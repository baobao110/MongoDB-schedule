package com.hysm.olympic.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import com.hysm.olympic.R;
import com.hysm.olympic.tool.Speek;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AskAct extends Activity {
	
	private TextView question_text;
	private LinearLayout img_view,text_view;
	private ImageView imgview;
	private TextView img_info,text_info;
	
	//private Thread thread;
	
	private Bitmap bitmap; 
	private Handler handler;
	
	private String content_info ="2022年北京冬季奥运会（XXIV Olympic Winter Games）第24届冬季奥林匹克运动会，将在2022年2月4日至2022年2月20日在中华人民共和国北京市和张家口市联合举行。这是中国历史上第一次举办冬季奥运会，北京、张家口同为主办城市，也是中国继北京奥运会、南京青奥会后，中国第三次举办的奥运赛事。";
		
	 
	// 语音识别对象
    private SpeechRecognizer recognizer; 
    //语音合成对象
    private SpeechSynthesizer speaker; 
    //识别出来的句子
    private StringBuilder sentence = new StringBuilder() ; 
     
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
  	
  //资料图片加载成功
  	public static final int IMG_LOAD_OK = 100;
  	//资料图片加载失败
  	public static final int IMG_LOAD_ERR = 101;
  	  
	@Override
	protected void onCreate(Bundle savedInstanceState){ 
		super.onCreate(savedInstanceState); 
		BaseApplication.getInstance().addActivity(this); // 添加Activity到堆栈
		setContentView(R.layout.ask);
		 
		init_data();
		init_view(); 
		init_listener();
		 
		init_crate(); 
		//thread = new Thread(runnable); 
		init_handler();
		
		 
	}
  
	private void init_data() {
		 
	}

	private void init_view() {
		 
		question_text =(TextView)findViewById(R.id.ask_question_text);
		img_view = (LinearLayout)findViewById(R.id.ask_img_view);
		text_view = (LinearLayout)findViewById(R.id.ask_text_view);
		imgview = (ImageView)findViewById(R.id.ask_img_img);
		
		img_info =(TextView)findViewById(R.id.ask_img_info);
		text_info =(TextView)findViewById(R.id.ask_text_info);
		 
	}

	private void init_listener(){
		 
	}
	
	/**
	 * 初始化语音识别/语音合成
	 */
	private void init_crate() {
		
		Speek speek = new Speek(AskAct.this);
		 
		//初始化语音对象
        recognizer = speek.getRecognizer();
        speaker = speek.getSpeaker();
		 
	}
	
	private void init_handler() {
		handler=new Handler(){ 
			@Override
			public void handleMessage(Message msg){ 
				super.handleMessage(msg);
				 
				int what = msg.what; 
				switch (what) {
				case SPEECH_SUCCESS:// 识别成功
					 
					String result = (String) msg.obj;  
					word_check(result);
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
					recognizer.startListening(recognizerListener); 
					break;
				case SPEECH_Progress:
					question_text.setText((String) msg.obj);
					break; 
				case SAY_END: 
					  
					//开始录音 
					recognizer.startListening(recognizerListener); 
					break;
				case SAY_START: 
					break;
				 case IMG_LOAD_ERR:
					 bitmap = null;
					 imgview.setVisibility(View.GONE);
					 break;
				 case IMG_LOAD_OK:
					 imgview.setVisibility(View.VISIBLE);
					 imgview.setImageBitmap(bitmap);
					 break;
				}
			}
			
		};
	}
  
	 
	
	
	
	
	@Override
	protected void onResume() { 
		super.onResume();
		
		speaker.startSpeaking("您可以向小奥咨询一些奥运知识,也可以退出奥运问答。",synthesizerListener);
	}


	//听写监听器
    private RecognizerListener recognizerListener = new RecognizerListener(){

    	/**
    	* 开始录音
    	*/
		@Override
		public void onBeginOfSpeech() {
			Message msg=handler.obtainMessage();
			msg.what=SPEECH_START;
			handler.sendMessage(msg);
		}

		 /**
		* 结束录音
		*/
		@Override
		public void onEndOfSpeech() {
			Message msg=handler.obtainMessage();
			msg.what=SPEECH_END;
			handler.sendMessage(msg);
		}

		/**
		* 会话发生错误回调接口
		* @param speechError
		*/
		@Override
		public void onError(SpeechError error) {
			// 回话发生错误时回调的函数
			String merror = error.getPlainDescription(true);// 获取错误码描述
			Message msg=handler.obtainMessage();
			msg.what=SPEECH_ERROR;
			msg.obj=merror;
			handler.sendMessage(msg);
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
					Message msgs = handler.obtainMessage();
					msgs.what = SPEECH_Progress;
					msgs.obj = rusult;
					handler.sendMessage(msgs);
					
				}else{
					//停止监听
					recognizer.stopListening(); 
					//发布发布监听内容
					 
					String rusult = sentence.toString();
					
					//语音读写区重置
					sentence= new StringBuilder();
					
					if(rusult!= null && !rusult.equals("")){
						Message msgs = handler.obtainMessage();
						msgs.what = SPEECH_SUCCESS;
						msgs.obj = rusult;
						handler.sendMessage(msgs);
					}else{
						Message msgs = handler.obtainMessage();
						msgs.what = SPEECH_FAIL;
						msgs.obj = rusult;
						handler.sendMessage(msgs);
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
  
    
   /* private void load_img() {
		 thread.start();
	}*/
 
    protected void word_check(String result) {
		 question_text.setText(result);
		 
		 if(result.contains("退出")){
			 recognizer.stopListening();
			 speaker.stopSpeaking();
			 finish();
		 }else{
			 imgview.setVisibility(View.VISIBLE);
			 img_info.setText(content_info); 
			 speaker.startSpeaking(content_info,synthesizerListener);
		 }
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
