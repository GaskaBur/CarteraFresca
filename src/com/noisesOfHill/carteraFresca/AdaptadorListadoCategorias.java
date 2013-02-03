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
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdaptadorListadoCategorias extends ArrayAdapter<Categoria>
{
	@Override
	public void setDropDownViewResource(int resource) {
		// TODO Auto-generated method stub
		super.setDropDownViewResource(resource);
	}

	Activity context;
	//Categoria[] categorias;
	List<Categoria> categorias;
	 
	
	public AdaptadorListadoCategorias(Activity context,List<Categoria> categorias)
	{
		super(context,R.layout.listacuentas,categorias);
		this.context = context;
		this.categorias = categorias;
		
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
		 		
		
		holder.cuenta.setText(Html.fromHtml(this.categorias.get(position).getDescripcion()));
		holder.cuenta.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
		
		if (categorias.get(position).getImagen().equals(""))			
			holder.imagen.setImageResource(categorias.get(position).getIcono());
		else
			holder.imagen.setImageBitmap(BitmapFactory.decodeFile(categorias.get(position).getImagen()));
			
		
		
		
		return item;	
	}
	
	static class ViewHolder
	{
		TextView cuenta;
		ImageView imagen;
	}


}
