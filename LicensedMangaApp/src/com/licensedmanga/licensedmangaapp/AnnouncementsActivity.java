package com.licensedmanga.licensedmangaapp;

import java.util.ArrayList;

import com.licensedmanga.licensedmangaapp.adapters.SeriesAdapter;
import com.licensedmanga.licensedmangaapp.db.DAO;
import com.licensedmanga.licensedmangaapp.db.Serie;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class AnnouncementsActivity extends Activity {

	private DAO datasource;
	ListView listView;	
	SeriesAdapter adapter;

	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_announcements);

	    datasource = new DAO(this);
	    datasource.open();
	    listView = (ListView) findViewById(R.id.listLicenses);
	    
	    ArrayList<Serie> values = datasource.getNewLicenses();
	    
	    adapter = new SeriesAdapter(AnnouncementsActivity.this,values);
	    listView.setAdapter(adapter);	
	  }
	  
	  @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.calendar, menu);
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
