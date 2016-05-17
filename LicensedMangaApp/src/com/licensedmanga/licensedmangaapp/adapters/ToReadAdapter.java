package com.licensedmanga.licensedmangaapp.adapters;

import java.util.ArrayList;

import com.licensedmanga.licensedmangaapp.R;
import com.licensedmanga.licensedmangaapp.db.Volume;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
/**
* Created by Wildux
*/
public class ToReadAdapter extends BaseAdapter {
 
	Context context;
	ArrayList<Volume> listData;
	 
	public ToReadAdapter(Context context,ArrayList<Volume> listData){
		this.context = context;
		this.listData = listData;
	}
	 
	@Override
	public int getCount() {
		return listData.size();
	}
	 
	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}
	 
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	class ViewHolder {
		private TextView textViewToRead;
		private TextView textViewToReadLocation;
		
	}
	
	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		ViewHolder viewHolder = null;
		if(view == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.toread_item, null);
			viewHolder = new ViewHolder();
			viewHolder.textViewToRead = (TextView) view.findViewById(R.id.txtViewToReadTitle);
			viewHolder.textViewToReadLocation = (TextView) view.findViewById(R.id.txtViewToReadLocation);
			view.setTag(viewHolder);
		}
		else{
			viewHolder = (ViewHolder) view.getTag();
		}
		Volume volume = listData.get(position);
		String serieTitle = volume.getTitle();
		int num = volume.getNum();
		String location = volume.getLocation();
		viewHolder.textViewToRead.setText(serieTitle + " #" + num);
		viewHolder.textViewToReadLocation.setText(location);
		return view;
	}
}





