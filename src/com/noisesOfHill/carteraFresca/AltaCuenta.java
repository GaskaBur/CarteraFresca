package com.noisesOfHill.carteraFresca;
/**
 * @author GaskaBur
 * @producto Cartera Fresca
 * @company Noises Of Hill
 * @version 1.0
 * @since 07/2012
 */
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AltaCuenta extends Activity   {

	TextView textNombre;
	TextView descripcion;	
	int idCuenta = -1; //Opcion para comprobar si estoy modificando o creando cuenta
	String nombreAnterior;
	private static int RESULT_LOAD_IMAGE = 1;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.altacuenta);
		
		Intent intento = getIntent(); // voy a modificar.
		idCuenta = intento.getIntExtra("idCuenta", -1);
		
		if (idCuenta !=-1)
		{
			TextView titulo = (TextView)findViewById(R.id.tituloAltaCuenta);
			titulo.setText("Modificar Cuenta");
		}
		
		textNombre = (TextView)findViewById(R.id.txtNombreCtaAltas);
		descripcion = (TextView)findViewById(R.id.txtDescripcionCuentaAltas);
		
		if (idCuenta == -1)
			DatosCompartidos.CuentaEditar = new Cuenta(); //Se crea una nueva Cuenta donde se recogen los datos.
		else
			DatosCompartidos.CuentaEditar = new Cuenta(AltaCuenta.this,idCuenta);
		
		nombreAnterior = DatosCompartidos.CuentaEditar.getNombre();
		
		
		
	}

	//--------------------------- ON RESUME ------------------------------------------------
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ImageView imagen = (ImageView)findViewById(R.id.imgCuentaNueva);	
		
		//textNombre = (TextView)findViewById(R.id.txtNombreCtaAltas);
		//descripcion = (TextView)findViewById(R.id.txtDescripcionCuentaAltas);
		textNombre.setText(DatosCompartidos.CuentaEditar.getNombre());
		descripcion.setText(DatosCompartidos.CuentaEditar.getDescripcion());
		
	
			if (!DatosCompartidos.CuentaEditar.getImagenPersonalizada().equals(""))
			{
				imagen.setImageBitmap(BitmapFactory.decodeFile(DatosCompartidos.CuentaEditar.getImagenPersonalizada()));
				imagen.setMaxHeight(50);
				imagen.setMaxWidth(50);
			}
			else			
				imagen.setImageResource(DatosCompartidos.CuentaEditar.getImagen());
			
			Button botonImagenesPre = (Button)findViewById(R.id.btnImgDefinidas);
			botonImagenesPre.setOnClickListener(new OnClickListener(){

				public void onClick(View v) {
					// TODO Auto-generated method stub
					DatosCompartidos.CuentaEditar.setNombre(textNombre.getText().toString());
					DatosCompartidos.CuentaEditar.setDescripcion(descripcion.getText().toString());
					Intent intent = new Intent(AltaCuenta.this,ListaImagenesPre.class);
					Bundle bundle = new Bundle();				
					intent.putExtras(bundle);
					startActivity(intent);		
					
				}			
			});	
			
			Button botonImagenPer = (Button)findViewById(R.id.btnImagenPersonal);
			botonImagenPer.setOnClickListener(new OnClickListener(){

				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					DatosCompartidos.CuentaEditar.setNombre(textNombre.getText().toString());
					DatosCompartidos.CuentaEditar.setDescripcion(descripcion.getText().toString());
					
					Intent i = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);				
					startActivityForResult(i, RESULT_LOAD_IMAGE);
				}
				
			});
			
			TextView botonAlta = (TextView)findViewById(R.id.btnAltaCuenta);
			
			if (idCuenta != -1)		
				botonAlta.setText("Modificar Cuenta");		//Cambio el texto del botón	
			
			botonAlta.setOnClickListener(new OnClickListener(){
				//Proceso de alta o modificación
				public void onClick(View v) 
				{
					
					if (textNombre.getText().toString().equals(""))
						mensaje("El nombre de la cuenta no puede estar vacio");
					else
					{
						
						System.out.println("modificar: " + idCuenta);
						
						if (idCuenta != -1)
						{
							
							
							if (!nombreAnterior.equals( textNombre.getText().toString()))
							{	
								if (!Cuenta.updateCuenta(AltaCuenta.this, 
										DatosCompartidos.CuentaEditar.getIdCuenta(), 
										textNombre.getText().toString(),
										DatosCompartidos.CuentaEditar.getImagen(),
										DatosCompartidos.CuentaEditar.getImagenPersonalizada(),
										descripcion.getText().toString()))									
									mensaje("Lo siento, ya existe una cuenta con ese nombre");
								else
									finish();	
							}
							else
							{
								
								Cuenta.updateCuenta(AltaCuenta.this,
										DatosCompartidos.CuentaEditar.getIdCuenta(),
										null,
										DatosCompartidos.CuentaEditar.getImagen(),
										DatosCompartidos.CuentaEditar.getImagenPersonalizada(),
										descripcion.getText().toString());
								finish();					
							}
						}
						else
						{						
							DatosCompartidos.CuentaEditar.setNombre(textNombre.getText().toString());
							DatosCompartidos.CuentaEditar.setDescripcion(descripcion.getText().toString());
							if (!Cuenta.insertCuenta(AltaCuenta.this,DatosCompartidos.CuentaEditar))								
								mensaje("Lo siento, ya existe una cuenta con ese nombre");
							else						
								finish();	
						}					
					}
				}
				
			});
		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}
	
	 @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	super.onActivityResult(requestCode, resultCode, data);
	    				
			//----------------	    	   		    	
			
			if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
				
				Uri selectedImage = data.getData();
				
				// Bloque para conseguir y prepara la rotación de la imagen
		    	Uri imageUri = data.getData();
		    	String[] orientationColumn = {MediaStore.Images.Media.ORIENTATION};
	            Cursor cur = managedQuery(imageUri, orientationColumn, null, null, null);
	            int orientation = -1;
	            if (cur != null && cur.moveToFirst()) {
	                orientation = cur.getInt(cur.getColumnIndex(orientationColumn[0]));
	                System.out.println("orientación " + orientation);
	            }  
	            Matrix matrix = new Matrix();
	            matrix.postRotate(orientation);
	            //
	            
				InputStream is;				
				
				
				try {
				    is = getContentResolver().openInputStream(selectedImage);
				    BufferedInputStream bis = new BufferedInputStream(is);
				    Bitmap bitmap = BitmapFactory.decodeStream(bis);
				    Bitmap bitmapToSave;
				    Bitmap bueno;
				    if (bitmap.getWidth() > bitmap.getHeight())
				    {
				    	bitmapToSave = Bitmap.createScaledBitmap(bitmap, 30, ((bitmap.getHeight() *30) / bitmap.getWidth() ), false);
				    	bueno = Bitmap.createBitmap(bitmapToSave, 0, 0,30, ((bitmap.getHeight() *30) / bitmap.getWidth() ),matrix	, true);
				    }
				    else
				    {
				    	bitmapToSave = Bitmap.createScaledBitmap(bitmap,((bitmap.getWidth() *30) / bitmap.getHeight() ), 30 , false);
				    	bueno = Bitmap.createBitmap(bitmapToSave, 0, 0, ((bitmap.getWidth() *30) / bitmap.getHeight() ),30,matrix	, true);
				    }				   
				   
				    
				    Log.i("Noises of HIll","Bytes imagen original: " + bitmap.getRowBytes());
				    Log.i("Noises of HIll","Bytes imagen a guardar: "+bitmapToSave.getRowBytes());
				    Log.i("Noises of HIll","Bytes imagen a guardar: "+bueno.getRowBytes());
				    
				    
				    if (bueno.getRowBytes() < 501)
				    {
				    	//---
				    	try {
				            
				    		//Preparo carpeta
				    		File sdcard = Environment.getExternalStorageDirectory();
				            File pictureDir = new File(sdcard, "CarteraFresca");
				            pictureDir.mkdirs();
				            File f = null;
				            
				            //Fecha Actual
				            Calendar cal = new GregorianCalendar();
				            Date date = cal.getTime();
				            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");				            
				            System.out.println(df.format(date));
				        
				            String name = "CF" + df.format(date) + ".png";
				            f = new File(pictureDir, name);
				               
				            
				            if (!f.exists()) {
				            	System.out.println("guarda)");
				                name = f.getAbsolutePath();
				                Log.i("Noises of HIll","guardando "+name);
				                FileOutputStream fos = new FileOutputStream(name);
				                bueno.compress(Bitmap.CompressFormat.PNG, 100, fos);
				                fos.flush();
				                fos.close();
				            }
				            
				            try
							{				
								//imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
								DatosCompartidos.CuentaEditar.setImagenPersonalizada(name);						
							}
							catch (Exception ex)
							{	
								System.out.println(ex);
							}
				            
				            
				        } catch (Exception e) {
				        } finally {
				            /*
				            if (fos != null) {
				                fos.close();
				            }
				            */
				        }
				    	//---
				    	
				    }
				    else
				    	this.mensaje("La imagen es demasiado grande, Max. 500bytes");
				    	
				} catch (FileNotFoundException e) 
				{
					Log.e("Noises Of Hill",e.toString());
				}
				
			}
	    
	    
	    }
	 
	 public void mensaje(String cadena)
	 {
			Toast.makeText(this,cadena, Toast.LENGTH_SHORT).show();
	 }
}