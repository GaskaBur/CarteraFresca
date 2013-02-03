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

public class EditMovimiento extends Activity {

	private Movimiento movimiento;
	private int idMovimiento;
	private boolean tipoIngreso; //False|0 = reintegro -- true|1 = ingreso
	private int duplicar = -1;
	private List<Categoria> listaCategorias;	
	private int idCat[];
	private int año, mes, dia;
	private TextView txtDescripcion;
	private TextView txtImporte;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nuevomovimiento);	
		Intent intento = getIntent();
		idMovimiento = intento.getIntExtra("idMovimiento", -1);
		duplicar = intento.getIntExtra("duplicar", -1);
		movimiento = new Movimiento(EditMovimiento.this,idMovimiento);
		String fechaca[] = movimiento.getFecha().split("-");
		año = Integer.parseInt(fechaca[0]);
		mes = Integer.parseInt(fechaca[1]) - 1;
		dia = Integer.parseInt(fechaca[2].substring(0, 2));
	}
	
	public void mensaje(String cadena)
	 {
			Toast.makeText(this,cadena, Toast.LENGTH_SHORT).show();
	 }

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
		listaCategorias = null;
		idCat = null;
		listaCategorias = Categoria.getCategorias(this);
		idCat = new int[listaCategorias.size()];		
		
		TextView txt21 = (TextView)findViewById(R.id.textView021);
		txt21.setText("Introduce los datos del movimiento");
		txtImporte = (TextView)findViewById(R.id.txtImporte);
		txtImporte.setText(String.format("%.2f", this.movimiento.getImporte()).replace(",", "."));
		final ImageButton btnIngreso = (ImageButton)findViewById(R.id.btnIngreso);
		if (movimiento.getTipo() == 0){
			tipoIngreso = false;
			btnIngreso.setImageResource(R.drawable.menos);
		}
		else {
			tipoIngreso = true;
			btnIngreso.setImageResource(R.drawable.mas);
		}
		
		
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
		txtDescripcion = (TextView)findViewById(R.id.txtDescripcion);
		txtDescripcion.setText(movimiento.getDescripcion());
		
		final Spinner spnCategorias = (Spinner)findViewById(R.id.spnCategorias);
		
		String cat[] = new String[listaCategorias.size()];
		
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
	        System.out.println(movimiento.getNameCategoria());
	        if (movimiento.getNameCategoria().equals("Sin categoría"))
	        {
				System.out.println("sin categoria");
	        	spnCategorias.setSelection(0);
	        }
			else
			{
				for (int i = 0; i < spnCategorias.getCount(); i++)
				{
					String categoria = (String)spnCategorias.getItemAtPosition(i);
					System.out.println(categoria);
					if (categoria.compareTo(movimiento.getNameCategoria()) == 0)
					{
						System.out.println("entra aqui");
						spnCategorias.setSelection(i);	
						break;
					}
				}
			}
	        
        }
        else
        	spnCategorias.setVisibility(-1);
		
		final DatePicker dp = (DatePicker)findViewById(R.id.datePicker1);
		
		dp.init(año, mes, dia, null);
	
		TextView btnAlta = (TextView)findViewById(R.id.btnAltaMovimiento);
		if (duplicar == -1)
			btnAlta.setText("Modificar Movimiento");
		else
			btnAlta.setText("Crear Movimiento");
		
		Button altaCat = (Button)findViewById(R.id.btnCategoriaDesdeMovimiento);
		altaCat.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				movimiento.setDescripcion(txtDescripcion.getText().toString());
				movimiento.setImporte(Float.parseFloat(txtImporte.getText().toString()));
				spnCategorias.setSelection(0);
				dia = dp.getDayOfMonth();
				mes = dp.getMonth();
				año = dp.getYear();
				Intent intento = new Intent(EditMovimiento.this,AltaCategoria.class);
				startActivity(intento);
				
			}
			
		});
		
		
		btnAlta.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (txtImporte.getText().length() == 0)
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
					if (duplicar == -1)
					{
					Movimiento.updateMovimiento(
							EditMovimiento.this, 
							idMovimiento,
							movimiento.getIdCuenta(), 
							idCategoria, 
							fecha + " 00:00:00",
							descripcion.getText().toString(), 
							Float.parseFloat(txtImporte.getText().toString()), 
							ingreso);
					
					finish();
					}
					else
					{
						Movimiento.insertMovimiento(EditMovimiento.this,
								movimiento.getIdCuenta(), 
								idCategoria, 
								fecha +" 00:00:00", 
								descripcion.getText().toString(), 
								Float.parseFloat(txtImporte.getText().toString()), 
								ingreso);
						
						finish();
					}
				}
			}
			
		});
		super.onResume();
	}

}
