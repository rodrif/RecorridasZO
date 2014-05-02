package com.recorridaszo.BDWeb;

import com.recorridaszo.interfaces.IManejadorBDWeb;
import com.recorridaszo.mocks.ManejadorBDWebMock;


public class ManejadorBDWeb {
	private static boolean mock = false;
	
	private ManejadorBDWeb() {
	}

	public static IManejadorBDWeb getInstance() {
		if(mock)
			return ManejadorBDWebMock.getInstance();
		else
			return ManejadorBDWebConcreto.getInstance();
	}
	
	
	public static void setMock(boolean valor) {
		mock = valor;
	}
}
