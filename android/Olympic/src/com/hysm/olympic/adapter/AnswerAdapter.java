package com.hysm.olympic.adapter;

import org.json.JSONArray; 
import com.hysm.olympic.R; 
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AnswerAdapter extends BaseAdapter {

	private Context context;
	private JSONArray list;
	
	private LayoutInflater inflater;
	
	public AnswerAdapter(Context context,JSONArray list){
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		 
		return list.length();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) { 
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		
		if (convertView == null) {
			holder = new Holder(); 
			convertView = inflater.inflate(R.layout.answer_view, null); 
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		
		holder.question_text =(TextView)convertView.findViewById(R.id.answer_view_question);
		holder.a_text =(TextView)convertView.findViewById(R.id.answer_view_a);
		holder.b_text =(TextView)convertView.findViewById(R.id.answer_view_b);
		holder.c_text =(TextView)convertView.findViewById(R.id.answer_view_c);
		holder.d_text =(TextView)convertView.findViewById(R.id.answer_view_d);
		
		holder.answer_text =(TextView)convertView.findViewById(R.id.answer_view_answer);
		holder.log_text =(TextView)convertView.findViewById(R.id.answer_view_log);
		holder.realy_text =(TextView)convertView.findViewById(R.id.answer_view_realy);
		holder.info_text =(TextView)convertView.findViewById(R.id.answer_view_info);
		
		
		//×°ÔØÔªËØ
		 
		
		return convertView;
	}
	
	class Holder{
		TextView question_text,a_text,b_text,c_text,d_text;
		TextView answer_text,log_text,realy_text,info_text;
	}

}
