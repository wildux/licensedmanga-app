package com.licensedmanga.licensedmangaapp;

import java.util.ArrayList;

import com.licensedmanga.licensedmangaapp.db.DAO;
import com.licensedmanga.licensedmangaapp.db.Serie;
import com.licensedmanga.licensedmangaapp.db.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SerieInfoActivity extends Activity {
	
	private DAO datasource;
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_serie_info);
		
		datasource = new DAO(this);
	    datasource.open();
	    listView = (ListView) findViewById(R.id.listSeriesVolumes);
	    
	    Bundle params = getIntent().getExtras();
		if (params != null) {
		    long id = Long.parseLong(params.getString("serie"));
		    Serie serie = datasource.getSerie(id);
		    TextView txtName = (TextView)findViewById(R.id.serie_name);
		    TextView txtType = (TextView)findViewById(R.id.serie_type);
		    TextView txtStat = (TextView)findViewById(R.id.serie_state);
		    TextView txtVols = (TextView)findViewById(R.id.serie_num);
		    txtName.setText(serie.getName());
		    txtType.setText("Type: "+ Utils.getType(serie.getType()));
		    txtStat.setText("State: "+ Utils.getState(serie.getState()));
		    int num_vols = serie.getTotal_vol();
		    txtVols.setText("Vols: "+ num_vols);
		    
		    		    
		    ArrayList<String> myArray = new ArrayList<String>();
		    for (int i = 1; i <= num_vols; ++i) myArray.add(Integer.toString(i));		    		    
		    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, myArray);
		    listView.setAdapter(adapter);
		    
		    //List<Serie> values = datasource.getSeriesByType(3);		    
		    //ArrayAdapter<Serie> adapter = new ArrayAdapter<Serie>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
		    //listView.setAdapter(adapter);
		    
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.serie_info, menu);
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
