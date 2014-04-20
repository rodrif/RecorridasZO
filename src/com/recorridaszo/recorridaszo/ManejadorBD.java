package com.recorridaszo.recorridaszo;

public class ManejadorBD {

	private static ManejadorBD INSTANCE = new ManejadorBD();

	// El constructor privado no permite que se genere un constructor por
	// defecto.
	// (con mismo modificador de acceso que la definición de la clase)
	private ManejadorBD() {
	}

	public static ManejadorBD getInstance() {
		return INSTANCE;
	}
}
