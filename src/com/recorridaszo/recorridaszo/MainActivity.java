package com.recorridaszo.recorridaszo;

import com.recorridaszo.utilitarios.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;


public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
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
}
