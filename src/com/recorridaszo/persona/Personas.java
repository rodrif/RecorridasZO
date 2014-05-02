package com.recorridaszo.persona;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

public class Personas {
	ArrayList<Persona> personas;

	public Personas() {
		personas = new ArrayList<Persona>();
	}
	
	public void addPersona(Persona persona) {
		personas.add(persona);
	}
	
	public boolean removePersona(Persona persona) {
		return personas.remove(persona);
	}
	
	public void removePersona(LatLng ubicacion) {
		Iterator<Persona> it = personas.iterator();
		
		while(it.hasNext()) {
			Persona persona = it.next();
			
			if((persona.getLatitud() == ubicacion.latitude) &&
					(persona.getLongitud() == ubicacion.longitude)) {
				removePersona(persona);
				return;
			}
		}
	}
	
	public void removeTodo() {
		personas.clear();
	}
	
	public int size(){
		return personas.size();	
	}
	
	public Iterator<Persona> iterator() {
		return personas.iterator();
	}

	public JSONArray toJsonArray() {
		JSONArray resultado = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		
		for(Persona persona : personas) {
		    try {
				jsonObject.put("id", Integer.toString(persona.getId()));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		    resultado.put(jsonObject);
		}
		return resultado;
	}	
}
