package com.recorridaszo.recorridaszo;

import com.recorridaszo.BDWeb.ManejadorBDWeb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;


public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onBotonMapaClick(View view) {
		Log.d(Utils.APPTAG, "Lanzando mapa activity");
		Intent intentMapa = new Intent(this, MapaActivity.class);
		startActivity(intentMapa);
	}
	
	public void onBotonActualizarClick(View view) {
		Log.d(Utils.APPTAG, "Lanzando Actualizar");
		ManejadorBDWeb.getInstance().obtenerActualizacion();
	}
	
	public void onBotonBorrarClick(View view) {
		Log.d(Utils.APPTAG, "Lanzando Borrar");
//TODO		ManejadorBDWeb.getInstance().borrar(1);
	}
	
}
