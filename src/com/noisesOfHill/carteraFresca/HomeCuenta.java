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
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class HomeCuenta extends Activity {
	
	private int idCuenta;
	private ImageView imagen;
	private TextView lblCuenta;
	private TextView lblSaldo; 
	private Button btnNuevoMovimiento;
	private Cuenta cuentaTramite;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homecuenta);
		
		Intent intent = getIntent();
		
		idCuenta = intent.getIntExtra("idCuenta", -1); //Esta ha de ser la buena.	
		cuentaTramite = new Cuenta(HomeCuenta.this,idCuenta);
		
		if (idCuenta == -1 ) //Hemos llegado sin cuenta.
			finish();	
		
		lblCuenta = (TextView)findViewById(R.id.lblTituloHomeCuenta);		
		imagen = (ImageView)findViewById(R.id.imageCuenta);
		lblSaldo = (TextView)findViewById(R.id.txtSaldoActual);
		btnNuevoMovimiento = (Button)findViewById(R.id.btnNuevoMovimiento);
		
		btnNuevoMovimiento.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intento = new Intent(HomeCuenta.this,NuevoMovimiento.class);
				Bundle bundle = new Bundle();
				bundle.putInt("idCuenta",idCuenta);
				intento.putExtras(bundle);
				startActivity(intento);
			}
			
		});
		
		TextView verMovAnteriores = (TextView)findViewById(R.id.btnMovimientosPendientes);
		verMovAnteriores.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intento = new Intent(HomeCuenta.this,MovimientosPendientes.class);
				Bundle bundle = new Bundle();
				bundle.putInt("idCuenta", idCuenta);

				bundle.putString("imgPerso",cuentaTramite.getImagenPersonalizada());
				bundle.putInt("ico", cuentaTramite.getImagen());
				intento.putExtras(bundle);
				startActivity(intento);
				
			}
			
		});
	}

	
	@Override
	protected void onResume() {

		super.onResume();	
		
		lblCuenta.setText(cuentaTramite.getNombre());
		
		if (cuentaTramite.getImagenPersonalizada().equals(""))			
			imagen.setImageResource(cuentaTramite.getImagen());	
		else
			imagen.setImageBitmap(BitmapFactory.decodeFile(cuentaTramite.getImagenPersonalizada()));
		
		Float saldo = Movimiento.getSaldoEfectivo(HomeCuenta.this, cuentaTramite.getIdCuenta());
              	  	
  	  	if (saldo != 0)      	  		
  	  	{
  		    lblSaldo.setText("" + String.format("%.2f", saldo) + " €");
  			if (saldo > -1)
  				lblSaldo.setTextColor(HomeCuenta.this.getResources().getColor(R.color.verde));
  			else
  				lblSaldo.setTextColor(HomeCuenta.this.getResources().getColor(R.color.rojo));
  	  	}
  	  	else
  	  		lblSaldo.setText("0 €");
  	  	
  	  	
  	   List<Movimiento> listaMovimientos = Movimiento.getMovimientosEfectivo(HomeCuenta.this, cuentaTramite.getIdCuenta(),false);
  	  	
  	  	        	  		
  		AdaptadorMovimientos adp = new AdaptadorMovimientos(HomeCuenta.this, listaMovimientos);      	  		 	 
  		ListView lstOpciones = (ListView)findViewById(R.id.lstMov);      	  	 
  		lstOpciones.setAdapter(adp);
  		
  		lstOpciones.setOnItemLongClickListener(new OnItemLongClickListener(){

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				Movimiento aux = (Movimiento) arg0.getItemAtPosition(arg2);
				System.out.println(aux.getNameCategoria());
				System.out.println(arg0.getItemAtPosition(arg2));
				System.out.println(arg0.getCount());
				// TODO Auto-generated method stub
				Intent intento = new Intent(HomeCuenta.this,MenuContextualMovimiento.class);
				Bundle bundle = new Bundle();
				bundle.putInt("idMomvimiento", aux.getIdMovimiento());
				
				intento.putExtras(bundle);
				startActivity(intento);
				return false;
			}

			
  			
  		});
  	  	
	}
}