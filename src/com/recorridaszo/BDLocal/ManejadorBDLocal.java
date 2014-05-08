package com.recorridaszo.BDLocal;

import java.util.Iterator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.recorridaszo.persona.CargadorPersona;
import com.recorridaszo.persona.Persona;
import com.recorridaszo.persona.Personas;
import com.recorridaszo.utilitarios.Utils;

public class ManejadorBDLocal {
	private SQLiteDatabase db;

	private static ManejadorBDLocal INSTANCE = new ManejadorBDLocal();

	// El constructor privado no permite que se genere un constructor por
	// defecto.
	// (con mismo modificador de acceso que la definición de la clase)
	private ManejadorBDLocal() {
	}

	public static ManejadorBDLocal getInstance() {
		return INSTANCE;
	}

	public void conectarse(Context contexto) {
		// Abrimos la base de datos 'DBPersonas' en modo escritura
		PersonasSQLiteHelper psdbh = new PersonasSQLiteHelper(contexto,
				"DBPersonas", null, 1);

		this.db = psdbh.getWritableDatabase();
	}

	public void desconectarse() {
		// Cerramos la base de datos
		if (db != null)
			db.close();
		this.db = null;
	}

	// TODO: cambiarlo por uno q devuelva Personas
	public Cursor selectTodo() {
		if (db != null) {
			String[] campos = Utils.camposBD;

			Cursor c = db.query(Utils.TPersonas, campos, null, null, null,
					null, null);

			return c;
		}
		return null;
	}

	public int borrarTodo() {
		if (db != null) {
			db.delete(Utils.TPersonas, null, null);
			return 0;
		}
		return -1;
	}

	public synchronized int guardarPersona(Persona persona) {
		if (db != null) {
			// Creamos el registro a insertar como objeto ContentValues
			ContentValues nuevoRegistro = CargadorPersona
					.cargarContentValues(persona);

			// si estaba en la BDLocal
			if (obtenerPersona(persona.getUbicacion()) != null) {
				actualizarPersona(persona);
			} else {
				// Insertamos el registro en la base de datos
				if (!persona.getEstado().equals(Utils.EST_BORRADO)) {
					db.insert(Utils.TPersonas, null, nuevoRegistro);
					Log.d(Utils.APPTAG,
							"Se guardo persona con id: " + persona.getId());
				}
			}

			return 0;
		}
		return -1;
	}

	public int actualizarPersona(Persona persona) {
		if (db != null) {
			String[] args = new String[] {
					String.valueOf(persona.getLatitud()),
					String.valueOf(persona.getLongitud()) };
			// Actualizar, utilizando argumentos
			ContentValues valores = CargadorPersona
					.cargarContentValues(persona);
			db.update("Personas", valores, "latitud=? AND longitud=?", args);

			return 0;
		}

		return -1;
	}

	public Persona obtenerPersona(LatLng latLng) {
/*		if (db != null) { //FIXME: revisar q este bien arreglar por Gonzalo
			String[] campos = Utils.camposBD;
			String[] args = new String[] { String.valueOf(latLng.latitude),
					String.valueOf(latLng.longitude) };

			Cursor c = db.query("Personas", campos, "latitud=? AND longitud=?",
					args, null, null, null);

			Persona persona = CargadorPersona.cargarPersona(c);			

			return persona;
		}*/
		
		Cursor c = this.selectTodo();
		Personas personas = CargadorPersona.cargarPersonas(c);
		Iterator<Persona> it = personas.iterator();
		
		while(it.hasNext()) {
			Persona posiblePersona = it.next();
			
			if(((Math.abs(posiblePersona.getLatitud() - posiblePersona.getLatitud()) <= Utils.PRECISION)) &&
				(Math.abs(posiblePersona.getLongitud() - posiblePersona.getLongitud()) <= Utils.PRECISION)) {
					return posiblePersona;
				}
		}		
		
		return null;
	}

	private synchronized int eliminarPersonas(String estado) {
		if (db != null) {
			String[] args = new String[] { estado };

			db.delete("Personas", "estado=?", args);

			return 0;
		}
		return -1;
	}

	public synchronized int eliminarPersonasActualizadas() {
		int resultado;

		resultado = eliminarPersonas(Utils.EST_ACTUALIZADO);
		return resultado;
	}

	public synchronized int eliminarPersona(LatLng latLng) {
		if (db != null) {
			String[] args = new String[] { String.valueOf(latLng.latitude),
					String.valueOf(latLng.longitude) };

			Persona persona = this.obtenerPersona(latLng);

			if (persona != null) {
				if (persona.getEstado().equals(Utils.EST_NUEVO)
						|| persona.getEstado().equals(Utils.EST_BORRADO)) {
					db.delete("Personas", "latitud=? AND longitud=?", args);
				} else {
					// Actualizar, utilizando argumentos
					ContentValues valores = new ContentValues();
					valores.put("estado", Utils.EST_BORRADO);
					db.update("Personas", valores, "latitud=? AND longitud=?",
							args);
					return 0;
				}
			}
		}
		return -1;
	}

	private Personas obtenerPersonasSegunEstado(String estado) {
		if (db != null) {
			Personas personas = new Personas();
			Cursor c = this.selectTodo();
			Personas todasPersonas = CargadorPersona.cargarPersonas(c);

			for (Iterator<Persona> it = todasPersonas.iterator(); it.hasNext();) {
				Persona p = it.next();

				if (p.getEstado().equals(estado)) {
					personas.addPersona(p);
				}
			}
			return personas;
		}
		return null;
	}

	public Personas obtenerPersonasNuevas() {
		return this.obtenerPersonasSegunEstado(Utils.EST_NUEVO);
	}

	public Personas obtenerPersonasModificadas() {
		return this.obtenerPersonasSegunEstado(Utils.EST_MODIFICADO);
	}

	public Personas obtenerPersonasBorradas() {
		return this.obtenerPersonasSegunEstado(Utils.EST_BORRADO);
	}

	public String getUltFechaMod() {
		if (db != null) {
			String[] campos = Utils.camposBD;

			Cursor c = db.query("Personas", campos, null, null, null, null,
					"ultMod DESC", "1");

			Persona persona = CargadorPersona.cargarPersona(c);

			if (persona == null)
				return Utils.FECHA_CERO;

			return persona.getUltMod();
		}

		return Utils.FECHA_CERO;
	}

}
