package com.noisesOfHill.carteraFresca;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MenuContextualCategorias extends Activity {
	
	private Lista[] listaca = {
			new Lista(android.R.drawable.ic_menu_delete,"Borrar Categoria"),
			new Lista(android.R.drawable.ic_menu_edit,"Modificar Categoria")};
		
	int idCategoria = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menucontextualcuentas);
		
		Intent intento = getIntent();
		idCategoria = intento.getIntExtra("idCategoria", -1);
				
		ListView lista = (ListView)findViewById(R.id.listaOpcionesContextualCuenta);
		AdaptadorMenuContextual adp = new AdaptadorMenuContextual(this);
		lista.setAdapter(adp);	
		
		lista.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arg2 == 0)
				{
					//borrar Categoría
					
					 AlertDialog.Builder builder = new AlertDialog.Builder(MenuContextualCategorias.this);
					 
					 builder.setMessage("¿Estás seguro de que quieres borrar esta Categoría?.")
					        .setCancelable(false)
					        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
					                 //si	
									Categoria.deleteCategoria(MenuContextualCategorias.this, idCategoria);
					            	finish();
					            }
					        })
					        .setNegativeButton("No", new DialogInterface.OnClickListener() {
					            public void onClick(DialogInterface dialog, int id) {
					                 finish();
					            }
					        });
					 AlertDialog alert = builder.create();
					 alert.show();



				}
				else if (arg2 == 1)
				{
					//modificar Cuenta
					Intent intento = new Intent(MenuContextualCategorias.this,AltaCategoria.class);
					Bundle bundle = new Bundle();
					bundle.putInt("idCategoria", idCategoria);
					intento.putExtras(bundle);
					startActivity(intento);		
					finish();
				}
				
			}
			
		});
	}
	
	class AdaptadorMenuContextual extends ArrayAdapter<Object>{
	 	   Activity context;
	 	   AdaptadorMenuContextual(Activity context){
	 		   super(context,R.layout.listamenus,listaca);
	 		   this.context = context;
	 	   } 	   
	 	   public View getView(int position,View convertView,ViewGroup parent){
	 		   	LayoutInflater inflater = context.getLayoutInflater();
				View item = inflater.inflate(R.layout.listamenus, null);
				
				TextView texto = (TextView)item.findViewById(R.id.txtOpcionMenu);
				texto.setText(listaca[position].getTexto());
				texto.setCompoundDrawablesWithIntrinsicBounds(listaca[position].getImagen(), 0, 0, 0);
				return(item);	  		  	
	 	   } 	   
	    }

}
