package com.tickit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class EmailAccountHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "emailManager";
 
    // Contacts table name
    private static final String TABLE_ACCOUNTS = "accounts";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_LOGIN_TYPE = "login_type";
 
    public EmailAccountHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_ACCOUNTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_EMAIL + " TEXT," + KEY_PASSWORD + " TEXT," + KEY_LOGIN_TYPE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
 
        // Create tables again
        onCreate(db);
    }
 
    
 
    // Adding new contact
    public void addAccount(String _email, String _password, String _login) {
        SQLiteDatabase Mdb = this.getWritableDatabase();

        Mdb.delete(TABLE_ACCOUNTS, KEY_ID + " = ?",
                new String[] { "1" });
        Log.i("User validated!", "Previous account information deleted!");
        Mdb.close();
        
        SQLiteDatabase Adb = this.getWritableDatabase();
        
        ContentValues values = new ContentValues(); 
        values.put(KEY_EMAIL, _email); 
        values.put(KEY_PASSWORD, _password); 
        values.put(KEY_LOGIN_TYPE, _login);
 
        // Inserting Row
        Adb.insert(TABLE_ACCOUNTS, null, values);
        Log.i("User validated!", "Account successfully inserted!");
        Adb.close(); // Closing database connection
        Log.i("User validated!", "Database closed!");
        
        
        Log.i("User validated", "Total account count: " + getAccountCount());
        
    }
 
    // Getting single contact
    TickitUser getUser() {
    	Log.i("Retrieving User!", "Starting...");
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.rawQuery("Select * from "  + TABLE_ACCOUNTS,null);
        if (cursor != null) cursor.moveToFirst();
        Log.i("Retrieving User!", "Almost there...");
        TickitUser user = new TickitUser(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        Log.i("Retrieving User!", "Done...");
        return user;
    }
    
    public int getAccountCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ACCOUNTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        
 
        // return count
        return cursor.getCount();
    }
    
    public int getFacebookCount() {
    	String countQuery = "SELECT  * FROM " + TABLE_ACCOUNTS + " WHERE " + KEY_LOGIN_TYPE + " = 'facebook';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        
 
        // return count
        return cursor.getCount();
    	
    }
    
    public int getEmailCount() {
    	String countQuery = "SELECT  * FROM " + TABLE_ACCOUNTS + " WHERE " + KEY_LOGIN_TYPE + " = 'email';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        
 
        // return count
        return cursor.getCount();
    	
    }
     
 
}