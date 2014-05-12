package com.recorridaszo.utilitarios;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class LocationListenerEx implements LocationListener{
	Location ubicacion = null;
	
	public LocationListenerEx(Location ubicacion) {
		this.ubicacion = ubicacion; 
	}
	@Override
	public void onLocationChanged(Location location) {
		this.ubicacion.set(location);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
