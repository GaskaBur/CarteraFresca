package com.noisesOfHill.carteraFresca;
/**
 * @author GaskaBur
 * @producto Cartera Fresca
 * @company Noises Of Hill
 * @version 1.0
 * @since 07/2012
 */


import java.util.List;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class CarteraFresca extends Activity   {
	
	/**
	 * ---------------------------------------------------------------------------------------------------------------------
	 * ON START
	 */
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
        setContentView(R.layout.cuentas);       
    }

	/**
	 * -----------------------------------------------------------------------------------------------------------------------
	 * ON RESUME
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
        
        List<Cuenta> getCuentas = Cuenta.getCuentas(CarteraFresca.this);
              
        TextView info01 = (TextView)findViewById(R.id.txtInfo01);
        
        AdapterSpinnerCuentas adp = new AdapterSpinnerCuentas(this,getCuentas);
    	adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	ListView listaCuentas = (ListView)findViewById(R.id.listadoCuentas);
    	listaCuentas.setAdapter(adp);
        
        if (getCuentas.size() > 0)
        {         	
        	
        	info01.setVisibility(1);
        	info01.setTextSize(9);
        	info01.setText(R.string.info01);        	
        	
        	listaCuentas.setOnItemLongClickListener(new OnItemLongClickListener(){

				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					//Menú Contextual, borrar modificar cuenta.
					
					Cuenta aux = (Cuenta) arg0.getItemAtPosition(arg2);
					System.out.println("Cuenta que mando: " +aux.getIdCuenta() );					
					Intent intento = new Intent(CarteraFresca.this,MenuContextualCuentas.class);
					Bundle bundle = new Bundle();
					bundle.putInt("position", arg2);
					bundle.putInt("idCuenta",aux.getIdCuenta());
					intento.putExtras(bundle);
					startActivity(intento);
					return false;					
				}
        		
        	});
        	
        	//Selecciono la cuenta en la que operar
        	listaCuentas.setOnItemClickListener(new OnItemClickListener(){

				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					//LLamada a la Cuenta
					Intent intento = new Intent(CarteraFresca.this, HomeCuenta.class);
					Bundle bundle = new Bundle();
					Cuenta aux = (Cuenta) arg0.getItemAtPosition(arg2);
					System.out.println("Cuenta que mando: " +aux.getIdCuenta() );
					bundle.putInt("idCuenta", aux.getIdCuenta());
					bundle.putInt("cuenta",arg2);
					intento.putExtras(bundle);
					startActivity(intento);
				}        		
        	});
        	
        }
        else
        {           	
        	info01.setVisibility(0);
        	info01.setTextSize(14);
        	info01.setText("No tienes ninguna cuenta con la que operar, pulsa en el botón 'Crear Cuenta' para empezar a funcionar.");
        }
        
        TextView btnNuevaCuenta = (TextView)findViewById(R.id.btnCrearCuenta);
       	btnNuevaCuenta.setVisibility(1);
       	
       	btnNuevaCuenta.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
					Intent intentoAlta = new Intent(CarteraFresca.this,AltaCuenta.class);
					startActivity(intentoAlta);
					
			}        		
        });  
       	
       	TextView btnCategorias =(TextView)findViewById(R.id.btnIrCategorias);
       	
       	btnCategorias.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intento = new Intent(CarteraFresca.this,ListadoCategorias.class);
				startActivity(intento);
			}
       		
       	});
	}


}