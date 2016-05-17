package com.licensedmanga.licensedmangaapp.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public class DAO {	
	
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	
	private String[] seriesColumns = { MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_TITLE, 
			MySQLiteHelper.COLUMN_TYPE, MySQLiteHelper.COLUMN_STATE, MySQLiteHelper.COLUMN_TOTAL_VOL    };
	
	private String[] volumesColumns = { MySQLiteHelper.COLUMN_ID_VOL, MySQLiteHelper.COLUMN_ID_SERIE, 
			MySQLiteHelper.COLUMN_NUM, MySQLiteHelper.COLUMN_RELEASE_DATE    };
	
	private String[] myVolumesColumns = { MySQLiteHelper.COLUMN_ID_MYVOL, MySQLiteHelper.COLUMN_LOCATION,
			MySQLiteHelper.COLUMN_READ    };
	
	private String[] watchlistColumns = { MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_TITLE	};
	
	private String[] newLicensesColumns = { MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_TITLE,
			MySQLiteHelper.COLUMN_DAY};
	
	private String[] calendarColumns = { MySQLiteHelper.COLUMN_ID_VOL, MySQLiteHelper.COLUMN_ID_SERIE, MySQLiteHelper.COLUMN_TITLE,
			MySQLiteHelper.COLUMN_NUM, MySQLiteHelper.COLUMN_RELEASE_DATE };
	
	private String[] toReadColumns = { MySQLiteHelper.COLUMN_ID_VOL, MySQLiteHelper.COLUMN_ID_SERIE, 
			MySQLiteHelper.COLUMN_NUM, MySQLiteHelper.COLUMN_TITLE,
			MySQLiteHelper.COLUMN_LOCATION, MySQLiteHelper.COLUMN_READ	};
	
	private String[] collectionColumns = { MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_TITLE,
			"count(*) AS own", MySQLiteHelper.COLUMN_TOTAL_VOL};
	
	public DAO(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	
	/*
	* SERIES
	*/
	public void createSerie(long id, String name, int type, int state, int total_vol) {
	    ContentValues values = new ContentValues();
	    values.put(MySQLiteHelper.COLUMN_ID, id);
	    values.put(MySQLiteHelper.COLUMN_TITLE, name);
	    values.put(MySQLiteHelper.COLUMN_TYPE, type);
	    values.put(MySQLiteHelper.COLUMN_STATE, state);
	    values.put(MySQLiteHelper.COLUMN_TOTAL_VOL, total_vol);	
	    
	    database.insert(MySQLiteHelper.TABLE_SERIES, null, values);	        
	    
	  /*  Cursor cursor = database.query(MySQLiteHelper.TABLE_SERIES, seriesColumns, MySQLiteHelper.COLUMN_ID + " = " + id, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Serie newSerie = cursorToSerie(cursor);
	    cursor.close();
	    return newSerie;*/
	}

	public void deleteSerie(Serie serie) {
		long id = serie.getId();
	    System.out.println("Serie deleted with id: " + id);
	    database.delete(MySQLiteHelper.TABLE_SERIES, MySQLiteHelper.COLUMN_ID + " = " + id, null);
	}
	  
	public List<Serie> getAllSeries() {
		List<Serie> series = new ArrayList<Serie>();
		Cursor cursor = database.query(MySQLiteHelper.TABLE_SERIES, seriesColumns, null, null, null, null, MySQLiteHelper.COLUMN_TITLE);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Serie serie = cursorToSerie(cursor);
			series.add(serie);
			cursor.moveToNext();
		}
		    
		cursor.close();
		return series;
	}
	
	public ArrayList<Serie> getSeriesByType(int type) {
		ArrayList<Serie> series = new ArrayList<Serie>();
		Cursor cursor = database.query(MySQLiteHelper.TABLE_SERIES, seriesColumns, MySQLiteHelper.COLUMN_TYPE + " = " + type, null, null, null, MySQLiteHelper.COLUMN_TITLE);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Serie serie = cursorToSerie(cursor);
			series.add(serie);
			cursor.moveToNext();
		}
		    
		cursor.close();
		return series;
	}

	private Serie cursorToSerie(Cursor cursor) {
		Serie serie = new Serie();
		serie.setId(cursor.getLong(0));
		serie.setName(cursor.getString(1));
		serie.setType(cursor.getInt(2));
		serie.setState(cursor.getInt(3));
		serie.setTotal_vol(cursor.getInt(4));
		return serie;
	}
	
	public Serie getSerie(long id) {
		Cursor cursor = database.query(MySQLiteHelper.TABLE_SERIES, seriesColumns, MySQLiteHelper.COLUMN_ID + " = " + id, null, null, null, null);
		cursor.moveToFirst();
		Serie serie = cursorToSerie(cursor);
		cursor.close();
		return serie;
	}
	
	public long getNumVolsOwn() {		
		SQLiteStatement res = database.compileStatement( "select count(*) from "+MySQLiteHelper.TABLE_MY_VOLUMES+";" );
		return res.simpleQueryForLong();
	}
	
	public long getNumVolsRead() {		
		SQLiteStatement res = database.compileStatement( "select count(*) from "+MySQLiteHelper.TABLE_MY_VOLUMES+" where "+MySQLiteHelper.COLUMN_READ+" = 1;" );
		return res.simpleQueryForLong();
	}
	
	public long getNumSeries() {
		SQLiteStatement res = database.compileStatement( "select count(DISTINCT "+MySQLiteHelper.COLUMN_ID_SERIE+" )"
				+ " from "+MySQLiteHelper.TABLE_MY_VOLUMES + "," + MySQLiteHelper.TABLE_VOLUMES 
				+ " where " + MySQLiteHelper.COLUMN_ID_MYVOL  + " = " + MySQLiteHelper.COLUMN_ID_VOL + ";" );
		return res.simpleQueryForLong();
		//return 999;
	}
	
	
	/*
	* VOLUMES
	*/
	
	public void createVolume(long id, long id_serie, int num, String release_date) {
	    ContentValues values = new ContentValues();
	    values.put(MySQLiteHelper.COLUMN_ID_VOL, id);
	    values.put(MySQLiteHelper.COLUMN_ID_SERIE, id_serie);
	    values.put(MySQLiteHelper.COLUMN_NUM, num);
	   //values.put(MySQLiteHelper.COLUMN_LOCATION, location);
	    //values.put(MySQLiteHelper.COLUMN_READ, read);	
	    values.put(MySQLiteHelper.COLUMN_RELEASE_DATE, release_date);
	    
	    database.insert(MySQLiteHelper.TABLE_VOLUMES, null, values);	        
	    
	  /*  Cursor cursor = database.query(MySQLiteHelper.TABLE_VOLUMES, volumesColumns, MySQLiteHelper.COLUMN_ID + " = " + id, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Volume newVolume = cursorToVolume(cursor);
	    cursor.close();
	    return newVolume;*/
	}

	public void deleteVolume(Volume volume) {
		long id = volume.getId();
	    System.out.println("Volume deleted with id: " + id);
	    database.delete(MySQLiteHelper.TABLE_VOLUMES, MySQLiteHelper.COLUMN_ID_VOL + " = " + id, null);
	}
	  
	public List<Volume> getAllVolumes() {
		List<Volume> volumes = new ArrayList<Volume>();
		Cursor cursor = database.query(MySQLiteHelper.TABLE_VOLUMES, volumesColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Volume volume = cursorToVolume(cursor);
			volumes.add(volume);
			cursor.moveToNext();
		}
		    
		cursor.close();
		return volumes;
	}
	
	private Volume cursorToVolume(Cursor cursor) {
		Volume volume = new Volume();
		volume.setId(cursor.getLong(0));
		volume.setId_serie(cursor.getLong(1));
		volume.setNum(cursor.getInt(2));
		volume.setTitle(cursor.getString(3));
		volume.setLocation(cursor.getString(4));
		volume.setRead(cursor.getInt(5));
		
		return volume;
	}
	
	public Volume modVolLocation(long id, String location)
	{
		Volume vol = new Volume();
		return vol;
	}
	
	public Volume modVolRead(long id, int read)
	{
		Volume vol = new Volume();
		return vol;
	}
	
	public Volume getVolume(long id) {
		Cursor cursor = database.query(MySQLiteHelper.TABLE_VOLUMES, volumesColumns, MySQLiteHelper.COLUMN_ID_VOL + " = " + id, null, null, null, null);
		cursor.moveToFirst();
		Volume volume = cursorToVolume(cursor);
		cursor.close();
		return volume;
	}
	
	public int getNumVols(long id)
	{
		// QUERY NUM_VOLS AMB SERIE_ID = id
		int num_vols = 0;
		return num_vols;
	}
	
	
	public void addVolume(long id, String location, int read) {
		ContentValues values = new ContentValues();
	    values.put(MySQLiteHelper.COLUMN_ID_MYVOL, id);
	    values.put(MySQLiteHelper.COLUMN_LOCATION, location);
	    values.put(MySQLiteHelper.COLUMN_READ, read);
	    database.insert(MySQLiteHelper.TABLE_MY_VOLUMES, null, values);	 
	}
	
	public void removeVolume(long id) {
		database.delete(MySQLiteHelper.TABLE_MY_VOLUMES, MySQLiteHelper.COLUMN_ID_MYVOL + " = " + id, null);	
	}
	
	public ArrayList<Volume> getToReadVolumes() {
		ArrayList<Volume> toReadVolumes = new ArrayList<Volume>();		
		Cursor cursor = database.query(
				MySQLiteHelper.TABLE_MY_VOLUMES + " , " + MySQLiteHelper.TABLE_VOLUMES + " , " + MySQLiteHelper.TABLE_SERIES,
				toReadColumns,
				MySQLiteHelper.COLUMN_ID + " = " + MySQLiteHelper.COLUMN_ID_SERIE 
				+ " AND " + MySQLiteHelper.COLUMN_ID_VOL + " = " + MySQLiteHelper.COLUMN_ID_MYVOL
				+ " AND " + MySQLiteHelper.COLUMN_READ + " = 0",
		        null,
		        MySQLiteHelper.COLUMN_ID_SERIE,
		        null,
		        "1 DESC"
		);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Volume volume = cursorToVolume(cursor);
			toReadVolumes.add(volume);
			cursor.moveToNext();
		}
		    
		cursor.close();
		return toReadVolumes;
	}
	
	/*
	 * MY SERIES 
	 */
	 
	public ArrayList<Serie> getWatchlist() {			
		ArrayList<Serie> mySeries = new ArrayList<Serie>();
			
		Cursor cursor = database.query(
				MySQLiteHelper.TABLE_WATCHLIST + " , " + MySQLiteHelper.TABLE_SERIES,
				seriesColumns,
				MySQLiteHelper.COLUMN_ID + " = " + MySQLiteHelper.COLUMN_ID_MYSERIE,
		        null,
		        null,
		        null,
		        MySQLiteHelper.COLUMN_TITLE
		);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Serie serie = cursorToSerie(cursor);
			mySeries.add(serie);
			cursor.moveToNext();
		}
		    
		cursor.close();
		return mySeries;	
	}	
	
	public void addSerieToWatchlist(long id) {
		ContentValues values = new ContentValues();
	    values.put(MySQLiteHelper.COLUMN_ID_MYSERIE, id);	    
	    database.insert(MySQLiteHelper.TABLE_WATCHLIST, null, values);	 
	}
	
	public void removeSerieFromWatchlist(long id) {
		int num_vols = getNumVols(id);
		if (num_vols == 0) {
			database.delete(MySQLiteHelper.TABLE_WATCHLIST, MySQLiteHelper.COLUMN_ID_MYSERIE + " = " + id, null);
		}
	}
	
	
	public ArrayList<SerieCol> getCollection() {			
		ArrayList<SerieCol> mySeries = new ArrayList<SerieCol>();
			
		Cursor cursor = database.query(
				MySQLiteHelper.TABLE_MY_VOLUMES + " , " + MySQLiteHelper.TABLE_VOLUMES + " , " + MySQLiteHelper.TABLE_SERIES,
				collectionColumns,
				MySQLiteHelper.COLUMN_ID + " = " + MySQLiteHelper.COLUMN_ID_SERIE 
				+ " AND " + MySQLiteHelper.COLUMN_ID_VOL + " = " + MySQLiteHelper.COLUMN_ID_MYVOL,
		        null,
		        MySQLiteHelper.COLUMN_ID_SERIE,
		        null,
		        MySQLiteHelper.COLUMN_TITLE
		);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			SerieCol serie = cursorToSerieCol(cursor);
			mySeries.add(serie);
			cursor.moveToNext();
		}
		    
		cursor.close();
		return mySeries;	
	}
	
	public ArrayList<Volume> getNewReleases() {			
		ArrayList<Volume> releases = new ArrayList<Volume>();
		Calendar today = Calendar.getInstance();
	    int m = today.get(Calendar.MONTH); m++;
	    String month = String.format("%02d", m); 
	    int year = today.get(Calendar.YEAR);
	    String date = year + "-" + month;
			
		Cursor cursor = database.query(
				MySQLiteHelper.TABLE_VOLUMES + " , " + MySQLiteHelper.TABLE_SERIES,
				calendarColumns,
				MySQLiteHelper.COLUMN_ID + " = " + MySQLiteHelper.COLUMN_ID_SERIE
				+ " AND substr("+MySQLiteHelper.COLUMN_RELEASE_DATE+",1,7) = '" + date + "'",
		        null,
		        null,
		        null,
		        MySQLiteHelper.COLUMN_RELEASE_DATE
		);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Volume volume = cursorToRelease(cursor);
			releases.add(volume);
			cursor.moveToNext();
		}
		    
		cursor.close();
		return releases;	
	}	
	
	/*
	 * ANNOUNCEMENTS
	 */
	
	public ArrayList<Serie> getNewLicenses() {
		ArrayList<Serie> releases = new ArrayList<Serie>();
		Calendar today = Calendar.getInstance();	    
	    String year = Integer.toString(today.get(Calendar.YEAR));
	    			
		Cursor cursor = database.query(
				MySQLiteHelper.TABLE_NEW_LICENSES + " , " + MySQLiteHelper.TABLE_SERIES,
				newLicensesColumns,
				MySQLiteHelper.COLUMN_ID + " = " + MySQLiteHelper.COLUMN_ID_NEW
				+ " AND substr("+MySQLiteHelper.COLUMN_DAY+",1,4) = '" + year + "'",
		        null,
		        null,
		        null,
		        MySQLiteHelper.COLUMN_DAY
		);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Serie serie = cursorToLicense(cursor);
			releases.add(serie);
			cursor.moveToNext();
		}
		    
		cursor.close();
		return releases;
	}
	
	
	
	private SerieCol cursorToSerieCol(Cursor cursor) {
		SerieCol serie = new SerieCol();
		serie.setId(cursor.getLong(0));		
		serie.setTitle(cursor.getString(1));
		serie.setOwn(cursor.getInt(2));
		serie.setTotal_vol(cursor.getInt(3));
		
		return serie;
	}
	
	private Volume cursorToRelease(Cursor cursor) {
		Volume volume = new Volume();
		volume.setId(cursor.getLong(0));
		volume.setId_serie(cursor.getLong(1));
		volume.setTitle(cursor.getString(2));
		volume.setNum(cursor.getInt(3));
		volume.setRelease_date(cursor.getString(4));
		
		return volume;
	}
	
	private Serie cursorToLicense(Cursor cursor) {
		Serie serie = new Serie();
		serie.setId(cursor.getLong(0));		
		serie.setName(cursor.getString(1));
		serie.setDay(cursor.getString(2));
				
		return serie;
	}
	
		
	
	public void getSerieInfo(long id) {
		// OBTENIR INFORMACIÓ COMPLETA SERIE
		// return serieInfo
	}
	
	public void addLicense(long id, String day) {
	    ContentValues values = new ContentValues();
	    values.put(MySQLiteHelper.COLUMN_ID_NEW, id);
	    values.put(MySQLiteHelper.COLUMN_DAY, day);	   
	    
	    database.insert(MySQLiteHelper.TABLE_NEW_LICENSES, null, values);
	}

	public void deleteLicense(long id) {
		database.delete(MySQLiteHelper.TABLE_NEW_LICENSES, MySQLiteHelper.COLUMN_ID_NEW + " = " + id, null);
	}

}
