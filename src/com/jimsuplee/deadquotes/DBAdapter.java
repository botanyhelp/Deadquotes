package com.jimsuplee.deadquotes;

//import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;
//import java.util.ArrayList;

public class DBAdapter {
	
	static final String TAG = "DEAD";
	
	static final String DATABASE_NAME = "deadquotes";
	static final String DATABASE_TABLE = "deadquotes";
	static final int DATABASE_VERSION = 1;
	
	static final String quote = "quote";
	
	static final String DATABASE_CREATE = "CREATE TABLE deadquotes (quote text default null);";
	
	final Context context;

	DatabaseHelper DBHelper;
	SQLiteDatabase db;

	public DBAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(DATABASE_CREATE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS deadquotes");
			onCreate(db);
		}
	}
	
	public DBAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		DBHelper.close();
	}
	public Cursor getByQuote(String quoteParam) throws SQLException {
		//Log.w(TAG, "In DBAdapter.getByQuote("+quoteParam+")");
		String[] columns = new String[] {quote};				
		String selection;
		//selection = "Country=? ORDER BY Startyear LIMIT 100";
		//selection = "location=?";
		//selection = "setone LIKE ? OR settwo LIKE ?";
		//String selectionConcatenation = songoneParam+"_"+songtwoParam+"_"+songthreeParam;
		//String[] selectionArgs = new String[] { selectionConcatenation, selectionConcatenation };
		//selection = "lyrics LIKE ?";
	    quoteParam = "%"+quoteParam+"%";
	    //selection = "title=?";
	    //selection = "title LIKE ?";
	    selection = "quote LIKE ?";
		String[] selectionArgs = new String[] { quoteParam };
		//Log.w(TAG, "In DBAdapter.getByQuote, about to dbquery("+selection+" "+selectionArgs[0]);
        Cursor mCursor = db.query(DATABASE_TABLE, columns, selection, selectionArgs, null, null, null);
		//Log.w(TAG, "In DBAdapter.getByQuote(String quoteParam), About to check if Cursor c is null");
		if (mCursor != null) {
		    //Log.w(TAG,"In DBAdapter.getByQuote(String quoteParam), c is NOT null, about to NOT c.moveToFirst()");
			//mCursor.moveToFirst();
			if(mCursor.moveToFirst()) {
				//Log.w(TAG,"In DBAdapter.getByQuote(),mCursor.moveToFirst() is true");
			} else {
				//Log.w(TAG,"In DBAdapter.getByQuote(),mCursor.moveToFirst() is NOT true");	
			}
		}
		//Log.w(TAG, "In DBAdapter.getByQuote(String lyricParam), about to return cursor, c");
		return mCursor;
	}
}
