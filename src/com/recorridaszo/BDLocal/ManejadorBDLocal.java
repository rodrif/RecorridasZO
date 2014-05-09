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

	public Personas selectTodoPersonas() {
		try {
			String[] campos = Utils.camposBD;

			Cursor c = db.query(Utils.TPersonas, campos, null, null, null,
					null, null);
			
			return CargadorPersona.cargarPersonas(c);

		} catch (Exception e) {
			e.printStackTrace();
			return new Personas();
		}
	}

	public int borrarTodo() {
		if (db != null) {
			db.delete(Utils.TPersonas, null, null);
			return 0;
		}
		return -1;
	}

	public synchronized int guardarPersona(Persona persona) {
		try {
			// si estaba en la BDLocal
			if (obtenerPersona(persona.getUbicacion()) != null) {
				actualizarPersona(persona);
			} else if (!persona.getEstado().equals(Utils.EST_BORRADO)) {
				insertarPersona(persona);
			}
			return 0;

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	private void insertarPersona(Persona persona) {
		// Creamos el registro a insertar como objeto ContentValues
		ContentValues nuevoRegistro = CargadorPersona
				.cargarContentValues(persona);
		db.insert(Utils.TPersonas, null, nuevoRegistro);
		Log.d(Utils.APPTAG, "Se guardo persona con id: " + persona.getId());
	}

	private int actualizarPersona(Persona persona) {
		Persona personaAActualizar = obtenerPersona(persona.getUbicacion());

		try {
			String[] args = new String[] {
					String.valueOf(personaAActualizar.getLatitud()),
					String.valueOf(personaAActualizar.getLongitud()) };

			persona.setUbicacion(personaAActualizar.getUbicacion());

			// Actualizar, utilizando argumentos
			ContentValues valores = CargadorPersona
					.cargarContentValues(persona);
			db.update("Personas", valores, "latitud=? AND longitud=?", args);

			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public Persona obtenerPersona(LatLng latLng) {
		Personas personas = this.selectTodoPersonas();
		Iterator<Persona> it = personas.iterator();

		while (it.hasNext()) {
			Persona posiblePersona = it.next();

			if (((Math.abs(posiblePersona.getLatitud() - latLng.latitude) <= Utils.PRECISION))
					&& (Math.abs(posiblePersona.getLongitud()
							- latLng.longitude) <= Utils.PRECISION)) {
				return posiblePersona;
			}
		}

		return null;
	}

	private synchronized int eliminarPersonas(String estado) {
		try {
			String[] args = new String[] { estado };

			db.delete("Personas", "estado=?", args);

			return 0;

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public synchronized int eliminarPersonasActualizadas() {
		int resultado;

		resultado = eliminarPersonas(Utils.EST_ACTUALIZADO);
		return resultado;
	}

	public synchronized int eliminarPersona(LatLng latLng) {
		try {
			Persona persona = this.obtenerPersona(latLng);

			String[] args = new String[] {
					String.valueOf(persona.getLatitud()),
					String.valueOf(persona.getLongitud()) };

			if (persona.getEstado().equals(Utils.EST_NUEVO)
					|| persona.getEstado().equals(Utils.EST_BORRADO)) {
				db.delete("Personas", "latitud=? AND longitud=?", args);
			} else {
				// Actualizar, utilizando argumentos
				ContentValues valores = new ContentValues();
				valores.put("estado", Utils.EST_BORRADO);
				db.update("Personas", valores, "latitud=? AND longitud=?", args);
			}
			return 0;

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	private Personas obtenerPersonasSegunEstado(String estado) {
		try {
			Personas personas = new Personas();
			Personas todasPersonas = this.selectTodoPersonas();

			for (Iterator<Persona> it = todasPersonas.iterator(); it.hasNext();) {
				Persona p = it.next();

				if (p.getEstado().equals(estado)) {
					personas.addPersona(p);
				}
			}
			return personas;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
		try {
			String[] campos = Utils.camposBD;

			Cursor c = db.query("Personas", campos, null, null, null, null,
					"ultMod DESC", "1");

			Persona persona = CargadorPersona.cargarPersona(c);

			if (persona == null)
				return Utils.FECHA_CERO;

			return persona.getUltMod();

		} catch (Exception e) {
			e.printStackTrace();
			return Utils.FECHA_CERO;
		}
	}
}
