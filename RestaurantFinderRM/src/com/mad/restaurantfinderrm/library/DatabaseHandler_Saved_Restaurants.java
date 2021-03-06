package com.mad.restaurantfinderrm.library;

import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler_Saved_Restaurants extends SQLiteOpenHelper {

	public DatabaseHandler_Saved_Restaurants(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
	
	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "savedRestaurant";
 
    // Login table name
    private static final String TABLE_FAV = "fav";
    
    // Login Table Columns names
    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "restaurantName";
    private static final String KEY_TYPE = "restaurantType";
    private static final String KEY_UID = "restaurantID";
	
    //create table
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.v("DB", "creating");
		String CREATE_FAV_TABLE = "CREATE TABLE " + TABLE_FAV + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_TYPE + " TEXT,"
                + KEY_UID + " TEXT" + ")";
        db.execSQL(CREATE_FAV_TABLE);
        Log.v("DB", "Succesfully created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAV);
 
        // Create tables again
        //onCreate(db);
	}
	
	/**
     * Storing user details in database
     * */
    public void addRest(String name, String type, String uid) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_TYPE, type);
        values.put(KEY_UID, uid); // Email
 
        // Inserting Row
        db.insert(TABLE_FAV, null, values);
        db.close(); // Closing database connection
    }
    
    public void deleteFav(String uid) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	String sql = "DELETE FROM " + TABLE_FAV + " WHERE restaurantID=" + uid;
    	db.execSQL(sql);
    	
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String,String> user = new HashMap<String,String>();
        String selectQuery = "SELECT  * FROM " + TABLE_FAV;
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            user.put("name", cursor.getString(1));
            user.put("uid", cursor.getString(2));
        }
        cursor.close();
        db.close();
        // return user
        return user;
    }
    
    /**
     * Getting user login status
     * return true if rows are there in table
     * */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_FAV;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
 
        // return row count
        return rowCount;
    }
    /* * Re crate database
    * Delete all tables and create them again
    * */
   public void resetTables(){
       SQLiteDatabase db = this.getWritableDatabase();
       // Delete All Rows
       db.delete(TABLE_FAV, null, null);
       db.close();
   }
    
}
