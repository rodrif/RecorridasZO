package com.recorridaszo.recorridaszo;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaActivity extends FragmentActivity {
	private GoogleMap mapa = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_mapa);
		final Context ctx = this;

		mapa = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();

		mapa.setOnMapClickListener(new OnMapClickListener() {
			public void onMapClick(LatLng point) {
				dibujarMarcador(point);
			}
		});
		
		mapa.setOnMarkerClickListener(new OnMarkerClickListener() {
		    public boolean onMarkerClick(Marker marker) {
				Log.d(Utils.APPTAG, "Lanzando VerPersona activity");
				Intent intent = new Intent(ctx, VerPersona.class);
				startActivity(intent);
		        return true;
		    }
		});
		
		CameraUpdate camUpd = CameraUpdateFactory.newLatLngZoom(new LatLng(
				-34.6209083, -58.4587529), 10);
		mapa.moveCamera(camUpd);
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	/*
	 * Called when the system detects that this Activity is now visible.
	 */
	@Override
	public void onResume() {
		super.onResume();
	}

	public void onBotonOkClick(View view) {
	}

	public void onBotonBuscarClick(View view) {
	}

	protected class GetLatLngTask extends AsyncTask<String, Void, String> {
		private Address ad;

		// Store the context passed to the AsyncTask when the system
		// instantiates it.
		Context localContext;

		// Constructor called by the system to instantiate the task
		public GetLatLngTask(Context context) {

			// Required by the semantics of AsyncTask
			super();

			// Set a Context for the background task
			localContext = context;
		}

		@Override
		protected String doInBackground(String... params) {

			Geocoder geocoder = new Geocoder(localContext);

			// Create a list to contain the result address
			List<Address> addresses = null;

			// Try to get an address for the current location.
			try {
				addresses = geocoder.getFromLocationName(params[0]
						+ ", argentina", 1); // REVISAR
				ad = addresses.get(0);

			} catch (Exception exception1) {
				exception1.printStackTrace();
				return "direccion invalida";
			}
			// If the reverse geocode returned an address
			if (addresses != null && addresses.size() > 0) {
				// Return the text
				return params[0];

				// If there aren't any addresses, post a message
			}
			return "fallo";
		}

		@Override
		protected void onPostExecute(String address) {

		}
	}

	/**
	 * Dibuja un marcador en el mapa
	 */
	private void dibujarMarcador(LatLng point) {
		mapa.addMarker(new MarkerOptions().position(point).draggable(true)
				.title("Sin datos"));
	}
}
