package com.noisesOfHill.carteraFresca;

import java.util.Date;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NuevoMovimiento extends Activity 
{
	private TextView importe;
	private Spinner spnCategorias;
	private int idCuenta;
	private ImageButton btnIngreso;
	private DatePicker dp;
	private TextView alta;
	private boolean tipoIngreso = false;
	
	private List<Categoria> listaCategorias;	
	private int idCat[];	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nuevomovimiento);
		
		
		
        
        
	}
	
	 @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		 listaCategorias = Categoria.getCategorias(this);
			idCat = new int[listaCategorias.size()];
			
			Intent intento = getIntent();
			idCuenta = intento.getIntExtra("idCuenta", -1);
			if (idCuenta == -1)
				finish();
					
			spnCategorias = (Spinner)findViewById(R.id.spnCategorias);
			importe = (TextView)findViewById(R.id.txtImporte);
			dp = (DatePicker)findViewById(R.id.datePicker1);
			btnIngreso = (ImageButton)findViewById(R.id.btnIngreso);
			
			btnIngreso.setOnClickListener(new OnClickListener(){			
				public void onClick(View v) {
						// TODO Auto-generated method stub
						if (tipoIngreso){
							tipoIngreso = false;
							btnIngreso.setImageResource(R.drawable.menos);
							
						}
						else{
							tipoIngreso = true;
							btnIngreso.setImageResource(R.drawable.mas);
						}
				}			
			});
			
			
			String cat[] = new String[listaCategorias.size()];
			
			Button altaCat = (Button)findViewById(R.id.btnCategoriaDesdeMovimiento);
			altaCat.setOnClickListener(new OnClickListener(){

				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intento = new Intent(NuevoMovimiento.this,AltaCategoria.class);
					startActivity(intento);
					
				}
				
			});
			
			
	        
	        if (cat.length > 0)
	        {
				for (int x=0; x<cat.length; x++)
		        {
		        	idCat[x] = listaCategorias.get(x).getIdCategoria();
		        	cat[x] = listaCategorias.get(x).getDescripcion();
		        } 
				
				ArrayAdapter<String> adaptador =
				        new ArrayAdapter<String>(this,
				            android.R.layout.simple_spinner_item, cat);
		        adaptador.setDropDownViewResource(
				        android.R.layout.simple_spinner_dropdown_item);
		        spnCategorias.setAdapter(adaptador);
	        }
	        else
	        	spnCategorias.setVisibility(-1);
	        
	        alta = (TextView)findViewById(R.id.btnAltaMovimiento);
	        alta.setOnClickListener(new OnClickListener(){
	        	
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (importe.getText().length() == 0)
						mensaje("Tienes que introducir un importe");
					else
					{												
						int idCategoria;
						try
						{
							 idCategoria = idCat[spnCategorias.getSelectedItemPosition()];
						}
						catch (Exception ex){ idCategoria = 0;}	
						System.out.println("idCategoria seleccionada - " + idCategoria);
						//DatosCompartidos.CuentaNueva.setDescripcion(descripcion.getText().toString());
						Date date = new Date();					
						String fecha = String.format("%02d", dp.getYear());
		               	fecha += "-"+String.format("%02d", (dp.getMonth()+1));
		               	fecha += "-"+String.format("%02d", dp.getDayOfMonth());
		                System.out.println(fecha);
		                String hora = String.format("%02d", date.getHours());
		                hora += ":"+String.format("%02d", date.getMinutes());
		                hora += ":"+String.format("%02d", date.getSeconds());
		                System.out.println(date.getHours()+"/"+date.getMinutes()+"/"+date.getSeconds());
		                System.out.println(hora);					
						final  TextView descripcion = (TextView)findViewById(R.id.txtDescripcion);					
						boolean ingreso = true;
						if (!tipoIngreso)
							ingreso = false;		
						Movimiento.insertMovimiento(NuevoMovimiento.this,
								idCuenta, 
								idCategoria, 
								fecha +" 00:00:00", 
								descripcion.getText().toString(), 
								Float.parseFloat(importe.getText().toString()), 
								ingreso);
						
						finish();
					}
				}
	        	
	        });
		super.onResume();
	}

	public void mensaje(String cadena)
	 {
			Toast.makeText(this,cadena, Toast.LENGTH_SHORT).show();
	 }
}
