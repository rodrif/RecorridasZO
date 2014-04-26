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
	Persona persona;
	

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
		this.persona = ml.obtenerPersona(latLng);
		
		this.nombre = (EditText) getActivity().findViewById(R.id.eTNombre);
		this.apellido = (EditText) getActivity().findViewById(
				R.id.eTApellido);	
		this.descripcion = (EditText) getActivity().findViewById(R.id.eTDescripcion);
		this.zona = (EditText) getActivity().findViewById(R.id.eTZona);
		this.direccion = (EditText) getActivity().findViewById(R.id.eTDireccion);

		if (persona != null) { // se quiere editar
			//FIXME solo se edita nombre y apellido???
			nombre.setText(persona.getNombre());
			apellido.setText(persona.getApellido());
		}
		else { // si es una persona nueva
			this.persona = new Persona(this.latLng);
		}
		
		Log.d(Utils.APPTAG, "nombre recibido: " + nombre.getText().toString());
	}

	public void clickEnBotonOK(View view) {
		Log.d(Utils.APPTAG, "Formulario fragment boton Ok apretado");
		Intent i = new Intent();
		i.putExtra(Utils.KEY_LATITUD, latLng.latitude);
		i.putExtra(Utils.KEY_LONGITUD, latLng.longitude);
		
		this.persona.setNombre(nombre.getText().toString());
		this.persona.setApellido((nombre.getText().toString()));
		this.persona.setDireccion(direccion.getText().toString());
		this.persona.setZona(zona.getText().toString());
		this.persona.setDescripcion(descripcion.getText().toString());
		this.persona.setUbicacion(this.latLng);
		this.persona.setUltMod(Utils.getDateTime());
		if(this.persona.getEstado().equals(Utils.EST_ACTUALIZADO)) {
			this.persona.setEstado(Utils.EST_MODIFICADO);
		}

		ml.guardarPersona(this.persona);
		
		Log.d(Utils.APPTAG, "nombre guardado: " + nombre.getText().toString());

		getActivity().setResult(Activity.RESULT_OK, i);
		getActivity().finish();
	}
}
