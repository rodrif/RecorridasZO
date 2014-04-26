package com.recorridaszo.recorridaszo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;
import com.recorridaszo.BDLocal.ManejadorBDLocal;
import com.recorridaszo.persona.Persona;

public class FormularioFragment extends Fragment {
	private ManejadorBDLocal ml;
	LatLng latLng;
	EditText nombre;
	EditText apellido;
	EditText descripcion;
	EditText direccion;
	EditText zona;

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

		Button botonOk = (Button) getActivity().findViewById(R.id.buttonOk);
		botonOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				clickEnBotonOK(view);
			}
		});

		this.ml = ManejadorBDLocal.getInstance();
		double latitud = getActivity().getIntent().getExtras()
				.getDouble(Utils.KEY_LATITUD);
		double longitud = getActivity().getIntent().getExtras()
				.getDouble(Utils.KEY_LONGITUD);
		this.latLng = new LatLng(latitud, longitud);
		
		ml.conectarse(getActivity());
		Persona persona = ml.obtenerPersona(latLng);

		nombre = (EditText) getActivity().findViewById(R.id.eTNombre);
		apellido = (EditText) getActivity().findViewById(R.id.eTApellido);
		descripcion = (EditText) getActivity().findViewById(R.id.eTDescripcion);
		zona = (EditText) getActivity().findViewById(R.id.eTZona);
		direccion = (EditText) getActivity().findViewById(R.id.eTDireccion);

		if (persona != null) { // se quiere editar
			//FIXME solo se edita nombre y apellido???
			nombre.setText(persona.getNombre());
			apellido.setText(persona.getApellido());
		}
		
		Log.d(Utils.APPTAG, "nombre recibido: " + nombre.getText().toString());
	}

	public void clickEnBotonOK(View view) {
		Log.d(Utils.APPTAG, "Formulario fragment boton Ok apretado");
		Intent i = new Intent();
		i.putExtra(Utils.KEY_LATITUD, latLng.latitude);
		i.putExtra(Utils.KEY_LONGITUD, latLng.longitude);
		
		//FIXME la fecha habria que guardarla aca???
		Persona persona = new Persona(nombre.getText().toString(), apellido
				.getText().toString(),direccion.getText().toString(),zona.getText().toString(),
				descripcion.getText().toString(),this.latLng);
		ml.guardarPersona(persona);
		
		Log.d(Utils.APPTAG, "nombre guardado: " + nombre.getText().toString());

		getActivity().setResult(Activity.RESULT_OK, i);
		getActivity().finish();
	}
}
