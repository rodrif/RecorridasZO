package com.recorridaszo.recorridaszo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FormularioFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.formulario_fragment, container,
				false);
		return view;
	}	
	
	@Override
	public void onActivityCreated(Bundle state) {
		super.onActivityCreated(state);
	}



}
