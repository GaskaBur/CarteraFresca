package com.noisesOfHill.carteraFresca;
/**
 * @author GaskaBur
 * @producto Cartera Fresca
 * @company Noises Of Hill
 * @version 1.0
 * @since 07/2012
 */

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;

import android.widget.ListView;
import android.widget.TextView;

public class ListadoCategorias extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.categorias);		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

       
        
		List<Categoria> getCategorias = Categoria.getCategorias(ListadoCategorias.this);
                
        AdaptadorListadoCategorias adp = new AdaptadorListadoCategorias(this,getCategorias);
        final ListView lista = (ListView)findViewById(R.id.listadoCategorias);
        lista.setAdapter(adp);
        
        lista.setOnItemLongClickListener(new OnItemLongClickListener(){

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Intent intento = new Intent(ListadoCategorias.this,MenuContextualCategorias.class);
				Categoria aux = (Categoria) arg0.getItemAtPosition(arg2);
				Bundle bundle = new Bundle();
				bundle.putInt("idCategoria", aux.getIdCategoria());				
				intento.putExtras(bundle);
				startActivity(intento);				
				// TODO Auto-generated method stub
				return false;
			}
        	
        });
        
        TextView boton = (TextView)findViewById(R.id.btnCrearCategoria);
        boton.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intento = new Intent(ListadoCategorias.this,AltaCategoria.class);
				startActivity(intento);				
			}        	
        });
        
        TextView botonVolver = (TextView)findViewById(R.id.btnVolver);
        botonVolver.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}        	
        });
        		
	}
}
