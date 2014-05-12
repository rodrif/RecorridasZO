package com.recorridaszo.recorridaszo;

import android.util.Log;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.recorridaszo.utilitarios.Utils;

public class MarcadorOptionsFactory {

	public MarcadorOptionsFactory() {

	}

	public static MarkerOptions crearOpciones(String estado, LatLng point) {
		// REVISAR
		MarkerOptions opciones = new MarkerOptions().position(point)
				.draggable(true).title(String.valueOf(point.latitude))
				.snippet(String.valueOf(point.longitude));

		// si es una perosona nueva, no gauardada en la BDWeb
		if (estado.equals(Utils.EST_NUEVO)) {
			opciones.icon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
		} else if (estado.equals(Utils.EST_ACTUALIZADO)) {
			opciones.icon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
		} else if (estado.equals(Utils.EST_MODIFICADO)) {
			opciones.icon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
		} else {
			opciones.icon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_RED));
			Log.e(Utils.APPTAG, "Estado inexistente");
		}

		return opciones;
	}

}
