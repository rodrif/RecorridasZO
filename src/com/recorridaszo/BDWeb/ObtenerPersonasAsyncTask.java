package com.recorridaszo.BDWeb;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.recorridaszo.BDLocal.ManejadorBDLocal;
import com.recorridaszo.interfaces.Actualizable;
import com.recorridaszo.persona.Persona;
import com.recorridaszo.utilitarios.Utils;

public class ObtenerPersonasAsyncTask extends AsyncTask<Context, Void, String>{
	// Store the context passed to the AsyncTask when the system
	// instantiates it.
	Context localContext;
	InputStream is;
	String line;
	String result;
	Actualizable actualizable;

	// Constructor called by the system to instantiate the task
	public ObtenerPersonasAsyncTask(Context context, Actualizable actualizable) {
		// Required by the semantics of AsyncTask
		super();

		// Set a Context for the background task
		this.localContext = context;
		this.actualizable = actualizable;
	}

	
	public ObtenerPersonasAsyncTask(Context context) {
		this(context, null);
	}	
    @Override
    protected void onPreExecute() {

    }

	/**
	 * Get a geocoding service instance, pass latitude and longitude to it,
	 * format the returned address, and return the address to the UI thread.
	 */
	@Override
	protected String doInBackground(Context... params) {
		//FIXME Falta pedir datos a partir de una fecha determinada.
//		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//		nameValuePairs.add(new BasicNameValuePair("id", id));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Utils.WEB_ACTUALIZAR);
//			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
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
			
			JSONArray jArray = new JSONArray(result);
			JSONObject json_data=null;
			int id= 0;
			String nombre="";
			String apellido ="";
			String direccion= "";
			String zona= "";
			String descripcion= "";
			String latitudS = "";
			String longitudS = "";
			String ultMod = "";
			String estado = "";	
			
			//String ultMod= "";
			Persona personaTemp = null;
			ManejadorBDLocal ml = ManejadorBDLocal.getInstance();
			ml.conectarse(localContext);
			
	        for(int i=0;i<jArray.length();i++){
	                json_data = jArray.getJSONObject(i);
	                id= json_data.getInt("id");
	                nombre=json_data.getString("nombre");
	                apellido=json_data.getString("apellido");
	                direccion=json_data.getString("direccion");
	                zona=json_data.getString("zona");
	                descripcion=json_data.getString("descripcion");
	                latitudS = json_data.getString("latitud");
	                longitudS = json_data.getString("longitud");
	                estado = json_data.getString("estado");
	                ultMod = json_data.getString("ultMod");

	                personaTemp = new Persona(id, nombre, apellido, direccion,
	                		zona, descripcion, new LatLng(Double.parseDouble(latitudS),
	                				Double.parseDouble(longitudS)), ultMod, estado);
	                
	                ml.guardarPersona(personaTemp);

	        }	     

			Log.d("pass 2", "connection success ");
		} catch (Exception e) {
			Log.e("Fail 2", e.toString());
		}
		return "Fin de bajada de personas";
	}

	/**
	 * A method that's called once doInBackground() completes. Set the text of
	 * the UI element that displays the address. This method runs on the UI
	 * thread.
	 */
	@Override
	protected void onPostExecute(String resultado) {	
		Toast toast =
				Toast.makeText(this.localContext,
						resultado, Toast.LENGTH_LONG);
			toast.show();
			if(this.actualizable != null)
				this.actualizable.Actualizar();
	}
}
