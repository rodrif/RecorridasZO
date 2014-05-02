package com.recorridaszo.interfaces;

import android.content.Context;
import com.recorridaszo.persona.Persona;
import com.recorridaszo.persona.Personas;


public interface IManejadorBDWeb {

	public void obtenerPersonasDBWeb(Context ctx, Actualizable actualizable);
	
	public void borrarDBWEB();	
	
	public String insertar(Personas personas, Context ctx, ActualizablePersonas aPersonas);

}
