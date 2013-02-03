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

public class AltaCategoria extends Activity {
	
	TextView nombre;
	
	int idCategoria = -1;
	
	private static int RESULT_LOAD_IMAGE = 1;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.altacategoria);
		
		Intent intento = getIntent(); 
		idCategoria = intento.getIntExtra("idCategoria", -1);
		System.out.println("IdCategoria "+idCategoria);
		
		if (idCategoria !=-1)
		{
			//Como me es distitno de -1 es que tengo que modificar la posicion m
			TextView titulo = (TextView)findViewById(R.id.tituloAltaCategoria);
			titulo.setText("Modificar Categoria");
			
		}
		
		nombre = (TextView)findViewById(R.id.txtNombreCategoriaAltas);
		
		if (idCategoria == -1)
			DatosCompartidos.categoriaEditar = new Categoria();
		else
			DatosCompartidos.categoriaEditar = new Categoria(AltaCategoria.this,idCategoria);
		
		Button botonImagenesPre = (Button)findViewById(R.id.btnImgDefinidas);
		botonImagenesPre.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				
				//Guardo el nombre antes de abrir el proceso de elegir imagen				
				DatosCompartidos.categoriaEditar.setDescripcion(nombre.getText().toString());				
				Intent intent = new Intent(AltaCategoria.this,ListaImagenesPre.class);
				Bundle bundle = new Bundle();
				bundle.putInt("categoria", 1);
				intent.putExtras(bundle);
				startActivity(intent);		
				
			}			
		});	
		
		Button botonImagenPer = (Button)findViewById(R.id.btnImagenPersonal);
		botonImagenPer.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//Guardo el nombre antes de abrir el proceso de elegir imagen
				DatosCompartidos.categoriaEditar.setDescripcion(nombre.getText().toString());				
				
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				
				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
			
		});
		
		TextView buton = (TextView)findViewById(R.id.btnAltaCategoria);
		if (idCategoria != -1)
			buton.setText("Modificar Categoría");
		buton.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (nombre.getText().toString().equals(""))
				{
					mensaje("El nombre de la categoría no puede estar en blanco");
				}
				else
				{	
					
					if (idCategoria != -1)
					{
						String nombreAnterior = DatosCompartidos.categoriaEditar.getDescripcion();
						if (!nombreAnterior.equals( nombre.getText().toString()))
						{	
							if (!Categoria.updateCategoria(AltaCategoria.this,
									DatosCompartidos.categoriaEditar.getIdCategoria(), 
									nombre.getText().toString(), 
									DatosCompartidos.categoriaEditar.getIcono(), 
									DatosCompartidos.categoriaEditar.getImagen()))									
								mensaje("Lo siento, ya existe una categoría con ese nombre");
							else
								finish();	
						}
						else
						{
							Categoria.updateCategoria(AltaCategoria.this,
									DatosCompartidos.categoriaEditar.getIdCategoria(), 
									null, 
									DatosCompartidos.categoriaEditar.getIcono(), 
									DatosCompartidos.categoriaEditar.getImagen());
							finish();
							
						}
					}
					else
					{
						DatosCompartidos.categoriaEditar.setDescripcion(nombre.getText().toString());
						if (!Categoria.insertNuevaCategoria(AltaCategoria.this,DatosCompartidos.categoriaEditar))
							mensaje("Lo siento, ya existe una Categoría con ese nombre");
					else
							finish();
					}
				}
				
			}
			
		});
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	//--
		
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
							DatosCompartidos.categoriaEditar.setImagen(name);						
						}
						catch (Exception ex)
						{}	
			            
			            
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
    	//--
		
    }
	
	@Override
	protected void onResume() {
		
		// TODO Auto-generated method stub
		super.onResume();
		ImageView imagen = (ImageView)findViewById(R.id.imgCuentaNueva);
		
		nombre.setText(DatosCompartidos.categoriaEditar.getDescripcion().toString());
		
				
		if (!DatosCompartidos.categoriaEditar.getImagen().equals(""))
		{
			imagen.setImageBitmap(BitmapFactory.decodeFile(DatosCompartidos.categoriaEditar.getImagen()));
			imagen.setMaxHeight(50);
			imagen.setMaxWidth(50);
		}
		else			
			imagen.setImageResource(DatosCompartidos.categoriaEditar.getIcono());
		
		
	}

	private void mensaje(String decir){
		Toast.makeText(this, decir, Toast.LENGTH_SHORT).show();
	}

}