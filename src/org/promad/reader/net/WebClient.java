package org.promad.reader.net;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.os.Environment;
import android.util.Log;

public class WebClient {
	public static JSONArray getWorks() throws JSONException{
        String url = "http://apify.heroku.com/api/tsciipmadurai.json";
        StringBuffer buff = new StringBuffer("");
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);
			BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	        String line = "";
			while((line = in.readLine()) != null){
				buff.append(line);
			}
			in.close();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
        Log.d("Promad", buff.toString());
		return new JSONArray(buff.toString());
	}
	
	public static String downloadFile(){
		String file_path = "";
		String url = "http://www.projectmadurai.org/pm_etexts/pdf/pm0049.pdf";
		try {
			URL uri = new URL(url);
			DataInputStream dis = new DataInputStream(uri.openStream());
			file_path = Environment.getExternalStorageDirectory() + "/pm0049.pdf";
			FileOutputStream fos = new FileOutputStream(new File(file_path));
			int length;
			byte[] buffer = new byte[1024];
			while((length = dis.read(buffer)) > 0){
				fos.write(buffer, 0, length);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file_path ;
	}
}
