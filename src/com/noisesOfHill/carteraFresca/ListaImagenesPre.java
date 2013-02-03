package com.noisesOfHill.carteraFresca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListaImagenesPre extends Activity {
	
	int modificar = -1;
	int categoria = -1;
	
	//Categorías por defecto
		public static int intCategorias[] = new int[20]; //Referencia al  Drawable
		public static String nombreCategorias[] = new String[20]; // Nombre de la categoría	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imagenespredefinidas);
		
		intCategorias[0] = R.drawable.categoria_agua;
		nombreCategorias[0] = "Agua";
		intCategorias[1] = R.drawable.categoria_alimentacion;
		nombreCategorias[1] = "Alimentación";
		intCategorias[2] = R.drawable.categoria_casa;
		nombreCategorias[2] = "Casa";
		intCategorias[3] = R.drawable.categoria_cine;
		nombreCategorias[3] = "Cine";
		intCategorias[4] = R.drawable.categoria_coche;
		nombreCategorias[4] = "Coche";
		intCategorias[5] = R.drawable.categoria_deportes;
		nombreCategorias[5] = "Deportes";
		intCategorias[6] = R.drawable.categoria_electricidad;
		nombreCategorias[6] = "Electricidad";
		intCategorias[7] = R.drawable.categoria_foto;
		nombreCategorias[7] = "Fotografía";
		intCategorias[8] = R.drawable.categoria_gas;
		nombreCategorias[8] = "Gas";
		intCategorias[9] = R.drawable.categoria_gasolina;
		nombreCategorias[9] = "Gasoleo/Gasolina";
		intCategorias[10] = R.drawable.categoria_higiene;
		nombreCategorias[10] = "Higiene";
		intCategorias[11] = R.drawable.categoria_internet;
		nombreCategorias[11] = "Internet";
		intCategorias[12] = R.drawable.categoria_mascotas;
		nombreCategorias[12] = "Mascotas";
		intCategorias[13] = R.drawable.categoria_musica;
		nombreCategorias[13] = "Música";
		intCategorias[14] = R.drawable.categoria_ocio;
		nombreCategorias[14] = "Ocio";
		intCategorias[15] = R.drawable.categoria_otros;
		nombreCategorias[15] = "Otros";
		intCategorias[16] = R.drawable.categoria_ropa;
		nombreCategorias[16] = "Ropa";
		intCategorias[17] = R.drawable.categoria_telefono;
		nombreCategorias[17] = "Teléfono";
		intCategorias[18] = R.drawable.categoria_viajes;
		nombreCategorias[18] = "Viajes";
		intCategorias[19] = R.drawable.categoria_vicios;
		nombreCategorias[19] = "Vicios";
		
		Intent intento = getIntent();
		categoria = intento.getIntExtra("categoria", -1);		
				
		final ListView lista = (ListView)findViewById(R.id.listaImagenes);
		
		AdaptadorImagenes adp = new AdaptadorImagenes(this, nombreCategorias, intCategorias);
		lista.setAdapter(adp);		
		
		lista.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				if (categoria == 1)
				{
					DatosCompartidos.categoriaEditar.setIcono(intCategorias[arg2]);
					DatosCompartidos.categoriaEditar.setImagen("");
				}
				else
				{
					DatosCompartidos.CuentaEditar.setImagen(intCategorias[arg2]);
					DatosCompartidos.CuentaEditar.setImagenPersonalizada("");
				}				
				
				finish();
				
			}
			
		});		
		
	}

}
