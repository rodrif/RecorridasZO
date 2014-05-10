package com.recorridaszo.BDWeb;

import java.io.InputStream;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import com.recorridaszo.interfaces.Actualizable;
import com.recorridaszo.interfaces.ActualizablePersona;
import com.recorridaszo.interfaces.IManejadorBDWeb;
import com.recorridaszo.persona.Persona;
import com.recorridaszo.utilitarios.Utils;


public class ManejadorBDWebConcreto implements IManejadorBDWeb {
	String lista;
	InputStream is=null;
	String result=null;
	String line=null;
	Object res;

	private static ManejadorBDWebConcreto INSTANCE = new ManejadorBDWebConcreto();

	// El constructor privado no permite que se genere un constructor por
	// defecto.
	// (con mismo modificador de acceso que la definición de la clase)
	private ManejadorBDWebConcreto() {
	}

	public static ManejadorBDWebConcreto getInstance() {
		return INSTANCE;
	}

	public void obtenerPersonasDBWeb(Context ctx, Actualizable actualizable) {
		ObtenerPersonasAsyncTask at = new ObtenerPersonasAsyncTask(ctx, actualizable);
		at.execute(ctx);
	}
	
	public void borrarDBWEB() {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Utils.WEB_BORRAR_DB);
			httpclient.execute(httppost);
			Log.d(Utils.APPTAG, "connection success borrarDBWeb ");
		} catch (Exception e) {
			Log.e(Utils.APPTAG, e.toString());
		}		
	}
	
	public String insertar(Persona persona, Context ctx, ActualizablePersona aPersona) {
		InsertarAsyncTask at = new InsertarAsyncTask(persona, ctx, true,aPersona);
		at.execute(ctx);
		return "bien";//FIXME pase lo que pase siempre retorna bien
	}
/* TODO: no se usa
	public Persona buscar(LatLng latLng) {
		Persona perResultado = null;
//		BuscarAsyncTask at = new BuscarAsyncTask(perResultado, ctx, );//FIXME devolver bien el resultado
		return perResultado;		
	}	
*/	
	//TODO: revisar, todavia no se usa
/*	public void borrar(Personas personas) {		
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
*/	

}
