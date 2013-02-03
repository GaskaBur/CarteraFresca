package com.noisesOfHill.carteraFresca;
/**
 * @author Gaskabur
 * @version 1.0
 * @company Noises of Hill
 * @since 28/08/2012
 *
 */



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class CarteraFrescaSQLiteHelper extends SQLiteOpenHelper 
{
	String sqlCreateTarjetas = "CREATE TABLE Tarjetas (idTarjeta INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , idCuenta INTEGER NOT NULL, diaCargo INTEGER, nombre VARCHAR NOT NULL, descripcion VARCHAR)";
	String sqlCreateMovimientosEfectivo = "CREATE TABLE Mov_efectivo (idMovimiento INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL" +
			", idCuenta INTEGER NOT NULL, idCategoria INTEGER NOT NULL,fecha DATETIME, descripcion VARCHAR, importe REAL, tipo BOOL)";
	
	
	
	public CarteraFrescaSQLiteHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(cadenaCategorias("Categorias"));
		db.execSQL(cadenaCuentas("Cuentas"));
		db.execSQL(cadenaTarjetas("Tarjetas"));
		db.execSQL(cadenaMovimientos("Mov_efectivo"));
		
		String query = "INSERT INTO Categorias (descripcion, icono, imagen) VALUES ('Casa'," + R.drawable.categoria_casa + ",'')";
		db.execSQL(query);
		query = "INSERT INTO Categorias (descripcion, icono, imagen) VALUES ('Ocio'," + R.drawable.categoria_ocio + ",'')";
		db.execSQL(query);
		query = "INSERT INTO Categorias (descripcion, icono, imagen) VALUES ('Coche'," + R.drawable.categoria_coche + ",'')";
		db.execSQL(query);
		query = "INSERT INTO Categorias (descripcion, icono, imagen) VALUES ('Alimentación'," + R.drawable.categoria_alimentacion + ",'')";
		db.execSQL(query);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		db.execSQL(cadenaCuentas("CuentasAux"));
		db.execSQL(cadenaCategorias("CategoriasAux"));
		db.execSQL(cadenaTarjetas("TarjetasAux"));
		db.execSQL(cadenaMovimientos("Mov_efectivoAux"));
		
		db.execSQL("INSERT INTO CuentasAux Select * from cuentas");
		db.execSQL("INSERT INTO CategoriasAux SELECT * FROM Categorias");
		db.execSQL("INSERT INTO TarjetasAux SELECT * FROM Tarjetas");
		db.execSQL("INSERT INTO Mov_efectivoAux SELECT * FROM Mov_efectivo");
		
		db.execSQL("DROP TABLE IF EXISTS Categorias");
		db.execSQL("DROP TABLE IF EXISTS Cuentas");
		db.execSQL("DROP TABLE IF EXISTS Tarjetas");
		db.execSQL("DROP TABLE IF EXISTS Mov_efectivo");
		
		db.execSQL("ALTER TABLE CuentasAux RENAME TO Cuentas");
		db.execSQL("ALTER TABLE CategoriasAux RENAME TO Categorias");
		db.execSQL("ALTER TABLE TarjetasAux RENAME TO Tarjetas");		
		db.execSQL("ALTER TABLE Mov_efectivoAux RENAME TO Mov_efectivo");
	
	}	
	
	private String cadenaCuentas(String nombre)
	{
		return "CREATE TABLE "+nombre+" (idCuenta INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
			"nombre VARCHAR NOT NULL, descripcion VARCHAR, imagen INT NOT NULL, imagenPers VARCHAR)";
		
	}
	private String cadenaCategorias(String nombre)
	{
		return "CREATE TABLE "+nombre+" (idCategoria INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, descripcion VARCHAR NOT NULL, icono INT, imagen VARCHAR)";
	}
	private String cadenaTarjetas (String nombre)
	{
		return  "CREATE TABLE "+nombre+" (idTarjeta INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , idCuenta INTEGER NOT NULL, diaCargo INTEGER, nombre VARCHAR NOT NULL, descripcion VARCHAR)";
	}
	private String cadenaMovimientos(String nombre)
	{
		return "CREATE TABLE "+nombre+" (idMovimiento INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL" +
				", idCuenta INTEGER NOT NULL, idCategoria INTEGER NOT NULL,fecha DATETIME, descripcion VARCHAR, importe REAL, tipo BOOL)";
	}
}