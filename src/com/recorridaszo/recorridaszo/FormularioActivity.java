package com.recorridaszo.recorridaszo;

import com.google.android.gms.maps.model.LatLng;
import com.recorridaszo.BDLocal.ManejadorBDLocal;
import com.recorridaszo.persona.Persona;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.EditText;

public class FormularioActivity extends FragmentActivity {
	private ManejadorBDLocal ml;
	LatLng latLng;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_formulario);

		this.ml = ManejadorBDLocal.getInstance();
		double latitud = getIntent().getExtras().getDouble(Utils.KEY_LATITUD);
		double longitud = getIntent().getExtras().getDouble(Utils.KEY_LONGITUD);
		this.latLng = new LatLng(latitud, longitud);

		ml.conectarse(this);
		Persona persona = ml.obtenerPersona(latLng);

		EditText nombre = (EditText) findViewById(R.id.eTNombre);
		EditText apellido = (EditText) findViewById(R.id.eTApellido);
		nombre.setText(persona.getNombre());
		apellido.setText(persona.getNombre());
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.formulario, menu);
		return true;
	}
}
