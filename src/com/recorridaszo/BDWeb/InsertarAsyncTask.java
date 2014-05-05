package com.recorridaszo.BDWeb;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.recorridaszo.interfaces.ActualizablePersonas;
import com.recorridaszo.persona.Persona;
import com.recorridaszo.persona.Personas;
import com.recorridaszo.recorridaszo.R;
import com.recorridaszo.utilitarios.Utils;


public class InsertarAsyncTask extends AsyncTask<Context, Void, String> {

	// Store the context passed to the AsyncTask when the system
	// instantiates it.
	Context localContext;
	ProgressDialog progressDialog;
	Personas personas;
	InputStream is;
	String line;
	String result;
	ActualizablePersonas actualizable;
	

	// Constructor called by the system to instantiate the task
	public InsertarAsyncTask(Personas personas, Context context, ProgressDialog pDialog
			,ActualizablePersonas actualizable) {
		// Required by the semantics of AsyncTask
		super();

		// Set a Context for the background task
		this.localContext = context;
		this.personas = personas;
		if(pDialog != null)
			this.progressDialog = pDialog;
		this.actualizable = actualizable;
	}

	public InsertarAsyncTask(Personas personas, Context context, ProgressDialog pDialog) {
		this(personas, context, pDialog, null);
	}	
	
	@Override
    protected void onPreExecute() {
    	if(progressDialog != null)
    		progressDialog.show();
    }


    @Override
	protected String doInBackground(Context... params) {
    	//FIXME
    	JSONArray jsonArray = this.personas.toJsonArray();
		Persona unaPersona;
		Iterator<Persona> it;
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("personas", jsonArray.toString()));
		Log.d(Utils.APPTAG, "jsonArrayString Mandado a servidor: "+ jsonArray.toString());

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Utils.WEB_INSERTAR_PERSONAS);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//			StringEntity se = new StringEntity(jsonObject.toString());
//			httppost.setEntity(se);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			Log.d(Utils.APPTAG, "connection success ");
		} catch (Exception e) {
			Log.e(Utils.APPTAG, "Fail 1 (insertarTask): " + e.toString());
			return localContext.getString(R.string.error_conexion);
		}
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			is.close();
			result = sb.toString();
			/////////////////////////////////////////////////////
			JSONArray aux  = new JSONArray(result);
			this.personas = new Personas(aux);
			it = this.personas.iterator();
			while (it.hasNext()) {
				unaPersona = it.next();
				if(!unaPersona.getEstado().equals(Utils.EST_BORRADO))
					unaPersona.setEstado(Utils.EST_ACTUALIZADO);				
			}			
			////////////////////////////////////////////////////
/*			this.persona.setId(Integer.parseInt(result));
			if(!this.persona.getEstado().equals(Utils.EST_BORRADO))
				this.persona.setEstado(Utils.EST_ACTUALIZADO);*/
						
			Log.d(Utils.APPTAG, "jsonString: "+result);
		}catch (Exception e) {
			Log.e(Utils.APPTAG, "Fail 2 (insertarTask): " + e.toString());
			return localContext.getString(R.string.error_conexion);
		}		
		return "Subida correcta";	//TODO: desharcodear
	}

	/**
	 * A method that's called once doInBackground() completes. Set the text of
	 * the UI element that displays the address. This method runs on the UI
	 * thread.
	 */
	@Override
	protected void onPostExecute(String resultado) {
		if(progressDialog != null)
			progressDialog.dismiss();
		
		Toast toast =
				Toast.makeText(this.localContext,
						resultado, Toast.LENGTH_LONG);
			toast.show();
		if(this.actualizable != null)
			this.actualizable.ActualizarPersonas(this.personas);	
	}
}
