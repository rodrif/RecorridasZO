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
import org.json.JSONObject;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.recorridaszo.interfaces.ActualizablePersona;
import com.recorridaszo.persona.Persona;
import com.recorridaszo.recorridaszo.R;
import com.recorridaszo.utilitarios.Utils;


public class InsertarAsyncTask extends AsyncTask<Context, Void, String> {

	// Store the context passed to the AsyncTask when the system
	// instantiates it.
	Context localContext;
	ProgressDialog pDialog = null;
	Persona persona;
	InputStream is;
	String line;
	String result;
	ActualizablePersona actualizable;
	

	// Constructor called by the system to instantiate the task
	public InsertarAsyncTask(Persona persona, Context context, Boolean dialog
			,ActualizablePersona actualizable) {
		// Required by the semantics of AsyncTask
		super();

		// Set a Context for the background task
		this.localContext = context;
		this.persona = persona;
		if(dialog) {
			pDialog = new ProgressDialog(this.localContext);
			pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDialog.setMessage("Subiendo al servidor...");
			pDialog.setCancelable(false);
			pDialog.setMax(100);
		}
		this.actualizable = actualizable;
	}

	public InsertarAsyncTask(Persona persona, Context context, Boolean dialog) {
		this(persona, context, dialog, null);
	}	
	
    @Override
    protected void onPreExecute() {
    	if(pDialog != null)
    		pDialog.show();
    }


    @Override
	protected String doInBackground(Context... params) {
		JSONObject jsonObject = persona.toJson();
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("persona", jsonObject.toString()));
		Log.d(Utils.APPTAG, "jsonString Mandado a servidor: "+ jsonObject.toString());

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Utils.WEB_INSERTAR);
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
			
			this.persona.setId(Integer.parseInt(result));
			if(!this.persona.getEstado().equals(Utils.EST_BORRADO))
				this.persona.setEstado(Utils.EST_ACTUALIZADO);
						
			Log.d(Utils.APPTAG, "jsonString: "+result);
		}catch (Exception e) {
			Log.e(Utils.APPTAG, "Fail 2 (insertarTask): " + e.toString());
			return localContext.getString(R.string.error_conexion);
		}
		
		return localContext.getString(R.string.subida_correcta);
	}

	/**
	 * A method that's called once doInBackground() completes. Set the text of
	 * the UI element that displays the address. This method runs on the UI
	 * thread.
	 */
	@Override
	protected void onPostExecute(String resultado) {
		if(pDialog != null)
			pDialog.dismiss();
		
		Toast toast =
				Toast.makeText(this.localContext,
						resultado, Toast.LENGTH_LONG);
			toast.show();
		if(this.actualizable != null)
			this.actualizable.ActualizarPersona(this.persona);	
	}
}
