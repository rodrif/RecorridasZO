package com.recorridaszo.recorridaszo;

import com.google.android.gms.maps.model.LatLng;

public class Persona {
	private int id;
	private String nombre;
	private String apellido;
	private String direccion;
	private String descripcion;
	private LatLng ubicacion;

	public Persona(String nombre, String apellido) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = "";
		this.descripcion = "";
		this.ubicacion = null;
	}

	public String getNombre() {
		return this.nombre;
	}

}
