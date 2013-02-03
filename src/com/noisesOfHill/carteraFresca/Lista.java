package com.noisesOfHill.carteraFresca;

public class Lista {

	private int imagen;
	private String texto;
	public int getImagen() {
		return imagen;
	}
	
	public Lista(int imagen, String texto)
	{
		this.imagen = imagen;
		this.texto = texto;
	}
	public void setImagen(int imagen) {
		this.imagen = imagen;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	
}
