package com.hysm.olympic.adapter;

import java.util.List;
  
import com.hysm.olympic.R; 
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CatalogAdapter extends BaseAdapter{
	
	private Context context;
	private List<String> list; 
	private LayoutInflater inflater;
	
	public CatalogAdapter(Context context,List<String> list){
		
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context); 	}

	@Override
	public int getCount() {
		 
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		 
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
			convertView = inflater.inflate(R.layout.catalog_view, null); 
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		
		holder.text = (TextView)convertView.findViewById(R.id.catalog_text);
		holder.text.setText(list.get(position));
		 
		return convertView;
	}
	
	
	class Holder{
		TextView text;
	}
	
	

}
