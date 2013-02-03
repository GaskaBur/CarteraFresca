package com.noisesOfHill.carteraFresca;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Movimiento {
	
	private String descripcion;
	private float importe;
	private String fecha;
	private int tipo;
	private int idCategoria;
	private int idMovimiento;
	private int idCuenta;
	private String nameCategoria;
	private int iconoCategoria;
	private String imagenCategoria;
	
	public Movimiento(Context context, int idMovimiento)
	{
		CarteraFrescaSQLiteHelper bdMiCartera = new CarteraFrescaSQLiteHelper(context,"DBCarteraFesca",null,DatosCompartidos.versionDB);        
        SQLiteDatabase db = bdMiCartera.getReadableDatabase();
          
        if (db != null)
        {
        	String consultaSaldoEfvo = "SELECT  * FROM Mov_efectivo WHERE idMovimiento = "+idMovimiento;        	
        	Cursor cursor = db.rawQuery(consultaSaldoEfvo,null);      	  	
      	  	if (cursor.getCount() != 0)      	  		
      		  while (cursor.moveToNext())
      		  {
      			  this.idMovimiento = cursor.getInt(0);
      			  this.idCuenta = cursor.getInt(1);
      			  this.idCategoria = cursor.getInt(2);
      			  this.fecha = cursor.getString(3);
      			  this.descripcion = cursor.getString(4);
      			  this.importe = cursor.getFloat(5);
      			  this.tipo = cursor.getInt(6);
      			  
      			  String sql =  "select descripcion,icono,imagen from Categorias where idCategoria = "+cursor.getInt(2);
      			  Cursor c = db.rawQuery(sql, null);
				  if (c.getCount() != 0)
				  {
					c.moveToNext();
					this.setNameCategoria(c.getString(0));
				  }
				  else
				  {
					this.setNameCategoria("Sin categoría");
					
				  }
      		  }      			
        }        
        db.close();
	}
	
	public Movimiento()
	{
		this.descripcion ="";
		this.importe = 0f;
		this.fecha = "";
		this.tipo = 0;
		this.idCategoria = 0;
		this.idMovimiento = 0;
		this.idCuenta = 0;
		this.nameCategoria = "";
		this.iconoCategoria = 0;
		this.imagenCategoria = "";
				
	}
	
	public int getIconoCategoria() {
		return iconoCategoria;
	}
	public void setIconoCategoria(int iconoCategoria) {
		this.iconoCategoria = iconoCategoria;
	}
	public String getImagenCategoria() {
		return imagenCategoria;
	}
	public void setImagenCategoria(String imagenCategoria) {
		this.imagenCategoria = imagenCategoria;
	}
	public String getNameCategoria() {
		return nameCategoria;
	}
	public void setNameCategoria(String nameCategoria) {
		this.nameCategoria = nameCategoria;
	}
	public int getIdCuenta() {
		return idCuenta;
	}
	public void setIdCuenta(int idCuenta) {
		this.idCuenta = idCuenta;
	}
	private String movimiento;
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public float getImporte() {
		return importe;
	}
	public void setImporte(float importe) {
		this.importe = importe;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public int getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}
	public int getIdMovimiento() {
		return idMovimiento;
	}
	public void setIdMovimiento(int idMovimiento) {
		this.idMovimiento = idMovimiento;
	}
	public String getMovimiento() {
		return movimiento;
	}
	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}
	
	/**
	 * Insert nuevo movimiento.
	 * @param context
	 * @param idCuenta
	 * @param idCat
	 * @param fecha
	 * @param descripcion
	 * @param importe
	 * @param tipoIngreso
	 */
	public static void insertMovimiento(Context context, int idCuenta, int idCat,String fecha,String descripcion,Float importe,boolean tipoIngreso)
	{
		CarteraFrescaSQLiteHelper bdMiCartera = 
        new CarteraFrescaSQLiteHelper(context,"DBCarteraFesca",null,DatosCompartidos.versionDB);        
		SQLiteDatabase db = bdMiCartera.getWritableDatabase();
			
		if (db != null)
		{			        	
											
				ContentValues nuevoMov = new ContentValues();
				nuevoMov.put("idCuenta",idCuenta);
				//DatosCompartidos.CuentaNueva.setNombre(textNombre.getText().toString());
				nuevoMov.put("idCategoria",idCat);
				//DatosCompartidos.CuentaNueva.setDescripcion(descripcion.getText().toString());
				nuevoMov.put("fecha",fecha);
				nuevoMov.put("descripcion", descripcion);																		
				nuevoMov.put("importe",importe);				
				if (tipoIngreso)
					nuevoMov.put("tipo", true);
				else
					nuevoMov.put("tipo", false);		
				
				
				db.insert("Mov_efectivo", null, nuevoMov);
				
		}
		db.close();
	}
	
	
	public static Float getSaldoEfectivo(Context context, int cuenta)
	{
		CarteraFrescaSQLiteHelper bdMiCartera = new CarteraFrescaSQLiteHelper(context,"DBCarteraFesca",null,DatosCompartidos.versionDB);        
        SQLiteDatabase db = bdMiCartera.getReadableDatabase();
        
        Float saldo = 0f;
        if (db != null)
        {
        	String consultaSaldoEfvo = "SELECT  DISTINCT(" +
        			"(SELECT IFNULL(SUM(Importe),0)  FROM Mov_efectivo WHERE tipo = 1 AND fecha <= DATETIME('now','+1 hour') AND IdCuenta = "+cuenta+") - " +
        			"(SELECT IFNULL(SUM(Importe),0)  FROM Mov_efectivo WHERE tipo = 0 AND fecha <= DATETIME('now','+1 hour') AND idCuenta = "+cuenta+" ) ) " +
        			"FROM Mov_efectivo WHERE idcuenta = "+cuenta + " ORDER BY fecha";        	
        	
        	Cursor cursorCuentas = db.rawQuery(consultaSaldoEfvo,null);
      	  	
      	  	if (cursorCuentas.getCount() != 0)      	  		
      		  while (cursorCuentas.moveToNext())
      			 saldo  = cursorCuentas.getFloat(0);
        }
        
        db.close();
        return saldo;
	}
	
	public static boolean deleteMovimiento(Context context, int idMovimiento)
	{
		boolean exit = false;
		CarteraFrescaSQLiteHelper bdMiCartera = new CarteraFrescaSQLiteHelper(context,"DBCarteraFesca",null,DatosCompartidos.versionDB);        
        SQLiteDatabase db = bdMiCartera.getReadableDatabase();
        if (db != null)
        {
        	db.delete("Mov_efectivo", "idMovimiento = " +idMovimiento, null);
        	exit = true;
        }
        
        db.close();
        return exit;
	}
	
	public static List<Movimiento> getMovimientosEfectivo(Context context, int cuenta, boolean pendientes)
	{
		CarteraFrescaSQLiteHelper bdMiCartera = new CarteraFrescaSQLiteHelper(context,"DBCarteraFesca",null,DatosCompartidos.versionDB);        
        SQLiteDatabase db = bdMiCartera.getReadableDatabase();
        
        List<Movimiento> listaMovimientos = new ArrayList<Movimiento>();
        Cursor cursorCuentas = null;
        
        if (db!= null)
        {
        	String queryMov =  "select * from Mov_efectivo where idcuenta = "+cuenta;
        	if (pendientes)
        		queryMov += " AND fecha > DATETIME('now','+1 hour') order by fecha desc, idMovimiento desc LIMIT 25";
        	else
        		queryMov += " AND fecha <= DATETIME('now','+1 hour') order by fecha desc, idMovimiento desc LIMIT 25";
  	  		cursorCuentas =db.rawQuery(queryMov, null);
  	  		
	  		while(cursorCuentas.moveToNext())
	  		{
	  			Movimiento movAux = new Movimiento();
	  			movAux.setIdMovimiento(cursorCuentas.getInt(0));
	  			movAux.setIdCuenta(cursorCuentas.getInt(1));
	  			movAux.setIdCategoria(cursorCuentas.getInt(2));
	  			movAux.setFecha(cursorCuentas.getString(3));
	  			movAux.setDescripcion(cursorCuentas.getString(4));
	  			movAux.setImporte(cursorCuentas.getFloat(5));
	  			movAux.setTipo(cursorCuentas.getInt(6));
	  			
	  			String sql =  "select descripcion,icono,imagen from Categorias where idCategoria = "+cursorCuentas.getInt(2);
	  			Cursor c = db.rawQuery(sql, null);
				if (c.getCount() != 0)
				{
					c.moveToNext();
					movAux.setNameCategoria(c.getString(0));
					movAux.setIconoCategoria(c.getInt(1));
					movAux.setImagenCategoria(c.getString(2));
				}
				else
				{
					movAux.setNameCategoria("Sin categoría");
					movAux.setIconoCategoria(R.drawable.ico_anotacion);
					movAux.setImagenCategoria("");
					
				}
	  			listaMovimientos.add(movAux);  	  			    	  					
	  		}  
	  		
        }
        db.close();
        return listaMovimientos;
  	  	
	}
	
	public static boolean updateMovimiento(Context context,int idMovimiento,int idCuenta, int idCat,String fecha,String descripcion,Float importe,boolean tipoIngreso)
	{
		boolean exit = false;
		CarteraFrescaSQLiteHelper bdMiCartera = 
				new CarteraFrescaSQLiteHelper(context,"DBCarteraFesca",null,DatosCompartidos.versionDB);        
		SQLiteDatabase db = bdMiCartera.getWritableDatabase();
		
		if (db != null)
		{			
				ContentValues editMovimiento = new ContentValues();
				editMovimiento.put("idcuenta",idCuenta);
				editMovimiento.put("idCategoria",idCat);
				editMovimiento.put("fecha",fecha);
				editMovimiento.put("descripcion", descripcion);
				editMovimiento.put("descripcion", descripcion);
				editMovimiento.put("importe", importe);
				if (tipoIngreso)
					editMovimiento.put("tipo", true);
				else
					editMovimiento.put("tipo", false);	
				db.update("Mov_efectivo", editMovimiento, "idMovimiento = " + idMovimiento, null);
				exit = true;
										
		}
		db.close();
		return exit;
	}
	

}
