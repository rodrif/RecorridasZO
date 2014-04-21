package com.recorridaszo.recorridaszo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import java.sql.Connection;

import com.recorridaszo.BDLocal.ManejadorBDLocal;

public class MainActivity extends FragmentActivity {
	Connection conexionMySQL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ManejadorBDLocal ml = new ManejadorBDLocal(this);
		ml.conectarsePrueba();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
