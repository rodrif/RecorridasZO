package com.recorridaszo.BDLocal;

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
		String[] campos = new String[] { "codigo", "nombre" };

		// Abrimos la base de datos 'DBUsuarios' en modo escritura
		PersonasSQLiteHelper psdbh = new PersonasSQLiteHelper(contexto,
				"DBPersonas", null, 1);

		SQLiteDatabase db = psdbh.getWritableDatabase();

		Cursor c = db.query("Personas", campos, null, null, null, null, null);

		return c;
	}
	
	public void borrarTodo() {
		db.delete("Personas", null, null);
	}
	
	public SQLiteDatabase getDB() {
		return this.db;
	}
}
