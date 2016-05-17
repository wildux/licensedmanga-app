package com.licensedmanga.licensedmangaapp.adapters;

import java.util.ArrayList;

import com.licensedmanga.licensedmangaapp.R;
import com.licensedmanga.licensedmangaapp.db.Serie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
/**
* Created by Wildux
*/
public class SeriesAdapter extends BaseAdapter {
 
	Context context;
	ArrayList<Serie> listData;
	 
	public SeriesAdapter(Context context,ArrayList<Serie> listData){
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
		private TextView textViewSeriesTitle;
	}
	
	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		ViewHolder viewHolder = null;
		if(view == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.series_item, null);
			viewHolder = new ViewHolder();
			viewHolder.textViewSeriesTitle = (TextView) view.findViewById(R.id.txtViewSeriesTitle);
			view.setTag(viewHolder);
		}
		else{
			viewHolder = (ViewHolder) view.getTag();
		}
		Serie serie = listData.get(position);
		String serieTitle = serie.getName();
		viewHolder.textViewSeriesTitle.setText(serieTitle);
		return view;
	}
}





