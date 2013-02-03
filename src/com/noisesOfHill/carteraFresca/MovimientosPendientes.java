package com.noisesOfHill.carteraFresca;

import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MovimientosPendientes extends Activity {
	private int idCuenta;
	private ImageView imagen;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movimientospendientes);
		Intent intent = getIntent();
		
		idCuenta = intent.getIntExtra("idCuenta", -1);
		String imgPerso = "";
		imgPerso = intent.getStringExtra("imgPerso");
		int ico = intent.getIntExtra("ico", 0);		
		imagen = (ImageView)findViewById(R.id.imageCuenta);
		
		if (imgPerso.equals(""))			
			imagen.setImageResource(ico);	
		else
			imagen.setImageBitmap(BitmapFactory.decodeFile(imgPerso));
		
		TextView volver = (TextView)findViewById(R.id.TextView01);
  		volver.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
  			
  		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		List<Movimiento> listaMovimientos = Movimiento.getMovimientosEfectivo(MovimientosPendientes.this,idCuenta,true);	  	  	
 	  		
  		AdaptadorMovimientos adp = new AdaptadorMovimientos(MovimientosPendientes.this, listaMovimientos);      	  		 	 
  		ListView lstOpciones = (ListView)findViewById(R.id.lstMov);      	  	 
  		lstOpciones.setAdapter(adp);
  		
  		lstOpciones.setOnItemLongClickListener(new OnItemLongClickListener(){

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Movimiento aux = (Movimiento) arg0.getItemAtPosition(arg2);
				Intent intento = new Intent(MovimientosPendientes.this,MenuContextualMovimiento.class);
				Bundle bundle = new Bundle();
				bundle.putInt("idMomvimiento", aux.getIdMovimiento());
				intento.putExtras(bundle);
				startActivity(intento);
				return false;
			}
  			
  		});
  		
  		
	}

}
