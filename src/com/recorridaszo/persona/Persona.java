package com.recorridaszo.persona;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.recorridaszo.recorridaszo.Utils;

public class Persona {
	private int id; // -1 si es una persona no guardada en la BDWeb
	private String nombre;
	private String apellido;
	private String direccion;
	private String zona;
	private String descripcion;
	private LatLng ubicacion;
	private String ultMod;
	private String estado;

	public Persona(int id, String nombre, String apellido, String direccion,
			String zona, String descripcion, LatLng ubicacion, String ultMod,
			String estado) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.zona = zona;
		this.descripcion = descripcion;
		this.ubicacion = ubicacion;
		this.ultMod = ultMod;
		this.estado = estado;
	}

	public Persona(String nombre, String apellido, String direccion,
			String zona, String descripcion, LatLng ubicacion) {
		this(-1, nombre, apellido, direccion, zona, descripcion, ubicacion, Utils.getDateTime(), Utils.EST_NUEVO);
	}
	
	public Persona(String nombre, String apellido, LatLng latLng) {
		this(-1, nombre, apellido, "", "", "", latLng, Utils.getDateTime(), Utils.EST_NUEVO);
	}

	public Persona(LatLng latLng) {
		this(-1, "", "", "", "", "", latLng, Utils.getDateTime(), Utils.EST_NUEVO);
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
	
	public String getEstado() {
		return estado;
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
	
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("nombre", this.nombre);
			json.put("apellido", this.apellido);
			json.put("direccion", this.direccion);
			json.put("zona", this.zona);
			json.put("descripcion", this.descripcion);
			json.put("latitud", Double.toString(this.ubicacion.latitude));
			json.put("longitud", Double.toString(this.ubicacion.longitude));
			json.put("estado", this.estado);
			json.put("ultMod", this.ultMod);
			
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e(Utils.APPTAG, "Error al convertir a json");
		}
		
		return json;
	}
}
