package com.recorridaszo.recorridaszo;

import java.io.IOException;
import java.util.List;
import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.recorridaszo.BDLocal.ManejadorBDLocal;

public class MapaActivity extends FragmentActivity {
	private GoogleMap mapa = null;
	private ManejadorBDLocal ml;
	private Address direccion;
	boolean dirEncontrada;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapa);

		mapa = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();

		mapa.setOnMapClickListener(new OnMapClickListener() {
			public void onMapClick(LatLng point) {
				clickEnMapa(point);
			}
		});

		mapa.setOnMarkerClickListener(new OnMarkerClickListener() {
			public boolean onMarkerClick(Marker marker) {
				clickEnMarcador(marker);
				return true;
			}
		});

		centrarCamara(-34.6209083, -58.4587529, Utils.ZOOM_LEJOS);
		Log.d(Utils.APPTAG, "onCreate MapaActivity");
	}

	private void centrarCamara(double Lat, double Lng, int zoom) {
		CameraUpdate camUpd = CameraUpdateFactory.newLatLngZoom(new LatLng(Lat,
				Lng), zoom);
		mapa.moveCamera(camUpd);
	}

	@Override
	public void onPause() {
		super.onPause();
		ml.desconectarse();
		Log.d(Utils.APPTAG, "onpause MapaActivity");
	}

	/*
	 * Called when the system detects that this Activity is now visible.
	 */
	@Override
	public void onResume() {
		super.onResume();
		this.ml = ManejadorBDLocal.getInstance();
		ml.conectarse(this);
		cargarMarcadores();
		Log.d(Utils.APPTAG, "onResume MapaActivity");
	}

	public void onBotonGuardarClick(View view) {
		// TODO: Subir los datos a la BDWeb
	}

	public void onBotonBuscarClick(View view) {
		GetLatLngTask miTarea = new GetLatLngTask(this);
		EditText et = (EditText) findViewById(R.id.editText1);
		String direccion = et.getText().toString();
		miTarea.execute(direccion);
	}

	protected class GetLatLngTask extends AsyncTask<String, Void, String> {
		// Store the context passed to the AsyncTask when the system
		// instantiates it.
		Context localContext;

		// Constructor called by the system to instantiate the task public
		GetLatLngTask(Context context) {
			// Required by the semantics of AsyncTask super();

			// Set a Context for the background task
			localContext = context;
		}

		@Override
		protected String doInBackground(String... params) {
			dirEncontrada = false;
			Geocoder geocoder = new Geocoder(localContext);

			// Create a list to contain the result address
			List<Address> addresses = null;

			// Try to get an address for the current location.
			try {
				addresses = geocoder.getFromLocationName(params[0]
						+ ", argentina", 1);

			} catch (IOException exception1) {
				exception1.printStackTrace();
				return getString(R.string.servicio_no_disponible);
			} catch (Exception exception2) {
				exception2.printStackTrace();
				return getString(R.string.direccion_invalida);
			}
			// If the reverse geocode returned an address
			if (addresses != null && addresses.size() > 0) { // Return the text
				direccion = addresses.get(0);
				dirEncontrada = true;
				return params[0];
			}

			// If there aren't any addresses, post a message
			return getString(R.string.direccion_invalida);
		}

		@Override
		protected void onPostExecute(String address) {
			Toast toast1 = Toast.makeText(getApplicationContext(), address,
					Toast.LENGTH_SHORT);
			toast1.show();
			if (dirEncontrada)
				centrarCamara(direccion.getLatitude(),
						direccion.getLongitude(), Utils.ZOOM_CERCA);
		}
	}

	public void clickEnMapa(LatLng point) {
		Log.d(Utils.APPTAG, "Lanzando Formulario activity");
		Intent intent = new Intent(this, FormularioActivity.class);
		intent.putExtra(Utils.KEY_LATITUD, point.latitude);
		intent.putExtra(Utils.KEY_LONGITUD, point.longitude);
		startActivityForResult(intent, Utils.REQ_CODE_FORMULARIO);
	}

	public void clickEnMarcador(Marker marker) {
		Log.d(Utils.APPTAG, "Lanzando VerPersona activity");
		Intent intent = new Intent(this, VerPersona.class);
		intent.putExtra(Utils.KEY_LATITUD, marker.getPosition().latitude);
		intent.putExtra(Utils.KEY_LONGITUD, marker.getPosition().longitude);
		startActivity(intent);
	}

	public void cargarMarcadores() {
		this.mapa.clear();
		Cursor c = ml.selectTodo();

		if (c.moveToFirst()) {
			do {
				int id = c.getInt(c.getColumnIndex("id"));
				double latitud = c.getDouble(c.getColumnIndex("latitud"));
				double longitud = c.getDouble(c.getColumnIndex("longitud"));
				dibujarMarcador(id, new LatLng(latitud, longitud));
			} while (c.moveToNext());
		}
	}

	/**
	 * Dibuja un marcador en el mapa
	 */
	private void dibujarMarcador(int id, LatLng point) { // TODO: drag true
		Marker marcador = mapa.addMarker(new MarkerOptions().position(point)
				.draggable(false).title("Sin datos"));

		if (id == -1) { // si es una perosona nueva, no gauardada en la BDWeb
			marcador.setIcon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
		} else {
			marcador.setIcon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		// Choose what to do based on the request code
		switch (requestCode) {

		case Utils.REQ_CODE_FORMULARIO: // nueva Persona creada
			Log.d(Utils.APPTAG,
					"onActivityResult Formulario Activity desde MapaActivity");

			if (resultCode == Activity.RESULT_OK) {
				Log.d(Utils.APPTAG, "Result OK");

				Double latitud = intent.getExtras()
						.getDouble(Utils.KEY_LATITUD);
				Double longitud = intent.getExtras().getDouble(
						Utils.KEY_LONGITUD);
				dibujarMarcador(-1, new LatLng(latitud, longitud));
			}
			break;
		}
	}

}
