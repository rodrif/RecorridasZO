package com.recorridaszo.persona;

import com.google.android.gms.maps.model.LatLng;

import android.content.ContentValues;
import android.database.Cursor;

public class CargadorPersona {

	public CargadorPersona() {

	}

	//Pre: el cursor solo tiene 1 persona
	public static Persona cargarPersona(Cursor c) {
		if (c.getCount() == 0) {
			return null;
		} else {
			c.moveToFirst();
			return obtenerPersonaPosAct(c);
		}
	}
	
	//para un cursor con una o mas personas
	public static Personas cargarPersonas(Cursor c) {
		Personas personas = new Personas();		
		
		if(c.getCount() > 0) {
			c.moveToFirst();
			do {
				Persona persona = obtenerPersonaPosAct(c);
				personas.addPersona(persona);
			} while (c.moveToNext());			
		}
		
		return personas;
	}
	
	private static Persona obtenerPersonaPosAct(Cursor c) {
		int id = c.getInt(c.getColumnIndex("id"));
		String nombre = c.getString(c.getColumnIndex("nombre"));
		String apellido = c.getString(c.getColumnIndex("apellido"));
		String direccion = c.getString(c.getColumnIndex("direccion"));
		String zona = c.getString(c.getColumnIndex("zona"));
		String descripcion = c.getString(c.getColumnIndex("descripcion"));
		double latitud = c.getDouble(c.getColumnIndex("latitud"));
		double longitud = c.getDouble(c.getColumnIndex("longitud"));
		String ultMod = c.getString(c.getColumnIndex("ultMod"));
		LatLng ubicacion = new LatLng(latitud, longitud);
		String estado = c.getString(c.getColumnIndex("estado"));

		Persona persona = new Persona(id, nombre, apellido, direccion,
				zona, descripcion, ubicacion, ultMod, estado);
		return persona;			
	}
	
	public static ContentValues cargarContentValues(Persona persona) {
		ContentValues nuevoRegistro = new ContentValues();
		nuevoRegistro.put("id", persona.getId());
		nuevoRegistro.put("nombre", persona.getNombre());
		nuevoRegistro.put("apellido", persona.getApellido());
		nuevoRegistro.put("direccion", persona.getDireccion());
		nuevoRegistro.put("zona", persona.getZona());
		nuevoRegistro.put("descripcion", persona.getDescripcion());
		if (persona.getUbicacion() != null) {
			nuevoRegistro.put("latitud", persona.getUbicacion().latitude);
			nuevoRegistro.put("longitud", persona.getUbicacion().longitude);
		}
		nuevoRegistro.put("ultMod", persona.getUltMod());
		nuevoRegistro.put("estado", persona.getEstado());
		
		return nuevoRegistro;		
	}

}
