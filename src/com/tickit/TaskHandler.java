package com.tickit;

import java.util.ArrayList;

import com.tickit.model.CardListItem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TaskHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_NAME = "tasksManager";
	
	private static final String TABLE_TASKS = "tasks";
	
	private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DATE = "due_date";
    private static final String KEY_DONE = "login_type";
    
    public TaskHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_DATE + " TEXT," + KEY_DONE + " TEXT" + ");";
        db.execSQL(CREATE_TASKS_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	       db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS  +";");
	       
	        // Create tables again
	        onCreate(db);
	}
	
	public ArrayList<CardListItem> getTasks(){
		SQLiteDatabase db = this.getReadableDatabase();
		
		ArrayList<CardListItem> cardListItems = new ArrayList<CardListItem>();
		
		Cursor cursor = db.query(TABLE_TASKS, new String[] {KEY_ID, KEY_NAME, KEY_DATE, KEY_DONE}, null, null, null, null, KEY_DATE + " DESC");
		
		if (cursor.moveToFirst()) {
            do {
                String[] arr = cursor.getString(2).split("__,__");
                // Adding contact to list
                cardListItems.add(new CardListItem(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), arr));
            } while (cursor.moveToNext());
        }
		
		return cardListItems;
	}
	
	public CardListItem getLastTask(){
		SQLiteDatabase db = this.getReadableDatabase();
		
		CardListItem cardListItem = new CardListItem();
		
		String query = "SELECT * FROM " +TABLE_TASKS+ " ORDER BY " +KEY_ID+" DESC LIMIT 1;";
		
		Cursor cursor = db.rawQuery(query, null);
		if (cursor.moveToFirst()) {
            do {
                String[] arr = cursor.getString(3).split("__,__");
                //TODO fix done
                //wwould have to have a status thing, and find an array of everyone with status = not done
                //then arrange them into a string and publish it.
                // Adding contact to list
                cardListItem = new CardListItem(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), arr);
            } while (cursor.moveToNext());
        }
		
		return cardListItem;
	}
	
	public int getAccountCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TASKS +";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        
 
        // return count
        return cursor.getCount();
    }
	
	public void createTask(String name, String date, String id){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues(); 
		values.put(KEY_ID, id); 
        values.put(KEY_NAME, name); 
        values.put(KEY_DATE, date);
        
        db.insert(TABLE_TASKS, null, values);
        Log.i("Task validated!", "Task successfully inserted!");
		//TODO
		
	}
    
    
	
}
