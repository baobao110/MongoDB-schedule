package com.hysm.olympic.activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
  
import com.hysm.olympic.R;
import com.hysm.olympic.adapter.AnswerAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.TextView;

public class AnswerAct extends Activity {
	
	private TextView name_text,studentid_text,score_text;
	
	private ListView answer_list;
	
	private AnswerAdapter answerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		BaseApplication.getInstance().addActivity(this); // 添加Activity到堆栈
		setContentView(R.layout.answer);
		
		init_data();
		init_view(); 
		init_listener();
		
	}

	private void init_data() {
		 
	}

	private void init_view() {
		name_text=(TextView)findViewById(R.id.answer_name_text);
		studentid_text=(TextView)findViewById(R.id.answer_studentid_text);
		score_text=(TextView)findViewById(R.id.answer_score_text);
		
		answer_list= (ListView)findViewById(R.id.answer_list);
		
		JSONArray jsarr = new JSONArray();
		
		try {
			
			JSONObject json = new JSONObject();
			json.put("aaa", ""); 
			jsarr.put(json); 
			jsarr.put(json); 
			jsarr.put(json); 
			jsarr.put(json); 
			jsarr.put(json); 
			jsarr.put(json); 
			jsarr.put(json); 
			jsarr.put(json); 
			
		} catch (JSONException e) { 
			e.printStackTrace();
		}
		
		answerAdapter =new AnswerAdapter(AnswerAct.this, jsarr);
		
		answer_list.setAdapter(answerAdapter);
		
		
	}

	private void init_listener() {
		 
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
