package com.recorridaszo.recorridaszo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;


public class FormularioActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_formulario);
	}

}
