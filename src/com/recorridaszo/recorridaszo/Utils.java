package com.recorridaszo.recorridaszo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class Utils {

	public static final String APPTAG = "RecorridasZO";
	public static final String[] camposBD = new String[] { "id", "nombre",
			"apellido", "direccion", "zona", "descripcion", "latitud",
			"longitud", "ultMod", "estado" };
	public static final String TPersonas = "Personas";
	public static final String EST_ACTUALIZADO = "Actualizado";
	public static final String EST_BORRADO = "Borrado";
	public static final String EST_MODIFICADO = "Modificado";
	public static final String EST_NUEVO = "Nuevo";
	public static final String KEY_LATITUD = "com.recorridaszo.recorridaszo.KEY_LATITUD";
	public static final String KEY_LONGITUD = "com.recorridaszo.recorridaszo.KEY_LONGITUD";

	public static String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}
}
