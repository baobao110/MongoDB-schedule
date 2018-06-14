package com.hysm.olympic.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hysm.olympic.R;
import com.hysm.olympic.bean.PublicData;
import com.hysm.olympic.http.HttpTool;
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
import android.widget.TextView;

/**
 * 从第一题到最后一题
 * 选择题ABCD，回答一题计算一次分数
 * @author songkai 
 */
public class GuessAct extends Activity {
	
	private TextView name_text,studentid_text,score_text,time_text;  
	private TextView question_text,a_text,b_text,c_text,d_text;
	private TextView answer_text,content_text;  
	
	private int score= 0,question_num = 0,second = 0;
	
	private String type ="0";//1课后习题 2练习3竞答
	
	private String question_str ="";
	private String a_str ="";
	private String b_str ="";
	private String c_str ="";
	private String d_str ="";
	
	private String true_answer = ""; 
	private String student_answer = "";
	
	private String content_info ="";
	
	private int work_code = 0;//0问问题   1解释问题答案   2 已全部答题 3超时
	
	// 语音识别对象
    private SpeechRecognizer recognizer; 
    //语音合成对象
    private SpeechSynthesizer speaker; 
    //识别出来的句子
    private StringBuilder sentence = new StringBuilder(); 
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
  	
    //数据加载成功
  	public static final int LOAD_OK = 200;
  	//数据加载失败
  	public static final int LOAD_ERR = 404; 
  	//答题超时
  	public static final int OVER_TIME = 500; 
  //答题时间
  	public static final int SECOND_TIME = 550;
  	
  	private SharedPreferences preferences;
  	private String student_name="",student_id="";
  	
  	private Thread msgthread,timethread;
  	
  	private int time_state = 0; //0不计时1计时
  	
  	

	@Override
	protected void onCreate(Bundle savedInstanceState){ 
		super.onCreate(savedInstanceState);
		BaseApplication.getInstance().addActivity(this); // 添加Activity到堆栈
		setContentView(R.layout.guessing);
		
		Intent myintent = getIntent();
		type = myintent.getStringExtra("type");
		
		init_data();
		init_view(); 
		init_listener(); 
		init_crate(); 
		init_handler();
		
		load_data(); 
	}
  

	private void init_data() {
		preferences = getSharedPreferences(PublicData.RobotShare, Context.MODE_PRIVATE);
		student_id = preferences.getString("studentid", "");
		student_name = preferences.getString("studentname", ""); 
	}

	private void init_view() {
		
		name_text = (TextView)findViewById(R.id.guess_name_text);
		studentid_text = (TextView)findViewById(R.id.guess_studentid_text);
		
		score_text = (TextView)findViewById(R.id.guess_score_text);
		time_text = (TextView)findViewById(R.id.guess_time_text);
		
		question_text = (TextView)findViewById(R.id.guree_question_text);
		a_text =(TextView)findViewById(R.id.guree_a_text);
		b_text =(TextView)findViewById(R.id.guree_b_text);
		c_text =(TextView)findViewById(R.id.guree_c_text);
		d_text =(TextView)findViewById(R.id.guree_d_text);
		
		answer_text = (TextView)findViewById(R.id.guree_answer_text);
		content_text = (TextView)findViewById(R.id.guree_content_text);
		
		name_text.setText("姓名："+student_name);
		studentid_text.setText("学号："+student_id);
		
		Net_tool.checkNet(GuessAct.this);
	}

	private void init_listener() {
		 
	}
	
	/**
	 * 初始化语音识别/语音合成
	 */
	private void init_crate() {
		
		Speek speek = new Speek(GuessAct.this);
		 
		//初始化语音对象
        recognizer = speek.getRecognizer();
        speaker = speek.getSpeaker();
		 
	}
	
