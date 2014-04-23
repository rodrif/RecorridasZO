package com.recorridaszo.BDLocal;

import com.google.android.gms.maps.model.LatLng;
import com.recorridaszo.persona.CargadorPersona;
import com.recorridaszo.persona.Persona;
import com.recorridaszo.recorridaszo.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
		db.close();
		this.db = null;
	}

	public Cursor selectTodo() {
		String[] campos = Utils.camposBD;

		Cursor c = db.query(Utils.TPersonas, campos, null, null, null, null,
				null);

		return c;
	}

	public void borrarTodo() {
		db.delete(Utils.TPersonas, null, null);
	}

	public SQLiteDatabase getDB() {
		return this.db;
	}

	public int guardarPersona(Persona persona) {
		if (db != null) {
			// Creamos el registro a insertar como objeto ContentValues
			ContentValues nuevoRegistro = CargadorPersona
					.cargarContentValues(persona);

			// Insertamos el registro en la base de datos
			long res = db.insert(Utils.TPersonas, null, nuevoRegistro);
			
			if(res != -1)
				return 0;
			return -1;
		} else {
			return -1;
		}
	}

	public Persona obtenerPersona(LatLng latLng) {
		String[] campos = Utils.camposBD;
		String[] args = new String[] { String.valueOf(latLng.latitude),
				String.valueOf(latLng.longitude) };

		Cursor c = db.query("Personas", campos, "latitud=? AND longitud=?",
				args, null, null, null);

		Persona persona = CargadorPersona.cargarPersona(c);

		return persona;
	}
	
	
}
