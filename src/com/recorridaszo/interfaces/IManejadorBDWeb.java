package com.recorridaszo.interfaces;

import android.content.Context;
import com.recorridaszo.persona.Persona;


public interface IManejadorBDWeb {

	public void obtenerPersonasDBWeb(Context ctx, Actualizable actualizable);
	
	public void borrarDBWEB();
	
	public String insertar(Persona persona, Context ctx, ActualizablePersona aPersona);

}
