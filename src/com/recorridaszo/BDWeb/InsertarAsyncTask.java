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

import com.recorridaszo.persona.Persona;
import com.recorridaszo.recorridaszo.Utils;


public class InsertarAsyncTask extends AsyncTask<Context, Void, String> {

	// Store the context passed to the AsyncTask when the system
	// instantiates it.
	Context localContext;
	ProgressDialog progressDialog;
	Persona persona;
	InputStream is;
	String line;
	String result;

	// Constructor called by the system to instantiate the task
	public InsertarAsyncTask(Persona persona, Context context, ProgressDialog pDialog) {
		// Required by the semantics of AsyncTask
		super();

		// Set a Context for the background task
		this.localContext = context;
		this.persona = persona;
		if(pDialog != null)
			this.progressDialog = pDialog;
	}
	
    @Override
    protected void onPreExecute() {
    	if(progressDialog != null)
    		progressDialog.show();
    }

	/**
	 * Get a geocoding service instance, pass latitude and longitude to it,
	 * format the returned address, and return the address to the UI thread.
	 */
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
			Log.d(Utils.APPTAG, "jsonString: "+result);
		}catch (Exception e) {
			Log.e("Fail 2", e.toString());
		}
//		return result;
		return "OK"; //REVISAR
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
	}
}