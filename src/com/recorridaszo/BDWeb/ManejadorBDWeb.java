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

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.recorridaszo.persona.Persona;
import com.recorridaszo.persona.Personas;
import com.recorridaszo.recorridaszo.Actualizable;
import com.recorridaszo.recorridaszo.ActualizablePersona;
import com.recorridaszo.recorridaszo.Utils;


public class ManejadorBDWeb {
	String lista;
	InputStream is=null;
	String result=null;
	String line=null;
	Object res;

	private static ManejadorBDWeb INSTANCE = new ManejadorBDWeb();

	// El constructor privado no permite que se genere un constructor por
	// defecto.
	// (con mismo modificador de acceso que la definición de la clase)
	private ManejadorBDWeb() {
	}

	public static ManejadorBDWeb getInstance() {
		return INSTANCE;
	}

	public void obtenerPersonasDBWeb(Context ctx, Actualizable actualizable) {
		ObtenerPersonasAsyncTask at = new ObtenerPersonasAsyncTask(ctx, actualizable);
		at.execute(ctx);
	}
	
	public void borrar(Personas personas) {		
		JSONArray jsonArray = personas.toJsonArray();
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("lista", jsonArray.toString()));

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Utils.WEB_BORRAR);
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
			Log.d(Utils.APPTAG, "id borrado: "+result);
		}catch (Exception e) {
			Log.e("Fail 2", e.toString());
		}
	}

	
	public String insertar(Persona persona, Context ctx, ActualizablePersona aPersona) {
		InsertarAsyncTask at = new InsertarAsyncTask(persona, ctx, null,aPersona);
		at.execute(ctx);
		return "bien";//FIXME pase lo que pase siempre retorna bien
	}

	public Persona buscar(LatLng latLng) {
		Persona perResultado = null;
//		BuscarAsyncTask at = new BuscarAsyncTask(perResultado, ctx, );//FIXME devolver bien el resultado
		return perResultado;		
	}	
}
