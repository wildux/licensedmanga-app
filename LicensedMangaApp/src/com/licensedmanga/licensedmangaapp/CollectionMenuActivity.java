package com.licensedmanga.licensedmangaapp;

import com.licensedmanga.licensedmangaapp.db.DAO;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class CollectionMenuActivity extends Activity {
	
	private DAO datasource;
	TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collection_menu);
		
		datasource = new DAO(this);
	    datasource.open();
	    textView = (TextView) findViewById(R.id.own);
	    textView.setText(getString(R.string.own) + " " + datasource.getNumVolsOwn());
	    textView = (TextView) findViewById(R.id.read);
	    textView.setText(getString(R.string.read) + " " + datasource.getNumVolsRead());
	    textView = (TextView) findViewById(R.id.numSeries);
	    textView.setText(getString(R.string.numSeries) + " " + datasource.getNumSeries());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.collection_menu, menu);
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
	
	public void goToReadList(View view) 
	{
	    Intent intent = new Intent(getApplicationContext(), ToReadActivity.class);
	    startActivity(intent);
	}
	
	public void goToMyCollection(View view) 
	{
	    Intent intent = new Intent(getApplicationContext(), CollectionActivity.class);
	    startActivity(intent);
	}
	
	public void goToWatchlist(View view) 
	{
	    Intent intent = new Intent(getApplicationContext(), WatchlistActivity.class);
	    startActivity(intent);
	}
}
