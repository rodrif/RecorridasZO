package com.recorridaszo.recorridaszo;

import com.google.android.gms.maps.model.LatLng;
import com.recorridaszo.BDLocal.ManejadorBDLocal;
import com.recorridaszo.persona.Persona;
import com.recorridaszo.utilitarios.Utils;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
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
		Log.e(Utils.APPTAG, "Lat problema = " + latLng.latitude);

		TextView nombre = (TextView) findViewById(R.id.textViewNombre);
		nombre.setText(persona.getNombre());
		TextView apellido = (TextView) findViewById(R.id.textViewApellido);
		apellido.setText(persona.getApellido());
		
		TextView direccion = (TextView) findViewById(R.id.textViewDireccion);
		direccion.setText(persona.getDireccion());
		
		TextView zona = (TextView) findViewById(R.id.textViewZona);
		zona.setText(persona.getZona());
		
		TextView descripcion = (TextView) findViewById(R.id.textViewDescripcion);
		descripcion.setText(persona.getDescripcion());
		
		
		
	}

	@Override
	public void onPause() {
		super.onPause();
		ml.desconectarse();
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
