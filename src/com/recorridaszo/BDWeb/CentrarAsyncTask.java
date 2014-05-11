package com.recorridaszo.BDWeb;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.model.LatLng;
import com.recorridaszo.interfaces.Centrable;
import com.recorridaszo.utilitarios.Utils;


public class CentrarAsyncTask extends AsyncTask<Void, Void, String> implements GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener{
	Context localContext;
	private Centrable centrable = null;
	private Location location = null;
	private LocationClient mLocationClient;
	
	public CentrarAsyncTask(Context context, Centrable centrable){
		super();
		this.localContext = context;
		this.centrable = centrable;
		this.mLocationClient = new LocationClient(localContext, this, this);
		
	}
	
	public CentrarAsyncTask(){
		this(null, null);
	}
	
	@Override
	protected String doInBackground(Void... params) {
		mLocationClient.connect();

		//FIXME mejorar
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@Override
	protected  void onPostExecute(String str) {		
		String msg = "Ubicación no encontrada";
		if(this.location != null) {
			msg = "Ubicación encontrada";
			if(this.centrable != null) {
				this.centrable.centrar(new LatLng(this.location.getLatitude(), this.location.getLongitude()));	
				Log.d(Utils.APPTAG, "centrar camara");
			}
		}
		
		Log.d(Utils.APPTAG, "Lat: " +this.location.getLatitude());
		
		Toast toast =
				Toast.makeText(this.localContext,
						msg, Toast.LENGTH_LONG);
			toast.show();	
		mLocationClient.disconnect();		
	}	


	@Override
	public void onConnected(Bundle arg0) {
		this.location = mLocationClient.getLastLocation();
		
	}

	@Override
	public void onDisconnected() {
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		
	}	
}
