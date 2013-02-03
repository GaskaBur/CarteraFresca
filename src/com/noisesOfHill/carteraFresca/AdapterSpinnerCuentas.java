package com.noisesOfHill.carteraFresca;
/**
 * @author GaskaBur
 * @company Noises Of Hill
 * @version 1.0
 * @since 07/2012
 */


import java.util.List;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterSpinnerCuentas extends ArrayAdapter<Cuenta>
{
	@Override
	public void setDropDownViewResource(int resource) {
		// TODO Auto-generated method stub
		super.setDropDownViewResource(resource);
	}

	Activity context;
	List<Cuenta> listaCuentas;
	 

	public AdapterSpinnerCuentas(Activity context, List<Cuenta> lista)
	{
		super(context,R.layout.listacuentas,lista);
		this.context = context;
		this.listaCuentas = lista;
		
	}
	
	
	
	
	public View getView(int position,View convertView,ViewGroup parent)
	{
		View item = convertView;
		ViewHolder holder;    		
		if (item == null)
		{
			LayoutInflater inflater = context.getLayoutInflater();
    		item = inflater.inflate(R.layout.listacuentas, null);
    		
    		holder = new ViewHolder();
    		holder.cuenta = (TextView)item.findViewById(R.id.listacuenta);
    		holder.imagen = (ImageView)item.findViewById(R.id.imagenCuentas);
    		    		
    		item.setTag(holder);
    		
		}
		else
		{
			holder = (ViewHolder)item.getTag();
		}    		
		 
		holder.cuenta.setText(listaCuentas.get(position).getNombre());
		
		holder.cuenta.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_media_play, 0);
		if (listaCuentas.get(position).getImagenPersonalizada().equals(""))
			holder.imagen.setImageResource(listaCuentas.get(position).getImagen());
		else
			holder.imagen.setImageBitmap(BitmapFactory.decodeFile(listaCuentas.get(position).getImagenPersonalizada()));		
		
		
		return item;	
	}
	
	static class ViewHolder
	{
		TextView cuenta;
		ImageView imagen;
	}


}