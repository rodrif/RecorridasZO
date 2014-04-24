package com.recorridaszo.persona;

import java.util.ArrayList;
import java.util.Iterator;

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
}
