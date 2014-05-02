package com.recorridaszo.mocks;

import java.util.Iterator;
import android.content.Context;
import com.recorridaszo.BDLocal.ManejadorBDLocal;
import com.recorridaszo.interfaces.Actualizable;
import com.recorridaszo.interfaces.ActualizablePersona;
import com.recorridaszo.interfaces.IManejadorBDWeb;
import com.recorridaszo.persona.Persona;
import com.recorridaszo.persona.Personas;
import com.recorridaszo.utilitarios.Utils;

public class ManejadorBDWebMock implements IManejadorBDWeb{
	private static int id = 0;
	private Personas personas;
	private ManejadorBDLocal ml;
	private static ManejadorBDWebMock INSTANCE = new ManejadorBDWebMock();

	private ManejadorBDWebMock() {
		personas = new Personas();
		ml = ManejadorBDLocal.getInstance();
	}
	
	public static ManejadorBDWebMock getInstance() {
		return INSTANCE;
	}

	@Override
	public void obtenerPersonasDBWeb(Context ctx, Actualizable actualizable) {
		ml.eliminarPersonasActualizadas();
		Iterator<Persona> it = personas.iterator();
		
		while(it.hasNext()) {
			Persona persona = it.next();
			ml.guardarPersona(persona);
		}
	}

	@Override
	public void borrarDBWEB() {
		personas.removeTodo();
		id = 0;
	}

	@Override
	public String insertar(Persona persona, Context ctx,
			ActualizablePersona aPersona) {

		if(persona.getEstado().equals(Utils.EST_NUEVO)) {
			id++;
			persona.setEstado(Utils.EST_ACTUALIZADO);
			persona.setId(id);			
			personas.addPersona(persona);
			ml.guardarPersona(persona);
		}
		else if (persona.getEstado().equals(Utils.EST_MODIFICADO)) {
			persona.setEstado(Utils.EST_ACTUALIZADO);
			personas.removePersona(persona.getUbicacion());
			personas.addPersona(persona);
			ml.guardarPersona(persona);
		}
		else if (persona.getEstado().equals(Utils.EST_BORRADO)) {
			personas.removePersona(persona.getUbicacion());
		}
		
		return "ok";
	}

}
