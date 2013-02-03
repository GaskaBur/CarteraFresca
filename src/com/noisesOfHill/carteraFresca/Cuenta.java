package com.noisesOfHill.carteraFresca;
/**
 * @author GaskaBur
 * @producto Cartera Fresca
 * @company Noises Of Hill
 * @version 1.0
 * @since 07/2012
 */

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Cuenta 
{
	private int idCuenta;
	private String nombre;
	private int imagen;
	private String imagenPersonalizada;
	private String descripcion;
	
	public Cuenta()
	{
		this.idCuenta=0;
		this.nombre = "";
		this.imagen = R.drawable.ico_cuenta;
		this.imagenPersonalizada = "";
		this.descripcion = "";
	}
	
	public Cuenta(Context context, int idCuenta)
	{
		CarteraFrescaSQLiteHelper bdMiCartera = 
				new CarteraFrescaSQLiteHelper(context,"DBCarteraFesca",null,DatosCompartidos.versionDB);        
		SQLiteDatabase db = bdMiCartera.getWritableDatabase();   
		if (db != null)
        {
			String consultaCuentas = "SELECT * FROM Cuentas Where idCuenta = " + idCuenta;
		  	  Cursor cursorCuentas = db.rawQuery(consultaCuentas,null);
		  	  if (cursorCuentas.getCount() != 0)
		  	  {
		  		  while (cursorCuentas.moveToNext())
		  		  {
		  			this.idCuenta = cursorCuentas.getInt(0);
		  			this.nombre = cursorCuentas.getString(1);
		  			this.descripcion = cursorCuentas.getString(2);
		  			this.imagen = cursorCuentas.getInt(3);
		  			this.imagenPersonalizada = cursorCuentas.getString(4);
		  		  }
		  	  }
        }        							        
        db.close();
	}
	
	public String getImagenPersonalizada() {
		return imagenPersonalizada;
	}

	public void setImagenPersonalizada(String imagenPersonalizada) {
		this.imagenPersonalizada = imagenPersonalizada;
	}

	public int getIdCuenta() {
		return idCuenta;
	}
	public void setIdCuenta(int idCuenta) {
		this.idCuenta = idCuenta;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getImagen() {
		return imagen;
	}
	public void setImagen(int imagen) {
		this.imagen = imagen;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}	
	
	public static boolean updateCuenta(Context context,int idCuenta, String nombre, int imagen, String imgPerso, String descrip)
	{
		boolean exit = true;			
		String query = "SELECT nombre FROM Cuentas WHERE nombre = '" + nombre + "'";
		CarteraFrescaSQLiteHelper bdMiCartera = 
				new CarteraFrescaSQLiteHelper(context,"DBCarteraFesca",null,DatosCompartidos.versionDB);        
		SQLiteDatabase db = bdMiCartera.getWritableDatabase();
		
		if (db != null)
		{
			Cursor c = db.rawQuery(query, null);
			if (c.getCount() != 0)
				exit = false;
			else
			{
				ContentValues nuevaCuenta = new ContentValues();
				if (nombre != null)
					nuevaCuenta.put("nombre",nombre);
				nuevaCuenta.put("descripcion",descrip);
				nuevaCuenta.put("imagen",imagen);
				nuevaCuenta.put("imagenPers", imgPerso);
				db.update("Cuentas", nuevaCuenta, "idCuenta = " + idCuenta, null);
			}								
		}
		db.close();
		return exit;
	}
		
	public static boolean insertCuenta(Context context,Cuenta cuenta)
	{
		boolean exit = true;
		String query = "SELECT nombre FROM Cuentas WHERE nombre = '" + cuenta.getNombre() + "'";
		
		CarteraFrescaSQLiteHelper bdMiCartera = 
    		new CarteraFrescaSQLiteHelper(context,"DBCarteraFesca",null,DatosCompartidos.versionDB);        
		SQLiteDatabase db = bdMiCartera.getWritableDatabase();
		
		if (db != null)
		{			        	
			Cursor c = db.rawQuery(query, null);
			if (c.getCount() != 0)				
				exit = false;
			else
			{				
				ContentValues nuevaCuenta = new ContentValues();
				nuevaCuenta.put("nombre",cuenta.getNombre());
				nuevaCuenta.put("descripcion",cuenta.getDescripcion());
				nuevaCuenta.put("imagen",cuenta.getImagen());
				nuevaCuenta.put("imagenPers", cuenta.getImagenPersonalizada());
				db.insert("Cuentas", null, nuevaCuenta);
			}	
		}
		db.close();
		return exit;
	}
	
	public static boolean deleteCuenta(Context context,int idCuenta)
	{
		boolean exit =  false;
		CarteraFrescaSQLiteHelper bdMiCartera = 
				new CarteraFrescaSQLiteHelper(context,"DBCarteraFesca",null,DatosCompartidos.versionDB);        
		SQLiteDatabase db = bdMiCartera.getWritableDatabase();        
        
        if (db != null)
        {
        	db.delete("Cuentas", "idCuenta = " +idCuenta,null);
        	db.delete("Tarjetas", "idCuenta = " +idCuenta, null);
        	db.delete("Mov_efectivo", "idCuenta = " +idCuenta, null);
        	        	
        	Log.e("Cuenta Borrada", "cuenta y sus tarjetas eliminada");
        	exit = true;
        }
        							        
        db.close();
        return exit;
	}
	
	public static List<Cuenta> getCuentas(Context context)
	{
		List<Cuenta> cuentas = new ArrayList<Cuenta>();
		CarteraFrescaSQLiteHelper bdMiCartera = 
				new CarteraFrescaSQLiteHelper(context,"DBCarteraFesca",null,DatosCompartidos.versionDB);        
		SQLiteDatabase db = bdMiCartera.getWritableDatabase();   
		if (db != null)
        {
			String consultaCuentas = "SELECT * FROM Cuentas ORDER BY idCuenta";
		  	  Cursor cursorCuentas = db.rawQuery(consultaCuentas,null);
		  	  if (cursorCuentas.getCount() != 0)
		  	  {
		  		  while (cursorCuentas.moveToNext())
		  		  {
		  			cuentas.add(new Cuenta());
		  			cuentas.get(cuentas.size()-1).setIdCuenta(cursorCuentas.getInt(0));
		  			cuentas.get(cuentas.size()-1).setNombre(cursorCuentas.getString(1));
		  			cuentas.get(cuentas.size()-1).setDescripcion(cursorCuentas.getString(2));
		  			cuentas.get(cuentas.size()-1).setImagen(cursorCuentas.getInt(3));
		  			cuentas.get(cuentas.size()-1).setImagenPersonalizada(cursorCuentas.getString(4));
		  		  }
		  	  }
        }
        							        
        db.close();
        return cuentas;		
	}
}