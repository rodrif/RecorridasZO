package com.recorridaszo.BDWeb;

import java.util.List;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;
import com.recorridaszo.interfaces.Centrable;
import com.recorridaszo.utilitarios.Utils;


public class CentrarAsyncTask extends AsyncTask<Context, Void, LatLng>  implements LocationListener{
	Context localContext;
	private Centrable centrable = null;
	private Location location = null;
	private LocationManager lm = null;
	
	public CentrarAsyncTask(Context context){
		this.localContext = context;  	 
	}
	
	public CentrarAsyncTask(){
		this(null);
	}
	
	@Override
	protected LatLng doInBackground(Context... params) {
		String bestProvider;
		lm = (LocationManager) this.localContext.getSystemService(Context.LOCATION_SERVICE);

		//Lista de proveedores
		List<String> listaProviders = lm.getAllProviders();		
		Log.d(Utils.APPTAG, listaProviders.toString());
		
		//Elijo proveedor segun criterio de busqueda
	    Criteria criteria = new Criteria();
	    criteria.setAccuracy(Criteria.ACCURACY_COARSE);
	    bestProvider = lm.getBestProvider(criteria, true);
	    Log.d(Utils.APPTAG, "Utilizando proveedor: " + bestProvider);
	    //Me registro a los updates
	    
	    //Nos registramos para recibir actualizaciones de la posición
	    
	    lm.requestLocationUpdates(bestProvider, 0,0,this);
     
	    for(int i = 0; i < Utils.CANTIDAD_INTENTOS_UBICACION; i++){
	    	 try {
	    		 Thread.sleep(1000);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
	    	 if(this.location != null)
	    		 return new LatLng(this.location.getLatitude(), this.location.getLongitude());	    	 
	     }	     
	     return null;
	}
	
	@Override
	protected void onPostExecute(LatLng resultado) {
		//Me desregistro
		this.lm.removeUpdates(this);
		
		String msg = "Ubicación no encontrada";
		if(resultado != null) {
			msg = "Ubicación encontrada";
			if(this.centrable != null)
				this.centrable.centrar(resultado);	
		}		
		
		Toast toast =
				Toast.makeText(this.localContext,
						msg, Toast.LENGTH_LONG);
			toast.show();	
	}
	
	@Override
	public void onLocationChanged(Location location) {
		this.location = location;
	}

	@Override
	public void onProviderDisabled(String provider) {		
	}

	@Override
	public void onProviderEnabled(String provider) {		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}	
}
