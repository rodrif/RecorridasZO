package com.recorridaszo.persona;

import com.google.android.gms.maps.model.LatLng;

public class Persona {
	private int id; // -1 si es una persona no guardada en la BDWeb
	private String nombre;
	private String apellido;
	private String direccion;
	private String zona;
	private String descripcion;
	private LatLng ubicacion;
	private String ultMod; // TODO: ver si solo va en la BD

	public Persona(int id, String nombre, String apellido, String direccion,
			String zona, String descripcion, LatLng ubicacion, String ultMod) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.zona = zona;
		this.descripcion = descripcion;
		this.ubicacion = ubicacion;
		this.ultMod = ultMod;
	}

	public Persona(String nombre, String apellido) {
		this(-1, nombre, apellido, "", "", "", null, "");
	}

	public Persona(String nombre, String apellido, LatLng latLng) {
		this(-1, nombre, apellido, "", "", "", latLng, "");
	}
	
	public Persona(LatLng latLng) {
		this(-1, "", "", "", "", "", latLng, "");
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

	public String getZona() {
		return zona;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public LatLng getUbicacion() {
		return ubicacion;
	}

	public double getLatitud() {
		return ubicacion.latitude;
	}

	public double getLongitud() {
		return ubicacion.longitude;
	}

	public String getUltMod() {
		return ultMod;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setUbicacion(LatLng ubicacion) {
		this.ubicacion = ubicacion;
	}

	public void setUltMod(String ultMod) {
		this.ultMod = ultMod;
	}
}