	private void init_handler() {
		mHandler = new Handler() { 
			@Override
			public void handleMessage(Message msg){ 
				super.handleMessage(msg); 
				int what = msg.what;
				
				switch (what) {
				case SPEECH_SUCCESS:// 识别成功 
					String result = (String) msg.obj;  
					answer_question(result);
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
					answer_text.setText((String) msg.obj);
					break;
				case SAY_END: 
					  
					if(work_code == 0){
						//题目说明结束
						//开始录音 
						second = 30;
						time_text.setText("剩余30秒");
						
						//开始计时
						time_state = 1;
						
						recognizer.startListening(recognizerListener); 
					}else if(work_code == 1){
						//一题回答解释
						next_question();
					}else if(work_code == 2){
						//全部答题结束
						recognizer.destroy();
						speaker.destroy();
						
						Intent intent = new Intent();
						intent.setClass(GuessAct.this, ChooseAct.class);
						startActivity(intent);
					}
					break;
				case SAY_START:  
					break;
				case LOAD_OK:
					question_frist(); 
					break;
				case OVER_TIME:
					recognizer.stopListening();
					speaker.stopSpeaking();
					
					//结束计时
					time_state = 0;
					
					next_question(); 
					break;
				case SECOND_TIME:
					time_text.setText("剩余"+second+"秒");
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
			BaseApplication.getInstance().exit();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void load_data() {
		
		time_state = 0;
		timethread = new Thread(time_runnable);
		timethread.start();
		 
		if(type.equals("1")){
			question_frist();
		}else{
			msgthread = new Thread(msg_runnable);
			msgthread.start();
		}
	}
	
	private void question_frist() {
		score = 0;
		question_num = 0;
		score_text.setText("0分");
		
		if(PublicData.QuestionList!= null && PublicData.QuestionList.length() > question_num){
			show_question();
		}
	}

	//显示并问问题
	private void show_question() {
		work_code = 0;
		second = 30;
		time_text.setText("剩余30秒");
		int num = question_num+1;
		
		try {
			question_str = PublicData.QuestionList.getJSONObject(question_num).getString("question");
			a_str ="A."+PublicData.QuestionList.getJSONObject(question_num).getString("chooseA");
			b_str ="B."+PublicData.QuestionList.getJSONObject(question_num).getString("chooseB");
			c_str ="C."+PublicData.QuestionList.getJSONObject(question_num).getString("chooseC");
			d_str ="D."+PublicData.QuestionList.getJSONObject(question_num).getString("chooseD");
			
			content_info = PublicData.QuestionList.getJSONObject(question_num).getString("info");
			true_answer = PublicData.QuestionList.getJSONObject(question_num).getString("answer");
			
		} catch (JSONException e){ 
			e.printStackTrace();
		}
		
		 
		question_text.setText("第"+num+"题："+question_str);
		a_text.setText(a_str);
		b_text.setText(b_str);
		c_text.setText(c_str);
		d_text.setText(d_str);
		
		answer_text.setText("");
		content_text.setText("");
		
		//问问题
		String  str = "第"+num+"题："+question_str+a_str+b_str+c_str+d_str+"请回答？"; 
		speaker.startSpeaking(str,synthesizerListener);
		
	}
	
	/**
	 * 回答问题
	 * @param result
	 */
	protected void answer_question(String result){
		if(result.equals("下一题")){
			//结束计时
			time_state = 0;
    		work_code = 1;
			student_answer=""; 
			check_answer();
    	}else{
    		
    		result = Word_tool.GetChar(result);
    		
    		if(result.contains("A")||result.contains("a")){
    			//结束计时
    			time_state = 0;
    			work_code = 1;
    			student_answer="A"; 
    			check_answer(); 
    			
    		}else if(result.contains("B")||result.contains("b")){
    			//结束计时
    			time_state = 0;
    			work_code = 1;
    			student_answer="B"; 
    			check_answer(); 
    		}else if(result.contains("C")||result.contains("c")){
    			//结束计时
    			time_state = 0;
    			work_code = 1;
    			student_answer="C"; 
    			check_answer(); 
    		}else if(result.contains("D")||result.contains("d")){
    			//结束计时
    			time_state = 0;
    			work_code = 1;
    			student_answer="D"; 
    			check_answer(); 
    		}else{
    			//开始录音 
    			recognizer.startListening(recognizerListener); 
    		} 
    	}
	}
	
	/**
     * 校验题目答案，显示分数，进入下一题
     */
    private void check_answer() {
    	if(true_answer.equals(student_answer)){ 
    		score = score+10;
			score_text.setText(score+"分");
		}
    	try {
			PublicData.QuestionList.getJSONObject(question_num).put("student_answer", student_answer);
		}catch(JSONException e){ 
			e.printStackTrace();
		}
    	
    	if(student_answer.equals("")){
    		next_question();
    	}else{
    		
    		String str ="";
    		if(true_answer.equals(student_answer)){
    			str ="恭喜您回答正确。";
    		}else{
    			str ="抱歉您答错了。";
    		}
    		content_text.setText(content_info+"所以答案是："+true_answer); 
    		
    		str = content_info+"所以答案是："+true_answer+str;
    		speaker.startSpeaking(str,synthesizerListener);
    	}
		 
	}

	private void next_question() {
		 
		question_num++; 
		if(PublicData.QuestionList!= null && PublicData.QuestionList.length() > question_num){
			show_question();
		}else{
			
			work_code = 2;//全部答题结束
			
			String str="全部问题已回答，您获得了"+score+"分。";
			speaker.startSpeaking(str,synthesizerListener);
			 
		}
	}


	/**
	 * 加载章节消息线程
	 */
	Runnable msg_runnable = new Runnable() {
		
		@Override
		public void run() {
			
			Map<String,String> params = new HashMap<String,String>();
		 
			String result = HttpTool.send_Post(HttpTool.Service_url+HttpTool.Test_url, params);
			
			try {
				JSONArray jarr = new JSONArray(result); 
				PublicData.QuestionList = jarr;
			}catch(JSONException e){ 
				e.printStackTrace();
			}
			
			Message msg = mHandler.obtainMessage();
			msg.what = LOAD_OK;
			mHandler.sendMessage(msg);
		}
	};
	
	Runnable time_runnable = new Runnable() {
		
		@Override
		public void run() {
			 while(true){
				 
				 try {
					new Thread().sleep(1000);
				}catch(InterruptedException e){ 
					e.printStackTrace();
				}
				 
				if(time_state == 1){ 
					Message msg = mHandler.obtainMessage();
					if(second>0){
						second --;
						msg.what=SECOND_TIME;
					}else{
						msg.what=OVER_TIME;
					} 
					mHandler.sendMessage(msg);
				}
			 }
		}
	};

     

}
