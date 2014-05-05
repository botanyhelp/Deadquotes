package com.jimsuplee.deadquotes;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
//import android.net.Uri;
import android.widget.TextView;
//import android.widget.Button;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.widget.Toast;
//import android.content.Intent;
import android.database.Cursor;
//import android.text.Html;
//import android.util.Log;
import android.view.View;
import android.widget.EditText;
//import android.widget.DatePicker;
//import android.widget.Toast;
//import java.util.Date;
//import java.text.SimpleDateFormat;
//import android.widget.ImageView;
//import java.util.HashMap;
//import java.util.Map;
//import android.widget.AutoCompleteTextView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
//import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Deadquotes extends Activity {
	DBAdapter db;
	static final String TAG = "DEAD";
	String[] speakers;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deadquotes);
		try {
			String destPath = "/data/data/" + getPackageName() + "/databases";
			File f = new File(destPath);
			if (!f.exists()) {
				f.mkdirs();
				f.createNewFile();
				Toast.makeText(this, "Please Wait...Database Being Created",
						Toast.LENGTH_LONG).show();
				CopyDB(getBaseContext().getAssets().open("deadquotes"),
						new FileOutputStream(destPath + "/deadquotes"));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Now that we (hopefully) have an SQLiteDatabase available, lets make
		// an adapter and put it
		// into our global variable.
		db = new DBAdapter(this);
		speakers = getResources().getStringArray(R.array.speakers);
		Spinner s1 = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, speakers);
        s1.setAdapter(adapter);
        s1.setOnItemSelectedListener(new OnItemSelectedListener()
        {
                @Override
                public void onItemSelected(AdapterView<?> arg0, 
                View arg1, int arg2, long arg3)
                {
                        int index = arg0.getSelectedItemPosition();
                        db.open();
                        String quoteParam = speakers[index];
                        Cursor c = db.getByQuote(quoteParam);
                		//Log.w(TAG, "In Deadquotes.onClickQuote(), about to db.close()");
                		db.close();
                		//Log.w(TAG, "In Deadquotes.onClickQuote(), Got cursor from db.getByQuote("+quoteParam+"), now loop");
                        String results = "";
                        if (c != null) {
                			//Log.w(TAG, "In Deadquotes.onClickQuote(), cursor c is not null.");
                		}
                		//Log.w(TAG, "In Deadquotes.onClickQuote(), about to c.moveToFirst()");
                		if (c.moveToFirst()) {
                			//Log.w(TAG,"In Deadquotes.onClickQuote(), c.moveToFirst() returned true");
                			do {
                				results +=  c.getString(0)+"\n\n" ;

                			} while (c.moveToNext());
                		}else {
                			//Log.w(TAG, "In Deadquotes.onClickQuote(), c.moveToFirst() returned NOT TRUE");
                		}
                		//Log.w(TAG, "In Deadquotes.onClickQuote(), about to db.close()");
                		db.close();
                        TextView tv = (TextView) findViewById(R.id.txt_quotedata);
                        tv.setText(results);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) { }
        });

	
	}
	
	public void CopyDB(InputStream inputStream, OutputStream outputStream)
			throws IOException {
		// copy 1024 bytes at a time
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, length);
		}
		inputStream.close();
		outputStream.close();
	}
	
    public void onClickQuote(View view) {
	    //Log.w(TAG, "In Deadquotes.onClickQuote()");
        EditText txt_quote = (EditText) findViewById(R.id.txt_quote);
        String quoteParam = txt_quote.getText().toString();
        //Log.w(TAG, "In Deadquotes.onClickQuote(), about to db.open()");
        db.open();
        //Log.w(TAG, "In Deadquotes.onClickQuote(), about to call db.getByQuote("+quoteParam+")");
        Cursor c = db.getByQuote(quoteParam);
		//Log.w(TAG, "In Deadquotes.onClickQuote(), about to db.close()");
		db.close();
		//Log.w(TAG, "In Deadquotes.onClickQuote(), Got cursor from db.getByQuote("+quoteParam+"), now loop");
        String results = "";
        if (c != null) {
			//Log.w(TAG, "In Deadquotes.onClickQuote(), cursor c is not null.");
		}
		//Log.w(TAG, "In Deadquotes.onClickQuote(), about to c.moveToFirst()");
		if (c.moveToFirst()) {
			//Log.w(TAG,"In Deadquotes.onClickQuote(), c.moveToFirst() returned true");
			do {
				results +=  c.getString(0)+"\n\n" ;

			} while (c.moveToNext());
		}else {
			//Log.w(TAG, "In Deadquotes.onClickQuote(), c.moveToFirst() returned NOT TRUE");
		}
		//Log.w(TAG, "In Deadquotes.onClickQuote(), about to db.close()");
		db.close();
        TextView tv = (TextView) findViewById(R.id.txt_quotedata);
        tv.setText(results);
    }
    public void onClickAllQuotes(View view) {
	    //Log.w(TAG, "In Deadquotes.onClickAllQuotes()");
        //EditText txt_quote = (EditText) findViewById(R.id.txt_quote);
        //String quoteParam = txt_quote.getText().toString();
	    String quoteParam = "__";
	    //Log.w(TAG, "In Deadquotes.onClickAllQuotes(), about to db.open()");
        db.open();
        //Log.w(TAG, "In Deadquotes.onClickAllQuotes(), about to call db.getByQuote("+quoteParam+")");
        Cursor c = db.getByQuote(quoteParam);
		//Log.w(TAG, "In Deadquotes.onClickAllQuotes(), about to db.close()");
		db.close();
		//Log.w(TAG, "In Deadquotes.onClickAllQuotes(), Got cursor from db.getByQuote("+quoteParam+"), now loop");
        String results = "";
        if (c != null) {
			//Log.w(TAG, "In Deadquotes.onClickAllQuotes(), cursor c is not null.");
		}
		//Log.w(TAG, "In Deadquotes.onClickAllQuotes(), about to c.moveToFirst()");
		if (c.moveToFirst()) {
			//Log.w(TAG, "In Deadquotes.onClickAllQuotes(), c.moveToFirst() returned true");
			do {
				results +=  c.getString(0)+"\n\n" ;

			} while (c.moveToNext());
		}else {
			//Log.w(TAG, "In Deadquotes.onClickAllQuotes(), c.moveToFirst() returned NOT TRUE");
		}
		//Log.w(TAG, "In Deadquotes.onClickAllQuotes(), about to db.close()");
		db.close();
        TextView tv = (TextView) findViewById(R.id.txt_quotedata);
        tv.setText(results);   	
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.deadquotes, menu);
		return true;
	}

}