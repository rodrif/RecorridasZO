package com.recorridaszo.recorridaszo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import java.sql.Connection;


public class MainActivity extends FragmentActivity {
	Connection conexionMySQL;

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
}
