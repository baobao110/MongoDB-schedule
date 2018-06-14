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
 * �ӵ�һ�⵽���һ��
 * ѡ����ABCD���ش�һ�����һ�η���
 * @author songkai 
 */
public class GuessAct extends Activity {
	
	private TextView name_text,studentid_text,score_text,time_text;  
	private TextView question_text,a_text,b_text,c_text,d_text;
	private TextView answer_text,content_text;  
	
	private int score= 0,question_num = 0,second = 0;
	
	private String type ="0";//1�κ�ϰ�� 2��ϰ3����
	
	private String question_str ="";
	private String a_str ="";
	private String b_str ="";
	private String c_str ="";
	private String d_str ="";
	
	private String true_answer = ""; 
	private String student_answer = "";
	
	private String content_info ="";
	
	private int work_code = 0;//0������   1���������   2 ��ȫ������ 3��ʱ
	
	// ����ʶ�����
    private SpeechRecognizer recognizer; 
    //�����ϳɶ���
    private SpeechSynthesizer speaker; 
    //ʶ������ľ���
    private StringBuilder sentence = new StringBuilder(); 
    //handler
 	private Handler mHandler;
 	
 	// ʶ��ɹ�
 	public static final int SPEECH_SUCCESS = 0;
 	// ʶ��ʧ��
 	public static final int SPEECH_FAIL = -1;
 	// ��ʼʶ��
 	public static final int SPEECH_START = 1;
 	// ʶ�����
 	public static final int SPEECH_ERROR = 2; 
    // ʶ�����
  	public static final int SPEECH_END = 3; 
  	 // ʶ�����
   	public static final int SPEECH_Progress = 4;
  	
  	//���Կ�ʼ����
  	public static final int SAY_START =10;
  	//���Բ��Ž���
  	public static final int SAY_END =11;
  	
    //���ݼ��سɹ�
  	public static final int LOAD_OK = 200;
  	//���ݼ���ʧ��
  	public static final int LOAD_ERR = 404; 
  	//���ⳬʱ
  	public static final int OVER_TIME = 500; 
  //����ʱ��
  	public static final int SECOND_TIME = 550;
  	
  	private SharedPreferences preferences;
  	private String student_name="",student_id="";
  	
  	private Thread msgthread,timethread;
  	
  	private int time_state = 0; //0����ʱ1��ʱ
  	
  	

	@Override
	protected void onCreate(Bundle savedInstanceState){ 
		super.onCreate(savedInstanceState);
		BaseApplication.getInstance().addActivity(this); // ���Activity����ջ
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
		
		name_text.setText("������"+student_name);
		studentid_text.setText("ѧ�ţ�"+student_id);
		
		Net_tool.checkNet(GuessAct.this);
	}

	private void init_listener() {
		 
	}
	
