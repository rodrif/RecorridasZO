package com.recorridaszo.BDWeb;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import android.util.Log;


public class ManejadorBDWeb {
	String id;
	String lista;
	InputStream is=null;
	String result=null;
	String line=null;

	private static ManejadorBDWeb INSTANCE = new ManejadorBDWeb();

	// El constructor privado no permite que se genere un constructor por
	// defecto.
	// (con mismo modificador de acceso que la definición de la clase)
	private ManejadorBDWeb() {
	}

	public static ManejadorBDWeb getInstance() {
		return INSTANCE;
	}

	public JSONArray select() {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("id", id));

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://pruebazo.atwebpages.com/pruebajson.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			Log.e("pass 1", "connection success ");
		} catch (Exception e) {
			Log.e("Fail 1", e.toString());
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
			Log.e("pass 2", "connection success ");
		} catch (Exception e) {
			Log.e("Fail 2", e.toString());
		}

		try {
			JSONObject json_data = new JSONObject(result);
			lista = (json_data.getString("info"));
			Log.e("pass 3", "connection success ");
			JSONArray json_list = new JSONArray(lista);			
			Log.d("ZO", json_list.get(0).toString());
			return json_list;
		} catch (Exception e) {			
			Log.e("Fail 3", e.toString());
		}
		return null;
	}
	
}
