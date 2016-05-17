package com.licensedmanga.licensedmangaapp;

import java.util.ArrayList;

import com.licensedmanga.licensedmangaapp.adapters.SeriesAdapter;
import com.licensedmanga.licensedmangaapp.db.DAO;
import com.licensedmanga.licensedmangaapp.db.Serie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SeriesListActivity extends Activity {
	
	private DAO datasource;
	ListView listView;	
	SeriesAdapter adapter;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_series_list);
		
		datasource = new DAO(this);
	    datasource.open();
	    listView = (ListView) findViewById(R.id.listSeries);
		
	    Bundle params = getIntent().getExtras();
		if (params != null) {
		    int type = Integer.parseInt(params.getString("series_type"));
		    ArrayList<Serie> values = datasource.getSeriesByType(type);
		    
		    //ArrayAdapter<Serie> adapter = new ArrayAdapter<Serie>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
		    //listView.setAdapter(adapter);
		    
		    adapter = new SeriesAdapter(SeriesListActivity.this,values);
		    listView.setAdapter(adapter);
		    
		    listView.setOnItemClickListener(new OnItemClickListener() {
		     
		    	@Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {                  
		    				    		
		    		Serie  serie = (Serie) listView.getItemAtPosition(position);
		    		String serie_id = Long.toString(serie.getId());
		    		
		    		/*Toast.makeText(getApplicationContext(),
			    			"Position :"+position+"  ListItem : " +serie.getName()+" Id: "+serie.getId() , Toast.LENGTH_LONG)
	                    	.show();*/
		    	    Intent intent = new Intent(getApplicationContext(), SerieInfoActivity.class);
		    	    intent.putExtra("serie", serie_id);
		    	    startActivity(intent);
                }
  
		    }); 
		    
		}			
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.series_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}
}
