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
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ProvaActivity extends Activity implements View.OnClickListener {
	
	private Button buttonGet;    
    private DAO datasource;  
        
    private static final String JSON_URL_SERIES = "http://licensedmanga.com/android/getSeries.php";
    private static final String JSON_URL_VOLUMES = "http://licensedmanga.com/android/getVolumes.php";
    private static final String JSON_URL_LICENSES = "http://licensedmanga.com/android/getAnnouncements.php";
        
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prova);		
		
        buttonGet = (Button) findViewById(R.id.buttonGet);       
        buttonGet.setOnClickListener(this);     
        
        datasource = new DAO(this);
	    datasource.open();	    
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.prova, menu);
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
                loading = ProgressDialog.show(ProvaActivity.this, "Updating DB. Please Wait...",null,true,true);
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
