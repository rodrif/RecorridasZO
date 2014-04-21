package com.recorridaszo.BDLocal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ManejadorBDLocal {
	private Context contexto;

	public ManejadorBDLocal(Context contexto) {
		this.contexto = contexto;
	}
	
	public void conectarsePrueba() {
		//Abrimos la base de datos 'DBUsuarios' en modo escritura
        PersonasSQLiteHelper psdbh =
            new PersonasSQLiteHelper(contexto, "DBPersonas", null, 1);
 
        SQLiteDatabase db = psdbh.getWritableDatabase();
 
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
            //Insertamos 5 usuarios de ejemplo
            for(int i=1; i<=5; i++)
            {
                //Generamos los datos
                int codigo = i;
                String nombre = "Persona" + i;
 
                //Insertamos los datos en la tabla Usuarios
                db.execSQL("INSERT INTO Personas (codigo, nombre) " +
                           "VALUES (" + codigo + ", '" + nombre +"')");
            }
 
            //Cerramos la base de datos
            db.close();
        }
    }	
}