	/**
	 * ��ʼ������ʶ��/�����ϳ�
	 */
	private void init_crate() {
		
		Speek speek = new Speek(GuessAct.this);
		 
		//��ʼ����������
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
				case SPEECH_SUCCESS:// ʶ��ɹ� 
					String result = (String) msg.obj;  
					answer_question(result);
					break; 
				case SPEECH_FAIL:// ʶ��ʧ��
					//��ʼ¼�� 
					recognizer.startListening(recognizerListener);
					break; 
				case SPEECH_START:
					 
					break;
				case SPEECH_END:
					 
					break;
				case SPEECH_ERROR: 
					String error = (String) msg.obj; 
					  
					//��ʼ¼�� 
					recognizer.startListening(recognizerListener);
					
					break;
				case SPEECH_Progress:
					answer_text.setText((String) msg.obj);
					break;
				case SAY_END: 
					  
					if(work_code == 0){
						//��Ŀ˵������
						//��ʼ¼�� 
						second = 30;
						time_text.setText("ʣ��30��");
						
						//��ʼ��ʱ
						time_state = 1;
						
						recognizer.startListening(recognizerListener); 
					}else if(work_code == 1){
						//һ��ش����
						next_question();
					}else if(work_code == 2){
						//ȫ���������
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
					
					//������ʱ
					time_state = 0;
					
					next_question(); 
					break;
				case SECOND_TIME:
					time_text.setText("ʣ��"+second+"��");
					break;
				}
			} 
        };
	}
 

	//��д������
    private RecognizerListener recognizerListener = new RecognizerListener(){

    	/**
    	* ��ʼ¼��
    	*/
		@Override
		public void onBeginOfSpeech() {
			Message msg=mHandler.obtainMessage();
			msg.what=SPEECH_START;
			mHandler.sendMessage(msg);
		}

		 /**
		* ����¼��
		*/
		@Override
		public void onEndOfSpeech() {
			Message msg=mHandler.obtainMessage();
			msg.what=SPEECH_END;
			mHandler.sendMessage(msg);
		}

		/**
		* �Ự��������ص��ӿ�
		* @param speechError
		*/
		@Override
		public void onError(SpeechError error) {
			// �ػ���������ʱ�ص��ĺ���
			String merror = error.getPlainDescription(true);// ��ȡ����������
			Message msg=mHandler.obtainMessage();
			msg.what=SPEECH_ERROR;
			msg.obj=merror;
			mHandler.sendMessage(msg);
		}

		/**
		* ��չ�ýӿ�
		*/
		@Override
		public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
			 
		}

		 /**
		* ��д����ص��ӿ� , ����Json��ʽ���
		* @param recognizerResult  json�������
		* @param b                 ����trueʱ�Ự����
		*/
		@Override
		public void onResult(RecognizerResult recognizerResult, boolean isLast) {
			// һ������»�ͨ��onResults�ӿڶ�η��ؽ����������ʶ�������Ƕ�ν�����ۼӣ�
			// ���ڽ���Json�Ĵ���ɲμ�MscDemo��JsonParser�ࣻ
			// isLast����trueʱ�Ự������
			/***
			 * ���--{"sn":1,"ls":false,"bg":0,"ed":0,"ws":[{"bg":0,"cw":[
			 * {"w":"����","sc":0.00}]}]}
			 * ���--{"sn":2,"ls":true,"bg":0,"ed":0,"ws"
			 * :[{"bg":0,"cw":[{"w":"��","sc":0.00}]}]}
			 */
			try{
				//��λ�ȡ�������д��������
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
					//ֹͣ����
					recognizer.stopListening(); 
					//����������������
					 
					String rusult = sentence.toString();
					
					//������д������
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
			// ����ֵ�仯
		}
  
    };
    
    
  //�����ϳɼ�����
    private SynthesizerListener synthesizerListener = new SynthesizerListener() {

    	 /**
         * ������Ȼص�
         */
		@Override
		public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {
			 
		}
		/**
         * �Ự�����ص��ӿڣ�û�д���ʱ��errorΪnull
         */
		@Override
		public void onCompleted(SpeechError arg0) {
			Message msg=mHandler.obtainMessage();
			msg.what=SAY_END;
			mHandler.sendMessage(msg);
		}

		 /**
	     * �Ự�¼��ص��ӿ�
	     */
		@Override
		public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
			 
		}

		/**
         * ��ʼ����
         */
		@Override
		public void onSpeakBegin() {
			Message msg=mHandler.obtainMessage();
			msg.what=SAY_START;
			mHandler.sendMessage(msg);
		}
		/**
         * ��ͣ����
         */
		@Override
		public void onSpeakPaused() {
			 
		}

		 /**
         * ���Ž��Ȼص�
         */
		@Override
		public void onSpeakProgress(int arg0, int arg1, int arg2) {
			 
		}
		/**
         * �ָ����Żص��ӿ�
         */
		@Override
		public void onSpeakResumed() {
			 
		}
    	
    };
  

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//��������¼� 
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
		score_text.setText("0��");
		
		if(PublicData.QuestionList!= null && PublicData.QuestionList.length() > question_num){
			show_question();
		}
	}

	//��ʾ��������
	private void show_question() {
		work_code = 0;
		second = 30;
		time_text.setText("ʣ��30��");
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
		
		 
		question_text.setText("��"+num+"�⣺"+question_str);
		a_text.setText(a_str);
		b_text.setText(b_str);
		c_text.setText(c_str);
		d_text.setText(d_str);
		
		answer_text.setText("");
		content_text.setText("");
		
		//������
		String  str = "��"+num+"�⣺"+question_str+a_str+b_str+c_str+d_str+"��ش�"; 
		speaker.startSpeaking(str,synthesizerListener);
		
	}
	
	/**
	 * �ش�����
	 * @param result
	 */
	protected void answer_question(String result){
		if(result.equals("��һ��")){
			//������ʱ
			time_state = 0;
    		work_code = 1;
			student_answer=""; 
			check_answer();
    	}else{
    		
    		result = Word_tool.GetChar(result);
    		
    		if(result.contains("A")||result.contains("a")){
    			//������ʱ
    			time_state = 0;
    			work_code = 1;
    			student_answer="A"; 
    			check_answer(); 
    			
    		}else if(result.contains("B")||result.contains("b")){
    			//������ʱ
    			time_state = 0;
    			work_code = 1;
    			student_answer="B"; 
    			check_answer(); 
    		}else if(result.contains("C")||result.contains("c")){
    			//������ʱ
    			time_state = 0;
    			work_code = 1;
    			student_answer="C"; 
    			check_answer(); 
    		}else if(result.contains("D")||result.contains("d")){
    			//������ʱ
    			time_state = 0;
    			work_code = 1;
    			student_answer="D"; 
    			check_answer(); 
    		}else{
    			//��ʼ¼�� 
    			recognizer.startListening(recognizerListener); 
    		} 
    	}
	}
	
	/**
     * У����Ŀ�𰸣���ʾ������������һ��
     */
    private void check_answer() {
    	if(true_answer.equals(student_answer)){ 
    		score = score+10;
			score_text.setText(score+"��");
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
    			str ="��ϲ���ش���ȷ��";
    		}else{
    			str ="��Ǹ������ˡ�";
    		}
    		content_text.setText(content_info+"���Դ��ǣ�"+true_answer); 
    		
    		str = content_info+"���Դ��ǣ�"+true_answer+str;
    		speaker.startSpeaking(str,synthesizerListener);
    	}
		 
	}

	private void next_question() {
		 
		question_num++; 
		if(PublicData.QuestionList!= null && PublicData.QuestionList.length() > question_num){
			show_question();
		}else{
			
			work_code = 2;//ȫ���������
			
			String str="ȫ�������ѻش��������"+score+"�֡�";
			speaker.startSpeaking(str,synthesizerListener);
			 
		}
	}


	/**
	 * �����½���Ϣ�߳�
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
