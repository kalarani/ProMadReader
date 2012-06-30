package org.promad.reader;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.promad.reader.net.WebClient;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProMadActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/TSC_Comic.ttf");
        LinearLayout pmworks_table = (LinearLayout) findViewById(R.id.pmworks_table);
        try {
        	JSONArray jsonArray = WebClient.getWorks();
        	for (int i = 0; i < 111; i++) {
            	JSONObject jsonObject = jsonArray.getJSONObject(i);
            	pmworks_table.addView(getRow(jsonObject, tf));
            }
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    
    private TextView createView(String text, Typeface tf){
    	TextView view = new TextView(getApplicationContext());
    	view.setTypeface(tf);
    	view.setText(decode(text));
		return view;
    }
    
    private String decode(String text){
    	try {
    		return new String(text.getBytes("ISO-8859-1"), Charset.forName("ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	return "Exception occured while decoding";
    }
    
    private LinearLayout getRow(JSONObject jsonObject, Typeface tf) throws JSONException{
    	LinearLayout layout = new LinearLayout(getApplicationContext());
    	layout.setOrientation(LinearLayout.VERTICAL);
    	layout.addView(createView(jsonObject.getString("title"), tf));
    	layout.addView(createView(jsonObject.getString("author"), tf));
    	JSONArray urls = jsonObject.getJSONArray("tscii_url");
    	String urlStrings = "";
    	for(int j=0; j < urls.length(); j++){
    		urlStrings += "http://www.projectmadurai.org" + urls.getString(j);
    	}
    	layout.addView(createView(urlStrings, tf));
		return layout;
    }
}