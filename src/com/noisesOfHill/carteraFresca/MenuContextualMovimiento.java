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

public class MenuContextualMovimiento extends Activity{
	
	private Lista[] listaca = {
			new Lista(android.R.drawable.ic_menu_edit,"Modificar Movimiento"),
			new Lista(android.R.drawable.ic_menu_slideshow,"Duplicar y editar Movimiento"),
			new Lista(android.R.drawable.ic_menu_delete,"Borrar Movimiento")};	
	private int idMovimiento;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menucontextualcuentas);
		
		
		
		Intent intento = getIntent();
		idMovimiento = intento.getIntExtra("idMomvimiento", -1);
		System.out.println("Id a Borrar: " + idMovimiento);
				
		ListView lista = (ListView)findViewById(R.id.listaOpcionesContextualCuenta);
		AdaptadorMenuContextual adp = new AdaptadorMenuContextual(this);
		lista.setAdapter(adp);	
		
		lista.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				//borrar Movimiento
				switch (arg2)
				{
				case 0:
					Intent intent = new Intent(MenuContextualMovimiento.this,EditMovimiento.class);
					Bundle bundle = new Bundle();
					bundle.putInt("idMovimiento", idMovimiento);
					intent.putExtras(bundle);
					startActivity(intent);
					finish();
					break;
				case 1:
					Intent intento = new Intent(MenuContextualMovimiento.this,EditMovimiento.class);
					Bundle bundleo = new Bundle();
					bundleo.putInt("idMovimiento", idMovimiento);
					bundleo.putInt("duplicar", 1);
					intento.putExtras(bundleo);
					startActivity(intento);
					finish();
					break;
				case 2:
					
					 AlertDialog.Builder builder = new AlertDialog.Builder(MenuContextualMovimiento.this);
					 builder.setMessage("¿Estás seguro de que quieres borrar este Movmiento?.")
						        .setCancelable(false)
						        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
						                 //si
										Movimiento.deleteMovimiento(MenuContextualMovimiento.this, idMovimiento);
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
					break;
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
