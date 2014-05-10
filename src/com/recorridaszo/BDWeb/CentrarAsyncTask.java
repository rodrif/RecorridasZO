package com.recorridaszo.BDWeb;

import java.util.List;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.recorridaszo.interfaces.Centrable;
import com.recorridaszo.utilitarios.LocationListenerEx;
import com.recorridaszo.utilitarios.Utils;


public class CentrarAsyncTask extends AsyncTask<Context, Void, LatLng>  {
	Context localContext;
	private Centrable centrable = null;
	Location location = null;
	LocationListener locListener = null;
	LocationManager lm = null;
	
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
	    this.locListener = new LocationListenerEx(new Location(bestProvider));	  
	    lm.requestLocationUpdates(bestProvider, 0,0,locListener);
     
	    for(int i = 0; i < Utils.CANTIDAD_INTENTOS_UBICACION; i++){
	    	 try {
	    		 Thread.sleep(1000);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
	    	 if(!(this.location.getLatitude() == 0.0 && this.location.getLongitude() == 0.0))
	    		 return new LatLng(this.location.getLatitude(), this.location.getLongitude());	    	 
	     }     
	     
	     return null;
	}
	
	@Override
	protected void onPostExecute(LatLng resultado) {
		//Me desregistro
		this.lm.removeUpdates(locListener);
		
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
}
