package com.noisesOfHill.carteraFresca;



import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdaptadorImagenes extends ArrayAdapter<Object>
{
	@Override
	public void setDropDownViewResource(int resource) {
		// TODO Auto-generated method stub
		super.setDropDownViewResource(resource);
	}

	Activity context;
	String[] nombres;
	int[] codigos;
	 
	
	public AdaptadorImagenes(Activity context,String[] nombres, int codigos[])
	{
		super(context,R.layout.detallelistaimagendefinida,nombres);
		this.context = context;
		this.nombres = nombres;
		this.codigos = codigos;
		
	}
	
	public View getView(int position,View convertView,ViewGroup parent)
	{
		View item = convertView;
		ViewHolder holder;    		
		if (item == null)
		{
			LayoutInflater inflater = context.getLayoutInflater();
    		item = inflater.inflate(R.layout.detallelistaimagendefinida, null);
    		
    		holder = new ViewHolder();
    		holder.texto = (TextView)item.findViewById(R.id.txtDetalleListaImagenes);
    		    		
    		item.setTag(holder);
    		
		}
		else
		{
			holder = (ViewHolder)item.getTag();
		}    		
		 		
		
		holder.texto.setText(Html.fromHtml(this.nombres[position]));
		holder.texto.setCompoundDrawablesWithIntrinsicBounds(codigos[position], 0, 0, 0);
		
		return item;	
	}
	
	static class ViewHolder
	{
		TextView texto;
	}

}
