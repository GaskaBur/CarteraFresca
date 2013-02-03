package com.noisesOfHill.carteraFresca;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
public class Lanzadera extends Activity{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lanzadera);
						
		MiTarea tarea2 = new MiTarea();
		tarea2.execute();
		
		
	}
	
	 private class MiTarea extends AsyncTask<Void, Integer, Boolean> {
	    	
	    	@Override
	    	protected Boolean doInBackground(Void... params) {
	    		
	    		
	    		
	    		try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
	    		
	    		return true;
	    	}
	    	
	    	@Override
	    	protected void onProgressUpdate(Integer... values) {	    			    		
	    	}
	    	
	    	@Override
	    	protected void onPreExecute() {   
	    		
	    	}
	    	
	    	@Override
	    	protected void onPostExecute(Boolean result) {
	    		if(result)
	    		{	    			
	    			Intent intento = new Intent(Lanzadera.this,CarteraFresca.class);
	    			startActivity(intento);
	    			finish();
	    		}
	    	}
	    	
	    	@Override
	    	protected void onCancelled() {
	    		finish();
	    	}
	    }

	

}
