package com.recorridaszo.recorridaszo;

import com.google.android.gms.maps.model.LatLng;
import com.recorridaszo.BDLocal.ManejadorBDLocal;
import com.recorridaszo.persona.Persona;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class VerPersona extends FragmentActivity {
	private ManejadorBDLocal ml;
	LatLng latLng; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ver_persona);
	}

	@Override
	public void onResume() {
		super.onResume();
		this.ml = ManejadorBDLocal.getInstance();
		double latitud = getIntent().getExtras().getDouble(Utils.KEY_LATITUD);
		double longitud = getIntent().getExtras().getDouble(Utils.KEY_LONGITUD);
		this.latLng = new LatLng(latitud, longitud);

		ml.conectarse(this);
		Persona persona = ml.obtenerPersona(latLng);

		TextView nombre = (TextView) findViewById(R.id.textViewNombre);
		nombre.setText(persona.getNombre());
		TextView apellido = (TextView) findViewById(R.id.textViewApellido);
		apellido.setText(persona.getApellido());
	}

	@Override
	public void onPause() {
		super.onPause();
		ml.desconectarse();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ver_persona, menu);
		return true;
	}

	public void onClickEditar(View v) {
		Log.d(Utils.APPTAG, "Lanzando Formulario activity");
		Intent intent = new Intent(this, FormularioActivity.class);
		intent.putExtra(Utils.KEY_LATITUD, this.latLng.latitude);
		intent.putExtra(Utils.KEY_LONGITUD, this.latLng.longitude);
		startActivityForResult(intent, Utils.REQ_CODE_FORMULARIO);
	}
	
	public void onClickEliminar(View v) {
		ml.eliminarPersona(this.latLng);
		finish();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		// Choose what to do based on the request code
		switch (requestCode) {

		case Utils.REQ_CODE_FORMULARIO: // persona editada
			Log.d(Utils.APPTAG, "onActivityResult Formulario Activity desde VerPersona");

			if (resultCode == Activity.RESULT_OK) {
				Log.d(Utils.APPTAG, "Result OK");
				finish();				
			}
			break;
		}
	}
}
