package com.recorridaszo.recorridaszo;

import com.google.android.gms.maps.model.LatLng;

public class Persona {
	private int id; //TODO: ver como se va a usar
	private String nombre;
	private String apellido;
	private String direccion;
	private String descripcion;
	private LatLng ubicacion;
	private String ultMod;

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

	public int getId() {
		return id;
	}

	public String getApellido() {
		return apellido;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public LatLng getUbicacion() {
		return ubicacion;
	}
	
	public String getUltMod() {
		return ultMod;
	}
}
