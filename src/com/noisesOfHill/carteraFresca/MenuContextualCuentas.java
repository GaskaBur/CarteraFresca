package com.noisesOfHill.carteraFresca;
/**
 * @author GaskaBur
 * @producto Cartera Fresca
 * @company Noises Of Hill
 * @version 1.0
 * @since 07/2012
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MenuContextualCuentas extends Activity 
{

	
	private Lista[] listaca = {
		new Lista(android.R.drawable.ic_menu_delete,"Borrar Cuenta"),
		new Lista(android.R.drawable.ic_menu_edit,"Modificar Cuenta")};	
	
	private int position = 0; // Luego a borrar
	private int idCuenta = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menucontextualcuentas);
		
		Intent intento = getIntent();
		position = intento.getIntExtra("position", -1);
		this.idCuenta = intento.getIntExtra("idCuenta", -1);
		System.out.println("position - " + position + " | idCuenta - " + idCuenta);
				
		ListView lista = (ListView)findViewById(R.id.listaOpcionesContextualCuenta);
		AdaptadorMenuContextual adp = new AdaptadorMenuContextual(this);
		lista.setAdapter(adp);	
		
		lista.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arg2 == 0)
				{
					//borrar Cuenta
					
					 AlertDialog.Builder builder = new AlertDialog.Builder(MenuContextualCuentas.this);
					 
					 builder.setMessage("¿Estás seguro de que quieres borrar esta cuenta?. Perderas todos los movimientos e información relacionados a ella.")
					        .setCancelable(false)
					        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
					            

								public void onClick(DialogInterface dialog, int id) {
					                 //si
									Cuenta.deleteCuenta(MenuContextualCuentas.this, idCuenta);
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
					Intent intento = new Intent(MenuContextualCuentas.this,AltaCuenta.class);
					Bundle bundle = new Bundle();
					bundle.putInt("idCuenta", idCuenta);
					intento.putExtras(bundle);
					startActivity(intento);		
					finish();
				}
				
			}
			
		});
	}	
	
	/**
	 * Adaptador del listado Contextual para editar o borrar una cuenta
	 * @author Gaskabur	 *
	 */
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