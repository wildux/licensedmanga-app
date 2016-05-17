/**
 * 
 */
package com.licensedmanga.licensedmangaapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Wildux
 *
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "manga.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_SERIES = "series";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_TITLE = "name";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_STATE = "state";
	public static final String COLUMN_TOTAL_VOL = "total_vol";
	
	public static final String TABLE_WATCHLIST = "watchlist";
	public static final String COLUMN_ID_MYSERIE = "id_myserie";
		
	public static final String TABLE_VOLUMES = "volumes";
	public static final String COLUMN_ID_VOL = "id_volume";
	public static final String COLUMN_ID_SERIE = "id_serie";
	public static final String COLUMN_NUM = "num";
	public static final String COLUMN_RELEASE_DATE = "release_date";
	
	public static final String TABLE_MY_VOLUMES = "myVolumes";
	public static final String COLUMN_ID_MYVOL = "id_myvolume";
	public static final String COLUMN_LOCATION = "location";
	public static final String COLUMN_READ = "read";
		
	public static final String TABLE_NEW_LICENSES = "announcements";
	public static final String COLUMN_ID_NEW = "id_new";
	public static final String COLUMN_DAY = "day";
  
	// DB CREATION SQL STATEMENTS
	private static final String DB_SERIES_CREATE = "create table "
			+ TABLE_SERIES 		+ "(" 
			+ COLUMN_ID 		+ " integer primary key, "
			+ COLUMN_TITLE 		+ " text not null, "
			+ COLUMN_TYPE 		+ " integer not null, "
			+ COLUMN_STATE 		+ " integer not null, "
			+ COLUMN_TOTAL_VOL 	+ " integer not null);";
	
	private static final String DB_VOLUMES_CREATE = "create table "
			+ TABLE_VOLUMES 		+ "(" 
			+ COLUMN_ID_VOL 		+ " integer primary key, "
			+ COLUMN_ID_SERIE 		+ " integer not null, "
			+ COLUMN_NUM 			+ " integer not null, "
			+ COLUMN_RELEASE_DATE 	+ " text not null);";
	
	private static final String DB_MYVOLUMES_CREATE = "create table "
			+ TABLE_MY_VOLUMES 	+ "(" 
			+ COLUMN_ID_MYVOL 	+ " integer primary key, "
			+ COLUMN_LOCATION 	+ " text not null, "
			+ COLUMN_READ 		+ " integer not null);";
	
	private static final String DB_WATCHLIST_CREATE = "create table "
			+ TABLE_WATCHLIST 	+ "(" 
			+ COLUMN_ID_MYSERIE	+ " integer primary key);";
	
	private static final String DB_NEWLICENSES_CREATE = "create table "
			+ TABLE_NEW_LICENSES 	+ "(" 
			+ COLUMN_ID_NEW			+ " integer primary key, "
			+ COLUMN_DAY 		+ " text not null);";	
	
	
	
	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DB_SERIES_CREATE);		
		database.execSQL(DB_VOLUMES_CREATE);
		database.execSQL(DB_MYVOLUMES_CREATE);
		database.execSQL(DB_WATCHLIST_CREATE);
		database.execSQL(DB_NEWLICENSES_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " 
				+ oldVersion + " to " + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERIES);		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOLUMES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MY_VOLUMES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_WATCHLIST);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEW_LICENSES);
		onCreate(db);
	}

} 
