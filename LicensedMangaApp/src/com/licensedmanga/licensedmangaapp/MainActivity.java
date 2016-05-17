package com.licensedmanga.licensedmangaapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.licensedmanga.licensedmangaapp.db.DAO;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {
	
	private Button buttonGet;    
    private DAO datasource;  
        
    private static final String JSON_URL_SERIES = "http://licensedmanga.com/android/getSeries.php";
    private static final String JSON_URL_VOLUMES = "http://licensedmanga.com/android/getVolumes.php";
    private static final String JSON_URL_LICENSES = "http://licensedmanga.com/android/getAnnouncements.php";
	
	// URL to get contacts JSON
	/*private static String url = "http://localhost/android/getSeries.php";

	// JSON Node names
	private static final String TAG_SERIES = "series";
	private static final String TAG_ID = "id";
	private static final String TAG_TITLE = "title";
	private static final String TAG_TYPE = "type";
	private static final String TAG_STATE = "state";
	private static final String TAG_TOTAL_VOL = "total_vol";*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
		buttonGet = (Button) findViewById(R.id.buttonGet);       
        buttonGet.setOnClickListener(this);     
        
        datasource = new DAO(this);
	    datasource.open();		    
	    
	    
	    //ADD SERIES
	  /*  datasource.createSerie(1, "One Piece", 3, 1, 80);
	    datasource.createSerie(2, "Naruto", 3, 2, 72);
	    datasource.createSerie(3, "Fairy Tail", 3, 1, 60);
	    datasource.createSerie(4, "Louvre", 4, 2, 1);
	    datasource.createSerie(5, "Ludwig B", 4, 2, 2);
	    datasource.createSerie(6, "Captain Ken", 3, 2, 2);
	    datasource.createSerie(7, "Museum", 4, 1, 3);
	    datasource.createSerie(8, "Drifters", 4, 1, 4);
	    datasource.createSerie(9, "QQ Sweeper", 1, 2, 3);
	    datasource.createSerie(10, "No Game No Life", 10, 1, 6);*/
	    
	    //ADD VOLUMES
	    //ID, ID_SERIE, NUM, RELEASE_DATE
	    
	  /*  datasource.createVolume(1, 1, 1, "2014");
	    datasource.createVolume(2, 1, 2, "2015");
	    datasource.createVolume(3, 1, 3, "2015");
	    datasource.createVolume(4, 1, 4, "2016");
	    datasource.createVolume(5, 1, 5, "2016");
	    datasource.createVolume(6, 2, 72, "2016");
	    datasource.createVolume(7, 3, 1, "2012");*/
	    
	    //ADD TO MY_VOLUMES
	    //ID, LOCATION, READ
	    datasource.addVolume(1, "Estanteria 1"	, 1);
	    datasource.addVolume(2, "Estanteria 1"	, 0);
	    datasource.addVolume(3, "Menjador"	, 0);
	    datasource.addVolume(4, "Estanteria 2"	, 1);
	    datasource.addVolume(7, "Escriptori"	, 0);
	    
	    
	    //ADD TO WATCHLIST (QQ Sweeper, Museum, Ludwig B)
	    datasource.addSerieToWatchlist(9);
	    datasource.addSerieToWatchlist(5);
	    datasource.addSerieToWatchlist(7);
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	public void browse(View view) 
	{
	    Intent intent = new Intent(getApplicationContext(), SeriesMenuActivity.class);
	    startActivity(intent);
	}
	
	public void goToCollectionMenu(View view) 
	{
	    Intent intent = new Intent(getApplicationContext(), CollectionMenuActivity.class);
	    startActivity(intent);
	}
	
	public void goToCalendar(View view) 
	{
	    Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);	        
	    startActivity(intent);
	}
	
	public void goToAnnouncements(View view) 
	{
	    Intent intent = new Intent(getApplicationContext(), AnnouncementsActivity.class);
	    startActivity(intent);
	}
	
	@Override
    public void onClick(View v) {
        if(v==buttonGet){
            getJSON(JSON_URL_SERIES,	"s");            
            getJSON(JSON_URL_VOLUMES, 	"v");            
            getJSON(JSON_URL_LICENSES, 	"l");          
        }
    }
	
	
	private void getJSON(String url, String tp) {
        class GetJSON extends AsyncTask<String, Void, String>{
            ProgressDialog loading;
            
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Updating DB. Please Wait...",null,true,true);
            }
 
            @Override
            protected String doInBackground(String... params) {
 
                String uri = params[0];
                String type = params[1];
 
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
 
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
 
                    String json;
                    while((json = bufferedReader.readLine())!= null){
                            sb.append(json+"\n");
                    }
 
                    String s = sb.toString().trim();                    
                    if (type.equals("s")) parseSeries(s);
                    else if (type.equals("v")) parseVolumes(s);
                    else if (type.equals("l")) parseLicenses(s);
                    return s;
                }catch(Exception e){
                    return null;
                }
 
            }
 
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s); 
                loading.dismiss();  
            }
            
            private void parseSeries(String json) {
            	
            	if (json != null) {            		   		
        			try {				
        				JSONObject jsonObj = new JSONObject(json);
        		
        				// Getting JSON Array node
        				JSONArray series = jsonObj.getJSONArray("series");	
        		
        				// looping through all series
        				for (int i = 0; i < series.length(); i++) {
        					JSONObject s = series.getJSONObject(i);
        			
        					long id = s.getLong("id");
        					String title = s.getString("title");	
        					int type = s.getInt("type");
        					int state = s.getInt("state");	
        					int total = 10;        					
        					
        					//ADD SERIE
        					datasource.createSerie(id, title, type, state, total);
        					
        				}				
        			}
        			catch (JSONException e) {				
        			}
        		} 
        		else {			
        		}
            	
            }
            
            private void parseLicenses(String json) {
            	if (json != null) {    		
        			try {				
        				JSONObject jsonObj = new JSONObject(json);
        		
        				// Getting JSON Array node
        				JSONArray series = jsonObj.getJSONArray("licenses");
        		
        				// looping through all series
        				for (int i = 0; i < series.length(); i++) {
        					JSONObject s = series.getJSONObject(i);
        			
        					long id = s.getLong("id_serie");
        					String day = s.getString("day");
        					
        					//ADD LICENSE
        					datasource.addLicense(id, day);					
        				}				
        			}
        			catch (JSONException e) {				
        			}
        		} 
        		else {			
        		}    	
            }
            
            private void parseVolumes(String json) {
            	if (json != null) {    		
        			try {				
        				JSONObject jsonObj = new JSONObject(json);
        		
        				// Getting JSON Array node
        				JSONArray series = jsonObj.getJSONArray("volumes");
        		
        				// looping through all series
        				for (int i = 0; i < series.length(); i++) {
        					JSONObject s = series.getJSONObject(i);
        			
        					long id = s.getLong("id");
        					long id_serie = s.getLong("id_serie");
        					int num = s.getInt("number");
        					String release_date = s.getString("release_date");
        					
        					//ADD VOLUME
        					datasource.createVolume(id, id_serie, num, release_date);					
        				}				
        			}
        			catch (JSONException e) {				
        			}
        		} 
        		else {			
        		}    	
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute(url, tp);
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
