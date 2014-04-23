package com.recorridaszo.recorridaszo;

import com.google.android.gms.maps.model.LatLng;
import com.recorridaszo.BDLocal.ManejadorBDLocal;
import com.recorridaszo.persona.Persona;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class VerPersona extends FragmentActivity {
	private ManejadorBDLocal ml;

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
		LatLng latLng = new LatLng(latitud, longitud);

		ml.conectarse(this);
		Persona persona = ml.obtenerPersona(latLng);

		TextView nombre = (TextView) findViewById(R.id.textViewNombre);
		nombre.setText(persona.getNombre());
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
		startActivity(intent);
	}
}
