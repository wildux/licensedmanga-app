package com.licensedmanga.licensedmangaapp.adapters;

import java.util.ArrayList;

import com.licensedmanga.licensedmangaapp.R;
import com.licensedmanga.licensedmangaapp.db.SerieCol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
/**
* Created by Wildux
*/
public class SeriesColAdapter extends BaseAdapter {
 
	Context context;
	ArrayList<SerieCol> listData;
	 
	public SeriesColAdapter(Context context,ArrayList<SerieCol> listData){
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
		private TextView textViewSeriesNum;
	}
	
	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		ViewHolder viewHolder = null;
		if(view == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.toread_item, null);
			viewHolder = new ViewHolder();
			viewHolder.textViewSeriesTitle = (TextView) view.findViewById(R.id.txtViewToReadTitle);
			viewHolder.textViewSeriesNum = (TextView) view.findViewById(R.id.txtViewToReadLocation);
			view.setTag(viewHolder);
		}
		else{
			viewHolder = (ViewHolder) view.getTag();
		}
		SerieCol serie = listData.get(position);
		String serieTitle = serie.getTitle();
		int own = serie.getOwn();
		int total = serie.getTotal_vol();
		viewHolder.textViewSeriesTitle.setText(serieTitle);
		viewHolder.textViewSeriesNum.setText(own + "/" + total);
		return view;
	}
}





