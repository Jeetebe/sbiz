package utils;

import java.util.ArrayList;

import object.Song;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public final class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;
	private static DatabaseHandler sInstance;
	private static SQLiteDatabase myWritableDb;
	// Database Name
	private static final String DATABASE_NAME = "dbmusicquiz";
	// Newss table name
	private static final String TABLE_BHYEUTHICH = "tbdachoi";
	// Newss Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_TONEID = "toneid";
	private static final String KEY_TONENAME = "tonename";
	private static final String KEY_TONECODE = "tonecode";
	private static final String KEY_SINGER = "singer";
	private static final String KEY_DOWNTIMES = "downtimes";
	private static final String KEY_PRICES = "prices";
	

	public static synchronized DatabaseHandler getInstance(Context context) {	
	    if (sInstance == null) {
	      sInstance = new DatabaseHandler(context.getApplicationContext());
	    }
	    return sInstance;
	  }
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);		
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		//db = this.getMyWritableDatabase();
		String CREATE_NewsS_TABLE = "CREATE TABLE " + TABLE_BHYEUTHICH + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," 
				+ KEY_TONEID + " TEXT,"
				+ KEY_TONENAME + " TEXT,"
				+ KEY_TONECODE + " TEXT,"
				+ KEY_SINGER + " TEXT,"
				+ KEY_DOWNTIMES + " TEXT," 				
				+ KEY_PRICES +" TEXT)";
		db.execSQL(CREATE_NewsS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db = this.getMyWritableDatabase();	
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BHYEUTHICH);	
		onCreate(db);
	}

	public void deleteAlldata()
	{
	    SQLiteDatabase db = this.getMyWritableDatabase();	
	    db.execSQL("delete  from " + TABLE_BHYEUTHICH);
	    db.close();
	}
	public void deletedatabysong(Song song)
	{
	    SQLiteDatabase db = this.getMyWritableDatabase();	
	    db.execSQL("delete  from " + TABLE_BHYEUTHICH + " where toneid='"+song.getToneid()+"'");
	    db.close();
	}
	
	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */
	
	// Adding new News
	public void addNewsSong(Song song) {
		SQLiteDatabase db = this.getMyWritableDatabase();
		ContentValues values = new ContentValues();		 
		values.put(KEY_TONEID, song.getToneid());
		values.put(KEY_TONENAME, song.getTonename());
		values.put(KEY_TONECODE, song.getTonecode());
		values.put(KEY_SINGER, song.getSinger()); 
		values.put(KEY_DOWNTIMES, song.getDowntimes()); 
		values.put(KEY_PRICES, song.getPrice());
		db.insert(TABLE_BHYEUTHICH, null, values);
		//Log.e("lichcupdien", "insert:"+News.getkhuvuc());
		db.close(); // Closing database connection
	}
	
	public ArrayList<Song> get_Allsong() {
		ArrayList<Song> NewsList = new ArrayList<Song>();
		
		String selectQuery = "SELECT  * FROM " + TABLE_BHYEUTHICH;
		SQLiteDatabase db = this.getMyWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
	
		if (cursor.moveToFirst()) {
			do {
				Song song = new Song();
			
				song.setToneid(cursor.getString(1));
				song.setTonename(cursor.getString(2));
				song.setTonecode(cursor.getString(3));
				song.setSinger(cursor.getString(4));
				song.setDowntimes(cursor.getString(5));
				song.setPrice(cursor.getString(6));
				
				NewsList.add(song);
				//Log.e("lichcupdien", "get data from DB:"+cursor.getString(5));
			} while (cursor.moveToNext());
		}

		// return News list
		return NewsList;
	}
	public Song get_firstSong() {
		SQLiteDatabase db = this.getMygetReadableDatabase();
	
		String selectQuery = "SELECT  * FROM " + TABLE_BHYEUTHICH + " ORDER BY " + KEY_ID ;
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor != null)
			cursor.moveToFirst();
			
		Song song = new Song();			
		song.setToneid(cursor.getString(1));
		song.setTonename(cursor.getString(2));
		song.setTonecode(cursor.getString(3));
		song.setSinger(cursor.getString(4));
		song.setDowntimes(cursor.getString(5));
		song.setPrice(cursor.getString(6));
		
		return song;
	}
	public boolean Checksong(Song song) {
		//String kq="chuacheck";
		boolean kq=false;
		String tonename=song.getTonename();
		String countQuery = "SELECT  * FROM " + TABLE_BHYEUTHICH + " where tonename='"+tonename+"' ";
		SQLiteDatabase db = this.getMygetReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		
		// return count
			if  (cursor.getCount()>0)
			{kq= true;}
			else
			{kq=false;}
			cursor.close();
		return kq;
		
	}
	public void deleteSong(Song song)
	{
	    SQLiteDatabase db = this.getMyWritableDatabase();	
	    db.execSQL("delete  from " + TABLE_BHYEUTHICH + " where toneid='"+song.getToneid()+"'");
	    db.close();
	}
	 @Override
	    public synchronized void close() {
	        if (sInstance != null)
	        	myWritableDb.close();
	    }
	 public SQLiteDatabase getMyWritableDatabase() {
	        if ((myWritableDb == null) || (!myWritableDb.isOpen())) {
	        	myWritableDb = this.getWritableDatabase();
	        }	 
	        return myWritableDb;
	    }
	 public SQLiteDatabase getMygetReadableDatabase() {
	        if ((myWritableDb == null) || (!myWritableDb.isOpen())) {
	        	myWritableDb = this.getReadableDatabase();
	        }	 
	        return myWritableDb;
	    }	 
}
