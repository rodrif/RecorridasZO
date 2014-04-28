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

	public int guardarPersona(Persona persona) {
		if (db != null) {
			// Creamos el registro a insertar como objeto ContentValues
			ContentValues nuevoRegistro = CargadorPersona
					.cargarContentValues(persona);

			// si estaba en la BDLocal
			if (obtenerPersona(persona.getUbicacion()) != null) {
				eliminarPersona(persona.getUbicacion());
			}

			// Insertamos el registro en la base de datos
			long res = db.insert(Utils.TPersonas, null, nuevoRegistro);
			Log.d(Utils.APPTAG, "Se guardo persona con id: " + persona.getId());

			if (res != -1)
				return 0;
			return -1;
		}
		return -1;
	}

	public Persona obtenerPersona(LatLng latLng) {
		if (db != null) {
			String[] campos = Utils.camposBD;
			String[] args = new String[] { String.valueOf(latLng.latitude),
					String.valueOf(latLng.longitude) };

			Cursor c = db.query("Personas", campos, "latitud=? AND longitud=?",
					args, null, null, null);

			Persona persona = CargadorPersona.cargarPersona(c);
			Log.d(Utils.APPTAG,
					"latitud buscada: " + Double.toString(latLng.latitude));
			Log.d(Utils.APPTAG,
					"longitud guardada: " + Double.toString(latLng.longitude));
			return persona;
		}
		return null;
	}

	public int eliminarPersona(LatLng latLng) {
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

}
