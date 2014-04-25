package com.recorridaszo.persona;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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