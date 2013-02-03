package com.noisesOfHill.carteraFresca;
//RECATORIZADA OK *******************************************************************************************************
import java.util.List;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdaptadorMovimientos extends ArrayAdapter<Movimiento>
{
	@Override
	public void setDropDownViewResource(int resource) {
		// TODO Auto-generated method stub
		super.setDropDownViewResource(resource);
	}

	private Activity context;
	private List<Movimiento> movimientos;	 
	
	public AdaptadorMovimientos(Activity context,List<Movimiento> movimientos)
	{				
		super(context,R.layout.movimiento,movimientos);
		this.context = context;
		this.movimientos = movimientos;		
	}
	
	public View getView(int position,View convertView,ViewGroup parent)
	{
		View item = convertView;
		ViewHolder holder;    		
		if (item == null)
		{
			LayoutInflater inflater = context.getLayoutInflater();
    		item = inflater.inflate(R.layout.movimiento, null);
    		
    		holder = new ViewHolder();
    		holder.fecha = (TextView)item.findViewById(R.id.txtFechaMovimiento);
    		holder.descripcion = (TextView)item.findViewById(R.id.txtDescripcionMovimiento);
    		holder.Importe = (TextView)item.findViewById(R.id.txtImporteMovimiento);
    		holder.categoria = (TextView)item.findViewById(R.id.txtCategoriaView);
    		holder.imagen = (ImageView)item.findViewById(R.id.imgMovimiento);
    		item.setTag(holder);
    		
		}
		else
		{
			holder = (ViewHolder)item.getTag();
		}    		
		String[] parseFecha = this.movimientos.get(position).getFecha().split("-");
		try{		
			holder.fecha.setText(parseFecha[2].substring(0, 2)+"/"+parseFecha[1]+"/"+parseFecha[0]);
		}
		catch (Exception ex)
		{
			holder.fecha.setText(this.movimientos.get(position).getFecha().toString());
		}
		
		holder.descripcion.setText(this.movimientos.get(position).getDescripcion() );
		
				
		if (this.movimientos.get(position).getTipo() == 0)
		{
			holder.Importe.setText(""+String.format("- %.2f", this.movimientos.get(position).getImporte()) +" €");
			holder.Importe.setTextColor(this.context.getResources().getColor(R.color.rojo));
		}
		else
		{
			holder.Importe.setText(""+String.format("+ %.2f", this.movimientos.get(position).getImporte()) +" €");
			holder.Importe.setTextColor(this.context.getResources().getColor(R.color.verde));
		}
		
		holder.categoria.setText(this.movimientos.get(position).getNameCategoria());
		if (this.movimientos.get(position).getImagenCategoria().compareTo("") != 0)
			try
			{
				holder.imagen.setImageBitmap(BitmapFactory.decodeFile(this.movimientos.get(position).getImagenCategoria()));
			}
			catch (Exception ex){
				holder.imagen.setImageResource(R.drawable.ico_anotacion);
			}
		else
			holder.imagen.setImageResource(this.movimientos.get(position).getIconoCategoria());
		return item;	
	}
	
	static class ViewHolder
	{
		TextView fecha;
		TextView descripcion;
		TextView Importe;
		TextView categoria;
		ImageView imagen;
	}
}
