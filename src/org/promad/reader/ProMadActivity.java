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
import android.widget.TextView;

public class ProMadActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView textView = (TextView)findViewById(R.id.main_text_view);
        Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/TSC_Comic.ttf");
        textView.setTypeface(tf);

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

        byte[] responseText = null;
        String responseString = "";
        try {
        	JSONArray jsonArray = new JSONArray(buff.toString());
        	for (int i = 0; i < 111; i++) {
            	JSONObject jsonObject = jsonArray.getJSONObject(i);
            	responseString += "title: " + jsonObject.getString("title");
            	responseString += ", author: " + jsonObject.getString("author");
            	responseString += ", urls: " + jsonObject.getJSONArray("tscii_url") + "\n";
            }
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
        	responseText = responseString.getBytes("ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        Log.d("Promad", responseText.toString());
        textView.setText(new String(responseText, Charset.forName("ISO-8859-1")));
    }
}