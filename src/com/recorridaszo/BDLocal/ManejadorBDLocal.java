package com.recorridaszo.BDLocal;

import com.recorridaszo.recorridaszo.Persona;
import com.recorridaszo.recorridaszo.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ManejadorBDLocal {
	private Context contexto;
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
		this.contexto = contexto;

		// Abrimos la base de datos 'DBUsuarios' en modo escritura
		PersonasSQLiteHelper psdbh = new PersonasSQLiteHelper(contexto,
				"DBPersonas", null, 1);

		this.db = psdbh.getWritableDatabase();
	}

	public void desconectarse() {
		// Cerramos la base de datos
		db.close();
		this.contexto = null;
		this.db = null;
	}

	public Cursor selectTodo() {
		String[] campos = Utils.camposBD;

		// Abrimos la base de datos 'DBUsuarios' en modo escritura
		PersonasSQLiteHelper psdbh = new PersonasSQLiteHelper(contexto,
				"DBPersonas", null, 1);

		SQLiteDatabase db = psdbh.getWritableDatabase();

		Cursor c = db.query(Utils.TPersonas, campos, null, null, null, null, null);

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
			ContentValues nuevoRegistro = new ContentValues();
			nuevoRegistro.put("id", persona.getId());
			nuevoRegistro.put("nombre", persona.getNombre());
			nuevoRegistro.put("apellido", persona.getApellido());
			nuevoRegistro.put("direccion", persona.getDireccion());
			nuevoRegistro.put("descripcion", persona.getDescripcion());
			nuevoRegistro.put("latitud", persona.getUbicacion().latitude);
			nuevoRegistro.put("longitud", persona.getUbicacion().longitude);
			nuevoRegistro.put("ultMod", persona.getUltMod());
			
			// Insertamos el registro en la base de datos
			db.insert(Utils.TPersonas, null, nuevoRegistro);
			return 0;
		} else {
			return -1;
		}
	}
}
