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

public class Categoria 
{
	private int idCategoria;
	private String descripcion;
	private int icono;
	private String imagen;
	
	public Categoria()
	{
		this.idCategoria=0;
		this.descripcion = "";
		this.icono = R.drawable.ico_anotacion;
		this.imagen = "";
	}
	public Categoria(Context context, int idCategoria)
	{
		CarteraFrescaSQLiteHelper bdMiCartera = 
				new CarteraFrescaSQLiteHelper(context,"DBCarteraFesca",null,DatosCompartidos.versionDB);        
		SQLiteDatabase db = bdMiCartera.getWritableDatabase();   
		if (db != null)
        {
			String consultaCuentas = "SELECT * FROM Categorias Where idCategoria = " + idCategoria;
		  	  Cursor cursorCuentas = db.rawQuery(consultaCuentas,null);
		  	  if (cursorCuentas.getCount() != 0)
		  	  {
		  		  while (cursorCuentas.moveToNext())
		  		  {
		  			this.idCategoria = cursorCuentas.getInt(0);
		  			this.descripcion = cursorCuentas.getString(1);
		  			this.icono = cursorCuentas.getInt(2);
		  			this.imagen = cursorCuentas.getString(3);
		  		  }
		  	  }
        }  
		db.close();
	}
	//getters a setters ---------------------------------------------
	public int getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getIcono() {
		return icono;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public void setIcono(int icono) {
		this.icono = icono;
	}
	//------------------------------------------------------------------
	
		
	public static boolean insertNuevaCategoria(Context context, Categoria categoria)
	{
		String cadena = categoria.getDescripcion().toLowerCase();
		String first = cadena.substring(0,1).toUpperCase();
		cadena = first + cadena.substring(1,cadena.length());
		categoria.setDescripcion(cadena);
		boolean exit = true;
		String query = "SELECT descripcion FROM Categorias WHERE descripcion = '" + categoria.getDescripcion() + "'";
		
		CarteraFrescaSQLiteHelper bdMiCartera = 
				new CarteraFrescaSQLiteHelper(context,"DBCarteraFesca",null,DatosCompartidos.versionDB);		
		SQLiteDatabase db = bdMiCartera.getWritableDatabase();
		if (db != null)
		{
			Cursor c = db.rawQuery(query, null);
			
			if (c.getCount() != 0)
			{									
				exit = false;
			}
			else
			{
				ContentValues nuevaCategoria = new ContentValues();
				nuevaCategoria.put("descripcion",categoria.getDescripcion());
				nuevaCategoria.put("icono",categoria.getIcono());
				nuevaCategoria.put("imagen",categoria.getImagen());
				db.insert("Categorias", null, nuevaCategoria);
			}
		}
		db.close();
		return exit;
	}
	
	public static boolean deleteCategoria(Context context, int cat)
	{
		boolean exit =  false;
		CarteraFrescaSQLiteHelper bdMiCartera = 
				new CarteraFrescaSQLiteHelper(context,"DBCarteraFesca",null,DatosCompartidos.versionDB);        
		SQLiteDatabase db = bdMiCartera.getWritableDatabase();        
        
        if (db != null)
        {
        	
        	db.delete("Categorias", "idCategoria = " +cat, null);
        	Log.e("Categoría Borrada", "categoria borrada");
        	exit = true;
        }
        							        
        db.close();
        return exit;
	}
	
	public static List<Categoria> getCategorias(Context context)
	{
		List<Categoria> categorias = new ArrayList<Categoria>();
		
		CarteraFrescaSQLiteHelper bdMiCartera = 
				new CarteraFrescaSQLiteHelper(context,"DBCarteraFesca",null,DatosCompartidos.versionDB); 
		
		SQLiteDatabase db = bdMiCartera.getWritableDatabase(); 
		
		if (db != null)
        {
			String consultaCuentas = "SELECT * FROM Categorias ORDER BY descripcion";
		  	  Cursor cursorCuentas = db.rawQuery(consultaCuentas,null);
		  	  if (cursorCuentas.getCount() != 0)
		  	  {
		  		  while (cursorCuentas.moveToNext())
		  		  {
		  			categorias.add(new Categoria());
		  			categorias.get(categorias.size()-1).setIdCategoria(cursorCuentas.getInt(0));
		  			categorias.get(categorias.size()-1).setDescripcion(cursorCuentas.getString(1));
		  			categorias.get(categorias.size()-1).setIcono(cursorCuentas.getInt(2));
		  			categorias.get(categorias.size()-1).setImagen(cursorCuentas.getString(3));
		  		  }
		  	  }
        }
        							        
        db.close();
        return categorias;		
	}
	
	public static boolean updateCategoria(Context context,int idCategoria, String descrip, int ico, String imagen)
	{
		boolean exit = true;			
		String query = "SELECT descripcion FROM Categorias WHERE descripcion = '" + descrip + "'";
		
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
				ContentValues updateCategoria = new ContentValues();
				if (descrip != null)
					updateCategoria.put("descripcion",descrip);
				updateCategoria.put("icono",ico);
				updateCategoria.put("imagen", imagen);
				db.update("Categorias", updateCategoria, "idCategoria = " + idCategoria, null);
			}								
		}
		db.close();
		return exit;
	}
	
}
