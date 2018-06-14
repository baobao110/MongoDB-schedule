package com.hysm.olympic.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
  


import com.hysm.olympic.R;
import com.hysm.olympic.bean.PublicData; 
import com.hysm.olympic.http.HttpTool;
import com.hysm.olympic.tool.Speek;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context; 
import android.content.SharedPreferences;
import android.graphics.Bitmap; 
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar; 

public class GameAct extends Activity{

	private WebView webview;
	private ProgressBar progressBar;
	
	private SharedPreferences preferences;
	private JSONObject student_info;
	
	//语音合成对象
    private SpeechSynthesizer speaker; 
    //handler
 	private Handler mHandler;
 	
 	//语言开始播放
  	public static final int SAY_START =10;
  	//语言播放结束
  	public static final int SAY_END =11; 
  	//数据加载成功
  	public static final int LOAD_OK = 200;
  	//数据加载失败
  	public static final int LOAD_ERR = 404;
  	
  	private String schoolcode,studentid,rid,robotid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){ 
		super.onCreate(savedInstanceState);
		BaseApplication.getInstance().addActivity(this); // 添加Activity到堆栈 
		setContentView(R.layout.game);
		
		init_view();
		init_data();
		
		init_crate(); 
		
		init_hander();
		
		init_web(); 
		init_listener();
		
		
	}
   
	private void init_view(){
		webview = (WebView)findViewById(R.id.game_webview);
		progressBar = (ProgressBar)findViewById(R.id.game_progress);
	}
	private void init_data(){
		 
		preferences = getSharedPreferences(PublicData.RobotShare, Context.MODE_PRIVATE); 
		
		rid = preferences.getString("rid", "");
		robotid = preferences.getString("robotid", "");
		
		schoolcode = preferences.getString("schoolcode", "");
		String schoolname = preferences.getString("schoolname", "");
		
		String sid = preferences.getString("sid", "");
		studentid = preferences.getString("studentid", "");
		String studentname = preferences.getString("studentname", "");
		String grade = preferences.getString("grade", "");
		String classname = preferences.getString("classname", "");
		String parent = preferences.getString("parent", "");
		String phone = preferences.getString("phone", "");
		 
		 
		String rank = preferences.getString("rank", "1");
		String score = preferences.getString("score", "0");
		String star = preferences.getString("star", "0");
		String experience = preferences.getString("experience", "0");
		
		String pass = preferences.getString("pass", "");
		String passid = preferences.getString("passid", "");
		String passnum = preferences.getString("passnum", "0");
		
		try {
			
			student_info = new JSONObject();
			
			student_info.put("rid", rid);
			student_info.put("robotid", robotid);
			student_info.put("schoolcode", schoolcode);
			student_info.put("schoolname", schoolname);
			
			student_info.put("sid", sid);
			student_info.put("studentid", studentid);
			student_info.put("studentname", studentname);
			student_info.put("grade", grade);
			student_info.put("classname", classname);
			student_info.put("parent", parent);
			student_info.put("phone", phone);
			
			student_info.put("rank", Integer.valueOf(rank));
			student_info.put("score", Integer.valueOf(score));
			student_info.put("star", Integer.valueOf(star));
			student_info.put("experience", Integer.valueOf(experience));
			
			student_info.put("pass", pass);
			student_info.put("passid", passid); 
			student_info.put("passnum", Integer.valueOf(passnum));
			
			
		} catch (JSONException e){ 
			e.printStackTrace();
		}
		
	}
	
	private void init_web(){
		WebSettings webSettings=webview.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js

        /**
         * LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
         * LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
         * LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
         * LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
         */
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据. 
        //不支持支持屏幕缩放
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false); 
        //不显示webview缩放按钮
        //webSettings.setDisplayZoomControls(false);
         
        webview.addJavascriptInterface(this,"OlymlpicAndroid");//添加js监听 这样html就能调用客户端
         
        webview.loadUrl("file:///android_asset/ay/pbindex.html");
         
	}
	
	 private void init_listener() {
		  webview.setWebChromeClient(webChromeClient);
	      webview.setWebViewClient(webViewClient);
		
	 }
	
	 //WebViewClient主要帮助WebView处理各种通知、请求事件
    private WebViewClient webViewClient=new WebViewClient(){
        @Override
        public void onPageFinished(WebView view, String url) {//页面加载完成
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //被拦截的地址 
            return super.shouldOverrideUrlLoading(view, url);
        }

    }; 
    //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
    private WebChromeClient webChromeClient=new WebChromeClient(){
        //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult result){
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
            localBuilder.setTitle("提示");
            localBuilder.setMessage(message).setPositiveButton("确定",null);
            localBuilder.setCancelable(false);
            localBuilder.create().show();

            //注意:
            //必须要这一句代码:result.confirm()表示:
            //处理结果为确定状态同时唤醒WebCore线程
            //否则不能继续点击按钮
            result.confirm();
            return true;
        }

        //获取网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title); 
        }

        //加载进度回调
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setProgress(newProgress);
        }
    };
	
	
	 @Override
     protected void onDestroy() {
        super.onDestroy(); 
        //释放资源
        webview.destroy();
        webview=null;
    }
	 
	 
	 private void init_crate() {
		 Speek speek = new Speek(GameAct.this); 
		 //初始化语音对象 
	     speaker = speek.getSpeaker();
	 }
 
	 private void init_hander(){
		 mHandler = new Handler() { 
				@Override
				public void handleMessage(Message msg){ 
					super.handleMessage(msg); 
					int what = msg.what;
					
					switch (what){ 
						case SAY_END: 
							
							speek_end();
							break;
						case SAY_START:  
							break;
						case LOAD_OK: 
						   //数据加载成功 
						   sync_data_ok();
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
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//点击按键事件 
		if(keyCode == KeyEvent.KEYCODE_BACK){ 
			speaker.destroy();
			BaseApplication.getInstance().exit();
		}
		return super.onKeyDown(keyCode, event);
	}
    
    
    /**
     * android通知js 数据同步成功
     */
    protected void sync_data_ok() {
    	webview.loadUrl("javascript:SyncDataOK()");
	}

    /**
     * android通知js 语音播放结束
     */
	protected void speek_end() {
		  webview.loadUrl("javascript:SpeekEnd()");
	} 
	 
	 /**
     * JS调用android的方法
     * @param str
     * @return
     */
	 
	 /**
	  * 获取学生信息
	  * @return
	  */
	 @JavascriptInterface
	 public String GetStudent(){ 
		 return student_info.toString();
	 } 
	 /**
	  * 同步学生信息
	  */
	 @JavascriptInterface
	 public void SyncStudent(){ 
		 new Thread(msgrunnable).start();
	 } 
	 /**
	  * 语音合成
	  * @param str
	  */
	 @JavascriptInterface
	 public void SpeekWord(String str){
		 
		 if(speaker.isSpeaking()){
			 speaker.stopSpeaking();
		 }else{
			 speaker.startSpeaking(str,synthesizerListener);
		 } 
	 } 
	 /**
	  * 语音结束
	  * @param str
	  */
	 @JavascriptInterface
	 public void SpeekStop(){
		 if(speaker.isSpeaking()){
			 speaker.stopSpeaking();
		 }
	 }
	  
	 /**
	  * 学生信息同步
	  */ 
	 Runnable msgrunnable = new Runnable(){
		
		@Override
		public void run() {
			 
			Map<String,String> params = new HashMap<String,String>(); 
			params.put("schoolcode", schoolcode);
			params.put("studentid", studentid);
			
			String result = HttpTool.send_Post(HttpTool.Service_url+HttpTool.Student_info, params);
			
			try {
				JSONObject json = new JSONObject(result);
				
				if(json.getString("backcode").equals("200")){
					
					//保存信息
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
					
					//学生信息同步
					
					try {
						
						student_info = new JSONObject();
						
						student_info.put("rid", rid);
						student_info.put("robotid", robotid);
						student_info.put("schoolcode", schoolcode);
						student_info.put("schoolname", json.getString("schoolname"));
						
						student_info.put("sid", json.getString("sid"));
						student_info.put("studentid", studentid);
						student_info.put("studentname", json.getString("name"));
						student_info.put("grade", json.getString("grade"));
						student_info.put("classname", json.getString("classname"));
						student_info.put("parent", json.getString("parent"));
						student_info.put("phone", json.getString("phone"));
						
						student_info.put("rank", json.getInt("rank"));
						student_info.put("score", json.getInt("score"));
						student_info.put("star", json.getInt("star"));
						student_info.put("experience", json.getInt("experience"));
						
						student_info.put("pass", json.getString("pass"));
						student_info.put("passid", json.getString("passid")); 
						student_info.put("passnum", json.getInt("passnum"));
						
						
					} catch (JSONException e){ 
						e.printStackTrace();
					}
					 
					Message msg = mHandler.obtainMessage();
					msg.what = LOAD_OK;
					mHandler.sendMessage(msg); 
					 
					
				}else{ 
					Message msg = mHandler.obtainMessage();
					msg.what = LOAD_ERR;
					msg.obj = json.getString("backmsg"); 
					mHandler.sendMessage(msg);
				}
				
			}catch(JSONException e){ 
				e.printStackTrace();
			}
		}
	};

}
