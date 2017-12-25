package com.cubetech.comprobante.servicios.domain.valueobject;

import java.util.Arrays;

import com.cubetech.comprobante.servicios.domain.ValueObject;

import lombok.Data;

@Data
public class Archivo implements ValueObject<Archivo>{

	private static final long serialVersionUID = 4053428933411430007L;
	
	private final String correlacion;
	private String nombre;	
	private String tipo;
	private byte[] content;
	
	public boolean sameValueAs(Archivo other){
		return other != null && ((this.content == null && other.content == null) || (this.content!=null && other.content!= null && Arrays.equals(this.content , other.content)));
	}
	
	public void actualiza(Archivo other){
		this.nombre  = other.nombre;
		this.tipo		 = other.tipo;
		this.content = other.content;
	}

}
