package org.promad.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ProMadActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/TSC_Comic.ttf");

        String url = "http://apify.heroku.com/api/tsciipmadurai.json";
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet();
        try {
			request.setURI(new URI(url));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        BufferedReader in = null;
        HttpResponse response = null;
        try {
			response = client.execute(request);
			Log.d("Promad", response.getEntity().toString());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        try {
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        StringBuffer buff = new StringBuffer("");
        String line = "";
        try {
			while((line = in.readLine()) != null){
				buff.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        LinearLayout pmworks_table = (LinearLayout) findViewById(R.id.pmworks_table);
        try {
        	JSONArray jsonArray = new JSONArray(buff.toString());
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