package com.cubetech.comprobante.servicios.domain.valueobject;

public enum EstadoCancelacion {
	
	CANCELADO(			201, "Cancelado"),
	CANCELADO_PREV(	202, "Cancelado previamente"),
	NO_VALIDO(			203, "No encontrado o no pertenece al emisor"),
	NO_CANCELABLE(	204, "No aplicable para cancelación"),
	NO_EXISTE(			205, "No existe"),
	NO_PRIMARIO(		206, "No corresponde a un CFDI del sector primario"),
	
	XML_ERROR(						301, "XML mal Formado"),
	SELLO_ERROR(					302, "Sello mal formado o invalido"),
	SELLO_EMISOR(					303, "Sello no corresponde al emisor"),
	CERTIFICADO_ERROR(		304, "Certificado revocado o caduco"),
	CERTIFICADO_INVALIDO(	305, "Certificado inválido"),
	EFIRMA_ERROR(					310, "Uso de certificado de e.firma inválido")
	;
	
	private EstadoCancelacion(int codigo, String descripcion){
		this.codigo = codigo;
		this.descripcion = descripcion;
	}
	
	private int codigo;
	private String descripcion; 
	
	
	public int getCodigo(){
		return codigo;
	}
	public String getDescripcion(){
		return this.descripcion;
	}
	
	public static EstadoCancelacion setEstado(String valor){
		EstadoCancelacion ret = null;
		int valorInt = Integer.parseInt(valor);
		
		switch(valorInt){
		case 201:
			ret = EstadoCancelacion.CANCELADO;
			break;
		case 202:
			ret = EstadoCancelacion.CANCELADO_PREV;
			break;
		case 203:
			ret = EstadoCancelacion.NO_VALIDO;
			break;
		case 204:
			ret = EstadoCancelacion.NO_CANCELABLE;
			break;
		case 205:
			ret = EstadoCancelacion.NO_EXISTE;
			break;
		case 206:
			ret = EstadoCancelacion.NO_PRIMARIO;
			break;
		}
		
		return ret;
	}
	
	
}
