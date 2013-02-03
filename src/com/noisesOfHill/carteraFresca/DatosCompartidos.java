package com.noisesOfHill.carteraFresca;
/**
 * @author GaskaBur
 * @producto Cartera Fresca
 * @company Noises Of Hill
 * @version 1.0
 * @since 07/2012
 */

import android.app.Application;

public class DatosCompartidos extends Application {
		
	//Versión Actual de la base de datos
	public static int versionDB = 1;
	
	//Cuenta temporal para dar de alta
	public static Cuenta CuentaEditar;
	
	
	//Categoria temporal que se edita
	public static Categoria categoriaEditar;
	
	
	
	
 
    //Objecto singleton con datos comunes a todas las clases.
	private static DatosCompartidos singleton; 
    public static DatosCompartidos getInstance() {
        return singleton;
    }
 
    @Override
    public void onCreate() {
        super.onCreate();        
        singleton = this;
    }
}
